package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class MemoryGame extends AppCompatActivity {

    RecyclerView memoBoard;
    TextView timeLbl;
    TextView scoreLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        memoBoard = findViewById(R.id.memoBoard);
        timeLbl = findViewById(R.id.timeLbl);
        scoreLbl = findViewById(R.id.scoreLbl);

        memoBoard.setLayoutManager(new GridLayoutManager(this, 2));
        memoBoard.setAdapter(new MemoryBoardAdapter(this, 8));
        memoBoard.setHasFixedSize(true);

    }
}