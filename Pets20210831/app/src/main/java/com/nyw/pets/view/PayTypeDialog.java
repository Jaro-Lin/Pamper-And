package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.nyw.pets.R;


/**
 * 自定义dialog，选择支付方式
 * 
 * @author 小宁
 * 
 */
public class PayTypeDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_ok;
	private CheckBox cb_select,cb_selectweixin;
	private LinearLayout llt_weixin,llt_pay_treasure;
	//默认支付宝为false
	private boolean isPayType=false;




	/**
	 * init the dialog
	 *
	 * @return
	 */
	public PayTypeDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle_buy);
		dialog.setContentView(R.layout.pay_type_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(true);
		//设置Dialog从窗体底部弹出
		dialog.getWindow().setGravity( Gravity.BOTTOM);
		//获得窗体的属性
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
		dialog.getWindow().setAttributes(lp);

		btn_ok=dialog.findViewById(R.id.btn_ok);
		cb_select=dialog.findViewById(R.id.cb_select);
		cb_selectweixin=dialog.findViewById(R.id.cb_selectweixin);
		llt_weixin=dialog.findViewById(R.id.llt_weixin);
		llt_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击选择微信支付
				cb_select.setChecked(false);
				cb_selectweixin.setChecked(true);
				isPayType=true;
			}
		});

		llt_pay_treasure=dialog.findViewById(R.id.llt_pay_treasure);
		llt_pay_treasure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//用户选择支付宝支付
				cb_select.setChecked(true);
				cb_selectweixin.setChecked(false);
				isPayType=false;

			}
		});

		cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//支付宝支付
				if (isChecked==true){
					isPayType=false;
					cb_selectweixin.setChecked(false);
				}
			}
		});

		cb_selectweixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//微信支付
				if (isChecked==true){
					isPayType=true;
					cb_select.setChecked(false);
				}
			}
		});




		//支付
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialogcallback!=null){
					dialogcallback.payType(isPayType);
				}
				dismiss();
			}
		});
	}

/**
 * 设定一个interfack接口,使mydialog可以處理activity定義的事情
 *
 * @author sfshine
 *
 */
public interface Dialogcallback {
	public void payType(boolean payType);
}

	public void setDialogCallback(Dialogcallback dialogcallback) {
		this.dialogcallback = dialogcallback;
	}
	public void setMessage(String versionName,String versionCode,String versoinMessage,String forceUpdate){

	}

	/**
	 * Get the Text of the EditText
	 * */
	public String getText() {
		//return editText.getText().toString();
		return null;
	}

	public void show() {
		dialog.show();
	}

	public void hide() {
		dialog.hide();
	}

	public void dismiss() {
		dialog.dismiss();

	}

}
