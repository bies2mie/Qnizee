package com.badrul.qnitibox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodDisplay extends AppCompatActivity {

    ImageButton continueBtn;
    ImageButton eventBtn;
    TextView title,price,desc;
    ImageView showFood;

    String menuType;//Data for database;foodTitle
    String foodtitle,foodprice,fooddesc;
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
    }
}
