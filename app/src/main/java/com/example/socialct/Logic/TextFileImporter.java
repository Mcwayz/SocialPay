package com.example.socialct.Logic;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.socialct.Database.DatabaseHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextFileImporter {
    private static final String TAG = "TextFileImporter";

    public static void importDataFromTextFile(Context context, DatabaseHelper dbHelper, Uri fileUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");
                // Extract data fields
                String nrc = data[0].trim();
                String fullname = data[1].trim();
                String customerNumber = data[2].trim();
                String phoneNumber = data[3].trim();
                String institution = data[4].trim();
                String accountNumber = data[5].trim();
                String district = data[6].trim();
                String accountBalance = data[7].trim();
                String user = data[8].trim();
                String image = data[9].trim();
                String nrcFront = data[10].trim();
                String nrcBack = data[11].trim();
                String status = data[12].trim();
                String terminalId = data[13].trim();
                String dateTime = data[14].trim();
                String amount = data[15].trim();
                // Insert data into database
                dbHelper.insertData(nrc, fullname, customerNumber, phoneNumber,
                        institution, accountNumber, district, accountBalance, user,
                        image, nrcFront, nrcBack, status, terminalId, dateTime, amount);
            }


            // Close the reader
            reader.close();
            Log.d(TAG, "Data imported successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error importing data from text file: " + e.getMessage());
        }
    }
}
