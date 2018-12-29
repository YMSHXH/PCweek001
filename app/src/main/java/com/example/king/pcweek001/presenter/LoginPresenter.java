package com.example.king.pcweek001.presenter;

import com.example.king.pcweek001.contract.ReqContract;
import com.example.king.pcweek001.module.LoginModule;

import java.util.Map;

public class LoginPresenter {

    private LoginModule loginModule;
    private ReqContract.ILoginView iLoginView;

    public LoginPresenter(ReqContract.ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        this.loginModule = new LoginModule();
    }

    public void goLogin(Map<String,String> params, String api) {
        //正则表达式验证合法性


        if (iLoginView != null){
            loginModule.setData(params, api, new ReqContract.OKHttpCallBack() {
                @Override
                public void onSuccess(String res) {
                    if (iLoginView != null){
                        iLoginView.onSuccess(res);
                    }
                }

                @Override
                public void onFilure(String meg) {
                    if (iLoginView != null){
                        iLoginView.onFilure(meg);
                    }
                }
            });
        }
    }
}
