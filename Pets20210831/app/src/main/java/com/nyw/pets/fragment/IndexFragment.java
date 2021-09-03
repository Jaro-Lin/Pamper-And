package com.nyw.pets.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nyw.pets.R;
import com.nyw.pets.activity.BaseFragmentActivity;
import com.nyw.pets.fragment.util.IndexFragmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统消息
 */
public class IndexFragment extends BaseFragmentActivity implements View.OnClickListener{
    private ImageView iv_hide;
    private List<String> mTitle = new ArrayList<String>();
    private List<String> mDatas = new ArrayList<String>();

    private MyPagerAdapter mAdapter;
    private int selectPosition;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_index);
        Bundle bundle= getIntent().getExtras();
        selectPosition=  bundle.getInt("position");

        mTabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);

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
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                //关闭
                finish();
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
            ft.commit();
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
