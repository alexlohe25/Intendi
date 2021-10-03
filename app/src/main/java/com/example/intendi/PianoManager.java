package com.example.intendi;

import java.util.Random;

public class PianoManager {

    private int score;
    private int round;
    private int comparison;
    private int[] instructions;
    private Random randomizer;

    public PianoManager() {

        this.score = 0;
        this.round = 1;
        this.comparison = 0;
        this.instructions = new int[100];
        this.randomizer = new Random();

        instructions[0] = randomizer.nextInt(12);
        instructions[2] = randomizer.nextInt(12);
        instructions[3] = randomizer.nextInt(12);
    }

    public int getScore() {
        return this.score;
    }

    public int getRound() {
        return this.round;
    }

    public int[] getInstructions() {
        return this.instructions;
    }

    public void changeScore() {
        this.score += 100;
    }

    public void changeRound() {
        this.round++;
        this.comparison = 0;
        instructions[this.round + 1] = randomizer.nextInt(12);
    }

    public int compare(int answer) {
        if(answer == this.instructions[this.comparison]) {
            comparison++;
            if(this.comparison == this.round + 2) return 1;
            else return 0;
        }else return -1;
    }
}
