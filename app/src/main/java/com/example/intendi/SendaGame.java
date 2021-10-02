package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SendaGame extends AppCompatActivity {

    RecyclerView sendaBoard;
    ImageView heartOne;
    ImageView heartTwo;
    ImageView heartThree;
    TextView scoreLbl;
    LayoutAnimationController layoutAnimationController;
    SendaManager sendaManager;

    FloatingActionButton go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senda_game);

        sendaManager = new SendaManager();

        sendaBoard = findViewById(R.id.sendaBoard);

        heartOne = findViewById(R.id.heartOneImg);
        heartTwo = findViewById(R.id.heartTwoImg);
        heartThree = findViewById(R.id.heartThreeImg);

        scoreLbl = findViewById(R.id.scoreLbl);

        sendaBoard.setAdapter(new SendaBoardAdapter(this, sendaManager, new SendaBoardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(int position) {
                int status = sendaManager.compareCurrentCell(position);
                if(status == -2){ //Game over
                    CardView card =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.cardView);
                    TextView text =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.textNumber);
                    turnError(0, card, text);
                    updateLifes(sendaManager.getLifes());

                    System.out.println("Game over");

                } else if(status == -1){ //Error, player continue on same round
                    CardView card =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.cardView);
                    TextView text =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.textNumber);
                    turnError(0, card, text);

                    updateLifes(sendaManager.getLifes());

                    final Handler handlerHide = new Handler(Looper.getMainLooper());
                    handlerHide.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            turnOffAll();
                        }
                    }, 700);

                    final Handler handlerShow = new Handler(Looper.getMainLooper());
                    handlerShow.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showPath();
                        }
                    }, 1000);

                }else if(status == 0){
                    CardView card =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.cardView);
                    TextView text =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.textNumber);
                    turnOn(0, card, text);

                    scoreLbl.setText(String.valueOf(sendaManager.getScore()));

                    final Handler handlerHide = new Handler(Looper.getMainLooper());
                    handlerHide.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            turnOffAll();
                        }
                    }, 500);

                    final Handler handlerShow = new Handler(Looper.getMainLooper());
                    handlerShow.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateNumsBoard();
                            showPath();
                        }
                    }, 1000);
                }else if(status == 1){
                    CardView card =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.cardView);
                    TextView text =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.textNumber);
                    turnOn(0, card, text);
                }else if(status == 2){
                    CardView card =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.cardView);
                    TextView text =  sendaBoard.getLayoutManager().getChildAt(position).findViewById(R.id.textNumber);
                    turnOn(0, card, text);

                    scoreLbl.setText(String.valueOf(sendaManager.getScore()));

                    final Handler handlerHide = new Handler(Looper.getMainLooper());
                    handlerHide.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            turnOffAll();
                        }
                    }, 500);

                    final Handler handlerHideGrid = new Handler(Looper.getMainLooper());
                    handlerHideGrid.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendaManager.expandBoard();
                            sendaBoard.setVisibility(View.INVISIBLE);
                            ((GridLayoutManager) sendaBoard.getLayoutManager()).setSpanCount(sendaManager.getBoardLen());
                            //sendaBoard.setLayoutManager(new GridLayoutManager(getApplicationContext(), sendaManager.getBoardLen()));
                        }
                    }, 1000);

                    final Handler handlerShowGrid = new Handler(Looper.getMainLooper());
                    handlerShowGrid.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendaBoard.setVisibility(View.VISIBLE);
                            sendaBoard.getAdapter().notifyDataSetChanged();
                            layoutAnimationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.recyclerview_grid_layout_animation);
                            sendaBoard.setLayoutAnimation(layoutAnimationController);
                        }
                    }, 1300);

                    final Handler handlerShow = new Handler(Looper.getMainLooper());
                    handlerShow.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateNumsBoard();
                            showPath();
                        }
                    }, 3000);


                }
            }
        }));
        sendaBoard.setHasFixedSize(true);

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.recyclerview_grid_layout_animation);
        sendaBoard.setLayoutAnimation(layoutAnimationController);

        sendaBoard.setLayoutManager(new GridLayoutManager(this, sendaManager.getBoardLen()));

        go_back = findViewById(R.id.closeButton);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Salir de juego");
            }
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showPath();
            }
        }, 2000);
    }

    private void turnOn(int delay, CardView card, TextView text){
        ObjectAnimator animatorColor;
        animatorColor = ObjectAnimator.ofObject(card,
                "cardBackgroundColor",
                new ArgbEvaluator(),
                card.getCardBackgroundColor().getDefaultColor(),
                0xFFFF7C03);
        animatorColor.setDuration(400);
        animatorColor.setStartDelay(delay);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(text, "alpha", text.getAlpha(), 1);
        fadeIn.setDuration(400);
        fadeIn.setStartDelay(delay);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorColor, fadeIn);
        animatorSet.start();
    }

    private void turnError(int delay, CardView card, TextView text){
        ObjectAnimator animatorColor;
        animatorColor = ObjectAnimator.ofObject(card,
                "cardBackgroundColor",
                new ArgbEvaluator(),
                card.getCardBackgroundColor().getDefaultColor(),
                0xFFFF0303);
        animatorColor.setDuration(400);
        animatorColor.setStartDelay(delay);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(text, "alpha", text.getAlpha(), 1);
        fadeIn.setDuration(400);
        fadeIn.setStartDelay(delay);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorColor, fadeIn);
        animatorSet.start();
    }

    private void turnOff(CardView card, TextView text){
        ObjectAnimator animatorColor;
        animatorColor = ObjectAnimator.ofObject(card,
                "cardBackgroundColor",
                new ArgbEvaluator(),
                card.getCardBackgroundColor().getDefaultColor(),
                0xFFC4C4C4);
        animatorColor.setDuration(400);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(text, "alpha", text.getAlpha(), 0);
        fadeOut.setDuration(400);
        fadeOut.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorColor, fadeOut);
        animatorSet.start();
    }

    public void disableClicks(){
        for(int i=0; i < sendaManager.getBoardSize(); i++){
            TextView text =  sendaBoard.getLayoutManager().getChildAt(i).findViewById(R.id.textNumber);
            text.setEnabled(false);
        }
    }

    public void enableClicks(){
        for(int i=0; i < sendaManager.getBoardSize(); i++){
            TextView text =  sendaBoard.getLayoutManager().getChildAt(i).findViewById(R.id.textNumber);
            text.setEnabled(true);
        }
    }

    public void showPath(){
        disableClicks();
        ArrayList<Integer> pattern =  sendaManager.getPattern();
        int del = 0;
        for(int i=0; i < sendaManager.getRound()+2; i++){
            CardView card =  sendaBoard.getLayoutManager().getChildAt(pattern.get(i)).findViewById(R.id.cardView);
            TextView text =  sendaBoard.getLayoutManager().getChildAt(pattern.get(i)).findViewById(R.id.textNumber);
            del = 1000 * i;
            turnOn(del, card, text);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableClicks();
                turnOffAll();
            }
        }, del+1000);
    }

    public void turnOffAll(){
        for(int i=0; i < sendaManager.getBoardSize(); i++){
            CardView card =  sendaBoard.getLayoutManager().getChildAt(i).findViewById(R.id.cardView);
            TextView text =  sendaBoard.getLayoutManager().getChildAt(i).findViewById(R.id.textNumber);
            turnOff(card, text);
        }
    }

    public void updateNumsBoard(){
        int[] pattern = sendaManager.getBoardNumbers();
        for(int i=0; i < sendaManager.getBoardSize(); i++){
            TextView textNumber =  sendaBoard.getLayoutManager().getChildAt(i).findViewById(R.id.textNumber);
            if(pattern[i] != -1) textNumber.setText(String.valueOf(pattern[i] + 1));
            else{
                textNumber.setText("");
            }
        }
    }

    public void updateLifes(int lifes){
        if(lifes == 2){
            heartThree.setImageResource(R.drawable.heart_gray);
        }else if(lifes == 1){
            heartTwo.setImageResource(R.drawable.heart_gray);
        }else{
            heartOne.setImageResource(R.drawable.heart_gray);
        }
    }

}