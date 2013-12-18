/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

import java.util.TreeMap;

import org.junit.Test;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-12-18
 */
public class ApiTest {
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
