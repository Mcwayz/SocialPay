package com.example.socialct.Activity;

import static com.example.socialct.Database.DatabaseHelper.COLUMN_NRC_BACK;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_NRC_FRONT;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_STATUS;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_ACCOUNT_NUMBER;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_CUSTOMER_NUMBER;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_DISTRICT;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_FULLNAME;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_INSTITUTION;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_NRC;
import static com.example.socialct.Database.DatabaseHelper.COLUMN_PHONE_NUMBER;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialct.Database.DatabaseHelper;
import com.example.socialct.Model.MyRecord;
import com.example.socialct.R;
import com.example.socialct.adapter.CustomAdapter;
import com.example.socialct.adapter.RecyclerViewInterface;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewInterface {

    private Button search;
    private EditText searchNrc;
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private ArrayList<String> tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, tvNRC_Frt, tvNRC_Bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Initialize ArrayLists
        tvNRC = new ArrayList<>();
        tvFullname = new ArrayList<>();
        tvStatus = new ArrayList<>();
        tvAccount = new ArrayList<>();
        tvPhone = new ArrayList<>();
        tvDistrict = new ArrayList<>();
        tvNRC_Frt = new ArrayList<>();
        tvNRC_Bck = new ArrayList<>();
        // Initialize RecyclerView
        search = findViewById(R.id.btn_filter);
        imgBack = findViewById(R.id.img_back_his);
        recyclerView = findViewById(R.id.recyclerView);
        searchNrc = findViewById(R.id.searchNrcEditText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize customAdapter
        customAdapter = new CustomAdapter(this, tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, this);

        imgBack.setOnClickListener(view -> {
            Intent list_view = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(list_view);
            finish();
        });


        search.setOnClickListener(view -> {
            String nrc = searchNrc.getText().toString().trim();
            searchTransactionsByNrc(nrc);
        });

    }

    private void storeDataInArrays(DatabaseHelper dbHelper) {
        List<MyRecord> approvedRecords = dbHelper.getApprovedRecords();
        if (!approvedRecords.isEmpty()) {
            tvNRC = new ArrayList<>();
            tvFullname = new ArrayList<>();
            tvStatus = new ArrayList<>();
            tvAccount = new ArrayList<>();
            tvPhone = new ArrayList<>();
            tvDistrict = new ArrayList<>();
            tvNRC_Frt = new ArrayList<>();
            tvNRC_Bck = new ArrayList<>();

            for (MyRecord record : approvedRecords) {
                tvNRC.add(record.getNrc());
                tvFullname.add(record.getFullName());
                tvStatus.add(record.getStatus());
                tvAccount.add(record.getAccountNumber());
                tvPhone.add(record.getPhoneNumber());
                tvDistrict.add(record.getDistrict());
                tvNRC_Frt.add(record.getNrc_front());
                tvNRC_Bck.add(record.getNrc_back());
            }

            customAdapter = new CustomAdapter(this, tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        } else {
            Toast.makeText(this, "No Records Found!", Toast.LENGTH_SHORT).show();
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
        intent.putExtra("nrc_front", tvNRC_Frt.get(position));
        intent.putExtra("nrc_back", tvNRC_Bck.get(position));
        startActivity(intent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }



    private void searchTransactionsByNrc(String nrc) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.searchDataByNRC(nrc);
        List<MyRecord> searchResults = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from cursor and create MyRecord objects
                int columnIndexNrc = cursor.getColumnIndex(COLUMN_NRC);
                String recordNrc = (columnIndexNrc != -1) ? cursor.getString(columnIndexNrc) : null;
                int columnIndexFullName = cursor.getColumnIndex(COLUMN_FULLNAME);
                String fullName = (columnIndexFullName != -1) ? cursor.getString(columnIndexFullName) : null;
                int columnIndexCustomerNumber = cursor.getColumnIndex(COLUMN_CUSTOMER_NUMBER);
                String customerNumber = (columnIndexCustomerNumber != -1) ? cursor.getString(columnIndexCustomerNumber) : null;
                int columnIndexPhoneNumber = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);
                String phoneNumber = (columnIndexPhoneNumber != -1) ? cursor.getString(columnIndexPhoneNumber) : null;
                int columnIndexInstitution = cursor.getColumnIndex(COLUMN_INSTITUTION);
                String institution = (columnIndexInstitution != -1) ? cursor.getString(columnIndexInstitution) : null;
                int columnIndexAccountNumber = cursor.getColumnIndex(COLUMN_ACCOUNT_NUMBER);
                String accountNumber = (columnIndexAccountNumber != -1) ? cursor.getString(columnIndexAccountNumber) : null;
                int columnIndexDistrict = cursor.getColumnIndex(COLUMN_DISTRICT);
                String district = (columnIndexDistrict != -1) ? cursor.getString(columnIndexDistrict) : null;
                int columnIndexStatus = cursor.getColumnIndex(COLUMN_STATUS);
                String status = (columnIndexStatus != -1) ? cursor.getString(columnIndexStatus) : null;

                int columnIndexNRCBack = cursor.getColumnIndex(COLUMN_NRC_BACK);
                String nrc_back = (columnIndexNRCBack != -1) ? cursor.getString(columnIndexNRCBack) : null;
                int columnIndexNRCFront = cursor.getColumnIndex(COLUMN_NRC_FRONT);
                String nrc_front = (columnIndexNRCFront != -1) ? cursor.getString(columnIndexNRCFront) : null;
                MyRecord record = new MyRecord(recordNrc, fullName, customerNumber, phoneNumber, institution, accountNumber, district, status, nrc_back, nrc_front);
                searchResults.add(record);
            } while (cursor.moveToNext());
            cursor.close();

            // Clear existing data
            tvNRC.clear();
            tvFullname.clear();
            tvStatus.clear();
            tvAccount.clear();
            tvPhone.clear();
            tvDistrict.clear();
            tvNRC_Bck.clear();
            tvNRC_Frt.clear();

            // Populate arrays with search results
            for (MyRecord record : searchResults) {
                tvNRC.add(record.getNrc());
                tvFullname.add(record.getFullName());
                tvStatus.add(record.getStatus());
                tvAccount.add(record.getAccountNumber());
                tvPhone.add(record.getPhoneNumber());
                tvDistrict.add(record.getDistrict());
                tvNRC_Bck.add(record.getNrc_back());
                tvNRC_Frt.add(record.getNrc_front());
            }

            // Notify adapter about the data change
            customAdapter = new CustomAdapter(this, tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        } else {
            Toast.makeText(this, "No matching records found!", Toast.LENGTH_SHORT).show();
        }
    }




}
