package com.huawei.insa2.util;

/**
 * 对监视线程的抽象。封装一种特殊的线程行为：一旦被创建就永远循环地做某件事情，
 * 直到该线程被杀死。这是一个抽象类，从Thread类继承而来，不能直接实例化，其子类
 * 需要覆盖方法task()来完成具体的功能。本系统的接收线程ReceiveThread，发送线程
 * SendThread，和过期会话清除线程SessionCleaner都有该特点，均从该类继承。
 * @author 李大伟
 * @version 1.0
 */
public abstract class WatchThread extends Thread {

  /** 该线程存活标志，kill()方法将该标志置为false。*/
  private boolean alive = true;

  /** 当前线程状态信息。用于告知外界该线程正在做什么。*/
  private State state= State.TERMINATED;

  /** 该类的所有子类对象均创建到这个线程组中。*/
  public static final ThreadGroup tg = new ThreadGroup("watch-thread");

  /**
   * 构造函数，提供一个线程名参数。构造方法只创建线程，并不启动。
   * @param name 线程的名字，为线程起个好名字对调试和日志记录很有帮助。
   */
  public WatchThread(String name) {
    super(tg,name);
    setDaemon(true); //设置成精灵线程（程序在只剩下精灵线程运行时将自动结束）
  }

  /**
   * 杀死该线程的方法，将alive标志置为false，当run()方法的while循环发现该标志为
   * false时将跳出循环结束线程。需注意的是kill()方法返回时并不一定线程立即死掉。
   * 要等到线程主体从一次task()方法返回后才会结束。
   */
  public void kill() {
    alive = false;
  }

  /**
   * 线程主体，循环运行task()方法，直到调用了kill()方法。
   */
  public final void run() {

    //无论出现什么异常都不能使该线程终止！
    while (alive) {
      try {
        //System.out.println("doTask()");
        task();
      } catch (Exception ex) {
          System.out.println("Exception ex");
          ex.printStackTrace();
      } catch (Throwable t) { //出现严重错误，搞不好系统会死掉
          System.out.println("Throwable t");
          t.printStackTrace();
      }
    }
  }

  /**
   * 设置状态信息。用来告诉外界该线程正在干什么。
   * @param state 新的状态信息。
   */
  protected void setState(State newState) {
    this.state = newState;
  }

  /**
   * 获取状态信息。告诉外界该线程正在干什么。
   * @return 状态信息。
   */
  public State getState() {
    return this.state;
  }

  /**
   * 子类必须覆盖的抽象方法，需要循环做的事情。
   */
  abstract protected void task();
}