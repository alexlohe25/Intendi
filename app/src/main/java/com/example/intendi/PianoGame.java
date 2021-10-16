package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Sampler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PianoGame extends AppCompatActivity {

    CardView doKey, reKey, miKey, faKey, solKey, laKey, siKey;
    CardView doSh, reSh, faSh, solSh, laSh;

    TextView scoreLbl, pianoNote;
    ImageView corcheaOne, corcheaTwo, corcheaThree;

    CardView[] notesArray;
    MediaPlayer[] noteSoundArray;

    PianoManager pianoManager;

    View go_screen;
    Button go_button;
    TextView finalScore;

    CardView cardOkPopUp;
    CardView cardCancelPopUp;
    View close_screen;
    View pause_background;
    DBHandler dbHandler;
    User currentUser;
    int answer;
    boolean isPlaying;

    private MediaPlayer doSound;
    private MediaPlayer reSound;
    private MediaPlayer miSound;
    private MediaPlayer faSound;
    private MediaPlayer solSound;
    private MediaPlayer laSound;
    private MediaPlayer siSound;
    private MediaPlayer doSharpSound;
    private MediaPlayer reSharpSound;
    private MediaPlayer faSharpSound;
    private MediaPlayer solSharpSound;
    private MediaPlayer laSharpSound;

    View help_screen;
    TextView helpText;
    View help_background;
    Button okHelpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano_game);

        dbHandler = dbHandler.getInstance(getApplicationContext());
        currentUser = (User)getIntent().getSerializableExtra("User");
        doKey = findViewById(R.id.doNote);
        reKey = findViewById(R.id.reNote);
        miKey = findViewById(R.id.miNote);
        faKey = findViewById(R.id.faNote);
        solKey = findViewById(R.id.solNote);
        laKey = findViewById(R.id.laNote);
        siKey = findViewById(R.id.siNote);
        doSh = findViewById(R.id.doSharp);
        reSh = findViewById(R.id.reSharp);
        faSh = findViewById(R.id.faSharp);
        solSh = findViewById(R.id.solSharp);
        laSh = findViewById(R.id.laSharp);

        scoreLbl = findViewById(R.id.scoreLbl);
        pianoNote = findViewById(R.id.pianoNote);
        corcheaOne = findViewById(R.id.CorcheaOne);
        corcheaTwo = findViewById(R.id.CorcheaTwo);
        corcheaThree = findViewById(R.id.CorcheaThree);
        cardOkPopUp = findViewById(R.id.cardViewOk);
        cardCancelPopUp= findViewById(R.id.cardViewCancel);
        close_screen = findViewById(R.id.close_screen);
        pause_background = findViewById(R.id.pause_background);

        help_screen = findViewById(R.id.help_screen);
        helpText = findViewById(R.id.help_text);
        help_background = findViewById(R.id.help_background);
        okHelpButton = findViewById(R.id.okHelpButton);

        cardCancelPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
            }
        });

        pause_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_screen.setVisibility(View.INVISIBLE);
            }
        });

        helpText.setText("Observa el patrón de teclas iluminadas y da click en aquellas que fueron coloreadas según la melodía entonada");

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

        cardOkPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        go_screen = findViewById(R.id.GO_super_screen);
        go_button = findViewById(R.id.goMenuButton);
        finalScore = findViewById(R.id.FinalScoreTxt);


        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        isPlaying = false;

        doSound = MediaPlayer.create(this, R.raw.don);
        reSound = MediaPlayer.create(this, R.raw.re);
        miSound = MediaPlayer.create(this, R.raw.mi);
        faSound = MediaPlayer.create(this, R.raw.fa);
        solSound = MediaPlayer.create(this, R.raw.sol);
        laSound = MediaPlayer.create(this, R.raw.la);
        siSound = MediaPlayer.create(this, R.raw.si);
        doSharpSound = MediaPlayer.create(this, R.raw.dob);
        reSharpSound = MediaPlayer.create(this, R.raw.reb);
        faSharpSound = MediaPlayer.create(this, R.raw.fab);
        solSharpSound = MediaPlayer.create(this, R.raw.solb);
        laSharpSound = MediaPlayer.create(this, R.raw.lab);

        notesArray = new CardView[12];
        notesArray[0] = doKey;
        notesArray[1] = reKey;
        notesArray[2] = miKey;
        notesArray[3] = faKey;
        notesArray[4] = solKey;
        notesArray[5] = laKey;
        notesArray[6] = siKey;
        notesArray[7] = doSh;
        notesArray[8] = reSh;
        notesArray[9] = faSh;
        notesArray[10] = solSh;
        notesArray[11] = laSh;

        noteSoundArray = new MediaPlayer[12];
        noteSoundArray[0] = doSound;
        noteSoundArray[1] = reSound;
        noteSoundArray[2] = miSound;
        noteSoundArray[3] = faSound;
        noteSoundArray[4] = solSound;
        noteSoundArray[5] = laSound;
        noteSoundArray[6] = siSound;
        noteSoundArray[7] = doSharpSound;
        noteSoundArray[8] = reSharpSound;
        noteSoundArray[9] = faSharpSound;
        noteSoundArray[10] = solSharpSound;
        noteSoundArray[11] = laSharpSound;

        changeKeyState(false);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameStart();
            }
        }, 1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(int i = 0; i < 12; i++) {
            noteSoundArray[i].reset();
        }
    }

    public void changeKeyState(Boolean state) {
        for (int i = 0; i < notesArray.length; i++) {
            notesArray[i].setEnabled(state);
        }
    }

    public void showHelpPopUp(View v){
        help_screen.setVisibility(View.VISIBLE);
    }

    private void colorAndPlay(int delay, CardView button) {

        final Handler handler = new Handler(Looper.getMainLooper());
        int indexSound;

        ObjectAnimator animator;
        final MediaPlayer player;
        if (button == doKey){
            indexSound = 0;
            animator = ObjectAnimator.ofObject(doKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    doKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFF94144);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Do");
                }
            }, delay);
        } else if (button == reKey) {
            indexSound = 1;
            animator = ObjectAnimator.ofObject(reKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    reKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFF8961E);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Re");
                }
            }, delay);
        }else if (button == miKey) {
            indexSound = 2;
            animator = ObjectAnimator.ofObject(miKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    miKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFF9C74F);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Mi");
                }
            }, delay);
        }else if (button == faKey) {
            indexSound = 3;
            animator = ObjectAnimator.ofObject(faKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    faKey.getCardBackgroundColor().getDefaultColor(),
                    0xFF90BE6D);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Fa");
                }
            }, delay);
        }else if (button == solKey) {
            indexSound = 4;
            animator = ObjectAnimator.ofObject(solKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    solKey.getCardBackgroundColor().getDefaultColor(),
                    0xFF43AA8B);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Sol");
                }
            }, delay);
        }else if (button == laKey) {
            indexSound = 5;
            animator = ObjectAnimator.ofObject(laKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    laKey.getCardBackgroundColor().getDefaultColor(),
                    0xFF577590);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("La");
                }
            }, delay);
        }else if (button == siKey){
            indexSound = 6;
            animator = ObjectAnimator.ofObject(siKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    siKey.getCardBackgroundColor().getDefaultColor(),
                    0xFF603982);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Si");
                }
            }, delay);
        }else if (button == doSh){
            indexSound = 7;
            animator = ObjectAnimator.ofObject(doSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    doSh.getCardBackgroundColor().getDefaultColor(),
                    0xFFf78f91);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Do#");
                }
            }, delay);
        }else if (button == reSh){
            indexSound = 8;
            animator = ObjectAnimator.ofObject(reSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    reSh.getCardBackgroundColor().getDefaultColor(),
                    0xFFf7db97);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Re#");
                }
            }, delay);
        }else if (button == faSh){
            indexSound = 9;
            animator = ObjectAnimator.ofObject(faSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    faSh.getCardBackgroundColor().getDefaultColor(),
                    0xFFaabd9d);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Fa#");
                }
            }, delay);
        }else if (button == solSh){
            indexSound = 10;
            animator = ObjectAnimator.ofObject(solSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    solSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF7c8791);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("Sol#");
                }
            }, delay);
        }else{
            indexSound = 11;
            animator = ObjectAnimator.ofObject(laSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    laSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF806d91);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pianoNote.setText("La#");
                }
            }, delay);
        }

        animator.setDuration(400);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setStartDelay(delay);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if(noteSoundArray[indexSound].isPlaying()){
                    noteSoundArray[indexSound].stop();
                    try {
                        noteSoundArray[indexSound].prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                noteSoundArray[indexSound].start();
            }
        });
        animator.start();
    }

    public void onTap(View v){
        CardView tapButton = (CardView) v;
        colorAndPlay(0, tapButton);

        if(tapButton == doKey) {
            answer = pianoManager.compare(0);
        }else if(tapButton == reKey) {
            answer = pianoManager.compare(1);
        }else if(tapButton == miKey) {
            answer = pianoManager.compare(2);
        }else if(tapButton == faKey) {
            answer = pianoManager.compare(3);
        }else if(tapButton == solKey) {
            answer = pianoManager.compare(4);
        }else if(tapButton == laKey) {
            answer = pianoManager.compare(5);
        }else if(tapButton == siKey) {
            answer = pianoManager.compare(6);
        }else if(tapButton == doSh) {
            answer = pianoManager.compare(7);
        }else if(tapButton == reSh) {
            answer = pianoManager.compare(8);
        }else if(tapButton == faSh) {
            answer = pianoManager.compare(9);
        }else if(tapButton == solSh) {
            answer = pianoManager.compare(10);
        }else{
            answer = pianoManager.compare(11);
        }

        if(answer == -2){
            scoreLbl.setText(String.valueOf(pianoManager.getScore()));
            changeTries(pianoManager.getTries());
            changeKeyState(false);
            finalScore.setText(String.valueOf(pianoManager.getScore()));
            java.util.Date today = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String gameDate = df.format(today);
            dbHandler.addResult(currentUser.getUser_id(), "Piano", pianoManager.getScore(), gameDate);
            go_screen.setVisibility(View.VISIBLE);

        }else if(answer == -1){
            scoreLbl.setText(String.valueOf(pianoManager.getScore()));
            changeTries(pianoManager.getTries());
            changeKeyState(false);

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInstructions();
                }
            }, 1000);

        }else if(answer == 1){
            pianoManager.changeScore();
            scoreLbl.setText(String.valueOf(pianoManager.getScore()));
            pianoManager.changeRound();

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInstructions();
                }
            }, 1000);
        }
    }

    public void showInstructions() {
        changeKeyState(false);

        int[] instruction = pianoManager.getInstructions();
        for(int i = 0; i < pianoManager.getRound() + 2; i++){
            colorAndPlay(1000 * i, notesArray[instruction[i]]);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeKeyState(true);
            }
        }, pianoManager.getRound() + 3 * 1000);
    }

    public void changeTries(int tries){
        if(tries == 2){
            corcheaThree.setImageResource(R.drawable.corchea_g);
        }else if(tries == 1){
            corcheaTwo.setImageResource(R.drawable.corchea_g);
        }else{
            corcheaOne.setImageResource(R.drawable.corchea_g);
        }
    }

    public void gameStart() {
        pianoManager = new PianoManager();
        if(isPlaying){
            scoreLbl.setText(0);
            isPlaying  = false;
            changeKeyState(false);
        }else{
            showInstructions();
            isPlaying = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(go_screen.getVisibility() != View.VISIBLE){ //Si aún no se ha terminado el juego
            if (close_screen.getVisibility() == View.VISIBLE ){ //Pantalla de pausa mostrada
                close_screen.setVisibility(View.INVISIBLE); //Pantalla de pausa
            }else if(help_screen.getVisibility() == View.VISIBLE){ //Pop up help presionado
                help_screen.setVisibility(View.INVISIBLE); //Pantalla ayuda
            }else{ //Si no hay pop up mostrado
                close_screen.setVisibility(View.VISIBLE);
            }
        }
    }
}