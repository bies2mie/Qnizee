package com.badrul.qnitibox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {

    TextView order,name,phone,email,matrix,orType,orDay,orDatenTime,orQTT,orUsrType,pickupLo,pickupTime,orStat,comDatenTime,totprice,canTitle,canMsg;
    String foodID,orderID;
    Button cancelOrder;
    List<SaleTime> saletimeList;
    int saletimeID;
    int saleStart;
    int saleEnd;
    int hour;
    String curTime;
    String currentDate;
    String userID;
    String orderStatus;
    String orderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        orderID = sharedPreferences.getString(Config.ORDER_ID, "Not Available");
        userID = sharedPreferences.getString(Config.USER_ID2,"Not Available");
        String nameID = sharedPreferences.getString(Config.NAME_ID, "Not Available");
        String phoneID = sharedPreferences.getString(Config.PHONE_ID, "Not Available");
        String emailID = sharedPreferences.getString(Config.EMAIL_ID, "Not Available");
        String matrixID = sharedPreferences.getString(Config.MATRIX_ID, "Not Available");
        String orderType = sharedPreferences.getString(Config.ORDER_TYPE, "Not Available");
        String orderDay = sharedPreferences.getString(Config.ORDER_DAY, "Not Available");
        orderDate = sharedPreferences.getString(Config.ORDER_DATE2, "Not Available");
        String orderTime = sharedPreferences.getString(Config.ORDER_TIME2, "Not Available");
        String orderQTT = sharedPreferences.getString(Config.ORDER_QTT, "Not Available");
        String orderUserType = sharedPreferences.getString(Config.ORDER_USERTYPE, "Not Available");
        String puLocation = sharedPreferences.getString(Config.PICKUP_LOCATION, "Not Available");
        String puTime = sharedPreferences.getString(Config.PICKUP_TIME, "Not Available");
        orderStatus = sharedPreferences.getString(Config.ORDER_STATUS, "Not Available");
        String completeDate = sharedPreferences.getString(Config.ORDER_COMPLETEDATE, "Not Available");
        String completeTime = sharedPreferences.getString(Config.ORDER_COMPLETETIME, "Not Available");
        String totalPrice1 = sharedPreferences.getString(Config.TOTAL_FOOD_PRICE, "Not Available");
        foodID = sharedPreferences.getString(Config.ORDER_FOODID, "Not Available");
        String cancelMsg = sharedPreferences.getString(Config.ORDER_CANCELMSG, "Not Available");

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
        totprice = findViewById(R.id.totalPricetxt);
        canMsg = findViewById(R.id.cancelTxt);
        canTitle = findViewById(R.id.cancelTitle);
        cancelOrder = findViewById(R.id.cancelOrderBtn);

        saletimeList = new ArrayList<>();
        Calendar currTime = Calendar.getInstance();
        hour = currTime.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        curTime = sdf.format(currTime.getTime());
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        float scale = getResources().getDisplayMetrics().density;
        final int dpAsPixels = (int) (10*scale + 0.5f); //10 is the intended DP value

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
        totprice.setText("RM "+totalPrice1);
        canMsg.setText(cancelMsg);


        ImageView imgView = findViewById(R.id.imageViewQR);
        TextView showQR = findViewById(R.id.showqrtxt);

        if (orderStatus.equalsIgnoreCase("Complete")){

            imgView.setVisibility(View.GONE);
            showQR.setVisibility(View.GONE);
            //cancelOrder.setVisibility(View.GONE);
            canTitle.setVisibility(View.GONE);
            canMsg.setVisibility(View.GONE);
        }

        if (orderStatus.equalsIgnoreCase("Processing")){

            imgView.setVisibility(View.VISIBLE);
            showQR.setVisibility(View.VISIBLE);
            //cancelOrder.setVisibility(View.VISIBLE);
            canTitle.setVisibility(View.GONE);
            canMsg.setVisibility(View.GONE);
        }

        if (orderStatus.equalsIgnoreCase("Cancel")){

            imgView.setVisibility(View.GONE);
            showQR.setVisibility(View.GONE);
            //cancelOrder.setVisibility(View.GONE);
            canTitle.setVisibility(View.VISIBLE);
            canMsg.setVisibility(View.VISIBLE);
        }
        checkSaleDate();


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


        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetails.this);
                //Adding Edit Text to Dialog
                final EditText edittext = new EditText(OrderDetails.this);
                edittext.setBackgroundResource(R.drawable.textboxcornergrey);
                edittext.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
                FrameLayout container = new FrameLayout(OrderDetails.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                edittext.setLayoutParams(params);
                container.addView(edittext);

                alertDialogBuilder.setView(container);

                alertDialogBuilder.setMessage("Do you want to cancel this order? Please give a reason");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                final String cancelMsg = edittext.getText().toString().trim();


                                try {
                                    if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 0) {

                                        Toast.makeText(getApplicationContext(),
                                                "Please set Automatic Date & Time to ON in the Settings",
                                                Toast.LENGTH_LONG).show();

                                        startActivityForResult(
                                                new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                    } else if (Settings.Global.getInt(getContentResolver(),
                                            Settings.Global.AUTO_TIME_ZONE) == 0) {

                                        Toast.makeText(getApplicationContext(),
                                                "Please set Automatic Time Zone to ON in the Settings",
                                                Toast.LENGTH_LONG).show();

                                        startActivityForResult(
                                                new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                    }

                                    else if (cancelMsg.length()<10){

                                        Toast.makeText(OrderDetails.this, "Reasoning must be more than 10 characters", Toast.LENGTH_LONG).show();

                                    }else{

                                        final ProgressDialog loading = ProgressDialog.show(OrderDetails.this,"Please Wait","Sending Data",false,false);

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.CANCEL_ORDER_URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(OrderDetails.this, response, Toast.LENGTH_LONG)
                                                        .show();

                                                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,
                                                        Context.MODE_PRIVATE);

                                                // Creating editor to store values to shared preferences
                                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                                // Adding values to editor

                                                editor.putString(Config.ORDER_ID, "0");

                                                editor.commit();

                                                Intent i = new Intent(OrderDetails.this, MainActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                    Toast.makeText(OrderDetails.this,"No internet . Please check your connection",
                                                            Toast.LENGTH_LONG).show();

                                                    finish();
                                                    overridePendingTransition(0, 0);
                                                    startActivity(getIntent());
                                                    overridePendingTransition(0, 0);
                                                }
                                                else{

                                                    Toast.makeText(OrderDetails.this, error.toString(), Toast.LENGTH_LONG).show();

                                                    finish();
                                                    overridePendingTransition(0, 0);
                                                    startActivity(getIntent());
                                                    overridePendingTransition(0, 0);
                                                }
                                                loading.dismiss();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("orderID",orderID);
                                                params.put("adminID", userID);
                                                params.put("completeDate", currentDate);
                                                params.put("completeTime", curTime);
                                                params.put("cancelMsg", cancelMsg);


                                                return params;
                                            }

                                        };
                                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                                30000,
                                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        requestQueue.add(stringRequest);


                                    }

                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }



                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
    public void checkSaleDate(){
        final ProgressDialog loading = ProgressDialog.show(this,"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Config.URL_CHECKSALEDATE+foodID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject saletime = array.getJSONObject(i);

                        //adding the product to product list
                        saletimeList.add(new SaleTime(
                                saletimeID = saletime.getInt("saletimeID"),
                                saleStart = saletime.getInt("saleStart"),
                                saleEnd = saletime.getInt("saleEnd")

                        ));

                    }

                    if ((hour >= saleStart && hour < saleEnd)&& orderStatus.equalsIgnoreCase("Processing") && orderDate.equalsIgnoreCase(currentDate))
                    {

                        cancelOrder.setVisibility(View.VISIBLE);

                    }else{

                        cancelOrder.setVisibility(View.GONE);

                    }

                    loading.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(OrderDetails.this,"No internet . Please check your connection",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                            Toast.makeText(OrderDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
