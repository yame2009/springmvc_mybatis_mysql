package com.hb.util.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionHtml {

	/**
	 * 除去HTML标签-script、script、HTML标签
	 * 
	 * @param html
	 * @return
	 */
	public static String RemoveHtml(String html) {
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义script的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(html);
			html = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(html);
			html = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(html);
			html = m_html.replaceAll(""); // 过滤HTML标签
			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(html);
			html = m_html1.replaceAll(""); // 过滤HTML标签
			html = ReplaceTag(html);
			html = html.replace("\n", "");
			html = html.replace("\r", "");
		} catch (Exception e) {
		}
		return html;
	}

	/**
	 * 去除JavaScript标签
	 * 
	 * @param html
	 * @return
	 */
	public static String RemoveJavaScript(String html) {
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(html);
			html = m_script.replaceAll(""); // 过滤script标签
			html = ReplaceTag(html);
		} catch (Exception e) {

		}
		return html;
	}

	/**
	 * 基本功能：过滤指定标签
	 * 
	 * @param str
	 * @param tag
	 *            指定标签<img />
	 * @return String
	 */
	public static String RemoveHtmlTag(String str, String tag) {
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regxp = "(<\\/?)\\s*(\\" + tag + ").*?(\\/?>)";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {

			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		String html = sb.toString();
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		m_html = p_html.matcher(html);
		html = m_html.replaceAll(""); // 过滤HTML标签
		return html;
	}

	/**
	 * 截取字符串长度
	 * 
	 * @param str
	 * @param width截取长度
	 * @param ellipsis省略符号
	 * @return
	 */
	public static String abbreviate(String str, int width, String ellipsis) {
		if (str == null || "".equals(str)) {
			return "";
		}
		int d = 0; // byte length
		int n = 0; // char length
		for (; n < str.length(); n++) {
			d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
			if (d > width) {
				break;
			}
		}
		if (d > width) {
			n = n - ellipsis.length() / 2;
			return str.substring(0, n > 0 ? n : 0) + ellipsis;
		}
		return str = str.substring(0, n);
	}

	/**
	 * 基本功能：替换标记以正常显示
	 * 
	 * @param input
	 * @return String
	 */
	public static String ReplaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			case '\'':
				filtered.append("&acute;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 基本功能：判断标记是否存在
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
