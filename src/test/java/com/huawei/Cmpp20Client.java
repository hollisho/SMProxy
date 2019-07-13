package com.huawei;

import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cmpp20Client {

    private Args cargs;
    private MySMProxy myProxy;

    private String name;
    private String password;
    private Date valid_Time;


    public Cmpp20Client(String name,String password) throws IOException {
        this.name = name;
        this.password = password;
        this.myProxy = getProxy();
        this.valid_Time = new Date(System.currentTimeMillis() + (long) 0xa4cb800);
    }

    private MySMProxy getProxy() throws IOException {
        cargs = new Cfg("SMProxy.xml",false).getArgs("cmpp20");
        cargs.set("source-addr",this.name);
        cargs.set("shared-secret",this.password);
        myProxy = new MySMProxy(this,cargs);
        return myProxy;
    }

    public static List<byte[]> getLongByte (String message){

        List<byte[]> list =  new ArrayList<byte[]>();

        try {
            byte[] messageUCS2;
            messageUCS2 = message.getBytes("UnicodeBigUnmarked");
            int messageUCS2Len  = messageUCS2.length;// 长短信长度
            int maxMessageLen = 140;

            if(messageUCS2Len > maxMessageLen) {// 长短信发送
                //int tpUdhi = 1;
                //长消息是1.短消息是0
                //int msgFmt = 0x08;//长消息不能用GBK
                int messageUCS2Count = messageUCS2Len / (maxMessageLen - 6) + 1;//
                byte[] tp_udhiHead = new byte[6];
                tp_udhiHead[0] = 0x05;
                tp_udhiHead[1] = 0x00;
                tp_udhiHead[2] = 0x03;
                tp_udhiHead[3] = 0x0A;
                tp_udhiHead[4] = (byte) messageUCS2Count;
                tp_udhiHead[5] = 0x01;// 默认为第一条
                for (int i = 0; i < messageUCS2Count; i++) {
                    tp_udhiHead[5] = (byte) (i + 1);
                    byte[] msgContent;
                    if (i != messageUCS2Count - 1) {// 不为最后一条
                        msgContent=byteAdd(tp_udhiHead, messageUCS2,i*(maxMessageLen-6),(i+1)*(maxMessageLen-6));
                        list.add(msgContent);
                    } else {
                        msgContent=byteAdd(tp_udhiHead,messageUCS2, i*(maxMessageLen-6),messageUCS2Len);
                        list.add(msgContent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return
                list;
    }

    private static byte[] byteAdd(byte[] tpUdhiHead, byte[] messageUCS2, int i, int j) {
        byte[] msgb = new byte[j-i+6];
        System.arraycopy(tpUdhiHead,0,msgb,0,6);
        System.arraycopy(messageUCS2,i,msgb,6,j-i);
        return msgb;
    }

    public void Sendshortsms(String[] mobiles, String content,String channelNumber,int timeout){
        try
        {
            //发送短信
            CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(
                    1,
                    1,
                    1,
                    0,
                    "1234",
                    0,
                    "",
                    0,
                    0,
                    15,
                    "4321",
                    "01",
                    "0000",
                    this.valid_Time,
                    null,
                    channelNumber,
                    mobiles,
                    content.getBytes("GBK"),
                    ""
            );
            CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy.send(submitMsg);
            if (submitRepMsg.getResult() == 0) {
                System.out.println("发送短信成功 msgid:" + submitRepMsg.getMsgId().toString()+" SequenceId:" + submitRepMsg.getSequenceId());
            }
            Thread.sleep(timeout);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Sendlongsms(String[] mobiles, String content,String channelNumber,int timeout){
        try
        {
            //发送短信
            CMPPSubmitMessage submitMsg =null;
            List<byte[]> list = getLongByte(content);
            for(byte[] msg:list){
                submitMsg = new CMPPSubmitMessage(
                        1,
                        1,
                        1,
                        0,
                        "1234",
                        0,
                        "",
                        0,
                        1,
                        8,
                        "4321",
                        "01",
                        "0000",
                        this.valid_Time,
                        null,
                        channelNumber,
                        mobiles,
                        msg,
                        ""
                );
                CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy.send(submitMsg);
                if (submitRepMsg.getResult() == 0) {
                    System.out.println("发送短信成功 msgid:" + submitRepMsg.getMsgId().toString()+" SequenceId:" + submitRepMsg.getSequenceId());
                }
            }
            Thread.sleep(timeout);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void ProcessRecvDeliverMsg(CMPPMessage msg) {
        CMPPDeliverMessage deliverMsg = (CMPPDeliverMessage) msg;
        if (deliverMsg.getRegisteredDeliver() == 0)
            try {
                // 编码方式
                if (deliverMsg.getMsgFmt() == 8) {
                    System.out.println("deliverMsg.getMsgFmt() == 8");
                    System.out.println(String.valueOf(String.valueOf((new StringBuffer(
                            "接收消息: 主叫号码=")).append(";内容=").append(
                            new String(deliverMsg.getMsgContent(),"UTF-16BE")))));
                }
                // 编码方式GBK
                else {
                    System.out.println(String.valueOf(String.valueOf((new StringBuffer(
                            "接收消息: 主叫号码=")).append(
                            deliverMsg.getSrcterminalId()).append(";内容=")
                            .append(new String(deliverMsg.getMsgContent())))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(
                    "收到状态报告消息： stat="))
                    .append(new String(deliverMsg.getStat())).append(
                            "dest_termID=").append(
                            new String(deliverMsg.getDestTerminalId())).append(
                            ";destterm=").append(
                            new String(deliverMsg.getDestnationId())).append(
                            ";serviceid=").append(
                            new String(deliverMsg.getServiceId())).append(
                            ";tppid=").append(deliverMsg.getTpPid()).append(
                            ";tpudhi=").append(deliverMsg.getTpUdhi()).append(
                            ";msgfmt").append(deliverMsg.getMsgFmt()).append(
                            //";内容").append(new String(deliverMsg.getMsgContent())).append(
                            ";srctermid=").append(
                            new String(deliverMsg.getSrcterminalId())).append(
                            ";deliver=").append(
                            deliverMsg.getRegisteredDeliver()))));
        }
    }

    public void Terminate() {
        myProxy.close();
        myProxy = null;
    }

    public void Close() {
        String stateDesc = myProxy.getConnState();
        System.out.println(stateDesc);
        myProxy.close();
    }

    public static void main( String[] args ) throws IOException, InterruptedException {

        // cmpp20 测试
        String name = "XXXX";
        String psword = "XXXXX";

        Long phone = 13100000000L;
        String [] mobiles = new String[1];
        String sgin = "【XXXX】";
        String content = "XXXX" ;
        String lcontent = "XXXXXXXXXXXXXXXXXX";
        String channelNumber ="9999";
        int timeout = 1000*10;
        String timestr =(new SimpleDateFormat("HHmmss")).format(new Date());
        int icount=1;

        Cmpp20Client smsclient = new Cmpp20Client(name,psword);
        for(int i=0;i<icount;i++) {
            mobiles[0]=phone.toString();
            smsclient.Sendshortsms(mobiles, sgin + timestr + content, channelNumber, timeout);
//            smsclient.Sendlongsms(mobiles, sgin +lcontent, channelNumber, timeout);
            //phone++;
        }
    }
}
