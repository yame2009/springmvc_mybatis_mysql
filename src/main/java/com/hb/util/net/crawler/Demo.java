/**  
 * @Title: Demo.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:11:38 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

/** 
 * 自己写的爬虫(测试对象：抓取API-JDK8) http://www.oschina.net/code/snippet_1590790_48008 
 * @ClassName: Demo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:11:38  
 */

public class Demo {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        MyCrawler crawler = MyCrawler.getInstance();
        crawler.setUrl("http://docs.oracle.com/javase/8/docs/api/");
        crawler.setDir("/api2");
        crawler.setDeep(3);
        crawler.setThread(1);
        crawler.start();
    }
}