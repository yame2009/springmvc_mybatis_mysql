package com.hb.util.html;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Attribute;

/**
 * HTML parsing proceeds by calling a callback for each and every piece of the
 * HTML do*****ent. This simple callback class simply prints an indented
 * structural listing of the HTML data.
 */
class HTMLParseLister extends HTMLEditorKit.ParserCallback {

	private List<String> imgUrlList = new ArrayList<String>();
	
	private List<String> harfList = new ArrayList<String>();

	private int indentSize = 0;

	public List getImgUrlList() {
		return imgUrlList;
	}
	
	public List getHarfList() {
		return harfList;
	}

	protected void indent() {
		indentSize += 3;
	}

	protected void unIndent() {
		indentSize -= 3;
		if (indentSize < 0)
			indentSize = 0;
	}

	protected void pIndent() {
		for (int i = 0; i < indentSize; i++)
			System.out.print(" ");
	}

	public void handleText(char[] data, int pos) {
		pIndent();
		System.out.println("Text(" + data.length + "  内容："
				+ String.valueOf(data) + " chars)");
	}

	public void handleComment(char[] data, int pos) {
		pIndent();
		System.out.println("Comment(" + data.length + "  内容："
				+ String.valueOf(data) + " chars)");
	}

	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		pIndent();
		String string = t.toString();
		System.out.println("Tag start(<" + string + ">, "
				+ a.getAttributeCount() + " attrs)");
		if (HTML.Tag.IMG.equals(string)) {
			System.out.println("Tag name : " + string + "  att:  "
					+ a.toString());
		} else if (HTML.Tag.LINK.equals(string)) {
			System.out.println("Tag name : " + string + "  att:  "
					+ a.toString());
		} else if (HTML.Tag.A.toString().equals(string)) {
			System.out.println("ATag : " + string + "  att:  " + a.toString());
			forAttrSet(a, HTML.Attribute.CLASS, HTML.Attribute.HREF);
		} else {
			System.out.println("Tag name : " + string + "  att:  "
					+ a.toString());
		}

		indent();
	}

	public void handleEndTag(HTML.Tag t, int pos) {
		unIndent();
		pIndent();
		String string = t.toString();
		System.out.println("Tag end(</" + string + ">)");

	}

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		pIndent();
		String string = t.toString();
		System.out.println("Tag(<" + string + ">, " + a.getAttributeCount()
				+ " attrs)");

		if (HTML.Tag.IMG.toString().equals(string)) {
			System.out
					.println("imgTag : " + string + "  att:  " + a.toString());
			getImgUrlList(a,HTML.Attribute.SRC);

		} else if (HTML.Tag.LINK.toString().equals(string)) {

			System.out.println("linkTag : " + string + "  att:  "
					+ a.toString());

		} else if (HTML.Tag.A.toString().equals(string)) {

			System.out.println("ATag : " + string + "  att:  " + a.toString());
			forAttrSet(a, HTML.Attribute.CLASS, HTML.Attribute.HREF);
		} else {
			System.out.println("SimpleTag name : " + string + "  att:  "
					+ a.toString());

		}
	}

	public void handleError(String errorMsg, int pos) {
		System.out.println("Parsing error: " + errorMsg + " at " + pos);
	}

	private void forAttrSet(MutableAttributeSet set, Attribute attFilter,
			Attribute att) {
		Enumeration<?> atts = set.getAttributeNames();

		String filterStr = String.valueOf(attFilter);

		boolean isFilter = !(filterStr == null || "".equals(filterStr) || "null"
				.equalsIgnoreCase(filterStr));

		HashMap<String, String> tempMap = new HashMap<String, String>();
		while (atts.hasMoreElements()) {
			Object nextElement = atts.nextElement();
			String obj = String.valueOf(nextElement);
			Object attribute = set.getAttribute(nextElement);
			System.out.println("attHash : " + obj + "  value:" + attribute);

			if (isFilter) {
				tempMap.put(obj, attribute.toString());
			} else {
				harfList.add(attribute.toString());
			}

		}

		if (isFilter) {
			String hrefAttr = att.toString();
			String classAttr = tempMap.get(filterStr);
			if ("img".equalsIgnoreCase(classAttr)) {
				String e = tempMap.get(hrefAttr);
				if(!e.endsWith(".jpg"))
				{
					harfList.add(e);
				}
				
			}
		}

		System.out.println("img array length :" + harfList.size());

	}

	private void getImgUrlList(MutableAttributeSet set,
			Attribute att) {
		Enumeration<?> atts = set.getAttributeNames();

		HashMap<String, String> tempMap = new HashMap<String, String>();
		while (atts.hasMoreElements()) {
			Object nextElement = atts.nextElement();
			String obj = String.valueOf(nextElement);
			Object attribute = set.getAttribute(nextElement);
			System.out.println("attHash : " + obj + "  value:" + attribute);
			imgUrlList.add(attribute.toString());
		}


		System.out.println("img array length :" + imgUrlList.size());

	}
	
	public void clear() {
		imgUrlList.clear();
		harfList.clear();
	}

}