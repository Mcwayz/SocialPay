package com.example.socialct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialct.Database.DatabaseHelper;
import com.example.socialct.adapter.CustomAdapter;
import com.example.socialct.Model.MyRecord;
import com.example.socialct.R;
import com.example.socialct.adapter.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<String> tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize RecyclerView
        imgBack = findViewById(R.id.img_back_his);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get approved records from database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        storeDataInArrays(dbHelper); // Call the method to populate arrays with data

        imgBack.setOnClickListener(view -> {
            Intent list_view = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(list_view);
            finish();
        });

        EditText searchNrcEditText = findViewById(R.id.searchNrcEditText);
        searchNrcEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nrc = s.toString().trim();
                if (!nrc.isEmpty()) {
                    searchTransactionsByNrc(nrc);
                } else {
                    storeDataInArrays(dbHelper);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    void storeDataInArrays(DatabaseHelper dbHelper) {
        List<MyRecord> approvedRecords = dbHelper.getApprovedRecords();
        if (!approvedRecords.isEmpty()) {
            tvNRC = new ArrayList<>();
            tvFullname = new ArrayList<>();
            tvStatus = new ArrayList<>();
            tvAccount = new ArrayList<>();
            tvPhone = new ArrayList<>();
            tvDistrict = new ArrayList<>();

            for (MyRecord record : approvedRecords) {
                tvNRC.add(record.getNrc());
                tvFullname.add(record.getFullName());
                tvStatus.add(record.getStatus());
                tvAccount.add(record.getAccountNumber());
                tvPhone.add(record.getPhoneNumber());
                tvDistrict.add(record.getDistrict());
            }

            customAdapter = new CustomAdapter(this, tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        } else {
            Toast.makeText(this, "No Transactions History Found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(HistoryActivity.this, DetailsActivity.class);
        intent.putExtra("nrc", tvNRC.get(position));
        intent.putExtra("fullname", tvFullname.get(position));
        intent.putExtra("status", tvStatus.get(position));
        intent.putExtra("account", tvAccount.get(position));
        intent.putExtra("phone", tvPhone.get(position));
        intent.putExtra("district", tvDistrict.get(position));
        startActivity(intent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    void searchTransactionsByNrc(String nrc) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.searchDataByNRC(nrc);
        List<MyRecord> searchResults = new ArrayList<>();

        if (!searchResults.isEmpty()) {
            // Clear existing data
            tvNRC.clear();
            tvFullname.clear();
            tvStatus.clear();
            tvAccount.clear();
            tvPhone.clear();
            tvDistrict.clear();

            // Populate arrays with search results
            for (MyRecord record : searchResults) {
                tvNRC.add(record.getNrc());
                tvFullname.add(record.getFullName());
                tvStatus.add(record.getStatus());
                tvAccount.add(record.getAccountNumber());
                tvPhone.add(record.getPhoneNumber());
                tvDistrict.add(record.getDistrict());
            }

            // Notify adapter about the data change
            customAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No matching transactions found!", Toast.LENGTH_SHORT).show();
        }
    }


}
