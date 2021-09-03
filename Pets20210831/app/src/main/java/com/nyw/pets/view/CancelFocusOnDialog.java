package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nyw.pets.R;


/**
 * 自定义dialog，取消关注弹窗显示
 * 
 * @author 小宁
 * 
 */
public class CancelFocusOnDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_cancel;
	private TextView txt_updata,txt_updata_message;
	private Button btn_update;
	private String isUpdate=null;

	/**
	 * init the dialog
	 *
	 * @return
	 */
	public CancelFocusOnDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.cancel_focus_on_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		返回
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//返回
				dismiss();


			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialogcallback!=null){
					dialogcallback.cancelFocusOn("");
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
	public void cancelFocusOn(String string);
}

	public void setDialogCallback(Dialogcallback dialogcallback) {
		this.dialogcallback = dialogcallback;
	}
	public void setMessage(String versionName,String versionCode,String versoinMessage,String forceUpdate){
		txt_updata_message.setText(versoinMessage);
		isUpdate=forceUpdate;
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
