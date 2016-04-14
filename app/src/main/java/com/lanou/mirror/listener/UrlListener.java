package com.lanou.mirror.listener;

/**
 * Created by Yi on 16/3/31.
 */
public interface UrlListener {
    /**
     * 服务端接口
     */
    String SERVER_SIDE_URL = "http://api101.test.mirroreye.cn/";

    /**
     * 接口全部为 post 请求,返回数据 JSON 格式,请求参不能是null, 但可以是""空的字符串
     */

    /**
     * 用户注册 编码 m001
     */
    String USER_REGISTER_URL = SERVER_SIDE_URL + "index.php/user/reg";
    /**
     * 用户登录 编码 m001
     */
    String USER_LOGIN_URL = SERVER_SIDE_URL + "index.php/user/login";
    /**
     * 故事列表 编码 m002
     */
    String STORY_LIST_URL = SERVER_SIDE_URL + "index.php/story/story_list";
    /**
     * 商品列表 编码 m003
     */
    String PRODUCTS_GOODS_LIST_URL = SERVER_SIDE_URL + "index.php/products/goods_list";
    /**
     * 商品详情 编码 m004
     */
    String PRODUCTS_GOODS_INFO_URL = SERVER_SIDE_URL + "index.php/products/goods_info";
    /**
     * 商品分类列表 编码 m005
     */
    String PRODUCT_CATEGORY_LIST_URL = SERVER_SIDE_URL + "index.php/products/category_list";
    /**
     * 个人中心 编码 m006
     */
    String USER_INFO_URL = SERVER_SIDE_URL + "index.php/user/user_info";
    /**
     * 绑定账号 编码 m007
     */
    String USER_BUNDLING_URL = SERVER_SIDE_URL + "index.php/user/bundling";
    /**
     * 账号获取验证码 编码 m007
     */
    String USER_SEND_CODE_URL = SERVER_SIDE_URL + "index.php/user/send_code";
    /**
     * 加入购物车 编码 m008
     */
    String JOIN_SHOPPING_CART_URL = SERVER_SIDE_URL + "index.php/order/join_shopping_cart";
    /**
     * 订单列表 编码 m009
     */
    String ORDER_LIST_URL = SERVER_SIDE_URL + "index.php/order/order_list";
    /**
     * 购物车列表 编码 m010
     */
    String ORDER_SHOPPING_CART_LIST_URL = SERVER_SIDE_URL + "index.php/order/shopping_cart_list";
    /**
     * 我的收获地址 编码 m011
     */
    String USER_ADDRESS_LIST_URL = SERVER_SIDE_URL + "index.php/user/address_list";
    /**
     * 添加收获地址 编码 m012
     */
    String USER_ADD_ADDRESS_URL = SERVER_SIDE_URL + "index.php/user/add_address";
    /**
     * 编辑收货地址 编码 m013
     */
    String USER_EDIT_ADDRESS_URL = SERVER_SIDE_URL + "index.php/user/edit_address";
    /**
     * 删除收获地址 编码 m014
     */
    String USER_DELETE_ADDRESS_URL = SERVER_SIDE_URL + "index.php/user/del_address";
    /**
     * 设置默认收获地址 编码 m015
     */
    String USER_DEFAULT_ADDRESS_URL = SERVER_SIDE_URL + "index.php/user/mr_address";
    /**
     * 下订单 编码 m016
     */
    String ORDER_SUB_URL = SERVER_SIDE_URL + "index.php/order/sub";
    /**
     * 支付宝支付 编码 m016
     */
    String PAY_AIL_PAY_SUB_URL = SERVER_SIDE_URL + "index.php/pay/ali_pay_sub";
    /**
     * 微信支付 编码 m016
     */
    String PAY_WX_PAY_SUB_URL = SERVER_SIDE_URL + "index.php/pay/wx_pay_sub";
    /**
     * 获取微信支付结果 编码 m017
     */
    String PAY_WX_ORDER_QUERY_URL = SERVER_SIDE_URL + "index.php/pay/wx_orderquery";
    /**
     * 我的订单详情 编码 m019
     */
    String ORDER_INFO_URL = SERVER_SIDE_URL + "index.php/order/info";

    /**
     * 故事详情 编码 m022
     */
    String STORY_INFO_URL = SERVER_SIDE_URL + "index.php/story/info";
    /**
     * 我的优惠券 编码 m024
     */
    String USER_DISCOUNT_LIST_URL = SERVER_SIDE_URL + "index.php/user/discount_list";
    /**
     * 启动图
     */
    String START_IMAGE_URL = SERVER_SIDE_URL + "index.php/index/started_img";
    /**
     * 分享开关
     */
    String SHARE_SWITCH_URL = SERVER_SIDE_URL + "index.php/index/share_switch";
    /**
     * 检查更新
     */
    String SYS_CHECK_URL = SERVER_SIDE_URL + "index.php/sys";


}
