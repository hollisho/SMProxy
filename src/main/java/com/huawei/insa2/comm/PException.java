package com.huawei.insa2.comm;

import java.io.*;
import java.net.ProtocolException;

/**
 * 通信异常的父类。
 * @author 李大伟
 * @version 1.0
 */
public class PException extends ProtocolException {

  /**
   * 通信异常必须说明原因，因此这里不提供不带参数的构造方法。
   * @param message 异常细节信息。
   */
  public PException(String message) {
    super(message);
  }
}