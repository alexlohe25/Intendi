package com.example.intendi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    List<MemoryCard> cards;
    List<Integer> card_icons = new ArrayList<>();
    int numPairsFound = 0;
    private int indexOfSingleSelectedCard = -1;
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
        card_icons.add(R.drawable.autopixel);

        Collections.shuffle(card_icons);
        List<Integer> chosen_cards = card_icons.subList(0,4 );
        List<Integer> randomizedImages = (chosen_cards);
        randomizedImages.addAll(chosen_cards);
        Collections.shuffle(randomizedImages);
        cards = new ArrayList<>();
        for(int i = 0; i < randomizedImages.size(); i++)
            cards.add(new MemoryCard(randomizedImages.get(i)));

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
        if (cards.get(pos1).identifier == cards.get(pos2).identifier){
            cards.get(pos1).isMatched = true;
            cards.get(pos2).isMatched = true;
            numPairsFound ++;
            System.out.println("Pares encontrados: " + numPairsFound);
            return true;
        }
        return false;
    }

    private void restoreCards() {
        for(MemoryCard card : cards){
            if(!card.isMatched)
                card.isFaceUp = false;
        }
    }
}
