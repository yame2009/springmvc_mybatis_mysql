package com.hb.util.file;

import java.io.File;
import java.util.StringTokenizer;
 
/**
 * 文件/目录 部分处理
 * @createTime Dec 25, 2010 7:06:58 AM
 * @version 1.0
 */
public class DealDir {
    /**
     * 获取文件的后缀名并转化成大写
     * 
     * @param fileName
     *            文件名
     * @return
     */
    public String getFileSuffix(String fileName) throws Exception {
        return fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length()).toUpperCase();
    }
 
    /**
     * 创建多级目录
     * 
     * @param path
     *            目录的绝对路径
     */
    public void createMultilevelDir(String path) {
        try {
            StringTokenizer st = new StringTokenizer(path, "/");
            String path1 = st.nextToken() + "/";
            String path2 = path1;
            while (st.hasMoreTokens()) {
 
                path1 = st.nextToken() + "/";
                path2 += path1;
                File inbox = new File(path2);
                if (!inbox.exists())
                    inbox.mkdir();
 
            }
        } catch (Exception e) {
            System.out.println("目录创建失败" + e);
            e.printStackTrace();
        }
 
    }
 
    /**
     * 删除文件/目录(递归删除文件/目录)
     * 
     * @param path
     *            文件或文件夹的绝对路径
     */
    public void deleteAll(String dirpath) {
        if (dirpath == null) {
            System.out.println("目录为空");
        } else {
            File path = new File(dirpath);
            try {
                if (!path.exists())
                    return;// 目录不存在退出
                if (path.isFile()) // 如果是文件删除
                {
                    path.delete();
                    return;
                }
                File[] files = path.listFiles();// 如果目录中有文件递归删除文件
                for (int i = 0; i < files.length; i++) {
                    deleteAll(files[i].getAbsolutePath());
                }
                path.delete();
 
            } catch (Exception e) {
                System.out.println("文件/目录 删除失败" + e);
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 文件/目录 重命名
     * 
     * @param oldPath
     *            原有路径（绝对路径）
     * @param newPath
     *            更新路径
     * @author lyf 注：不能修改上层次的目录
     */
    public void renameDir(String oldPath, String newPath) {
        File oldFile = new File(oldPath);// 文件或目录
        File newFile = new File(newPath);// 文件或目录
        try {
            boolean success = oldFile.renameTo(newFile);// 重命名
            if (!success) {
                System.out.println("重命名失败");
            } else {
                System.out.println("重命名成功");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}