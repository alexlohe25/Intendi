package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOException;

public class PianoFree extends AppCompatActivity {

    View doKey, reKey, miKey, faKey, solKey, laKey, siKey;
    View doSh, reSh, faSh, solSh, laSh;

    TextView pianoNote;

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

    private void colorAndPlay(int delay, View view) {
        ObjectAnimator animator;
        final MediaPlayer player;
        if (view == doKey){
            player = doSound;
            animator = ObjectAnimator.ofObject(doKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    doKey.getBackgroundTintList().getDefaultColor(),
                    0x9400D3);
            pianoNote.setText("Do");
        } else if (view == reKey) {
            player = reSound;
            animator = ObjectAnimator.ofObject(reKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    reKey.getBackgroundTintList().getDefaultColor(),
                    0x4B0082);
            pianoNote.setText("Re");
        }else if (view == miKey) {
            player = miSound;
            animator = ObjectAnimator.ofObject(miKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    miKey.getBackgroundTintList().getDefaultColor(),
                    0x0000FF);
            pianoNote.setText("Mi");
        }else if (view == faKey) {
            player = faSound;
            animator = ObjectAnimator.ofObject(faKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    faKey.getBackgroundTintList().getDefaultColor(),
                    0x00FF00);
            pianoNote.setText("Fa");
        }else if (view == solKey) {
            player = solSound;
            animator = ObjectAnimator.ofObject(solKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    solKey.getBackgroundTintList().getDefaultColor(),
                    0xFFFF00);
            pianoNote.setText("Sol");
        }else if (view == laKey) {
            player = laSound;
            animator = ObjectAnimator.ofObject(laKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    laKey.getBackgroundTintList().getDefaultColor(),
                    0xFF7F00);
            pianoNote.setText("La");
        }else if (view == siKey){
            player = siSound;
            animator = ObjectAnimator.ofObject(siKey,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    siKey.getBackgroundTintList().getDefaultColor(),
                    0xFF0000);
            pianoNote.setText("Si");
        }else if (view == doSh){
            player = doSharpSound;
            animator = ObjectAnimator.ofObject(doSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    doSh.getBackgroundTintList().getDefaultColor(),
                    0xFFFFFF);
            pianoNote.setText("Do#");
        }else if (view == reSh){
            player = reSharpSound;
            animator = ObjectAnimator.ofObject(reSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    reSh.getBackgroundTintList().getDefaultColor(),
                    0xFFFFFF);
            pianoNote.setText("Re#");
        }else if (view == faSh){
            player = faSharpSound;
            animator = ObjectAnimator.ofObject(faSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    faSh.getBackgroundTintList().getDefaultColor(),
                    0xFFFFFF);
            pianoNote.setText("Fa#");
        }else if (view == solSh){
            player = solSharpSound;
            animator = ObjectAnimator.ofObject(solSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    solSh.getBackgroundTintList().getDefaultColor(),
                    0xFFFFFF);
            pianoNote.setText("Sol#");
        }else{
            player = laSharpSound;
            animator = ObjectAnimator.ofObject(laSh,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    laSh.getBackgroundTintList().getDefaultColor(),
                    0xFFFFFF);
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
        View tappedView = (View)v;
        colorAndPlay(0, tappedView);
    }
}