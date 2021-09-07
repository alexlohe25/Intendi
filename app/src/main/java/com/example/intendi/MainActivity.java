package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
//import androidx.databinding.BindingAdapter;
//import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    UserLoginFragment userLogInFragment = new UserLoginFragment();
    AddUserFragment addUserFragment = new AddUserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //int count = getSupportFragmentManager().getBackStackEntryCount();

        if (addUserFragment.isAdded()) {
            getSupportFragmentManager().popBackStack();
            loadFragment(userLogInFragment);
            //additional code
        } else {
            super.onBackPressed();

        }

    }

}