package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class MemoryGame extends AppCompatActivity {

    RecyclerView memoBoard;
    TextView timeLbl;
    TextView scoreLbl;
    int totalScore = 0, gridScore = 0;
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

        boardAdapter = new MemoryBoardAdapter(this, 8, memoryGame.cards, new MemoryBoardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(int position) {
                updateGameWithFlip(position);
            }
        });
        memoBoard.setLayoutManager(new GridLayoutManager(this, 2));
        memoBoard.setAdapter(boardAdapter);
        memoBoard.setHasFixedSize(true);
        memoBoard.getAdapter().notifyDataSetChanged();

    }
    public void generaCartas(){
        memoBoard.setVisibility(View.INVISIBLE);
        MemoryManager newMemoCards = new MemoryManager();
        boardAdapter = new MemoryBoardAdapter(this, 8, newMemoCards.cards, new MemoryBoardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(int position) {
                updateGameWithFlip(position);
            }
        });

        memoBoard.setAdapter(boardAdapter);
        memoBoard.setVisibility(View.VISIBLE);
        memoryGame = newMemoCards;
        memoBoard.getAdapter().notifyDataSetChanged();
    }


    private void updateGameWithFlip(int position){
        memoryGame.flipCard(position);
        if (memoryGame.cards.get(position).isSound && !memoryGame.cards.get(position).isMatched){
            final MediaPlayer cardSound = memoryGame.setSound(this, position);
            cardSound.start();
        }
        gridScore = memoryGame.numPairsFound;
        scoreLbl.setText(Integer.toString(totalScore + gridScore));
        if (gridScore == 40){
            totalScore += gridScore;
            generaCartas();
        }
        boardAdapter.notifyDataSetChanged();
    }


}