package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
//import androidx.databinding.BindingAdapter;
//import androidx.databinding.DataBindingUtil;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    UserLoginFragment userLogInFragment = new UserLoginFragment();
    AddUserFragment addUserFragment = new AddUserFragment();
    Button okInfoButton;
    View info_screen;

    @Override
    //Start instance of database and load content.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = dbHandler.getInstance(getApplicationContext());
        setContentView(R.layout.activity_main);
        loadFragment(userLogInFragment);
        okInfoButton = findViewById(R.id.okInfoButton);
        info_screen = findViewById(R.id.info_screen);

        okInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_screen.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Function to load any fragment through replacement.
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_frame_container, fragment);
        transaction.commit();
    }
    //Function to load the user creation screen fragment.
    public void registro(View v){
        loadFragment(addUserFragment);
    }

    public void info(View v){
        info_screen.setVisibility(View.VISIBLE);
    }

    //Override to prevent exiting the app through the user creation screen, and instead
    //returns to the login screen.
    @Override
    public void onBackPressed() {
        if (addUserFragment.isAdded()) {
            getSupportFragmentManager().popBackStack();
            loadFragment(userLogInFragment);
        } else {
            super.onBackPressed();
        }
    }

}