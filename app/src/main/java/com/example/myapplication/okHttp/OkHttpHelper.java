package com.example.myapplication.okHttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {
    private Gson gson;
    private static OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpHelper() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance() {
//        if (okHttpClient == null) {
//            okHttpClient = new OkHttpHelper();
//        }
        return new OkHttpHelper();
    }

    public void httpGet(String url, HttpCallback httpCallback) {
        doRequest(requestBuild(url, null, HttpRequestType.GET), httpCallback);
    }

    public void httpPost(String url, Map<String, String> params, HttpCallback httpCallback) {
        doRequest(requestBuild(url, params, HttpRequestType.POST), httpCallback);
    }

    private void doRequest(final Request request, final HttpCallback httpCallback) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    callbackSuccess(httpCallback,response);
//                    if (httpCallback.type == String.class) {
//                        callbackSuccess(httpCallback, response, resultStr);
////                        httpCallback.onSuccess(response, resultStr);
//                    } else {
//                        try {
//                            Object o = gson.fromJson(resultStr, httpCallback.type);
////                            httpCallback.onSuccess(response, null);
//                            callbackSuccess(httpCallback, response, null);
//                        } catch (JsonIOException e) {
//                            callbackError(httpCallback, response, null);
//                        }
//                    }
                } else {
//                    httpCallback.onError(response, response.code(), null);
                    callbackError(httpCallback, response, null);
                }
            }
        });

    }

    private void callbackSuccess(final HttpCallback callback, final Response response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    private void callbackError(final HttpCallback callback, final Response response, final Object o) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), null);
            }
        });
    }


    //    创建request
    private Request requestBuild(String url, Map<String, String> params, HttpRequestType httpRequestType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
//        Get请求还是Post请求
        if (HttpRequestType.GET == httpRequestType) {
            builder.get();
        } else if (HttpRequestType.POST == httpRequestType) {
            builder.post(getRequestBody(params));
        }
        return builder.build();
    }

    private RequestBody getRequestBody(Map<String, String> params) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }
        return formBody.build();
    }

    //    http请求方法的枚举
    enum HttpRequestType {
        GET, POST
    }

}
