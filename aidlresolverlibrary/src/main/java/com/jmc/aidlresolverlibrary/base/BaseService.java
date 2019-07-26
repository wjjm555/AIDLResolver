package com.jmc.aidlresolverlibrary.base;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

import com.jmc.aidlresolverlibrary.IProxyAidlInterface;
import com.jmc.aidlresolverlibrary.IServiceAidlInterface;
import com.jmc.aidlresolverlibrary.model.MessageBean;
import com.jmc.aidlresolverlibrary.utils.AnalysisUtil;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseService extends Service {

    protected abstract String getPermission();

    private MyHandler myHandler = new MyHandler(this);

    private Map<String, IProxyAidlInterface> proxies = new ConcurrentHashMap<>();

    private AnalysisUtil analysisUtil = new AnalysisUtil();

    @Override
    public IBinder onBind(Intent intent) {
        return new AIDL();
    }

    public void addTargets(Object... targets) {
        analysisUtil.addTargets(targets);
    }

    public void sendMessage(MessageBean bean) {
        for (IProxyAidlInterface proxy : proxies.values()) {
            if (proxy != null) {
                try {
                    proxy.sendMessage(bean);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String client, MessageBean bean) {
        IProxyAidlInterface proxy = proxies.get(client);
        if (proxy != null) {
            try {
                proxy.sendMessage(bean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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

    class AIDL extends IServiceAidlInterface.Stub {

        @Override
        public void setProxy(String key, IProxyAidlInterface proxy) throws RemoteException {
            proxies.put(key, proxy);
        }

        @Override
        public void sendMessage(MessageBean message) throws RemoteException {
            myHandler.sendMessage(transformationMessage(message));
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (!TextUtils.isEmpty(getPermission()) && checkCallingPermission(getPermission()) == PackageManager.PERMISSION_GRANTED) {
                return super.onTransact(code, data, reply, flags);
            }
            return false;
        }
    }

    static class MyHandler extends Handler {

        private final WeakReference<BaseService> myService;

        private MyHandler(BaseService service) {
            super(Looper.getMainLooper());
            myService = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseService service = myService.get();
            if (service != null) {
                service.getAnalysisUtil().analysis(msg);
            }
            super.handleMessage(msg);
        }

    }
}
