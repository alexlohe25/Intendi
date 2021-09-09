package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PregameTemplate extends AppCompatActivity {

    ImageView imageBubble;
    ImageView imageLogo;
    TextView textBubble;
    FloatingActionButton go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_template);

        imageBubble = findViewById(R.id.imageBubble);
        imageLogo = findViewById(R.id.imageLogo);
        textBubble = findViewById(R.id.textBubble);

        Intent myIntent = getIntent();
        String imageBubbleDir = myIntent.getStringExtra("imageBubble");
        String imageLogoDir = myIntent.getStringExtra("imageLogo");
        String textBubbleDir = myIntent.getStringExtra("textBubble");

        int idImageBubble = this.getResources().getIdentifier(imageBubbleDir, "drawable", this.getPackageName());
        int idLogo = this.getResources().getIdentifier(imageLogoDir, "drawable", this.getPackageName());

        imageBubble.setImageResource(idImageBubble);
        imageLogo.setImageResource(idLogo);
        textBubble.setText(textBubbleDir);

        go_back = findViewById(R.id.returnButton);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}