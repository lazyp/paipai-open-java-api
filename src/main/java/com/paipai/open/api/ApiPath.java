/**
 *Copyright 2012-2013.All Rights Reserved
 */
package com.paipai.open.api;

/**
 * api path
 * @author <a href="mailto:lazy_p@163.com">lazyp</a>
 * @version 2013-11-7
 */
public interface ApiPath {
    /**
     * 订购关系api path
     */
    public static final String APPSTORE_SUBSCRIBE_API_PATH       = "/appstore/getSubscribeList.xhtml";
    /**
     * 获取用户信息
     */
    public static final String USER_INFO_API_PATH                = "/user/getUserInfo.xhtml";
    /**
     * 卖家搜索订单列表api
     */
    public static final String SELLER_SEARCH_ORDER_LIST_API_PATH = "/deal/sellerSearchDealList.xhtml";
    /**
     * 搜索商品列表
     */
    public static final String SELLER_SEARCH_ITEM_LIST_API_PATH  = "/item/sellerSearchItemList.xhtml";
    /**
     * 评价
     */
    public static final String DEAL_RATE_API_PATH                = "/evaluation/evaluateDeal.xhtml";
}
