package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class PianoGame extends AppCompatActivity {

    Button doKey, reKey, miKey, faKey, solKey, laKey, siKey;
    Button doSh, reSh, faSh, solSh, laSh;

    TextView scoreLbl, pianoNote;

    Button[] notesArray;

    PianoManager pianoManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano_game);

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

        notesArray = new Button[12];
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

        changeKeyState(false);

        gameStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doSound.release();
        reSound.release();
        miSound.release();
        faSound.release();
        solSound.release();
        laSound.release();
        siSound.release();
        doSharpSound.release();
        reSharpSound.release();
        faSharpSound.release();
        solSharpSound.release();
        laSharpSound.release();
    }

    public void changeKeyState(Boolean state) {
        for (int i = 0; i < notesArray.length; i++) {
            notesArray[i].setEnabled(state);
        }
    }

    private void colorAndPlay(int delay, Button button) {
        ObjectAnimator animator;
        final MediaPlayer player;
        if (button == doKey){
            player = doSound;
            animator = ObjectAnimator.ofObject(doKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    doKey.getBackgroundTintList().getDefaultColor(),
                    0xFFF94144);
            pianoNote.setText("Do");
        } else if (button == reKey) {
            player = reSound;
            animator = ObjectAnimator.ofObject(reKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    reKey.getBackgroundTintList().getDefaultColor(),
                    0xFFF8961E);
            pianoNote.setText("Re");
        }else if (button == miKey) {
            player = miSound;
            animator = ObjectAnimator.ofObject(miKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    miKey.getBackgroundTintList().getDefaultColor(),
                    0xFFF9C74F);
            pianoNote.setText("Mi");
        }else if (button == faKey) {
            player = faSound;
            animator = ObjectAnimator.ofObject(faKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    faKey.getBackgroundTintList().getDefaultColor(),
                    0xFF90BE6D);
            pianoNote.setText("Fa");
        }else if (button == solKey) {
            player = solSound;
            animator = ObjectAnimator.ofObject(solKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    solKey.getBackgroundTintList().getDefaultColor(),
                    0xFF43AA8B);
            pianoNote.setText("Sol");
        }else if (button == laKey) {
            player = laSound;
            animator = ObjectAnimator.ofObject(laKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    laKey.getBackgroundTintList().getDefaultColor(),
                    0xFF577590);
            pianoNote.setText("La");
        }else if (button == siKey){
            player = siSound;
            animator = ObjectAnimator.ofObject(siKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    siKey.getBackgroundTintList().getDefaultColor(),
                    0xFF603982);
            pianoNote.setText("Si");
        }else if (button == doSh){
            player = doSharpSound;
            animator = ObjectAnimator.ofObject(doSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    doSh.getBackgroundTintList().getDefaultColor(),
                    0xFFf78f91);
            pianoNote.setText("Do#");
        }else if (button == reSh){
            player = reSharpSound;
            animator = ObjectAnimator.ofObject(reSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    reSh.getBackgroundTintList().getDefaultColor(),
                    0xFFf7db97);
            pianoNote.setText("Re#");
        }else if (button == faSh){
            player = faSharpSound;
            animator = ObjectAnimator.ofObject(faSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    faSh.getBackgroundTintList().getDefaultColor(),
                    0xFFaabd9d);
            pianoNote.setText("Fa#");
        }else if (button == solSh){
            player = solSharpSound;
            animator = ObjectAnimator.ofObject(solSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    solSh.getBackgroundTintList().getDefaultColor(),
                    0xFF7c8791);
            pianoNote.setText("Sol#");
        }else{
            player = laSharpSound;
            animator = ObjectAnimator.ofObject(laSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    laSh.getBackgroundTintList().getDefaultColor(),
                    0xFF806d91);
            pianoNote.setText("La#");
        }

        animator.setDuration(400);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setStartDelay(delay);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if(player.isPlaying()){
                    player.stop();
                    try {
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                player.start();
            }
        });
        animator.start();
    }

    public void onTap(View v){
        System.out.println("Compare");
        Button tapButton = (Button)v;
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

        System.out.println("Check");

        if(answer == -1){
            scoreLbl.setText(String.valueOf(pianoManager.getScore()));
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
}