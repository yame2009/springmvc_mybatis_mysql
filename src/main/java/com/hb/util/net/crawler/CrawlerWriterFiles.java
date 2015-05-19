/**  
 * @Title: CrawlerWriterFiles.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:29:10 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.util.EntityUtils;

/** 
 * @ClassName: CrawlerWriterFiles 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:29:10  
 */
public class CrawlerWriterFiles {
    public void open() {
        for (int i = 0; i < MyCrawler.getThread(); i++) {
            new Thread(new Runnable() {
                public void run() {
                    while(true){
                        try {
                            DefaultHttpClient client = new SystemDefaultHttpClient();
                            final UrlObject obj = QueryCrawler.pop();
                            if(obj != null){
                                HttpPost httpPost = new HttpPost(obj.getUrl());
                                HttpResponse response = client.execute(httpPost);
                                final String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                                if(obj.getIdeep() < MyCrawler.getDeep() && !obj.getUrl().endsWith(".css")){
                                    CrawlerUtil.addUrlObject(obj, result);
                                }
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {                                           
                                            CrawlerUtil.writer(obj.getUrl(), result);
                                        } catch (IOException e) {
                                            System.err.println("输出错误url:"+obj.getUrl());
                                        }
                                    }
                                }).start();
                            } else {
                                System.out.println("--------暂时没有任务！！");
                                Thread.sleep(5000);                             
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("error");
                        }
                    }               
                }
                 
            }).start();
        }               
    }   
}
