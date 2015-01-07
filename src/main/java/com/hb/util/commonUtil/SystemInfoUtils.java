package com.hb.util.commonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0
 * @author 勋辉
 * @createDate 2014年3月15日 上午10:02:18
 * @since JDK1.6
 *  
 * 
 */
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
 
 
 
public class SystemInfoUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SystemInfoUtils.class);
     
	
    public static void main(String[] args) {
        getDiskInfo();
    }
     
    /**
     * 获取CPU使用率
     * @Title: getCpuPerc
     * @return        double
     * @exception   异常
     * @throws
     * @see                  需要参见的其它内容
     * @since     ISSP v1.5
     * @author        勋辉
     * @time         2014年3月15日上午10:40:21
     */
    public static double  getCpuPerc(){
        Sigar sigar = new Sigar();
        CpuPerc cpuPerc[] =null;
        double result =0d;
        try {
            /* CPU 信息列表 */
            cpuPerc=sigar.getCpuPercList();
            List<BigDecimal> list = new ArrayList<BigDecimal>();
            /* 获取每颗CPU的总共使用量  */
            for (int i = 0; i < cpuPerc.length; i++) {
                printCpuInfo(cpuPerc[i],i);
                BigDecimal b = new BigDecimal(Double.toString(cpuPerc[i].getCombined()));
                list.add(b);
            }
            /* 相加 */
            BigDecimal add = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                add=add.add(list.get(i));
            }
            /* 求平均值 */
            result = add.divide(new BigDecimal(list.size()),2,RoundingMode.HALF_EVEN).doubleValue();
        } catch (SigarException e) {
            logger.error("[获取CPU使用率失败]",e);
        }
        return result;
    }
     
     
    private static void printCpuInfo(CpuPerc cpuPerc,int i ){
        logger.debug("[ CPU"+i+"   ]总共使用率："+CpuPerc.format(cpuPerc.getCombined())+" ]");
    }
     
     
    /**
     * 获取内存信息
     * @Title: getPhysicalMemory
     * @param     
     * @return        void
     * @exception   异常
     * @throws
     * @see                  需要参见的其它内容
     * @since     ISSP v1.5
     * @author        勋辉
     * @time         2014年3月15日上午10:50:55
     */
    public static double getPhysicalMemory(){
        double result = 0d;
        Sigar sigar = new Sigar();
        Mem men = null;
        try {
            men=sigar.getMem();
            logger.debug("[系统内存总量："+men.getTotal()/1024L+"k]");
            logger.debug("[系统内存用量："+men.getUsed()/1024L+"k]");
            logger.debug("[使用率百分比："+men.getUsedPercent()+"]");
             
            BigDecimal userPrect = new BigDecimal(men.getUsedPercent());
            result = userPrect.divide(new BigDecimal(1),2,RoundingMode.HALF_EVEN).doubleValue();
             
        } catch (SigarException e) {
            logger.error("[获取系统内存使用率]",e);
        }
        return result;
    }
     
     
    /**
     * 获取磁盘信息
     * @Title: getDiskInfo
     * @param     
     * @return        void
     * @exception   异常
     * @throws
     * @see                  需要参见的其它内容
     * @since     ISSP v1.5
     * @author        勋辉
     * @time         2014年3月15日上午11:47:06
     */
    public static double getDiskInfo(){
        double result = 0d;
        BigDecimal total = new BigDecimal(0);
        BigDecimal used = new BigDecimal(0);
        Sigar sigar = new Sigar();  
         
         try {
            FileSystem fslist[] = sigar.getFileSystemList();  
             // String dir = System.getProperty("user.home");// 当前用户文件夹路径  
             for (int i = 0; i < fslist.length; i++) {  
                 FileSystem fs = fslist[i];  
                 FileSystemUsage usage = null;  
                 try {  
                     usage = sigar.getFileSystemUsage(fs.getDirName());  
                 } catch (SigarException e) {  
                     if (fs.getType() == 2)  
                     continue;  
                 }  
                 switch (fs.getType()) {  
                 case 0: // TYPE_UNKNOWN ：未知  
                     break;  
                 case 1: // TYPE_NONE  
                     break;  
                 case 2: // TYPE_LOCAL_DISK : 本地硬盘  
                     // 文件系统总大小  
                     total= total.add(new BigDecimal((float)usage.getTotal()/1024/1024));
                     // 文件系统已经使用量  
                     used=used.add(new BigDecimal((float)usage.getUsed()/1024/1024));
                     break;  
                 case 3:// TYPE_NETWORK ：网络  
                     break;  
                 case 4:// TYPE_RAM_DISK ：闪存  
                     break;  
                 case 5:// TYPE_CDROM ：光驱  
                     break;  
                 case 6:// TYPE_SWAP ：页面交换  
                     break;  
                 }  
             }
        } catch (SigarException e) {
            e.printStackTrace();
        }  
        result=used.divide(total,2,RoundingMode.HALF_EVEN).doubleValue();
        return result;
    }
}