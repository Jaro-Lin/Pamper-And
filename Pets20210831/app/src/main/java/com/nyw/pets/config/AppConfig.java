package com.nyw.pets.config;

public class AppConfig {
    public static boolean DEBUG_ENABLE = false;// 是否调试模式
    public static String DEBUG_TAG = "liyujiang";// LogCat的标记
    public static final String WENIN_APP_ID = "wx55a9e6512ec27911";//微信开放平台，支付
    public static  final String APP_KEY="035efb5087cc966a94bc6f13b0a76bc6";
    public static  final String MASTER_SECRET=  new MyBase64().getBase64Data("07434064d54783c48e43d1b457fb1f53");
    public static final String YOU_MEG_KEY="";
    public static  int SUCCESS=1;
    public static int ERROR=0;
    public static int OPEN_TYPE_COUPON=690;
    //强制更新APP
    public static String UPDATE_APP="1";
    //h5  网页文章
    public static  String H5_HTTP="h5";
    //编辑信息
    public static String EDIT_INFO="EDIT_INFO";
    //支付状态
    public static  String payState="0";
    public static  int SETUP_USER_INFO=12;
    public static  String NAME="NAME";
    public static  String GET_CART="GET_CART";
    //无队伍
    //id
    public static String ID="ID";
    public final static int HEAD_CODE=0x711;//修改用户资料返回
    public final static int LOGIN_CODE=0x11;//登录返回
    public static  String LOGIN_OUT="1004";
    public static  String TWO="2";
    public final static int EDIT_USER_RECTUIT_INFO=0x711;//编辑用户招聘详情信息返回
    public static  String CREATE_TEAMS="CREATE_TEAMS";
    public static  String PHONE="PHONE";
    public static  String MAILBOX="mailbox";
    public static  String ADRESS="ADRESS";
    public static  String OCCUPATION="OCCUPATION";
    public static  String SKILLS="SKILLS";
    public static  String WORK_EXPERIENCE="WORK_EXPERIENCE";
    public static int ADRESS_SELECT=2666;
    public static  int CHOICE_CONVERSATION_STATE_DATA=456;
    public static int SELECT_PETS_TYPE=6176;










}
