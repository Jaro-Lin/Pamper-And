package com.nyw.pets.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nyw.pets.R;
import com.nyw.pets.util.BondUtil;

import java.util.List;

/**
 * 我的明细
 * Created by Administrator on 2016/12/5.
 */

public class BondAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BondUtil> data;
    //普通Item View
    private static final int TYPE_ITEM = 0;
    //底部上拉加载view
    private static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    ////加载完成已经没有更多数据了
    public static final int  NO_MORE_DATA=2;
    //第一次执行下拉刷新，隐藏上拉加载
    public static final int  NULL_MORE_DATA=3;
    //上拉加载更多隐藏状态-默认为3
    private int load_more_status=3;
    //如果请求服务器失败，提示网络错误，请刷新重试
    public static final int  ERROR_NETWORK_MORE_DATA=4;
    //是否可以上拉加载,默认可以加载
    private boolean isloading=true;

    public boolean isIsloading() {
        return isloading;
    }

    public BondAdapter(Context context, List<BondUtil> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if(viewType==TYPE_ITEM){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bond_item,parent,false);
            return new ViewHolder(view);
        }else if(viewType==TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot,parent,false);
            return new LoadingItemVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {
        if (holder1 instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) holder1;
            holder.tv_title.setText(data.get(position).getTitle());

            //保证金
            String bondPrice=data.get(position).getBondPrice();
            //人民币，服务器返回来的是分，分转元
            float bondMonney=Float.parseFloat(bondPrice)/100;
            bondPrice=String.valueOf(bondMonney);

            if(bondPrice.indexOf(".") == -1)//如果没有小数点
            {
                bondPrice += ".00";//直接在后面补点并且加两个00
            }else {
                String [] array= bondPrice.split("\\.");
                if ( array[1].length()==1){
                    bondPrice += "0";//直接在后面补点并且加两个00
                }
            }
            holder.tv_bondPrice.setText(bondPrice);

            //金额
            String wallet=data.get(position).getPrice();
            //人民币，服务器返回来的是分，分转元
            float monney=Float.parseFloat(wallet)/100;
            wallet=String.valueOf(monney);

            if(wallet.indexOf(".") == -1)//如果没有小数点
            {
                wallet += ".00";//直接在后面补点并且加两个00
            }else {
                String [] array= wallet.split("\\.");
                if ( array[1].length()==1){
                    wallet += "0";//直接在后面补点并且加两个00
                }
            }
            holder.tv_price.setText(wallet);

            holder.llt_bond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入投标项目详情
                }
            });

//            Glide.with(context).load(url).into(holder.iv_money_ico);
        } else if (holder1 instanceof LoadingItemVH) {
            LoadingItemVH footViewHolder=(LoadingItemVH)holder1;
            if(isloading==true){
                switch (load_more_status) {
                    case PULLUP_LOAD_MORE:
                        if (data==null){
                            footViewHolder.tv_loadmore.setVisibility(View.GONE);
                        }else {
                            footViewHolder.tv_loadmore.setVisibility(View.VISIBLE);
                        }
                        footViewHolder.tv_loadmore.setText("上拉加载更多");
                        footViewHolder.progressBar.setVisibility(View.GONE);
                        break;
                    case LOADING_MORE:
                        footViewHolder.progressBar.setVisibility(View.VISIBLE);
                        footViewHolder.tv_loadmore.setText(context.getResources().getString(R.string.loading));
                        break;
                    case NO_MORE_DATA:
                        //已经没有数据了
                        footViewHolder.tv_loadmore.setText("已经没有数据了");
                        footViewHolder.progressBar.setVisibility(View.GONE);
                        break;
                    case NULL_MORE_DATA:
                        //隐藏上拉加载布局
                        footViewHolder.tv_loadmore.setVisibility(View.GONE);
                        footViewHolder.progressBar.setVisibility(View.GONE);
                        break;
                    case ERROR_NETWORK_MORE_DATA:
                        //请求服务器出错显示布局
                        footViewHolder.tv_loadmore.setText("网络连接失败，请刷新重试");
                        footViewHolder.progressBar.setVisibility(View.GONE);
                        break;
                }
            }else {
                footViewHolder.tv_loadmore.setText("已经没有数据了");
                footViewHolder.progressBar.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position +1  == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }

    public void ismoreLoading(boolean isloading) {
        this.isloading=isloading;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_bondPrice;
        public TextView tv_title;
        public TextView tv_price;
        public LinearLayout llt_bond;
        public ViewHolder(View view) {
            super(view);
            tv_title= (TextView) view.findViewById(R.id.tv_title);
            tv_bondPrice= (TextView) view.findViewById(R.id.tv_bondPrice);
            tv_price= (TextView) view.findViewById(R.id.tv_price);
            llt_bond= (LinearLayout) view.findViewById(R.id.llt_bond);
        }
    }

    static class LoadingItemVH extends RecyclerView.ViewHolder {
        private TextView tv_loadmore;
        private ProgressBar progressBar;

        public LoadingItemVH(View itemView) {
            super(itemView);
            tv_loadmore= (TextView) itemView.findViewById(R.id.tv_loadmore);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);


        }

    }

}
