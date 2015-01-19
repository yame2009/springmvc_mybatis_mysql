package com.hb.util.json.fastjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.TypeReference;

public class FastJsonTools {

	private FastJsonTools() {
	}

	public static String toJson(Object object) {
		String jsonString = JSON.toJSONString(object);
		return jsonString;
	}

	public static <T> T fromJson(String jsonString, Class<T> cls) {
		T t = JSON.parseObject(jsonString, cls);
		return t;
	}

	public static <T> List<T> fromJsonList(String jsonString, Class<T> cls) {
		List<T> list = null;
		list = JSON.parseArray(jsonString, cls);
		return list;
	}

	public static Map<String, Object> fromJsonMap(String jsonString) {
		Map<String, Object> list2 = JSON.parseObject(jsonString,
				new TypeReference<Map<String, Object>>() {
				});
		return list2;
	}

	public static List<Map<String, Object>> fromJsonListMap(String jsonString) {

		List<Map<String, Object>> list2 = JSON.parseObject(jsonString,
				new TypeReference<List<Map<String, Object>>>() {
				});
		return list2;
	}

	/**
	 *  读文件，返回字符串
	 * @param path 文件路径
	 * @return
	 * @throws IOException 
	 */
	public static String readFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				laststr = laststr + tempString;
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;

	}
	
	/**
	 *  读取超大文件，返回json字符串
	 * @param <T>
	 * @param path 文件路径
	 * @return
	 * @throws IOException 
	 */
	public static <T> String readHugeFile(String path,Class<T> cls) throws IOException {
		JSONReader reader = new JSONReader(new FileReader(path));
		  reader.startObject();
		  while(reader.hasNext()) {
		        reader.readObject(cls);
		  }
		  reader.endObject();
		  reader.close();
		  return reader.toString();
	}

	/**
	 *  把json格式的字符串写到文件
	 * @param filePath 文件路径
	 * @param jsonStr		字符串
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String jsonStr) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fw);
		out.write(jsonStr);
		out.println();
		fw.close();
		out.close();
	}
	
	/**
	 * 超大json字符串写入文件
	 * @param filePath
	 * @param jsonStr
	 * @throws IOException
	 */
	public static void writeHugeFile(String filePath, String jsonStr) throws IOException
	{
		JSONWriter writer = new JSONWriter(new FileWriter(filePath));
		writer.startObject();
		writer.writeObject(jsonStr);
		writer.endObject();
		writer.close();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Person person1 = new Person("张三1", 24, "北京1");
		// Person person2 = new Person("张三2", 23, "北京2");
		// List<Person> list = new ArrayList<Person>();
		// list.add(person1);
		// list.add(person2);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "jack");
		map.put("age", 23);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "rose");
		map2.put("age", 24);
		list.add(map);
		list.add(map2);
		String jsonString = JSON.toJSONString(list);
		System.out.println(jsonString);
		// JSON.parseArray(arg0, arg1)
		List<Map<String, Object>> list2 = JSON.parseObject(jsonString,
				new TypeReference<List<Map<String, Object>>>() {
				});
		// List<Person> lists = JSON.parseArray(arg0, arg1);
		System.out.println(list2.toString());
	}
}