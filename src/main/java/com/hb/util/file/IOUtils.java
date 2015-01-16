package com.hb.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO,File，读写工具类
 * 
 * @author 338342
 *
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

	private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

	public static final String UTF_8 = "UTF-8";
	public static final Charset CHARSET_UTF8 = Charset.forName(UTF_8);

	public static String fileToString(String filePath) throws IOException {
		File file = new File(filePath);

		return fileToString(file,UTF_8);
	}

	public static String fileToString(File file,String encoding) throws IOException {

		InputStream in = new FileInputStream(file);
		try {
			return org.apache.commons.io.IOUtils.toString(in,encoding);
		} finally {
			org.apache.commons.io.IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 数据保存到文件
	 * 
	 * @param file 
	 *            File文件,获取路径"c:\\test.json"
	 * @param data
	 *            数据
	 * @return
	 * @throws IOException
	 */
	public static void StringToFile(File file,String data) throws IOException {
		try {
			 StringToFile(file.toURI().toString(), data);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 数据保存到文件
	 * 
	 * @param filePath
	 *            文件路径 ，"c:\\test.json"
	 * @param data
	 *            数据
	 * @return
	 * @throws IOException
	 */
	public static void StringToFile(String filePath, String data)
			throws IOException {
		FileWriter file = null;
		try {
			file = new FileWriter(filePath);
			file.write(data);

		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (file != null) {
				file.flush();
				file.close();
			}
		}
		
	}

}
