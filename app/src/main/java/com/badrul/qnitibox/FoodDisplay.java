package com.badrul.qnitibox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodDisplay extends AppCompatActivity {

    List<Food> foodList;
    List<User> userList;
    ImageButton continueBtn;
    ImageButton eventBtn;
    TextView title,price,desc;
    ImageView showFood;
    ProgressBar progressBar;
    String my_date ;

    int userID;
    String nameID;
    String phoneID;
    String emailID;
    String matrixID;
    String userLocation;
    String userEmailID;

    String menuType;//Data for database;foodTitle
    String foodtitle,foodprice,fooddesc,foodimage;
    int foodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_display);

        continueBtn = findViewById(R.id.continueBtn);
        eventBtn = findViewById(R.id.eventBtn);
        title = findViewById(R.id.showTitle);
        price = findViewById(R.id.showPrice);
        desc = findViewById(R.id.foodDesc);
        showFood = findViewById(R.id.imageFood);
        progressBar = findViewById(R.id.progress);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userEmailID = sharedPreferences.getString(Config.ID_SHARED_PREF, "Not Available");
        foodtitle = sharedPreferences.getString(Config.MENU_TYPE, "Not Available");
        foodprice= sharedPreferences.getString(Config.FOOD_PRICE, "Not Available");
        fooddesc= sharedPreferences.getString(Config.FOOD_DESC, "Not Available");
        foodimage= sharedPreferences.getString(Config.FOOD_IMAGE, "Not Available");

        userList = new ArrayList<>();
        foodList = new ArrayList<>();
        checkDate();

        title.setText(foodtitle);
        price.setText("RM "+foodprice);
        desc.setText(fooddesc);

        RequestOptions options = new RequestOptions().centerCrop().dontAnimate().placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher);
        Glide
                .with(FoodDisplay.this)
                .load(foodimage).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                showFood.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                showFood.setVisibility(View.VISIBLE);
                return false;
            }
        })
                .into(showFood);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = sdf.parse(my_date);
                        if (System.currentTimeMillis() < strDate.getTime()) {

                            Toast.makeText(FoodDisplay.this,"You can start ordering on "+my_date,
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                        Intent intent = new Intent(FoodDisplay.this, NewIndvOrder.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        }

                } catch (Settings.SettingNotFoundException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(FoodDisplay.this,"This feature coming soon. Stay updated",
                        Toast.LENGTH_LONG).show();

            }
        });



    }

    public void checkDate(){

        final ProgressDialog loading = ProgressDialog.show(this,"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Config.URL_CHECKDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                my_date = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(FoodDisplay.this,
                            "No internet. Please check your connection",
                            Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(FoodDisplay.this,
                            error.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        })
                ;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
