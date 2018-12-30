package com.example.king.pcweek001.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class LoginActivity extends AppCompatActivity implements ReqContract.ILoginView {

    private EditText mEdUrl;
    private EditText mEdPwd;
    private Button mBtnLogin;
    private Button mBtnZh;
    private LoginPresenter loginPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();

    }

    private void initData() {
        loginPresenter = new LoginPresenter(this);

        sharedPreferences = getSharedPreferences("Login",0);
        edit = sharedPreferences.edit();

        boolean isOnlyOneLogin = sharedPreferences.getBoolean("isOnlyOneLogin", false);
        if (isOnlyOneLogin){
            goToLogin();
        }
    }

    private void initView() {
        mEdUrl = (EditText) findViewById(R.id.ed_url);
        mEdPwd = (EditText) findViewById(R.id.ed_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnZh = (Button) findViewById(R.id.btn_zh);
        
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        /**
         * 注册
         */
        mBtnZh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegActivity.class);
                startActivityForResult(intent,1000);
            }
        });

    }
    

    /**
     * 登录
     */
    private void Login() {
        String url = mEdUrl.getText().toString();
        String pwd = mEdPwd.getText().toString();
        Map<String,String> params = new HashMap<>();
        params.put("mobile",url);
        params.put("password",pwd);

        //调用
        loginPresenter.goLogin(params,LoginApi.LOGIN);

    }

    @Override
    public void onSuccess(String res) {
        LoginBean bean = new Gson().fromJson(res, LoginBean.class);
        Toast.makeText(this,bean.getMsg(),Toast.LENGTH_SHORT).show();
        //登录
        if ("登录成功".equals(bean.getMsg())){
            edit.putBoolean("isOnlyOneLogin",true);
            edit.commit();
            goToLogin();
        }


    }

    private void goToLogin() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFilure(String meg) {
        Toast.makeText(this,meg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 信使回调方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == 2000){
            String url = data.getStringExtra("url");
            String pwd = data.getStringExtra("pwd");
            mEdUrl.setText(url);
            mEdPwd.setText(pwd);
        }
    }
}
