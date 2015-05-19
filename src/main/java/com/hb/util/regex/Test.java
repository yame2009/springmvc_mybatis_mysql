/**  
 * @Title: Test.java 
 * @Package com.hb.util.regex 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:00:04 
 * @version V1.0  
 */ 
package com.hb.util.regex;

/** 
 * @ClassName: Test 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:00:04  
 */
public class Test {
     
    public static void main(String[] arg){
        String res = "abc adc aac acc abbc";
        String pattern = "a\\wc";
        //String pattern = "awc";
        //简单用法的示范
        int size = ZZUtil.init(res, pattern).size();
        boolean find = ZZUtil.init(res, pattern).find();
        System.out.println("find-->"+find);
        System.out.println("size-->"+size);
        //获取匹配到的字符串
        System.out.println("------下面是get相关------");
        ZZUtil zz = ZZUtil.init(res, pattern);
        System.out.println("all-->"+zz.get(-1));
        System.out.println("all-->"+zz.get(0));
        System.out.println("all-->"+zz.get(1));
        System.out.println("all-->"+zz.get(2));
        System.out.println("all-->"+zz.get(3));
        System.out.println("all-->"+zz.get(4));
        //替换字符串
        System.out.println("------下面是replace相关------");
        String replacement = "诶嘿嘿";
        System.out.println("替换所有："+zz.replaceAll(replacement));
        System.out.println("替换第一个："+zz.replaceFirst(replacement));
        System.out.println("替换最后一个："+zz.replaceTail(replacement));
        System.out.println("替换第2个："+zz.replace(1, replacement));
        System.out.println("替换第3个："+zz.replace(2, replacement));
        System.out.println("替换第10个（并没有10个）："+zz.replace(9, replacement));
        //实用功能
        System.out.println("------下面是匹配整个字符串------");
        String p = "1\\d{10}";//验证手机号
        String e = ".+@\\w+.com";//验证邮箱
        System.out.println("验证手机号18202020202："+ZZUtil.init("18202020202", p).match());
        System.out.println("验证手机号1320202020："+ZZUtil.init("1820202020", p).match());
        System.out.println("验证手机号20202020202："+ZZUtil.init("20202020202", p).match());
        System.out.println("验证邮箱3164@qq.com："+ZZUtil.init("3164@qq.com", e).match());
        System.out.println("验证邮箱www.3164@163.com："+ZZUtil.init("www.3164@163.com", e).match());
        System.out.println("验证邮箱www.baidu.com："+ZZUtil.init("www.baidu.com", e).match());
    }
 
}
