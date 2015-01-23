package com.hb.util.security;

import java.util.HashMap;

/**
 * 128进制加解密, 一个符号可表示7个bit
 * 可以自定义符号表, 符号不能重复
 * @author lry
 *
 */
public class MyBASE128 {
 
    public static final char[] symbolTable = new char[128];
    public static final HashMap<Character, Integer> indexTable = new HashMap<Character, Integer>(128);
    static {
        int i = 0;
        for (int j = 0; j < 128; j++) {
            if('A' <= j && j <= 'Z'
                || 'a' <= j && j <= 'z'
                || '0' <= j && j <= '9') {
                symbolTable[i++] = (char) j;
            }
        }
        for (char c : "这是中国人写地[备思要爱发]编解码程序&拥有独特的自定义符号表|不过布能使用重复字,汗啊(还差一十二个知*.#)。为我们加油吧，亲！".toCharArray()) {
            symbolTable[i++] = c;
        }
         
        checkTable();
 
        for (int j = 0; j < 128; j++) {
            indexTable.put(symbolTable[j], j);
        }
    }
 
    private static void checkTable() throws Error {
        if(symbolTable[127] == 0) {
            throw new Error("符号表长度不正确！");
        }
        for (char a : symbolTable) {
            int count = 0;
            for (char b : symbolTable) {
                if(a == b) {
                    count++;
                }
            }
            if(count > 2) {
                throw new Error("符号表有重复符号！");
            }
        }
    }
 
    public String encode(byte[] data) {
        if(data == null || data.length == 0) {
            return new String();
        }
        StringBuilder result = new StringBuilder();
        int tail = 0;
        for (int i = 0; i < data.length; i++) {
            int mov = (i % 7 + 1);
            int curr = 0xFF & data[i];
            int code = tail + (curr >> mov);
            result.append(symbolTable[code]);
            tail = (0xFF & (curr << (8 - mov))) >> 1;
            if(mov == 7) {
                result.append(symbolTable[tail]);
                tail = 0;
            }
        }
        result.append(symbolTable[tail]);
        return result.toString();
    }
 
    public byte[] decode(String base128) {
        if(base128 == null || base128.length() == 0) {
            return new byte[] { };
        }
        int length = (int) Math.floor(base128.length() * 0.875);
        byte[] result = new byte[length];
        int idx = 0;
        int head = indexTable.get(base128.charAt(0)) << 1;
        for (int i = 1; i < base128.length();) {
            int mod = i % 8;
            int code = indexTable.get(base128.charAt(i++));
            result[idx++] = (byte) (0xFF & (head + (code >> (7 - mod))));
            if(mod == 7) {
                head = 0xFF & (indexTable.get(base128.charAt(i++)) << 1);
            } else {
                head = 0xFF & (code << (mod + 1));
            }
        }
        return result;
    }
     
    ///////////////////////测试方法///////////////////////////////
    public static void main(String[] args) {
        MyBASE128 base128 = new MyBASE128();
        test(base128);
         
        String txt = "李茹钰的所得税的速度是滴哦osidusiou3247IZIiauydiYUI";
        String enc = base128.encode(txt.getBytes());
        System.out.println(enc);
        System.out.println("----------------");
        System.out.println(new String(base128.decode(enc)));
    }
 
    private static void test(MyBASE128 base128) {
        for (int i = 0; i < 10000; i++) {
            String r = randomData();
            String d = new String(base128.decode(base128.encode(r.getBytes())));
            if(!r.equals(d)) {
                System.out.println("加解密失败！: " + r);
            }
        }
    }
 
    private static String randomData() {
        String textString = "了咖啡机累啊戴假发\n\r哦\\\\地 \\ 方i \\ 就啊 \n\n\\ \r\\ n\\ 我诶\n人\\ 竟\n \\然n\n\\a\f去tr品\r\f \t\b 味a rad\\ \n\\r\\bn\\r\\h\\j\\\f\\g\\g\\yoi端午节凹入30498305u8tfjlerf-12345i0rwe94ri349-1=-230rcki库i情况i爬q-23r0ｉｗｅ我饿去骗人３４０９１２３９５８１－４５４－５１２￥！＠＃——％）！＃￥……×！（）￥＠＃％——（CR!#$U*(%_#*_$%*!245=-";
        int start = random(0, textString.length() - 3);
        int end = random(start + 1, textString.length() - 1);
        return textString.substring(start, end);
    }
 
    private static int random(int i, int j) {
        return (int) Math.ceil(Math.random()*(j-i)+i);
    }
}