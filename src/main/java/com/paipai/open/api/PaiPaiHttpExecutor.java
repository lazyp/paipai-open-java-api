/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.paipai.open.api.utils.SignUtils;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public final class PaiPaiHttpExecutor {
    private static final Log  LOG                     = LogFactory.getLog(PaiPaiHttpExecutor.class);
    private static final int  HTTP_CONNECTION_TIMEOUT = 60000;
    private static final int  SOCKET_TIMEOUT          = 60000;
    private String            protocol                = "http";
    private String            hostName                = "api.paipai.com";
    private String            format                  = "json";
    private String            appOAuthID;
    private String            appOAuthkey;
    private String            charset                 = "utf-8";

    private static HttpClient httpClient              = null;
    static {
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(20);
        httpClient = new DecompressingHttpClient(new DefaultHttpClient(cm));
        HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreProtocolPNames.USER_AGENT,
                                "PaiPai API Invoker/Java " + System.getProperty("java.version"));// useAagent
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
        httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
        LOG.info("init finished");
    }

    public PaiPaiHttpExecutor(String appOAuthID, String appOAuthkey){
        this.appOAuthID = appOAuthID;
        this.appOAuthkey = appOAuthkey;
    }

    public PaiPaiResponse getJSON(long uin, String accessToken, String apiPath, TreeMap<String, Object> params) {
        pre(params, accessToken, uin);
        String sign = SignUtils.sign("get", apiPath, appOAuthkey, params);
        params.put("sign", sign);
        HttpGet get = null;
        PaiPaiResponse resp = new PaiPaiResponse();
        HttpEntity respEntity = null;
        try {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            NameValuePair pair;
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object obj = params.get(key);
                if (obj.getClass().isArray()) {
                    String arr[] = (String[]) obj;
                    for (String value : arr) {
                        pair = new BasicNameValuePair(key, value);
                        parameters.add(pair);
                    }
                } else if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
                    pair = new BasicNameValuePair(key, obj.toString());
                    parameters.add(pair);
                } else {
                    throw new RuntimeException("http get not support parameter[key=" + key + ";value=" + obj.toString()
                                               + "]");
                }
            }

            HttpEntity entity = new UrlEncodedFormEntity(parameters, charset);
            String reqParamStr = "";
            try {
                InputStream stream = entity.getContent();
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = stream.read(buffer)) != -1) {
                    byteStream.write(buffer, 0, len);
                }
                reqParamStr = new String(byteStream.toByteArray());
            } catch (RuntimeException e) {
                throw e;
            }

            String reqUrl = buildUrl(apiPath, reqParamStr);

            if (LOG.isDebugEnabled()) {
                LOG.debug("生成的完成get方法的URL:" + reqUrl);
            }

            get = new HttpGet(reqUrl);
            HttpResponse response = httpClient.execute(get);
            respEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                resp.setStatusCode(response.getStatusLine().getStatusCode());
                resp.setMsg(response.getStatusLine().toString());
            } else {
                resp.setStatusCode(HttpStatus.SC_OK);
            }
            resp.setResponseBody(EntityUtils.toString(respEntity));
            parseResponseBody(resp);
        } catch (ClientProtocolException e) {
            LOG.error("", e);
            get.abort();
        } catch (IOException e) {
            LOG.error("", e);
            get.abort();
        } finally {
            EntityUtils.consumeQuietly(respEntity);
        }

        return resp;
    }

    public PaiPaiResponse post(long uin, String accessToken, String apiPath, TreeMap<String, Object> params) {
        pre(params, accessToken, uin);
        String sign = SignUtils.sign("post", apiPath, appOAuthkey, params);
        params.put("sign", sign);

        PaiPaiResponse resp = new PaiPaiResponse();
        String reqUrl = this.protocol + "://" + this.hostName + apiPath;
        HttpPost post = new HttpPost(reqUrl);
        HttpEntity responseEntity = null;
        try {

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            NameValuePair pair;
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object obj = params.get(key);
                if (obj.getClass().isArray()) {
                    String arr[] = (String[]) obj;
                    for (String value : arr) {
                        pair = new BasicNameValuePair(key, value);
                        parameters.add(pair);
                    }
                } else if (obj instanceof String) {
                    pair = new BasicNameValuePair(key, (String) obj);
                    parameters.add(pair);
                } else {
                    LOG.error(obj.toString());
                    throw new RuntimeException("http get not support parameter");
                }
            }

            HttpEntity entity = new UrlEncodedFormEntity(parameters, charset);
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            responseEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                resp.setStatusCode(response.getStatusLine().getStatusCode());
                resp.setMsg(response.getStatusLine().toString());
            } else {
                resp.setStatusCode(HttpStatus.SC_OK);
            }
            resp.setResponseBody(EntityUtils.toString(responseEntity));
            parseResponseBody(resp);
        } catch (ClientProtocolException e) {
            LOG.error("", e);
            post.abort();
        } catch (IOException e) {
            LOG.error("", e);
            post.abort();
        } finally {
            EntityUtils.consumeQuietly(responseEntity);
        }

        return resp;
    }

    private void parseResponseBody(PaiPaiResponse resp) {
        JSONObject json = JSONObject.parseObject(resp.getResponseBody());
        resp.setApiErrorCode(json.getIntValue("errorCode"));
        resp.setApiErrorMsg(json.getString("errorMessage"));
    }

    private void pre(TreeMap<String, Object> params, String accessToken, long uin) {
        // 默认参数系统自动设置
        params.put("pureData", "1");
        params.put("appOAuthID", appOAuthID);
        if (StringUtils.isNotBlank(accessToken)) {
            params.put("accessToken", accessToken);
        }
        params.put("uin", String.valueOf(uin));
        params.put("format", format);
        params.put("charset", charset);
        params.put("randomValue", String.valueOf(((long) (Math.random() * 100000 + 11229))));
        params.put("timeStamp", System.currentTimeMillis() + "");
    }

    private String buildUrl(String apiPath, String reqParamStr) {
        String url = this.protocol + "://" + this.hostName + apiPath;
        if (StringUtils.isNotBlank(reqParamStr)) {
            url = url + "?" + reqParamStr;
        }
        return url;
    }
}
