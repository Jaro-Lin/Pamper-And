package com.nyw.pets.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dalong.refreshlayout.OnHeaderListener;
import com.nyw.pets.R;

/**
 * 美团加载头部
 * Created by zhouweilong on 2016/10/21.
 */

public class MeiTuanHeader extends RelativeLayout  implements OnHeaderListener {
    private ImageView mPullDownView;
    private ImageView mReleaseRefreshingView;

    private AnimationDrawable mChangeToReleaseRefreshAd;
    private AnimationDrawable mRefreshingAd;

    private int mChangeToReleaseRefreshAnimResId= R.drawable.bga_refresh_mt_change_to_release_refresh;
    private int mRefreshingAnimResId=R.drawable.bga_refresh_mt_refreshing;

    public MeiTuanHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.meituan_refresh_view, this, true);
        mPullDownView = (ImageView) findViewById(R.id.iv_meituan_pull_down);
        mReleaseRefreshingView = (ImageView) findViewById(R.id.iv_meituan_release_refreshing);
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
        mPullDownView.setImageResource(R.mipmap.bga_refresh_mt_pull_down);
    }
    public MeiTuanHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void handleScale(float scale) {
        scale = 0.1f + 0.9f * scale;
        ViewCompat.setScaleX(mPullDownView, scale);
        ViewCompat.setPivotY(mPullDownView, mPullDownView.getHeight());
        ViewCompat.setScaleY(mPullDownView, scale);
        if(scale>=0.8f){
            changeToReleaseRefresh();
        }else{
            onEndRefreshing();
        }
    }

    public void changeToIdle() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();

        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToPullDown() {
        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToReleaseRefresh() {
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
        mChangeToReleaseRefreshAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mChangeToReleaseRefreshAd.start();
    }

    public void changeToRefreshing() {
        stopChangeToReleaseRefreshAd();

        mReleaseRefreshingView.setImageResource(mRefreshingAnimResId);
        mRefreshingAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mRefreshingAd.start();
    }

    public void onEndRefreshing() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();
    }

    private void stopRefreshingAd() {
        if (mRefreshingAd != null) {
            mRefreshingAd.stop();
            mRefreshingAd = null;
        }
    }

    private void stopChangeToReleaseRefreshAd() {
        if (mChangeToReleaseRefreshAd != null) {
            mChangeToReleaseRefreshAd.stop();
            mChangeToReleaseRefreshAd = null;
        }
    }

    /**
     * 下拉刷新
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshBefore(int scrollY,int refreshHeight,int headerHeight) {
        changeToPullDown();
        handleScale(Math.abs(scrollY)/(1.0f*refreshHeight));
    }

    @Override
    public void onRefreshAfter(int scrollY,int refreshHeight,int headerHeight) {
    }

    /**
     * 准备刷新
     * @param scrollY
     */
    @Override
    public void onRefreshReady(int scrollY,int refreshHeight,int headerHeight) {
    }

    /**
     * 正在刷新
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshing(int scrollY,int refreshHeight,int headerHeight) {
        changeToRefreshing();
    }

    /**
     * 刷新完成
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     * @param isRefreshSuccess  刷新的状态  是成功了 还是失败了
     */
    @Override
    public void onRefreshComplete(int scrollY,int refreshHeight,int headerHeight, boolean isRefreshSuccess) {
        onEndRefreshing();
        changeToIdle();
    }

    /**
     * 刷新取消
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshCancel(int scrollY,int refreshHeight,int headerHeight) {
        onEndRefreshing();
        changeToIdle();
    }
}
