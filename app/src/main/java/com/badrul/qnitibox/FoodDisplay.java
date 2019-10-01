package com.badrul.qnitibox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class FoodDisplay extends AppCompatActivity {

    List<Food> foodList;
    ImageButton continueBtn;
    ImageButton eventBtn;
    TextView title,price,desc;
    ImageView showFood;
    ProgressBar progressBar;

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

        foodList = new ArrayList<>();

        show_Food();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDisplay.this, NewIndvOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
    public void show_Food(){

        final ProgressDialog loading = ProgressDialog.show(this,"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.SHOW_FOOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject food = array.getJSONObject(i);

                                //adding the product to product list
                                foodList.add(new Food(
                                        foodID = food.getInt("foodID"),
                                        foodtitle = food.getString("foodTitle"),
                                        foodprice = food.getString("foodPrice"),
                                        fooddesc = food.getString("foodDesc"),
                                        foodimage = food.getString("foodImage")


                                ));

                            }

                            title.setText(foodtitle);
                            price.setText("RM "+foodprice);
                            desc.setText(fooddesc);

                            //add shared preference ID,nama,credit here
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,
                                    Context.MODE_PRIVATE);

                            // Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // Adding values to editor

                            editor.putString(Config.FOOD_ID, String.valueOf(foodID));
                            editor.putString(Config.MENU_TYPE, foodtitle);
                            editor.putString(Config.FOOD_PRICE,foodprice);

                            // Saving values to editor
                            editor.commit();

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
                            Toast.makeText(FoodDisplay.this,"No internet . Please check your connection",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                            Toast.makeText(FoodDisplay.this, error.toString(), Toast.LENGTH_LONG).show();
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
