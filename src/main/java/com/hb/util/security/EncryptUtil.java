package com.hb.util.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
 






import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
/**
 * java支持的加密解密
 * <br>
 * 单向加密：MD5、SHA1
 * <br>
 * 双向加密：DES、AES
 * <br>
 * 注意：本工具类不使用Base64转字符串，而是直接将byte[]转为16进制字符串
 * <br>
 * Java加密技术（一）——BASE64与单向加密算法MD5&SHA&MAC<url>http://snowolf.iteye.com/blog/379860</url>
 * <br>
 * 
 * @author linin
 *
 */
public class EncryptUtil{
 
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";
 
    /**编码格式；默认null为GBK*/
    public String charset = null;
    /**DES*/
    public int keysizeDES = 0;
    /**AES*/
    public int keysizeAES = 128;
    public static EncryptUtil me;
 
    private EncryptUtil(){
        //单例
    }
 
    public static EncryptUtil getInstance(){
        if (me==null) {
            me = new EncryptUtil();
        }
        return me;
    }
     
    /**使用MessageDigest进行单向加密（无密码）*/
    private String messageDigest(String res,String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
            return parseByte2HexStr(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**使用KeyGenerator进行单向/双向加密（可设密码）*/
    private String keyGeneratorMac(String res,String algorithm,String key){
        try {
            SecretKey sk = null;
            if (key==null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错*/
    private String keyGeneratorES(String res,String algorithm,String key,int keysize,boolean isEncode){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            }else if (key==null) {
                kg.init(keysize);
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            }else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**将二进制转换成16进制 */
    private  String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {
                hex = '0' + hex;  
            }
            sb.append(hex.toUpperCase());  
        }
        return sb.toString();  
    }
    /**将16进制转换为二进制*/
    private  byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }
        return result;  
    }
    
    
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

 
    public String MD5(String res) {
        return messageDigest(res, MD5);
    }
 
    public String MD5(String res, String key) {
        return keyGeneratorMac(res, HmacMD5, key);
    }
 
    public String MD5Random(String res) {
        return keyGeneratorMac(res, HmacMD5, null);
    }
    
    /**
	 * 根据提供的参数，生成md5值</br> 会对传过来的值用UTF-8方式编码
	 * 
	 * @param source
	 * @return 正常的字符串，出错会返回null
	 */

	public static String getMD5(String ss) {
		byte[] source;
		try {
			source = ss.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
		}
		return s;
	}
 
    public String SHA1(String res) {
        return messageDigest(res, SHA1);
    }
 
    public String SHA1(String res, String key) {
        return keyGeneratorMac(res, HmacSHA1, key);
    }
 
    
    public String SHA1Random(String res) {
        return keyGeneratorMac(res, HmacSHA1, null);
    }
 
    
    public String DESencode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, true);
    }
 
    
    public String DESdecode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, false);
    }
 
    
    public String AESencode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, true);
    }
 
    
    public String AESdecode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, false);
    }
     
    
    /***************************  end    *************************************************/
    
    /***************************  new     *************************************************/
    
    /**  
     * 用MD5算法进行加密  
     * @param str 需要加密的字符串  
     * @return MD5加密后的结果  
     */    
    public  String encodeMD5String(String str) {    
        return encode(str, "MD5");    
    }    
    
    /**  
     * 用SHA算法进行加密  
     * @param str 需要加密的字符串  
     * @return SHA加密后的结果  
     */    
    public  String encodeSHAString(String str) {    
        return encode(str, "SHA");    
    }    
    
    /**  
     * 用base64算法进行加密  
     * @param str 需要加密的字符串  
     * @return base64加密后的结果  
     */    
    public  String encodeBase64String(String str) {    
        BASE64Encoder encoder =  new BASE64Encoder();    
        return encoder.encode(str.getBytes());    
    }    
        
    /**  
     * 用base64算法进行解密  
     * @param str 需要解密的字符串  
     * @return base64解密后的结果  
     * @throws IOException   
     */    
    public  String decodeBase64String(String str) throws IOException {    
        BASE64Decoder encoder =  new BASE64Decoder();    
        return new String(encoder.decodeBuffer(str));    
    }    
        
    private  String encode(String str, String method) {    
        MessageDigest md = null;    
        String dstr = null;    
        try {    
            md = MessageDigest.getInstance(method);    
            md.update(str.getBytes());    
            dstr = new BigInteger(1, md.digest()).toString(16);    
        } catch (NoSuchAlgorithmException e) {    
            e.printStackTrace();    
        }    
        return dstr;    
    }  
    
    
    public static void main(String ...arg){
        String res = "测试test";
        String key = "秘钥key";
        String mw = "密文，临时用的";
        System.out.println("--MD5--");
        EncryptUtil instance = EncryptUtil.getInstance();
		System.out.println(instance.MD5(res));
        System.out.println(instance.MD5(res,key));
        System.out.println(instance.MD5Random(res));
        System.out.println("--SHA1--");
        System.out.println(instance.SHA1(res));
        System.out.println(instance.SHA1(res,key));
        System.out.println(instance.SHA1Random(res));
        System.out.println("--DES--");
        mw = instance.DESencode(res,key);
        System.out.println(mw);
        mw = instance.DESdecode(mw, key);
        System.out.println(mw);
        System.out.println("--AES--");
        mw = instance.AESencode(res,key);
        System.out.println(mw);
        mw = instance.AESdecode(mw, key);
        System.out.println(mw);
        
        //******************************************************************//
        String user = "oneadmin";    
        System.out.println("原始字符串 " + user);    
        System.out.println("MD5加密 " + instance.encodeMD5String(user));    
        System.out.println("SHA加密 " +instance.encodeSHAString(user));    
        String base64Str = instance.encodeBase64String(user);    
        System.out.println("Base64加密 " + base64Str);    
        try {
			System.out.println("Base64解密 " + instance.decodeBase64String(base64Str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
}