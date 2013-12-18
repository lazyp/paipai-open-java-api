/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api.meta;

/**
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-18
 */
public enum DealRateState {
    DEAL_RATE_NO_EVAL, // 评价未到期
    DEAL_RATE_BUYER_NO_SELLER_NO, // 买家未评，卖家未评
    DEAL_RATE_BUYER_DONE_SELLER_NO, // 买家已评，卖家未评
    DEAL_RATE_BUYER_NO_SELLER_DONE, // 卖家已评，买家未评
    DEAL_RATE_BUYER_DONE_SELLER_DONE, // 买家已评，卖家已评
    DEAL_RATE_DISABLE, // 不可评价
    UNKNOW;
    public boolean isEq(String state) {
        return this.name().equalsIgnoreCase(state);
    }
}
