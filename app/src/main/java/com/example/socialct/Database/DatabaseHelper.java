package com.example.socialct.Database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import com.example.socialct.Model.MyRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "socialct_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "socialct_tbl";
    public static final String COLUMN_NRC = "nrc";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_CUSTOMER_NUMBER = "customer_number";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_INSTITUTION = "institution";
    public static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    public static final String COLUMN_DISTRICT = "district";
    public static final String COLUMN_ACCOUNT_BALANCE = "account_balance";
    public static final String COLUMN_USER = "username";
    public static final String COLUMN_IMAGE = "customer_img";
    public static final String COLUMN_NRC_FRONT = "front_nrc";
    public static final String COLUMN_NRC_BACK = "back_nrc";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TERMINAL_ID = "terminal_id";
    public static final String COLUMN_DATE_ID = "date_time";
    public static final String COLUMN_WITHDRAW = "withdraw";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NRC + " TEXT PRIMARY KEY," +
                    COLUMN_FULLNAME + " TEXT," +
                    COLUMN_CUSTOMER_NUMBER + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_INSTITUTION + " TEXT," +
                    COLUMN_ACCOUNT_NUMBER + " TEXT," +
                    COLUMN_DISTRICT + " TEXT," +
                    COLUMN_STATUS + " TEXT," +
                    COLUMN_ACCOUNT_BALANCE + " REAL DEFAULT 0," +
                    COLUMN_USER + " TEXT," +
                    COLUMN_IMAGE + " TEXT," +
                    COLUMN_NRC_FRONT + " TEXT," +
                    COLUMN_NRC_BACK + " TEXT," +
                    COLUMN_TERMINAL_ID + " TEXT," +
                    COLUMN_DATE_ID + " TEXT," +
                    COLUMN_WITHDRAW + " REAL DEFAULT 0" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String nrc, String fullname, String customerNumber, String phoneNumber, String institution,
                           String accountNumber,String district, String accountBalance, String user, String image,
                           String nrcFront, String nrcBack, String status, String terminalId, String dateTime, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NRC, nrc);
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_CUSTOMER_NUMBER, customerNumber);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_INSTITUTION, institution);
        values.put(COLUMN_ACCOUNT_NUMBER, accountNumber);
        values.put(COLUMN_DISTRICT, district);
        values.put(COLUMN_ACCOUNT_BALANCE, accountBalance);
        values.put(COLUMN_USER, user);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_NRC_FRONT, nrcFront);
        values.put(COLUMN_NRC_BACK, nrcBack);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_TERMINAL_ID, terminalId);
        values.put(COLUMN_DATE_ID, dateTime);
        values.put(COLUMN_WITHDRAW, amount);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public Cursor searchDataByNRC(String nrc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NRC, COLUMN_FULLNAME, COLUMN_CUSTOMER_NUMBER,
                COLUMN_PHONE_NUMBER, COLUMN_INSTITUTION, COLUMN_ACCOUNT_NUMBER,
                COLUMN_DISTRICT, COLUMN_ACCOUNT_BALANCE, COLUMN_STATUS, COLUMN_IMAGE,
                COLUMN_NRC_FRONT, COLUMN_NRC_BACK};
        String selection = COLUMN_NRC + "=?";
        String[] selectionArgs = {nrc};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        return cursor;
    }


    @SuppressLint("Range")
    public List<MyRecord> getApprovedRecords() {
        List<MyRecord> approvedRecords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COLUMN_NRC, COLUMN_FULLNAME, COLUMN_CUSTOMER_NUMBER,
                    COLUMN_PHONE_NUMBER, COLUMN_INSTITUTION, COLUMN_ACCOUNT_NUMBER,
                    COLUMN_DISTRICT, COLUMN_ACCOUNT_BALANCE, COLUMN_STATUS, COLUMN_IMAGE,
                    COLUMN_NRC_FRONT, COLUMN_NRC_BACK};
            String selection = COLUMN_STATUS + "=?";
            String[] selectionArgs = {"Pending"};
            cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extract data from cursor and create Record objects
                    String nrc = cursor.getString(cursor.getColumnIndex(COLUMN_NRC));
                    String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME));
                    String customerNumber = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NUMBER));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                    String institution = cursor.getString(cursor.getColumnIndex(COLUMN_INSTITUTION));
                    String accountNumber = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NUMBER));
                    String district = cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT));
                    String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                    String nrc_front = cursor.getString(cursor.getColumnIndex(COLUMN_NRC_FRONT));
                    String nrc_back = cursor.getString(cursor.getColumnIndex(COLUMN_NRC_BACK));
                    String account_balance = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_BALANCE));
                    MyRecord record = new MyRecord(nrc, fullName, customerNumber, phoneNumber, institution, accountNumber, district, status, nrc_back, nrc_front, account_balance);
                    approvedRecords.add(record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return approvedRecords;
    }

    // function that updates the database record
    public boolean updateTransactionDetails(String nrc, double withdrawAmount, String dateTime, String username, String terminalID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ACCOUNT_BALANCE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NRC + " = ?", new String[]{nrc});
        if (cursor != null && cursor.moveToFirst()) {
            int accountBalanceIndex = cursor.getColumnIndex(COLUMN_ACCOUNT_BALANCE);
            if (accountBalanceIndex != -1) {
                double accountBalance = cursor.getDouble(accountBalanceIndex);
                cursor.close();
                if (accountBalance >= withdrawAmount) {
                    // Subtract the withdrawAmount from the accountBalance
                    double updatedBalance = accountBalance - withdrawAmount;

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_STATUS, "Withdrawal");
                    values.put(COLUMN_WITHDRAW, withdrawAmount);
                    values.put(COLUMN_DATE_ID, dateTime);
                    values.put(COLUMN_USER, username);
                    values.put(COLUMN_TERMINAL_ID, terminalID);
                    values.put(COLUMN_ACCOUNT_BALANCE, updatedBalance); // Update the account balance
                    int rowsAffected = db.update(TABLE_NAME, values, COLUMN_NRC + " = ?", new String[]{nrc});
                    db.close();
                    return rowsAffected > 0;
                } else {
                    db.close();
                    return false;
                }
            } else {
                cursor.close();
                db.close();
                return false;
            }
        } else {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return false;
        }
    }





    // function that exports end of day records

    public void exportRecordsToTxt(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE_ID + " = ?", new String[]{currentDate});
        if (cursor.getCount() > 0) {
            File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/MyApp");
            directory.mkdirs();
            File file = new File(directory, "records.txt");
            try {
                FileWriter writer = new FileWriter(file);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    StringBuilder record = new StringBuilder();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        record.append(cursor.getString(i)).append(",");
                    }
                    record.append("\n");
                    writer.write(record.toString());

                    cursor.moveToNext();
                }
                writer.close();
                Toast.makeText(context, "Records Exported", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to Export Records", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No Records Found For The Current Day", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
    
}