package com.example.king.pcweek001.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.king.pcweek001.R;
import com.example.king.pcweek001.apis.LoginApi;
import com.example.king.pcweek001.beans.LoginBean;
import com.example.king.pcweek001.contract.ReqContract;
import com.example.king.pcweek001.presenter.LoginPresenter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity implements ReqContract.ILoginView {

    private EditText mEdUrl;
    private EditText mEdPwd;
    /**
     * 注册
     */
    private Button mBtnReg;
    private LoginPresenter loginPresenter;
    private String url;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        initData();
    }

    private void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        mEdUrl = (EditText) findViewById(R.id.ed_url);
        mEdPwd = (EditText) findViewById(R.id.ed_pwd);
        mBtnReg = (Button) findViewById(R.id.btn_reg);

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goReg();
            }
        });

    }

    /**
     * 注册
     */
    private void goReg() {
        url = mEdUrl.getText().toString();
        pwd = mEdPwd.getText().toString();
        Map<String,String> params = new HashMap<>();
        params.put("mobile", url);
        params.put("password", pwd);

        //调用
        loginPresenter.goLogin(params,LoginApi.Reg);

    }

    @Override
    public void onSuccess(String res) {
        LoginBean bean = new Gson().fromJson(res, LoginBean.class);
        Toast.makeText(this,bean.getMsg(),Toast.LENGTH_SHORT).show();
        if ("注册成功".equals(bean.getMsg())){
            Intent intent = new Intent();
            intent.putExtra("url",url);
            intent.putExtra("pwd",pwd);
            setResult(2000,intent);
            finish();
        }
    }

    @Override
    public void onFilure(String meg) {
        Toast.makeText(this,meg,Toast.LENGTH_SHORT).show();
    }
}
