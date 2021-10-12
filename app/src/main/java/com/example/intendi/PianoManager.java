package com.example.intendi;

import java.util.Random;

public class PianoManager {

    private int score;
    private int round;
    private int comparison;
    private int[] instructions;
    private Random randomizer;
    private int tries;

    public PianoManager() {

        this.score = 0;
        this.round = 1;
        this.comparison = 0;
        this.instructions = new int[100];
        this.randomizer = new Random();
        this.tries = 3;

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

    public int getTries() { return this.tries; }

    public void changeRound() {
        this.round++;
        this.comparison = 0;
        instructions[this.round + 1] = randomizer.nextInt(12);
    }

    //-1 -> error, 0 -> nextAns, 1 -> roundFinish, -2 -> Game Over
    public int compare(int answer) {
        if(answer == this.instructions[this.comparison]) {
            comparison++;
            if(this.comparison == this.round + 2) return 1;
            else return 0;
        }else{
            this.tries--;
            if(this.tries == 0){
                this.comparison = 0;
                return -2;
            }else{
                this.comparison = 0;
                return -1;
            }
        }

    }
}
