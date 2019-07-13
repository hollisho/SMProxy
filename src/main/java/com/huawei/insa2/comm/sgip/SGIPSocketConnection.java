// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:46
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SGIPSocketConnection.java

package com.huawei.insa2.comm.sgip;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.util.*;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SGIPSocketConnection extends PLayer
{

    public SGIPSocketConnection(Args args)
    {
        super(null);
        errorTime = new Date();
        port = -1;
        localPort = -1;
        init(args);
    }

    protected SGIPSocketConnection()
    {
        super(null);
        errorTime = new Date();
        port = -1;
        localPort = -1;
    }

    protected void init(Args args)
    {
        resource = getResource();
        initResource();
        error = NOT_INIT;
        setAttributes(args);
        class ReceiveThread extends WatchThread
        {

            public void task()
            {
                try
                {
                    if(error == null)
                    {
                        PMessage m = in.read();
                        if(m != null && m != null)
                            onReceive(m);
                    } else
                    {
                        if(error != SGIPSocketConnection.NOT_INIT)
                            try
                            {
                                Thread.sleep(reconnectInterval);
                            }
                            catch(InterruptedException interruptedexception) { }
                        connect();
                    }
                }
                catch(IOException ioexception) { }
            }

            public ReceiveThread()
            {
                super(String.valueOf(String.valueOf(name)).concat("-receive"));
            }
        }

        receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    protected void init(Args args, Socket socket)
    {
        resource = getResource();
        initResource();
        error = NOT_INIT;
        if(socket != null)
        {
            this.socket = socket;
            try
            {
                out = getWriter(this.socket.getOutputStream());
                in = getReader(this.socket.getInputStream());
                setError(null);
            }
            catch(IOException ex)
            {
                setError(String.valueOf(CONNECT_ERROR) + String.valueOf(explain(ex)));
            }
            if(args != null)
                setAttributes1(args);
            class ReceiveThread1 extends WatchThread
            {

                public void task()
                {
                    try
                    {
                        if(error == null)
                        {
                            PMessage m = in.read();
                            if(m != null && m != null)
                                onReceive(m);
                        }
                    }
                    catch(IOException ex)
                    {
                        setError(explain(ex));
                        if(error == SGIPSocketConnection.RECEIVE_TIMEOUT)
                        {
                            setError(null);
                            onReadTimeOut();
                        }
                    }
                }

            public ReceiveThread1()
            {
                super(String.valueOf(String.valueOf(name)).concat("-receive"));
            }
            }

            receiveThread = new ReceiveThread1();
            receiveThread.start();
        }
    }

    protected void onReadTimeOut()
    {
        throw new UnsupportedOperationException("Not implement");
    }

    public void setAttributes(Args args)
    {
        if(name != null && name.equals(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(String.valueOf(host))))).append(':').append(port)))))
            name = null;
        String oldHost = host;
        int oldPort = port;
        String oldLocalHost = localHost;
        int oldLocalPort = localPort;
        host = args.get("host", null);
        port = args.get("port", -1);
        localHost = args.get("local-host", null);
        localPort = args.get("local-port", -1);
        name = args.get("name", null);
        if(name == null)
            name = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(host)))).append(':').append(port)));
        readTimeout = 0;
        if(socket != null)
            try
            {
                socket.setSoTimeout(readTimeout);
            }
            catch(SocketException socketexception) { }
        heartbeatInterval = 0;
        transactionTimeout = 1000 * args.get("transaction-timeout", -1);
        if(error == null && host != null && port != -1 && (!host.equals(oldHost) || port != port || !host.equals(oldHost) || port != port))
        {
            setError(resource.get("comm/need-reconnect"));
            receiveThread.interrupt();
        }
    }

    public void setAttributes1(Args args)
    {
        if(name != null && name.equals(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(String.valueOf(host))))).append(':').append(port)))))
            name = null;
        host = args.get("host", null);
        port = args.get("port", -1);
        localHost = args.get("local-host", null);
        localPort = args.get("local-port", -1);
        name = args.get("name", null);
        if(name == null)
            name = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(host)))).append(':').append(port)));
        readTimeout = 1000 * args.get("read-timeout", readTimeout / 1000);
        if(socket != null)
            try
            {
                socket.setSoTimeout(readTimeout);
            }
            catch(SocketException socketexception) { }
        heartbeatInterval = 0;
        transactionTimeout = 1000 * args.get("transaction-timeout", -1);
    }

    public void send(PMessage message)
        throws PException
    {
        if(error != null)
            throw new PException(String.valueOf(SEND_ERROR) + String.valueOf(getError()));
        try
        {
            out.write(message);
            fireEvent(new PEvent(8, this, message));
        }
        catch(PException ex)
        {
            fireEvent(new PEvent(16, this, message));
            setError(String.valueOf(SEND_ERROR) + String.valueOf(explain(ex)));
            throw ex;
        }
        catch(Exception ex)
        {
            fireEvent(new PEvent(16, this, message));
            setError(String.valueOf(SEND_ERROR) + String.valueOf(explain(ex)));
        }
    }

    public String getName()
    {
        return name;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public int getReconnectInterval()
    {
        return reconnectInterval / 1000;
    }

    public String toString()
    {
        return String.valueOf(String.valueOf((new StringBuffer("PShortConnection:")).append(name).append('(').append(host).append(':').append(port).append(')')));
    }

    public int getReadTimeout()
    {
        return readTimeout / 1000;
    }

    public boolean available()
    {
        return error == null;
    }

    public String getError()
    {
        return error;
    }

    public Date getErrorTime()
    {
        return errorTime;
    }

    public synchronized void close()
    {
        try
        {
            if(socket != null)
            {
                socket.close();
                in = null;
                out = null;
                socket = null;
                if(heartbeatThread != null)
                    heartbeatThread.kill();
                receiveThread.kill();
            }
        }
        catch(Exception exception) { }
        setError(NOT_INIT);
    }

    protected synchronized void connect()
    {
        if(error == NOT_INIT)
            error = CONNECTING;
        else
        if(error == null)
            error = RECONNECTING;
        errorTime = new Date();
        if(socket != null)
            try
            {
                socket.close();
            }
            catch(IOException ioexception) { }
        try
        {
            if(port <= 0 || port > 65535)
            {
                setError(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(PORT_ERROR)))).append("port:").append(port))));
                return;
            }
            if(localPort < -1 || localPort > 65535)
            {
                setError(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(PORT_ERROR)))).append("local-port:").append(localPort))));
                return;
            }
            if(localHost != null)
            {
                boolean isConnected = false;
                InetAddress localAddr = InetAddress.getByName(localHost);
                if(localPort == -1)
                {
                    for(int p = (int)(Math.random() * (double)64500); p < 0xdc758; p += 13)
                        try
                        {
                            socket = new Socket(host, port, localAddr, 1025 + p % 64500);
                            isConnected = true;
                            break;
                        }
                        catch(IOException ioexception1) { }
                        catch(SecurityException securityexception) { }

                    if(!isConnected)
                        throw new SocketException("Can not find an avaliable local port");
                } else
                {
                    socket = new Socket(host, port, localAddr, localPort);
                }
            } else
            {
                socket = new Socket(host, port);
            }
            socket.setSoTimeout(readTimeout);
            out = getWriter(socket.getOutputStream());
            in = getReader(socket.getInputStream());
            setError(null);
        }
        catch(IOException ex)
        {
            setError(String.valueOf(CONNECT_ERROR) + String.valueOf(explain(ex)));
        }
    }

    protected void setError(String desc)
    {
        if(error == null && desc == null || desc != null && desc.equals(error))
            return;
        error = desc;
        errorTime = new Date();
        if(desc == null)
            desc = CONNECTED;
    }

    protected abstract PWriter getWriter(OutputStream outputstream);

    protected abstract PReader getReader(InputStream inputstream);

    protected abstract Resource getResource();

    protected void heartbeat()
        throws IOException
    {
    }

    public void initResource()
    {
        NOT_INIT = resource.get("comm/not-init");
        CONNECTING = resource.get("comm/connecting");
        RECONNECTING = resource.get("comm/reconnecting");
        CONNECTED = resource.get("comm/connected");
        HEARTBEATING = resource.get("comm/heartbeating");
        RECEIVEING = resource.get("comm/receiveing");
        CLOSEING = resource.get("comm/closeing");
        CLOSED = resource.get("comm/closed");
        UNKNOWN_HOST = resource.get("comm/unknown-host");
        PORT_ERROR = resource.get("comm/port-error");
        CONNECT_REFUSE = resource.get("comm/connect-refused");
        NO_ROUTE_TO_HOST = resource.get("comm/no-route");
        RECEIVE_TIMEOUT = resource.get("comm/receive-timeout");
        CLOSE_BY_PEER = resource.get("comm/close-by-peer");
        RESET_BY_PEER = resource.get("comm/reset-by-peer");
        CONNECTION_CLOSED = resource.get("comm/connection-closed");
        COMMUNICATION_ERROR = resource.get("comm/communication-error");
        CONNECT_ERROR = resource.get("comm/connect-error");
        SEND_ERROR = resource.get("comm/send-error");
        RECEIVE_ERROR = resource.get("comm/receive-error");
        CLOSE_ERROR = resource.get("comm/close-error");
    }

    protected String explain(Exception ex)
    {
        String msg = ex.getMessage();
        if(msg == null)
            msg = "";
        if(ex instanceof PException)
            return ex.getMessage();
        if(ex instanceof EOFException)
            return CLOSE_BY_PEER;
        if(msg.indexOf("Connection reset by peer") != -1)
            return RESET_BY_PEER;
        if(msg.indexOf("SocketTimeoutException") != -1)
            return RECEIVE_TIMEOUT;
        if(ex instanceof SocketTimeoutException)
            return RECEIVE_TIMEOUT;
        if(ex instanceof NoRouteToHostException)
            return NO_ROUTE_TO_HOST;
        if(ex instanceof ConnectException)
            return CONNECT_REFUSE;
        if(ex instanceof UnknownHostException)
            return UNKNOWN_HOST;
        if(msg.indexOf("errno: 128") != -1)
        {
            return NO_ROUTE_TO_HOST;
        } else
        {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    protected static String NOT_INIT;
    protected static String CONNECTING;
    protected static String RECONNECTING;
    protected static String CONNECTED;
    protected static String HEARTBEATING;
    protected static String RECEIVEING;
    protected static String CLOSEING;
    protected static String CLOSED;
    protected static String UNKNOWN_HOST;
    protected static String PORT_ERROR;
    protected static String CONNECT_REFUSE;
    protected static String NO_ROUTE_TO_HOST;
    protected static String RECEIVE_TIMEOUT;
    protected static String CLOSE_BY_PEER;
    protected static String RESET_BY_PEER;
    protected static String CONNECTION_CLOSED;
    protected static String COMMUNICATION_ERROR;
    protected static String CONNECT_ERROR;
    protected static String SEND_ERROR;
    protected static String RECEIVE_ERROR;
    protected static String CLOSE_ERROR;
    private String error;
    protected Date errorTime;
    protected String name;
    protected String host;
    protected int port;
    protected String localHost;
    protected int localPort;
    protected int heartbeatInterval;
    protected PReader in;
    protected PWriter out;
    protected static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    protected int readTimeout;
    protected int reconnectInterval;
    protected Socket socket;
    protected WatchThread heartbeatThread;
    protected WatchThread receiveThread;
    protected int transactionTimeout;
    protected Resource resource;


}