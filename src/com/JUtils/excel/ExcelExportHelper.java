package com.JUtils.excel;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @Description: Excel 生成通用类，为了兼容，所有 Excel 统一生成 Excel2003 即：xx.xls
 * @Project：javaUtils
 * @Author : chenssy
 * @Date ： 2014年6月15日 下午9:09:38
 */
public class ExcelExportHelper {
	
	/** 时间格式：默认为yyyy-MM-dd */
	private String DATE_PATTERN = "yyyy-MM-dd";
	
	/** 图片宽度，默认为：100 */
	private int IMAGE_WIDTH = 30;
	 
	/** 图片高度，默认为：50 */
	private int IMAGE_HEIGHT = 5;
	
	/** 单元格的最大宽度 */
	private int[] maxWidth;
	
	/** 
	 * 单页支持最多数据列：超过65534会出错
	 * 若数据列多余65534则需要通过MORE_EXCEL_FLAG、MORE_SHEET_FLAG来区别生成多个Excel、还是sheet
	 */
	private int maxRowCount = 2500;
	
	/** 大量数据，多个Excel标识---0001 */
	private String  MORE_EXCEL_FLAG = "0001";
	
	/** 大量数据，多个sheet标识---0001 */
	private String MORE_SHEET_FLAG = "0002";
	
	/**
	 * 默认构造函数 
	 */
	public ExcelExportHelper(){
	}
	
	/**
	 * @param datePattern 指定的时间格式
	 */
	public ExcelExportHelper(String datePattern){
		this.DATE_PATTERN = datePattern;
	}
	
	/**
	 * @param imageWidth 
	 * 					指定图片的宽度
	 * @param imageHeight
	 * 				           指定图片的高度
	 */
	public ExcelExportHelper(int imageWidth,int imageHeight){
		this.IMAGE_HEIGHT = imageHeight;
		this.IMAGE_WIDTH = imageWidth;
	}
	
	/**
	 * @param datePatter 
	 * 					指定时间格式
	 * @param imageWidth 
	 * 					指定图片的宽度
	 * @param imageHeight 
	 * 					指定图片的高度
	 */
	public ExcelExportHelper(String datePatter,int imageWidht,int imageHeight){
		this.DATE_PATTERN = datePatter;
		this.IMAGE_HEIGHT = imageHeight;
		this.IMAGE_WIDTH = imageWidht;
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]<br>
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须对应（javaBean的属性顺序）：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月15日 下午9:18:37
	 * 
	 * @param header  
	 * 				表格属性列名数组
	 * @param excelList 
	 * 			需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean
	 *          属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 	
	 * 			表格标题名
	 * @return 生成的HSSFWorkBook
	 * @version 1.0
	 */
	public HSSFWorkbook exportExcel(String[] header,List<Object> excelList,String sheetTitle){
		//生成一个Excel
		HSSFWorkbook book = new HSSFWorkbook();  
		//生成一个表格
		sheetTitle = getSheetTitle(sheetTitle);   //判断、设置sheetTitle
		HSSFSheet sheet = book.createSheet(sheetTitle);
		
		//设置Excel里面数据
		setExcelContentData(book,sheet,header,excelList);
		
		System.out.println("——————————————————ExcelExportHelper:Excel生成成功...");
		
		return book;
	}
	
