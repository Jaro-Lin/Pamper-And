package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetPetsDataListUtil;
import com.nyw.pets.activity.util.GetPetsListDataUtil;
import com.nyw.pets.adapter.PetsDataListAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义dialog，获取宠物列表显示，首页添加宠物
 * 
 * @author 小宁
 * 
 */
public class GetPetsDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_cancel;
	private TextView txt_updata,txt_updata_message;
	private Button btn_update;
	private String isUpdate=null;
	private RecyclerView rcv_data;
	private List<GetPetsDataListUtil> pestsList=new ArrayList<>();
	private PetsDataListAdapter petsDataListAdapter;
	private int page=1,limit=1000;
	private GetPetsListDataUtil getPetsListDataUtil;
	private int selectNumer=99999;

	/**
	 * init the dialog
	 *
	 * @return
	 */
	public GetPetsDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.get_pets_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		rcv_data=dialog.findViewById(R.id.rcv_data);

		rcv_data.setLayoutManager(new LinearLayoutManager(context));
		getData();
//		for (int i=0;i<10;i++){
//			GetPetsDataListUtil getPetsDataListUtil=new GetPetsDataListUtil();
//			getPetsDataListUtil.setPetsName("小王"+i);
//			pestsList.add(getPetsDataListUtil);
//		}
		petsDataListAdapter=new PetsDataListAdapter(context,pestsList);
		rcv_data.setAdapter(petsDataListAdapter);
		petsDataListAdapter.setSelectPetsInterface(new PetsDataListAdapter.SelectPetsInterface() {
			@Override
			public void selectPetsItem(int i) {
				selectNumer=i;
			}
		});

//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();

			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (selectNumer==99999){
					ToastManager.showShortToast(con,"请选择宠物");
					return;
				}
				if (dialogcallback!=null){
					dialogcallback.selectPets(getPetsListDataUtil,selectNumer);
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
	public void selectPets(GetPetsListDataUtil getPetsListDataUtil,int i);

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

	/**
	 * 获取宠物列表
	 */
	private void getData() {
		SharedPreferences getUser=context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
		String token= getUser.getString(SaveAPPData.TOKEN,null);
//		Log.i("sdjfsifsjfsf",token);



		OkGo.<String>post(Api.GET_PETS_INFO).tag(this)
				.params("token",token)
				.params("page",page)
				.params("limit",limit)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						String data= response.body();
						Log.i("sdfjsifdjsftgvofgtgt",data);
						Gson gson=new Gson();
						 getPetsListDataUtil= gson.fromJson(data, GetPetsListDataUtil.class);

						pestsList.clear();
						if (getPetsListDataUtil.getData()!=null) {
							for (int i = 0; i < getPetsListDataUtil.getData().size(); i++) {
								GetPetsDataListUtil getPetsDataListUtil = new GetPetsDataListUtil();
								getPetsDataListUtil.setPetsName(getPetsListDataUtil.getData().get(i).getNickname());
								pestsList.add(getPetsDataListUtil);
							}
						}

						petsDataListAdapter.notifyDataSetChanged();



					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
						ToastManager.showShortToast(context,"网络错误");
					}
				});
	}


}
