package com.example.hp.mn3.mvp.presenter;

import com.example.hp.mn3.base.BasePresenter;
import com.example.hp.mn3.mvp.model.LoginBean;
import com.example.hp.mn3.mvp.model.LoginModel;
import com.example.hp.mn3.mvp.view.iview.IView;

/**
 * Created by hp on 2018/6/22.
 */

public class LoginPresenter extends BasePresenter<IView> {
        protected LoginModel loginModel;

    public LoginPresenter(IView view) {
        super(view);
    }

    @Override
    protected void onModel() {
       loginModel=new LoginModel();
    }
    public void logins(){
        loginModel.logins(new LoginModel.InClient() {
            @Override
            public void getSuccess(LoginBean loginBean) {
                if (view!=null){
                    view.getDataSuccess(loginBean);
                }
            }

            @Override
            public void getError(String error) {
                if (view!=null){
                    view.getDataError(error);
                }
            }
        });


    }



}
