package com.hb.util.commonUtil;

import net.sourceforge.pinyin4j.PinyinHelper;  
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;  
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;  
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
  
 /**
  * 中文转拼音
  * @author Administrator
  *
  */
public class SpellHelper {  
     //将中文转换为英文  
     public static String getEname(String name) throws BadHanyuPinyinOutputFormatCombination {  
           HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();  
           pyFormat.setCaseType(HanyuPinyinCaseType. LOWERCASE);  
          pyFormat.setToneType(HanyuPinyinToneType. WITHOUT_TONE);  
           pyFormat.setVCharType(HanyuPinyinVCharType. WITH_V);  
            return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
     }  
  
     //姓、名的第一个字母需要为大写  
     public static String getUpEname(String name) throws BadHanyuPinyinOutputFormatCombination {  
            char[] strs = name.toCharArray();  
           String newname = null;  
                 
        //名字的长度  
     if (strs.length == 2) {     
                newname = toUpCase(getEname ("" + strs[0])) + " "  
                           + toUpCase(getEname ("" + strs[1]));  
           } else if (strs. length == 3) {  
                newname = toUpCase(getEname ("" + strs[0])) + " "  
                           + toUpCase(getEname ("" + strs[1] + strs[2]));  
           } else if (strs. length == 4) {  
                newname = toUpCase(getEname ("" + strs[0] + strs[1])) + " "  
                           + toUpCase(getEname ("" + strs[2] + strs[3]));  
           } else {  
                newname = toUpCase(getEname (name));  
           }  
  
            return newname;  
     }  
  
     //首字母大写  
     private static String toUpCase(String str) {  
           StringBuffer newstr = new StringBuffer();  
           newstr.append((str.substring(0, 1)).toUpperCase()).append(  
                     str.substring(1, str.length()));  
  
            return newstr.toString();  
     }  
     
     /**
 	 * 将汉字转换为全拼
 	 * 
 	 * @param src
 	 * @return
 	 */
 	public static String getPingYin(String src) {

 		char[] t1 = null;
 		t1 = src.toCharArray();
 		String[] t2 = new String[t1.length];
 		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
 		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
 		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
 		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
 		String t4 = "";
 		int t0 = t1.length;
 		try {
 			for (int i = 0; i < t0; i++) {
 				// 判断是否为汉字字符
 				if (java.lang.Character.toString(t1[i]).matches(
 						"[\\u4E00-\\u9FA5]+")) {
 					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
 					t4 += t2[0];
 				} else
 					t4 += java.lang.Character.toString(t1[i]);
 			}
 			// System.out.println(t4);
 			return t4;
 		} catch (Exception e1) {
 			e1.printStackTrace();
 		}
 		return t4;
 	}
 	
 	/**
	 * 返回中文的首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String temp = "";
		String demo = "";
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		for (int i = 0; i < convert.length(); i++) {// convert目前为小写首字母,下面是将小写首字母转化为大写
			if (convert.charAt(i) >= 'a' && convert.charAt(i) <= 'z') {
				temp = convert.substring(i, i + 1).toUpperCase();
				demo += temp;
			}
		}
		return demo;
	}
  
     public static void main(String[] args) {  
           try {
			System. out.println( getEname("李宇春"));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
     }  
  
}  
