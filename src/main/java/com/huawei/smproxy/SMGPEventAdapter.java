// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMGPSMProxy.java

package com.huawei.smproxy;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smgp.SMGPConnection;
import com.huawei.insa2.comm.smgp.SMGPTransaction;
import com.huawei.insa2.comm.smgp.message.*;

// Referenced classes of package com.huawei.smproxy:
//            SMGPSMProxy

class SMGPEventAdapter extends PEventAdapter
{

    private SMGPSMProxy smProxy;
    private SMGPConnection conn;

    public SMGPEventAdapter(SMGPSMProxy smProxy)
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
        SMGPTransaction t = (SMGPTransaction)child;
        SMGPMessage msg = t.getResponse();
        //定义响应消息
        SMGPMessage resmsg = null;
        if(msg.getRequestId() == 6)//对下发终止应用层连接的响应
        {
            resmsg = new SMGPExitRespMessage();
            smProxy.onTerminate();
        } else
        if(msg.getRequestId() == 4)
            resmsg = new SMGPActiveTestRespMessage();
        else
        if(msg.getRequestId() == 3)//对主动下发的短信消息的响应
        {
            SMGPDeliverMessage tmpmes = (SMGPDeliverMessage)msg;
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
        if(msg.getRequestId() == 6)
            conn.close();
    }
}
