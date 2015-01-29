package com.hb.util.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO,File，读写工具类
 * 
 * @author 338342
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	public static final String UTF_8 = "UTF-8";
	public static final Charset CHARSET_UTF8 = Charset.forName(UTF_8);

	/** 文件分隔符 --window系统中是"\"，（在 UNIX 系统中是“/”） */
	public static final String FOLDER_SEPARATOR = System.getProperty("file.separator");
	
	/** 路径分隔符（在 UNIX 系统中是“:”） */
	public static final String PATH_SEPARATOR = System.getProperty("path.separator");

	/**
	 * 换行分割符 windows下的文本文件换行符:\r\n .
	 *  linux/unix下的文本文件换行符:\r Mac下的文本文件换行符:\n
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * Java 类路径
	 */
	public static final String JAVA_CLASS_PATH = System.getProperty("java.class.path");

	public static final char EXTENSION_SEPARATOR = '.';

	public static String fileToString(String filePath) throws IOException {
		File file = new File(filePath);

		return fileToString(file, UTF_8);
	}

	public static String fileToString(File file, String encoding)
			throws IOException {

		InputStream in = new FileInputStream(file);
		try {
			return org.apache.commons.io.IOUtils.toString(in, encoding);
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
	public static void StringToFile(File file, String data) throws IOException {
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
			file.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (file != null) {
				file.close();
			}
		}

	}

	/**
	 * 功能：复制文件或者文件夹。
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param inputFile
	 *            源文件
	 * @param outputFile
	 *            目的文件
	 * @param isOverWrite
	 *            是否覆盖(只针对文件)
	 * @throws IOException
	 */
	public static void copy(File inputFile, File outputFile, boolean isOverWrite)
			throws IOException {
		if (!inputFile.exists()) {
			throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
		}
		copyPri(inputFile, outputFile, isOverWrite);
	}

	/**
	 * 功能：为copy 做递归使用。
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param inputFile
	 * @param outputFile
	 * @param isOverWrite
	 * @throws IOException
	 */
	private static void copyPri(File inputFile, File outputFile,
			boolean isOverWrite) throws IOException {
		// 是个文件。
		if (inputFile.isFile()) {
			copySimpleFile(inputFile, outputFile, isOverWrite);
		} else {
			// 文件夹
			if (!outputFile.exists()) {
				outputFile.mkdir();
			}
			// 循环子文件夹
			for (File child : inputFile.listFiles()) {
				copy(child,
						new File(outputFile.getPath() + "/" + child.getName()),
						isOverWrite);
			}
		}
	}

	/**
	 * 功能：copy单个文件
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param inputFile
	 *            源文件
	 * @param outputFile
	 *            目标文件
	 * @param isOverWrite
	 *            是否允许覆盖
	 * @throws IOException
	 */
	private static void copySimpleFile(File inputFile, File outputFile,
			boolean isOverWrite) throws IOException {
		// 目标文件已经存在
		if (outputFile.exists()) {
			if (isOverWrite) {
				if (!outputFile.delete()) {
					throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
				}
			} else {
				// 不允许覆盖
				return;
			}
		}
		InputStream in = new FileInputStream(inputFile);
		OutputStream out = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}

	/**
	 * 功能：删除文件
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param file
	 *            文件
	 */
	public static void delete(File file) {
		deleteFile(file);
	}

	/**
	 * 功能：删除文件，内部递归使用
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param file
	 *            文件
	 * @return boolean true 删除成功，false 删除失败。
	 */
	private static void deleteFile(File file) {
		if (file == null || !file.exists()) {
			return;
		}
		// 单文件
		if (!file.isDirectory()) {
			boolean delFlag = file.delete();
			if (!delFlag) {
				throw new RuntimeException(file.getPath() + "删除失败！");
			} else {
				return;
			}
		}
		// 删除子目录
		for (File child : file.listFiles()) {
			deleteFile(child);
		}
		// 删除自己
		file.delete();
	}

	/**
	 * 从文件路径中抽取文件的扩展名, 例如. "mypath/myfile.txt" -> "txt". * @author 宋立君
	 * 
	 * @date 2014年06月24日
	 * @param 文件路径
	 * @return 如果path为null，直接返回null。
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}
		return path.substring(extIndex + 1);
	}

	/**
	 * 从文件路径中抽取文件名, 例如： "mypath/myfile.txt" -> "myfile.txt"。 * @author 宋立君
	 * 
	 * @date 2014年06月24日
	 * @param path
	 *            文件路径。
	 * @return 抽取出来的文件名, 如果path为null，直接返回null。
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
				: path);
	}

	/**
	 * 功能：保存文件。
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param content
	 *            字节
	 * @param file
	 *            保存到的文件
	 * @throws IOException
	 */
	public static void save(byte[] content, File file) throws IOException {
		if (file == null) {
			throw new RuntimeException("保存文件不能为空");
		}
		if (content == null) {
			throw new RuntimeException("文件流不能为空");
		}
		InputStream is = new ByteArrayInputStream(content);
		save(is, file);
	}

	/**
	 * 功能：保存文件
	 * 
	 * @author 宋立君
	 * @date 2014年06月24日
	 * @param streamIn
	 *            文件流
	 * @param file
	 *            保存到的文件
	 * @throws IOException
	 */
	public static void save(InputStream streamIn, File file) throws IOException {
		if (file == null) {
			throw new RuntimeException("保存文件不能为空");
		}
		if (streamIn == null) {
			throw new RuntimeException("文件流不能为空");
		}
		// 输出流
		OutputStream streamOut = null;
		// 文件夹不存在就创建。
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		streamOut = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
			streamOut.write(buffer, 0, bytesRead);
		}
		streamOut.close();
		streamIn.close();
	}

	/**
	 * 文件转化为字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				// log.error("helper:the file is null!");
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			// log.error("helper:get bytes from file process error!");
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 把字节数组保存为一个文件
	 * 
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			ret = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	 /** 
     * 将输入流转换成字节流 
     * @param input 
     * @return 
     * @throws Exception 
     */  
    public static byte[] toBytes(InputStream input) throws Exception {  
        byte[] data = null;  
        try {  
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int read = 0;  
            while ((read = input.read(b)) > 0) {  
                byteOut.write(b, 0, read);  
            }  
            data = byteOut.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            input.close();  
        }  
        return data;  
    }  
    
    /** 
     * 以指定编码格式将输入流按行置入一个List<String> 
     *  
     * @param input 
     * @return 
     * @throws Exception 
     */  
    public static List<String> toLines(InputStream input, String encoding)  
            throws Exception {  
        InputStreamReader insreader = new InputStreamReader(input, encoding);  
        BufferedReader bin = new BufferedReader(insreader);  
        List<String> lines = new ArrayList<String>();  
        String line;  
        while ((line = bin.readLine()) != null) {  
            lines.add(line);  
        }  
        bin.close();  
        insreader.close();  
        return lines;  
    }  
    
    /** 
     * 以GBK格式将输入流按行置入一个List<String> 
     *  
     * @param input 
     * @return 
     * @throws Exception 
     */  
    public static List<String> toLines(InputStream input) throws Exception {  
        return toLines(input, "GBK");  
    } 
    
    /**
     * 转换文件大小格式
     * 
     * @param fileS
     * @return 2014年7月8日
     */
    public static String FormetFileSize(long fileS)
    {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    
    /** 递归 */
    public static long getFileSize(File f) throws Exception// 取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            }
            else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    
    /**
     * 获得文件大小
     * 
     * @param f
     * @return
     * @throws Exception
     *             2014年7月8日
     */
    public static long getFileSizes(File f) throws Exception
    {// 取得文件大小
        long s = 0;
        if (f.exists())
        {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        }
        else
        {
            // f.createNewFile();
            System.out.println("文件不存在");
            return 0;

        }
        return s;
    }
    
    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath)
    {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath)
    {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator))
        {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory())
        {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            // 删除子文件
            if (files[i].isFile())
            {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else
            {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath)
    {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists())
        { // 不存在返回 false
            return flag;
        }
        else
        {
            // 判断是否为文件
            if (file.isFile())
            { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            }
            else
            { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除文件和文件夹，去掉需要过滤的文件。
     * 
     * @param dirPath
     * @param filterFileList
     * @return 2014年7月2日
     */
    public static boolean deleteFileByFilterFile(String dirPath,
            List<File> filterFileList)
    {
        File file = new File(dirPath);
        if (!file.exists())
        {
        	log.error(dirPath + "目录不存在。");
            return false;
        }

        if (!file.isDirectory())
        {
        	log.error(dirPath + "是一个文件，不是目录结构。");
            return false;
        }

        File[] fileArray = file.listFiles();

        if (fileArray == null)
        {
        	log.error(dirPath + "目录下没有子文件。");
            return false;
        }

        for (int i = 0; i < fileArray.length; i++)
        {
            File tempFile = fileArray[i];
            // 过滤不需要删除的文件
            if (filterFileList != null && filterFileList.contains(tempFile))
            {
                continue;
            }
            deleteFolder(tempFile.getPath());
        }

        return true;
    }
    
    
    

}
