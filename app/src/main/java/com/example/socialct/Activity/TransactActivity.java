package com.example.socialct.Activity;

import static com.ftpos.library.smartpos.errcode.ErrCode.ERR_SUCCESS;
import static com.ftpos.library.smartpos.printer.AlignStyle.PRINT_STYLE_CENTER;
import static com.ftpos.library.smartpos.printer.AlignStyle.PRINT_STYLE_LEFT;
import static com.ftpos.library.smartpos.printer.AlignStyle.PRINT_STYLE_RIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.socialct.R;
import com.ftpos.library.smartpos.device.Device;
import com.ftpos.library.smartpos.printer.OnPrinterCallback;
import com.ftpos.library.smartpos.printer.PrintStatus;
import com.ftpos.library.smartpos.printer.Printer;
import com.ftpos.library.smartpos.servicemanager.OnServiceConnectCallback;
import com.ftpos.library.smartpos.servicemanager.ServiceManager;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class TransactActivity extends AppCompatActivity {


    public Device device;
    private Dialog dialog;
    private Button Transact;
    private Printer printer;
    private Context mContext;
    private TextInputEditText Amount;
    private final Handler handler = new Handler();
    private String NRC, Fullname, Status, Account, Phone, District, CustomerImg, serialNumber, ZMW_Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact);
        Transact = findViewById(R.id.btnTransact);
        Amount = findViewById(R.id.tf_withdraw_amount);

        NRC = getIntent().getStringExtra("nrc");
        Phone = getIntent().getStringExtra("phone");
        Status = getIntent().getStringExtra("status");
        Account = getIntent().getStringExtra("account");
        Fullname = getIntent().getStringExtra("fullname");
        District = getIntent().getStringExtra("district");
        CustomerImg = getIntent().getStringExtra("customer_img");
        Transact.setOnClickListener(view -> {

            // Complete Transaction

            if(Objects.equals(Amount.getText(), "")){
                Toast.makeText(mContext, "Please Enter The Withdraw Amount", Toast.LENGTH_LONG).show();
            }else{
                // Transact
                Transact();
            }
        });

        // Obtain the context
        Context context = getApplicationContext();
        try {
            ServiceManager.bindPosServer(this, new OnServiceConnectCallback() {
                @Override
                public void onSuccess() {
                    device = Device.getInstance(mContext);
                    serialNumber = device.getSerialNumber();
                }
                @Override
                public void onFail(int i) {

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            ServiceManager.bindPosServer(TransactActivity.this, new OnServiceConnectCallback() {
                @Override
                public void onSuccess() {
                    printer = Printer.getInstance(mContext);
                }
                @Override
                public void onFail(int var1) {
                    Log.e("binding", "onFail");
                }
            });
        } catch (Exception e) {
            Log.e("binding", "onFail"+ e);
        }

    }

    // Transact Function
    private void Transact(){

    }


    // Save / Update DB

    private void SaveTransaction(){


    }


    // Print Receipt

    private void printReceipt(String copy) {
        try {
            int ret;
            ret = printer.open();
            if (ret != ERR_SUCCESS) {
                System.out.println("open failed" + String.format(" errCode = 0x%x\n", ret));
                Toast.makeText(TransactActivity.this, "open failed" + String.format(" errCode = 0x%x\n", ret), Toast.LENGTH_LONG).show();
                return;
            }

            ret = printer.startCaching();
            if (ret != ERR_SUCCESS) {
                System.out.println("startCaching failed" + String.format(" errCode = 0x%x\n", ret));
                Toast.makeText(TransactActivity.this, "startCaching failed" + String.format(" errCode = 0x%x\n", ret), Toast.LENGTH_LONG).show();
                return;
            }

            ret = printer.setGray(3);
            if (ret != ERR_SUCCESS) {
                System.out.println("startCaching failed" + String.format(" errCode = 0x%x\n", ret));
                Toast.makeText(TransactActivity.this, "startCaching failed" + String.format(" errCode = 0x%x\n", ret), Toast.LENGTH_LONG).show();
                return;
            }

            PrintStatus printStatus = new PrintStatus();
            ret = printer.getStatus(printStatus);
            if (ret != ERR_SUCCESS) {
                System.out.println("getStatus failed" + String.format(" errCode = 0x%x\n", ret));
                Toast.makeText(TransactActivity.this, "getStatus failed" + String.format(" errCode = 0x%x\n", ret), Toast.LENGTH_LONG).show();
                return;
            }

            System.out.println("Temperature = " + printStatus.getmTemperature() + "\n");
            System.out.println("Gray = " + printStatus.getmGray() + "\n");
            if (!printStatus.getmIsHavePaper()) {
                System.out.println("Printer out of paper\n");
                Toast.makeText(TransactActivity.this, "Printer Out of Paper", Toast.LENGTH_LONG).show();
                return;
            }

            System.out.println("IsHavePaper = true\n");

            printer.setAlignStyle(PRINT_STYLE_CENTER);
            Bitmap originalBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.profile);
            int desiredWidth = 200;
            int desiredHeight = 163;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, false);
            printer.printBmp(resizedBitmap);
            printer.printStr("\n");
            printer.printStr("__________________________________\n");


            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Terminal ID");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(serialNumber);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Customer's Name");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(Fullname);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Customer's Phone");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(Phone);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Customer's NRC");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(NRC);
            printer.printStr("\n");


            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Account No.");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(Account);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("District");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(District);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("User");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr("iZyane");
            printer.printStr("\n");


            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Withdraw Amount");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr("ZMW "+ZMW_Amount);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Transaction Status");
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(Status);

            printer.printStr("\n");

            printer.printStr("\n");
            printer.setAlignStyle(PRINT_STYLE_CENTER);
            printer.printStr("__________________________________");
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Date");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            String saveCurrentDate = currentDate.format(calendar.getTime());
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(saveCurrentDate);
            printer.printStr("\n");

            //Single line print left justified, right justified
            printer.setAlignStyle(PRINT_STYLE_LEFT);
            printer.printStr("Time");
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            String saveCurrentTime = currentTime.format(calendar.getTime());
            printer.setAlignStyle(PRINT_STYLE_RIGHT);
            printer.printStr(saveCurrentTime);

            printer.printStr("\n");
            printer.setAlignStyle(PRINT_STYLE_CENTER);
            printer.printStr("__________________________________\n");
            printer.printStr("Thank You");
            printer.printStr("\n");
            printer.setAlignStyle(PRINT_STYLE_CENTER);
            printer.printStr(copy);

            ret = printer.getUsedPaperLenManage();
            if (ret < 0) {
                System.out.println("getUsedPaperLenManage failed" + String.format(" errCode = 0x%x\n", ret));
                // Toast.makeText(MomoActivity.this, "getUsedPaperLenManage failed" + String.format(" errCode = 0x%x\n", ret), Toast.LENGTH_LONG).show();
            }

            System.out.println("UsedPaperLenManage = " + ret + "mm \n");

            printer.print(new OnPrinterCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("Print Success\n");
                    printer.feed(32);
                    goBack();
                }

                @Override
                public void onError(int i) {
                    System.out.println("printBmp failed" + String.format(" errCode = 0x%x\n", i));
                    Toast.makeText(TransactActivity.this, "PrintBmp Failed" + String.format(" errCode = 0x%x\n", i), Toast.LENGTH_LONG).show();
                    goBack();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Print Failed" + e + "\n");
            Toast.makeText(TransactActivity.this, "Print Failed" + e, Toast.LENGTH_LONG).show();
            goBack();
        }
    }

    private void goBack(){

    }



}