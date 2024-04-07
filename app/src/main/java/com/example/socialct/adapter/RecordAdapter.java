package com.example.socialct.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialct.Model.MyRecord;
import com.example.socialct.R;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<MyRecord> recordList;

    public RecordAdapter(List<MyRecord> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyRecord record = recordList.get(position);
        holder.tvNRC.setText(String.valueOf(record.getNrc()));
        holder.tvFullname.setText(record.getFullName());
        holder.tvStatus.setText(String.valueOf(record.getStatus()));
        holder.tvPhone.setText(String.valueOf(record.getPhoneNumber()));
        holder.tvAccount.setText(String.valueOf(record.getAccountNumber()));
        holder.tvDistrict.setText(String.valueOf(record.getDistrict()));
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNRC = itemView.findViewById(R.id.tv_nrc_no);
            tvFullname = itemView.findViewById(R.id.tv_fullname);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvAccount = itemView.findViewById(R.id.tv_account_no);
            tvPhone = itemView.findViewById(R.id.tv_phone_number);
            tvDistrict = itemView.findViewById(R.id.tv_district);
        }
    }
}
