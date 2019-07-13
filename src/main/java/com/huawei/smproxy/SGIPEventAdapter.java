// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 18:01:11
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SGIPSMProxy.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.sgip.SGIPConnection;
import com.huawei.insa2.comm.sgip.SGIPTransaction;
import com.huawei.insa2.comm.sgip.message.*;

// Referenced classes of package com.huawei.smproxy:
//            SGIPSMProxy

class SGIPEventAdapter extends PEventAdapter
{

    public SGIPEventAdapter(SGIPSMProxy smProxy, SGIPConnection conn)
    {
        this.smProxy = smProxy;
        this.conn = conn;
    }

    /**
     * 覆盖父类该方法，处理PEvent.CHILD_CREATED事件，也就是SMC主动下发的消息
     * @child 为SMC主动下发的消息所建立的对应事务
     */
    public void childCreated(PLayer child)
    {
      //获取消息后，删除事务
        SGIPTransaction t = (SGIPTransaction)child;
        SGIPMessage msg = t.getResponse();
    //定义响应消息
        SGIPMessage resmsg = null;
        if(msg.getCommandId() == 2)//对下发终止应用层连接的响应
        {
            resmsg = new SGIPUnbindRepMessage();
            if(t.isChildOf(conn))
                smProxy.onTerminate();
        } else
        if(msg.getCommandId() == 1)
        {
            SGIPBindMessage tmpmes = (SGIPBindMessage)msg;
            int logintype = tmpmes.getLoginType();
            if(logintype != 2 && logintype != 11)
                resmsg = new SGIPBindRepMessage(4);
            resmsg = new SGIPBindRepMessage(0);
        } else
        if(msg.getCommandId() == 4)//对主动下发的短信消息的响应
        {
            SGIPDeliverMessage tmpmes = (SGIPDeliverMessage)msg;
            //调用客户程序处理主动下发短信消息的接口
            resmsg = smProxy.onDeliver(tmpmes);
        } else
        if(msg.getCommandId() == 5)
        {
            SGIPReportMessage tmpmes = (SGIPReportMessage)msg;
            resmsg = smProxy.onReport(tmpmes);
        } else
        if(msg.getCommandId() == 17)
        {
            SGIPUserReportMessage tmpmes = (SGIPUserReportMessage)msg;
            resmsg = smProxy.onUserReport(tmpmes);
        } else//不进行任何处理,只删除事务
        {
            t.close();
        }
        //对SMC主动下发的消息进行响应消息发送后，删除事务
        if(resmsg != null)
        {
            try
            {
                t.send(resmsg);
            }
            catch(PException e)
            {
                e.printStackTrace();
            }
            t.close();
        }
        if(msg.getCommandId() == 2)
        {
            SGIPConnection theconn = (SGIPConnection)t.getParent();
            theconn.close();
        }
    }

    private SGIPSMProxy smProxy;
    private SGIPConnection conn;
}