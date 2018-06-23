package com.example.hp.mn3.mvp.model;

import com.example.hp.mn3.http.HttpUtils;
import com.google.gson.Gson;

/**
 * Created by hp on 2018/6/22.
 */

public class LoginModel {
    private static final String TAG = "LoginModel";
    public void logins(final InClient inClient){
        String url="http://365jia.cn/news/api3/365jia/news/headline?page=1";
        HttpUtils.getHttpUtils().get(url, new HttpUtils.InOkCallback() {
            @Override
            public void getSuccess(String json) {
                LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);
                int code = loginBean.getCode();
                if (code==0){
                    if (inClient!=null){
                        inClient.getSuccess(loginBean);
                    }
                }else {
                    if (inClient!=null){
                        inClient.getError("失败");
                    }
                }
            }

            @Override
            public void getError(Exception error) {

            }
        });
    }

    public interface InClient{
        void getSuccess(LoginBean loginBean);
        void getError(String error);
    }

}
