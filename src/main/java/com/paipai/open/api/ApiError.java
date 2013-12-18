/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public enum ApiError {
    UNKNOWN(-1, "未知错误!");
    int    errorCode;
    String errorMsg;

    ApiError(int errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ApiError getApiError(int errorCode) {
        for (ApiError apiError : ApiError.values()) {
            if (errorCode == apiError.getErrorCode()) {
                return apiError;
            }
        }
        return UNKNOWN;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
