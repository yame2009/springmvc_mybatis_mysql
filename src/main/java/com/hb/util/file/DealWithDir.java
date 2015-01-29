package com.hb.util.file;

import java.io.File;

/**
 * 目录处理工具类
 *
 */
public class DealWithDir {
    /**
     * 新建目录
     */
    public static boolean newDir(String path) throws Exception {
        File file = new File(path);
        return file.mkdirs();//创建目录
    }
     
    /**
     * 删除目录
     */
    public static boolean deleteDir(String path) throws Exception {
        File file = new File(path);
        if (!file.exists())
            return false;// 目录不存在退出
        if (file.isFile()) // 如果是文件删除
        {
            file.delete();
            return false;
        }
        File[] files = file.listFiles();// 如果目录中有文件递归删除文件
        for (int i = 0; i < files.length; i++) {
            deleteDir(files[i].getAbsolutePath());
        }
        file.delete();
         
        return file.delete();//删除目录
    }
 
    /**
     * 更新目录
     */
    public static boolean updateDir(String path, String newPath) throws Exception {
        File file = new File(path);
        File newFile = new File(newPath);
        return file.renameTo(newFile);
    }
     
    public static void main(String d[]) throws Exception{
        //deleteDir("d:/ff/dddf");
        updateDir("D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/22222", "D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/224222");
    }
     
     
}