package com.example.king.pcweek001.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.king.pcweek001.R;
import com.example.king.pcweek001.adapter.XRecyclerViewAdapter;
import com.example.king.pcweek001.apis.GoodsApi;
import com.example.king.pcweek001.beans.ProductBean;
import com.example.king.pcweek001.contract.ReqContract;
import com.example.king.pcweek001.presenter.LoginPresenter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ReqContract.ILoginView {


    private int page = 1;
    private EditText mEdGoods;
    private XRecyclerView mReMessage;
    private Button mBtnFind;
    private LoginPresenter loginPresenter;
    private List<ProductBean.DataBean> list = new ArrayList<>();
    private ProductBean bean;
    private XRecyclerViewAdapter xRecyclerViewAdapter;
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    /**
     * 设置数据
     */
    private void initData() {

        loginPresenter = new LoginPresenter(this);
        //定义适配器
        xRecyclerViewAdapter = new XRecyclerViewAdapter(this);
        mReMessage.setAdapter(xRecyclerViewAdapter);
        //获取数据
        params = new HashMap<>();
        params.put("keywords","手机");
        setLoadData(params,page);

    }

    private void initView() {
        mEdGoods = (EditText) findViewById(R.id.ed_goods);
        mReMessage = (XRecyclerView) findViewById(R.id.re_message);
        //设置布局管理器
        mReMessage.setLayoutManager(new LinearLayoutManager(this));
        mBtnFind = (Button) findViewById(R.id.btn_find);

        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFind();
            }
        });

        // 可以设置是否开启加载更多/下拉刷新
        mReMessage.setLoadingMoreEnabled(true);
        // 添加刷新和加载更多的监听
        mReMessage.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
                page = 1;
                setLoadData(params,page);
            }

            //加载
            @Override
            public void onLoadMore() {
                setLoadData(params,page);
            }
        });
    }

    private void toFind() {
        page = 1;
        String ed_goodsname = mEdGoods.getText().toString();
        //获取数据
        params.put("keywords",ed_goodsname);
        setLoadData(params,page);
    }

    /**
     * 加载数据
     *
     */
    private void setLoadData(Map<String,String> params,int page){
        params.put("page",1 +"");
        //设置获取数据
        loginPresenter.goLogin(params,GoodsApi.USER_API);
    }

    @Override
    public void onSuccess(String res) {
        bean = new Gson().fromJson(res, ProductBean.class);
        if (bean != null) {
            list = bean.getData();
            //Toast.makeText(MainActivity.this, "总条目"+list.size(),Toast.LENGTH_SHORT).show();
            if(page ==1){
                xRecyclerViewAdapter.setList(list);
            } else {
                xRecyclerViewAdapter.addList(list);
            }
            mReMessage.loadMoreComplete();
            page++;
        }
    }

    @Override
    public void onFilure(String meg) {
        Toast.makeText( MainActivity.this,meg,Toast.LENGTH_SHORT).show();
    }
}
