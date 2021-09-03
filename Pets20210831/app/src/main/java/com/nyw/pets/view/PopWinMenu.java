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
 * 选择菜单
 */

public class PopWinMenu extends PopupWindow {
    private View mainView;
    private LinearLayout layout_report,layout_healthAnalysis, layout_Instrucetions;

    public PopWinMenu(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popwin_menu, null);
        //搜索设备
        layout_report= (LinearLayout) mainView.findViewById(R.id.layout_report);
        //数据表展示
        layout_healthAnalysis = ((LinearLayout)mainView.findViewById(R.id.layout_healthAnalysis));
        //使用教程
        layout_Instrucetions = (LinearLayout)mainView.findViewById(R.id.layout_Instrucetions);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layout_healthAnalysis.setOnClickListener(paramOnClickListener);
            layout_Instrucetions.setOnClickListener(paramOnClickListener);
            layout_report.setOnClickListener(paramOnClickListener);
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
