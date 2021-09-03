package com.nyw.pets.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nyw.pets.R;


/**
 * Created by nyw on 2017/7/27.
 * 宠物选择菜单
 */

public class UpdatePetsMenu extends PopupWindow {
    private View mainView;
    private LinearLayout layout_switch,layout_bath, layout_deworming;

    public UpdatePetsMenu(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.update_pets_menu, null);
        //搜索设备
        layout_switch= (LinearLayout) mainView.findViewById(R.id.layout_switch);
        //数据表展示
        layout_bath = ((LinearLayout)mainView.findViewById(R.id.layout_bath));
        //使用教程
        layout_deworming = (LinearLayout)mainView.findViewById(R.id.layout_deworming);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layout_bath.setOnClickListener(paramOnClickListener);
            layout_deworming.setOnClickListener(paramOnClickListener);
            layout_switch.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
