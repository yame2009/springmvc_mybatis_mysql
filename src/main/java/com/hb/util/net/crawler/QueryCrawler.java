/**  
 * @Title: QueryCrawler.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:28:53 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: QueryCrawler 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:28:53  
 */
public class QueryCrawler {
    private static QueryCrawler query = new QueryCrawler();
    private static ArrayList<UrlObject> list = new ArrayList<UrlObject>();
    private QueryCrawler(){}
    public static QueryCrawler getInstance() {
        return query;
    }
    public synchronized static void push(UrlObject obj) {
        list.add(obj);
    }
    public synchronized static void push(List<UrlObject> objs) {
        list.addAll(objs);
    }
    public synchronized static UrlObject pop() {
        if(list.size() < 1)
            return null;
        return list.remove(0);
    }
}
