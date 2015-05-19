/**  
 * @Title: CrawlerUtil.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:29:27 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: CrawlerUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:29:27  
 */
public class CrawlerUtil {
    private static List<String> arrays = new ArrayList<String>();
    private static List<String> filearrays = new ArrayList<String>();
    static {
        String a = ",[]'\"+:;{}";
        String[] as = a.split("");
        for (int i = 0; i < as.length; i++) {
            if(as[i].equals("")){
                continue;
            }
            arrays.add(as[i]);
        }
        filearrays.add("?");
        filearrays.add("=");
        //filearrays.add(".");
    }
    public static void writer(String url, String data) throws IOException {
        File file = null;
        if(url.toLowerCase().endsWith(".css")){
            file = new File(getPathCSS(url));
        } else {
            file = new File(getPathHTML(url));
        }
        System.out.println(file.getPath());
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            byte[] datab = data.getBytes();
            FileOutputStream f = new FileOutputStream(file);
            f.write(datab, 0, datab.length);
            f.close();
        }
    }
 
    private static String getPathHTML(String url) {
        if(url.equals(MyCrawler.getHost())){
            url += "index";
        }
        if(!url.endsWith("html")){
            if(url.endsWith("/")){
                url+="index.html";
            }else if(url.lastIndexOf("/") < url.lastIndexOf(".")) {
                url = url.substring(0, url.lastIndexOf(".")) + ".html";
            } else {
                url += ".html";
            }
        }
        if(url.startsWith("http://")){
            url = MyCrawler.getDir() + url.replace(MyCrawler.getHost(), "");
        }       
        for (int i = 0; i < filearrays.size(); i++) {
            url = url.replaceAll("\\"+filearrays.get(i)+"", "_");
        }
        return url;
    }
    private static String getPathCSS(String url) {      
        if(url.startsWith("http://")){
            url = MyCrawler.getDir() + url.replace(MyCrawler.getHost(), "");
        }       
        return url;
    }
 
    public static void addUrlObject(UrlObject obj, String result) {
        //"<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]"
        Pattern pcss =Pattern.compile("<link.*href\\s*=\\s*\"?(.*?)[\"|>]",Pattern.CASE_INSENSITIVE);
        addUrlObjToPattern(pcss, obj, result);
        Pattern pa =Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]",Pattern.CASE_INSENSITIVE);
        addUrlObjToPattern(pa, obj, result);
        Pattern pframe =Pattern.compile("<frame\\s+src\\s*=\\s*\"?(.*?)[\"|>]",Pattern.CASE_INSENSITIVE);
        addUrlObjToPattern(pframe, obj, result);
    }
    private static void addUrlObjToPattern(Pattern p, UrlObject obj,
            String result) {
        Matcher m = p.matcher(result);
        ArrayList<UrlObject> urlobjs = new ArrayList<UrlObject>();
        while(m.find()){
            String link = m.group(1).trim();
            //urlobjs.add(new UrlObject(link, 1+obj.getIdeep()));
            if(!isLink(link)){
                continue;
            }
            if(link.startsWith(MyCrawler.getHost())){
                urlobjs.add(new UrlObject(link, 1+obj.getIdeep()));
            } else if(!link.contains("://")){
                urlobjs.add(new UrlObject(MyCrawler.getHost() + link, 1+obj.getIdeep()));
            }
        }
        QueryCrawler.push(urlobjs);
        show(urlobjs);
    }
 
    private static void show(ArrayList<UrlObject> urlobjs) {
        /*for (int i = 0; i < urlobjs.size(); i++) {
            System.out.println(urlobjs.get(i).getUrl());
        }*/    
    }
 
    private static boolean isLink(String link) {
        if(null == link) return false;
        link = link.replace(MyCrawler.getHost(), "");
        for (int i = 0; i < arrays.size(); i++) {
            if(link.contains(arrays.get(i))){
                return false;
            }
        }
        return true;
    }
}
