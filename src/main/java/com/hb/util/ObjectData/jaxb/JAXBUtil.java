package com.hb.util.ObjectData.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import com.hb.models.StudentInfo;
import com.hb.util.commonUtil.StringUtil;

/**
 * <pre>
 *  marshal对象和unmarshal对象都是由JAXBContext创建.所以一开始需要初始化JAXBContext.
 *	JAXB: 参考资料<a>http://my.oschina.net/zhaoqian/blog/89763</a>,<a>http://my.oschina.net/zhaoqian/blog/179334</a>
 *		1.输出一个XML文件进行交互.
 *		2.读取一个XML文件并输出对象.
 * </pre>
 */
public class JAXBUtil {
	
	/**
     * 生成xml文件的二进制数据
     * @param obj 对象
     */
    public static byte[] marshal(Object obj) throws JAXBException {
 
        JAXBContext context = JAXBCache.instance().getJAXBContext(obj.getClass());
        Marshaller m = context.createMarshaller();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(obj, outputStream);
        byte[] result = outputStream.toByteArray();
        return result;
    }
    
	/**
     * 生成xml文件的二进制数据,"D:\\HelloWorld.xml"
     * @param obj 对象
     */
    public static File marshal(Object obj,String filePath) throws JAXBException {
    	if(StringUtil.isEmpty(filePath))
    	{
    		return null;
    	}
    	
    	File file= new File(filePath);
    	
    	if(file == null)
    	{
    		return null;
    	}
    	
    	 //初始化JAXBContext.JAXBContext类提供的JAXB API的客户端的入口点。
        //它提供一个抽象的用于管理XML / Java绑定的必要信息，以实现JAXB绑定框架行动：解组，编组和验证。
        JAXBContext context = JAXBCache.instance().getJAXBContext(obj.getClass());
        
        //将Java对象Marshal成XML内容的Marshal的初始化设置.
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.marshal(obj, file); //输出到文件
        jaxbMarshaller.marshal(obj, System.out);//控制台输出
        
        return file;
    }
 
    /**
     * @param data xml stream
     * @param classe 类
     * @return jaxb生成xml的java 类对象
     */
    public static Object unmarshal(byte[] data, Class<?> classe) throws JAXBException {
 
        JAXBContext context = JAXBCache.instance().getJAXBContext(classe);
        Unmarshaller m = context.createUnmarshaller();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Object obj = m.unmarshal(inputStream);
        return obj;
    }
    
    /**
     * @param data xml stream
     * @param classe 类
     * @return jaxb生成xml的java 类对象
     */
    public static Object unmarshal(File file,Class<?> classe) throws JAXBException {
 
        JAXBContext context = JAXBCache.instance().getJAXBContext(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        
        return  unmarshaller.unmarshal(file);
    }
 
    /**
     * @param data xml stream
     * @param classe 类
     * @return jaxb生成xml的java 类对象
     */
    public static Object unmarshal(InputStream in, Class<?> classe) throws JAXBException, IOException {
 
        JAXBContext context = JAXBCache.instance().getJAXBContext(classe);
        byte[] data = IOUtils.toByteArray(in);
        Unmarshaller m = context.createUnmarshaller();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Object obj = m.unmarshal(inputStream);
        return obj;
    }
 
	 public static void main(String[] args) {
		 java2XmlTest();
		 
		 xml2JavaTest();
	 }

	private static void java2XmlTest() {
		StudentInfo student=new StudentInfo();
		 student.setId(1);
		 student.setName("Hello World!");
		 student.setAge(26);
		 student.setAddress("深圳市福田区新洲街湖北大厦");
		 student.setBirthday(new Date());
		 student.setCreateTime(new Date());
		 student.setEmail("sss@126.com");
		 student.setPassword("123465");
		 student.setSex(1);
	         
	        try {
//	        	 File file=new File("D:\\HelloWorld.xml");
//		            //初始化JAXBContext.JAXBContext类提供的JAXB API的客户端的入口点。
//		            //它提供一个抽象的用于管理XML / Java绑定的必要信息，以实现JAXB绑定框架行动：解组，编组和验证。
//		            JAXBContext jc=JAXBContext.newInstance(StudentInfo.class);
//		            //将Java对象Marshal成XML内容的Marshal的初始化设置.
//		            Marshaller jaxbMarshaller=jc.createMarshaller();
//		            //output
//		            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		            jaxbMarshaller.marshal(student, file);
//		            jaxbMarshaller.marshal(student, System.out);//控制台输出
	            File marshalFile = marshal(student,"D:\\HelloWorld.xml");
	            if(marshalFile != null)
	            {
	            	System.out.println("marshalFile path : "+marshalFile);
	            }
	             
	        } catch (JAXBException e) {
	            System.out.println("output xml error!");
	            e.printStackTrace();
	        }
	}
	
	private static void xml2JavaTest() {
		  File file=new File("D:\\HelloWorld.xml");
	        try {
	            //反着来
//	            JAXBContext jc=JAXBContext.newInstance(StudentInfo.class);
//	            Unmarshaller unmarshaller=jc.createUnmarshaller();
//	            StudentInfo cus=(StudentInfo) unmarshaller.unmarshal(file);
	        	 StudentInfo cus=(StudentInfo)  unmarshal(file,StudentInfo.class);
	            System.out.println("data:"+cus);
	            System.out.println("data:"+cus.getId());
	            System.out.println("data:"+cus.getName());
	            System.out.println("data:"+cus.getAge());
	        } catch (JAXBException e) {
	            System.out.println("input xml error!");
	            e.printStackTrace();
	        }
	}
	     
	    
}
