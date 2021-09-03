package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nyw.pets.R;


/**
 * 自定义dialog，未签到弹窗
 * 
 * @author 小宁
 * 
 */
public class No_SignDialog {
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
	public No_SignDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.user_no_sign_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//dialogcallback.dialogdo(editText.getText().toString());
				//如果后台传1到这里来，代表需要强制更新APP，0不强制更新

			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				dialogcallback.updata();
				if (dialogcallback!=null){
					dialogcallback.addSgin("");
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
	public void addSgin(String string);

}

	public void setDialogCallback(Dialogcallback dialogcallback) {
		this.dialogcallback = dialogcallback;
	}
	public void setMessage(String msg){
		txt_updata_message.setText(msg);

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
