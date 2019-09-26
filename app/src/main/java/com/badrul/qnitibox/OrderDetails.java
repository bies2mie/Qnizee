package com.badrul.qnitibox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class OrderDetails extends AppCompatActivity {

    TextView order,name,phone,email,matrix,orType,orDay,orDatenTime,orQTT,orUsrType,pickupLo,pickupTime,orStat,comDatenTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String orderID = sharedPreferences.getString(Config.ORDER_ID, "Not Available");
        String nameID = sharedPreferences.getString(Config.NAME_ID, "Not Available");
        String phoneID = sharedPreferences.getString(Config.PHONE_ID, "Not Available");
        String emailID = sharedPreferences.getString(Config.EMAIL_ID, "Not Available");
        String matrixID = sharedPreferences.getString(Config.MATRIX_ID, "Not Available");
        String orderType = sharedPreferences.getString(Config.ORDER_TYPE, "Not Available");
        String orderDay = sharedPreferences.getString(Config.ORDER_DAY, "Not Available");
        String orderDate = sharedPreferences.getString(Config.ORDER_DATE2, "Not Available");
        String orderTime = sharedPreferences.getString(Config.ORDER_TIME2, "Not Available");
        String orderQTT = sharedPreferences.getString(Config.ORDER_QTT, "Not Available");
        String orderUserType = sharedPreferences.getString(Config.ORDER_USERTYPE, "Not Available");
        String puLocation = sharedPreferences.getString(Config.PICKUP_LOCATION, "Not Available");
        String puTime = sharedPreferences.getString(Config.PICKUP_TIME, "Not Available");
        String orderStatus = sharedPreferences.getString(Config.ORDER_STATUS, "Not Available");
        String completeDate = sharedPreferences.getString(Config.ORDER_COMPLETEDATE, "Not Available");
        String completeTime = sharedPreferences.getString(Config.ORDER_COMPLETETIME, "Not Available");

        order = findViewById(R.id.orderIDtxt);
        name = findViewById(R.id.buyerNametxt);
        phone = findViewById(R.id.phoneNumtxt);
        email = findViewById(R.id.buyerEmailtxt);
        matrix = findViewById(R.id.matrixIDtxt);
        orType = findViewById(R.id.orderTypetxt);
        orDay = findViewById(R.id.orderDayTxt);
        orDatenTime = findViewById(R.id.purchasedateNtime);
        orQTT = findViewById(R.id.orderqtt);
        orUsrType = findViewById(R.id.orderUserTypetxt);
        pickupLo = findViewById(R.id.puLoctxt);
        pickupTime = findViewById(R.id.puTimetxt);
        orStat = findViewById(R.id.orderStat);
        comDatenTime = findViewById(R.id.completedatentime);

        order.setText(orderID);
        name.setText(nameID);
        phone.setText(phoneID);
        email.setText(emailID);
        matrix.setText(matrixID);
        orType.setText(orderType);
        orDay.setText(orderDay);
        orDatenTime.setText(orderDate+" "+orderTime);
        orQTT.setText("Quantity: "+orderQTT);
        orUsrType.setText(orderUserType);
        pickupLo.setText(puLocation);
        pickupTime.setText(puTime);
        orStat.setText(orderStatus);
        comDatenTime.setText(completeDate+" "+completeTime);


        ImageView imgView = findViewById(R.id.imageViewQR);
        TextView showQR = findViewById(R.id.showqrtxt);

        if (orderStatus.equalsIgnoreCase("Complete")){

            imgView.setVisibility(View.GONE);
            showQR.setVisibility(View.GONE);

        }


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();


        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(orderID, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgView.setImageBitmap(bitmap);
        } catch (
                WriterException e) {
            e.printStackTrace();
        }

    }


}
