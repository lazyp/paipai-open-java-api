/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public final class UrlEncodeUtils {
    public static String urlEncode(String msg, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(msg, charset).replace("+", "%20").replace("*", "%2A");
    }
}
