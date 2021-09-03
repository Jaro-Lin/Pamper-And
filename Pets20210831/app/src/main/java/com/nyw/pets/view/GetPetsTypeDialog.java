package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetPetsDataListUtil;
import com.nyw.pets.adapter.AddPetsDataAdapter;
import com.nyw.pets.interfaces.GetPetsInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义dialog，获取宠物列表显示宠物类型，首页添加宠物
 * 
 * @author 小宁
 * 
 */
public class GetPetsTypeDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_cancel;
	private TextView txt_updata,txt_updata_message;
	private Button btn_update;
	private String isUpdate=null;
	private RecyclerView rcv_data;
	private List<GetPetsDataListUtil> pestsList=new ArrayList<>();
	private AddPetsDataAdapter addPetsDataAdapter;
	//宠物名字
	private String getPetsName,getPetsId;


	/**
	 * init the dialog
	 *
	 * @return
	 */
	public GetPetsTypeDialog(Context con, List<GetPetsDataListUtil> pestsList) {
		this.context = con;
		dialog = new Dialog(context, R.style.mydialog_style);
		dialog.setContentView(R.layout.get_pets_type_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(false);
		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		rcv_data=dialog.findViewById(R.id.rcv_data);

		rcv_data.setLayoutManager(new LinearLayoutManager(context));
//		for (int i=0;i<10;i++){
//			GetPetsDataListUtil getPetsDataListUtil=new GetPetsDataListUtil();
//			getPetsDataListUtil.setPetsName("小王"+i);
//			pestsList.add(getPetsDataListUtil);
//		}
		addPetsDataAdapter=new AddPetsDataAdapter(context,pestsList);
		rcv_data.setAdapter(addPetsDataAdapter);
		addPetsDataAdapter.setGetPetsInterface(new GetPetsInterface() {
			@Override
			public void ok(String data, String id) {
				getPetsName=data;
				getPetsId=id;
			}

		});

//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//dialogcallback.dialogdo(editText.getText().toString());
				dismiss();
			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialogcallback!=null) {
					dialogcallback.ok(getPetsName,getPetsId);
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
	public void ok(String data,String id);
}

	public void setDialogCallback(Dialogcallback dialogcallback) {
		this.dialogcallback = dialogcallback;
	}
	public void setMessage(List<GetPetsDataListUtil>  pestsList){
	   this.pestsList=pestsList;
		addPetsDataAdapter.notifyDataSetChanged();

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
