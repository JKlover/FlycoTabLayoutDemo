package com.example.st.flycotablayoutdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by st on 18-1-30.
 */

public abstract class BaseFragment extends Fragment {
    protected View view;
    protected Context mcontext;
    protected abstract int getLayoutID();//孩子加载视图

    protected abstract void initChildView();//初始化孩子的视图控件
    //初始化数据
    protected void initData() {
        judgeAndLoadData();//根据页面传URL解析相同的布局的数据//其他不同的可以不用实现着方法
    }

    protected void judgeAndLoadData() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutID(), container, false);
        return view;
        
//        return initView();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChildView();
        initData();
    }




//    protected void initData() {
//
//    }
}
