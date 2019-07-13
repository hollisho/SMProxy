// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2005-7-16 19:21:48
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SSEventListener.java

package com.huawei.insa2.comm.sgip;

import java.net.Socket;

public interface SSEventListener
{

    public abstract void onConnect(Socket socket);
}