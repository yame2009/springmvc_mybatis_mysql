package com.hb.util.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.SAXReader;

/**
 * 基于DOM4J开源包实现xml解析
 * @author 338342
 *
 */
public class XMLUtil {
	/** ************解析xml 元素时需要的变量 **************** */

	private static final Log log = LogFactory.getLog(XMLUtil.class);
	private static SAXReader saxReader = new SAXReader();

	private XMLUtil() {
	}

	/**
	 * 将xml元素输出到控制台
	 * 
	 * @param xml
	 */
	public static void dump(Node xml) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		XMLWriter writer;
		try {
			writer = new XMLWriter(System.out, format);
			writer.write(xml);
			writer.flush();
			// do not call writer.close(); !
		} catch (Throwable t) {
			// otherwise, just dump it
			System.out.println(xml.asXML());
		}
	}

	/**
	 * 将document 对象写入指定的文件
	 * 
	 * @param xml
	 * @param fileName
	 */
	public static void dumpToFile(Node xml, String fileName) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(fileName), format);
			writer.write(xml);
			writer.close();
		} catch (IOException e) {
			log.error("将document 对象写入指定的文件时出现IO错误 !", e);
		}
	}

//	/**
//	 * 获得Xml 文档对象
//	 * 
//	 * @param xmlFile
//	 *            指向xml 文件的引用
//	 * @return xmlDoc 从文件读取xml Document
//	 */
//	public static Document read(File xmlFile) {
//		Document document = null;
//		try {
//			document = saxReader.read(xmlFile);
//		} catch (DocumentException e) {
//			log.error("通过指向xml文件的文件获得Document对象时出错 !", e);
//		}
//		return document;
//	}

	/**
	 * 通过xml 文件的名字读取Document对象
	 * 
	 * @param xmlFileName
	 * @return Document
	 */
	public static Document read(String xmlFileName) {
		return read(new File(xmlFileName));
	}

