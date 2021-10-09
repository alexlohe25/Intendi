package com.example.intendi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigation extends AppCompatActivity {
    User currentUser;

    MenuJuegosFragment menuJuegosFragment = new MenuJuegosFragment();
    ResultadosFragment resultadosFragment = new ResultadosFragment();
    AjustesFragment ajustesFragment = new AjustesFragment();
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = dbHandler.getInstance(getApplicationContext());
        setContentView(R.layout.activity_bottom_navigation);
        currentUser = (User)getIntent().getSerializableExtra("User");
        currentUser = dbHandler.getCurrentUser(currentUser.getUser_id());
        menuJuegosFragment.setCurrentUser(currentUser);
        resultadosFragment.setCurrentUser(currentUser);
        ajustesFragment.setCurrentUser(currentUser);
        loadFragment(menuJuegosFragment);
        BottomNavigationView  navigation = findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuJuegosFragment:
                        loadFragment(menuJuegosFragment);
                        return true;
                    case R.id.resultadosFragment:
                        loadFragment(resultadosFragment);
                        return true;
                    case R.id.ajustesFragment:
                        loadFragment(ajustesFragment);
                        return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}