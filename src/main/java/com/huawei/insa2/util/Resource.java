package com.huawei.insa2.util;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.URL;

/**
 * 资源文件读取类，该类内部实际使用Cfg类来实现，最大的区别是增加了格式化方法和
 * 修改为只读方法。
 * @author 李大伟
 * @version 1.0
 */
public class Resource {

  /** 使用的资源包。实际是用配置类来实现的，资源文件只读。*/
  private Cfg resource;

  /**
   * 根据URL获取资源对象。该方法可从jar文件或classpath中获得资源。
   * @param url 资源位置URL，不包含语言后缀和.xml
   */
  public Resource(String url) throws IOException {
    init(url);
  }

  /**
   * 相对一个类的位置来读取资源。
   * @param c URL取相对该类的位置。
   * @param url 相对URL。
   */
  public Resource(Class c,String url) throws IOException {

    String className = c.getName();

    int i = className.lastIndexOf('.');
    if (i>0) {
      className = className.substring(i+1);
    }
    URL u = new URL(c.getResource(className+".class"),url);
    init(u.toString());
  }

  /**
   * 初始化
   */
  public void init(String url) throws IOException {
    String str = url + '_' + Locale.getDefault();
    InputStream in = null;
    int i;
    for(;;){
      try {
        resource = new Cfg(str+".xml",false);
        return;
      } catch (IOException ex) {
        i = str.lastIndexOf('_');
        if (i<0) {
          throw new MissingResourceException("Can't find resource url:"
              + url + ".xml",getClass().getName(),null);
        }
        str = str.substring(0,i);
        continue;
      }
    }
  }

  /**
   * 从资源文件中获取字符串，该方法利用配置模块的功能，和读取配置的方法类似。
   * @param key 资源中的键名。
   * @return 和该键名对应的资源字符串。若找不到key对应的资源则返回key本身。
   * @exception NullPointException key为null。
   */
  public String get(String key) {
    return resource.get(key,key);
  }

 /**
   * 查找某节点下的所有子节点的名字。
   * @param key 被查节点的名字。
   * @return String[] 子节点名字的字符串数组，如果不存在key对应的子节点，
   * 则返回长度为0的空数组。
   */
  public String[] childrenNames(String key) {
      return resource.childrenNames(key);
  }

  /**
   * 取得资源字符串并且用给定的参数进行格式化。参数个数最多10个：{0}到{9}。
   * 若取资源失败或者格式化失败则返回key。
   * 例如：在资源文件中有如下一行：<br>
   * connectionFail=连接失败，地址{0}，端口号{1}。
   * 则Resource.getString("connectionFail",new String[]{"10.108.12.34","8080"})
   * 返回的字符串为：<br>
   * 连接失败，地址10.108.12.34，端口号8080。<br>
   * 详细格式化规则请参见MessageFormat.format()。
   * @param key 资源中的键名。
   * @param params用于格式化资源字符串的参数。
   * @return 和该键名对应的资源字符串。
   * 若找不到key对应的资源或者格式化失败，则返回key本身。
   *
   */
  public String get(String key,Object[] params) {
    String value = resource.get(key,key);        //得到资源字符串
    try {
      return MessageFormat.format(value,params);     //根据参数格式化
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return key;
    }
  }

  /**
   * 取得资源字符串并用一个参数对其进行格式化。
   * @param 插入字符串中的参数。
   * @return 格式化后的字符串。
   */
  public String get(String key, Object param) {
    return get(key,new Object[]{param});
  }

  /**
   * 取得资源字符串并用两个参数对其进行格式化。
   * @param 插入字符串中的参数。
   * @return 格式化后的字符串。
   */
  public String get(String key, Object param1,Object param2) {
    return get(key,new Object[]{param1,param2});
  }

  /**
   * 取得资源字符串并用三个参数对其进行格式化。
   * @param 插入字符串中的参数。
   * @return 格式化后的字符串。
   */
  public String get(String key, Object param1, Object param2,
          Object param3) {
    return get(key,new Object[]{param1,param2,param3});
  }
}
