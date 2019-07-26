package com.jmc.aidlresolverlibrary;

import com.jmc.aidlresolverlibrary.model.MessageBean;

interface IProxyAidlInterface {

    void sendMessage(in MessageBean message);

}
