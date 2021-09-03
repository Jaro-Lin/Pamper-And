package com.nyw.pets.refresh;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * 美团下拉刷新  https://github.com/dalong982242260/PullRefresh
 * Created by  on 2016/10/19.
 */

public class MeiTuanRefreshView extends RefreshLayout {

    public MeiTuanRefreshView(Context context) {
        super(context);
    }

    public MeiTuanRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        MeiTuanHeader header = new MeiTuanHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}
