package com.badrul.qnitibox;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Calendar startDate;
    Calendar calendar1;
    Calendar calendar2;
    Calendar calendar3;
    Date x;
    String startTime = "00:00:00";;
    String stopTime = "19:59:00";
    Button startOrder;

    int hour;
    String curTime;
    String currentDate;
    ImageButton feedback;
    String menuDay;
    Button prv2;
    String userLocation;
    String location;
    int checkedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userLocation = sharedPreferences.getString(Config.LOCATION_ID2, "Not Available");

        feedback = findViewById(R.id.feedbackbtn);
        startOrder = findViewById(R.id.startOrder);
        prv2 = findViewById(R.id.prevorder2);


        prv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, OrderPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        if (userLocation.equalsIgnoreCase("Not Available")||userLocation.equalsIgnoreCase("")||userLocation.isEmpty()){

            showAlertDialog();

        }

        startDate = Calendar.getInstance();

        Calendar currTime = Calendar.getInstance();
        hour = currTime.get(Calendar.HOUR_OF_DAY);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        curTime = sdf.format(currTime.getTime());

        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        feedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, FeedbackPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //finish();
            }
        });



        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) { // x.after(calendar1.getTime())

                    menuDay = "Sunday";
                    sharedpref();


                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)) {

                    menuDay = "Monday";
                    sharedpref();


                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) ) {//Check THis


                    menuDay = "Tuesday";
                    sharedpref();




                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) ) {

                    menuDay = "Wednesday";
                    sharedpref();


                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) {

                    menuDay = "Thursday";
                    sharedpref();



                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {

                    menuDay = "Friday";
                    sharedpref();



                } else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {

                    menuDay = "Saturday";
                    sharedpref();


                } else {

                    Intent i = new Intent(MainActivity.this, ErrorPage2.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    public void sharedpref() {

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        // Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Adding values to editor

        editor.putString(Config.ORDER_TIME, curTime);
        editor.putString(Config.ORDER_DATE, currentDate);
        editor.putString(Config.MENU_DAY, menuDay);

        // Saving values to editor
        editor.commit();

        Intent i = new Intent(MainActivity.this, InasisDisplay.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    public void adjustFontScale(Configuration configuration)
    {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Please select your location");
        String[] items = {"UUM","UNIMAP"};
        checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        location = "UUM";
                        checkedItem = 0;
                        break;
                    case 1:
                        location = "UNIMAP";
                        checkedItem = 1;
                        break;
                }
            }
        });

        alertDialog.setPositiveButton(getString(R.string.btn_confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {

                        if (checkedItem < 0) {
                            Toast.makeText(MainActivity.this, "Please select your location", Toast.LENGTH_LONG).show();
                            showAlertDialog();

                        } else {

                            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME,
                                    Context.MODE_PRIVATE);

                            // Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // Adding values to editor

                            editor.putString(Config.LOCATION_ID2, location);

                            // Saving values to editor
                            editor.commit();
                        }
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}
