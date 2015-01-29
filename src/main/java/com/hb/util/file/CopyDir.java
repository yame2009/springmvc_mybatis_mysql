package com.hb.util.file;

import java.io.*;

/**
 * 1,建立目的目录。 2，遍历源目录。 3，遍历过程中，创建文件或者文件夹。 原理：其实就是改变了源文件或者目录的目录头。
 * @datetime  Dsc  24
 */
public class CopyDir {
    private File sDir, dDir, newDir;
 
    public CopyDir(String s, String d) {
        this(new File(s), new File(d));
    }
 
    CopyDir(File sDir, File dDir)// c:\\Test d:\\abc
    {
        this.sDir = sDir;
        this.dDir = dDir;
    }
 
    public void copyDir() throws IOException {
        // 是创建目的目录。也就是创建要拷贝的源文件夹。Test
        // 获取源文件夹名称。
        String name = sDir.getName();
        // 通过该名称在目的目录创建该文件夹，为了存放源文件夹中的文件或者文件夹。
        // 将目的目录和源文件夹名称，封装成File对象。
        newDir = dDir;
        // new File(dDir,name);
        // 调用该对象的mkdir方法。在目的目录创建该文件夹。d:\\abc\\Test
        newDir.mkdir();//
 
        // 遍历源文件夹。
        listAll(sDir);
    }
 
    /*
     * 将遍历目录封装成方法。 在遍历过程中，遇到文件创建文件。 遇到目录创建目录。
     */
    private void listAll(File dir) throws IOException {
        File[] files = dir.listFiles();
        for (int x = 0; x < files.length; x++) {
            if (files[x].isDirectory()) {
                createDir(files[x]);// 调用创建目录的方法。
                listAll(files[x]);// 在继续进行递归。进入子级目录。
            } else {
                createFile(files[x]);// 调用创建文件的方法。
            }
        }
    }
 
    /*
     * copy目录。通过源目录在目的目录创建新目录。
     */
    private void createDir(File dir) {
        File d = replaceFile(dir);
        d.mkdir();
    }
 
    /*
     * copy文件。
     */
    private void createFile(File file) throws IOException {
        File newFile = replaceFile(file);
        // copy文件是一个数据数据传输的过程。需要通过流来完成。
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(newFile);
        byte[] buf = new byte[1024 * 2];
        int num = 0;
        while ((num = fis.read(buf)) != -1) {
            fos.write(buf, 0, num);
        }
        fos.close();
        fis.close();
    }
 
    /*
     * 替换路径。
     */
    private File replaceFile(File f) {
        // 原理是：将源目录的父目录(C:\\Tset)，替换成目的父目录。（d:\\abc\\Test）
        String path = f.getAbsolutePath();// 获取源文件或者文件夹的决定路径。
        // 将源文件或者文件夹的绝对路径替换成目的路径。
        String newPath = path.replace(sDir.getAbsolutePath(), newDir
                .getAbsolutePath());
        // 将新的目的路径封装成File对象
        File newFile = new File(newPath);
        return newFile;
    }
}