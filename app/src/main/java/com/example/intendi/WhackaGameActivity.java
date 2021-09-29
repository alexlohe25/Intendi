package com.example.intendi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;

public class WhackaGameActivity extends AppCompatActivity {
    public clickableDolphin[] myDolphins;
    public View[] clickableViews;
    public TextView colorText;
    public TextView timerText;
    public TextView scoreText;
    public long initTime = 30;
    static CountDownTimer timer;
    public static int NUMBER_OF_DOLPHINS = 7;
    public WhackaGameManager GameManager;
    //View D1,D2,D3,D4,D5,D6,D7;
    clickableDolphin dlp1,dlp2,dlp3,dlp4,dlp5,dlp6,dlp7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whacka_game);

        GameManager = new WhackaGameManager();

        myDolphins = new clickableDolphin[NUMBER_OF_DOLPHINS];
        clickableViews= new View[NUMBER_OF_DOLPHINS];

        colorText = findViewById(R.id.ColorText);
        scoreText = findViewById(R.id.scoreLbl);
        scoreText.setText("0");
        timerText = findViewById(R.id.timeLbl);
        timerText.setText("0");

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

        timer = new CountDownTimer(initTime*1000, 1000) {
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
                initTime--;
                timerText.setText(mS + " : " + sS);

            }

            @Override
            public void onFinish() {
                timerText.setText("0");
            }
        }.start();

        initDolphins();
        Update();
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
                    GameManager.checkBall(myDolphins[finalI]);
                    scoreText.setText(Integer.toString(GameManager.getScore()));
                    if(GameManager.getBallAmountLeft() == 0 ){
                        Update();
                    }
                    timer.cancel();
                    initTime+=1;
                    timer = new CountDownTimer(initTime*1000, 1000) {
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
                            initTime--;
                            timerText.setText(mS + " : " + sS);
                        }

                        @Override
                        public void onFinish() {
                            timerText.setText("0");
                        }
                    }.start();
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

    }

}

