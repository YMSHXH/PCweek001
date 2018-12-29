package com.example.king.pcweek001.contract;

import java.util.Map;

public interface ReqContract {

    /**
     * OKHttpCallBack
     */
    interface OKHttpCallBack{
        void onSuccess(String res);
        void onFilure(String meg);
    }

    /**
     * Module层请求数据
     */
    interface ILoginModule{
        void setData(Map<String,String> params, String api, OKHttpCallBack okHttpCallBack);
    }

    /**
     * View
     */
    interface ILoginView{
        void onSuccess(String res);
        void onFilure(String meg);
    }
}
