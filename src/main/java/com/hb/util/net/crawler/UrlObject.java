/**  
 * @Title: UrlObject.java 
 * @Package com.hb.util.net.crawler 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:28:36 
 * @version V1.0  
 */ 
package com.hb.util.net.crawler;

/** 
 * @ClassName: UrlObject 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:28:36  
 */
public class UrlObject {
    private String url;
    private int ideep;
    public UrlObject(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getIdeep() {
        return ideep;
    }
    public void setIdeep(int ideep) {
        this.ideep = ideep;
    }
    public UrlObject(String url, int ideep) {
        this.url = url;
        this.ideep = ideep;
    }   
}