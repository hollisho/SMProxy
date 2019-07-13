package com.huawei.insa2.util;

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;

/**
 * 和加解密等安全主题相关的工具函数集。
 * @author InternetTeam3
 * @version 1.0
 */
public class SecurityTools {
  private static final byte[] salt = "webplat".getBytes();

  /**
   * 单向简单加密函数，对字符串加密，结果仍是一个字符串。该方法保证任何情况都不抛出异常或者
   * 返回null。输入为null或""都返回""。返回值为固定长度的28个Base64字符。
   * @param str 明文。
   * @return 加密后字符串。
   */
  public static String digest(String str) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("SHA");
      md5.update(salt); //混淆盐值
      return Base64.encode(md5.digest(str.getBytes()));
    } catch (NoSuchAlgorithmException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }
  }

  /**
   * 计算MD5消息摘要。(MD5摘要长度为16字节，保存摘要的数组长度要够，否则抛异常。)
   * @param data 需要计算摘要的数据。
   * @param offset 计算摘要的数据的起始偏移地址。
   * @param length 数据的长度。
   * @param digest 存放摘要的字节数组。
   * @param dOffset 摘要存放起始位置。
   */
  public static void md5(byte[] data,int offset, int length,
                    byte[] digest, int dOffset) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(data,offset,length);
      md5.digest(digest,dOffset,16);
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    } catch (DigestException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 计算消息摘要。
   * @param data 计算摘要的数据。
   * @param offset 数据偏移地址。
   * @param length 数据长度。
   * @return 摘要结果。(16字节)
   */
  public static byte[] md5(byte[] data,int offset, int length) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(data,offset,length);
      return md5.digest();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * 对称加密函数，针对字节数据。
   * @param src 明文。
   * @return 加密后数据。
   */
  public static byte[] encrypt(byte[] key, byte[] src) {
    try {
      return getCipher(key,Cipher.ENCRYPT_MODE).doFinal(src);
    } catch (BadPaddingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (IllegalBlockSizeException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }

  }

  /**
   * 对称加密函数，针对字符串数据。
   * @param str 明文。
   * @return 加密后字符串。
   */
  public static String encrypt(String key, String src) {
    try {
      return Base64.encode(getCipher(key.getBytes("UTF8"),
             Cipher.ENCRYPT_MODE).doFinal(src.getBytes("UTF8")));
    } catch (UnsupportedEncodingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (BadPaddingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (IllegalBlockSizeException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }
  }

  /**
   * 对称解密函数，针对字节数据。
   * @param src 明文。
   * @return 加密后数据。
   */
  public static byte[] decrypt(byte[] key, byte[] src) {
    try {
      return getCipher(key,Cipher.DECRYPT_MODE).doFinal(src);
    } catch (IllegalBlockSizeException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (BadPaddingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }
  }

  /**
   * 对称解密函数，针对字符串数据。
   * @param str 明文。
   * @return 解密后字符串。
   */
  public static String decrypt(String key, String src) {
    try {
      return new String(getCipher(key.getBytes("UTF8"),
             Cipher.DECRYPT_MODE).doFinal(Base64.decode(src)),"UTF8");
    } catch (UnsupportedEncodingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (BadPaddingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (IllegalBlockSizeException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }
  }

  /**
   * 对称加密算法。如果key长度小于8则右补0到8字节，使用DES加密；如果key长度大于等于24则
   * 使用三重DES加密。加密方法举例：<pre>
   * Cipher c = SecurityTools.getCipher("key".getBytes(),Cipher.ENCRYPT);
   * byte[] b = c.doFinal("data".getBytes());
   * </pre>
   * @param algorithm 算法名称。
   * @param key 密钥。
   * @param mode 使用模式如果是加密，则填Cipher.ENCRYPT，解密则填Cipher.DECRYPT。
   * @return 加密后的数据。
   */
  public static Cipher getCipher(byte[] key, int mode) {
    try {
      SecretKeyFactory keyFactory;
      KeySpec keySpec;
      Cipher c;
      if (key.length<8) {
        byte[] oldkey = key;
        key = new byte[8];
        System.arraycopy(oldkey,0,key,0,oldkey.length);
      }
      if (key.length>=24) {
        keyFactory = SecretKeyFactory.getInstance("DESede");
        keySpec = new DESedeKeySpec(key);
        c = Cipher.getInstance("DESede");
      } else {
        keyFactory = SecretKeyFactory.getInstance("DES");
        keySpec = new DESKeySpec(key);
        c = Cipher.getInstance("DES");
      }
      SecretKey k = keyFactory.generateSecret(keySpec);
      c.init(mode,k);
      return c;
    } catch (NoSuchAlgorithmException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (InvalidKeyException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (NoSuchPaddingException ex) {
      throw new UnsupportedOperationException(ex.toString());
    } catch (InvalidKeySpecException ex) {
      throw new UnsupportedOperationException(ex.toString());
    }
  }

  /**
   * 测试代码。
   * @param args 命令行参数，无。
   */
  public static void main(String[] args) {
    Debug.dump(digest("hello world"));
    for (int i=0;i<1000;i++) {
      decrypt("key",encrypt("key","hello world"));
    }
    Debug.dump(digest("hello world"));
  }
}