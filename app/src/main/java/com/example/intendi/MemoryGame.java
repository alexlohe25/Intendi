package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

public class MemoryGame extends AppCompatActivity {

    GridRecyclerView memoBoard;
    TextView timeLbl;
    TextView scoreLbl;
    int totalScore = 0, gridScore = 0;
    MemoryManager memoryGame = new MemoryManager();
    MemoryBoardAdapter boardAdapter;
    CountDownTimer timer;
    MediaPlayer cardSound;
    long initTime = 180000, interval = 100;
    LayoutAnimationController layoutAnimationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        memoBoard = findViewById(R.id.memoBoard);
        timeLbl = findViewById(R.id.timeLbl);
        scoreLbl = findViewById(R.id.scoreLbl);
        cardSound = MediaPlayer.create(this, R.raw.bird);
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
        memoBoard.setAdapter(boardAdapter);
        memoBoard.setHasFixedSize(true);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.recyclerview_grid_layout_animation);
        memoBoard.setLayoutAnimation(layoutAnimationController);
        memoBoard.setLayoutManager(new GridLayoutManager(this, 2));
        memoBoard.startLayoutAnimation();


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
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.recyclerview_grid_layout_animation);
        memoBoard.setLayoutAnimation(layoutAnimationController);
        memoryGame = newMemoCards;
        memoBoard.getAdapter().notifyDataSetChanged();
    }

    private void updateGameWithFlip(int position){
        if (!memoryGame.cards.get(position).isMatched && !cardSound.isPlaying()) {
            memoryGame.flipCard(position);
            if (memoryGame.cards.get(position).isSound && memoryGame.cards.get(position).isFaceUp) {
                cardSound = memoryGame.setSound(this, position);
                cardSound.start();
            }
        }
        gridScore = memoryGame.numPairsFound;
        scoreLbl.setText(Integer.toString(totalScore + gridScore));
        if (gridScore == 40){
            totalScore += gridScore;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generaCartas();
                }
            }, 500);
        }
        boardAdapter.notifyDataSetChanged();
    }
}