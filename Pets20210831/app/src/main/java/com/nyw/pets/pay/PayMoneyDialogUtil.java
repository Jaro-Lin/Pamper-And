package com.nyw.pets.pay;

import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.interfaces.PayMoneyInterface;
import com.nyw.pets.view.CommonDialog;
import com.nyw.pets.view.PasswordEditText;
import com.nyw.pets.view.XNumberKeyboardView;


/**
 * Created by Administrator on 2017/12/6.
 */

public class PayMoneyDialogUtil implements XNumberKeyboardView.IOnKeyboardListener
        , PasswordEditText.PasswordFullListener{
    private XNumberKeyboardView keyboardView;
    private EditText editText;
    private PasswordEditText mPasswordEditText;
    private  CommonDialog.Builder builder;
    private Activity activity;
    public PayMoneyDialogUtil(Activity activity){
        this.activity=activity;
    }
    public PayMoneyInterface checkPayPasswordListener;


    public void setCheckPayPasswordListener(PayMoneyInterface checkPayPasswordListener) {
        this.checkPayPasswordListener = checkPayPasswordListener;
    }

    @Override
    public void passwordFull(String password) {
//        Toast.makeText(activity, "密码输入完毕：" + password, Toast.LENGTH_SHORT).show();
        builder.dismiss();
        if(checkPayPasswordListener!=null){
            checkPayPasswordListener.inputPasswordResult(password);
        }
    }
    @Override
    public void onInsertKeyEvent(String text) {
        mPasswordEditText.append(text);
    }

    @Override
    public void onDeleteKeyEvent() {
        int start = mPasswordEditText.length() - 1;
        if (start >= 0) {
            mPasswordEditText.getText().delete(start, start + 1);
        }
//		Toast.makeText(IndexActivity.this, mPasswordEditText.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开支付弹窗
     */
    public void payDialog(int type,String title){
        builder = new CommonDialog.Builder(activity).fullWidth().fromBottom()
                .setView(R.layout.pay_password_dialog);
        builder.create().show();
        keyboardView = (XNumberKeyboardView)builder.getView(R.id.view_keyboard);
       TextView tv_title=builder.getView(R.id.tv_title);
        if (type==0) {
            tv_title.setText(title);
        }
        keyboardView.setIOnKeyboardListener(this);
        keyboardView.shuffleKeyboard();
        mPasswordEditText =  builder.getView(R.id.password_edit_text);
        //禁止输入法
        mPasswordEditText.setInputType(InputType.TYPE_NULL);
        mPasswordEditText.setOnPasswordFullListener(this);
        builder.setOnClickListener(R.id.delete_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

            }
        });

    }
}
