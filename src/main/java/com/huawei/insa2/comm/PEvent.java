package com.huawei.insa2.comm;

import java.util.HashMap;
import java.lang.reflect.Field;

/**
 * 协议层自动机的状态事件。包括：创建、删除、收到消息、发送响应、创建子协议层自动机。
 * @author 李大伟
 * @version 1.0
 */
public class PEvent {

  /** 事件类型常量-自动机已创建。*/
  public static final int CREATED = 1;

  /** 事件类型常量-创建子协议层自动机。*/
  public static final int CHILD_CREATED = 2;

  /** 事件类型常量-自动机被删除。*/
  public static final int DELETED = 4;

  /** 事件类型常量-消息发送成功。*/
  public static final int MESSAGE_SEND_SUCCESS = 8;

  /** 事件类型常量-消息发送失败(底层自动机异常)。*/
  public static final int MESSAGE_SEND_FAIL = 16;

  /** 事件类型常量-收到消息，调度成功。(消息已提交给上层协议自动机)*/
  public static final int MESSAGE_DISPATCH_SUCCESS = 32;

  /** 事件类型常量-收到消息，调度失败。(找不到消息对应的上层协议自动机)*/
  public static final int MESSAGE_DISPATCH_FAIL = 64;

  /** 事件值和名称的对应表。*/
  static final HashMap names = new HashMap();
  static {
    try {
      Field[] f = PEvent.class.getFields();
      for (int i=0;i<f.length;i++) {
        String name = f[i].getName();
        Object id = f[i].get(null);
        names.put(id,name);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** 事件来源。*/
  protected PLayer source;

  /** 事件类型。*/
  protected int type;

  /** 事件所带数据。*/
  protected Object data;

  /**
   * 创建一个事件。
   * @param type 事件类型。
   * @param source 事件发出的协议层自动机。
   * @param data 事件所带数据。该数据具体什么内容由事件发出者决定。例如创建子协议层自动机事件可把子
   * 协议自动机作为数据。
   */
  public PEvent(int type,PLayer source,Object data) {
    this.type = type;
    this.source = source;
    this.data = data;
  }

  /**
   * 事件来自哪个协议层。
   * @return 事件的发源地。
   */
  public PLayer getSource() {
    return source;
  }

  /**
   * 取得事件类型。
   * @return 本类定义的一组常量之一。
   */
  public int getType() {
    return type;
  }

  /**
   * 取得事件所带数据。该数据具体什么内容由事件发出者决定。例如创建子协议层自动机事件可把子
   * 协议自动机作为数据。
   * @return 事件所带数据。
   */
  public Object getData() {
    return data;
  }

  /**
   * 本事件的字符串描述。
   * @return 字符串描述。
   */
  public String toString() {
    return "PEvent:source=" + source + ",type=" +
           names.get(new Integer(type)) + ",data=" + data;
  }
}