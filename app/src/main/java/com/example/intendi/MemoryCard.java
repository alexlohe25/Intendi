package com.example.intendi;
//class for individual card in Memory Game
public class MemoryCard {
    int identifier;
    int contentIndex;
    boolean isFaceUp = false;
    boolean isMatched = false;
    boolean isSound = false;
    public MemoryCard(Integer id) {
        identifier = id;
    }
    public MemoryCard(Integer id, Integer listIndex, boolean sound) {
        identifier = id;
        contentIndex = listIndex;
        isSound = sound;
    }
}
