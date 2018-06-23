package com.example.hp.mn3.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.LoggingPermission;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by hp on 2018/6/21.
 */

public class HttpUtils {
    private static HttpUtils httpUtils=new HttpUtils();
    private OkHttpClient mOkHttpClien;
    private final Handler handler;
    private static final String TAG = "HttpUtils";
    private HttpUtils() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Map<String,String> map = new HashMap<>();
          map.put("source","android");
        PublicPramInterceptor publicPramInterceptor = new PublicPramInterceptor(map);
        //创建一个主线程的handler
        handler = new Handler(Looper.getMainLooper());
        mOkHttpClien = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(publicPramInterceptor)
                .build();
    }

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    return httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    //get封装
    public void get(String url, final InOkCallback inokCallback) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                 if (inokCallback!=null){
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             inokCallback.getError(e);
                         }
                     });
                 }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (inokCallback!=null){
                    final String json = response.body().string();
                        if (response!=null&&response.isSuccessful()){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    inokCallback.getSuccess(json);
                                    Log.d(TAG, "run: httputils===="+json);
                                }
                            });
                        }
                }
            }
        });
    }
    //post封装
    public void post(String url,Map<String,String>map, final InOkCallback inokCallback) {
        FormBody.Builder formbody = new FormBody.Builder();
        for (String key:map.keySet()){
            formbody.add(key,map.get(key));
        }
        FormBody build = formbody.build();
        Request request = new Request.Builder()
                .post(build)
                .url(url)
                .build();
        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                if (inokCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            inokCallback.getError(e);
                        }
                    });
                }
            }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
                if (inokCallback!=null){
                    final String json = response.body().string();
                    if (response!=null&&response.isSuccessful()){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                inokCallback.getSuccess(json);
                                Log.d(TAG, "run: ppppppppppppppppppppppppppp====================="+json);
                            }
                        });
                    }
                }
            }
        });
    }



   //创建一个接口
    public interface InOkCallback {
        void getSuccess(String json);
        void getError(Exception error);
    }
    //自定义一个拦截器
    private class PublicPramInterceptor implements Interceptor {
        Map<String,String>pubmap=new HashMap<>();
        public PublicPramInterceptor(Map<String, String> map) {
            this.pubmap=pubmap;
        }
        //请求相应的时候
        @Override
        public Response intercept(Chain chain) throws IOException {
            //1.应该先拿到原来的request
            Request oldrequest = chain.request();
            //2.拿到请求的url
            String url = oldrequest.url().toString();
            //3.判断这个方法是get还是post请求
            if (oldrequest.method().equalsIgnoreCase("GET")){
                if (pubmap!=null&&pubmap.size()>0){
                    StringBuilder stringBuilder = new StringBuilder();
                    //拼接公共请求参数
                    for (Map.Entry<String,String>entry:pubmap.entrySet()){
                        stringBuilder.append("&"+entry.getKey()+"="+entry.getValue());
                    }
                    url=stringBuilder.toString();
                    //如果之前的ulr没有？号，我们就需要手动田间一个
                    if (!url.contains("?")){
                        url= url.replaceFirst("&", "?");
                    }
                    //依据原来的request构造一个新的request
                    Request newrequest = oldrequest.newBuilder()
                            .url(url)
                            .build();
                }
                return chain.proceed(oldrequest);
            }else {
                if (pubmap!=null&&pubmap.size()>0){
                    RequestBody body = oldrequest.body();
                    if (body!=null&&body instanceof FormBody){
                        FormBody formvody = (FormBody) body;
                        //把原来的body里面的参数添加到新的body中
                        FormBody.Builder builder = new FormBody.Builder();
                        //为了防止重复添加相同的key和value
                        Map<String,String> postmap = new HashMap<>();
                        for (int i = 0; i < formvody.size(); i++) {
                            builder.add(formvody.encodedName(i),formvody.encodedValue(i));
                            postmap.put(formvody.encodedName(i),formvody.encodedValue(i));
                        }
                        //把公共请求添加到新的body中
                        for (Map.Entry<String,String>entry:postmap.entrySet()){
                            if (!postmap.containsKey(entry.getKey())){
                                builder.add(entry.getKey(),entry.getValue());
                            }
                        }
                        FormBody newformbody = builder.build();
                        //依据原来的request构造一个新的request
                        Request newrquest = oldrequest.newBuilder()
                                .post(newformbody)
                                .build();
                        return chain.proceed(newrquest);
                    }
                }
            }
            return chain.proceed(oldrequest);
        }
    }

}
