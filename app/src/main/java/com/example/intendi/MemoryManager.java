package com.example.intendi;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    List<MemoryCard> cards = new ArrayList<>();;
    List<Integer> card_icons = new ArrayList<>();
    List<Integer> card_sounds = new ArrayList<>();
    int numPairsFound = 0;
    private int indexOfSingleSelectedCard = -1;
    protected MediaPlayer mp = null;

    MemoryManager(){
        card_icons.add(R.drawable.autopixel);
        card_icons.add(R.drawable.bike_pixe);
        card_icons.add(R.drawable.bird_pixel);
        card_icons.add(R.drawable.duck_pixel);
        card_icons.add(R.drawable.guitar_pixel);
        card_icons.add(R.drawable.lion_pixel);
        card_icons.add(R.drawable.piano_pixel);
        card_icons.add(R.drawable.pig_pixel);
        card_icons.add(R.drawable.plane_pixel);
        card_icons.add(R.drawable.snake_pixel);
        card_icons.add(R.drawable.tren_pixel);
        card_icons.add(R.drawable.trumpet_pixel);

        card_sounds.add(R.raw.car);
        card_sounds.add(R.raw.bicycle);
        card_sounds.add(R.raw.bird);
        card_sounds.add(R.raw.duck);
        card_sounds.add(R.raw.guitar);
        card_sounds.add(R.raw.lion);
        card_sounds.add(R.raw.piano);
        card_sounds.add(R.raw.pig);
        card_sounds.add(R.raw.plane);
        card_sounds.add(R.raw.snake);
        card_sounds.add(R.raw.train);
        card_sounds.add(R.raw.trumpet);
        shuffleCards();
    }
    public void shuffleCards(){
        cards.clear();
        List<Integer> iconIndexes = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11);
        Collections.shuffle(iconIndexes);
        for(int i = 0; i < 4; i++){
            int sortedIndex = iconIndexes.get(i);
            cards.add(new MemoryCard(card_icons.get(sortedIndex), sortedIndex , false));
            cards.add(new MemoryCard(card_sounds.get(sortedIndex), sortedIndex , true));
        }
        Collections.shuffle(cards);
    }

    public boolean flipCard(int position) {
        MemoryCard tappedCard = cards.get(position);
        boolean foundMatch = false;
        if(indexOfSingleSelectedCard == -1){
            restoreCards();
            indexOfSingleSelectedCard = position;
        }else{
            checkForMatch(indexOfSingleSelectedCard, position);
            indexOfSingleSelectedCard = -1;
        }
        tappedCard.isFaceUp = !tappedCard.isFaceUp;
        return foundMatch;
    }

    private boolean checkForMatch(int pos1, int pos2) {
        if ((cards.get(pos1).contentIndex == cards.get(pos2).contentIndex) && pos1 != pos2){
            if(cards.get(pos1).isMatched == false && cards.get(pos2).isMatched == false){
                cards.get(pos1).isMatched = true;
                cards.get(pos2).isMatched = true;
                numPairsFound += 10;
                return true;
            }
        }
        return false;
    }

    private void restoreCards() {
        for(MemoryCard card : cards){
            if(!card.isMatched)
                card.isFaceUp = false;
        }
    }
    public void resetCards(){
        cards.clear();
        shuffleCards();
    }

    public MediaPlayer setSound(MemoryGame memoryGame, int position) {
        if(this.mp != null){
            this.mp.stop();
            this.mp.release();
            this.mp = null;
        }
        return this.mp = MediaPlayer.create(memoryGame.getApplicationContext(),cards.get(position).identifier);
    }
}
