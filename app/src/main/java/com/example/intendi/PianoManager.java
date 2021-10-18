package com.example.intendi;

import java.util.Random;

public class PianoManager {

    private int score;
    private int round;
    private int comparison;

    //Array of random instructions that changes during the game
    private int[] instructions;
    private Random randomizer;
    private int tries;

    //Constructor the initializes the variables
    public PianoManager() {

        this.score = 0;
        this.round = 1;
        this.comparison = 0;
        this.instructions = new int[100];
        this.randomizer = new Random();
        this.tries = 3;

        //Three instructions as default
        instructions[0] = randomizer.nextInt(12);
        instructions[2] = randomizer.nextInt(12);
        instructions[3] = randomizer.nextInt(12);
    }

    //Getters of score, round, instructions, and tries
    public int getScore() {
        return this.score;
    }

    public int getRound() {
        return this.round;
    }

    public int[] getInstructions() {
        return this.instructions;
    }

    //Adds 100 to the score
    public void changeScore() {
        this.score += 100;
    }

    public int getTries() { return this.tries; }

    //Adds one to the round
    public void changeRound() {
        this.round++;
        this.comparison = 0;
        instructions[this.round + 1] = randomizer.nextInt(12);
    }

    //-1 -> Error, 0 -> Next answer, 1 -> RoundFinish, -2 -> Game Over
    public int compare(int answer) {
        //If the user answer is the same as the given instruction adn round havent ended
        if(answer == this.instructions[this.comparison]) {
            comparison++;
            //Round ended
            if(this.comparison == this.round + 2) return 1;
            else return 0;
        //User gets answer wrong, lives get deducted
        }else{
            this.tries--;
            //User has no more lives left
            if(this.tries == 0){
                this.comparison = 0;
                return -2;
            //User still has lives
            }else{
                this.comparison = 0;
                return -1;
            }
        }

    }
}
