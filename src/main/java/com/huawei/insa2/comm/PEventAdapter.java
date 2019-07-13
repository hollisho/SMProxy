package com.huawei.insa2.comm;

import com.huawei.insa2.util.*;

/**
 * 协议事件适配器。实现PEventListener接口，并提供将事件分类并提供更明确的事件信息。
 * @author 李大伟
 * @version 1.0
 */

public class PEventAdapter implements PEventListener{

  /**
   * 适配器实现Listener的handle()方法并根据事件类型将事件分类。子类只需要覆盖其关心的事件
   * 类型即可。
   * @param e 收到的事件。
   */
  public void handle(PEvent e) {
    switch (e.getType()) {
      case PEvent.CHILD_CREATED:
        childCreated((PLayer)e.getData());
        break;
      case PEvent.CREATED:
        created();
        break;
      case PEvent.DELETED:
        deleted();
        break;
      case PEvent.MESSAGE_DISPATCH_FAIL:
        messageDispatchFail((PMessage)e.getData());
        break;
      case PEvent.MESSAGE_DISPATCH_SUCCESS:
        messageDispatchFail((PMessage)e.getData());
        break;
      case PEvent.MESSAGE_SEND_SUCCESS:
        messageDispatchFail((PMessage)e.getData());
        break;
      case PEvent.MESSAGE_SEND_FAIL:
        messageDispatchFail((PMessage)e.getData());
        break;
      default:

        //有漏掉的事件类型，不应该出现的情况
        //Log.warn(Resource.get("comm/event-adapter-slip"));
    }
  }
  public void childCreated(PLayer child) {}
  public void messageSendError(PMessage msg) {}
  public void messageSendSuccess(PMessage msg) {}
  public void messageDispatchFail(PMessage msg) {}
  public void messageDispatchSuccess(PMessage msg) {}
  public void created(){}
  public void deleted(){}
}
