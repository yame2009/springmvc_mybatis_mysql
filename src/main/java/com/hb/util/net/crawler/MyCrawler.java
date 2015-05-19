/**  
 * @Title: MyCrawler.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:27:52 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

/** 
 * @ClassName: MyCrawler 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:27:52  
 */

public class MyCrawler {
    private static String url;
    private static int deep = 4;
    private static int topN = 10;
    private static int thread = 3;
    private static String host;
    private static String dir = System.getProperty("user.dir");
    private static MyCrawler crawler = new MyCrawler();
    public static MyCrawler getInstance(){
        return crawler;
    }
    private MyCrawler(){}
    public static int getDeep() {
        return deep;
    }
    public static void setDeep(int deep) {
        MyCrawler.deep = deep;
    }
    public static int getTopN() {
        return topN;
    }
    public static void setTopN(int topN) {
        MyCrawler.topN = topN;
    }
    public static String getUrl() {
        return url;
    }
    public static void setUrl(String url) {
        MyCrawler.url = url;
        if(url.endsWith(".html")){
            host = url.substring(0, url.lastIndexOf("/"));
        } else {
            MyCrawler.host = url;
        }
    }
    public static String getHost() {
        return host;
    }
    public static String getDir() {
        return dir;
    }   
    public void start() {
        UrlObject obj = new UrlObject(url);
        obj.setIdeep(1);
        QueryCrawler.push(obj);
        CrawlerWriterFiles writer = new CrawlerWriterFiles();
        writer.open();
    }
    public static void setDir(String dir) {
        MyCrawler.dir += dir+"\\";
    }
    public static int getThread() {
        return MyCrawler.thread;
    }
    public static void setThread(int thread) {
        MyCrawler.thread = thread;
    }
}