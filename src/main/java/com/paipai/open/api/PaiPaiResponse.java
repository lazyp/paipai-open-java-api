/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public class PaiPaiResponse {
    private int    statusCode;
    private int    apiErrorCode;
    private String apiErrorMsg;
    private String msg;
    private String responseBody;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getApiErrorCode() {
        return apiErrorCode;
    }

    public void setApiErrorCode(int apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public String getApiErrorMsg() {
        return apiErrorMsg;
    }

    public void setApiErrorMsg(String apiErrorMsg) {
        this.apiErrorMsg = apiErrorMsg;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return String.format("{\"statusCode\":%s,\"msg\":%s,\"responseBody\":%s}", this.statusCode, this.msg,
                             this.responseBody);
    }

}
