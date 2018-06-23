package com.example.hp.mn3.mvp.view.iview;

import com.example.hp.mn3.base.BaseIView;
import com.example.hp.mn3.mvp.model.LoginBean;

/**
 * Created by hp on 2018/6/22.
 */

public interface IView extends BaseIView{
    void getDataSuccess(LoginBean loginBean);
    void getDataError(String error);

}
