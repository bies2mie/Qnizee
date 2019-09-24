package com.badrul.qnitibox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class OrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String orderID = sharedPreferences.getString(Config.ORDER_ID, "Not Available");
        String nameID = sharedPreferences.getString(Config.NAME_ID2, "Not Available");
        String phoneID = sharedPreferences.getString(Config.PHONE_ID2, "Not Available");
        String emailID = sharedPreferences.getString(Config.EMAIL_ID2, "Not Available");
        String matrixID = sharedPreferences.getString(Config.MATRIX_ID2, "Not Available");
        String orderType = sharedPreferences.getString(Config.ORDER_TYPE, "Not Available");
        String orderDay = sharedPreferences.getString(Config.ORDER_DAY, "Not Available");
        String orderDate = sharedPreferences.getString(Config.ORDER_DATE2, "Not Available");
        String orderTime = sharedPreferences.getString(Config.ORDER_TIME2, "Not Available");
        String orderQTT = sharedPreferences.getString(Config.ORDER_QTT, "Not Available");
        String orderUserType = sharedPreferences.getString(Config.ORDER_USERTYPE, "Not Available");
        String puLocation = sharedPreferences.getString(Config.PICKUP_LOCATION, "Not Available");
        String puTime = sharedPreferences.getString(Config.PICKUP_TIME, "Not Available");
        String orderStatus = sharedPreferences.getString(Config.ORDER_STATUS, "Not Available");
        String completeDate = sharedPreferences.getString(Config.COMPLETE_DATE, "Not Available");
        String completeTime = sharedPreferences.getString(Config.COMPLETE_TIME, "Not Available");


        ImageView imgView = findViewById(R.id.imageViewQR);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();


        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(orderID, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgView.setImageBitmap(bitmap);
        } catch (
                WriterException e) {
            e.printStackTrace();
        }

    }


}
