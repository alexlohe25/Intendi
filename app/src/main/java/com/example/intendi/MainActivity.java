package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void prueba(View v){
        Intent miIntent = new Intent(MainActivity.this, Registro_screen.class);
        startActivity(miIntent);
    }
}