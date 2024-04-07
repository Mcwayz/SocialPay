package com.example.socialct.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.example.socialct.Database.DatabaseHelper;
import com.example.socialct.Logic.TextFileImporter;
import com.example.socialct.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends Activity {
    private static final int REQUEST_PICK_FILE = 1001;

    private Button getData;

    private ImageView imgBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = findViewById(R.id.importButton);
        imgBack = findViewById(R.id.img_back_his);
        TextInputEditText fileButton = findViewById(R.id.pickFileButton);
        fileButton.setOnClickListener(view -> pickTextFile());
        getData.setOnClickListener(view -> {
            showProgressDialog();
            Import();
        });

        imgBack.setOnClickListener(view -> {
            Intent list_view = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(list_view);
            finish();
        });
    }

    private void pickTextFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, REQUEST_PICK_FILE);
    }

    private void Import() {
        pickTextFile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            // Get the DatabaseHelper instance
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            // Import data from the selected file into the database
            TextFileImporter.importDataFromTextFile(this, dbHelper, selectedFileUri);
            // Dismiss the progress dialog
            dismissProgressDialog();
            // Show success dialog
            showSuccessDialog();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success")
                .setMessage("Data imported successfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle OK button click if needed
                    }
                })
                .show();
    }
}
