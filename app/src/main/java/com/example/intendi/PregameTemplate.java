package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PregameTemplate extends AppCompatActivity {

    ImageView imageBubble;
    ImageView imageLogo;
    TextView textBubble;
    FloatingActionButton go_back;
    Button playButton, libreButton;
    String gameType;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_template);

        imageBubble = findViewById(R.id.imageBubble);
        imageLogo = findViewById(R.id.imageLogo);
        textBubble = findViewById(R.id.textBubble);
        playButton = findViewById(R.id.playButton);

        libreButton = findViewById(R.id.libreButton);

        Intent myIntent = getIntent();
        String imageBubbleDir = myIntent.getStringExtra("imageBubble");
        String imageLogoDir = myIntent.getStringExtra("imageLogo");
        String textBubbleDir = myIntent.getStringExtra("textBubble");

        //Change texts and images to match selected game
        gameType = myIntent.getStringExtra("game");
        currentUser = (User)myIntent.getSerializableExtra("User");

        //Piano has extra option, extra button is shown
        if (gameType.equals("piano")){
            libreButton.setVisibility(View.VISIBLE);
            libreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gameIntent = new Intent(view.getContext(), PianoFree.class);
                    startActivity(gameIntent);
                }
            });
        }

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
            Intent gameIntent = new Intent(this, MemoryGame.class);
            gameIntent.putExtra("User", currentUser);
            startActivity(gameIntent);
        }else if(gameType.equals("nums")){
            Intent gameIntent = new Intent(this, SendaGame.class);
            gameIntent.putExtra("User", currentUser);
            startActivity(gameIntent);
        }else if(gameType.equals("laber")){
            Intent gameIntent = new Intent(this, LaberintendiGame.class);
            gameIntent.putExtra("User", currentUser);
            startActivity(gameIntent);
        }else if(gameType.equals("whack")){
            Intent gameIntent = new Intent(this, WhackaGameActivity.class);
            gameIntent.putExtra("User", currentUser);
            startActivity(gameIntent);
        }else if(gameType.equals("piano")){
            Intent gameIntent = new Intent(this, PianoGame.class);
            gameIntent.putExtra("User", currentUser);
            startActivity(gameIntent);
        }
        finish();
    }
}