package com.example.hp.mn3.mvp.view.activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hp.mn3.R;
import com.example.hp.mn3.base.BaseActivity;
import com.example.hp.mn3.mvp.model.LoginBean;
import com.example.hp.mn3.mvp.presenter.LoginPresenter;
import com.example.hp.mn3.mvp.view.adapter.LoginAdapter;
import com.example.hp.mn3.mvp.view.iview.IView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<LoginPresenter> implements IView{
    private static final String TAG = "MainActivity";
    private RecyclerView recycler;
    private LoginAdapter loginAdapter;
    private List<LoginBean.DataBeanX.DataBean>list=new ArrayList<>();

    @Override
    protected void initData() {
         prenter.logins();
    }

    @Override
    protected LoginPresenter onprenterview() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initLinsenter() {

    }

    @Override
    protected void initView() {
        recycler = findViewById(R.id.recycler_view);
        Log.d(TAG, "initView:cccccccccccccccccccccccccccc ");
    }

    @Override
    protected int getPrenterId() {
        return R.layout.activity_main;
    }


    @Override
    public void getDataSuccess(LoginBean loginBean) {
        List<LoginBean.DataBeanX.DataBean> data = loginBean.getData().getData();
        list.addAll(data);
        final LoginAdapter loginAdapter = new LoginAdapter(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
          recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(loginAdapter);
        loginAdapter.setOnItemClickListener(new LoginAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, final int position) {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "rotation", 720);
                alpha.setDuration(8000);
                alpha.start();


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除");
                builder.setMessage("确定删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                       loginAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
    }


    @Override
    public void getDataError(String error) {

    }

    @Override
    public Context context() {
        return this;
    }
}
