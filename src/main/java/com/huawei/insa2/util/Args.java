package com.huawei.insa2.util;

import java.util.*;

/**
 * 提供一个保存参数的容器，用来给那些参数多、可选参数多、参数易变的方法提供参数读取能力。
 * 直接使用Map等类对于数据类型处理不是方便。本类提供常用数据类型的读取方法。用法示例：<br>
 * port = args.get("port",8080);<br>
 * 放入Map中的port值可以是"8443"或new Integer(8443)或其他任何类型，只要toString()
 * 能够解析出一个整数。
 * @author 李大伟
 * @version 1.0
 */
public class Args {

  /** 一个常量空参数表。用于传递空参数，该参数列表已经锁定，不能修改。*/
  public static final Args EMPTY = new Args().lock();

  /** 参数列表是否已经锁定，锁定后的参数列表将只读。*/
  boolean locked;

  /** Map保存的参数。*/
  Map args;

  /** 创建空参数列表。*/
  public Args() {
    this(new HashMap());
  }
  /**
   * 用Map创建参数。
   * @param theArgs 保存参数的Map对象。
   */
  public Args(Map theArgs) {
    if (theArgs==null) {
      throw new NullPointerException("argument is null");
    }
    args = theArgs;
  }

  /**
   * 取得字符串参数的方法。
   * @param key 参数名。
   * @param def 参数值。
   */
  public String get(String key,String def) {
    try {
      return args.get(key).toString();
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 取得整数参数的方法。
   * @param key 参数名。
   * @param def 参数值。
   */
  public int get(String key,int def) {
    try {
      return Integer.parseInt(args.get(key).toString());
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 取得长整数参数的方法。
   * @param key 参数名。
   * @param def 参数值。
   */
  public long get(String key,long def) {
    try {
      return Long.parseLong(args.get(key).toString());
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 取得浮点数参数的方法。
   * @param key 参数名。
   * @param def 参数值。
   */
  public float get(String key,float def) {
    try {
      return Float.parseFloat(args.get(key).toString());
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 取得布尔参数的方法。只有"true"解析成true其他解析成false。
   * @param key 参数名。
   * @param def 参数值。
   */
  public boolean get(String key,boolean def) {
    try {
      return ("true".equals(args.get(key)));
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 取得原始对象类型。
   * @param key 参数名。
   * @param def 参数缺省值。
   */
  public Object get(String key,Object def) {
    try {
      Object obj = args.get(key);
      if (obj==null) {
        return def;
      }
      return obj;
    } catch (Exception ex) {
      return def;
    }
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,Object value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,value);
    return this;
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,int value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,new Integer(value));
    return this;
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,boolean value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,new Boolean(value));
    return this;
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,long value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,new Long(value));
    return this;
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,float value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,new Float(value));
    return this;
  }

  /**
   * 往当前参数表中添加一个参数。
   * @param key 参数名。
   * @param value 参数值。
   * @return 添加了新参数后的参数表(对象实例没有变)。
   * @exception UnsupportedOperationException 如果参数列表已经被锁定。
   */
  public Args set(String key,double value) {
    if(locked) {
      throw new UnsupportedOperationException("Args have locked,can modify");
    }
    args.put(key,new Double(value));
    return this;
  }

  /**
   * 锁定参数列表。锁定后该参数列表将变成只读的，任何对set()方法的调用都将抛出
   * UnsupportedOperationException异常。已经锁定的参数表再次调用本方法没有任何影响。
   * @return 锁定后的参数列表(对象实例没有变)。
   */
  public Args lock() {
    locked = true;
    return this;
  }

  /**
   * 参数列表的字符串描述。
   * @return 所有参数值。
   */
  public String toString() {
    return args.toString();
  }
}