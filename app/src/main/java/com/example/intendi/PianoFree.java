package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class PianoFree extends AppCompatActivity {

    CardView doKey, reKey, miKey, faKey, solKey, laKey, siKey;
    CardView doSh, reSh, faSh, solSh, laSh;

    TextView pianoNote;

    FloatingActionButton closeButton;

    private MediaPlayer doSound;
    private MediaPlayer reSound;
    private MediaPlayer miSound;
    private MediaPlayer faSound;
    private MediaPlayer solSound;
    private MediaPlayer laSound;
    private MediaPlayer siSound;
    private MediaPlayer doSharpSound;
    private MediaPlayer reSharpSound;
    private MediaPlayer solSharpSound;
    private MediaPlayer faSharpSound;
    private MediaPlayer laSharpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano_free);

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

        pianoNote = findViewById(R.id.pianoNote);
        closeButton = findViewById(R.id.returnButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
    }
    //OnDestroy to release de MediaPlayers after the user leaves the activity
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

    //Function that changes the color of the piano keys once they are pressed, the ifs depend on the key pressed. At the end the function uses an animator to return the color back to its original one
    private void colorAndPlay(int delay, CardView view) {
        ObjectAnimator animator;
        final MediaPlayer player;
        //Depending on the key pressed the function changes the key to the colors shown and cahnegs teh piano label to the key being pressed
        if (view == doKey){
            player = doSound;
            animator = ObjectAnimator.ofObject(doKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    doKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Do");
        } else if (view == reKey) {
            player = reSound;
            animator = ObjectAnimator.ofObject(reKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    reKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Re");
        }else if (view == miKey) {
            player = miSound;
            animator = ObjectAnimator.ofObject(miKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    miKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Mi");
        }else if (view == faKey) {
            player = faSound;
            animator = ObjectAnimator.ofObject(faKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    faKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Fa");
        }else if (view == solKey) {
            player = solSound;
            animator = ObjectAnimator.ofObject(solKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    solKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Sol");
        }else if (view == laKey) {
            player = laSound;
            animator = ObjectAnimator.ofObject(laKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    laKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("La");
        }else if (view == siKey){
            player = siSound;
            animator = ObjectAnimator.ofObject(siKey,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    siKey.getCardBackgroundColor().getDefaultColor(),
                    0xFFFFFFFF);
            pianoNote.setText("Si");
        }else if (view == doSh){
            player = doSharpSound;
            animator = ObjectAnimator.ofObject(doSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    doSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF000000);
            pianoNote.setText("Do#");
        }else if (view == reSh){
            player = reSharpSound;
            animator = ObjectAnimator.ofObject(reSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    reSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF000000);
            pianoNote.setText("Re#");
        }else if (view == faSh){
            player = faSharpSound;
            animator = ObjectAnimator.ofObject(faSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    faSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF000000);
            pianoNote.setText("Fa#");
        }else if (view == solSh){
            player = solSharpSound;
            animator = ObjectAnimator.ofObject(solSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    solSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF000000);
            pianoNote.setText("Sol#");
        }else{
            player = laSharpSound;
            animator = ObjectAnimator.ofObject(laSh,
                    "cardBackgroundColor",
                    new ArgbEvaluator(),
                    laSh.getCardBackgroundColor().getDefaultColor(),
                    0xFF000000);
            pianoNote.setText("La#");
        }

        //configuration of the animation and the start of the player
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

    //Function asign to each cardView representing the piano keys, once played it executes colorAndPlay
    public void onTap(View v){
        CardView tappedView = (CardView) v;
        colorAndPlay(0, tappedView);
    }
}