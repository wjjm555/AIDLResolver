package com.jmc.aidlresolverlibrary.utils;

import android.os.Message;
import android.text.TextUtils;

import com.jmc.aidlresolverlibrary.interfaces.AnalysisMethod;
import com.jmc.aidlresolverlibrary.interfaces.AnalysisParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnalysisUtil {

    private List<Object> targets = new CopyOnWriteArrayList<>();

    public AnalysisUtil(Object... objects) {
        addTargets(objects);
    }

    public void addTargets(Object... objects) {
        targets.addAll(Arrays.asList(objects));
    }

    public void analysis(Message message) {
        try {
            if (message != null) {
                String methodName = (String) message.obj;
                if (!TextUtils.isEmpty(methodName)) {
                    for (Object target : targets) {
                        Class clazz = target.getClass();
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            if (methodName.equals(method.getName())) {
                                AnalysisMethod analysis = method.getAnnotation(AnalysisMethod.class);
                                if (analysis != null) {
                                    boolean needParameter = analysis.value();
                                    int paramCount = analysis.paramCount();
                                    Parameter[] parameters = method.getParameters();
                                    Object[] objects = new Object[parameters.length];
                                    if (paramCount < 0 || paramCount == parameters.length) {
                                        for (int i = 0; i < parameters.length; ++i) {
                                            Parameter parameter = parameters[i];
                                            String name = parameter.getName();
                                            if (needParameter) {
                                                AnalysisParameter analysisParameter = parameter.getAnnotation(AnalysisParameter.class);
                                                if (analysisParameter != null)
                                                    name = analysisParameter.value();
                                            }
                                            objects[i] = message.getData().get(name);
                                        }
                                    }
                                    method.setAccessible(true);
                                    method.invoke(target, objects);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
