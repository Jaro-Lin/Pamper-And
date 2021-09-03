package com.nyw.pets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.MyApplication;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetSginDataUtil;
import com.nyw.pets.activity.user.AddPetsActivity;
import com.nyw.pets.activity.user.LoginActivity;
import com.nyw.pets.activity.util.GetDefaultInfoUtil;
import com.nyw.pets.activity.util.GetPetsListDataUtil;
import com.nyw.pets.activity.util.GetUserInfoUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.fragment.util.IndexFragmentUtil;
import com.nyw.pets.util.ToastManager;
import com.nyw.pets.util.getVistionUtil;
import com.nyw.pets.view.GetPetsDialog;
import com.nyw.pets.view.No_SignDialog;
import com.nyw.pets.view.SignDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首页
 */
public class IndexActivity extends BaseFragmentActivity implements View.OnClickListener {
    private String[] s=new String[]{"推荐","关注","同城"};
    private List<Fragment> list;
    private TabLayout mTablayout;
    private ViewPager viewpager;
    private MyPagerAdapter mAdapter;
    private int selectPosition;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView iv_sign;
    private  SignDialog signDialog;
    private No_SignDialog no_signDialog;
    //添加宠物
    private LinearLayout llt_add_pets,llt_otherPets,llt_pets,llt_no_pets,llt_search;
    private GetPetsDialog getPetsDialog;
    //发布动态消息
    private ImageView iv_send_data;
    private Button btn_add_pets;
    //分页
    private int page=1,limit=100;
    //获取用户资料信息
    private  GetUserInfoUtil getUserInfoUtil;

    //是否点击签到
    private boolean isClickSgin=false;
    //签到结果
    private GetSginDataUtil getSginDataUtil;
    private TextView tv_state,tv_msg,tv_sex,tv_name;
    private ImageView iv_img;
    private MyApplication myApplication;
    //获取宠物信息列表
    private  GetPetsListDataUtil getPetsListDataUtil;
    //是否更新APP
    private  boolean isUpeate=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        new getVistionUtil(IndexActivity.this, Api.GET_OTHER_APP_VERSION).checkUpdate(false);
        myApplication= (MyApplication) getApplication();
//        Bundle bundle= getIntent().getExtras();
//        selectPosition=  bundle.getInt("position");

