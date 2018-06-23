package com.example.hp.mn3.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hp on 2018/6/22.
 */

public abstract class BaseActivity<P extends BasePresenter>extends AppCompatActivity {
         protected P prenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getPrenterId());
        prenter=onprenterview();
        initView();
        initLinsenter();
        initData();
    }

    protected abstract void initData();
    protected abstract P onprenterview();
    protected abstract void initLinsenter();
    protected abstract void initView();
    protected abstract int getPrenterId();



}
