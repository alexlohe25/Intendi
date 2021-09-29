package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends AppCompatActivity {

    RecyclerView memoBoard;
    TextView timeLbl;
    TextView scoreLbl;
    MemoryManager memoryGame = new MemoryManager();
    MemoryBoardAdapter boardAdapter;
    CountDownTimer timer;
    long initTime = 120000, interval = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        memoBoard = findViewById(R.id.memoBoard);
        timeLbl = findViewById(R.id.timeLbl);
        scoreLbl = findViewById(R.id.scoreLbl);

        timer = new CountDownTimer(initTime,interval) {
            @Override
            public void onTick(long l) {
                long seconds = (l/1000) % 60;
                long minutes = (l/1000) / 60;
                String mS = Long.toString(minutes);
                String sS = Long.toString(seconds);

                if(minutes < 10){
                    mS = "0" + Long.toString(minutes);
                }
                if(seconds < 10){
                    sS = "0" + Long.toString(seconds);
                }
                initTime -= interval;
                timeLbl.setText(mS + " : " + sS);
            }

            @Override
            public void onFinish() {
                timeLbl.setText("00 : 00");
            }
        }.start();
        generaCartas();

    }
    public void generaCartas(){
        memoBoard.setLayoutManager(new GridLayoutManager(this, 2));
        boardAdapter = new MemoryBoardAdapter(this, 8, memoryGame.cards, new MemoryBoardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(int position) {
                updateGameWithFlip(position);
            }
        });
        memoBoard.setAdapter(boardAdapter);
        memoBoard.setHasFixedSize(true);
    }


    private void updateGameWithFlip(int position){
        memoryGame.flipCard(position);
        if (memoryGame.cards.get(position).isSound && !memoryGame.cards.get(position).isMatched){
            final MediaPlayer cardSound = memoryGame.setSound(this, position);
            cardSound.start();
        }
        scoreLbl.setText(Integer.toString(memoryGame.numPairsFound));
        boardAdapter.notifyDataSetChanged();
    }


}