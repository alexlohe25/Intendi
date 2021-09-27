package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    List<Integer> card_icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);



        memoBoard = findViewById(R.id.memoBoard);
        timeLbl = findViewById(R.id.timeLbl);
        scoreLbl = findViewById(R.id.scoreLbl);



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
        boardAdapter.notifyDataSetChanged();
    }
}