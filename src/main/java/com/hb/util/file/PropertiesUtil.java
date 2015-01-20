package com.hb.util.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * java.util.Properties工具类
 *      1、封装了对属性文件的简单读写
 *      2、拓展加入了对List、Map数据格式的支持
 * 
 * 注意：
 *      1、由于在属性值读取过程中，字符分割是采用“特殊字符+向后断言”的方式
 *          所以被分割的key/value结束不允许以“\”作为结尾，当前逻辑是replace掉后缀的“\”符号
 * 
 * 
 * 
 * @author lanfog
 *      2014年05月29日 16时41分23秒
 *
 */
public class PropertiesUtil {

	/**
	 * 写入properties信息
	 * 
	 * @param filePath
	 *            绝对路径（包括文件名和后缀名）
	 * @param parameterName
	 *            名称
	 * @param parameterValue
	 *            值
	 */
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties props = new Properties();
		try {

			// 如果文件不存在，创建一个新的
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("sharedata.properties 文件不存在，创建一个新的!");
				file.createNewFile();
			}

			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			props.load(fis);
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, parameterName);
			fos.close(); // 关闭流
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterName + " value error");
		}
	}

	/**
	 * filename: 相对路径+文件名（不要后缀）
	 */
	public synchronized static String getPropertyFromFile(String filename,
			String key) {
		ResourceBundle rb = ResourceBundle.getBundle(filename);
		return rb.getString(key).trim();
	}

	/**
	 *
	 * @Title: readValue
	 * @Description: TODO 通过绝对路径获取properties文件属性， 根据key读取value
	 * @param filePath
	 *            properties文件绝对路径（包括文件名和后缀）
	 * @param key
	 *            属性key
	 * @return String 返回value
	 */
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();// -----------------------------------important
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	 /**
	  * 单属性读写
	  * @param filePath
	  * @param fileName
	  * @param propertyName
	  * @param propertyValue
	  * @return
	  */
    public static boolean setProperty(String filePath, String fileName, String propertyName, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, propertyValue);
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 清除属性propertyName的值。
     * @param filePath
     * @param fileName
     * @param propertyName
     * @return
     */
    public static boolean cleanProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, "");
            String comment = propertyName;
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除属性propertyName
     * @param filePath
     * @param fileName
     * @param propertyName
     * @return
     */
    public static boolean removeProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.remove(propertyName);
            String comment = propertyName;
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static String getProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            return p.getProperty(propertyName);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    public static String getProperty(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            return p.getProperty(propertyName, defaultValue);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /*
     * 多属性读写
     */
 
    public static boolean setProperty(String filePath, String fileName, Map<String, String> propertyMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(String name:propertyMap.keySet()){
                p.setProperty(name, propertyMap.get(name));
            }
            String comment = "Update '" + propertyMap.keySet().toString() + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 这么随便的代码，却是极好的！
     * 
     * @param filePath
     * @param fileName
     * @param propertyArray
     * @return
     */
    public static boolean setProperty(String filePath, String fileName, String... propertyArray){
        if(propertyArray == null || propertyArray.length%2 != 0){
            throw new IllegalArgumentException("make sure 'propertyArray' argument is 'ket/value' pairs");
        }
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(int i=0;i<propertyArray.length/2;i++){
                p.setProperty(propertyArray[i*2], propertyArray[i*2+1]);
            }
            String comment =  "Update '" + propertyArray[0] + "..." + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 方法返回布尔值，为true时表示成功
     *  详细数据见传入参数propertyMap（此处采用传引用的方式）
     * 
     * @param filePath
     * @param fileName
     * @param propertyMap
     * @return
     */
    public static boolean getProperty(String filePath, String fileName, Map<String, String> propertyMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(String name:propertyMap.keySet()){
                propertyMap.put(name, p.getProperty(name, propertyMap.get(name)));
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /*
     * List属性值读写
     */
     
    public static boolean setProperty(String filePath, String fileName, String propertyName, List<String> propertyValueList){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            if(propertyValueList != null && propertyValueList.size() > 0){
                for(String value:propertyValueList){
                    propertyValue.append(
                            value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                            ";");
                }
            }
            p.setProperty(propertyName, propertyValue.toString());
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, List<String> propertyValueList){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            for(String value:propertyValueList){
                propertyValue.append(
                        value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                        ";");
            }
            p.setProperty(propertyName, p.getProperty(propertyName) + propertyValue.substring(1));
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, p.getProperty(propertyName, "") +
                    propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                    ";");
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static List<String> getPropertyList(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            String v = p.getProperty(propertyName, defaultValue);
            String[] iA = v.split("(?<!\\\\);");
            for(int i=0;i<iA.length;i++){
                iA[i] = iA[i].replaceAll("(\\\\)+$", "").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\");
            }
            return Arrays.asList(iA);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /**
     *  MAP属性值读写
     * @param filePath
     * @param fileName
     * @param propertyName
     * @param propertyValueMap
     * @return
     */
    public static boolean setProperty(String filePath, String fileName, String propertyName, Map<String, String> propertyValueMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            if(propertyValueMap != null && propertyValueMap.size() > 0){
                for(String key:propertyValueMap.keySet()){
                    propertyValue.append(
                            key.replaceAll("\\\\", "\\\\\\\\").replaceAll("(\\\\)+$", "").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") + 
                            "," +
                            propertyValueMap.get(key).replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") +
                            ";");
                }
            }
            p.setProperty(propertyName, propertyValue.toString());
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, Map<String, String> propertyValueMap){
        try{
            Map<String, String> combinePropertyValueMap = getPropertyMap(filePath, fileName, propertyName, "");
            combinePropertyValueMap.putAll(propertyValueMap);
            return setProperty(filePath, fileName, propertyName, combinePropertyValueMap);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, String propertyKey, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, p.getProperty(propertyName, "") +
                    propertyKey.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") + 
                    "," +
                    propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") +
                    ";");
            String comment = "Update '" + propertyName + "." + propertyKey + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 返回Map格式封装的属性值
     *  其中，Map以HashMap创建，若要求排序的话，推荐外部排序
     * 
     * @param filePath
     * @param fileName
     * @param propertyName
     * @param defaultValue
     * @return
     */
    public static Map<String, String> getPropertyMap(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            String v = p.getProperty(propertyName, defaultValue);
             
            Map<String, String> retMap = new HashMap<String, String>();
            String[] iA = v.split("(?<!\\\\);");
            for(String i:iA){
                String[] jA = i.split("(?<!\\\\),");
                if(jA.length == 2){
                    retMap.put(
                            jA[0].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\"), 
                            jA[1].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\"));
                }
            }
            return retMap;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /**
     * 加载属性文件
     * @param parentFilePath
     * @param fileName
     * @return
     */
    public static Properties loadPropertyInstance(String parentFilePath, String fileName){
        try{
            File d = new File(parentFilePath);
            if(!d.exists()){
                d.mkdirs();
            }
            File f = new File(d, fileName);
            if(!f.exists()){
                f.createNewFile();
            }
            return loadPropertyInstance(f);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 加载属性文件
     * @param parentFilePath
     * @param fileName
     * @return
     */
    public static Properties loadPropertyInstance(String fileName){
       return loadPropertyInstance(new File(fileName));
    }
    
    /**
     * 加载属性文件
     * @param parentFilePath
     * @param fileName
     * @return
     */
    public static Properties loadPropertyInstance(File f){
        try{
            if(!f.exists()){
                f.createNewFile();
            }
            Properties p = new Properties();
            InputStream is = new FileInputStream(f);
            p.load(is);
            is.close();
            return p;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 保存属性文件
     * @param filePath
     * @param fileName
     * @param p
     * @param comment
     * @return
     */
    public static boolean storePropertyInstance(String filePath, String fileName, Properties p, String comment){
        try{
            File d = new File(filePath);
            if(!d.exists()){
                d.mkdirs();
            }
            File f = new File(d, fileName);
            if(!f.exists()){
                f.createNewFile();
            }
            return storePropertyInstance(f,p,comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 保存属性文件
     * @param filePath
     * @param fileName
     * @param p
     * @param comment
     * @return
     */
    public static boolean storePropertyInstance(String fileName, Properties p, String comment){
        try{
            File f = new File(fileName);
            return storePropertyInstance(f,p,comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
       
    }
    
    /**
     * 保存属性文件
     * @param filePath
     * @param fileName
     * @param p
     * @param comment
     * @return
     */
    public static boolean storePropertyInstance(File f, Properties p, String comment){
        try{
            OutputStream os = new FileOutputStream(f);
            p.store(os, comment);
            os.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
	

	/**
	 * 本类主要是对config。properties的密码进行修改
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        // TODO Auto-generated method stub
           
                //写文件
        String passwork = "123";
                  //更改src的config包下的config.properties文件中的“userPassword”属性的值
        writeProperties("config/config.properties","userPassword",passwork); //config.properties一定要写完整
          
 
         //从文件中取出userPassword，
         String decStr=getPropertyFromFile("config/config", "userPassword");
        System.out.println("============"+ decStr);
    }
	
	
}
