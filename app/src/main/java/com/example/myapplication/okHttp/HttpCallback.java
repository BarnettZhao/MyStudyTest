package com.example.myapplication.okHttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

public abstract class HttpCallback<T> {
//    Type type;
//
//    static Type getSuperclassTypeParameter(Class<?> subclass) {
//        Type superclass = subclass.getGenericSuperclass();
//        if (superclass instanceof Class) {
//            throw new RuntimeException("Missing type parameter");
//        }
//        ParameterizedType parameterizedType = (ParameterizedType) superclass;
//        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
//    }
//
//    public HttpCallback() {
//        type = getSuperclassTypeParameter(getClass());
//    }

    public abstract void onRequestBefore(Request request);

    public abstract void onFailure(Request request, IOException e);

    public abstract void onSuccess(Response response);

    public abstract void onError(Response response, int errorCode, Exception e);
}
