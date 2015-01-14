package com.hb.util.object2file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.hb.models.StudentInfo;

/**
 * Java将对象保存到文件中/从文件中读取对象.
 *
 */
public class ObjectToFile {

	/**
	 * 1.保存对象到文件中.
	 *   Java语言只能将实现了Serializable接口的类的对象保存到文件中，利用如下方法即可.
	 *   
	 *   参数obj一定要实现Serializable接口，否则会抛出java.io.NotSerializableException异常。
	 *   另外，如果写入的对象是一个容器，例如List、Map，也要保证容器中的每个元素也都是实现 了Serializable接口。
	 *   例如，如果按照如下方法声明一个Hashmap，并调用writeObjectToFile方法就会抛出异常。
	 *   但是如果是Hashmap<String,String>就不会出问题，因为String类已经实现了Serializable接口。
	 *   另外如果是自己创建的类，如果继承的基类没有实现Serializable，
	 *   那么该类需要实现Serializable，否则也无法通过这种方法写入到文件中。
	 * @param obj
	 * @throws IOException 
	 */
	public static void writeObjectToFile(Object obj,File file) throws IOException
    {
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            throw e;
        }
        
    }
	
	/**
	 * 2.从文件中读取对象
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IOException ClassNotFoundException
	 */
	public static Object readObjectFromFile(File file) throws ClassNotFoundException, IOException
    {
        Object temp=null;
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            throw e;
        } catch (ClassNotFoundException e) {
        	throw e;
        }
        return temp;
    }
	
	public static void main(String[] args) {
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
		 
		 File file =new File("d:\\test2.dat");
		 
		 
		 try {
			writeObjectToFile(student,file);
			StudentInfo s2=(StudentInfo) readObjectFromFile(file);
			
			System.out.println(s2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	}
}
