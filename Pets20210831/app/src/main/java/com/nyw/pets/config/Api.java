package com.nyw.pets.config;

public class Api {
    //当设置为true时为生产服务器模式，false为测试服务器模式
    public static final boolean isDebug = true;
    //服务器
    public final static String SERVER = isDebug ? "http://lovelovepets.com/" : "http://lovelovepets.com/";
    //测试服务器与生产服务器域名切换
    public final static String URL=isDebug?"api.php/":"api.php/";
    //测试服务器与生产服务器域名切换
//    public final static String URL=isDebug?"index.php/api/v1/":"bipp/index.php/index/show?user_agreement=";
    //服务器APP接口地址
    public final static String MAIN = Api.SERVER + URL;
    //微信头像信息
    public final  static  String GET_WEIXIN_IMG_URL="http://thirdwx.qlogo.cn/";
    //APP版本检测更新
    public final static String VERSION = Api.MAIN + "version/latest.html";
    //获取用户资料
    public  final static String GET_USER_INFO=Api.MAIN+"user/about_user/userinfo";
    //用户注册
    public final static String GET_USER_LOGIN_REGISTER=Api.MAIN+"user/login/register";
    //发送短信验证码
    public final  static  String SEND_CODE=Api.MAIN+"user/verification_code/send";
    //用户登录
    public final  static  String USER_LOGIN_PHONE=Api.MAIN+"user/login/login_psw";
    //用户手机验证码登录
    public final  static  String USER_LOGIN_CODE=Api.MAIN+"user/login/login_send";
    //用户忘记密码，找回密码
    public final  static  String USER_ABOUT_USER_FORGET_PASSWORD=Api.MAIN+"user/about_user/forget_password";
    //上传图片
    public final  static  String UPDATE_USER_IMG=Api.MAIN+"user/about_user/avatar";
    //修改用户头像
    public final  static  String UPDATE_USER_AVATAR=Api.MAIN+"user/about_user/avatar_update";
    //修改呢称
    public final  static  String UPDATE_USER_NAME=Api.MAIN+"user/about_user/change_nickname";
    //修改性别
    public final static String UPDATE_USER_SEX=Api.MAIN+"user/about_user/change_sex";
    //修改出生年月日
    public final static String UPDATE_USER_CREATE=Api.MAIN+"user/about_user/change_birthday";
    //修改城市
    public final static String UPDATE_USER_CHANGE_CITY=Api.MAIN+"user/about_user/change_city";
    //修改简介
    public final static String UPDATE_USER_CHANGE_SPE=Api.MAIN+"user/about_user/change_spe";
    //退出登录
    public final static String UPDATE_USER_CHANGE_EXIT_LOGIN=Api.MAIN+"user/about_user/exit_login";
    //qq登录注册
    public final static String USER_CHANGE_QQ_LOGIN=Api.MAIN+"user/qq/login";
    //QQ注册邦定手机号码
    public final static String GET_USER_QQ_BINDING=Api.MAIN+"user/qq/binding";
    //微信登录注册
    public final static String USER_CHANGE_WECHAT_LOGIN=Api.MAIN+"user/wechat/login";
    //微信注册邦定手机号码
    public final static String GET_USER_WECHAT_BINDING=Api.MAIN+"user/wechat/binding";
    //第三方登录设置密码
    public final static String LOGIN_SET_PASSWORD=Api.MAIN+"user/about_user/set_password";
    //获取宠物类型
    public final static String GET_PETS_GET_TYPE=Api.MAIN+"pets/pets/get_type";
    //载入宠物品种
    public final static String GET_PETS_VARIETIES=Api.MAIN+"pets/pets/get_varieties";
    //添加宠物
    public final static String ADD_PETS=Api.MAIN+"pets/pets/login";
    //宠物详细列表
    public final static String GET_PETS_INFO=Api.MAIN+"pets/pets/pets_info";
    //发布动态
    public final static String SEND_NEW_INFO_DATA=Api.MAIN+"post/send/send";
    //获取话题列表
    public final static String GET_POST_THEME=Api.MAIN+"post/theme/theme";
    //创建话题
    public final static String ADD_SEND_NEW_THEME=Api.MAIN+"post/send/new_theme";
    //搜索话题
    public final static String SEARCH_THEME_=Api.MAIN+"post/theme/search";
    //上传图片或视频
    public final static String SEND_IMG_AND_VIDEO_FILE=Api.MAIN+"post/send/upload";
    //获取首页动态信息
    public final static String GET_DYNAMIC_INFO=Api.MAIN+"post/post/get_post";
    //获取话题所属动态
    public final static String GET_OPIC_DYNAMIC=Api.MAIN+"post/theme/theme_info";
    //获取举报类型
    public final static String GET_REPORT_TYPE=Api.MAIN+"post/comment/get_report_type";
    //更新经纬度数据
    public final static String UPDATE_ADRESS_MY=Api.MAIN+"post/post_about/position";
    //获取其他用户详情信息
    public final static String GET_USER_INFO_OTHER=Api.MAIN+"user/about_user/userinfo_other";
    //获取其他用户动态列表
    public final static String GET_PERSONAL_OTHER_USER_POST=Api.MAIN+"user/personal/other_user_post";
    //举报评论
    public final static String COMMENT_DEL_IMPEACH=Api.MAIN+"post/comment/del_impeach";
    //获取当前动态评论列表
    public final static String GET_POST_ABOUT_COMMENT_LIST=Api.MAIN+"comment/comment/list";
    //发布动态评论
    public final static String SEND_COMMENT_MY_SEND=Api.MAIN+"comment/comment/do_comment";
    //点赞-取消点赞 (回复评论)
    public final static String SEND_GOOD_COMMENT_REPLY=Api.MAIN+"post/comment/good_comment_reply";
    //评论点赞-取消点赞
    public final static String SEND_COMMENT_GOOD_COMMENT=Api.MAIN+"post/comment/good_comment";
    //根据类型获取所有视频数据
    public final static String GET_COURSE_COURSE_GET_TYPE_VIDEO=Api.MAIN+"course/course/get_type_video";
    //获取课程分类
    public final static String GET_COURSE_GET_TYPE=Api.MAIN+"course/course/get_type";
    //获取当前课程信息
    public final static String GET_COURSE_VIDEO=Api.MAIN+"course/course/video";
    //收藏 与取消收藏视频
    public final static  String GET_COURSE_COLLECTION=Api.MAIN+"course/course/collection";
    //获取相关推荐课程
    public final static  String GET_COURSE_REC_ABOUT=Api.MAIN+"course/course/rec_about";
    //动态点赞-取消点赞
    public final static  String GET_POST_ABOUT_GOOD=Api.MAIN+"comment/comment/good";
    //商品评论点赞-取消点赞
    public final static  String GET_COMMENT_GOOD=Api.MAIN+"comment/comment/good";
    //收藏与取消收藏
    public final static  String GET_POST_ABOUT_COLLECTION=Api.MAIN+"post/post_about/collection";
    //获取热门推荐课程
    public final static  String GET_COURSE_GET_POPULAR=Api.MAIN+"course/course/get_popular";
    //获取举报 类型
    public final static  String GET_COMMENT_GET_REPORT_TYPE=Api.MAIN+"post/comment/get_report_type";
    //我的动态
    public final static  String GET_USER_PERSONAL_POST_USER=Api.MAIN+"user/personal/post_user";
    //获取我的粉丝
    public final static String GET_USER_PERSONAL_FANS=Api.MAIN+"user/personal/fans";
    //举报动态
    public final static  String GET_POST_ABOUT_REPORT_POST=Api.MAIN+"post/post_about/report_post";
    //获取我关注的人
    public final static String GET_USER_PERSONAL_FOLLOW=Api.MAIN+"user/personal/follow";
    //修改编辑宠物 信息
    public final static String GET_PETS_UPDATA_PETS=Api.MAIN+"pets/pets/update_pets";
    //删除宠物 信息
    public final static String GET_PETS_PETS_DEL=Api.MAIN+"pets/pets/del";
    //收藏的动态
    public final static String GET_USER_PERSONAL_POST=Api.MAIN+"user/personal/post";
    //收藏的视频课程
    public final static String GET_PERSONAL_VIDEO=Api.MAIN+"user/personal/video";
    //获取首页商城banner
    public final static String GET_SHOOP_BANNER=Api.MAIN+"shop/shop/banner";
    //商品列表
    public final static String GET_SHOP_LIST=Api.MAIN+"shop/shop/list";
    //获取我关注的社区
    public final static String GET_USER_PERSONAL_THEME_FOLLOW=Api.MAIN+"user/personal/theme_follow";
    //搜索动态
    public final static String GET_POST_ABOUT__SEARCH_POST=Api.MAIN+"post/post_about/search_post";
    //搜索商品
    public final static String GET_SEARCH_SHOP_INFO=Api.MAIN+"shop/order_about/search";
    //商品详情
    public final static String GET_SHOP_INFO=Api.MAIN+"shop/shop/shop_info";
    //加入购物车
    public final static  String ADD_SHOP_CART=Api.MAIN+"shop/car/in_car";
    //搜索课程分类
    public final static  String SEARCH_COURSE_TYPE=Api.MAIN+"course/course/search_type";
    //签到
    public final static  String ADD_SGIN_INFO_USER=Api.MAIN+"user/user/sign";
    //商品评论列表，这个已经不用
    public  final  static  String GET_SHOP_ORDER_ABOUT_COMMENT_LIST=Api.MAIN+"shop/order_about/comment_list";
    //获取用户收货地址
    public  final  static  String GET_SHOP_USER_LIST=Api.MAIN+"shop/user/list";
    //热销榜
    public  final  static  String GET_SHOP_PREFERENTIAL=Api.MAIN+"shop/shop/preferential";
    //获取购物车列表
    public  final  static  String GET_SHOP_CAR_LIST=Api.MAIN+"shop/car/list";
    //购物车商品数量修改
    public  final  static  String GET_SHOP_CAR_NUMBER=Api.MAIN+"shop/car/update";
    //删除购物车商品
    public  final  static  String DEL_SHOP_CAR_DEL_SHOP=Api.MAIN+"shop/car/delete";
    //删除收货人地址
    public  final  static  String DEL_USER_ADRESS_INFO=Api.MAIN+"shop/user/del_address";
    //新增收货人地址
    public  final  static  String ADD_USER_ADRESS_INFO=Api.MAIN+"shop/user/add_user";
    //修改收件人信息
    public  final  static  String UPFATE_USER_ADRESS_INFO=Api.MAIN+"shop/user/update";
    //商品下订单
    public  final  static  String GET_ORDER_SHOP_INFO=Api.MAIN+"shop/order/in_order";
    //生成支付订单签名信息
    public  final  static  String GET_PAY_ORDER_SHOP_INFO=Api.MAIN+"shop/order/order_pay";
    //我的订单
    public  final  static  String GET_SHOP_ORDER_LIST=Api.MAIN+"shop/order/order_list";
    //商品评论、动态评论列表
    public  final  static  String GET_COMMENT_COMMENT_LIST=Api.MAIN+"comment/comment/list";
    //取消订单
    public  final  static  String GET_CANCEL_ORDER_INFO=Api.MAIN+"shop/order_about/cancel";
    //客服微信获取
    public  final  static  String GET_ABOUT_USER_SERVE_WECHAT=Api.MAIN+"user/about_user/serve_wechat";
    //确定 收货
    public  final  static  String GET_SHOP_ABOUT_RECEIPT=Api.MAIN+"shop/order_about/receipt";
    //商品评论
    public  final  static  String GET_SHOP_ORDER_COMMENT=Api.MAIN+"shop/order/comment";
    //我的售后列表
    public  final  static  String GET_SHOP_ORDER_AFTER_LIST=Api.MAIN+"shop/order_after/list";
    //获取图片文件域名
    public  final  static  String GET_IMG_FILE_PATH_SERVICE_URL=Api.MAIN+"other/other/upload_host";
    //订单详情
    public  final  static  String GET_ORDER_DETAILS=Api.MAIN+"shop/order_about/details";
    //获取用户优惠卷列表
    public  final  static  String GET_SHOP_COUPON_COUPON_LIST=Api.MAIN+"shop/coupon/coupon_list";
    //获取默认宠物
    public  final  static  String GET_PETS_ABOUT_GET_DEFAULT=Api.MAIN+"pets/pets_about/get_default";
    //关注用户或取消关注用户
    public  final  static  String GET_USER_ABOUT_USER_FOLLOW=Api.MAIN+"user/about_user/follow";
    //分享
    public  final  static  String GET_POST_ABOUT_SHARE=Api.MAIN+"post/post_about/share";
    //版本更新
    public  final  static  String GET_OTHER_APP_VERSION=Api.MAIN+"other/other/app_version";
    //申请售后，发起售后
    public  final  static  String GET_SHOP_ORDER_AFTER_SALES=Api.MAIN+"shop/order_after/after_sales";
    //填写物流单号
    public  final  static  String GET_SHOP_ORDER_AFTER_LOGISTICE_ORDER=Api.MAIN+"shop/order_after/logistics_order";
    //删除售后订单记录
    public  final  static  String GET_SHOP_ORDER_AFTER_DEL=Api.MAIN+"shop/order_after/del";
    //初始化宠物
    public  final  static  String GET_USER_SCHEDULE_SCHEDULE_INIT=Api.MAIN+"user/schedule_v2/init";
    //记录当天日程信息
    public  final  static  String SEND_USER_SCHEDULE_WRITE=Api.MAIN+"user/schedule_v2/set";
    //记录模块，获得某月的日程信息
    public  final  static  String GET_USER_SCHEDULE_SOME_MONTH=Api.MAIN+"user/schedule_v2/get";
    //关注或取消关注话题
    public  final static  String GET_POST_THEME_THEME_FOLLOW=Api.MAIN+"post/theme/theme_follow";
    //宠物品种信息
    public  final static  String GET_PETS_PETS_TYPE_GET_TYPE=Api.MAIN+"pets/pets_type/get_type";
    //第三方登录
    public  final static  String GET_USER_OAUTH_LOGIN=Api.MAIN+"user/oauth/login";
    //第三方注册
    public  final static  String GET_USER_OAUTH_REG=Api.MAIN+"user/oauth/reg";
    //获取商品分类
    public final  static  String GET_SHOP_SHOP_TYPE=Api.MAIN+"shop/shop/type";
    //软删除动态
    public  final  static  String DEL_SEND_POST=Api.MAIN+"post/send/del_post";
    //删除评论
    public  final  static  String DEL_COMMENT_DELETS=Api.MAIN+"comment/comment/delete";
    //病症列表
    public  final  static  String GET_PETS_MALADY_ALL_INFO=Api.MAIN+"pets/malady/all";
    //病症详情
    public  final  static  String GET_PETS_MALADY_DETAIL=Api.MAIN+"pets/malady/detail";
    //修改驱虫/洗澡间隔
    public  final  static  String GET_USER_SCHEDULE_V2_EDIT_RULE=Api.MAIN+"user/schedule_v2/edit_rule";
    //体重详情----宠物id
    public  final  static  String GET_PETS_TYPE_MEAL_ADD_ID=Api.MAIN+"pets/pets_type/meal_add?id=";
    //身体状态详情---病症id
    public  final  static  String GET_PETS_MALADY_SHOW_ID=Api.MAIN+"pets/malady/show?id=";
    //驱虫详情---宠物品种id
    public  final  static  String GET_PETS_EXPELLING_ID=Api.MAIN+"pets/pets_type/expelling?id=";
    //疫苗详情--宠物品种id
    public  final  static  String GET_PETS_VACCIN_ID=Api.MAIN+"pets/pets_type/vaccin?id=";
    //洗澡详情---宠物品种id
    public  final  static  String GET_PETS_SHOWER_ID=Api.MAIN+"pets/pets_type/shower?id=";
    //喂养详情---宠物品种id
    public  final  static  String GET_PETS_FEED_ID=Api.MAIN+"pets/pets_type/feed?id=";


































































































}

