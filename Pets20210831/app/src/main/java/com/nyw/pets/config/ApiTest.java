package com.nyw.pets.config;

public class ApiTest {
    //当设置为true时为生产服务器模式，false为测试服务器模式
    public static final boolean isDebug = true;
    //服务器
    public final static String SERVER = isDebug ? "http://test.zhijianmeigu.com/" : "http://zhijianmeigu.com/";
    //测试服务器与生产服务器域名切换
    public final static String URL=isDebug?"api/v1/":"bipp/index.php/api/v1/";
    //测试服务器与生产服务器域名切换
//    public final static String URL=isDebug?"index.php/api/v1/":"bipp/index.php/index/show?user_agreement=";
    //服务器APP接口地址
    public final static String MAIN = ApiTest.SERVER + URL;
    //APP版本检测更新
    public final static String VERSION = ApiTest.MAIN + "version/latest.html";
    //登录
    public final  static String LOGIN= ApiTest.MAIN+"user/login.html";
    //获取用户信息
    public final  static String GET_USER_INFO= ApiTest.MAIN+"user/get_user_info.html";
    //退出登录
    public final  static String LOGIN_OUT= ApiTest.MAIN+"user/logout.html";
    //发送手机验证码
    public  final  static  String SEND_PHONE_CODE= ApiTest.MAIN+"user/get_code.html";
    //注册
    public final  static  String REGISTER= ApiTest.MAIN+"user/register.html";
    //修改密码
    public final  static  String USER_PASSWORD_UPDATE= ApiTest.MAIN+"user/pwd_reset.html";
    //修改用户资料
    public final  static  String USER_INFO_UPDATE= ApiTest.MAIN+"user/user_info_modif.html";
    //邦定手机号码
    public final  static  String GET_USER_RESET_PHONE= ApiTest.MAIN+"user/untie_phone.html";
    //更新头像
    public final  static  String USER_PHOTO_UPDATE= ApiTest.MAIN+"Puh/up_img";
    //版本更新
    public final  static  String GET_VERSION_FIND= ApiTest.MAIN+"version/find";
    //使用说明
    public final  static  String GET_AGREEMENT_INDEX= ApiTest.MAIN+"agreement/index";
    //用户协议
    public final  static  String GET_USER_AGREEMENT= ApiTest.SERVER+"index/index/useragreement";
    //订单充值数据记录列表
    public final  static  String GET_MONEY_LOG_LIST= ApiTest.MAIN+"money/money_log_list.html";
    //订单详情
    public final  static  String GET_MONEY_DETAILS= ApiTest.MAIN+"money/details.html";
    //提现
    public final  static  String SEND_MONEY_WITHDRAWAL= ApiTest.MAIN+"money/withdrawal";
    //钱包支付
    public final  static  String MONEY_PAY_PWD_CONFIRM= ApiTest.MAIN+"money/pay_pwd_confirm.html";
    //修改支付密码
    public final  static  String USER_PAYPWD_RESET= ApiTest.MAIN+"user/paypwd_reset.html";
    //在线客服
    public final  static  String GET_PLATFORM_SYS_INDEX= ApiTest.MAIN+"platform_sys/index.html";
    //微信登录
    public final  static  String USER_LOGIN_WECHAT= ApiTest.MAIN+"user/login_wechat.html";














}

