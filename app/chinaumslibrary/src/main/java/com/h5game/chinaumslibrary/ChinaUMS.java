package com.h5game.chinaumslibrary;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.h5game.thirdpartycallback.ThirdPartyCallback;

import java.util.Map;

public class ChinaUMS extends ThirdPartyCallback {
    private UnifyPayPlugin payPlugin;

    public ChinaUMS(Activity activity, String className){
        super(className);
        mActivity = activity;

        payPlugin = UnifyPayPlugin.getInstance(mActivity);
        /*
         * 设置支付结果监听
         */
        payPlugin.setListener((resultCode, resultInfo) -> {
            /*
             * 根据返回的支付结果进行处理
             */
            if (resultCode.equals("0000")) {
                //支付成功
            } else {
                //其他
            }
        });
    }

    public void pay(Map map){
        JSONObject appRequestData = (JSONObject)map.get("appPayRequest");
        String payType = (String)map.get("payType");
        /*
         * 新建统一支付请求类
         *  */
        UnifyPayRequest payRequest = new UnifyPayRequest();
        /*
         * 初始化支付渠道(如：微信支付)
         * */
        if(payType.equals("wechatsdk")){
            payRequest.payChannel = UnifyPayRequest.CHANNEL_WEIXIN;
        }else if(payType.equals("alipaysdk")){
            payRequest.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        }else {
            payRequest.payChannel = UnifyPayRequest.CHANNEL_UMSPAY;
        }

        /*
         * 设置下单接口中返回的数据(appRequestData)
         * */
        payRequest.payData = appRequestData.toString();

        payPlugin.sendPayRequest(payRequest);
    }

    public void umspayResult(String str){
        if (str.equalsIgnoreCase("success")) {
//            云闪付支付成功
        } else if (str.equalsIgnoreCase("fail")) {
//            云闪付支付失败
        } else if (str.equalsIgnoreCase("cancel")) {
//            用户取消了云闪付支付
        }
    }
}
