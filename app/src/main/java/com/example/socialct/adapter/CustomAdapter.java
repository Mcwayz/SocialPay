package com.example.socialct.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialct.R;

import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements RecyclerViewInterface {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict;
    public CustomAdapter(Context context, ArrayList tvNRC, ArrayList tvFullname, ArrayList tvStatus, ArrayList tvAccount,
    ArrayList tvPhone, ArrayList tvDistrict,  RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.tvNRC = tvNRC;
        this.tvFullname = tvFullname;
        this.tvStatus = tvStatus;
        this.tvAccount = tvAccount;
        this.tvPhone = tvPhone;
        this.tvDistrict = tvDistrict;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNRC.setText(String.valueOf(tvNRC.get(position)));
        holder.tvFullname.setText(String.valueOf(tvFullname.get(position)));
        holder.tvStatus.setText(String.valueOf(tvStatus.get(position)));
        holder.tvAccount.setText(String.valueOf(tvAccount.get(position)));
        holder.tvPhone.setText(String.valueOf(tvPhone.get(position)));
        holder.tvDistrict.setText(String.valueOf(tvDistrict.get(position)));

    }

    @Override
    public int getItemCount() {
        return tvNRC.size();
    }
    @Override
    public void onItemClick(int position) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict;
        ImageView imgQRC;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvNRC = itemView.findViewById(R.id.tv_nrc_no);
            tvFullname = itemView.findViewById(R.id.tv_fullname);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvAccount = itemView.findViewById(R.id.tv_account_no);
            tvPhone = itemView.findViewById(R.id.tv_phone_number);
            tvDistrict = itemView.findViewById(R.id.tv_district);
             imgQRC = itemView.findViewById(R.id.img_his_qrc);

             itemView.setOnClickListener(v -> {
                 if(recyclerViewInterface != null){
                     int pos = getAdapterPosition();
                     if(pos !=RecyclerView.NO_POSITION){
                         recyclerViewInterface.onItemClick(pos);
                     }
                 }
             });
        }
    }
}
