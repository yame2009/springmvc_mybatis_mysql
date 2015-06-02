/**  
 * @Title: DownMM.java 
 * @Package com.hb.util.net 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年6月2日 上午8:58:16 
 * @version V1.0  
 */ 
package com.hb.util.net;

/** 
 * @ClassName: DownMM 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年6月2日 上午8:58:16  
 */
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class DownMM {
    public static void main(String[] args) throws Exception {
        //By: X!ao_f
        String out = "d:/"; 
        String url = "http://www.mzitu.com/share/comment-page-";
        Pattern reg = Pattern.compile("<p><img src=\"(.*?)\"");
        for(int j=0, i=1; i<=10; i++){
            Matcher m = reg.matcher(new Scanner(new URL(url+i).openStream()).useDelimiter("\\A").next());
            while(m.find()){
                Files.copy(new URL(m.group(1)).openStream(), Paths.get(out + UUID.randomUUID() + ".jpg"));
                System.out.println("已下载:"+j++);
            }
        }
    }
}
