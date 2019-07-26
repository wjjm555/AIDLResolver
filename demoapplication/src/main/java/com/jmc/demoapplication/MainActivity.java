package com.jmc.demoapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jmc.aidlresolverlibrary.base.BaseProxy;
import com.jmc.aidlresolverlibrary.interfaces.AnalysisMethod;
import com.jmc.aidlresolverlibrary.interfaces.AnalysisParameter;
import com.jmc.aidlresolverlibrary.model.MessageBean;

public class MainActivity extends Activity implements BaseProxy.ConnectListener {
    public static String TAG = "MyApp_CJM";

    final String ACTION = "com.jmc.service.TestService", PACKAGE = "com.jmc.demoservice";

    private MyProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proxy = new MyProxy(this, this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proxy.onDestroy();
    }

    public void onClick(View view) {
//        MessageBean bean = new MessageBean.Builder().setTarget("testOne").putString("name", "haha").putInt("count", 110).build();
//        proxy.sendMessage(bean);

        Log.w(TAG, "Start:" + System.currentTimeMillis());

        MessageBean bean1 = new MessageBean.Builder().setTarget("testTwo").putString("test", "wahaha").build();
        proxy.sendMessage(bean1);
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @AnalysisMethod()
    public void testOne(int count, String name) {
        Log.w(TAG, "Analysis App Success one !!!!!!!!!!!!!" + count + name);
    }

    @AnalysisMethod(true)
    public void testTwo(@AnalysisParameter("test") String name) {
        Log.w(TAG, "Analysis App Success two !!!!!!!!!!!!!" + name);
    }

    class MyProxy extends BaseProxy {

        public MyProxy(Context context, ConnectListener listener, Object... targets) {
            super(context, listener, targets);
        }

        @Override
        public String getAction() {
            return ACTION;
        }

        @Override
        public String getPackage() {
            return PACKAGE;
        }

        @Override
        public String getProxyKey() {
            return "key";
        }
    }
}
