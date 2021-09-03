package com.nyw.pets.view;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetWechatDataUtil;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.util.ToastManager;


/**
 * 自定义dialog，联系客服弹窗
 * 
 * @author 小宁
 * 
 */
public class ServiceUsDialog {
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
	public ServiceUsDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.user_service_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);

		getServerWechat();
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
	 * 获取客服微信联系方式
	 *
	 */
	private void getServerWechat() {
		String url= Api.GET_ABOUT_USER_SERVE_WECHAT;
		OkGo.<String>get(url).tag(this)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						String data = response.body();
						Log.i("smdfnkgfgdisfdsfg", data);

						Gson gson=new Gson();
						GetWechatDataUtil getWechatDataUtil=gson.fromJson(data,GetWechatDataUtil.class);
						txt_updata_message.setText("已成功复制客服微信号 "+getWechatDataUtil.getData().getServe_wechat()+" 请到微信添加客服好友");
						ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
//                cmb.setText(mProductDetailsUtil.getBanner().get(0).getShoppingBannerLink());
						cmb.setText(getWechatDataUtil.getData().getServe_wechat());
					}
					@Override
					public void onError(Response<String> response) {
						super.onError(response);
						ToastManager.showShortToast(context,"网络错误");
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
