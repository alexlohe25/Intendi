package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MemoryGame extends AppCompatActivity {
    DBHandler dbHandler;
    GridRecyclerView memoBoard;
    TextView timeLbl;
    TextView scoreLbl;
    int totalScore = 0, gridScore = 0;
    MemoryManager memoryGame = new MemoryManager();
    MemoryBoardAdapter boardAdapter;
    CountDownTimer timer;
    MediaPlayer cardSound;
    long initTime = 90000, interval = 100;
    LayoutAnimationController layoutAnimationController;
    User currentUser;
    public View go_screen;

    CardView cardOkPopUp;
    CardView cardCancelPopUp;
    Button goMenuButton;
    View close_screen;
    View pause_background;

    View help_screen;
    TextView helpText,finalScoreText;
    View help_background;
    Button okHelpButton;
    boolean isTimerFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (User)getIntent().getSerializableExtra("User");
        dbHandler = dbHandler.getInstance(getApplicationContext());
        setContentView(R.layout.activity_memory_game);
        finalScoreText = findViewById(R.id.FinalScoreTxt);
        go_screen = findViewById(R.id.GO_super_screen);
        goMenuButton = findViewById(R.id.goMenuButton);
        pause_background = findViewById(R.id.pause_background);
        memoBoard = findViewById(R.id.memoBoard);
        timeLbl = findViewById(R.id.timeLbl);
        scoreLbl = findViewById(R.id.scoreLbl);
        cardSound = MediaPlayer.create(this, R.raw.bird);
        isTimerFinished = true;
        //set 3 minute timer and then show game over ppop up
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
            //once its finished
            @Override
            public void onFinish() {
                timeLbl.setText("00 : 00");
                //if current board has less than 4 pairs found
                if(memoryGame.numPairsFound < 40){
                    totalScore += memoryGame.numPairsFound;
                }
                //if game is over by natural timer finishing
                if(isTimerFinished){
                    //get game date
                    java.util.Date today = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String gameDate = df.format(today);
                    //add result to database
                    dbHandler.addResult(currentUser.getUser_id(), "Memorama", totalScore, gameDate);
                    finalScoreText.setText(Integer.toString(totalScore));
                    go_screen.setVisibility(View.VISIBLE);
                }
            }
        }.start();
        //set new 4x2 with random cards
        boardAdapter = new MemoryBoardAdapter(this, 8, memoryGame.cards, new MemoryBoardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(int position) {
                updateGameWithFlip(position);
            }
        });
        memoBoard.setAdapter(boardAdapter);
        memoBoard.setHasFixedSize(true);
        //set fade in animation to board
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.recyclerview_grid_layout_animation);
        memoBoard.setLayoutAnimation(layoutAnimationController);
        memoBoard.setLayoutManager(new GridLayoutManager(this, 2));
        memoBoard.startLayoutAnimation();

        close_screen = findViewById(R.id.close_screen);
        cardOkPopUp = findViewById(R.id.cardViewOk);
        cardCancelPopUp = findViewById(R.id.cardViewCancel);

        help_screen = findViewById(R.id.help_screen);
        helpText = findViewById(R.id.help_text);
        help_background = findViewById(R.id.help_background);
        okHelpButton = findViewById(R.id.okHelpButton);

        //Actions of pop up
        cardOkPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTimerFinished = false;
                timer.onFinish();
                finish();
            }
        });

        pause_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
            }
        });

        helpText.setText("Encuentra las parejas de sonido e imagen en el memorama, dando click a las tarjetas");
        //help pop up functions
        help_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help_screen.setVisibility(View.INVISIBLE);
            }
        });

        okHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help_screen.setVisibility(View.INVISIBLE);
            }
        });

        cardCancelPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
            }
        });

        goMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    //release media player once activity is finished
    @Override
    public void onDestroy(){
        super.onDestroy();
        cardSound.release();
    }
    //function to set a brand new memory board
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

    public void showClosePopUp(View v){
        close_screen.setVisibility(View.VISIBLE);
    }

    public void showHelpPopUp(View v){
        help_screen.setVisibility(View.VISIBLE);
    }
    private void updateGameWithFlip(int position){
        //if media player is not playing and current card is not matched
        if (!memoryGame.cards.get(position).isMatched && !cardSound.isPlaying()) {
            //flip current card
            memoryGame.flipCard(position);
            //if current card is a sound card and its face up, play sound in media player
            if (memoryGame.cards.get(position).isSound && memoryGame.cards.get(position).isFaceUp) {
                cardSound = memoryGame.setSound(this, position);
                cardSound.start();
            }
        }
        //local MemoryBoard score equals to the number of pairs found
        gridScore = memoryGame.numPairsFound;
        scoreLbl.setText(Integer.toString(totalScore + gridScore));
        //all pair in current board were found
        if (gridScore == 40){
            //add board score to total score
            totalScore += gridScore;
            //show a new board afer .5 seconds
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

    @Override
    public void onBackPressed() {
        if(go_screen.getVisibility() != View.VISIBLE){
            if (close_screen.getVisibility() == View.VISIBLE ){
                close_screen.setVisibility(View.INVISIBLE);
            }else if(help_screen.getVisibility() == View.VISIBLE){
                help_screen.setVisibility(View.INVISIBLE);
            }else{
                close_screen.setVisibility(View.VISIBLE);
            }
        }
    }
}