package com.nyw.pets.fragment;

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
import com.nyw.pets.activity.util.GetMyCollectionVideoDataUtil;
import com.nyw.pets.activity.util.GetMyCollectionVideoUtil;
import com.nyw.pets.adapter.MyCollectionVideoAdapter;
import com.nyw.pets.config.Api;
import com.nyw.pets.config.SaveAPPData;
import com.nyw.pets.refresh.MeiTuanRefreshView;
import com.nyw.pets.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 视频----收藏
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyVideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rcv_data;
    private MyCollectionVideoAdapter myCollectionVideoAdapter;
    private List<GetMyCollectionVideoUtil> myCollectionList=new ArrayList<>();
    //动态页码与页数
    private int limit=15;
    private int page=1;
    //刷新
    private MeiTuanRefreshView refreshview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyVideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyVideoFragment newInstance(String param1, String param2) {
        MyVideoFragment fragment = new MyVideoFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_video, container, false);
        rcv_data=view.findViewById(R.id.rcv_data);
        refreshview=view.findViewById(R.id.refreshview);
        rcv_data.setLayoutManager(new LinearLayoutManager(getContext()));
//        for (int i=0;i<10;i++){
//            GetMyCollectionVideoUtil collectionVideoUtil=new GetMyCollectionVideoUtil();
//            collectionVideoUtil.setTitle("宠物猫该从何养起该从何养该从何养起该从何养起起？"+i);
//            collectionVideoUtil.setVideoImg("");
//            collectionVideoUtil.setVideoImgPath("");
//            collectionVideoUtil.setIsCollection("");
//            myCollectionList.add(collectionVideoUtil);
//        }
        myCollectionVideoAdapter=new MyCollectionVideoAdapter(getContext(),myCollectionList);
        rcv_data.setAdapter(myCollectionVideoAdapter);

        getData();
        //刷新
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
                page=1;
                myCollectionList.clear();
                getData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
                page++;
                getData();
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

    private void getData() {
        SharedPreferences getUser=getContext().getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token= getUser.getString(SaveAPPData.TOKEN,null);
        Log.i("sdjfsifsjfsf",token);

        OkGo.<String>post(Api.GET_PERSONAL_VIDEO).tag(this)
                .params("token",token)
                .params("limit",limit)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       String data= response.body();
                        Log.i("lsdfjsifsjfsff",data);
                        Gson gson=new Gson();
                        GetMyCollectionVideoDataUtil getMyCollectionVideoDataUtil= gson.fromJson(data,GetMyCollectionVideoDataUtil.class);
                        for (int i=0;i<getMyCollectionVideoDataUtil.getData().getList().size();i++){
                            GetMyCollectionVideoUtil collectionVideoUtil=new GetMyCollectionVideoUtil();
                            collectionVideoUtil.setTitle(getMyCollectionVideoDataUtil.getData().getList().get(i).getTitle());
                            collectionVideoUtil.setVideoImg(getMyCollectionVideoDataUtil.getData().getList().get(i).getImage());
                            collectionVideoUtil.setVideoImgPath(getMyCollectionVideoDataUtil.getData().getList().get(i).getImage());
                            collectionVideoUtil.setIsCollection(getMyCollectionVideoDataUtil.getData().getList().get(i).getIf_collection()+"");
                            collectionVideoUtil.setType_id(getMyCollectionVideoDataUtil.getData().getList().get(i).getType_id()+"");
                            collectionVideoUtil.setVideo_id(getMyCollectionVideoDataUtil.getData().getList().get(i).getVideo_id()+"");
                            myCollectionList.add(collectionVideoUtil);
                        }
                        myCollectionVideoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastManager.showShortToast(getContext(),"网络错误");
                    }
                });
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
