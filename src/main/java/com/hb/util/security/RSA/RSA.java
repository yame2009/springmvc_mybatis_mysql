/**  
 * @Title: RSA.java 
 * @Package com.hb.util.security.RSA 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:07:23 
 * @version V1.0  
 */ 
package com.hb.util.security.RSA;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
 
import javax.crypto.Cipher;
 
public class RSA {
     
    public static void main(String ...args){
        System.out.println("甲方：支付宝");
        System.out.println("乙方：我");
        RSA rsa = RSA.create();
        System.out.println("支付宝平台生成公钥跟私钥：\n"+rsa.getPublicKey()+"\n"+rsa.getPrivateKey());
        System.out.println("我从支付宝获取到了公钥："+rsa.getPublicKey());
        System.out.println("------支付流程1------");
        String res = "request for pay";
        System.out.println("我需要传一段数据给支付宝请求支付，原文："+res);
        String resEncode = rsa.encodeByPublicKey(res, rsa.getPublicKey());
        System.out.println("我用我的公钥进行加密："+resEncode);
        System.out.println("支付宝使用私钥进行解密："+rsa.decodeByPrivateKey(resEncode, rsa.getPrivateKey()));
        System.out.println("------支付流程2------");
        res = "success";
        System.out.println("支付宝确认数据无误，打算返回一段数据通知我结果："+res);
        resEncode = rsa.encodeByPrivateKey(res, rsa.getPrivateKey());
        System.out.println("支付宝用私钥加密数据："+resEncode);
        System.out.println("我使用公钥进行解密："+rsa.decodeByPublicKey(resEncode, rsa.getPublicKey()));
        System.out.println("------特殊情况------");
        System.out.println("我的爱人偷偷记下我的公钥，打算拦截我的购物信息！");
        res = "request for pay";
        resEncode = rsa.encodeByPublicKey(res, rsa.getPublicKey());
        System.out.println("我请求了支付："+res);
        System.out.println("我爱人拦截到了密文："+resEncode);
        System.out.println("我爱人自作聪明的用公钥解密："+rsa.decodeByPublicKey(resEncode, rsa.getPublicKey()));
        System.out.println("我在角落里冷冷一笑，天真！");
    }
     
    public static final String KEY_ALGORITHM = "RSA";
     
    private static RSA me;
    private RSA(){}//单例
    public static RSA create(){
        if (me==null) {
            me = new RSA();
        }
        //生成公钥、私钥
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();
            me.publicKey = (RSAPublicKey) kp.getPublic();
            me.privateKey = (RSAPrivateCrtKey) kp.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return me;
    }
     
    private RSAPublicKey publicKey;
    private RSAPrivateCrtKey privateKey;
     
    /**获取公钥*/
    public String getPublicKey(){
        return parseByte2HexStr(publicKey.getEncoded());
    }
     
    /**获取私钥*/
    public String getPrivateKey(){
        return parseByte2HexStr(privateKey.getEncoded());
    }
 
    /**加密-公钥*/
    public String encodeByPublicKey(String res,String key){
        byte[] keyBytes = parseHexStr2Byte(key);//先把公钥转为2进制
        X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);//用2进制的公钥生成x509
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key pubKey = kf.generatePublic(x5);//用KeyFactory把x509生成公钥pubKey
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());//生成相应的Cipher
            cp.init(Cipher.ENCRYPT_MODE, pubKey);//给cipher初始化为加密模式，以及传入公钥pubKey
            return parseByte2HexStr(cp.doFinal(res.getBytes()));//以16进制的字符串返回
        } catch (Exception e) {
            System.out.println("公钥加密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**加密-私钥*/
    public String encodeByPrivateKey(String res,String key){
        byte[] keyBytes = parseHexStr2Byte(key);
        PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key priKey = kf.generatePrivate(pk8);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.ENCRYPT_MODE, priKey);
            return parseByte2HexStr(cp.doFinal(res.getBytes()));
        } catch (Exception e) {
            System.out.println("私钥加密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**解密-公钥*/
    public String decodeByPublicKey(String res,String key){
        byte[] keyBytes = parseHexStr2Byte(key);
        X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key pubKey = kf.generatePublic(x5);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.DECRYPT_MODE, pubKey);
            return new String(cp.doFinal(parseHexStr2Byte(res)));
        } catch (Exception e) {
            System.out.println("公钥解密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**解密-私钥*/
    public String decodeByPrivateKey(String res,String key){
        byte[] keyBtyes = parseHexStr2Byte(key);
        PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBtyes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key priKey = kf.generatePrivate(pk8);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cp.doFinal(parseHexStr2Byte(res)));
        } catch (Exception e) {
            System.out.println("私钥解密失败");
            e.printStackTrace();
        }
        return null;
    }
     
    /**将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
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
    public static byte[] parseHexStr2Byte(String hexStr) {
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
 
}