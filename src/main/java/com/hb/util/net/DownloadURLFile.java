package com.hb.util.net;

import java.io.ByteArrayOutputStream;
import java.io.File;  
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;  
  
import org.apache.commons.io.FileUtils;  

public class DownloadURLFile {
  
	/**
	 * 方法1，此方法依赖于org.apache.commons.io包
	 * @param url
	 * @param dir
	 * @return
	 */
    public static String downloadFromUrl(String url,String dir) {  
  
        try {  
            URL httpurl = new URL(url);  
            String fileName = getFileNameFromUrl(url);  
            System.out.println(fileName);  
            File f = new File(dir + fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(String url){  
        String name = new Long(System.currentTimeMillis()).toString() + ".X";  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1);  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }  
    
    /**
	 * 方法2，从网络Url中下载文件。推荐此方法。
	 * @param urlStr    URL下载路径
	 * @param fileName  下载后，保存文件名
	 * @param savePath  下载文件夹路径
	 * @throws IOException
	 */
	public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
		URL url = new URL(urlStr);  
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
		conn.setConnectTimeout(3*1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//得到输入流
		InputStream inputStream = conn.getInputStream();  
		//获取自己数组
		byte[] getData = readInputStream(inputStream);    

		//文件保存位置
		File saveDir = new File(savePath);
		if(!saveDir.exists()){
			saveDir.mkdir();
		}
		File file = new File(saveDir+File.separator+fileName);    
		FileOutputStream fos = new FileOutputStream(file);     
		fos.write(getData); 
		if(fos!=null){
			fos.close();  
		}
		if(inputStream!=null){
			inputStream.close();
		}


		System.out.println("info:"+url+" download success"); 

	}

	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
		
		byte[] buffer = new byte[1024];  
		int len = 0;  
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		while((len = inputStream.read(buffer)) != -1) {  
			bos.write(buffer, 0, len);  
		}  
		bos.close();  
		return bos.toByteArray();  
	}  

	public static void main(String[] args) {
		
		//方法1
//		  String res = downloadFromUrl("http://images.17173.com/2010/www/roll/201003/0301sohu01.jpg","d:/");  
//	        System.out.println(res);  
		
		//方法2：
		try{
			downLoadFromUrl("http://101.95.48.97:8005/res/upload/interface/apptutorials/manualstypeico/6f83ce8f-0da5-49b3-bac8-fd5fc67d2725.png",
					"百度.jpg","d:/images");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
