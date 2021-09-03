package com.nyw.pets.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetShopDataUtil;
import com.nyw.pets.activity.util.GetShopBuyTypeUtil;
import com.nyw.pets.adapter.ShopBuyTypeAdapter;
import com.nyw.pets.interfaces.GetShopBuyTypeInterface;
import com.nyw.pets.interfaces.GetShopSpecifications;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义dialog，点击加入购物车，进行购买商品
 * 
 * @author 小宁
 * 
 */
public class GetShopBuyDialog {
	private Context context;
	private Dialogcallback dialogcallback;
	private Dialog dialog;
	private Button btn_cancel,btn_ok;
	private TextView txt_updata,txt_updata_message,tv_number;
	private Button btn_update;
	private String isUpdate=null;
	private RecyclerView rcv_data;
	private List<GetShopBuyTypeUtil> buyTypeList=new ArrayList<>();
	private ShopBuyTypeAdapter shopBuyTypeAdapter;
	private ImageView iv_reduce,iv_increase,iv_img;
	private GetShopBuyTypeInterface getShopBuyTypeInterface;
	private String specifications,specificationsId;
	private int itemNumber=0;
	private TextView tv_price,tv_stock;
	private boolean openType=true;//默认立即购买，则加入购物车

	public void setGetShopBuyTypeInterface(GetShopBuyTypeInterface getShopBuyTypeInterface) {
		this.getShopBuyTypeInterface = getShopBuyTypeInterface;
	}

	/**
	 * init the dialog
	 *
	 * @return
	 */
	public GetShopBuyDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle_buy);
		dialog.setContentView(R.layout.get_shop_buy_dialog);
		//设置dialog以外的不能点击
		dialog.setCancelable(true);
		//设置Dialog从窗体底部弹出
		dialog.getWindow().setGravity( Gravity.BOTTOM);
		//获得窗体的属性
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
		dialog.getWindow().setAttributes(lp);

		txt_updata_message=(TextView) dialog.findViewById(R.id.txt_updata_message);
		btn_update=(Button) dialog.findViewById(R.id.btn_update);
		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		rcv_data=dialog.findViewById(R.id.rcv_data);
		btn_ok=dialog.findViewById(R.id.btn_ok);
		tv_number=dialog.findViewById(R.id.tv_number);
		iv_reduce=dialog.findViewById(R.id.iv_reduce);
		iv_increase=dialog.findViewById(R.id.iv_increase);
		iv_img=dialog.findViewById(R.id.iv_img);
		tv_price=dialog.findViewById(R.id.tv_price);
		tv_stock=dialog.findViewById(R.id.tv_stock);
		iv_increase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//增加
				int number=Integer.parseInt(tv_number.getText().toString())+1;
				tv_number.setText(number+"");

			}
		});
		iv_reduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//减少
				int number=Integer.parseInt(tv_number.getText().toString());
				if (number<=1){
					number=1;
				}else {
					number=number-1;
				}
				tv_number.setText(number+"");

			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(specifications)||TextUtils.isEmpty(specificationsId)){
					ToastManager.showShortToast(context,"请选择规格");
					return;
				}
				dialog.dismiss();
				if (getShopBuyTypeInterface!=null){
					getShopBuyTypeInterface.getShopBuyType(openType,tv_number.getText().toString(),itemNumber,specifications,specificationsId);
				}
			}
		});

		GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
		rcv_data.setLayoutManager(gridLayoutManager);
//		for (int i=0;i<6;i++){
//			GetShopBuyTypeUtil getPetsDataListUtil=new GetShopBuyTypeUtil();
//			getPetsDataListUtil.setTitle("1000"+i+"g");
//			buyTypeList.add(getPetsDataListUtil);
//		}
		shopBuyTypeAdapter=new ShopBuyTypeAdapter(context,buyTypeList);
		rcv_data.setAdapter(shopBuyTypeAdapter);
		shopBuyTypeAdapter.setGetShopSpecifications(new GetShopSpecifications() {
			@Override
			public void getSpecifications(String id, String price,String stock,int i, String mySpecifications) {
				specifications=mySpecifications;
				specificationsId=id;
				itemNumber=i;
				tv_price.setText("￥  "+price);
				tv_stock.setText("库存 "+stock+" 件");
			}
		});

//		img_Qr_code = (ImageView)dialog.findViewById(R.id.img_Qr_code);
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


			}
		});
		//更新
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
	public void getDataInfo(String spe,String id);

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

	/**
	 * 显示规格
	 * @return
	 */
	public String setData(int type,GetShopDataUtil getShopDataUtil,String shopImg,String price,String stock) {
		buyTypeList.clear();
		if(type==0) {
			openType =true;
		}else {
			openType =false;
		}
		for (int i=0;i<getShopDataUtil.getData().getSpe().size();i++){
			GetShopBuyTypeUtil getPetsDataListUtil=new GetShopBuyTypeUtil();
			getPetsDataListUtil.setTitle(getShopDataUtil.getData().getSpe().get(i).getSpe());
			getPetsDataListUtil.setId(getShopDataUtil.getData().getSpe().get(i).getId()+"");
			getPetsDataListUtil.setShop_id(getShopDataUtil.getData().getSpe().get(i).getShop_id()+"");
			getPetsDataListUtil.setPrice(getShopDataUtil.getData().getSpe().get(i).getPrice()+"");
			getPetsDataListUtil.setStock(getShopDataUtil.getData().getSpe().get(i).getStock()+"");
			buyTypeList.add(getPetsDataListUtil);
		}
		shopBuyTypeAdapter.notifyDataSetChanged();

		//显示图片
		Glide.with(context).load(shopImg).placeholder(R.mipmap.http_error).error(R.mipmap.http_error).into(iv_img);
		tv_price.setText("￥  "+price);
		tv_stock.setText("库存 "+stock+" 件");

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
