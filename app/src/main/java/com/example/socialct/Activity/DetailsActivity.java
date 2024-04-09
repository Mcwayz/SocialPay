package com.example.socialct.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialct.R;

public class DetailsActivity extends AppCompatActivity {

    private Button btn_verify, no_match;
    private ImageView imgBack, NRC_FRONT, NRC_BACK;
    private String NRC, Fullname, Status, Account, Phone, District, NRC_Frt, NRC_Bck;
    private TextView tvNRC, tvFullname, tvStatus, tvAccount, tvPhone, tvDistrict, tvStatusN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize

        tvNRC = findViewById(R.id.tv_nrc_no);
        no_match = findViewById(R.id.btn_back);
        NRC_BACK = findViewById(R.id.nrc_back);
        tvStatus = findViewById(R.id.tv_status);
        NRC_FRONT = findViewById(R.id.nrc_front);
        tvStatusN = findViewById(R.id.tv_statusN);
        btn_verify = findViewById(R.id.btn_print);
        tvPhone = findViewById(R.id.tv_d_phone_no);
        tvFullname = findViewById(R.id.tv_fullname);
        tvAccount = findViewById(R.id.tv_account_no);
        imgBack = findViewById(R.id.img_back_ticket);
        tvDistrict = findViewById(R.id.tv_disrict_name);

        // Get Data From Intent

        NRC = getIntent().getStringExtra("nrc");
        Phone = getIntent().getStringExtra("phone");
        Status = getIntent().getStringExtra("status");
        Account = getIntent().getStringExtra("account");
        NRC_Bck = getIntent().getStringExtra("nrc_back");
        Fullname = getIntent().getStringExtra("fullname");
        District = getIntent().getStringExtra("district");
        NRC_Frt = getIntent().getStringExtra("nrc_front");

        imgBack.setOnClickListener(v -> goBack());

        no_match.setOnClickListener(view -> goBack());

        btn_verify.setOnClickListener(view -> {
            // Update Function
            // String nrc = tvNRC.getText().toString().trim();
            // boolean isUpdated = dbHelper.updateStatusToPaid(nrc);
            Intent intent = new Intent(DetailsActivity.this, CaptureActivity.class);
            intent.putExtra("nrc", tvNRC.getText());
            intent.putExtra("fullname", tvFullname.getText());
            intent.putExtra("status", tvStatus.getText());
            intent.putExtra("account", tvAccount.getText());
            intent.putExtra("phone", tvPhone.getText());
            intent.putExtra("district", tvDistrict.getText());
            startActivity(intent);
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

        // Convert Base64 strings to Bitmap and set them to ImageViews
        if (NRC_Frt != null && !NRC_Frt.isEmpty()) {
            Bitmap nrcFrontBitmap = decodeBase64(NRC_Frt);
            if (nrcFrontBitmap != null) {
                NRC_FRONT.setImageBitmap(nrcFrontBitmap);
            }
        }

        if (NRC_Bck != null && !NRC_Bck.isEmpty()) {
            Bitmap nrcBackBitmap = decodeBase64(NRC_Bck);
            if (nrcBackBitmap != null) {
                NRC_BACK.setImageBitmap(nrcBackBitmap);
            }
        }
    }

    private Bitmap decodeBase64(String base64Str) {
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void goBack(){
        Intent i = new Intent(DetailsActivity.this, HistoryActivity.class);
        startActivity(i);
        finish();
    }
}
