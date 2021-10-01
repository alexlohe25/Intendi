package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PianoGame extends AppCompatActivity {

    View doKey, reKey, miKey, faKey, solKey, laKey, siKey;
    View doSh, reSh, faSh, solSh, laSh;

    TextView scoreLbl, pianoNote;

    Button returnBotton;

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

        returnBotton = findViewById(R.id.returnButton);

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

        doKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSound.start();
            }
        });
        reKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSound.start();
            }
        });
        miKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miSound.start();
            }
        });
        faKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faSound.start();
            }
        });
        solKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solSound.start();
            }
        });
        laKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laSound.start();
            }
        });
        siKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siSound.start();
            }
        });
        doSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSharpSound.start();
            }
        });
        reSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSharpSound.start();
            }
        });
        faSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faSharpSound.start();
            }
        });
        solSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solSharpSound.start();
            }
        });
        laSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laSharpSound.start();
            }
        });
    }
}