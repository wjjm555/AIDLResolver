package com.jmc.aidlresolverlibrary;

import com.jmc.aidlresolverlibrary.IProxyAidlInterface;
import com.jmc.aidlresolverlibrary.model.MessageBean;

interface IServiceAidlInterface {

    void setProxy(String key,IProxyAidlInterface proxy);

    void sendMessage(in MessageBean message);
}
