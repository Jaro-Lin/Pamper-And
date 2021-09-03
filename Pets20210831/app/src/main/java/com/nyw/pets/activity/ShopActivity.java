package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.SearchShopActivity;
import com.nyw.pets.activity.shop.ShopDetailsActivity;
import com.nyw.pets.activity.shop.ShopListActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.GetBannerDataUtil;
import com.nyw.pets.activity.util.GetPreferentialDataUtil;
import com.nyw.pets.activity.util.GetShopTypeTitleUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.activity.util.ShopPreferentialUtil;
import com.nyw.pets.activity.util.ShopTypeTitleDataUtil;
import com.nyw.pets.adapter.ShopPreferentialAdapter;
import com.nyw.pets.adapter.ShopTypeTitleAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.DisplayUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.view.ShopPetsMenu;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城
 */
public class ShopActivity extends BaseActivity implements View.OnClickListener {
    private MZBannerView banner;
    //图片集合
    private List<String> images = new ArrayList<String>();

    private RecyclerView rcv_data,rcv_hot_cakes;
    //
    private ShopPreferentialAdapter shopCakesAdapter ;
    private List<ShopPreferentialUtil> shopCakesList=new ArrayList<>();

    //限时优惠和热门榜
    private ShopPreferentialAdapter shopPreferentialAdapter ;
    private List<ShopPreferentialUtil> shopPriferentialList=new ArrayList<>();

    //弹出菜单
    private ShopPetsMenu shopPetsMenu;
    private ImageView iv_menu;
    private TextView tv_title;

    private LinearLayout llt_hot_cakes,llt_preferential,llt_food,llt_snacks,llt_clean,llt_nutrition,
            llt_clearer;
    private ImageView iv_search;