        Log.i("sfsifsnfsfsf",isUpeate+"");
        initView();
        initTitle();



    }

    @Override
    protected void onResume() {
        super.onResume();

        isLoginInfo();

        getUserInfo();
        getPetsInfo();


    }

    private void initView() {
        mTabLayout= (TabLayout) findViewById(R.id.mTable);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        iv_send_data=findViewById(R.id.iv_send_data);
        iv_send_data.setOnClickListener(this);
        llt_search=findViewById(R.id.llt_search);
        llt_search.setOnClickListener(this);
        tv_state=findViewById(R.id.tv_state);
        tv_msg=findViewById(R.id.tv_msg);
        tv_sex=findViewById(R.id.tv_sex);
        tv_name=findViewById(R.id.tv_name);
        iv_img=findViewById(R.id.iv_img);


        iv_sign=findViewById(R.id.iv_sign);
        iv_sign.setOnClickListener(this);
        llt_add_pets=findViewById(R.id.llt_add_pets);
        llt_add_pets.setOnClickListener(this);
        llt_otherPets=findViewById(R.id.llt_otherPets);
        llt_otherPets.setOnClickListener(this);
        llt_pets=findViewById(R.id.llt_pets);
        llt_no_pets=findViewById(R.id.llt_no_pets);
        btn_add_pets=findViewById(R.id.btn_add_pets);
        btn_add_pets.setOnClickListener(this);




         signDialog=new SignDialog(IndexActivity.this);
         no_signDialog=new No_SignDialog(IndexActivity.this);
        no_signDialog.setDialogCallback(new No_SignDialog.Dialogcallback() {
            @Override
            public void addSgin(String string) {
                //签到
                getUserSginInfo();
            }
        });
//         getPetsDialog=new GetPetsDialog(IndexActivity.this);

    }
    @SuppressLint("NewApi")
    private void initTitle() {
//        list=new ArrayList<>();
//        list.add(new RecommendFragment());
//        list.add(new FollowFragment());
//        list.add(new SameCityFragment());
//        MyAdapter adapter=new MyAdapter(getSupportFragmentManager(),list,s);
//        viewpager.setAdapter(adapter);
//        mTablayout.setupWithViewPager(viewpager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //1，设置Tab的标题来自PagerAdapter.getPageTitle()
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        //2，设置TabLayout的选项卡监听
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition(),false);
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        //3，设置TabLayout.TabLayoutOnPageChangeListener监听给ViewPager
//        TabLayout.TabLayoutOnPageChangeListener listener =
//                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
//        mViewPager.addOnPageChangeListener(listener);
        //4，viewpager设置适配器
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(selectPosition,true);
//        mViewPager.set
        //这个方法是addOnPageChangeListener和setOnTabSelectedListener的封装。代替2,3步骤
        mTabLayout.getTabAt(selectPosition).select();
        mTabLayout.setTextAlignment(selectPosition);
        //设置顶部标签指示条的颜色和选中页面时标签字体颜色
        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setOffscreenPageLimit(0);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_sign:
                //签到
                isClickSgin=true;
                getUserInfo();


                break;
            case R.id.llt_add_pets:
                //添加宠物
                Intent intent=new Intent();
                intent.setClass(IndexActivity.this, AddPetsActivity.class);
                startActivity(intent);
                break;
            case R.id.llt_otherPets:
                //选择其他的宠物
                if (getPetsListDataUtil==null){
                    ToastManager.showShortToast(IndexActivity.this,"没有宠物，请您添加宠物");
                    return;
                }else if (getPetsListDataUtil.getData().size()<=0){
                    ToastManager.showShortToast(IndexActivity.this,"没有宠物，请您添加宠物");
                    return;
                }
                getPetsDialog=null;
                getPetsDialog=new GetPetsDialog(IndexActivity.this);
                getPetsDialog.show();
                getPetsDialog.setDialogCallback(new GetPetsDialog.Dialogcallback() {
                    @Override
                    public void selectPets(GetPetsListDataUtil getPetsListDataUtil,int selectNumer) {
                        //选择宠物返回显示
                        tv_name.setText(getPetsListDataUtil.getData().get(selectNumer).getNickname());
                        tv_sex.setText("今天已经"+getPetsListDataUtil.getData().get(selectNumer).getSex()+"岁了");
                        tv_msg.setText("相当于人类"+getPetsListDataUtil.getData().get(selectNumer).getAge()+"岁");
                        getDefaultPetsInfo(getPetsListDataUtil.getData().get(selectNumer).getId()+"");
                        String msg="";
//                        if (getPetsListDataUtil.getData().get(selectNumer).get())

//                        tv_state.setText();
                        Glide.with(IndexActivity.this).load(myApplication.getImgFilePathUrl()+getPetsListDataUtil.getData().get(selectNumer)
                        .getAvatar()).placeholder(R.mipmap.gougou_logo).error(R.mipmap.gougou_logo).into(iv_img);
                    }
                });
                break;
            case R.id.iv_send_data:
                //发布
                Intent send=new Intent();
                send.setClass(IndexActivity.this,SendDataActivity.class);
                startActivity(send);
                break;
            case R.id.btn_add_pets:
                //无宠物 的时候，添加宠物
                Intent intentAdd=new Intent();
                intentAdd.setClass(IndexActivity.this, AddPetsActivity.class);
                startActivity(intentAdd);
                break;
            case R.id.llt_search:
                //搜索动态
                Intent search=new Intent();
                search.setClass(IndexActivity.this, SearchDynamicActivity.class);
                startActivity(search);
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        FragmentManager fm;
        int current;

        private final List<String> order = new ArrayList<String>();
        private final List<Fragment> mFragment = new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
            order.add("推荐");
            order.add("关注");
            order.add("同城");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            current = position;
            return order.get(position);
        }

        @Override
        public int getCount() {
            return order.size();
        }

        @Override
        public Fragment getItem(int position) {
            mFragment.add(IndexFragmentUtil.createFragment(position));
            return IndexFragmentUtil.createFragment(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = IndexFragmentUtil.createFragment(position);
            //添加新fragment时必须用前面获得的tag，这点很重要
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
//            ft.commit();
            ft.commitAllowingStateLoss();
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    /**
     * 获取用户信息,判断是否登录
     */
    private void isLoginInfo(){

        isUpeate=myApplication.isUpdateApp();
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdjfsifsjfsf", token);
        }
        if (TextUtils.isEmpty(token)){
            //用户没有登录

                Intent login = new Intent();
                login.setClass(IndexActivity.this, LoginActivity.class);
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
                            login.setClass(IndexActivity.this, LoginActivity.class);
                            startActivity(login);


                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(IndexActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 签到
     */
    private void getUserSginInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(Api.ADD_SGIN_INFO_USER).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsiertfsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            msg=  jsonObject.getString("message");
                            code=  jsonObject.getInt("code");

                            if (code==AppConfig.SUCCESS){
                                Gson gson=new Gson();
                                 getSginDataUtil= gson.fromJson(data,GetSginDataUtil.class);


                                //签到总天数
                              String total =getSginDataUtil.getData().getTotal()+"";
                              //是否重复签到
                                int sgin=getSginDataUtil.getData().isSign();
                                if (sgin==1){
                                    //签到成功
                                    signDialog.show();
                                    signDialog.setData(getSginDataUtil);


                                }else {
                                    //今天已经签到
                                    ToastManager.showShortToast(IndexActivity.this,"今天已经签到了");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        if (code== AppConfig.SUCCESS){
//
//                            signDialog.show();
//                            signDialog.setData(getSginDataUtil);
//                        }else {
//                            no_signDialog.show();
//                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(IndexActivity.this,"网络错误");
                    }
                });
    }

    /**
     * 获取用户信息,处理是否有签到 的
     */
    private void getUserInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        if (!TextUtils.isEmpty(token)) {
            Log.i("sdfjsifsnvxcxnvrdfdf", token);
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
                             msg=  jsonObject.getString("message");
                             code=  jsonObject.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){
                            Gson gson=new Gson();
                             getUserInfoUtil= gson.fromJson(data, GetUserInfoUtil.class);

                        }


                        if (isClickSgin==true) {
                            isClickSgin=false;
                            String last_sigin_day = getUserInfoUtil.getData().getLast_sign_day();

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
                            //获取当前时间
                            Date date = new Date(System.currentTimeMillis());
                            String time = simpleDateFormat.format(date);
                            Log.i("sfdsjifsnfmsfsf", "time  " + time);


                            if (last_sigin_day != null) {
                                if (!last_sigin_day.equals("null")) {
                                    last_sigin_day = last_sigin_day.replace("-", "");
                                    Log.i("sfdsjifsnfmsfsf", "last_sigin_day  " + last_sigin_day);

                                    if (!last_sigin_day.equals(time)) {
                                        //未签到
                                        no_signDialog.show();
                                        no_signDialog.setMessage("你已经签到"+getUserInfoUtil.getData().getCoupon_count()+"天");
                                    }else {
                                        ToastManager.showShortToast(IndexActivity.this,"今天已经签到了");
                                    }
                                }
                            } else {
                                //未签到
                                no_signDialog.show();
                                no_signDialog.setMessage("你已经签到"+getUserInfoUtil.getData().getCoupon_count()+"天");
                            }
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(IndexActivity.this, "网络错误，无法获取数据");
                    }
                });
    }

    /**
     * 获取宠物信息
     */
    private void getPetsInfo(){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(Api.GET_PETS_INFO).tag(this)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsifyuisjfsf",data);
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
                        if (code== AppConfig.SUCCESS) {
                            Gson gson = new Gson();
                             getPetsListDataUtil = gson.fromJson(data, GetPetsListDataUtil.class);
                            if (getPetsListDataUtil.getData().size() > 0) {
                                //有宠物
                                llt_pets.setVisibility(View.VISIBLE);
                                llt_no_pets.setVisibility(View.GONE);
                               String pets_id= myApplication.getPets_id();
                               if (TextUtils.isEmpty(pets_id)) {
                                   getDefaultPetsInfo("");
                               }else {
                                   getDefaultPetsInfo(pets_id);
                               }
                            } else {
                                //无宠物
                                llt_pets.setVisibility(View.GONE);
                                llt_no_pets.setVisibility(View.VISIBLE);
                            }

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 获取默认宠物信息或查看宠物详细信息
     */
    private void getDefaultPetsInfo(String mypets_id){
        SharedPreferences getUser=getSharedPreferences(SaveAPPData.USER_ID_Token,MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);

        OkGo.<String>post(Api.GET_PETS_ABOUT_GET_DEFAULT).tag(this)
                .params("token",token)
                .params("mypets_id",mypets_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data=response.body();
                        Log.i("sdjfsidferqssffsjfsf",data);
                        JSONObject jsonObject=null;
                        int code = 0;
                        String msg=null;
                        try {
                            jsonObject=new JSONObject(data);
                            msg=  jsonObject.getString("message");
                            code=  jsonObject.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code== AppConfig.SUCCESS){
                            //请求成功
                            Gson gson=new Gson();
                            GetDefaultInfoUtil getDefaultInfoUtil=  gson.fromJson(data, GetDefaultInfoUtil.class);
                            myApplication.setPets_id(getDefaultInfoUtil.getData().getId()+"");
                            myApplication.setIs_init(getDefaultInfoUtil.getData().getIs_init()+"");
                            tv_name.setText(getDefaultInfoUtil.getData().getNickname());
                            tv_sex.setText("今天已经"+getDefaultInfoUtil.getData().getReal_sex()+"岁了");
                            tv_msg.setText("相当于人类"+getDefaultInfoUtil.getData().getAge()+"岁");
                            String petsMsg="";

                            if (getDefaultInfoUtil.getData().getDo_some().getBath()==0){
                                //需要洗澡
                                petsMsg=petsMsg+" 洗澡";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getExpelling()==0){
                                //需要驱虫
                                petsMsg=petsMsg+" 驱虫";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getVaccin_1()==0){
                                //需要狂犬疫苗
                                petsMsg=petsMsg+" 狂犬疫苗";
                            }
                            if (getDefaultInfoUtil.getData().getDo_some().getVaccin_0()==0){
                                //需要三联or六联疫苗
                                petsMsg=petsMsg+" 三联和六联疫苗";
                            }
                            tv_state.setText("应进行 "+petsMsg);
                            Glide.with(IndexActivity.this).load(myApplication.getImgFilePathUrl()+getDefaultInfoUtil.getData().getAvatar()
                                   ).placeholder(R.mipmap.gougou_logo).error(R.mipmap.gougou_logo).into(iv_img);

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(IndexActivity.this,"网络错误");
                    }
                });
    }

}
