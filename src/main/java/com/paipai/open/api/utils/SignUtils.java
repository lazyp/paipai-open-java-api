/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paipai.open.api.OpenApiException;

/**
 * 签名工具类
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public final class SignUtils {
    private static final Log    LOG             = LogFactory.getLog(SignUtils.class);
    private static final String DEFAULT_CHARSET = "GBK";
    private static final String CONCAT_CHAR     = "&";

    /**
     * 签名算法
     * @param httpMethod
     * @param apiPath
     * @param appOAuthkey
     * @param params
     * @return
     */
    public static String sign(String httpMethod, String apiPath, String appOAuthkey, TreeMap<String, Object> params) {
        String sig = "";
        String charset = DEFAULT_CHARSET;
        if (params.get("charset") != null) {
            charset = params.get("charset").toString();
        }
        // 签名密钥
        String secret = appOAuthkey + "&";
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(charset), mac.getAlgorithm());
            mac.init(secretKey);

            String paramStr = UrlEncodeUtils.urlEncode(paramToString(params), charset);
            if (StringUtils.isNotBlank(apiPath)) {
                paramStr = UrlEncodeUtils.urlEncode(apiPath, charset) + CONCAT_CHAR + paramStr;
            }
            if (StringUtils
                           .isNotBlank(httpMethod)) {
                paramStr = httpMethod.toUpperCase(Locale.ENGLISH) + CONCAT_CHAR + paramStr;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("用于计算sign的源串[" + paramStr + "]");
            }

            byte[] hash = mac.doFinal(paramStr.getBytes(charset));
            sig = new String(Base64Coder.encode(hash));
        } catch (Exception e) {
            throw new OpenApiException(OpenApiException.MAKE_SIGNATURE_ERROR, e);
        }
        return sig;
    }

    private static String paramToString(TreeMap<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> keyIter = params.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            String value = valueToString(params.get(key));
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    private static String valueToString(Object value) {
        if (value.getClass().isArray()) {
            String valueStr = "";
            String[] items = (String[]) value;
            Arrays.sort(items);

            for (String item : items) {
                valueStr += item;
            }

            return valueStr;
        } else {
            return value.toString();
        }
    }

    public static void main(String[] args) {

    }
}