//	/**
//	 * 通过指向xml 文件的URL获得Document对象
//	 * 
//	 * @param url
//	 * @return Document
//	 */
//	public static Document read(URL url) {
//		Document document = null;
//		try {
//			document = saxReader.read(url);
//		} catch (DocumentException e) {
//			log.error("通过指向xml文件的URL获得Document对象时出错...", e);
//		}
//		return document;
//	}

	/**
	 * 将xml Node 对象转换成 String
	 * 
	 * @param node
	 * @return string
	 */
	public static String NodeToString(Node node) {
		return node.asXML();
	}

	/**
	 * 将字符串解析成Node
	 * 
	 * @param xmlString
	 * @return node
	 */
	public static Node StringToNode(String xmlString) {
		Node node = null;
		try {
			node = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			log.error("将字符串解析成doc时出错", e);
		}
		return node;
	}

	/**
	 * 根据给定路径查询给定 xml 元素的子元素
	 * 
	 * @param parent
	 *            父节点元素
	 * @param childPath
	 *            相对与父节点的子节点路径,路径组成部分之间用"/"分割,支持通配符号"*"
	 * @return child 子节点元素
	 */
	public static Element child(Element parent, String childPath) {
		String names[] = childPath.split("/");
		Element child = parent;
		for (String name : names) {
			if (name.equals("*")) {
				child = (Element) child.elements().get(0);
			} else {
				child = child.element(name);
			}
			if (child == null) {
				log.debug("未找到指定元素[" + name + "],返回null...");
			}
		}
		return child;
	}

	/**
	 * 根据给定路径查询给定 xml 元素的子元素
	 * 
	 * @param parent
	 *            父节点元素
	 * @param childPath
	 *            相对与父节点的子节点路径,路径组成部分之间用"/"分割,支持通配符号"*"
	 * @param index
	 *            子节点在兄弟列表中的位置
	 * @return child 子节点元素
	 */
	public static Element child(Element parent, String childPath, int index) {
		String names[] = childPath.split("/");
		Element child = parent;
		for (String name : names) {
			if (name.equals("*")) {
				child = (Element) child.elements().get(index);
			} else {
				child = child.element(name);
			}
			if (child == null) {
				log.debug("未找到指定元素[" + name + "],返回null...");
			}
		}
		return child;
	}

	/**
	 * 查询父节点的子节点的属性值
	 * 
	 * @param parent
	 *            父节点
	 * @param attributePath
	 *            子节点属性相对于父节点的路径,路径各组成部分用"/"分割, 属性名称前要添加"@"符号 支持通配符号"*"
	 * @return 子节点的属性值,如果找不到返回null
	 */
	public static String childAttribute(Element parent, String attributePath) {
		if (attributePath.indexOf('@') == -1)
			throw new IllegalArgumentException("属性查询要使用 '@'");
		int slashLoc = attributePath.lastIndexOf('/');
		String childPath = attributePath.substring(0, slashLoc);
		Element child = child(parent, childPath);
		String attributeName = attributePath.substring(slashLoc + 2);
		String attributeValue = child.attributeValue(attributeName);
		if (null == attributeValue) {
			log.debug("未找到指定属性[" + attributeName + "],返回null...");
		}
		return attributeValue;
	}

	/**
	 * 根据相对于父节点的子节点路径,查询子节点列表
	 * 
	 * @param parent
	 *            父节点
	 * @param childrenPath
	 *            子节点路径
	 * @return children 子节点列表
	 */
	public static List children(Element parent, String childrenPath) {
		int slashLoc = childrenPath.lastIndexOf('/');
		Element child = child(parent, childrenPath.substring(0, slashLoc));
		if (child == null)
			return Collections.EMPTY_LIST;
		String childrenName = childrenPath.substring(slashLoc + 1);
		List children;
		if (childrenName.equals("*")) {
			children = child.elements();
		} else {
			children = child.elements(childrenName);
		}
		return children;
	}

	/**
	 * 创建一个xml 元素
	 * 
	 * @param name
	 *            xml 元素的名称
	 * @param attributes
	 *            元素属性
	 * @param ns
	 *            命名空间
	 * @return
	 */
	public static Element createElement(String name,
			Map<String, String> attributes, Namespace ns) {
		Element element = null;
		if (ns == null) {
			element = DocumentHelper.createElement(name);
		} else {
			element = DocumentHelper.createElement(new QName(name, ns));
		}
		for (String key : attributes.keySet()) {
			element.addAttribute(key, attributes.get(key));
		}
		return element;
	}

	/**
	 * 创建xml 文档
	 * 
	 * @param nsArray
	 *            命名空间数组
	 * @param root
	 * @return
	 */
	public static Document createDocument(Namespace[] nsArray, Element root) {
		Document document = DocumentHelper.createDocument();
		if (root != null) {
			document.add(root);
		}
		if (nsArray != null && nsArray.length > 0) {
			for (Namespace ns : nsArray) {
				document.add(ns);
			}
		}
		return document;
	}
	
	/**
     * 获取根节点
     * 
     * @param doc
     * @return
     */
    public static Element getRootElement(Document doc) {
        if (doc == null) {
            return null;
        }
        return doc.getRootElement();
    }
 
    /**
     * 获取节点eleName下的文本值，若eleName不存在则返回默认值defaultValue
     * 
     * @param eleName
     * @param defaultValue
     * @return
     */
    public static String getElementValue(Element eleName, String defaultValue) {
        if (eleName == null) {
            return defaultValue == null ? "" : defaultValue;
        } else {
            return eleName.getTextTrim();
        }
    }
 
    public static String getElementValue(String eleName, Element parentElement) {
        if (parentElement == null) {
            return null;
        } else {
            Element element = parentElement.element(eleName);
            if (element != null) {
                return element.getTextTrim();
            } else {
                try {
                    throw new Exception("找不到节点" + eleName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
 
    /**
     * 获取节点eleName下的文本值
     * 
     * @param eleName
     * @return
     */
    public static String getElementValue(Element eleName) {
        return getElementValue(eleName, null);
    }
 
     /**
	 * 获得Xml 文档对象
	 * 
	 * @param xmlFile
	 *            指向xml 文件的引用
	 * @return xmlDoc 从文件读取xml Document
	 */
    public static Document read(File file) {
        return read(file, null);
    }
 
    public static Document findCDATA(Document body, String path) {
        return stringToXml(getElementValue(path,body.getRootElement()));
    }
 
    /**
     * 
     * @param file
     * @param charset
     * @return
     * @throws DocumentException
     */
    public static Document read(File file, String charset) {
        if (file == null) {
            return null;
        }
        SAXReader reader = new SAXReader();
        if (charset != null) {
            reader.setEncoding(charset);
        }
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
 
    /**
	 * 通过指向xml 文件的URL获得Document对象
	 * 
	 * @param url
	 * @return Document
	 */
    public static Document read(URL url) {
        return read(url, null);
    }
 
    /**
     * 
     * @param url
     * @param charset
     * @return
     * @throws DocumentException
     */
    public static Document read(URL url, String charset) {
        if (url == null) {
            return null;
        }
        SAXReader reader = new SAXReader();
        if (charset != null) {
            reader.setEncoding(charset);
        }
        Document document = null;
        try {
            document = reader.read(url);
        } catch (DocumentException e) {
        	log.error("通过指向xml文件的文件获得Document对象时出错 !", e);
//            e.printStackTrace();
        }
        return document;
    }
 
    /**
     * 将文档树转换成字符串
     * 
     * @param doc
     * @return
     */
    public static String xmltoString(Document doc) {
        return xmltoString(doc, null);
    }
 
    /**
     * 
     * @param doc
     * @param charset
     * @return
     * @throws IOException
     */
    public static String xmltoString(Document doc, String charset) {
        if (doc == null) {
            return "";
        }
        if (charset == null) {
            return doc.asXML();
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter strWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(strWriter, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strWriter.toString();
    }
 
    /**
     * 持久化Document
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, File file, String charset)
            throws Exception {
        if (doc == null) {
            throw new NullPointerException("doc cant not null");
        }
        if (charset == null) {
            throw new NullPointerException("charset cant not null");
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        FileOutputStream os = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(os, charset);
        XMLWriter xmlWriter = new XMLWriter(osw, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
            if (osw != null) {
                osw.close();
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, String filePath, String charset)
            throws Exception {
        xmltoFile(doc, new File(filePath), charset);
    }
 
     
    /**
     * 
     * @param doc
     * @param filePath
     * @param charset
     * @throws Exception
     */
    public static void writDocumentToFile(Document doc, String filePath, String charset)
            throws Exception {
        xmltoFile(doc, new File(filePath), charset);
    }
     
    public static Document stringToXml(String text) {
        try {
            return DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    public static Document createDocument() {
        return DocumentHelper.createDocument();
    }
     
	
}
