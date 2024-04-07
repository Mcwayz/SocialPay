package com.example.socialct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.socialct.R;

public class HomeActivity extends AppCompatActivity {

    private CardView ListImport, ViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewList = findViewById(R.id.cv_view);
        ListImport = findViewById(R.id.cv_import);

        // Displays the  List View Activity

        ViewList.setOnClickListener(view -> {
            Intent list_view = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(list_view);
            finish();
        });

        // Displays The Home Activity
        ListImport.setOnClickListener(view -> {
            Intent home = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(home);
            finish();
        });
    }

}