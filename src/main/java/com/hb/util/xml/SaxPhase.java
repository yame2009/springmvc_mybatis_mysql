package com.hb.util.xml;

import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * 优点：不用事先调入整个文档，占用资源少；SAX解析器代码比DOM解析器代码小，适于Applet，下载。
 * 缺点：不是持久的；事件过后，若没保存数据，那么数据就丢了； 无状态性；
 * 
 * @author hongliang.dinghl SAX文档解析
 */
public class SaxPhase implements XmlDocument {
	private Properties props;

	public SaxPhase() {
		props = new Properties();
	}

	public Properties getProps() {
		return this.props;
	}

	public void parse(String filename) throws Exception {
		// 实例化解析器
		SAXReader handler = new SAXReader();
		// 实例化用于分析的工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 实例化分析类
		SAXParser parser = factory.newSAXParser();
		// 得到xml文件对应的路径
		URL confURL = SAXReader.class.getClassLoader().getResource(filename);
		try {
			parser.parse(confURL.toString(), handler);
			props = handler.getPrpos();
		} finally {
			// 销毁已过期对象
			factory = null;
			parser = null;
			handler = null;
		}

	}

	/*
	 * 提供给外部程序调用的用于返回程序所对应需要的xml文件属性的方法
	 */
	public String getElementValue(String elementName) {
		// elementValue:对应于elementName的节点的属性值
		String elementValue = null;
		elementValue = props.getProperty(elementName);
		return elementValue;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	public void createXml(String fileName) {
		System.out.println("<<" + fileName + ">>");
	}

	public void parserXml(String fileName) {
		SAXParserFactory saxfac = SAXParserFactory.newInstance();
		try {
			SAXParser saxparser = saxfac.newSAXParser();
			InputStream is = new FileInputStream(fileName);
			saxparser.parse(is, new MySAXHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MySAXHandler extends DefaultHandler {
	boolean hasAttribute = false;

	Attributes attributes = null;

	public void startDocument() throws SAXException {
		System.out.println("文档开始打印了");
	}

	public void endDocument() throws SAXException {
		System.out.println("文档打印结束了");
	}

	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		if (qName.equals("employees")) {
			return;
		}

		if (qName.equals("employee")) {
			System.out.println(qName);
		}

		if (attributes.getLength() > 0) {
			this.attributes = attributes;
			this.hasAttribute = true;
		}
	}

	public void endElement(String uri, String localName, String qName)throws SAXException {
		if (hasAttribute && (attributes != null)) {
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.println(attributes.getQName(0)
						+ attributes.getValue(0));
			}
		}
	}

	public void characters(char[] ch, int start, int length)throws SAXException {
		System.out.println(new String(ch, start, length));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SaxPhase sp = new SaxPhase();
		String filename = "testXML.xml";
		try {
			sp.parse(filename);
			Properties props = sp.getProps();
			// System.out.println(props.size()+"");
			Iterator it = props.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				System.out.println(props.get(key) + "");
			}
			// System.out.println(sp.getElementValue("driver-class"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
