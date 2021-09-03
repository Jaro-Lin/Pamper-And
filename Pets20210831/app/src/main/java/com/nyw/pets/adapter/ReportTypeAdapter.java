package com.nyw.pets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.nyw.pets.R;
import com.nyw.pets.activity.util.GetReportTypeUtil;
import com.nyw.pets.interfaces.GetReportTypeInterface;

import java.util.List;

/**
 * 举报类型选择显示
 */
public class ReportTypeAdapter extends RecyclerView.Adapter<ReportTypeAdapter.ViewHolder> {

    private Context context;
    private List<GetReportTypeUtil> data;

    private GetReportTypeInterface getReportTypeInterface;

    public void setGetReportTypeInterface(GetReportTypeInterface getReportTypeInterface) {
        this.getReportTypeInterface = getReportTypeInterface;
    }

    public ReportTypeAdapter(Context context, List<GetReportTypeUtil> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        int number=data.size();
        return number;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int postion) {
        // TODO Auto-generated method stub
        //数据邦定
        viewHolder.btn_title.setText(data.get(postion).getTitle());

        viewHolder.btn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item选择举报类型
                if (getReportTypeInterface!=null){
                    getReportTypeInterface.getReport(data.get(postion).getId(),data.get(postion).getTitle());
                }
            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //邦定xml
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_report_item,viewGroup, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_title;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_title = (Button) itemView.findViewById(R.id.btn_title);
        }

    }
}

