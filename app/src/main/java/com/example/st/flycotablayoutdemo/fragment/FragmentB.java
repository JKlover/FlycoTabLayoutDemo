package com.example.st.flycotablayoutdemo.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.flycotablayoutdemo.MmDetails;
import com.example.st.flycotablayoutdemo.R;
import com.example.st.flycotablayoutdemo.adapter.MZiAdapter;
import com.example.st.flycotablayoutdemo.base.BaseFragment;
import com.example.st.flycotablayoutdemo.model.MeiZiBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.st.flycotablayoutdemo.Api.A_URL;
import static com.example.st.flycotablayoutdemo.Api.BASE_PAGE;
import static com.example.st.flycotablayoutdemo.utils.MJsoupSpider.parseDataFromHtml;

/**
 * Created by st on 18-1-30.
 */

public class FragmentB extends BaseFragment {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private String title;
    private MZiAdapter mAdapter;
    private  ArrayList<MeiZiBean>meiZiBeans;
    private OkHttpClient mHttpClient;
    int  Page=1;
    public static FragmentB getInstance(String title) {
        FragmentB mf = new FragmentB();
        mf.title = title;
        return mf;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.setNewData(meiZiBeans);
        }
    };
//    @Override
//    protected View initView() {
//        View view=View.inflate(mcontext,R.layout.fragment_mian,null);
//
//        return view;
//    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getDataFromNet(A_URL);
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++Page;
                getDataFromNet(A_URL+BASE_PAGE+Page+"/");
//                Log.e("下一页链接为",getPager);
                refreshlayout.finishLoadmore();
            }
        });
    }

//    @Override
//    protected void initData() {
//        super.initData();
//        getDataFromNet(A_URL);
////        Log.e("下一页链接为",getPager);
//
//    }

    private void getDataFromNet(String url) {
        mHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        mHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //OkHttp3设置编码为GBK（默认编码是UTF-8）的两个步骤
//                byte[] responseBytes=response.body().bytes();
//
                String json = response.body().string();
//                String responseUrl = new String(responseBytes,"GBK");
////                Log.d("okHttp", responseUrl);
//                processData(responseUrl);
                meiZiBeans=parseDataFromHtml(json);
                handler.sendEmptyMessage(10);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_mian;
    }

    @Override
    protected void initChildView() {
        recyclerView=view.findViewById(R.id.rv_mian);
        mAdapter=new MZiAdapter(mcontext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //上拉刷新下拉加载更多
        refreshLayout = view.findViewById(R.id.srl_m_refresh);
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(mcontext).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(mcontext).setSpinnerStyle(SpinnerStyle.Scale));
//        mImgBeans=new ArrayList<>();
        meiZiBeans=new ArrayList<>();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MeiZiBean meiZiBean=meiZiBeans.get(position);
                Toast.makeText(mcontext,"点击了第"+position+"个item",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mcontext, MmDetails.class);
                intent.putExtra("DETAILS_URL",meiZiBean.getMeiziHref());
                startActivity(intent);
            }
        });

        refreshData();
    }
}
