package com.hb.util.commonUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	/**
     * 强转对象类型
     * 
     * @param obj
     * @param c
     * @return
     */
    public static <T> T cast(Object obj, Class<T> c)
    {
        try
        {
            if (null == obj)
            {
                return null;
            }
            return c.cast(obj);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    /**
     * 判断字符串是否为数值类型 例如： A.整数，1；2；3；... B.双精度型，1.3；6.5；65.22
     *
     * @param str
     * @param lastNum
     *            精确到小数点后几位数,或整型精确数值长度
     * @param isInt
     *            是否只需要判断字符串为整型。
     * @return boolean true:匹配成功。
     */
    public static boolean isNumeric(String str, int lastNum, boolean isInt)
    {
        if (StringUtil.isEmpty(str))
        {
            return false;
        }

        String tempPattern = "";
        if (isInt)
        {
            tempPattern = "^[0-9]+$";
        }
        else
        {
            if (lastNum < 1)
            {
                return false;
            }
            tempPattern = "^[0-9]+(.([0-9]{1," + lastNum + "}))?$";
        }

        Pattern pattern = Pattern.compile(tempPattern);
        Matcher isNum1 = pattern.matcher(str);

        if (!isNum1.matches())
        {
            return false;
        }

        return true;
    }
    
    /**
     * 判断字符串是否为数值类型 例如： A.整数，1；2；3；... B.双精度型，1.3；6.5；65.22
     *
     * @param str
     * @param lastNum
     *            精确到小数点后几位数,或整型精确数值长度
     * @param isInt
     *            是否只需要判断字符串为整型。
     * @return boolean true:匹配成功。
     */
    public static boolean isNumeric(String str, int lastNum)
    {
        if (StringUtil.isEmpty(str))
        {
            return false;
        }

        if (lastNum < 1)
        {
            return false;
        }

        String tempPattern = "^[0-9]+(\\.([0-9]{1," + lastNum + "}))?$";
        if (str.endsWith("."))
        {
            tempPattern = "^([0-9])+(\\.([0-9]{1," + lastNum + "})?)?$";
        }

        Pattern pattern = Pattern.compile(tempPattern);
        Matcher isNum1 = pattern.matcher(str);

        if (!isNum1.matches())
        {
            return false;
        }

        return true;
    }
    
    /**
     * 判断字符串中是否包含中文
     *
     * @param str
     * @return true,表示包含中文.
     */
    public static boolean hasChineseChar(String str)
    {
        if (StringUtil.isEmpty(str))
        {
            return false;
        }

        char[] tempChar = str.toCharArray();
        for (int i = 0; i < tempChar.length; i++)
        {
            if (CommonUtil.isChinese(tempChar[i]))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     */
    public static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
        {
            return true;
        }
        return false;
    }


}
