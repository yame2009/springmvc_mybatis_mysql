package com.hb.util.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/** 
 *  具有性能优异、功能强大和极端易用使用的特点 
 *  开源 
 *  
 * @author hongliang.dinghl Dom4j 生成XML文档与解析XML文档 
 */ 
public class Dom4JPhase implements XmlDocument{
	
	@Override  
    public void init() {  
        // TODO Auto-generated method stub  
          
    }  
      
    public void createXml(String fileName) {  
        Document document = DocumentHelper.createDocument();  
        Element employees = document.addElement("employees");  
        Element employee = employees.addElement("employee");  
        Element name = employee.addElement("name");  
        name.setText("ddvip");  
        Element sex = employee.addElement("sex");  
        sex.setText("m");  
        Element age = employee.addElement("age");  
        age.setText("29");  
        try {  
            Writer fileWriter = new FileWriter(fileName);  
            XMLWriter xmlWriter = new XMLWriter(fileWriter);  
            xmlWriter.write(document);  
            xmlWriter.close();  
        } catch (IOException e) {  
  
            System.out.println(e.getMessage());  
        }  
  
    }  
  
    public void parserXml(String fileName) {  
        File inputXml = new File(fileName);  
        SAXReader saxReader = new SAXReader();  
        try {  
            Document document = saxReader.read(inputXml);  
            Element employees = document.getRootElement();  
            for (Iterator i = employees.elementIterator(); i.hasNext();) {  
                Element employee = (Element) i.next();  
                for (Iterator j = employee.elementIterator(); j.hasNext();) {  
                    Element node = (Element) j.next();  
                    System.out.println(node.getName() + ":" + node.getText());  
                }  
  
            }  
        } catch (DocumentException e) {  
            System.out.println(e.getMessage());  
        }  
        System.out.println("dom4j parserXml");  
    }  
	
	/**
	 * 产生xml文档
	 */
	public void generateDocument(){ 
		  //使用 DocumentHelper 类创建一个文档实例。 DocumentHelper 是生成 XML 文档节点的 dom4j API 工厂类   
		   Document document = DocumentHelper.createDocument();   		      
		   //使用 addElement()方法创建根元素catalog , addElement()用于向 XML 文档中增加元素   
		   Element catalogElement = document.addElement("catalog");
		   //在 catalog 元素中使用 addComment() 方法添加注释"An XML catalog"   
		   catalogElement.addComment("An XML Catalog"); 
		   //在 catalog 元素中使用 addProcessingInstruction() 方法增加一个处理指令 (不知有什么用)  
//		   catalogElement.addProcessingInstruction("target","text");
		   //在 catalog 元素中使用 addElement() 方法增加 journal 节点   
		   Element booksElement = catalogElement.addElement("books");   
		   //使用 addAttribute() 方法向 book节点添加 title 和 publisher 属性   
		   booksElement.addAttribute("title", "XML Zone");   
		   booksElement.addAttribute("publisher", "IBM developerWorks");		   
		   //添加子节点
		   Element bookElement=booksElement.addElement("book");   
		   //添加属性   
		   bookElement.addAttribute("level", "Intermediate");   
		   bookElement.addAttribute("date", "December-2001"); 
		   //添加节点
		   Element titleElement=bookElement.addElement("title");  
		   //添加内容
		   titleElement.setText("Java OO Book");  
		   //添加节点
		   Element authorElement=bookElement.addElement("author");  
		   //添加节点
		   authorElement.setText("author");		      		      
		   //可以使用 addDocType() 方法添加文档类型说明   
		   //这样就向 XML 文档中增加文档类型说明：   
//		   document.addDocType("catalog","nikee","file://c:/Dtds/catalog.dtd");   
		   try{    
			   FileOutputStream fos=new FileOutputStream("c:/catalog.xml");   
		       OutputFormat of=new OutputFormat("    ", true);   
		       XMLWriter xw=new XMLWriter(fos, of);   
		       xw.write( document );   
		       xw.close();   
		   } catch(IOException e)    {   
		    System.out.println(e.getMessage());   
		   }   
	  }   
	  
	/**
	 * 读取xml文件的元素值
	 * @param list
	 */
	public void getXMLValue(List list){
//		List list=element.elements();
		if(list==null||list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			 Element foo = (Element) list.get(i);
			 System.out.println(foo.getName()+"="+foo.getData().toString().trim());
			 List result=foo.elements();	
			 if(result!=null||result.size()>0){
				 this.getXMLValue(result);//递归
			 }
		}		
	}
	
	/**
	 * 根据节点名获取值
	 * @param fileName
	 * @param name
	 */
	public void getElement(String fileName,String name){
		Document document = this.readXML(fileName);
		Element root=this.getRootElement(document);
		// 枚举名称为name的节点
		for ( Iterator i = root.elementIterator(name); i.hasNext();) {
		    Element foo = (Element) i.next();
		    System.out.println(foo.getName()+"="+foo.getData());
		}
		// 枚举所有子节点
//	    for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
//	       Element foo = (Element) i.next();
//	       System.out.println(foo.getName()+"="+foo.getData());
//	    }
		
	}
	
	/**
	 * 根据节点名获取值
	 * @param fileName
	 * @param name
	 */
	public void getAttribute(Element element,String name){
		// 枚举属性
	    for ( Iterator i = element.attributeIterator(); i.hasNext(); ) {
	       Attribute attribute = (Attribute) i.next();
	       System.out.println(attribute.getName()+"="+attribute.getData());
	    }  
	}
	
	/**
	 * 得到root节点
	 * @param doc
	 * @return
	 */
	public Element getRootElement(Document doc){
	       return doc.getRootElement();
	}
	
	/**
	 * 读取xml文件
	 * @param fileName
	 * @return 返回document对象
	 */
	public Document readXML(String fileName){
		 SAXReader saxReader = new SAXReader();
		 Document document=null;
		  try {
			   document = saxReader.read(new File(fileName));
		  }catch(Exception e){
			  System.out.println("readXML has error:"+e.getMessage());
			  e.printStackTrace();
		  }
		  return document;
	}
	
	
	
	 /**
	  * 遍历整个XML文件，获取所有节点的值与其属性的值，并放入HashMap中
	  * @param 待遍历的XML文件
	  */
	 public void iterateWholeXML(String filename){	 
		  Document document = this.readXML(filename);
		  Element root=this.getRootElement(document);
		  List list=root.elements();
		  this.getXMLValue(list);	 
	 }
	 
	  public static void main(String[] argv){   
		  Dom4JPhase dom4j=new Dom4JPhase();   
		  dom4j.generateDocument();
		  String fileName="c:/catalog.xml";
		  String elementName="book";
//		  dom4j.generateDocument();
		  long lasting = System.currentTimeMillis();
		  dom4j.iterateWholeXML(fileName);
//		  dom4j.getElement(fileName, elementName);		  
		  System.out.println("运行时间：" + (System.currentTimeMillis() - lasting) + " 毫秒");		  
	  }  
}
