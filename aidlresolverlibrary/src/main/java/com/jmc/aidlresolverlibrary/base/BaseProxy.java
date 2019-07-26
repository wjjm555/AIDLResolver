package com.jmc.aidlresolverlibrary.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

import com.jmc.aidlresolverlibrary.IProxyAidlInterface;
import com.jmc.aidlresolverlibrary.IServiceAidlInterface;
import com.jmc.aidlresolverlibrary.model.MessageBean;
import com.jmc.aidlresolverlibrary.utils.AnalysisUtil;

import java.lang.ref.WeakReference;

public abstract class BaseProxy {

    public abstract String getAction();

    public abstract String getPackage();

    public abstract String getProxyKey();

    private MyHandler myHandler = new MyHandler(this);

    private IServiceAidlInterface serviceAidlInterface;

    private MyServiceConnection connection;

    private Context context;

    private ConnectListener listener;

    private AnalysisUtil analysisUtil;

    public BaseProxy(Context context, ConnectListener listener, Object... targets) {
        this.context = context;
        this.listener = listener;
        analysisUtil = new AnalysisUtil(targets);
        connect();
    }

    public void onDestroy() {
        if (context != null) context.unbindService(connection);
    }

    public void addTargets(Object... targets) {
        analysisUtil.addTargets(targets);
    }

    public void sendMessage(MessageBean bean) {
        if (serviceAidlInterface != null) {
            try {
                serviceAidlInterface.sendMessage(bean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    void connect() {
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(getAction());
            intent.setPackage(getPackage());
            context.bindService(intent, connection = new MyServiceConnection(), Context.BIND_AUTO_CREATE);
        }
    }

    AnalysisUtil getAnalysisUtil() {
        return analysisUtil;
    }

    Message transformationMessage(MessageBean bean) {
        Message message = Message.obtain();
        message.what = bean.getWhat();
        message.obj = bean.getTarget();
        message.setData(bean.getData());
        return message;
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceAidlInterface = IServiceAidlInterface.Stub.asInterface(service);
            if (serviceAidlInterface != null) {
                try {
                    serviceAidlInterface.setProxy(getProxyKey(), new AIDL());
                    if (listener != null) listener.onConnected();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (listener != null) listener.onDisconnected();
            serviceAidlInterface = null;
            connect();
        }
    }

    class AIDL extends IProxyAidlInterface.Stub {

        @Override
        public void sendMessage(MessageBean message) throws RemoteException {
            myHandler.sendMessage(transformationMessage(message));
        }
    }

    static class MyHandler extends Handler {

        private final WeakReference<BaseProxy> myProxy;

        private MyHandler(BaseProxy service) {
            super(Looper.getMainLooper());
            myProxy = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseProxy service = myProxy.get();
            if (service != null) {
                service.getAnalysisUtil().analysis(msg);
            }
            super.handleMessage(msg);
        }

    }

    public interface ConnectListener {
        void onConnected();

        void onDisconnected();
    }
}
