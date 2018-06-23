package com.example.hp.mn3.base;

/**
 * Created by hp on 2018/6/22.
 */

public abstract class BasePresenter<V extends BaseIView> {

       protected V view;

    public BasePresenter(V view) {
        this.view = view;
        onModel();
    }

    protected abstract void onModel();


}
