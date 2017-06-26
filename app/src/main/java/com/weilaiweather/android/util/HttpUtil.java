package com.weilaiweather.android.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Lucky on 2017/6/26.
 * http请求类
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient cilent = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        cilent.newCall(request).enqueue(callback);
    }

}
