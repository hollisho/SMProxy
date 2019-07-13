package com.huawei;

import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SMProxy;

public class MySMProxy extends SMProxy {

    private Cmpp20Client cmpp20Client ;

    public MySMProxy(Cmpp20Client cmpp20Client, Args args)
    {
        super(args);
        this.cmpp20Client = null;
        this.cmpp20Client = cmpp20Client;
    }

    /**
     * 对ISMG主动下发的消息的处理。
     * @param msg 收到的消息
     * @return 返回的相应消息。
     */
    public CMPPMessage onDeliver(CMPPDeliverMessage msg)
    {
        cmpp20Client.ProcessRecvDeliverMsg(msg);
        return super.onDeliver(msg);
    }

    public void OnTerminate()
    {
        cmpp20Client.Terminate();
    }
}