	/**
	 * 
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]<br>
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:02:02
	 * 
	 * @param header  
	 * 				Excel表头
	 * @param properties  
	 * 				表头对应javaBean中的属性
	 * @param excelList  
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
	 * 				javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle  
	 * 				表格标题名
	 * 
	 * @return 生成的HSSFWorkbook
	 * @version 1.0
	 */
	public HSSFWorkbook exportExcel(String[] header,String[] properties,List<Object> excelList,
			String sheetTitle){
		//生成一个Excel
		HSSFWorkbook book = new HSSFWorkbook();
		// 生成一个表格
		sheetTitle = getSheetTitle(sheetTitle); // 判断、设置sheetTitle
		HSSFSheet sheet = book.createSheet(sheetTitle);

		// 设置Excel里面数据
		setExcelContentData(book, sheet, header, properties ,excelList);

		System.out.println("——————————————————ExcelExportHelper:Excel生成成功...");

		return book;
	}

	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月17日 下午2:24:38
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param filePath 
	 * 				Excel文件保存位置 
	 * @param fileName 
	 * 				Excel文件名
	 * 
	 * @return
	 * @version 1.0
	 */
	public void exportExcelAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String filePath,String fileName){
		//生成Excel
		HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);
		
		//保存生成的Excel
		saveExcel(book,filePath,fileName);
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]<Br>
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenming 
	 * @date 2014年6月19日 下午6:24:56
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param properties 
	 * 				表头对应javaBean中的属性
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param filePath 
	 * 				Excel文件保存位置 
	 * @param fileName 
	 * 				Excel文件名
	 * @version 1.0
	 */
	public void exportExcelAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String filePath,String fileName){
		//生成Excel
		HSSFWorkbook book = exportExcel(header, properties,excelList, sheetTitle);	
		//保存生成的Excel
		saveExcel(book,filePath,fileName);
	}

	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包 zip 格式保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月18日 下午12:36:01
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle
	 *				表格标题名
	 * @param filePath 		
	 * 				zip文件保存位置 
	 * @param excelName  
	 * 				Excel名称
	 * @param zipName 
	 * 				zip名称
	 * 
	 * @version 1.0
	 */
	public void exportExcelAndZip(String[] header,List<Object> excelList,String sheetTitle,
			String filePath,String excelName,String zipName){
		//生成Excel
		HSSFWorkbook book = exportExcel(header, excelList, sheetTitle);
		
		//将生成的Excel打包保存起来
		List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
		books.add(book);
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包 zip 格式保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:33:04
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param properties
	 *				表头对应javaBean的属性
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param filePath 
	 * 				zip文件保存位置 
	 * @param excelName  
	 * 				Excel名称
	 * @param zipName
	 * 				zip名称
	 * 
	 * @version 1.0
	 */
	public void exportExcelAndZip(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String filePath,String excelName,String zipName){
		//生成Excel
		HSSFWorkbook book = exportExcel(header, properties,excelList, sheetTitle);
				
		//将生成的Excel打包保存起来
		List<HSSFWorkbook> books = new ArrayList<HSSFWorkbook>();
		books.add(book);
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月17日 下午9:53:15
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * 
	 * @return List<HSSFWorkbook>
	 * @version 1.0
	 */
	public List<HSSFWorkbook> exportExcelForBigData(String[] header,List<Object> excelList,String sheetTitle,
			String flag){
		List<HSSFWorkbook> list = new ArrayList<>();    //创建表数据结果集

		//判断需要生成几个Excel
		int num  = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount : excelList.size() / maxRowCount + 1;
		
		HSSFWorkbook book = new HSSFWorkbook();
		List<Object> newList  = null;    //新数据列表
		String newTitle = null;    //新title
		for(int i = 0 ; i < num ; i++){
			//计算新的数据列表
			int beginRowNum = maxRowCount * i;
			int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
			newList = excelList.subList(beginRowNum, endRowNum);
			newTitle = getSheetTitle(sheetTitle) + "_" + i;    
			if(MORE_EXCEL_FLAG.equals(flag)){     //如果是创建多个Excel
				book = exportExcel(header, newList, newTitle);
				list.add(book);
			}
			else if(MORE_SHEET_FLAG.equals(flag)){   //创建多sheet
				HSSFSheet sheet = book.createSheet(newTitle);
				setExcelContentData(book,sheet,header,newList);
			}
		}
		
		if(MORE_SHEET_FLAG.equals(flag)){   //创建多sheet
			list.add(book);
		}
		
		return list;
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:41:23
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param properties 
	 * 				表头对应javaBean的属性
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * @return List<HSSFWorkbook>
	 * @version 1.0
	 */
	public List<HSSFWorkbook> exportExcelForBigData(String[] header,String[] properties,
			List<Object> excelList,String sheetTitle, String flag){
		List<HSSFWorkbook> list = new ArrayList<>();    //创建表数据结果集
		// 判断需要生成几个Excel
		int num = excelList.size() % maxRowCount == 0 ? excelList.size() / maxRowCount : excelList.size() / maxRowCount + 1;

		HSSFWorkbook book = new HSSFWorkbook();
		List<Object> newList = null; // 新数据列表
		String newTitle = null; // 新title
		for (int i = 0; i < num; i++) {
			// 计算新的数据列表
			int beginRowNum = maxRowCount * i;
			int endRowNum = maxRowCount * (i + 1) > excelList.size() ? excelList.size() : maxRowCount * (i + 1);
			newList = excelList.subList(beginRowNum, endRowNum);
			newTitle = getSheetTitle(sheetTitle) + "_" + i;
			if (MORE_EXCEL_FLAG.equals(flag)) { // 如果是创建多个Excel
				book = exportExcel(header,properties, newList, newTitle);
				list.add(book);
			} else if (MORE_SHEET_FLAG.equals(flag)) { // 创建多sheet
				HSSFSheet sheet = book.createSheet(newTitle);
				setExcelContentData(book, sheet, header, properties,newList);
			}
		}

		if (MORE_SHEET_FLAG.equals(flag)) { // 创建多sheet
			list.add(book);
		}
		return list;
	}
	
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月17日 下午10:39:15
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * @param filePath
	 * 			 	文件保存路径
	 * @param fileName 
	 * 				保存文件名
	 * @return 
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String fileName){
		//获取数据结果集
		List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);
		String _fileName = "";
		for(int i = 0 ; i < books.size() ; i ++){
			HSSFWorkbook book = books.get(i);
			_fileName = getFileName(fileName) + "_0" + i;
			//保存Excel文件
			saveExcel(book, filePath, _fileName);
		}
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将Excel保存至某个路径下,
	 * 如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午8:22:25
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param properties 
	 * 				表头对应javaBean属性
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * @param filePath 
	 * 				文件保存路径
	 * @param fileName 
	 * 				保存文件名
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String fileName){
		//获取数据结果集
		List<HSSFWorkbook> books = exportExcelForBigData(header, properties,excelList, sheetTitle, flag);
		
		String _fileName = "";
		for(int i = 0 ; i < books.size() ; i ++){
			HSSFWorkbook book = books.get(i);
			_fileName = getFileName(fileName) + "_0" + i;
			//保存Excel文件
			saveExcel(book, filePath, _fileName);
		}
	}
	
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包成 ZIP 
	 * 保存至某个路径下,如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、excelList规则如下：<br>
	 * header、excelList中的Bean必须一一对应(javaBean的属性顺序)：如下<br>
	 * header：姓名、年龄、性别、班级<br>
	 * Bean：name、age、sex、class<br>
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午10:39:15
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * @param filePath 
	 * 				文件保存路径
	 * @param excelName 
	 * 				Excel文件名
	 * @param zipName 
	 * 				zip文件名
	 * @return 
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndZipAndSave(String[] header,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String excelName,String zipName){
		//获取生成的Excel集合
		List<HSSFWorkbook> books = exportExcelForBigData(header, excelList, sheetTitle, flag);
		
		//将生成的Excel打包并保存
		zipExcelAndSave(books, filePath, zipName, excelName);
	}
	
	/**
	 * 通用方法，使用 java 反射机制，根据提供表头 header ，数据列 excelList 生成 Excel,并将 Excel 打包成 ZIP 
	 * 保存至某个路径下,如有图片请转换为byte[]<br>
	 * 用于大数据量时使用,涉及到一个表只能有65536行,当数据量较大时会直接写入下一个表(excel、sheet)
	 * header、properties需要一一对应：<Br>
	 * header = ["学号","年龄","性别","班级"]
	 * properties = ["id","age","sex","class"],其对应的excelList中javaBean的属性值
	 * 
	 * @author chenssy 
	 * @date 2014年6月19日 下午8:24:21
	 * 
	 * @param header 
	 * 				表格属性列名数组
	 * @param properties 
	 * 				表头对应javaBean属性
	 * @param excelList 
	 * 				需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *              javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param sheetTitle 
	 * 				表格标题名
	 * @param flag 
	 * 				分页标识为。 flag == 0001：生成多个Excel,flag == 0002：生成多个sheet
	 * @param filePath 
	 * 				文件保存路径
	 * @param excelName  
	 * 				Excel文件名
	 * @param zipName 
	 * 				ZIP文件名
	 * @version 1.0
	 */
	public void exportExcelForBigDataAndZipAndSave(String[] header,String[] properties,List<Object> excelList,String sheetTitle,
			String flag,String filePath,String excelName,String zipName){
		//获取生成的Excel集合
		List<HSSFWorkbook> books = exportExcelForBigData(header, properties,excelList, sheetTitle, flag);
		
		//将生成的Excel打包并保存
		zipExcelAndSave(books, filePath, zipName, excelName);
	}

	/**
	 * 填充Excel数据内容
	 * @author chenssy 
	 * @date 2014年6月17日 下午10:32:34
	 * @param book Excel
	 * @param sheet sheet
	 * @param header Excel头部title
	 * @param excelList Excel数据列
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	private void setExcelContentData(HSSFWorkbook book,HSSFSheet sheet,String[] header,List<Object> excelList) {
		//设置列头样式(居中、变粗、蓝色)
		HSSFCellStyle headerStyle = book.createCellStyle();
		setHeaderStyle(headerStyle, book);

		// 设置单元格样式
		HSSFCellStyle cellStyle = book.createCellStyle();
		setCellStyle(cellStyle, book);

		// 创建头部
		HSSFRow row = createHeader(sheet, headerStyle, header);

		// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		
		int index = 0;
		/* 避免在迭代过程中产生的新对象太多，这里讲循环内部变量全部移出来 */
		Object t = null;    
		HSSFCell cell = null;
		Field field = null;
		String fieldName = null;
		String getMethodName = null;
		Class tCls = null;
		Method getMethod = null;
		Object value = null;
		// 遍历集合数据，产生数据行
		Iterator<Object> it = excelList.iterator();
		maxWidth = new int[header.length];   //初始化单元格宽度
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			// 设置数据列
			t = it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  //构建getter方法
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName,new Class[] {});
				    value = (Object) getMethod.invoke(t, new Object[] {});
					// 将value设置当单元格指定位置
					setCellData(row, index, i, value, cell, sheet, patriarch, book);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (SecurityException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				}
			}
		}
		
		System.out.println("-------------------------填充Excel数据成功..........");
	}
	
	/**
	 * 填充Excel内容
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:00:35
	 * @param book
	 * @param sheet
	 * @param header
	 * @param properties
	 * @param excelList
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, String[] properties,
			List<Object> excelList) {
		//设置列头样式(居中、变粗、蓝色)
		HSSFCellStyle headerStyle = book.createCellStyle();
		setHeaderStyle(headerStyle, book);

		// 设置单元格样式
		HSSFCellStyle cellStyle = book.createCellStyle();
		setCellStyle(cellStyle, book);

		// 创建头部
		HSSFRow row = createHeader(sheet, headerStyle, header);

		// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		/* 为了避免迭代过程中产生过多的新对象，这里将循环内部变量全部移出来 */
		int index = 0;
		Object t = null;
		HSSFCell cell = null;
		Object o = null;
		Class clazz = null;
		PropertyDescriptor pd = null;
		Method getMethod = null;
		// 遍历集合数据，产生数据行
		Iterator<Object> it = excelList.iterator();
		maxWidth = new int[header.length];   //初始化单元格宽度
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			// 设置数据列
			t = it.next();
			for(int i = 0 ; i < header.length ; i++){
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				o = null;    //每一个单元格都需要将O设置为null
				try {
					clazz = t.getClass();
					pd = new PropertyDescriptor(properties[i],clazz);
					getMethod = pd.getReadMethod();   // 获得get方法
					if (pd != null) {  
			           o  = getMethod.invoke(t);   //执行get方法返回一个Object  
			        }  
					setCellData(row, index, i, o, cell, sheet, patriarch, book);
				} catch (IntrospectionException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message："+e.getMessage());
				}
			}
		}

		System.out.println("——————————————————填充Excel数据成功..........");
	}
	
	/**
	 * 设置sheet的title，若为空则为yyyyMMddHH24mmss
	 * @author chenssy 
	 * @date 2014年6月16日 下午1:46:06
	 * @param sheetTitle 
	 * @return
	 * @version 1.0
	 */
	private  String getSheetTitle(String sheetTitle) {
		String title = null;
		if(sheetTitle != null && !"".equals(sheetTitle)){
			title = sheetTitle;
		}
		else{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
			title = sdf.format(date);
		}
		return title;
	}
	
	/**
	 * 设置Excel图片的格式：字体居中、变粗、蓝色、12号
	 * @author chenssy 
	 * @date 2014年6月16日 下午8:46:49
	 * @param headerStyle
	 * 				头部样式
	 * @param book
	 * 		  		生产的excel book 	 HSSFWorkbook对象	
	 * @version 1.0
	 */
	private void setHeaderStyle(HSSFCellStyle headerStyle,HSSFWorkbook book) {
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //水平居中
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
		//设置字体
		HSSFFont font = book.createFont();
		font.setFontHeightInPoints((short) 12);     //字号：12号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //变粗
		font.setColor(HSSFColor.BLUE.index);   //蓝色
		
		headerStyle.setFont(font);
	}
	
	/**
	 * 设置单元格样式
	 * @author chenssy 
	 * @date 2014年6月17日 上午11:00:53
	 * @param cellStyle
	 * 			单元格样式
	 * @param book
	 * 			book HSSFWorkbook对象
	 * @version 1.0
	 */
	private void setCellStyle(HSSFCellStyle cellStyle, HSSFWorkbook book) {
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
		
		HSSFFont font = book.createFont();
		font.setFontHeightInPoints((short)12);
		
		cellStyle.setFont(font);
	}
	
	/**
	 * 根据头部样式、头部数据创建Excel头部
	 * @author chenssy 
	 * @date 2014年6月17日 上午11:37:28
	 * @param sheet 
	 * 				sheet
	 * @param headerStyle 
	 * 				头部样式
	 * @param header 
	 * 				头部数据
	 * @return 设置完成的头部Row
	 * @version 1.0
	 */
	private HSSFRow createHeader(HSSFSheet sheet,HSSFCellStyle headerStyle,
			String[] header) {
		HSSFRow headRow = sheet.createRow(0);
		headRow.setHeightInPoints((short)(20));   //设置头部高度
		//添加数据
		HSSFCell cell = null;
		for(int i = 0 ; i < header.length ; i++){
			cell = headRow.createCell(i);
			cell.setCellStyle(headerStyle);
			HSSFRichTextString text = new HSSFRichTextString(header[i]);
			cell.setCellValue(text);
		}
		
		return headRow;
	}
	
	/**
	 * 设置单元格数据
	 * @author chenssy 
	 * @date 2014年6月17日 上午11:48:14
	 * @param row  
	 * 				指定行
	 * @param index 
	 * @param i 
	 * 				行数
	 * @param value 
	 * 				单元格值 cellValue
	 * @param cell 
	 * 				单元格 HSSFCell对象
	 * @param sheet 
	 * 				sheet HSSFSheet对象
	 * @param patriarch  
	 * 				顶级画板 用于实现突破
	 * @param book 
	 * 			Excel HSSFWorkbook对象
	 * @version 1.0
	 */
	private void setCellData(HSSFRow row, int index ,int i ,Object value,HSSFCell cell,HSSFSheet sheet,HSSFPatriarch patriarch,HSSFWorkbook book) {
		String textValue = null; 
		if (value instanceof Date) {    //为日期设置时间格式
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			textValue = sdf.format(date);  
		}
		if(value instanceof byte[]){   //byte为图片
			//设置图片单元格宽度、高度
			row.setHeightInPoints((short)(IMAGE_HEIGHT * 10));
			sheet.setColumnWidth(i, IMAGE_WIDTH * 256);
		    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,(short) i, index, (short) i, index);   
	        anchor.setAnchorType(3);   
	        //插入图片  
	        byte[] bsValue = (byte[]) value;
	        patriarch.createPicture(anchor, book.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG)); 
		}
		else{   //其余全部当做字符处理
			if(value != null){
				textValue = String.valueOf(value);
			}
			else{
				textValue = "";
			}
		}
		// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
		if (textValue != null) {
			Pattern p = Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher = p.matcher(textValue);
			
			//设置单元格宽度，是文字能够全部显示
			setCellMaxWidth(textValue,i);
			sheet.setColumnWidth(i, maxWidth[i]);    //设置单元格宽度
			row.setHeightInPoints((short)(20));   //设置单元格高度
			if (matcher.matches()) {
				// 是数字当作double处理
				cell.setCellValue(Double.parseDouble(textValue));
			} else {
				cell.setCellValue(textValue);
			}
		}
	}

	/**
	 * 获取文件名，若为空，则规则为：yyyyMMddHH24mmss+6位随机数
	 * @author chenssy 
	 * @date 2014年6月17日 下午5:44:27
	 * @param fileName
	 * 				文件名
	 * @return
	 * @version 1.0
	 */
	private String getFileName(String fileName) {
		if(fileName == null || "".equals(fileName)){
			//日期
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
			//随机数
			Random random = new Random();
			fileName = sdf.format(date) + String.valueOf(Math.abs(random.nextInt() * 1000000));
		}
		return fileName;
	}
	
	/**
	 * 根据字数来获取单元格大小,并更新当前列的最大宽度
	 * @author chenssy 
	 * @date 2014年6月17日 下午7:35:52
	 * @param textValue 
	 * @param 指定列
	 * @return
	 * @version 1.0
	 */
	private void setCellMaxWidth(String textValue,int i ) {
		int size = textValue.length();
		int width = (size + 6) * 256;
		if(maxWidth[i] <= width){
			maxWidth[i] = width;
		}
	}
	
	/**
	 * 将生成的Excel保存到指定路径下
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:10:17
	 * @param book 
	 * 			生成的Excel HSSFWorkbook对象
	 * @param filePath 
	 * 			需要保存的路劲
	 * @param fileName 
	 * 			Excel文件名
	 * @version 1.0
	 */
	private void saveExcel(HSSFWorkbook book, String filePath, String fileName) {
		//检测保存路劲是否存在，不存在则新建
		checkFilePathIsExist(filePath);
		//将Excel保存至指定目录下
		fileName = getFileName(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath + "\\" + fileName + ".xls");
			book.write(out); 
			System.out.println("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + fileName + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("——————————————————保存Excel文件失败。exportExcelForListAndSave,message："+e.getMessage());
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将生成的Excel打包并保存到指定路径下
	 * @author chenssy 
	 * @date 2014年6月19日 下午6:18:09
	 * @param book 
	 * 			生成的Excel HSSFWorkbook list集合
	 * @param filePath 
	 * 			保存路劲
	 * @param zipName 
	 * 			zip 文件名
	 * @param excelName 
	 * 			Excel文件名
	 * @version 1.0
	 */
	private void zipExcelAndSave(List<HSSFWorkbook> books,String filePath,String zipName,String excelName){
		//检测保存路径是否存在，若不存在则新建
		checkFilePathIsExist(filePath);
		
		zipName = getFileName(zipName);
		excelName = getFileName(excelName);
		
		//将Excel打包并保存至指定目录下
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		try {
			out = new FileOutputStream(filePath + "\\" + zipName + ".zip");
			zip = new ZipOutputStream(out);
			HSSFWorkbook book = null;
			String _excelName = "";
			for (int i = 0; i < books.size(); i++) {
				book = books.get(i);
				_excelName = getFileName(excelName) + "_0" + i;
				ZipEntry entry = new ZipEntry(_excelName + ".xls");
				zip.putNextEntry(entry);
				book.write(zip);
			}
			System.out.println("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + zipName + ".xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + e.getMessage());
		} finally {
			if (zip != null) {
				try {
					zip.flush();
					zip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 检测保存路径是否存在，不存在则新建
	 * @author chenssy 
	 * @date 2014年6月18日 下午1:05:17
	 * @param filePath  
	 * 				文件路径
	 * @version 1.0
	 */
	private void checkFilePathIsExist(String filePath) {
		File file = new File(filePath);
		
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
