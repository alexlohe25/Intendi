package com.example.intendi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class WhackaGameActivity extends AppCompatActivity {
    public clickableDolphin[] myDolphins;
    public View[] clickableViews;
    public TextView colorText;
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
                    if(GameManager.getBallAmountLeft() == 0 ){
                        Update();
                    }
                }
            });
        }
    }

    void Update(){
        String initColor = availableColors();
        GameManager.prepareColors(initColor);
        GameManager.setBallAmountLeft(getAmountWithColor(initColor));
        GameManager.setCurrentColor(initColor);
        colorText.setText(GameManager.getCurrentColor());
    }

}

