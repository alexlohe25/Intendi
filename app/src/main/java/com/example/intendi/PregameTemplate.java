package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PregameTemplate extends AppCompatActivity {

    ImageView imageBubble;
    ImageView imageLogo;
    TextView textBubble;
    FloatingActionButton go_back;
    Button playButton;
    String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_template);

        imageBubble = findViewById(R.id.imageBubble);
        imageLogo = findViewById(R.id.imageLogo);
        textBubble = findViewById(R.id.textBubble);
        playButton = findViewById(R.id.playButton);

        Intent myIntent = getIntent();
        String imageBubbleDir = myIntent.getStringExtra("imageBubble");
        String imageLogoDir = myIntent.getStringExtra("imageLogo");
        String textBubbleDir = myIntent.getStringExtra("textBubble");
        gameType = myIntent.getStringExtra("game");

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

    public void startGame(View v){
        if(gameType.equals("memo")){
            //Intent gameIntent = new Intent(this, MemoryGame.class);
            //startActivity(gameIntent);
        }else if(gameType.equals("nums")){
            //Intent gameIntent = new Intent(this, SendaGame.class);
            //startActivity(gameIntent);
        }else if(gameType.equals("piano")){
            Intent gameIntent = new Intent(this, PianoGame.class);
            startActivity(gameIntent);
        }
    }
}