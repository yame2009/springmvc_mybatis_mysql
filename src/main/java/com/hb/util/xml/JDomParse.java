package com.hb.util.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * JDOM 生成与解析XML文档 ,需要引入JDOM包
 * 
 * @author 338342
 *
 */
public class JDomParse implements XmlDocument {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	public void createXml(String fileName) {
		Document document;
		Element root;
		root = new Element("employees");
		document = new Document(root);
		Element employee = new Element("employee");
		root.addContent(employee);
		Element name = new Element("name");
		name.setText("ddvip");
		employee.addContent(name);
		Element sex = new Element("sex");
		sex.setText("m");
		employee.addContent(sex);
		Element age = new Element("age");
		age.setText("23");
		employee.addContent(age);
		XMLOutputter XMLOut = new XMLOutputter();
		try {
			XMLOut.output(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void parserXml(String fileName) {
		SAXBuilder builder = new SAXBuilder(false);
		try {
			Document document = builder.build(fileName);
			Element employees = document.getRootElement();
			List employeeList = employees.getChildren("employee");
			for (int i = 0; i < employeeList.size(); i++) {
				Element employee = (Element) employeeList.get(i);
				List employeeInfo = employee.getChildren();
				for (int j = 0; j < employeeInfo.size(); j++) {
					System.out.println(((Element) employeeInfo.get(j))
							.getName()
							+ ":"
							+ ((Element) employeeInfo.get(j)).getValue());
				}
			}
		} catch (JDOMException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws JDOMException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException, JDOMException {
		String xmlpath = "c:/catalog.xml";
		// 使用JDOM首先要指定使用什么解析器
		SAXBuilder builder = new SAXBuilder(false);// 这表示使用的是默认的解析器
		// 得到Document，以后要进行的所有操作都是对这个Document操作的
		Document doc = builder.build(xmlpath);
		// 得到根元素
		Element root = doc.getRootElement();
		// 得到元素（节点）的集合
		List bookslist = root.getChildren("books");
		// 轮循List集合
		for (Iterator iter = bookslist.iterator(); iter.hasNext();) {
			Element books = (Element) iter.next();
			List bookList = books.getChildren("book");
			for (Iterator it = bookList.iterator(); it.hasNext();) {
				Element book = (Element) it.next();
				// 取得元素的属性
				String level = book.getAttributeValue("level");
				System.out.println(level);
				// 取得元素的子元素（为最低层元素）的值 注意的是，必须确定book元素的名为“name”的子元素只有一个。
				String author = book.getChildTextTrim("author");
				System.out.println(author);
				// 改变元素（为最低层元素）的值;对Document的修改，并没有在实际的XML文档中进行修改
				book.getChild("author").setText("author_test");
			}

		}
		// 保存Document的修改到XML文件中
		// XMLOutputter outputter=new XMLOutputter();
		// outputter.output(doc,new FileOutputStream(xmlpath));

	}

}
