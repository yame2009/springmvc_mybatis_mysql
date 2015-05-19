/**  
 * @Title: ZZUtil.java 
 * @Package com.hb.util.regex 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午8:59:38 
 * @version V1.0  
 */ 
package com.hb.util.regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * 正则表达式工具类
 * 主要功能有：
    boolean find() 是否能匹配到至少一个
    boolean match() 匹配整个字符串//这个方法其实可以用^$做到
    int size() 匹配的字符串个数
    String[] getAll() 获取所有匹配到的字符串
    String get(int index) 获取匹配到的第N个字符串
    String replaceAll(String replacement) 替换所有匹配到的字符串
    String replaceFirst(String replacement) 替换第一个匹配到的字符串
    String replaceTail(String replacement) 替换最后一个匹配到的字符串
    String replace(int index,String replacement) 替换第N个匹配到的字符串

 * @author lu
 *
 */
public class ZZUtil {
     
    private static ZZUtil zz;
    public static ZZUtil init(CharSequence res,String pattern){
        if (zz==null||(!res.equals(zz.res)||!pattern.equals(zz.pattern))) {
            //在没有实例或内容不同的时候新建一个实例
            zz = new ZZUtil(res, pattern);
        }
        return zz;
    }
     
    private CharSequence res;
    private String pattern;
    private Pattern p;
    private Matcher m;
    private int size = 0;
    private String[] list;//所有匹配到的字符串
     
    private ZZUtil(CharSequence res,String pattern){
        this.res = res;
        this.pattern = pattern;
        p = Pattern.compile(pattern);
        m = p.matcher(res);
        while(m.find()){
            size++;
        }
        list = new String[size];
        m.reset();
        for (int i = 0; i < size; i++) {
            if(m.find())list[i] = m.group();
        }
    }
     
    /**是否能匹配到至少一个*/
    public boolean find(){
        return size>0;
    }
     
    /**匹配整个字符串，必须整个字符串满足正则表达式才算true*/
    public boolean match(){
        return find()&&get(0).equals(res);
    }
     
    /**获取所有匹配的字符串个数*/
    public int size(){
        return size;
    }
     
    /**获取所有匹配到的字符串*/
    public String[] getAll(){
        return list;
    }
     
    /**获取匹配到的第N个字符串*/
    public String get(int index){
        if (index<0) {
            System.out.println("请不要开玩笑");
            return null;
        }
        if (index>=size) {
            System.out.println("并没有匹配到辣么多");
            return null;
        }
        return list[index];
    }
     
    /**替换所有匹配到的字符串*/
    public String replaceAll(String replacement){
        return m.replaceAll(replacement);
    }
     
    /**替换第一个匹配到的字符串*/
    public String replaceFirst(String replacement){
        return m.replaceFirst(replacement);
    }
 
    /**替换最后一个匹配到的字符串*/
    public String replaceTail(String replacement){
        return replace(size-1, replacement);
    }
     
    /**替换第N个匹配到的字符串*/
    public String replace(int index,String replacement){
        m.reset();
        boolean isFind = false;
        while(index>=0){
            isFind = m.find();
            index--;
        }
        if(isFind){
            StringBuffer sb = new StringBuffer();
            m.appendReplacement(sb, replacement);
            m.appendTail(sb);
            return sb.toString();
        }
        return (String) res;
    }
 
}
