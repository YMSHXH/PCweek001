package com.example.king.pcweek001.module;

import com.example.king.pcweek001.contract.ReqContract;
import com.example.king.pcweek001.net.OkHttpUtil;

import java.util.Map;

public class LoginModule implements ReqContract.ILoginModule {

    @Override
    public void setData(Map<String, String> params, String api, ReqContract.OKHttpCallBack okHttpCallBack) {
        OkHttpUtil.getInstance().toPost(params,api,okHttpCallBack);
    }
}
