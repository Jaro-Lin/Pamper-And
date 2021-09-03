package com.nyw.pets;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.edu.heuet.littlecurl.ninegridview.preview.NineGridViewGroup;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static MyApplication myApp;
    private List<Activity> list = new LinkedList<Activity>();
    public  boolean isUpdateApp;
    //宠物id
    private String pets_id;
    //宠物是否初始化,等于0是未初始化，1是已经初始化
    private String is_init;

    @Override
    public void onCreate() {
        super.onCreate();
        setImageLoader();
        okgoConfig();
        UMConfigure.init(getApplicationContext(), "5e7880190cafb24bc90000e8", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //        捕获程序崩溃日志
        MobclickAgent.reportError(getApplicationContext(), "Parameter Error");

        try {
            // 抛出异常的代码
        } catch (Exception e) {
            MobclickAgent.reportError(getApplicationContext(), e);
        }

        //分享
        //微信
        PlatformConfig.setWeixin("wx2eb0de8f8aa5ef8b", "550f35c0fd3c8fbd411262a6f4b27609");
        //qq
        PlatformConfig.setQQZone("1110291319", "cIpa4Fhj9uLdeFIl");
        //weibo
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad"
                , "http://sns.whalecloud.com");
        //极光推送初始化
        //设置开启日志,发布时请关闭日志,打开调试模式
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        setImgFilePathUrl(imgFilePathUrl);

    }

    /**
     * okgo请求网络配制
     */
    private void okgoConfig() {
//        OkGo.getInstance().init(this);
        //方法一
//        OkGo.getInstance().init(this);
        //方法二
        //1、构建OkHttpClient.Builder
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //2、配置log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //非必要情况，不建议使用，第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
//        builder.addInterceptor(new ChuckInterceptor(this));

        //3、配置超时时间
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //4、配置Cookie，以下几种任选其一就行
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//        //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
//        //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        //5、Https配置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
//        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
//        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
//        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        //6、配置OkGo
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("Content-Type", "application/json");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
//-------------------------------------------------------------------------------------//

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    public boolean isUpdateApp() {
        return isUpdateApp;
    }

    public void setUpdateApp(boolean updateApp) {
        isUpdateApp = updateApp;
    }

    // 连续按2次返回健退出 开始
    public static MyApplication getInstance() {
        if (myApp == null) {
            myApp = new MyApplication();
        }
        return myApp;
    }
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exitApp() {
        for (Activity ac : list) {
            ac.finish();
        }
    }
    private boolean isupdateVersion = false;
    public String imgFilePathUrl;
    public boolean isOpenShop=false;
    public boolean isOrder=false;

    public String getIs_init() {
        return is_init;
    }

    public void setIs_init(String is_init) {
        this.is_init = is_init;
    }

    public String getPets_id() {
        return pets_id;
    }

    public void setPets_id(String pets_id) {
        this.pets_id = pets_id;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public boolean isOpenShop() {
        return isOpenShop;
    }

    public void setOpenShop(boolean openShop) {
        isOpenShop = openShop;
    }

    public String getImgFilePathUrl() {
        return imgFilePathUrl;
    }

    public void setImgFilePathUrl(String imgFilePathUrl) {
        this.imgFilePathUrl = imgFilePathUrl;
    }

    public boolean isIsupdateVersion() {
        return isupdateVersion;
    }

    public void setIsupdateVersion(boolean isupdateVersion) {
        this.isupdateVersion = isupdateVersion;
    }

    private void setImageLoader() {
        NineGridViewGroup.setImageLoader(new GlideImageLoader());
    }

    /**
     * Glide 加载图片
     */
    private class GlideImageLoader implements NineGridViewGroup.ImageLoader {
        GlideImageLoader() {
        }

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_default_color)   // 图片未加载时的占位图或背景色
                    .error(R.drawable.ic_default_color)         // 图片加载失败时显示的图或背景色
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // 开启本地缓存
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
