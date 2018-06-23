package com.example.hp.mn3.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mn3.MyApp;
import com.example.hp.mn3.R;
import com.example.hp.mn3.mvp.model.LoginBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/6/22.
 */

public class LoginAdapter extends RecyclerView.Adapter{
              public static final int a=0;
              public static final int  b=1;
              List<LoginBean.DataBeanX.DataBean>list=new ArrayList<>();
              private View view;

    public LoginAdapter(List<LoginBean.DataBeanX.DataBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==a){
           view = View.inflate(parent.getContext(), R.layout.one, null);
            return new One(view);
        }else {
            view = View.inflate(parent.getContext(), R.layout.two, null);
        }
        return new Two(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case a:
               ((One)holder).oneTvName.setText(list.get(position).getTitle());
                ((One)holder).oneTvPing.setText(list.get(position).getViews()+"评论");
                ((One)holder).oneTvZan.setText(list.get(position).getViews_label()+"赞");
                 String image="http://365jia.cn/uploads/"+list.get(position).getPics().get(0);
                ImageLoader.getInstance().displayImage(image,((One)holder).oneIvImage, MyApp.getOptions());
                ((One)holder).oneTvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(v,position);
                    }
                });
                ((One)holder).oneIvImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(v,position);
                    }
                });

                break;
            case b:
                ((Two)holder).twoTvName.setText(list.get(position).getTitle());
                String image01="http://365jia.cn/uploads/"+list.get(position).getPics().get(0);
                ImageLoader.getInstance().displayImage(image01,((Two)holder).twoIvImage01, MyApp.getOptions());
                String image02="http://365jia.cn/uploads/"+list.get(position).getPics().get(1);
                ImageLoader.getInstance().displayImage(image02,((Two)holder).twoIvImage02, MyApp.getOptions());
                String image03="http://365jia.cn/uploads/"+list.get(position).getPics().get(2);
                ImageLoader.getInstance().displayImage(image03,((Two)holder).twoIvImage03, MyApp.getOptions());
                ((Two)holder).twoIvImage01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       onItemClickListener.OnItemClick(v,position);
                    }
                });
                ((Two)holder).twoIvImage02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(v,position);
                    }
                });
                ((Two)holder).twoIvImage03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(v,position);
                    }
                });
                ((Two)holder).twoTvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(v,position);
                    }
                });


                break;
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        List<String> pics = list.get(position).getPics();
        int size = pics.size();
        if (size==1){
            return a;
        }else {
            return b;
        }

    }

    //加载两个布局视图
       public class One extends RecyclerView.ViewHolder{
        private final ImageView oneIvImage;
        private final TextView oneTvName;
        private final TextView oneTvPing;
        private final TextView oneTvZan;

        public One(View view) {
            super(view);
            oneTvName = view.findViewById(R.id.one_tv_name);
            oneIvImage = view.findViewById(R.id.one_iv_image);
            oneTvPing = view.findViewById(R.id.one_tv_ping);
            oneTvZan = view.findViewById(R.id.one_tv_zan);
        }
    }
    public class Two extends RecyclerView.ViewHolder{
        private final ImageView twoIvImage01,twoIvImage02,twoIvImage03;
        private final TextView twoTvName;

        public Two(View view) {
            super(view);
            twoTvName = view.findViewById(R.id.two_tv_name);
            twoIvImage01 = view.findViewById(R.id.two_iv_image01);
            twoIvImage02 = view.findViewById(R.id.two_iv_image02);
            twoIvImage03 = view.findViewById(R.id.two_iv_image03);

        }
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }
    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    public interface OnItemLongClickListener{
        void OnItemLongClick(View view,int position);
    }

}
