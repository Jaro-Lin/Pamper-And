package com.nyw.pets.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 这是首页的粘性布局
 */
public class Vpadapter extends PagerAdapter {
    private List<ImageView> list;
    @Override
    public int getCount() {
        return 3;
    }
    public Vpadapter(List<ImageView> list) {
        this.list = list;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        //添加页卡
        container.addView( list.get(position), 0);
        return list.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        //删除页卡
        container.removeView(list.get(position));
    }
}
