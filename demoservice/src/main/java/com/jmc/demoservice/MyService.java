package com.jmc.demoservice;

import com.jmc.aidlresolverlibrary.base.BaseService;

public class MyService extends BaseService {
    public MyService() {
    }

    @Override
    protected String getPermission() {
        return "com.jmc.ACCESS_AIDL";
    }

}
