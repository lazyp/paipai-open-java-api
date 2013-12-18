/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api.meta;

/**
 * 商品状态
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-8
 */
public enum ItemState {
    ONSALE(1, "出售中"), STORE_HOUSE(2, "仓库中"), OUT_STOCK(8, "售完");
    int    code;
    String desc;

    ItemState(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
