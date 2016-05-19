package com.JUtils.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金钱处理工具类
 * 
 * @Author:chenssy
 * @date:2014年8月7日
 */
public class MoneyUtils {
	
	/**
	 * 汉语中数字大写
	 */
	 private static final String[] CN_UPPER_NUMBER = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖" };
	 
	 /**
	  * 汉语中货币单位大写
	  */
	 private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元","拾", "佰", "仟", "万", "拾", 
		 													  "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
		 													  "佰", "仟" };
	 /**
	  * 特殊字符：整
	  */
	 private static final String CN_FULL = "";
	 
	 /**
	  * 特殊字符：负
	  */
	 private static final String CN_NEGATIVE = "负";
	 /**
	  * 零元整
	  */
	 private static final String CN_ZEOR_FULL = "零元整";
	 
	 /**
	  * 金额的精度，默认值为2
	  */
	 private static final int MONEY_PRECISION = 2;
	 
	 /**
	  * 人民币转换为大写,格式为：x万x千x百x十x元x角x分
	  * 
	  * @autor:chenssy
	  * @date:2014年8月7日
	  *
	  * @param numberOfMoney 传入的金额
	  * @return
	  */
	 public static String number2CNMontray(String numberOfMoney) {
		 return number2CNMontray(new BigDecimal(numberOfMoney));
	 }
	 

	/**
	 * 人民币转换为大写,格式为：x万x千x百x十x元x角x分
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param numberOfMoney
	 * 					传入的金额
	 * @return
	 */
	public static String number2CNMontray(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
	}
	
	/**
	 * 将人民币转换为会计格式金额(xxxx,xxxx,xxxx.xx),保留两位小数
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param money
	 * 				待转换的金额
	 * @return
	 */
	public static String accountantMoney(BigDecimal money){
		return accountantMoney(money, 2, 1);
	}
	
	/**
	 * 格式化金额，显示为xxx万元，xxx百万,xxx亿
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param money 
	 * 				待处理的金额
	 * @param scale  
	 * 				小数点后保留的位数
	 * @param divisor 
	 * 				格式化值（10:十元、100:百元,1000千元，10000万元......）
	 * @return
	 */
	public static String getFormatMoney(BigDecimal money,int scale,double divisor){
		return formatMoney(money, scale, divisor) + getCellFormat(divisor);
	}
	
	/**
	 * 获取会计格式的人民币(格式为:xxxx,xxxx,xxxx.xx)
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param money 
	 * 				待处理的金额
	 * @param scale 
	 * 				小数点后保留的位数
	 * @param divisor 
	 * 				格式化值（10:十元、100:百元,1000千元，10000万元......）
	 * @return
	 */
	public static String getAccountantMoney(BigDecimal money, int scale, double divisor){  
        return accountantMoney(money, scale, divisor) + getCellFormat(divisor);
    }  
	
	/**
	 * 将人民币转换为会计格式金额(xxxx,xxxx,xxxx.xx)
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param money 
	 * 				待处理的金额
	 * @param scale 
	 * 				小数点后保留的位数
	 * @param divisor 
	 * 				格式化值
	 * @return
	 */
	private static String accountantMoney(BigDecimal money,int scale,double divisor){
		String disposeMoneyStr = formatMoney(money, scale, divisor);  
        //小数点处理  
        int dotPosition = disposeMoneyStr.indexOf(".");  
        String exceptDotMoeny = null;//小数点之前的字符串  
        String dotMeony = null;//小数点之后的字符串  
        if(dotPosition > 0){  
            exceptDotMoeny = disposeMoneyStr.substring(0,dotPosition);  
            dotMeony = disposeMoneyStr.substring(dotPosition);  
        }else{  
            exceptDotMoeny = disposeMoneyStr;  
        }  
        //负数处理  
        int negativePosition = exceptDotMoeny.indexOf("-");  
        if(negativePosition == 0){  
            exceptDotMoeny = exceptDotMoeny.substring(1);  
        }  
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);  
        reverseExceptDotMoney.reverse();//字符串倒转  
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();  
        StringBuffer returnMeony = new StringBuffer();//返回值  
        for(int i = 0; i < moneyChar.length; i++){  
            if(i != 0 && i % 3 == 0){  
                returnMeony.append(",");//每隔3位加','  
            }  
            returnMeony.append(moneyChar[i]);  
        }  
        returnMeony.reverse();//字符串倒转  
        if(dotPosition > 0){  
            returnMeony.append(dotMeony);  
        }  
        if(negativePosition == 0){  
            return "-" + returnMeony.toString();  
        }else{  
            return returnMeony.toString();  
        }  
	}
	
	/**
	 * 格式化金额，显示为xxx万元，xxx百万,xxx亿
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param money 
	 * 				待处理的金额
	 * @param scale  
	 * 				小数点后保留的位数
	 * @param divisor 
	 * 				格式化值
	 * @return
	 */
	private static String formatMoney(BigDecimal money,int scale,double divisor){
		if (divisor == 0) {
			return "0.00";
		}
		if (scale < 0) {
			return "0.00";
		}
		BigDecimal divisorBD = new BigDecimal(divisor);
		return money.divide(divisorBD, scale, RoundingMode.HALF_UP).toString();
	}
	
	private static String getCellFormat(double divisor){
		String str = String.valueOf(divisor);
		int len = str.substring(0,str.indexOf(".")).length();
		String cell = "";
		switch(len){
			case 1:
				cell = "元";
				break;
			case 2:
				cell = "十元";
				break;
			case 3:
				cell = "百元";
				break;
			case 4:
				cell = "千元";
				break;
			case 5:
				cell = "万元";
				break;
			case 6:
				cell = "十万元";
				break;
			case 7:
				cell = "百万元";
				break;
			case 8:
				cell = "千万元";
				break;
			case 9:
				cell = "亿元";
				break;
			case 10:
				cell = "十亿元";
				break;
		}
		return cell;
	}
}
