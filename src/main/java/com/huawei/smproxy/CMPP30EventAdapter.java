// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-11 18:01:09
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   SMProxy30.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cmpp.message.*;
import com.huawei.insa2.comm.cmpp30.CMPP30Connection;
import com.huawei.insa2.comm.cmpp30.CMPP30Transaction;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;

// Referenced classes of package com.huawei.smproxy:
//            SMProxy30

class CMPP30EventAdapter extends PEventAdapter
{

    public CMPP30EventAdapter(SMProxy30 smProxy)
    {
        this.smProxy = smProxy;
        conn = smProxy.getConn();
    }

    /**
     * 覆盖父类该方法，处理PEvent.CHILD_CREATED事件，也就是SMC主动下发的消息
     * @child 为SMC主动下发的消息所建立的对应事务
     */
    public void childCreated(PLayer child)
    {
      //获取消息后，删除事务
        CMPP30Transaction t = (CMPP30Transaction)child;
        CMPPMessage msg = t.getResponse();
    //定义响应消息
        CMPPMessage resmsg = null;
        if(msg.getCommandId() == 2)//对下发终止应用层连接的响应
        {
            resmsg = new CMPPTerminateRepMessage();
            smProxy.onTerminate();
        } else
        if(msg.getCommandId() == 8)
            resmsg = new CMPPActiveRepMessage(0);
        else
        if(msg.getCommandId() == 5)//对主动下发的短信消息的响应
        {
            CMPP30DeliverMessage tmpmes = (CMPP30DeliverMessage)msg;
            //调用客户程序处理主动下发短信消息的接口
            resmsg = smProxy.onDeliver(tmpmes);
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
            conn.close();
    }

    private SMProxy30 smProxy;
    private CMPP30Connection conn;
}