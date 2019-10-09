package com.badrul.qnitibox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuDisplay extends AppCompatActivity implements FoodAdapter.OnItemClicked {

    List<Food> foodList;
    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_display);


        recyclerView = findViewById(R.id.recylcerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FoodMenuDisplay.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //initializing the productlist

        foodList = new ArrayList<>();


        //this method will fetch and parse json
        //to display it in recyclerview

        loadOrder();

    }

    private void loadOrder() {

        final ProgressDialog loading = ProgressDialog.show(FoodMenuDisplay.this,"Please Wait","Contacting Server",false,false);

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

                                        food.getInt("foodID"),
                                        food.getString("foodTitle"),
                                        food.getString("foodPrice"),
                                        food.getString("foodDesc"),
                                        food.getString("foodImage")


                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            FoodAdapter adapter = new FoodAdapter(FoodMenuDisplay.this, foodList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnClick(FoodMenuDisplay.this);


                            //add shared preference ID,nama,credit here
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
                            Toast.makeText(FoodMenuDisplay.this,"No internet . Please check your connection",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            //Toast.makeText(FoodMenuDisplay.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(FoodMenuDisplay.this, "Wrong version detected. Please update QnitiBox to latest version", Toast.LENGTH_LONG).show();
                            openMarket();
                        }
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);


    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", curCheckPosition);
    }

   /* public void setRvadapter (List<Product> productList) {

        ProductsAdapter myAdapter = new ProductsAdapter(this,productList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }*/



    @Override
    public void onItemClick(int position) {
        // The onClick implementation of the RecyclerView item click
        //ur intent code here
        Food food = foodList.get(position);
        //Toast.makeText(FoodMenu.this, product.getLongdesc(),
        //      Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        // Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Adding values to editor

        editor.putString(Config.FOOD_ID, String.valueOf(food.getFoodID()));
        editor.putString(Config.MENU_TYPE, food.getFoodTitle());
        editor.putString(Config.FOOD_PRICE,food.getFoodPrice());
        editor.putString(Config.FOOD_DESC,food.getFoodDesc());
        editor.putString(Config.FOOD_IMAGE,food.getFoodImage());

        // Saving values to editor
        editor.commit();

        Intent i = new Intent(FoodMenuDisplay.this, FoodDisplay.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        //finish();
    }

    public void openMarket(){

        final String LiveAppPackage = "com.badrul.qnitibox";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FoodMenuDisplay.this);
        alertDialogBuilder.setMessage("Do you want to update to latest version?");
        final Dialog dialog = new Dialog(FoodMenuDisplay.this);

        alertDialogBuilder.setPositiveButton(getString(R.string.btn_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog.setCanceledOnTouchOutside(true);
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("market://details?id="
                                            + LiveAppPackage)));
                        } catch (android.content.ActivityNotFoundException e1) {
                            startActivity(new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id="
                                            + LiveAppPackage)));
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton(getString(R.string.btn_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog.setCanceledOnTouchOutside(true);
                        Intent i = new Intent(FoodMenuDisplay.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();


                    }
                });
        alertDialogBuilder.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Intent i = new Intent(FoodMenuDisplay.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }
        );

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



    }
}
