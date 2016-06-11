package com.JUtils.QRCode;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MatrixToImageWriterEx {


	private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();
	
	/**
	 * 根据内容生成二维码数据
	 * @param content 二维码文字内容[为了信息安全性，一般都要先进行数据加密]
	 * @param width 二维码照片宽度
	 * @param height 二维码照片高度
	 * @return
	 */
	public static BitMatrix createQRCode(String content, int width, int height){
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
		//设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        // 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = null;  
        try {  
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints); 
        } catch (WriterException e) {  
            e.printStackTrace();  
        }
        
        return matrix;
	}

	/**
	 * 写入二维码、以及将照片logo写入二维码中
	 * @param matrix 要写入的二维码
	 * @param format 二维码照片格式
	 * @param imagePath 二维码照片保存路径
	 * @param logoPath logo路径
	 * @throws java.io.IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());
		
		//添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
		BufferedImage img = ImageIO.read(new File(imagePath));
		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, DEFAULT_CONFIG);
	}
	
	/**
	 * 写入二维码、以及将照片logo写入二维码中
	 * @param matrix 要写入的二维码
	 * @param format 二维码照片格式
	 * @param imagePath 二维码照片保存路径
	 * @param logoPath logo路径
						 * @param logoConfig logo配置对象
						 * @throws java.io.IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());
		
		//添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
		BufferedImage img = ImageIO.read(new File(imagePath));
		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, logoConfig);
	}

	/**
	 * 将照片logo添加到二维码中间
	 * @param image 生成的二维码照片对象
	 * @param imagePath 照片保存路径
	 * @param logoPath logo照片路径
	 * @param formate 照片格式
	 */
	public static void overlapImage(BufferedImage image, String formate, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) {
		try {
			//将logo写入二维码中
			drawImage(logoPath, image, logoConfig);
			
			//写入logo照片到二维码
			ImageIO.write(image, formate, new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将照片添加到二维码中间，并生成流
	 * @author:chenming
	 * @date:2014年12月31日
	 *
	 * @param matrix 要写入的二维码
	 * @param formate 照片格式
	 * @param logoPath	要写入照片的路径
	 * @param logoConfig logo配置对象  可以为null，为 null 默认 DEFAULT_CONFIG
	 * @throws java.io.IOException
	 */
	public static void overlapImage(BitMatrix matrix,String formate,String logoPath,MatrixToLogoImageConfig logoConfig,OutputStream out) throws IOException{
		//将matrix转换为bufferImage
		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
		
		//将logo照片绘制到二维码中间
		drawImage(logoPath, image, logoConfig);
		
		//输出
		ImageIO.write(image, formate, out);
	}
	
	/**
	 * 将照片添加到二维码中间，并生成流
	 * @author:chenming
	 * @date:2014年12月31日
	 *
	 * @param image 要写入的二维码
	 * @param formate 照片格式
	 * @param logoPath	要写入照片的路径
	 * @param logoConfig logo配置对象  可以为null，为 null 默认 DEFAULT_CONFIG
	 * @throws java.io.IOException
	 */
	public static void overlapImage(BufferedImage image,String formate,String logoPath,MatrixToLogoImageConfig logoConfig,OutputStream out) throws IOException{
		//将logo照片绘制到二维码中间
		drawImage(logoPath, image, logoConfig);
		
		//输出
		ImageIO.write(image, formate, out);
	}
	
	/**
	 * 将logo添加到二维码中间
	 * @author:chenming
	 * @date:2014年12月31日
	 *
	 * @param logoPath	logo路径
	 * @param image	需要绘制的二维码图片
	 * @param logoConfig	配置参数
	 * @throws java.io.IOException
	 */
	private static void drawImage(String logoPath,BufferedImage image,MatrixToLogoImageConfig logoConfig) throws IOException{
		if(logoConfig == null){
			logoConfig = DEFAULT_CONFIG;
		}
		
		try {
			BufferedImage logo = ImageIO.read(new File(logoPath));
			logo.setRGB(0, 0, BufferedImage.TYPE_INT_BGR);
			Graphics2D g = image.createGraphics();
			
			//考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
			int width = image.getWidth() / logoConfig.getLogoPart();
			int height = image.getHeight() / logoConfig.getLogoPart();
			
			//logo起始位置，此目的是为logo居中显示
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			
			//绘制图
			g.drawImage(logo, x, y, width, height, null);
			
			//给logo画边框
			//构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
//		g.setStroke(new BasicStroke(logoConfig.getBorder()));
//		g.setColor(logoConfig.getBorderColor());
//		g.drawRect(x, y, width, height);
			
			g.dispose();
		} catch (Exception e) {   //捕捉异常后不做任何处理，防止图片路径错误而导致二维码生成失败
			
		}
	}
	
}
