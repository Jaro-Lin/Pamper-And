package com.nyw.pets.fragment.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalong.refreshlayout.OnRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nyw.pets.R;
import com.nyw.pets.activity.shop.util.GetOrderDataInfoUtil;
import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;
import com.nyw.pets.activity.util.GetShopOrderInfoUtil;
import com.nyw.pets.adapter.MyAllOrderAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.AppConfig;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.interfaces.CancelOrderInterface;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 等待发货
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaitingDeliveryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaitingDeliveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingDeliveryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rcv_data;
    private List<GetShopOrderInfoUtil> shopOrderInfoList=new ArrayList<>();
    private MyAllOrderAdapter myAllOrderAdapter;

    private  int page=1,limit=15;
    private  String shopId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //刷新
    private MeiTuanRefreshView refreshview;

    public WaitingDeliveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingDeliveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingDeliveryFragment newInstance(String param1, String param2) {
        WaitingDeliveryFragment fragment = new WaitingDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_waiting_delivery, container, false);

        rcv_data=view.findViewById(R.id.rcv_data);
        refreshview=view.findViewById(R.id.refreshview);
        rcv_data.setLayoutManager(new LinearLayoutManager(getContext()));

//        for (int i=0;i<10;i++){
//            GetShopOrderInfoUtil getShopOrderInfoUtil=new GetShopOrderInfoUtil() ;
//            getShopOrderInfoUtil.setId(i+"");
//            getShopOrderInfoUtil.setTitle("狗狗食物超级好吃的零食营养的食物优惠");
//            getShopOrderInfoUtil.setSpecifications("规格:1000 g");
//            getShopOrderInfoUtil.setOrderState("待付款");
//            getShopOrderInfoUtil.setShopNumber("共1件商品 合计19.9元");
//            getShopOrderInfoUtil.setPrice("￥ 19.9");
//            getShopOrderInfoUtil.setShopImg("");
//
//            shopOrderInfoList.add(getShopOrderInfoUtil);
//        }

        myAllOrderAdapter=new MyAllOrderAdapter(getContext(),shopOrderInfoList);
        rcv_data.setAdapter(myAllOrderAdapter);
        myAllOrderAdapter.setCancelOrderInterface(new CancelOrderInterface() {
            @Override
            public void cancelOrder() {
                //重新加载数据
                shopOrderInfoList.clear();
                getOrderInfo();

            }
        });

        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                shopOrderInfoList.clear();
                getOrderInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getOrderInfo();
            }
        });
        return view;
    }
    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    refreshview.stopRefresh(true);
                    break;
                case 1:
                    refreshview.stopLoadMore(true);
                    break;
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        shopOrderInfoList.clear();
        getOrderInfo();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }
    /**
     * 获取订单数据
     */
    private void getOrderInfo(){

        SharedPreferences getUser=getActivity().getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        //这里是使用RequestBody 请求，把json商品数据传给后台


//    GetShopCommentInfoUtil getShopCommentInfoUtil=new GetShopCommentInfoUtil();
//        getShopCommentInfoUtil.setToken(token);
//        getShopCommentInfoUtil.setModule("shop");
//        getShopCommentInfoUtil.setModule_id(shopId);
//        getShopCommentInfoUtil.setLimit(limit);
//        getShopCommentInfoUtil.setPage(page);
//    String sendData= new Gson().toJson(getShopCommentInfoUtil);

//       Log.i("sdfjsifirewrfwer",sendData);

//    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sendData);
        String url= Api.GET_SHOP_ORDER_LIST;
        Log.i("sdfsiofskfsffg",url);
        OkGo.<String>post(url).tag(this)
                .params("token",token)
                .params("type","hassend")
                .params("page",page)
                .params("limit",limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data= response.body();
                        Log.i("sdfjsfisnvxkcvv",data);
                        Gson gson=new Gson();
                        GetOrderDataInfoUtil getOrderDataInfoUtil=  gson.fromJson(data, GetOrderDataInfoUtil.class);

                        if (getOrderDataInfoUtil.getCode()== AppConfig.SUCCESS){
                            for (int i=0;i<getOrderDataInfoUtil.getData().getList().size();i++){
                                GetShopOrderInfoUtil getShopOrderInfoUtil=new GetShopOrderInfoUtil() ;


                                getShopOrderInfoUtil.setOrderId(getOrderDataInfoUtil.getData().getList().get(i).getOrder_id()+"");
                                getShopOrderInfoUtil.setId(getOrderDataInfoUtil.getData().getList().get(i).getId()+"");
                                getShopOrderInfoUtil.setTotalPrice(getOrderDataInfoUtil.getData().getList().get(i).getTotal_price());
                                getShopOrderInfoUtil.setServer(getOrderDataInfoUtil.getData().getList().get(i).getServer());


                                getShopOrderInfoUtil.setIs_pay(getOrderDataInfoUtil.getData().getList().get(i).getIs_pay()+"");
                                getShopOrderInfoUtil.setIs_send(getOrderDataInfoUtil.getData().getList().get(i).getIs_send()+"");
                                getShopOrderInfoUtil.setIs_put(getOrderDataInfoUtil.getData().getList().get(i).getIs_put()+"");
                                getShopOrderInfoUtil.setIs_comment(getOrderDataInfoUtil.getData().getList().get(i).getIs_comment()+"");
                                getShopOrderInfoUtil.setIs_cancel(getOrderDataInfoUtil.getData().getList().get(i).getIs_cancel()+"");
                                getShopOrderInfoUtil.setIs_salelate(getOrderDataInfoUtil.getData().getList().get(i).getIs_salelate()+"");
                                getShopOrderInfoUtil.setOrderNumber(getOrderDataInfoUtil.getData().getList().get(i).getOrder_id()+"");

                                getShopOrderInfoUtil.setLogistics_id(getOrderDataInfoUtil.getData().getList().get(i).getLogistics_id()+"");
                                getShopOrderInfoUtil.setPut_type(getOrderDataInfoUtil.getData().getList().get(i).getPut_type()+"");

                                //商品数量信息

                                List<MyShopOrderInfoUtil> shopOrderList=new ArrayList<>();

                                for (int j=0;j<  getOrderDataInfoUtil.getData().getList().get(i).getShop_list().size();j++){
                                    MyShopOrderInfoUtil myShopOrderInfoUtil=new MyShopOrderInfoUtil();
                                    myShopOrderInfoUtil.setId( getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getId()+"");
                                    myShopOrderInfoUtil.setTitle(getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getShop().getTitle());
                                    myShopOrderInfoUtil.setPrice(getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getPrice());
                                    myShopOrderInfoUtil.setShopImg(getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getShop().getIcon());
                                    myShopOrderInfoUtil.setShopNumber(getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getNumber()+"");
                                    myShopOrderInfoUtil.setSpecifications(getOrderDataInfoUtil.getData().getList().get(i).getShop_list().get(j).getSpe().getSpe());
                                    myShopOrderInfoUtil.setServer(getOrderDataInfoUtil.getData().getList().get(i).getServer()+"");

                                    myShopOrderInfoUtil.setIs_pay(getOrderDataInfoUtil.getData().getList().get(i).getIs_pay()+"");
                                    myShopOrderInfoUtil.setIs_send(getOrderDataInfoUtil.getData().getList().get(i).getIs_send()+"");
                                    myShopOrderInfoUtil.setIs_put(getOrderDataInfoUtil.getData().getList().get(i).getIs_put()+"");
                                    myShopOrderInfoUtil.setIs_comment(getOrderDataInfoUtil.getData().getList().get(i).getIs_comment()+"");
                                    myShopOrderInfoUtil.setIs_cancel(getOrderDataInfoUtil.getData().getList().get(i).getIs_cancel()+"");
                                    myShopOrderInfoUtil.setIs_salelate(getOrderDataInfoUtil.getData().getList().get(i).getIs_salelate()+"");



                                    shopOrderList.add(myShopOrderInfoUtil);
                                }
                                String shopData=gson.toJson(shopOrderList);
                                Log.i("dfljsifdsjfsfsrtyy",shopData);
                                getShopOrderInfoUtil.setShopOrderList(shopOrderList);
                                getShopOrderInfoUtil.setShopInfo(shopData);
                                shopOrderInfoList.add(getShopOrderInfoUtil);

                            }
                            myAllOrderAdapter.notifyDataSetChanged();


                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (getContext()!=null) {
                            ToastManager.showShortToast(getContext(), "网络错误");
                        }
                    }
                });

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