    //动态页码与页数
    private int limit=15;
    private int page=1;
    //banner
    private GetBannerDataUtil getBannerDataUtil;
    //宠物类型
    private String petsType="2";//1是猫，2是狗
    private  String token;
    private MyApplication myApplication;
    //商品分类
    private  GetShopTypeTitleUtil getShopTypeTitleUtil;
    private RecyclerView rcv_type;
    private ShopTypeTitleAdapter shopTypeTitleAdapter;
    private List<ShopTypeTitleDataUtil> shopTypeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        myApplication=(MyApplication)getApplication();
        initView();

    }


    private void initView() {
        rcv_data=findViewById(R.id.rcv_data);
        rcv_hot_cakes=findViewById(R.id.rcv_hot_cakes);
        iv_menu=findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(this);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        llt_hot_cakes=findViewById(R.id.llt_hot_cakes);
        llt_hot_cakes.setOnClickListener(this);
        llt_preferential=findViewById(R.id.llt_preferential);
        llt_preferential.setOnClickListener(this);
        llt_food=findViewById(R.id.llt_food);
        llt_food.setOnClickListener(this);
        llt_snacks=findViewById(R.id.llt_snacks);
        llt_snacks.setOnClickListener(this);
        llt_clean=findViewById(R.id.llt_clean);
        llt_clean.setOnClickListener(this);
        llt_nutrition=findViewById(R.id.llt_nutrition);
        llt_nutrition.setOnClickListener(this);
        llt_clearer=findViewById(R.id.llt_clearer);
        llt_clearer.setOnClickListener(this);
        iv_search=findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        rcv_type=findViewById(R.id.rcv_type);

        //限时优惠
        LinearLayoutManager preferentialManager=  new LinearLayoutManager(this);
        preferentialManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_data.setLayoutManager(preferentialManager);

//        for (int j=0;j<5;j++){
//            ShopPreferentialUtil shopPreferentialUtil=new ShopPreferentialUtil();
//            shopPreferentialUtil.setShopID(""+j);
//            shopPreferentialUtil.setPrice("￥"+2566+j);
//            shopPreferentialUtil.setPreferentialPrice("￥"+589+j);
//            shopPriferentialList.add(shopPreferentialUtil);
//        }
        shopPreferentialAdapter=new ShopPreferentialAdapter(ShopActivity.this,shopPriferentialList);
        rcv_data.setAdapter(shopPreferentialAdapter);



        //热销榜
        LinearLayoutManager layoutManager=  new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_hot_cakes.setLayoutManager(layoutManager);

//        for (int j=0;j<5;j++){
//            ShopPreferentialUtil shopPreferentialUtil=new ShopPreferentialUtil();
//            shopPreferentialUtil.setShopID(""+j);
//            shopPreferentialUtil.setPrice("￥"+2566+j);
//            shopPreferentialUtil.setPreferentialPrice("￥"+589+j);
//            shopCakesList.add(shopPreferentialUtil);
//        }
        shopCakesAdapter=new ShopPreferentialAdapter(ShopActivity.this,shopCakesList);
        rcv_hot_cakes.setAdapter(shopCakesAdapter);

        //商品分类
        LinearLayoutManager layoutManagerType=  new LinearLayoutManager(this);
        layoutManagerType.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_type.setLayoutManager(layoutManagerType);
        shopTypeTitleAdapter=new ShopTypeTitleAdapter(ShopActivity.this,shopTypeList,myApplication);
        rcv_type.setAdapter(shopTypeTitleAdapter);


        shopType();


    }

    private void getShopInfo() {
//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);

        String type_id=null,pets_id=null;
        OkGo.<String>get(Api.GET_SHOP_LIST).tag(this)
                .params("token",token)
                .params("pets_id",pets_id)
                .params("type_id",type_id)
                .params("limit",limit)
                .params("page",page)
                .params("sort","sale_up")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsifsf",data) ;
                        Gson gson=new Gson();
                        getBannerDataUtil= gson.fromJson(data, GetBannerDataUtil.class);
                        if (getBannerDataUtil.getCode()==AppConfig.SUCCESS){

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 产品分类
     */
    private void  shopType(){
        OkGo.<String>get(Api.GET_SHOP_SHOP_TYPE).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                       Log.i("sfjsifntrinvjdl",data);
                       Gson gson=new Gson();

                       try {
                           getShopTypeTitleUtil = gson.fromJson(data, GetShopTypeTitleUtil.class);

                           if (getShopTypeTitleUtil.getCode() == AppConfig.SUCCESS) {

                               for (int i = 0; i < getShopTypeTitleUtil.getData().size(); i++) {
                                   ShopTypeTitleDataUtil shopTypeTitleDataUtil = new ShopTypeTitleDataUtil();
                                   shopTypeTitleDataUtil.setId(getShopTypeTitleUtil.getData().get(i).getId() + "");
                                   shopTypeTitleDataUtil.setImg(getShopTypeTitleUtil.getData().get(i).getImg() + "");
                                   shopTypeTitleDataUtil.setTitle(getShopTypeTitleUtil.getData().get(i).getTitle() + "");
                                   shopTypeTitleDataUtil.setPetsType(petsType);
                                   shopTypeList.add(shopTypeTitleDataUtil);
                               }
                           }
                       }catch (Exception e){}
                        shopTypeTitleAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取热销榜
     */
    private void getHotData(){
//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);

        shopCakesList.clear();
        //discount是优惠，hot是热销
        OkGo.<String>post(Api.GET_SHOP_PREFERENTIAL).tag(this)
                .params("token",token)
                .params("type","hot")
                .params("page",page)
                .params("limit",10)
                .params("pets_id",petsType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjwedfsfsfrtsifsf",data) ;
                        Gson gson=new Gson();
                        GetPreferentialDataUtil   getPreferentialDataUtil= gson.fromJson(data, GetPreferentialDataUtil.class);

                        if (getPreferentialDataUtil.getData()!=null) {
                            for (int i = 0; i < getPreferentialDataUtil.getData().getList().size(); i++) {

                                ShopPreferentialUtil shopPreferentialUtil = new ShopPreferentialUtil();
                                shopPreferentialUtil.setShopID(getPreferentialDataUtil.getData().getList().get(i).getId() + "");
                                shopPreferentialUtil.setPrice(getPreferentialDataUtil.getData().getList().get(i).getPrice() + "");
                                shopPreferentialUtil.setPreferentialPrice(getPreferentialDataUtil.getData().getList().get(i).getOrigin_price() + "");
                                shopPreferentialUtil.setShopImg(myApplication.getImgFilePathUrl() + getPreferentialDataUtil.getData().getList().get(i).getIcon() + "");
                                shopPreferentialUtil.setIsDiscount(getPreferentialDataUtil.getData().getList().get(i).getDiscount() + "");
                                shopCakesList.add(shopPreferentialUtil);

                            }
                        }
                        shopCakesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });

    }

    /**
     * 获取限时优惠
     */
    private void getPreferentialData(){
//        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
//        String token= getUser.getString(SaveAPPData.TOKEN,null);
//        Log.i("sdjfsifsjfsf",token);

        shopPriferentialList.clear();
        //discount是优惠，hot是热销
        OkGo.<String>post(Api.GET_SHOP_PREFERENTIAL).tag(this)
                .params("token",token)
                .params("type","discount")
                .params("page",page)
                .params("limit",10)
                .params("pets_id",petsType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjwereerwtsifsf",data) ;
                        Gson gson=new Gson();
                        GetPreferentialDataUtil   getPreferentialDataUtil= gson.fromJson(data, GetPreferentialDataUtil.class);

                        if (getPreferentialDataUtil.getData()!=null) {
                            for (int i = 0; i < getPreferentialDataUtil.getData().getList().size(); i++) {


                                ShopPreferentialUtil shopPreferentialUtil = new ShopPreferentialUtil();
                                shopPreferentialUtil.setShopID(getPreferentialDataUtil.getData().getList().get(i).getId() + "");
                                shopPreferentialUtil.setPrice(getPreferentialDataUtil.getData().getList().get(i).getPrice() + "");
                                shopPreferentialUtil.setPreferentialPrice(getPreferentialDataUtil.getData().getList().get(i).getOrigin_price() + "");
                                shopPreferentialUtil.setShopImg(myApplication.getImgFilePathUrl() + getPreferentialDataUtil.getData().getList().get(i).getIcon() + "");
                                shopPreferentialUtil.setIsDiscount(getPreferentialDataUtil.getData().getList().get(i).getDiscount() + "");

                                shopPriferentialList.add(shopPreferentialUtil);

                            }
                        }
                        shopPreferentialAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });

    }

    /**
     * 获取banner数据
     */
    private void getBanner() {
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
         token= getUser.getString(SaveAPPData.TOKEN,null);


        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(ShopActivity.this, LoginActivity.class);
            startActivity(login);
            return;
        }
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>get(Api.GET_SHOOP_BANNER).tag(this)
                .params("token",token)
                .params("limit",limit)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                       Log.i("sdfjsifsf",data) ;
                        Gson gson=new Gson();
                         getBannerDataUtil= gson.fromJson(data, GetBannerDataUtil.class);
                         if (getBannerDataUtil.getCode()==AppConfig.SUCCESS){
                             initBanner();
                         }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });
    }
    /**
     * banner
     */
    private void initBanner() {
        banner=findViewById(R.id.banner);
        //设置是否显示Indicator
        banner.setIndicatorVisible(true);
        //设置BannerView 的切换时间间隔
        banner. setDelayedTime(2000);
        //设置指示器显示位置
//        banner.setIndicatorAlign(CENTER);
        //设置ViewPager（Banner）切换速度
        banner.setDuration(500);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {

            @Override
            public void onPageClick(View view, int i) {
                //点击banner打开，这里分别有h5和项目信息
                if (getBannerDataUtil.getData()!=null) {
                        Intent intent = new Intent();
                        intent.setClass(ShopActivity.this, ShopDetailsActivity.class);
                        intent.putExtra("id", getBannerDataUtil.getData().get(i).getShop_id() + "");
                        startActivity(intent);

                }

            }
        });

        //设置banner数据
        images.clear();
        for (int i=0;i<getBannerDataUtil.getData().size();i++){
            images.add( getBannerDataUtil.getData().get(i).getServer()+getBannerDataUtil.getData().get(i).getImage());
        }
        banner.setPages(images, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        banner.start();//开始轮播
    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_menu:
                //选择菜单
                selectMenu();
            case R.id.tv_title:
                selectMenu();
                break;
            case R.id.llt_hot_cakes:
                //热销榜
                Intent intent=new Intent();
                intent.setClass(ShopActivity.this, ShopListActivity.class);
                intent.putExtra("petsType",petsType);
                intent.putExtra("openTypeTitle","热销榜");
                startActivity(intent);
                break;
            case R.id.llt_preferential:
                //限时优惠
                Intent intentType=new Intent();
                intentType.setClass(ShopActivity.this, ShopListActivity.class);
                intentType.putExtra("petsType",petsType);
                intentType.putExtra("openTypeTitle","限时优惠");
                startActivity(intentType);
                break;
            case R.id.llt_food:
                //主食
                Intent intentFood=new Intent();
                intentFood.setClass(ShopActivity.this, SearchShopActivity.class);
                intentFood.putExtra("petsType",petsType);
                intentFood.putExtra("shopType","1");
                startActivity(intentFood);
                break;
            case R.id.llt_snacks:
                //零食
                Intent intentsnacks=new Intent();
                intentsnacks.setClass(ShopActivity.this, SearchShopActivity.class);
                intentsnacks.putExtra("petsType",petsType);
                intentsnacks.putExtra("shopType","2");
                startActivity(intentsnacks);
                break;
            case R.id.llt_clean:
                //清洁
                Intent intentClean=new Intent();
                intentClean.setClass(ShopActivity.this, SearchShopActivity.class);
                intentClean.putExtra("petsType",petsType);
                intentClean.putExtra("shopType","3");
                startActivity(intentClean);
                break;
            case R.id.llt_nutrition:
                //营养
                Intent intentNutrition=new Intent();
                intentNutrition.setClass(ShopActivity.this, SearchShopActivity.class);
                intentNutrition.putExtra("petsType",petsType);
                intentNutrition.putExtra("shopType","4");
                startActivity(intentNutrition);
                break;
            case R.id.llt_clearer:
                //养宠必备
                Intent intentClearer=new Intent();
                intentClearer.setClass(ShopActivity.this, SearchShopActivity.class);
                intentClearer.putExtra("petsType",petsType);
                intentClearer.putExtra("shopType","5");
                startActivity(intentClearer);
                break;
            case R.id.iv_search:
                //搜索商品
                Intent intentSearch=new Intent();
                intentSearch.setClass(ShopActivity.this, SearchShopActivity.class);
                intentSearch.putExtra("petsType",petsType);
                startActivity(intentSearch);
                break;
        }
    }
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(final Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, String s) {
            // 数据绑定
            Glide.with(context).load(s).placeholder(R.mipmap.http_error).error(R.mipmap.http_error)
                    .into(mImageView);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if (banner!=null) {
            banner.pause();//暂停轮播
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
        getBanner();
//        getShopInfo();
        getPreferentialData();
        getHotData();


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(){

        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)){
            //用户没有登录
            Intent login=new Intent();
            login.setClass(ShopActivity.this, LoginActivity.class);
            startActivity(login);
        }
        OkGo.<String>post(Api.GET_USER_INFO).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsifsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            code=  jsonObject.getInt("code");
                            msg=  jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){
                            //登录后的正常获取到数据
                            Gson gson=new Gson();
                            GetUserInfoUtil getUserInfoUtil=  gson.fromJson(data, GetUserInfoUtil.class);

                        }else {
                            //未登录的
                            Intent login=new Intent();
                            login.setClass(ShopActivity.this, LoginActivity.class);
                            startActivity(login);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(ShopActivity.this,"网络错误");
                    }
                });
    }
    /**
     * 选择菜单
     */
    private void selectMenu() {
        if(shopPetsMenu==null) {
            //自定义的单击事件
            OnClickLintener paramOnClickListener = new OnClickLintener();
            //控制窗口大小，第一个是宽，第二个是高
            shopPetsMenu = new ShopPetsMenu(ShopActivity.this,paramOnClickListener,
                    DisplayUtil.dip2px(this, 130), DisplayUtil.dip2px(this, 75));
            //监听窗口的焦点事件，点击窗口外面则取消显示
            shopPetsMenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        shopPetsMenu.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        shopPetsMenu.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        shopPetsMenu.showAsDropDown(iv_menu, 0, 0);
        //如果窗口存在，则更新
        shopPetsMenu.update();
    }
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id){
                case R.id.layout_dog:
                    //狗
                    shopPetsMenu.dismiss();
                    tv_title.setText("狗");
                    petsType="2";
                    iv_menu.setImageResource(R.mipmap.donghead);
                    getPreferentialData();
                    getHotData();
                    break;
                case R.id.layout_cat:
                    //猫
                    shopPetsMenu.dismiss();
                    tv_title.setText("猫");
                    petsType="1";
                    getPreferentialData();
                    getHotData();
                    iv_menu.setImageResource(R.mipmap.cathead);
                    break;
            }
        }
    }
}
