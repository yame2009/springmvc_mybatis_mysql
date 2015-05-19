/**  
 * @Title: EncryptUtilApi.java 
 * @Package com.hb.util.security.encrypt 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:03:15 
 * @version V1.0  
 */ 
package com.hb.util.security.encrypt;

/**
 * java支持的加密解密
 * java加密解密工具类v1.1  http://www.oschina.net/code/snippet_816576_45518
 * <br>
 * 单向加密：MD5、SHA1
 * <br>
 * 双向加密：DES、AES
 * 
 * @author linin
 *
 */
public interface EncryptUtilApi {
 
    //------MD5-------//
    String MD5(String res);
    String MD5(String res,String key);
 
    //------SHA1-------//
    String SHA1(String res);
    String SHA1(String res,String key);
 
    //------DES-------//
    String DESencode(String res,String key);
    String DESdecode(String res,String key);
 
    //------AES-------//
    String AESencode(String res,String key);
    String AESdecode(String res,String key);
     
    //------异或加密-----//
    String XORencode(String res,String key);
    String XORdecode(String res,String key);
    int XOR(int res,String key);
 
}
