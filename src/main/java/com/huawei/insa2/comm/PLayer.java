package com.huawei.insa2.comm;

//import java.io.*;
import java.util.*;
//import com.huawei.insa2.util.*;

/**
 * 抽象协议层，如连接、会话、事务等。
 * @author 李大伟
 * @version 1.0
 */
public abstract class PLayer {

  /** 最大自动机号。*/
  public static final int maxId = 1000000000; //1G足够大了

  /** 自动机号。*/
  protected int id;

  /** 下一个子协议层自动机号。只使用有限正整数。*/
  protected int nextChildId;

  /** 父协议层。若是最低层则该成员为null。*/
  protected PLayer parent;

  /** 子协议层。在第一次往里面添加时才创建，防止无子协议层的自动机浪费内存。*/
  private HashMap children;

  /** 对消息触发的新建子协议层自动机行为的一组监听器。保存PListener的列表。*/
  private List listeners;

  /**
   * 创建一个协议层自动机。如果父协议层非空，则创建同时把自己注册到父协议层的子列表中。
   * @param theParent 父协议层，如果是最底层则传入null。
   */
  protected PLayer(PLayer theParent) {
    if (theParent!=null) { //父协议层存在
      id = ++theParent.nextChildId;
      if (theParent.nextChildId>=maxId) {
        theParent.nextChildId=0;
      }
      if (theParent.children==null) { //子协议层列表尚未创建
        theParent.children = new HashMap();
      }
      theParent.children.put(new Integer(id),this);
      parent = theParent;
    }
  }

  /**
   * 将消息向下传递给父协议层。
   * @param message 发送的消息。
   */
  public abstract void send(PMessage message) throws PException;

  /**
   * 当父协议层收到消息时，通过getChildId()方法，根据消息中带的信息，找到相应的本协议层自
   * 动机号，并自动回调其onReceive()的方法。本协议层的onReceive()方法再继续调度消息到更
   * 上一层协议自动机，直到达到最顶层协议自动机。
   * 最顶层协议自动机应该覆盖onReceive()方法，将消息保存起来并提供取得消息的方法。如果
   * getChildId()返回的是-1，表示是通信对方主动发起的操作，则主动创建一个子协议层自动机。
   * @param message 协议栈下层递交的消息。
   */
  public void onReceive(PMessage message) {
    PLayer child; //接收本消息的子协议层自动机
    int childId = getChildId(message);
    if (childId==-1) { //表示需要主动创建一个子协议层自动机
      child = createChild();
      child.onReceive(message);
      fireEvent(new PEvent(PEvent.CHILD_CREATED,this,child));
    } else { //从当前子协议自动机中找
      child = (PLayer) children.get(new Integer(getChildId(message)));
      if (child==null) { //找不到消息对应的自动机
        fireEvent(new PEvent(PEvent.MESSAGE_DISPATCH_FAIL,this,message));
        //Log.error(Resource.get("comm/dispatch-message-fail"));
      } else { //找到消息对应的自动机
        child.onReceive(message);
        //fireEvent(new PEvent(PEvent.CHILD_CREATED,this,child));
      }
    }
  }

  /**
   * 取得该协议层的父协议层。如果当前协议层是最底层则返回null。
   * return 父协议层自动机对象。
   */
  public PLayer getParent() {
    return parent;
  }

  /**
   * 取得子协议层自动机的个数。如果该协议层是最高层则返回0。
   * @return 子协议层自动机个数。
   */
  public int getChildNumber() {
    if (children==null) {
      return 0;
    }
    return children.size();
  }

  /**
   * 创建子协议层。最高协议层没有子协议层，可以不实现本方法，其他协议层必须覆盖本方法。
   * @exception UnsupportedOperationException 总是抛出。
   */
  protected PLayer createChild() {
    throw new UnsupportedOperationException("Not implement");
  }

  /**
   * 从消息中获得调度本消息到子协议层自动机的自动机号。例如，如果连接层的上一层是会话层，则
   * 连接层的getChildId()方法应该从消息中提取出会话Id来。如果该消息要求创建新的自动机则该
   * 方法应返回-1。最高协议层没有子协议层，可以不实现本方法，其他协议层必须覆盖本方法。
   * @param message 调度的消息。
   * @return 消息对应的子协议层自动机号。
   * @exception UnsupportedOperationException 总是抛出。
   */
  protected int getChildId(PMessage message) {
    throw new UnsupportedOperationException("Not implement");
  }

  /**
   * 关闭该自动机。缺省的实现是将自己从父协议层的子协议层列表中删除。最底层协议自动机必须
   * 覆盖close()方法并做自己该做的事情，例如关闭Socket连接等。若不覆盖则会抛出异常。
   * 最低协议层必须覆盖本方法，其他协议层不应覆盖本方法。
   * @exception UnsupportedOperationException 如果是最低协议层。
   */
  public void close() {
    if (parent==null) { //如果是最低协议层则抛出异常
      throw new UnsupportedOperationException("Not implement");
    }
    parent.children.remove(new Integer(id));
  }

  /**
   * 注册协议层自动机各种事件的监听器。如创建、删除、消息发送成功、消息发送失败、接收消息
   * 消息。
   * @param l 待添加的事件监听器。
   */
  public void addEventListener(PEventListener l) {
    if (listeners==null) {
      listeners = new ArrayList();
    }
    listeners.add(l);
  }

  /**
   * 删除该协议层的事件监听器。
   * @param l 待删除的事件监听器。
   */
  public void removeEventListener(PEventListener l) {
    listeners.remove(l);
  }

  /**
   * 发出一个协议事件。
   * @param e 事件内容。
   */
  protected void fireEvent(PEvent e) {
    //Log.debug(e);
    if (listeners==null) { //没有人对该协议层上发生的事情感兴趣，那就直接返回
      //Log.warn(Resource.get("comm-no-listener")+e);
      return;
    }

    //通知所有事件监听器
    for (Iterator i = listeners.iterator();i.hasNext();) {
      ((PEventListener)i.next()).handle(e);
    }
  }
}
