package com.example.socialct.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socialct.R;

import java.io.ByteArrayOutputStream;

public class CaptureActivity extends AppCompatActivity {
    private String NRC, Fullname, Status, Account, Phone, District, CustomerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        NRC = getIntent().getStringExtra("nrc");
        Phone = getIntent().getStringExtra("phone");
        Status = getIntent().getStringExtra("status");
        Account = getIntent().getStringExtra("account");
        Fullname = getIntent().getStringExtra("fullname");
        District = getIntent().getStringExtra("district");
        Button captureButton = findViewById(R.id.capture_button);
        captureButton.setOnClickListener(view -> captureImage());
    }

    private void captureImage() {
        // Launch camera to capture image
        ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Bundle extras = data.getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                if (imageBitmap != null) {
                                    CustomerImg = convertBitmapToBase64(imageBitmap);
                                    // Now you can use the base64Image as needed
                                    Intent intent = new Intent(CaptureActivity.this, TransactActivity.class);
                                    intent.putExtra("nrc", NRC);
                                    intent.putExtra("fullname", Fullname);
                                    intent.putExtra("status", Status);
                                    intent.putExtra("account", Account);
                                    intent.putExtra("phone", Phone);
                                    intent.putExtra("district", District);
                                    intent.putExtra("customer_img", CustomerImg);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Failed to Capture Image, Try Again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
