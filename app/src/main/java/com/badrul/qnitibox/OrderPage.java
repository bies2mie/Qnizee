package com.badrul.qnitibox;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

public class OrderPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    boolean openF2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openF2"))
            openF2 = extras.getBoolean("openF2");
        if(openF2){
            CompleteFragment fragment = new CompleteFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        //loading the default fragment
        loadFragment(new NonCompleteFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_noncomplete:
                fragment = new NonCompleteFragment();
                break;

            case R.id.navigation_complete:
                fragment = new CompleteFragment();
                break;

            case R.id.navigation_cancel:
                fragment = new CancelFragment();
                break;


        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}