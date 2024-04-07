package com.example.socialct.Database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.socialct.Model.MyRecord;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "socialct_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "socialct_tbl";
    private static final String COLUMN_NRC = "nrc";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_CUSTOMER_NUMBER = "customer_number";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_INSTITUTION = "institution";
    private static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    private static final String COLUMN_DISTRICT = "district";
    private static final String COLUMN_STATUS = "status";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NRC + " TEXT," +
                    COLUMN_FULLNAME + " TEXT," +
                    COLUMN_CUSTOMER_NUMBER + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_INSTITUTION + " TEXT," +
                    COLUMN_ACCOUNT_NUMBER + " TEXT," +
                    COLUMN_DISTRICT + " TEXT," +
                    COLUMN_STATUS + " TEXT" +
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

    public void insertData(String nrc, String fullname, String customerNumber,
                           String phoneNumber, String institution, String accountNumber,
                           String district, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NRC, nrc);
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_CUSTOMER_NUMBER, customerNumber);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_INSTITUTION, institution);
        values.put(COLUMN_ACCOUNT_NUMBER, accountNumber);
        values.put(COLUMN_DISTRICT, district);
        values.put(COLUMN_STATUS, status);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public Cursor searchDataByNRC(String nrc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NRC, COLUMN_FULLNAME, COLUMN_CUSTOMER_NUMBER,
                COLUMN_PHONE_NUMBER, COLUMN_INSTITUTION, COLUMN_ACCOUNT_NUMBER,
                COLUMN_DISTRICT, COLUMN_STATUS};
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
        String[] columns = {COLUMN_NRC, COLUMN_FULLNAME, COLUMN_CUSTOMER_NUMBER,
                COLUMN_PHONE_NUMBER, COLUMN_INSTITUTION, COLUMN_ACCOUNT_NUMBER,
                COLUMN_DISTRICT, COLUMN_STATUS};
        String selection = COLUMN_STATUS + "=?";
        String[] selectionArgs = {"Pending"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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

                MyRecord record = new MyRecord(nrc, fullName, customerNumber, phoneNumber, institution, accountNumber, district, status);
                approvedRecords.add(record);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return approvedRecords;
    }

    
}