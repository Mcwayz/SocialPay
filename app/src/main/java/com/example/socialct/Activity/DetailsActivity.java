package com.example.socialct.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialct.R;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imgBack;
    private String NRC, Fullname, Status, Account, Phone, District;
    private TextView tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, tvStatusN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize TextViews
        tvNRC = findViewById(R.id.tv_nrc_no);
        tvStatus = findViewById(R.id.tv_status);
        tvStatusN = findViewById(R.id.tv_statusN);
        tvPhone = findViewById(R.id.tv_d_phone_no);
        tvFullname = findViewById(R.id.tv_fullname);
        tvAccount = findViewById(R.id.tv_account_no);
        imgBack = findViewById(R.id.img_back_ticket);
        tvDistrict = findViewById(R.id.tv_disrict_name);

        // Get data from intent
        NRC = getIntent().getStringExtra("nrc");
        Phone = getIntent().getStringExtra("phone");
        Status = getIntent().getStringExtra("status");
        Account = getIntent().getStringExtra("account");
        Fullname = getIntent().getStringExtra("fullname");
        District = getIntent().getStringExtra("district");


        imgBack.setOnClickListener(v -> {
            Intent i = new Intent(DetailsActivity.this, HistoryActivity.class);
            startActivity(i);
            finish();

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set data to TextViews
        tvNRC.setText(NRC);
        tvFullname.setText(Fullname);
        tvStatus.setText(Status);
        tvStatusN.setText(Status);
        tvAccount.setText(Account);
        tvPhone.setText(Phone);
        tvDistrict.setText(District);
    }
}
