package com.nyw.pets.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.CommunityListUtil;
import com.nyw.pets.adapter.CommunityListAdapter;
import com.nyw.pets.fragment.util.MyMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区数据列表
 */
public class CommunityListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_hide;
    private RecyclerView rcv_data;
    private CommunityListAdapter communityListAdapter;
    private List<CommunityListUtil> communityList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list);
        initView();
    }

    private void initView() {
        iv_hide=findViewById(R.id.iv_hide);
        iv_hide.setOnClickListener(this);
        rcv_data=findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(this));

        for (int i=0;i<8;i++) {
            CommunityListUtil communityListUtil = new CommunityListUtil();
            communityListUtil.setName("王五" + i);
            communityListUtil.setTime("1585396422");
            communityListUtil.setMsg("今天的狗狗真可爱，现在带狗狗出去逛街了，开心。");
            communityListUtil.setComment(86 + i + "");
            communityListUtil.setThumbs(96 + i + "");
            communityListUtil.setCollection(266 + i + "");
            ArrayList<MyMedia> mediaList10 = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                MyMedia myMedia = new MyMedia();
                myMedia.setImageUrl("http://ml.nnddkj.com/img/img1.png");
//                myMedia.setVideoUrl("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
                mediaList10.add(myMedia);
            }
            communityListUtil.setMediaList(mediaList10);
            communityList.add(communityListUtil);
        }

        communityListAdapter=new CommunityListAdapter(CommunityListActivity.this,communityList);
        rcv_data.setAdapter(communityListAdapter);

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.iv_hide:
                finish();
                break;

        }
    }
}
