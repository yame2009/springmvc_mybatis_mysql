package com.hb.util.xml;

public interface XmlDocument {
	
	public void init();
	
	/**
	 * 创建xml文档
	 * @param fileName
	 */
	 public void createXml(String fileName);
	 
	 /**
	  * 解析xml文档
	  * @param fileName
	  */
	 public void parserXml(String fileName);
	 

}
