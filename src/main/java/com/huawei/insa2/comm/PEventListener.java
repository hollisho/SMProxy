package com.huawei.insa2.comm;

/**
 * 协议层监听器接口。用来监听协议自动机创建、删除、消息发送、调度、创建子协议层等事件。
 * @author 李大伟
 * @version 1.0
 */
public interface PEventListener {

  /**
   * 协议层上发生的事件的监听者。
   * @param e 协议事件。
   */
  public abstract void handle(PEvent e);
}