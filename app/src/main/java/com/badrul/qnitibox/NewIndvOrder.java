package com.badrul.qnitibox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewIndvOrder extends AppCompatActivity implements OnItemSelectedListener {

    public static final String NEW_CONFIRMORDER_URL = "http://gmartbox.cvmall.my/apps/neworder.php";
    public static final String EMAIL_IDV_URL = "http://atsventures.com/mail/indmailer.php";

    public static final String KEY_MENUTYPE = "menuType";
    public static final String KEY_MENUDAY = "menuDay";
    public static final String KEY_ORDER_DATE = "orderDate";
    public static final String KEY_ORDER_TIME = "orderTime";
    public static final String KEY_CARDNUM = "userCard";
    public static final String KEY_PHONE = "phoneID";
    public static final String KEY_NAME = "nameID";
    public static final String KEY_MENUQTT = "userQtt";
    public static final String KEY_MENUSTATUS = "orderStatus";
    public static final String KEY_LOCATION = "puLocation";
    public static final String KEY_EMAIL = "emailID";
    public static final String KEY_USERID = "userID";
    public static final String KEY_FOODID = "foodID";

    String userID;
    String nameID;
    String phoneID;
    String emailID;
    String matrixID;
    String menuType;
    String menuDay;
    String orderDate;
    String orderTime;
    // String emailID;
    String locat = "";
    String myStatus = "Processing";
    private PopupWindow pwindo;
    final Context context = this;
    int id = 0;
    Button nextBtn;
    ImageButton exit;
    ImageButton staffOrder;
    TextView menuTypeD;
    TextView menuDayD;
    EditText cardNum;
    EditText qttNum;
    Spinner sp;
    List<String> list;
    ArrayAdapter<String> adp;
    Boolean loggedIn;
    String foodID;
    // int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_indv_order);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        menuType = sharedPreferences.getString(Config.MENU_TYPE, "Not Available");
        menuDay = sharedPreferences.getString(Config.MENU_DAY, "Not Available");
        orderDate = sharedPreferences.getString(Config.ORDER_DATE, "Not Available");
        orderTime = sharedPreferences.getString(Config.ORDER_TIME, "Not Available");
        userID = sharedPreferences.getString(Config.USER_ID2,"0");
        nameID = sharedPreferences.getString(Config.NAME_ID2, "Not Available");
        phoneID = sharedPreferences.getString(Config.PHONE_ID2, "Not Available");
        emailID = sharedPreferences.getString(Config.EMAIL_ID2, "Not Available");
        matrixID = sharedPreferences.getString(Config.MATRIX_ID2, "Not Available");
        foodID = sharedPreferences.getString(Config.FOOD_ID, "Not Available");

        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        //If we will get true
        if(loggedIn==false){
            //We will start the Profile Activity
            Intent intent = new Intent(NewIndvOrder.this, LoginPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        TextView showName = findViewById(R.id.nameText);
        TextView showPhone = findViewById(R.id.phoneNum);
        TextView showEmail = findViewById(R.id.emailIDtxt);
        TextView showMatrix = findViewById(R.id.matrixNum);

        showName.setText(nameID);
        showEmail.setText(emailID);
        showMatrix.setText(matrixID);
        showPhone.setText(phoneID);

        nextBtn = findViewById(R.id.nextBtn);
        exit = findViewById(R.id.exit);
        // staffOrder =(ImageButton) findViewById(R.id.stafforder);
        menuTypeD = findViewById(R.id.menuTypeDisplay);
        menuDayD = findViewById(R.id.menuDayDisplay);
        cardNum = findViewById(R.id.cardNum);
        qttNum = findViewById(R.id.qttNum);
        sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // to convert Date to String, use format method of SimpleDateFormat
        // class.
        String strDate = dateFormat.format(dt);

        menuDayD.setText(orderDate);
        menuTypeD.setText(menuType);

        RadioGroup locationType = findViewById(R.id.radioType);

        locationType.clearCheck();
        locationType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.inasis) {


                            list = new ArrayList<>();

                            list.add("Pejabat Inasis Bank Rakyat (Pick Up: 7 PM - 9 PM)");
                            list.add("Pusat Dobi SME (Pick Up: 7 PM - 9 PM)");

                            adp = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, list);
                            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp.setAdapter(adp);

                    }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String myCard = cardNum.getText().toString().trim();
                final String myQtt = qttNum.getText().toString().trim();
                final int result = Integer.parseInt(myQtt);

                if (locat == "") {
                    Toast.makeText(getApplicationContext(), "Please select pickup location",
                            Toast.LENGTH_LONG).show();
                } else if (result < 1) {
                    Toast.makeText(getApplicationContext(), "Please enter minimum 1 order",
                            Toast.LENGTH_LONG).show();
                } else if (result > 10) {
                    Toast.makeText(getApplicationContext(),
                            "Cannot order more than 10. Maximum allowed per order reached",
                            Toast.LENGTH_LONG).show();
                }
                /*
                 * else if (myCard.length() < 5 ||myCard.length() >
                 * 10) { Toast.makeText(getApplicationContext(),
                 * "Please enter proper ATS Card number!",
                 * Toast.LENGTH_LONG).show(); }
                 */
                else

                    try {
                        // We need to get the instance of the LayoutInflater
                        LayoutInflater inflater = (LayoutInflater) NewIndvOrder.this
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = inflater.inflate(R.layout.popup_confirmationorder,
                                (ViewGroup) findViewById(R.id.popup_element));
                        pwindo = new PopupWindow(layout, 700, 1000, true);
                        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
                        Button cancel = (Button) layout.findViewById(R.id.cancelBtn);
                        Button confirm = (Button) layout.findViewById(R.id.confirmBtn);

                        cancel.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                pwindo.dismiss();
                            }
                        });

                        confirm.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                try {
                                    if (Settings.Global.getInt(getContentResolver(), Global.AUTO_TIME) == 0) {

                                        Toast.makeText(getApplicationContext(),
                                                "Please set Automatic Date & Time to ON in the Settings",
                                                Toast.LENGTH_LONG).show();

                                        startActivityForResult(
                                                new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                    } else if (Settings.Global.getInt(getContentResolver(),
                                            Global.AUTO_TIME_ZONE) == 0) {

                                        Toast.makeText(getApplicationContext(),
                                                "Please set Automatic Time Zone to ON in the Settings",
                                                Toast.LENGTH_LONG).show();

                                        startActivityForResult(
                                                new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                    }

                                    final ProgressDialog loading = ProgressDialog.show(NewIndvOrder.this,"Please Wait","Contacting Server",false,false);

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                            NEW_CONFIRMORDER_URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(NewIndvOrder.this, response, Toast.LENGTH_LONG)
                                                    .show();

                                            loading.dismiss();

                                            Intent i = new Intent(NewIndvOrder.this, MenuType.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            loading.dismiss();
                                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                Toast.makeText(NewIndvOrder.this,"No internet . Please check your connection",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            else{

                                                Toast.makeText(NewIndvOrder.this, error.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put(KEY_MENUTYPE, menuType);
                                            params.put(KEY_MENUDAY, menuDay);
                                            params.put(KEY_ORDER_DATE, orderDate);
                                            params.put(KEY_ORDER_TIME, orderTime);
                                            params.put(KEY_CARDNUM, myCard);
                                            params.put(KEY_MENUQTT, myQtt);
                                            params.put(KEY_MENUSTATUS, myStatus);
                                            params.put(KEY_LOCATION, locat);
                                            params.put(KEY_USERID, userID);
                                            params.put(KEY_FOODID, foodID);
                                            return params;
                                        }

                                    };

                                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                            30000,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(stringRequest);

                                    emailer();
                                    notifyMe();
                                }
                                catch (SettingNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent i = new Intent(NewIndvOrder.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        /*
         * staffOrder.setOnClickListener(new View.OnClickListener() { public
         * void onClick(View view) {
         *
         * Intent i = new Intent(IndvOrder.this, StaffOrder.class);
         * i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(i);
         * finish(); } });
         */

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        locat = parent.getSelectedItem().toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + locat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(arg0.getContext(), "Please Select Your Pickup Location", Toast.LENGTH_LONG).show();

    }

    @TargetApi(24)
    public void notifyMe() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Order Notifications")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your order has been recorded \n\nPlease pay during delivery. Check your email for order details"))
                .setContentText("Your order has been recorded");

        Intent notifyIntent = new Intent(this, MainActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(notifyPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());

    }

    public void emailer() {

        final String myQtt = qttNum.getText().toString().trim();
        final ProgressDialog loading = ProgressDialog.show(NewIndvOrder.this,"Please Wait","Contacting Server",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EMAIL_IDV_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        // Toast.makeText(IndvOrder.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(NewIndvOrder.this,"No internet . Please check your connection",
                            Toast.LENGTH_LONG).show();
                }
                else{

                    Toast.makeText(NewIndvOrder.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_MENUTYPE, menuType);
                params.put(KEY_MENUDAY, menuDay);
                params.put(KEY_ORDER_DATE, orderDate);
                params.put(KEY_ORDER_TIME, orderTime);
                params.put(KEY_PHONE, phoneID);
                params.put(KEY_NAME, nameID);
                params.put(KEY_MENUQTT, myQtt);
                params.put(KEY_MENUSTATUS, myStatus);
                params.put(KEY_LOCATION, locat);
                params.put(KEY_EMAIL, emailID);
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
}