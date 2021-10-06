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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "user_data.xml";
    private Properties userData;
    DBHandler dbHandler;
    UserLoginFragment userLogInFragment = new UserLoginFragment();
    AddUserFragment addUserFragment = new AddUserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userData = new Properties();
        //loadResults();
        dbHandler = dbHandler.getInstance(getApplicationContext());
        userLogInFragment.setDbHandler(dbHandler);
        addUserFragment.setDbHandler(dbHandler);
        setContentView(R.layout.activity_main);
        loadFragment(userLogInFragment);
        //BindingAdapter binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_frame_container, fragment);
        transaction.commit();
    }
    public void registro(View v){
        loadFragment(addUserFragment);
    }
    public void menu(View v){
        Intent miIntent = new Intent(this, BottomNavigation.class);
        startActivity(miIntent);
    }

    @Override
    public void onBackPressed() {
        if (addUserFragment.isAdded()) {
            getSupportFragmentManager().popBackStack();
            loadFragment(userLogInFragment);
        } else {
            super.onBackPressed();
        }
    }
    /*
    private void loadResults(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            userData.loadFromXML(fis);
            fis.close();
        }
        catch (FileNotFoundException fnfe){
            //results.setProperty("Negro","0");
            saveResults();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    private void saveResults(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            userData.storeToXML(fos, null);
            fos.close();

        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }*/

}