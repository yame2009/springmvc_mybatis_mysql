package com.hb.util.file;
import java.io.*;
import java.util.logging.Logger;
import java.util.zip.*;
 
/**
 * Java zip 工具类 <a>http://201212262922.iteye.com/blog/2096807</a>
 * 不需要依赖第三方包
 * Created by sunyameng on 14-3-10.
 */
public class ZipUtil2 {
	
    private final static Logger logger = Logger.getLogger(ZipUtil.class.getName());
    private static final int BUFFER = 1024 * 10;
    
    /**
     * 将指定目录压缩到和该目录同名的zip文件，自定义压缩路径
     *
     * @param sourceFilePath 目标文件路径
     * @param zipFilePath    指定zip文件路径
     * @return
     */
    public static boolean zip(String sourceFilePath, String zipFilePath,String zipFileName) {
        boolean result = false;
        File source = new File(sourceFilePath);
        if (!source.exists()) {
            logger.info(sourceFilePath + " doesn't exist.");
            return result;
        }
        if (!source.isDirectory()) {
            logger.info(sourceFilePath + " is not a directory.");
            return result;
        }
        File zipFile = new File(zipFilePath + File.separator + zipFileName + ".zip");
        if (zipFile.exists()) {
            logger.info(zipFile.getName() + " is already exist.");
            return result;
        } else {
            if (!zipFile.getParentFile().exists()) {
                if (!zipFile.getParentFile().mkdirs()) {
                    logger.info("cann't create file " + zipFileName);
                    return result;
                }
            }
        }
        logger.info("creating zip file...");
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        try {
            dest = new FileOutputStream(zipFile);
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            out = new ZipOutputStream(new BufferedOutputStream(checksum));
            out.setMethod(ZipOutputStream.DEFLATED);
            compress(source, out, source.getName());
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result) {
            logger.info("done.");
        } else {
            logger.info("fail.");
        }
        return result;
    }
 
    private static void compress(File file, ZipOutputStream out, String mainFileName) {
        int index = file.getAbsolutePath().indexOf(mainFileName);
        String entryName = file.getAbsolutePath().substring(index);
        //System.out.println(entryName);
        if (file.isFile()) {
            FileInputStream fi = null;
            BufferedInputStream origin = null;
            try {
                fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(entryName);
                out.putNextEntry(entry);
                byte[] data = new byte[BUFFER];
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (origin != null) {
                    try {
                        origin.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (file.isDirectory()) {
            try {
                out.putNextEntry(new ZipEntry(entryName+File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
            File[] fs = file.listFiles();
            if (fs != null && fs.length > 0) {
                for (File f : fs) {
                    compress(f, out, mainFileName);
                }
            }
        }
    }
 
    /**
     * 将zip文件解压到指定的目录，该zip文件必须是使用该类的zip方法压缩的文件
     *
     * @param zipFile   要解压的zip文件
     * @param destPath  指定解压到的目录
     * @return
     */
    public static boolean unzip(File zipFile, String destPath) {
        boolean result = false;
        if (!zipFile.exists()) {
            logger.info(zipFile.getName() + " doesn't exist.");
            return result;
        }
        File target = new File(destPath);
        if (!target.exists()) {
            if (!target.mkdirs()) {
                logger.info("cann't create file " + target.getName());
                return result;
            }
        }
        String mainFileName = zipFile.getName().replace(".zip", "");
        File targetFile = new File(destPath + File.separator + mainFileName);
        if (targetFile.exists()) {
            logger.info(targetFile.getName() + " already exist.");
            return result;
        }
        ZipInputStream zis = null;
        logger.info("start unzip file ...");
        try {
            FileInputStream fis = new FileInputStream(zipFile);
            CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
            zis = new ZipInputStream(new BufferedInputStream(checksum));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[BUFFER];
                String entryName = entry.getName();
                //logger.info(entryName);
                String newEntryName = destPath + File.separator + entryName;
                newEntryName=newEntryName.replaceAll("\\\\", "/");
                File f = new File(newEntryName);
                if(newEntryName.endsWith("/")){
                    if(!f.exists()){
                        if(!f.mkdirs()) {
                            throw new RuntimeException("can't create directory " + f.getName());
                        }
                    }
                }else{
                    File temp=f.getParentFile();
                    if (!temp.exists()) {
                        if (!temp.mkdirs()) {
                            throw new RuntimeException("create file " + temp.getName() + " fail");
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(f);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result) {
            logger.info("done.");
        } else {
            logger.info("fail.");
        }
        return result;
    }
 
    public static void main(String[] args) throws IOException {
//        String path="D:\\temp\\B";
//        ZipUtil.zip(path,"d:/temp/c","anhuigs123");
        String zipfile ="D:\\temp\\c\\B.zip";
        File zipFile = new File(zipfile);
        String output="D:\\temp\\c";
        ZipUtil2.unzip(zipFile, output);
    }
}
