package com.nyw.pets.view;
/*
 *Created by haseeon 2019/1/31.
 */


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nyw.pets.R;


public class PhoneNumDialog {
    private Context context;
    private Dialog dialog;
    private OnClickListener onClickListener;
    protected Button btn_cancel,btn_confirm;
    private TextView tv_content;

    public void setBtn_cancel(String str) {
        btn_cancel.setText(str);
    }

    public void setBtn_confirm(String str) {
        btn_confirm.setText(str);
    }

    public PhoneNumDialog(Context context) {
        this.context = context;
        dialog=new Dialog(context, R.style.mydialog_style);
        dialog.setContentView(R.layout.dialog_phone_num);
        dialog.setTitle("拨打电话");
        dialog.setCancelable(false);
        tv_content=(TextView)dialog.findViewById(R.id.tv_content);
        btn_cancel=(Button)dialog.findViewById(R.id.btn_cancel);
        btn_confirm=(Button)dialog.findViewById(R.id.btn_confirm);




        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick();
            }
        });
    }

    public void setTv_content(String content){
        tv_content.setText(content);
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }
    public interface OnClickListener{
        void onClick();
        void dismiss();
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
