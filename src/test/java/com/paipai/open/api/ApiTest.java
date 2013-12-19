/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

import java.util.TreeMap;

import org.junit.Test;

import com.paipai.open.api.utils.SignUtils;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-12-18
 */
public class ApiTest {
    /**
     * 登录签名验证
     */
    public void siginTest() {
        String accessToken = "";
        long useruin = 1111;// qq号
        String appAuthId = "";// 自己应用的authid
        String appOauthKey = "";// 自己应用的OauthKey
        /* 签名验证 */
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("access_token", accessToken);
        params.put("useruin", useruin);
        params.put("app_oauth_id", appAuthId);
        String calSign = SignUtils.sign("", "", appOauthKey, params);// 注意登录签名验证，是没有httpMethod(请求方式GET or POST)，api
                                                                     // path的
        System.out.println(calSign);
    }

    /**
     * 获取用户个人信息
     */
    @Test
    public void getUserInfoTest() {
        String appOAuthID = "";
        String secretOAuthKey = "";
        String token = "";
        long userUin = 597322783;
        PaiPaiHttpExecutor paiPaiHttpExecutor = new PaiPaiHttpExecutor(appOAuthID, secretOAuthKey);
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("pureData", "1");
        params.put("needRoot", "1");
        params.put("userUin", userUin);
        System.out.println(paiPaiHttpExecutor.getJSON(userUin, token,
                                                      ApiPath.USER_INFO_API_PATH, params));

    }
}
