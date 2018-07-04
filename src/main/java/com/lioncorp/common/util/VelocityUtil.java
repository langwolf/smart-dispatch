package com.lioncorp.common.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class VelocityUtil {

	public static final String REGEX_UNICODE = "[\r\t\f\\x0b\\xa0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]";
	public static final String DEFAULT_PROFILE_IMG_URL = "http://mimg.126.net/p/butter/1008031648/img/face_big.gif";
	public static final String DEFAULT_PROFILE_BIG_IMG_URL = "http://img1.cache.netease.com/t/img/default80.png";
	private static final int OIMAGE_SERVERS = 9;
	private static final String OIMAGE_URL = "http://timge%s.126.net/image?w=%s&amp;h=%s&amp;url=%s&amp;gif=%s&amp;quality=85";
	private static final String OIMAGE_URL_NOHEIGHT = "http://timge%s.126.net/image?w=%s&amp;url=%s&amp;gif=%s&amp;quality=85";
	public static final String DEFAULT_PROFILE_IMG_URL_OLD = "http://img1.cache.netease.com/t/img/default48.png";
	private static final 	DecimalFormat df = new DecimalFormat();
	private static final  String STYLE = "0,000";
	/**
	 * 
	 * @param time
	 *            时间
	 * @param formate
	 *            格式yyyy年MM月dd日
	 * @return
	 */
	public static String getDate(long time, String formate) {
		try {
			if (StringUtils.isEmpty(formate)) {
				formate = "yyyy年MM月dd日";
			}
			SimpleDateFormat formator = new SimpleDateFormat(formate);
			return formator.format(new Date(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	
	/**
	 * 
	 * @param time
	 *            时间
	 * @param formate
	 *            格式yyyy年MM月dd日
	 * @return
	 */
	public static String getDate(Date date, String formate) {
		try {
			if (date == null) {
				return "";
			}
			if (StringUtils.isEmpty(formate)) {
				formate = "yyyy年MM月dd日";
			}
			SimpleDateFormat formator = new SimpleDateFormat(formate);
			return formator.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	public static String format(String format, Date time) {
        SimpleDateFormat formator = new SimpleDateFormat(format);
        return formator.format(time);
    }
	
	/**
	 * 
	 * @param time
	 *            是长 秒
	 * @param formate
	 * @return
	 */
	public static String getDuration(long duration) {
		if (duration == 0) {
			return "";
		}
		try {
			duration = duration / 1000;
			long hour = duration / 3600;
			long min = (duration % 3600) / 60;
			long s = (duration % 3600) % 60;
			if (hour == 0) {
				if (min == 0) {
					return s + "秒";
				} else {
					return min + "分钟" + s + "秒";
				}
			} else {
				return hour + "小时" + min + "分钟" + s + "秒";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}

	// 显示给前台页面的上次在线的时间
	public static String showDuration(Date lastLoginTime) {
		if (lastLoginTime == null) {
			return "";
		}
		return showDuration(lastLoginTime.getTime());
	}

	// 显示给前台页面的上次在线的时间
	public static String showDuration(long lastLoginTime) {
		long duration = new Date().getTime() - lastLoginTime;
		if (duration == 0) {
			return "";
		}
		try {
			duration = duration / 1000; // 多少秒
			long hour = duration / 3600; // 多少小时
			int day = (int) (hour / 24);
			long min = (duration % 3600) / 60;
			if (day == 0) {
				if (hour == 0) {
					if (min == 0) {
						return 1 + "分钟";
					} else {
						return min + "分钟";
					}
				} else {
					return hour + "小时";
				}
			} else {
				return day + "天";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static int floor(double number) {
		return (int) Math.floor(number);
	}


	public static void main(String[] args) {
//			String ss="人民币共:20000000(元),波豆共:25000";
//	java.util.regex.Pattern parseSignPattern1 = java.util.regex.Pattern
//					.compile("人民币共:(.+?)\\(元\\)");
//	java.util.regex.Pattern parseSignPattern2 = java.util.regex.Pattern
//					.compile("波豆共:(.+?)$");
//			StringBuffer sb = new StringBuffer();
//			StringBuffer sb1 = new StringBuffer();
//			Matcher m = parseSignPattern1.matcher(ss);
//			if(m.find()){
//				m.appendReplacement(sb,"人民币共:"+formatNumber(Long.parseLong(m.group(1)))+"\\(元\\)");		
//			}
//			m.appendTail(sb);
//			System.out.println(sb);
//			Matcher m1 = parseSignPattern2.matcher(sb);
//			if(m1.find()){
//				m1.appendReplacement(sb1,"波币共:"+formatNumber(Long.parseLong(m1.group(1))));		
//	
//			}
//			m1.appendTail(sb1);
//			System.out.println(sb1);
		System.out.println(formatNumber("231412341234.23"));
	}

	/**
	 * 转义
	 * 
	 * @param input
	 * @return
	 */
	public static String escapeHTMLTag(String input) {
		if (input == null)
			return "";
		input = input.trim().replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll(REGEX_UNICODE, " ");
		return input.trim();
	}

	/**
	 * 反转义
	 * 
	 * @param text
	 * @return
	 */
	public static String unescapeHTMLTag(String text) {
		if (text == null) {
			return "";
		}
		text = text.replaceAll("&quot;", "\"");
		text = text.replaceAll("&gt;", ">");
		text = text.replaceAll("&lt;", "<");
		text = text.trim().replaceAll("&amp;", "&");
		return text.trim();
	}

	/**
	 * 中文一个2个字符,英文1个字符,截取size个字符
	 * 
	 * @param text
	 * @return
	 */
	public static String getSubString(String text, int size) {
		if (text == null) {
			return "";
		}
		int resultCount = 0;
		int totalCount = 0;
		for (int i = 0; i < text.length(); i++) {
			int c = text.codePointAt(i);
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				totalCount++;
			} else {
				totalCount += 2;
			}
			if (totalCount <= size) {
				resultCount++;
			} else {
				break;
			}
		}
		if (resultCount < text.length()) {
			return text.substring(0, resultCount) + "...";
		} else {
			return text.substring(0, resultCount);
		}
	}

	/**
	 * 中文一个2个字符,英文1个字符,截取size个字符
	 * 
	 * @param text
	 * @return
	 */
	public static String getSubString2(String text, int size) {
		if (text == null) {
			return "";
		}
		int resultCount = 0;
		int totalCount = 0;
		for (int i = 0; i < text.length(); i++) {
			int c = text.codePointAt(i);
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				totalCount++;
			} else {
				totalCount += 2;
			}
			if (totalCount <= size) {
				resultCount++;
			} else {
				break;
			}
		}
		if (resultCount < text.length()) {
			return text.substring(0, resultCount);
		} else {
			return text.substring(0, resultCount);
		}
	}

	/**
	 * 根据byte的长度来subString操作
	 * 
	 * @param srcStr
	 * @param length
	 * @return
	 */
	public static String subStringByByte(String srcStr, int length)

	{
		StringBuffer sb = new StringBuffer(length);
		int srcLength = srcStr.length();// source string length
		int tempLength = 0;// the byte length
		for (int i = 0; i < srcLength; i++) {
			String tempStr = String.valueOf(srcStr.charAt(i));// string consists
																// of a char
			byte[] b = tempStr.getBytes();// the byte length in the tempStr
			tempLength += b.length;
			if (length >= tempLength)
				sb.append(tempStr);
			else
				break;
		}
		return sb.toString();
	}

	/**
	 * 得到字符串长度
	 * 
	 * @param text
	 * @return
	 */
	public static int getLength(String text) {
		if (text == null) {
			return 0;
		}
		int totalCount = 0;
		for (int i = 0; i < text.length(); i++) {
			int c = text.codePointAt(i);
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				totalCount++;
			} else {
				totalCount += 2;
			}
		}
		return totalCount;
	}

	/**
	 * 名字标红
	 * 
	 * @param text
	 * @param keywords
	 * @return
	 */
	public static String makeNameRed(String text, String keywords) {
		if (StringUtils.isEmpty(text)) {
			return "";
		}
		if (StringUtils.isEmpty(keywords)) {
			return text;
		}
		return text.replace(keywords, "<span class=\"cDRed\">" + keywords
				+ "</span>");
	}

	/**
	 * 处理js
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String escapeJavaScript(String str) throws Exception {
		if (str == null || str.length() == 0) {
			return null;
		}
		StringWriter out = new StringWriter(str.length() * 2);
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			if (ch < ' ')
				switch (ch) {
				case 8: // '\b'
					out.write(92);
					out.write(98);
					break;

				case 10: // '\n'
					out.write(92);
					out.write(110);
					break;

				case 9: // '\t'
					out.write(92);
					out.write(116);
					break;

				case 12: // '\f'
					out.write(92);
					out.write(102);
					break;

				case 13: // '\r'
					out.write(92);
					out.write(114);
					break;

				case 11: // '\013'
				default:
					break;
				}
			else
				switch (ch) {
				case 39: // '\''
					out.write(39);
					break;

				case 34: // '"'
					out.write(92);
					out.write(34);
					break;

				case 92: // '\\'
					out.write(92);
					out.write(92);
					break;

				default:
					out.write(ch);
					break;
				}
		}
		return out.toString();
	}

	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

	public static String encodeSpecialCode(String source) {
		source = source.replaceAll("\"", "&#34");
		source = source.replaceAll("'", "&#39");
		source = source.replaceAll("\\(", "&#40");
		source = source.replaceAll("\\)", "&#41");
		return source;
	}

	public static String filterHtml(String str) {
		String regxpForHtml = "<([^>]*)>";
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static int parseInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
		}
		return 0;
	}

	public static String encodeURL(String url, String encode) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		String result = url;
		try {
			return URLEncoder.encode(url, encode);
		} catch (Exception e) {
		}
		return result;
	}

	public static String encodeURL(String url) {
		return encodeURL(url, "utf-8");
	}

	/*
	 * 随机获取oimage的服务器地址
	 */
	@Deprecated
	public static String randOimage() {
		Random rand = new Random();
		int value1 = rand.nextInt(8);
		if (value1 <= 0)
			value1 = 1;
		if (value1 >= 8)
			value1 = 8;

		char items[] = new char[] { 'a', 'b', 'c' };
		int value2 = rand.nextInt(3);
		if (value2 <= 0)
			value2 = 0;
		if (value2 >= 3)
			value2 = 2;

		return items[value2] + Integer.toString(value1);
	}

	@SuppressWarnings("deprecation")
	public static String getWeek(Date date) {
		if (date == null)
			return "";
		int week = date.getDay();
		String str = null;
		if (0 == week) {
			str = "周日";
		} else if (1 == week) {
			str = "周一";
		} else if (2 == week) {
			str = "周二";
		} else if (3 == week) {
			str = "周三";
		} else if (4 == week) {
			str = "周四";
		} else if (5 == week) {
			str = "周五";
		} else if (6 == week) {
			str = "周六";
		} else {
			str = "周" + week;
		}
		return str;
	}

	// 将以||@分割的text分割开
	public static String[] splitAtText(String org) {
		return org.split("\\|\\|\\@");
	}

	public static boolean checkMobile(String mobile) {
		Pattern pattern = Pattern.compile("^1(3|5|8)\\d{9}$");
		Matcher matcher = pattern.matcher(mobile);
		return matcher.find();
	}

	// public static String sublength(String content,int length){
	// if(StringUtils.isBlank(content)||length<=0){
	// return "";
	// }
	// StringBuffer result = new StringBuffer();
	// int resultLength = 0;
	// for(int i=0;i<content.length();i++){
	// char ch = content.charAt(i);
	// if(CharUtils.isAscii(ch)){
	// resultLength++;
	// }else{
	// resultLength+=2;
	// }
	// result.append(ch);
	// if(resultLength>=2*length){
	// break;
	// }
	// }
	// return result.toString();
	// }
	/**
	 * replace each char by replacement from start util length times
	 * 
	 * @param content
	 * @param start
	 * @param length
	 * @param replacement
	 * @return
	 */
	public static String replace(String content, int start, int length,
			String replacement) {
		if (StringUtils.isBlank(content)) {
			return "";
		}
		if (length <= 0 || start < 0 || start >= content.length()) {
			return content;
		}
		String r = replacement;
		if (StringUtils.isBlank(replacement)) {
			r = "";
		}
		int end = start + length;
		StringBuffer result = new StringBuffer(content.length());
		for (int i = 0; i < content.length(); i++) {
			if (i >= start && i < end) {
				result.append(r);
			} else {
				char ch = content.charAt(i);
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static String escapeHTML(String input) {
		if (input == null)
			return "";
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("'", "&apos;");
		input = input.replaceAll("&", "&amp;");
		return input.trim();
	}

	public static String escapeSequence(String input) {
		if (input == null)
			return "";
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("'", "&apos;");
		input = input.replaceAll("/*", "&nbsp;");
		input = input.replaceAll("*/", "&nbsp;");
		input = input.replaceAll(REGEX_UNICODE, "&nbsp;");
		return input.trim();
	}

	public static String escHTML(String s) {
		if (s == null) {
			return "";
		}
		s = s.trim();
		StringBuilder buffer = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == '"') {
				buffer.append("&quot;");
			} else if (ch == '&') {
				buffer.append("&amp;");
			} else if (ch == '<') {
				buffer.append("&lt;");
			} else if (ch == '>') {
				buffer.append("&gt;");
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static final String EMAIL_PATTERN = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	public static boolean isEmail(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern p = Pattern.compile(EMAIL_PATTERN);
		Matcher m = p.matcher(str);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	public static final String escapeParenthesis(String source) {

		if (source == null) {
			return source;
		}
		source = source.replaceAll("\\(", "&#40");
		source = source.replaceAll("\\)", "&#41");
		source = source.replaceAll("\\u0028", "&#40");
		source = source.replaceAll("\\u0029", "&#41");
		return source;
	}

	/**
	 * Get a OImage url.
	 * 
	 * @param url
	 * @param w
	 * @param h
	 * @return
	 */

	public static String getOImageUrl(String url, int w) {
		return getOImageUrl(url, w, -1, false);
	}

	public static String getOImageUrl(String url, int w, boolean enableGif) {
		return getOImageUrl(url, w, -1, enableGif);
	}

	public static String getOImageUrl(String url, int w, int h) {
		return getOImageUrl(url, w, h, false);
	}

	public static String getOImageUrl(String url, int w, int h,
			boolean enableGif) {
		if (StringUtils.isBlank(url)) {
			url = DEFAULT_PROFILE_IMG_URL;
		}
		long serverId = url.charAt(url.length() - 1) % OIMAGE_SERVERS + 1;
		String server = String.valueOf(serverId);
		try {
			if (h < 0) {
				return String.format(OIMAGE_URL_NOHEIGHT, server, int2Str(w),
						URLEncoder.encode(url, "utf8"), enableGif ? "0" : "1");
			} else {
				return String.format(OIMAGE_URL, server, int2Str(w),
						int2Str(h), URLEncoder.encode(url, "utf8"),
						enableGif ? "0" : "1");
			}
		} catch (Exception e) {
			if (h < 0) {
				return String.format(OIMAGE_URL_NOHEIGHT, server, w, url,
						enableGif ? "0" : "1");
			} else {
				return String.format(OIMAGE_URL, server, w, h, url,
						enableGif ? "0" : "1");
			}
		}
	}

	private static String int2Str(int x) {
		return x == Integer.MIN_VALUE ? "" : Integer.toString(x);
	}

	public static String getAnonymousName(String timestamp) {
		String anonymousName = String.valueOf(RandomUtils.nextInt(100000000));
		if (StringUtils.isNotBlank(timestamp)) {
			try {
				int size = timestamp.length();
				anonymousName = timestamp.substring(size - 8);
			} catch (Exception e) {
			}
		}
		return anonymousName;
	}

	/**
	 * This method escape the HTML format and quote the string as a valid JSON
	 * String.
	 * 
	 * @param str
	 * @return
	 */
	public static String escHTMLAndJSONExt(String str) {
		str = escHTMLAndJSON(str);
		if (str.contains("#")) {
			str = " " + str.trim() + " ";
		}
		return str;
	}

	/**
	 * This method escape the HTML format and quote the string as a valid JSON
	 * String.
	 * 
	 * @param str
	 * @return
	 */
	public static String escHTMLAndJSON(String str) {
		str = getHTMLValidText(str);
		return escapeJSONString(str);
	}

	public static String getHTMLValidText(String s) {
		if (s == null) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == '"') {
				buffer.append("&quot;");
			} else if (ch == '&') {
				buffer.append("&amp;");
			} else if (ch == '<') {
				buffer.append("&lt;");
			} else if (ch == '>') {
				buffer.append("&gt;");
			} else if (ch == '\'') {
				buffer.append("&#39;");
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String escHTMLNoTrim(String s) {
		if (s == null) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == '"') {
				buffer.append("&quot;");
			} else if (ch == '&') {
				buffer.append("&amp;");
			} else if (ch == '<') {
				buffer.append("&lt;");
			} else if (ch == '>') {
				buffer.append("&gt;");
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String escJSON(String str) {
		return escapeJSONString(str);
	}

	public static String escReplyBuildingJSON(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		str = str.replace("\r\n", "\n");
		str = str.replaceAll("[\n-\r]", "<br/>");
		str = escapeJSONString(str);
		return str;
	}

	public static String prettySubStr(String str, int size) {
		if (str == null) {
			return null;
		}
		String subStr = StringUtils.substring(str, 0, size);
		if (subStr.length() < str.length()) {
			subStr += "...";
		}
		return subStr;
	}

	private static String escapeJSONString(String string) {
		if (string == null || string.length() == 0) {
			return "";
		}

		char b;
		char c = 0;
		int i;
		int len = string.length();
		StringBuilder sb = new StringBuilder(len * 2);
		String t;
		char[] chars = string.toCharArray();
		char[] buffer = new char[1030];
		int bufferIndex = 0;
		for (i = 0; i < len; i += 1) {
			if (bufferIndex > 1024) {
				sb.append(buffer, 0, bufferIndex);
				bufferIndex = 0;
			}
			b = c;
			c = chars[i];
			switch (c) {
			case '\\':
			case '"':
				buffer[bufferIndex++] = '\\';
				buffer[bufferIndex++] = c;
				break;
			case '/':
				if (b == '<') {
					buffer[bufferIndex++] = '\\';
				}
				buffer[bufferIndex++] = c;
				break;
			default:
				if (c < ' ') {
					switch (c) {
					case '\b':
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 'b';
						break;
					case '\t':
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 't';
						break;
					case '\n':
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 'n';
						break;
					case '\f':
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 'f';
						break;
					case '\r':
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 'r';
						break;
					default:
						t = "000" + Integer.toHexString(c);
						int tLength = t.length();
						buffer[bufferIndex++] = '\\';
						buffer[bufferIndex++] = 'u';
						buffer[bufferIndex++] = t.charAt(tLength - 4);
						buffer[bufferIndex++] = t.charAt(tLength - 3);
						buffer[bufferIndex++] = t.charAt(tLength - 2);
						buffer[bufferIndex++] = t.charAt(tLength - 1);
					}
				} else {
					buffer[bufferIndex++] = c;
				}
			}
		}
		sb.append(buffer, 0, bufferIndex);
		return sb.toString();
	}

	/**
	 * 提供给前端模板使用.按长度截取字符串,一个全角字符算两个长度
	 * 
	 * @param content
	 * @param length
	 * @return
	 */
	public static String sublength(String content, int length) {
		return sublength(content, length, true);
	}

	public static String REGEX = "[\\u4e00-\\u9fa5]";

	/**
	 * 计算字符串长度，汉字算2个，英文1个
	 * 
	 * @param str
	 * @return
	 */
	public static int charlength(String str) {
		int sum = 0;
		int i = 0;
		for (; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch >= '\u4E00' && ch <= '\u9FA5') {
				sum += 2;
			} else {
				sum += 1;
			}
		}
		return sum;
	}

	/**
	 * 提供给前端模板使用.按长度截取字符串,汉字按两个字符计算<br>
	 * 如果超过长度时,最后一个为汉字,则该汉字被截断
	 * 
	 * @param content
	 * @param length
	 * @return
	 */
	public static String sublength(String str, int length, boolean appendSuffix) {
		int sum = 0;
		int i = 0;
		if (str == null) {
			return "";
		}
		for (; i < str.length(); i++) {
			if (sum >= length) {
				break;
			}
			char ch = str.charAt(i);
			if (ch >= '\u4E00' && ch <= '\u9FA5') {
				sum += 2;
			} else {
				sum += 1;
			}
		}
		return str.substring(0, i)
				+ (appendSuffix && (sum > length || i < str.length()) ? "..."
						: "");
	}

	/**
	 * 提供给前端模板使用.截取给定字符串的前length个字符
	 * 
	 * @param content
	 * @param length
	 * @return
	 */
	public static String substr(String content, int length) {
		if (StringUtils.isBlank(content) || length <= 0) {
			return "";
		}
		if (content.length() <= length) {
			return content;
		}
		return content.substring(0, length);
	}

	// 双字节字符(包含了对汉字的匹配)
	private final static String DOUBLE_BYTE_CHAR_REGEX = "[^\\x00-\\xff]";

	/**
	 * 汉字算两个字符
	 * 
	 * @param content
	 * @return
	 */
	public static int length(String content) {
		if (StringUtils.isBlank(content)) {
			return 0;
		}
		int resultLength = 0;
		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			if (String.valueOf(ch).matches(DOUBLE_BYTE_CHAR_REGEX)) {
				resultLength += 2;
			} else {
				resultLength++;
			}
		}

		return resultLength;
	}

	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String formatNumber(long number) {
		String num = String.valueOf(number);
		String result="";
		StringBuffer buffer=new StringBuffer(num);
		num=buffer.reverse().toString();
		for(int i=0;i<num.length();i=i+3){
			if(num.length()-i>=3){
				result+=num.substring(i,i+3);
			}else{
				if(i==num.length()-1){
					result+=num.substring(num.length()-1);
				}else{
					result+=num.substring(i,num.length());
				}
			}
			result+=",";
			}
		result=result.substring(0, result.length()-1);
		buffer =new StringBuffer(result);
		return buffer.reverse().toString();
	}

	public static String subString(String str, int length) {
		return subString(str, length, true);
	}

	/**
	 * 截取字符串，双字节字符算两个长度。 截取长度处于最后一个双字节中间时，该字符不会添加到结果中。
	 * 
	 * @param str
	 * @param length
	 * @param appendSuffix
	 * @return
	 */
	public static String subString(String str, int length, boolean appendSuffix) {
		if (str == null) {
			return "";
		}
		int sum = 0;
		int index = 0;
		int strLength = str.length();
		for (int i = 0; i < strLength; i++) {
			char ch = str.charAt(i);
			int thisLen = 0;
			if (String.valueOf(ch).matches(DOUBLE_BYTE_CHAR_REGEX)) {
				thisLen = 2;
			} else {
				thisLen = 1;
			}

			if ((sum + thisLen) > length) {
				break;
			}
			sum += thisLen;
			index = i;
			if (sum >= length) {
				break;
			}
		}
		if (index + 1 >= strLength) {// 取整个字符串
			return str;
		} else {
			return str.substring(0, index + 1) + (appendSuffix ? "..." : "");
		}
	}

    
	public static String formatNumber(double numberDouble,String style) {
		try{
			if(!StringUtils.isEmpty(style)){
				df.applyPattern(style);
			}else{
				df.applyPattern(STYLE);
			}
			return df.format(numberDouble);
		}catch(Exception e){
			
		}
		return "";
	}
	
    public static String formatNumber(String numberDouble) {
    	if(numberDouble != null) {
    		return formatNumber(Math.floor(Double.parseDouble(numberDouble)), "##,###");
    	}
    	return "0";
    }
    
    
    //把金额用"，" 四位一分割
    public static String formatCash(Object cash){
    	if(null == cash){
    		return "";
    	}
    	String cashStr = String.valueOf(cash);
    	if(cashStr != null) {
    		return formatNumber(Double.parseDouble(cashStr), "###,####.##");
    	}
    	return "0";
    }
    
    //波币转人民币
    public static String bobiToRMB(Object bobi){
    	if(null == bobi){
    		return "0.00";
    	}
    	BigDecimal b = new BigDecimal(bobi.toString());
    	return b.divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    //分成前波币转分成后人民币
    public static String fencheng(Object bobi, String ratioStr){
    	double ratio = Double.parseDouble(ratioStr);
    	String cash = bobiToRMB(bobi);
    	if (cash != null && !"".equals(cash)){
    		return (new BigDecimal(cash).multiply(new BigDecimal(ratio))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    	}
    	return "0.00";
    }
    
    //两个波币相加
    public static String getSum(Object f1, Object f2){
    	if(null == f1){
    		f1 = 0;
    	}
    	BigDecimal b1 = new BigDecimal(f1.toString());
    	if(null == f2){
    		f2 = 0;
    	}
    	BigDecimal b2 = new BigDecimal(f2.toString());
    	return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    //小数点转百分比
    public static String radioToPercent(Object bobi){
    	if(null == bobi){
    		return "0%";
    	}
    	BigDecimal b = new BigDecimal(bobi.toString());
    	return b.multiply(new BigDecimal("100")).setScale(0).toString()+"%";
    }
    

}
