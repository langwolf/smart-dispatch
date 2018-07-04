package com.lioncorp.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/****
 * 对字符串的常规操作
 * 
 */
public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/** 取整数，默认值-1 */
	public static int getInt(String str) {
		return getInt(str, -1);
	}

	/**
	 * 把字符型数字转换成整型.
	 * 
	 * @param str
	 *            字符型数字
	 * @return int 返回整型值。如果不能转换则返回默认值defaultValue.
	 */
	public static int getInt(String str, int defaultValue) {
		if (str == null)
			return defaultValue;
		if (isInt(str)) {
			return Integer.parseInt(str);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 判断一个字符串是否为数字
	 */
	public static boolean isInt(String str) {
		if (str == null)
			return false;
		return str.matches("\\d+");
	}
	
	/**
	 * 是否全数字
	 * @param str
	 * @return
	 */
	public static boolean isAllNum(String str){
	    return str.matches("[0-9]+");
	}
	/**
	 * 截取某字符串中指定字符串之后的一段字符串 StringUtil.subAfter("abcba", "b") = "cba"
	 */
	public static String subAfter(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 判断字符串是否正常
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFine(final String str) {
		try {
			if (str == null || str.trim().length() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断多个字符串是否正常
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isAllFine(String... strs) {
		try {
			for (String s : strs) {
				if (!isFine(s)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 使HTML的标签失去作用*
	 * 
	 * @param input
	 *            被操作的字符串
	 * @return String
	 */
	public static final String escapeHTMLTag(String input) {
		if (input == null)
			return "";
		// input = input.trim().replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\n", "<br>");
		input = input.replaceAll("'", "&#39;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("\\\\", "&#92;");
		return input;
	}

	public static int calculateTextCharNum(String text) {
		int wordNum = 0;
		for (int i = 0, len = text.length(); i < len; i++) {
			int charCode = text.codePointAt(i);
			if (charCode >= 0 && charCode <= 128)
				wordNum += 1;
			else
				wordNum += 2;
		}
		// logger.info("text : {} - char num: {}", text, wordNum);
		return wordNum;
	}

	public static double calculateWordNumByReg(String text) {
		double wordNum = 0;
		Pattern pattern1 = Pattern.compile("[A-Za-z0-9,.，。、'/!@#$%]");
		Matcher matcher1 = pattern1.matcher(text);
		while (matcher1.find()) {
			wordNum += 0.5;
		}
		Pattern pattern2 = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher2 = pattern2.matcher(text);
		while (matcher2.find()) {
			wordNum += 1;
		}
		return wordNum;
	}

	public static boolean OnlyHanziYinwenShuzi(String text) {
		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(text);
		boolean b = match.matches();
		return b;
	}

	public static String OnlyRemainHanziYinwenShuzi(String text) {
		if (text == null || text.isEmpty())
			return text;
		StringBuffer temp = new StringBuffer();
		int len = text.length();
		for (int i = 0; i < len; i++) {
			if (OnlyHanziYinwenShuzi(text.charAt(i) + ""))
				temp.append(text.charAt(i));

		}
		return temp.toString();
	}

	public static boolean isHanziYinwenShuzi(String text) {
		if (text == null || text.isEmpty())
			return true;
		int len = text.length();
		for (int i = 0; i < len; i++) {
			if (!OnlyHanziYinwenShuzi(text.charAt(i) + ""))
				return false;

		}
		return true;
	}

	/**
	 * 去除所有空格信息
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去掉收尾空格
	 * 
	 * @param source
	 * @return
	 */
	public static String trimSpace(String source) {
		return source == null ? source : source.replaceAll("^[\\s　]*|[\\s　]*$", "").replace(" ", "");
	}

	/**
	 * @param len
	 *            需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
	 * @param symbol
	 *            用于表示省略的信息的字符，如“...”,“>>>”等。
	 * @return 返回处理后的字符串
	 */
	public static String getSub(String str, int len, String symbol) {
		if (str == null)
			return "";
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gbk");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0)
					counterOfDoubleByte++; // 通过判断字符的类型来进行截取
			}
			if (counterOfDoubleByte % 2 == 0)
				str = new String(b, 0, len, "gbk") + symbol;
			else
				str = new String(b, 0, len - 1, "gbk") + symbol;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String getSub(String str, int len) {
		return getSub(str, len, "");
	}

	public static String getSuffix(String str, int len) {
		if (str == null)
			return "";
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gbk");
			if (b.length <= len)
				return str;
			for (int i = b.length - len; i < b.length; i++) {
				if (b[i] < 0)
					counterOfDoubleByte++; // 通过判断字符的类型来进行截取
			}
			if (counterOfDoubleByte % 2 == 0)
				str = new String(b, b.length - len, len, "gbk");
			else
				str = new String(b, b.length - len - 1, len + 1, "gbk");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * 在数字前补零
	 * 
	 * @param num
	 *            数字
	 * @param length
	 *            输出位数
	 */
	public static String addzero(int num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	/***
	 * 截取最后一个表情
	 * 
	 * @param originString
	 * @return
	 */
	public static String safeCutExpression(String originString) {
		if (!originString.contains("[") || originString.length() <= 6) {
			return originString;
		}
		for (int i = originString.length() - 1; i >= 0; i--) {
			if (']' == originString.charAt(i)) {
				return originString;
			}
			if ('[' == originString.charAt(i)) {
				int len = originString.length() - i;
				// System.out.println(len);
				if (len < 6) {
					return originString.substring(0, originString.length() - len);
				}
				break;
			}
		}
		return originString;
	}

	public static void main(String[] args) {
		// String s =
		// filterHTML(" <p>&lt;p&gt;dfdsfdsfdsfdsfgfgdfg&lt;/p&gt;</p>");
		// String un = unEscapeStr(s);
		// System.out.println(s);
		// System.out.println(un);
		String ss = "哈哈s[啊！？]";
		System.out.println(safeCutExpression(ss));
	}
}
