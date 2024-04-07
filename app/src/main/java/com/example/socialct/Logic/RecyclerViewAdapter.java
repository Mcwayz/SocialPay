package com.example.socialct.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.socialct.Model.MyRecord;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialct.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecordViewHolder> {

    private List<MyRecord> recordList;
    private Context context;

    public RecyclerViewAdapter(List<MyRecord> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        MyRecord record = recordList.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private TextView nrcTextView;
        private TextView fullNameTextView;
        // Add other TextViews for other fields as needed

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            nrcTextView = itemView.findViewById(R.id.nrcTextView);
            fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            // Initialize other TextViews here
        }

        public void bind(MyRecord record) {
            nrcTextView.setText(record.getNrc());
            fullNameTextView.setText(record.getFullName());
            // Bind other fields similarly
        }
    }
}
