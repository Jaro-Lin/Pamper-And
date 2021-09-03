package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetSginDataUtil;
import com.nyw.pets.config.AppConfig;


/**
 * 自定义dialog，签到弹窗
 * 
 * @author 小宁
 * 
 */
public class SignDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_cancel;
	private TextView txt_updata,txt_updata_message,tv_coupons;
	private Button btn_update;
	private String isUpdate=null;
	private LinearLayout llt_sginOk;

	/**
	 * init the dialog
	 *
	 * @return
	 */
	public SignDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.user_sign_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		tv_coupons=dialog.findViewById(R.id.tv_coupons);
		llt_sginOk=dialog.findViewById(R.id.llt_sginOk);

//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//dialogcallback.dialogdo(editText.getText().toString());
				//如果后台传1到这里来，代表需要强制更新APP，0不强制更新
				if (isUpdate==null){
					dismiss();
					return;
				}
				if (isUpdate.equals(AppConfig.UPDATE_APP)){
					dismiss();
					dialogcallback.exit();
				}else {
					dismiss();
				}

			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				dialogcallback.updata();
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
	public void dialogdo(String string);
	public void updata();
	public  void exit();
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
	public void  setData(GetSginDataUtil getSginDataUtil) {
		//return editText.getText().toString();
		txt_updata_message.setText("你已经签到 "+getSginDataUtil.getData().getTotal()+" 天");
		if (getSginDataUtil.getData().getCoupon()!=null) {
			tv_coupons.setText("恭喜你获得一张 " + getSginDataUtil.getData().getCoupon().getPrice() + " 元优惠卷");
			llt_sginOk.setVisibility(View.VISIBLE);
//			Toast.makeText(context,"有数据",Toast.LENGTH_SHORT).show();
		}else {
			llt_sginOk.setVisibility(View.GONE);
//			Toast.makeText(context,"空空",Toast.LENGTH_SHORT).show();
		}

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
