package com.example.intendi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class WhackaGameActivity extends AppCompatActivity {
    clickableDolphin[] myDolphins;
    View[] clickableViews;
    TextView colorText;
    TextView timerText;
    TextView scoreText;
    TextView finalScoreText;
    long initTime = 30000;
    long bonus = 1500;
    long interval = 100;
    long timeCurrent;
    static CountDownTimer timer;

    public static int NUMBER_OF_DOLPHINS = 7;
    WhackaGameManager GameManager;
    clickableDolphin dlp1,dlp2,dlp3,dlp4,dlp5,dlp6,dlp7;

    FloatingActionButton go_back;
    CardView cardOkPopUp;
    CardView cardCancelPopUp;
    View go_screen;
    View close_screen;
    View pause_background;
    Button goBackButton;
    User currentUser;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (User)getIntent().getSerializableExtra("User");
        dbHandler = dbHandler.getInstance(getApplicationContext());
        setContentView(R.layout.activity_whacka_game);
        go_screen = findViewById(R.id.GO_super_screen);
        close_screen = findViewById(R.id.close_screen);
        cardOkPopUp = findViewById(R.id.cardViewOk);
        cardCancelPopUp = findViewById(R.id.cardViewCancel);
        goBackButton = findViewById(R.id.goMenuButton);
        pause_background = findViewById(R.id.pause_background);
        go_back = findViewById(R.id.closeButton);



        GameManager = new WhackaGameManager();
        timeCurrent = 0;
        myDolphins = new clickableDolphin[NUMBER_OF_DOLPHINS];
        clickableViews= new View[NUMBER_OF_DOLPHINS];

        finalScoreText = go_screen.findViewById(R.id.FinalScoreTxt);
        colorText = findViewById(R.id.ColorText);
        scoreText = findViewById(R.id.scoreLbl);
        scoreText.setText("0");
        timerText = findViewById(R.id.timeLbl);
        timerText.setText("00 : 00");

        clickableViews[0] = (ConstraintLayout)findViewById(R.id.D1);
        clickableViews[1] = (ConstraintLayout)findViewById(R.id.D2);
        clickableViews[2] = (ConstraintLayout)findViewById(R.id.D3);
        clickableViews[3] = (ConstraintLayout)findViewById(R.id.D4);
        clickableViews[4] = (ConstraintLayout)findViewById(R.id.D5);
        clickableViews[5] = (ConstraintLayout)findViewById(R.id.D6);
        clickableViews[6] = (ConstraintLayout)findViewById(R.id.D7);

        myDolphins[0] = dlp1;
        myDolphins[1] = dlp2;
        myDolphins[2] = dlp3;
        myDolphins[3] = dlp4;
        myDolphins[4] = dlp5;
        myDolphins[5] = dlp6;
        myDolphins[6] = dlp7;

        timer = new CountDownTimer(initTime, interval) {
            @Override
            public void onTick(long l) {
                long seconds = (l/1000) % 60;
                long minutes = (l/1000) / 60;
                String mS =  Long.toString(minutes);
                String sS = Long.toString(seconds);

                if (minutes<10){
                    mS = "0" + Long.toString(minutes);
                }
                if (seconds < 10)
                {
                    sS = "0" + Long.toString(seconds);
                }
                timeCurrent+=interval;
                initTime-=interval;
                timerText.setText(mS + " : " + sS);
                if(timeCurrent > 20000){
                    if(timeCurrent < 35000){
                        bonus = 1000;
                    }else if(timeCurrent < 45000){
                        bonus = 500;
                    }else{
                        bonus = 200;
                    }
                }
            }

            @Override
            public void onFinish() {
                finalScoreText.setText(Integer.toString(GameManager.getScore()));
                timerText.setText("00 : 00");
                finishDolphins();
                java.util.Date today = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String gameDate = df.format(today);
                dbHandler.addResult(currentUser.getUser_id(), "Whack-A-Ball", GameManager.getScore(), gameDate);
                go_screen.setVisibility(View.VISIBLE);
            }
        }.start();

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pause_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
                go_back.setVisibility(View.VISIBLE);
            }
        });

        cardOkPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardCancelPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
                go_back.setVisibility(View.VISIBLE);
                //enableClicks();
            }
        });

        initDolphins();
        Update();
        changeTextColor();
    }

    private void changeTextColor(){
        String newHex = GameManager.getRandomHex();;
        for(int i = 0; i<myDolphins.length;i++){
            if(myDolphins[i].getColor() != GameManager.getCurrentColor()){
                for(int j = 0; j<WhackaGameManager.allColors.length; j++){
                    if((myDolphins[i].getColor() == WhackaGameManager.allColors[j])){
                        newHex = WhackaGameManager.allHex[j];
                        colorText.setTextColor(Color.parseColor(newHex));
                        return;
                    }
                }
            }
        }
        colorText.setTextColor(Color.parseColor(newHex));
    }


    private String availableColors(){
        String newColor;
        int newIndex1 = GameManager.getRandomIndex();
        newColor = myDolphins[newIndex1].getColor();

        return newColor;
    };

    public int getAmountWithColor(String color){
        int amount = 0;
        for(int i = 0; i<myDolphins.length;i++){
            if(myDolphins[i].getColor() == color){
                amount++;
            }
        }
        return amount;
    };

    private void finishDolphins(){
        for(int i = 0; i<clickableViews.length;i++){
            int finalI = i;
            clickableViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private void initDolphins(){
        for(int i = 0; i<myDolphins.length;i++){
            int newIndex = GameManager.getRandomIndex();
            if(i == myDolphins.length-1){
                if(newIndex == WhackaGameManager.allColors.length){
                    newIndex--;
                }
                if(newIndex == 0){
                    newIndex++;
                }
            }
            myDolphins[i] = new clickableDolphin( WhackaGameManager.allColors[newIndex],clickableViews[i]);
            myDolphins[i].setBall(WhackaGameManager.allBalls[newIndex],WhackaGameManager.allColors[newIndex]);
        }

        for(int i = 0; i<clickableViews.length;i++){
            int finalI = i;
            clickableViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(GameManager.getCurrentColor().equals(myDolphins[finalI].getColor())){
                        timer.cancel();
                        initTime+=bonus;
                        timer = new CountDownTimer(initTime, interval) {
                            @Override
                            public void onTick(long l) {
                                long seconds = (l/1000) % 60;
                                long minutes = (l/1000) / 60;
                                String mS =  Long.toString(minutes);
                                String sS = Long.toString(seconds);

                                if (minutes<10){
                                    mS = "0" + Long.toString(minutes);
                                }
                                if (seconds < 10)
                                {
                                    sS = "0" + Long.toString(seconds);
                                }
                                timeCurrent+=interval;
                                initTime-=interval;
                                timerText.setText(mS + " : " + sS);
                                if(timeCurrent > 20000){
                                    if(timeCurrent < 35000){
                                        bonus = 1000;
                                    }else if(timeCurrent < 45000){
                                        bonus = 500;
                                    }else{
                                        bonus = 200;
                                    }
                                }
                            }

                            @Override
                            public void onFinish() {
                                finalScoreText.setText(Integer.toString(GameManager.getScore()));
                                timerText.setText("00 : 00");
                                finishDolphins();
                                java.util.Date today = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String gameDate = df.format(today);
                                dbHandler.addResult(currentUser.getUser_id(), "Whack-A-Ball", GameManager.getScore(), gameDate);
                                go_screen.setVisibility(View.VISIBLE);
                            }
                        }.start();
                    }
                    GameManager.checkBall(myDolphins[finalI]);
                    scoreText.setText(Integer.toString(GameManager.getScore()));
                    if(GameManager.getBallAmountLeft() == 0 ){
                        Update();
                    }
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    void Update(){
        String initColor = availableColors();
        GameManager.prepareColors(initColor);
        GameManager.setBallAmountLeft(getAmountWithColor(initColor));
        GameManager.setCurrentColor(initColor);
        colorText.setText(GameManager.getCurrentColor());
        changeTextColor();
    }

    public void showClosePopUp(View v){
        close_screen.setVisibility(View.VISIBLE);
        v.setVisibility(View.INVISIBLE);
        //disableClicks();
    }
}

