package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
//import androidx.databinding.BindingAdapter;
//import androidx.databinding.DataBindingUtil;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.security.acl.Group;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout usersGrid = (TableLayout)findViewById(R.id.usersGrid);
        int users = 0;

        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();

        //BindingAdapter binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        for (int i=0;i<3;i++){
            TableRow line = (TableRow)usersGrid.getChildAt(i);
            for(int j=0;j<2;j++)
            {
                users+=1;
                TableRow.LayoutParams vp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                vp.weight = 1;
                View  layout = inflater.inflate(R.layout.user_login_space, null);

                TextView textView = (TextView) layout.findViewById(R.id.username);
                textView.setText("user " + users);
                ImageView profilePic = (ImageView) layout.findViewById(R.id.userAvatar);
                profilePic.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.iguanee));
                //profilePic.setImageDrawable(Drawable.createFromPath("@mipmap/delphi"));

                layout.setLayoutParams(vp);
                line.addView(layout,j);
            }
        }

    }
    public void registro(View v){
        Intent miIntent = new Intent(MainActivity.this, Registro_screen.class);
        startActivity(miIntent);
    }
    public void menu(View v){
        Intent miIntent = new Intent(MainActivity.this, BottomNavigation.class);
        startActivity(miIntent);
    }
}