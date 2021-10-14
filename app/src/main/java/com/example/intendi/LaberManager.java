package com.example.intendi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class LaberManager {

    private int labSize;
    private int round;
    private int score;
    private int energy;
    private int start;
    private int end;
    private int fishes;
    private int[] arrayImages;
    private String[] arrayColors;

    private Random rand;

    //Numbers inside cards in board
    private int[] labNumbers;
    private ArrayList<Integer> pattern;

    //Pattern of instructions given by the user
    private ArrayList<Integer> answerUser;

    public LaberManager(){
        this.labSize = 20;
        this.energy = 3;
        this.labNumbers = new int[20];
        this.rand = new Random();
        this.answerUser = new ArrayList<Integer>();
        this.round = 1;
        this.score = 0;
        this.pattern = new ArrayList<Integer>();

        this.arrayImages = new int[]{android.R.color.transparent, android.R.color.transparent, R.drawable.intendi_right, R.drawable.intendi_up, R.drawable.intendi_left, R.drawable.intendi_down, R.drawable.intendi_right_fish, R.drawable.intendi_up_fish, R.drawable.intendi_left_fish, R.drawable.intendi_down_fish, R.drawable.fish, R.drawable.end, R.drawable.intendi_right_flag, R.drawable.intendi_up_flag, R.drawable.intendi_left_flag, R.drawable.intendi_down_flag};
        this.arrayColors = new String[]{"#C4C4C4", "#79CFEA"}; //gray and blue

        randomizePattern();
    }

    public int getImageResource(int state){
        return this.arrayImages[state+1];
    }

    public String getColorResource(int state){
        if(state == -1) return this.arrayColors[0];
        else return this.arrayColors[1];
    }

    public int[] getLabNumbers(){
        return this.labNumbers;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getScore() {
        return this.score;
    }

    public int getFishes() { return this.fishes; }

    public int getLabSize() {
        return this.labSize;
    }

    public void fishAte(){
        this.fishes--;
    }

    public void deleteElementAnswer(int position){
        this.answerUser.remove(position);
    }

    public void deleteAllAnswers(){
        this.answerUser.clear();
    }

    // 0 -> correct answer, -1 error
    public int compareSolution(int finalUserPos, int userFishes){
        this.energy--;
        if(finalUserPos == this.end && userFishes == 0){
            this.round++;
            this.energy++;
            this.score+=100;
            return 0;
        }else{
            return -1;
        }
    }

    public ArrayList<Integer> getAnswerUser() {
        return this.answerUser;
    }

    public int getStart(){ return this.start; }
    public int getEnd(){ return this.end; }

    public void randomizePattern() {
        /*
            -1 -> not available, 0 -> available
            1 -> IntendiRight, 2 -> IntendiUp, 3 -> IntendiLeft, 4 -> IntendiDown
            5 -> IntendiRightFish, 6 -> IntendiUpFish, 7 -> IntendiLeftFish, 8 -> IntendiDownFish
            9 -> fish, 10 -> end, 11 -> IntendiRightFlag, 12 -> IntendiUpFlag, 13 -> IntendiLeftFlag, 14 -> IntendiDownFlag
         */

        Arrays.fill(this.labNumbers, -1);

        //Assign random orientation with or without fish (1-8) at random index, is the start
        this.start = rand.nextInt(this.labSize - 1);
        this.labNumbers[this.start] =  (int) Math.floor(Math.random()*(4-1+1)+1);

        //Define numbers of cells available
        int limit = this.labSize - 1;
        if(this.round+4 < limit) limit = this.round+4;

        int j = 0;
        int curPos = this.start;

        int width = this.getLabWidth();

        this.pattern.clear(); //Save available indexes
        this.pattern.add(curPos);

        ArrayList<Integer> candidatesFish = new ArrayList<>();
        candidatesFish.add(curPos);

        //Create random path, starting from start
        // 1 -> go right, 2 -> go up, 3 -> go left, 4 -> go down

        while(j < limit){
            int movement = (int) Math.floor(Math.random()*(4-1+1)+1); //Select one random direction

            if(isLaberClosed(curPos)){
                System.out.println("Entra");
                this.pattern.remove(new Integer(curPos));
                curPos = this.pattern.get((int) Math.floor(Math.random()*((pattern.size()-1)-0+1)+0));
                /*System.out.println(curPos);
                System.out.println(this.pattern.toString());*/
            }

            if(movement == 1 && ((curPos % width)+1) < width && this.labNumbers[curPos + 1] == -1 ){ //Validate go right, avoid if prev is left
                this.labNumbers[curPos + 1] = 0;
                this.pattern.add(curPos + 1);
                candidatesFish.add(curPos+1);
                curPos = curPos + 1;
                j++;
            }else if(movement == 2 && (curPos - width) >= 0 && this.labNumbers[curPos - width] == -1){ //Validate go up
                this.labNumbers[curPos - width] = 0;
                this.pattern.add(curPos - width);
                candidatesFish.add(curPos - width);
                curPos = curPos - width;
                j++;
            }else if(movement == 3 && ((curPos % width)- 1) >= 0 && this.labNumbers[curPos - 1] == -1){//Validate go left
                this.labNumbers[curPos - 1] = 0;
                this.pattern.add(curPos - 1);
                candidatesFish.add(curPos - 1);
                curPos = curPos - 1;
                j++;
            }else if(movement == 4 && (curPos + width) < this.labSize && this.labNumbers[curPos + width] == -1){ //Validate go down
                this.labNumbers[curPos + width] = 0;
                this.pattern.add(curPos + width);
                candidatesFish.add(curPos + width);
                curPos = curPos + width;
                j++;
            }

            //System.out.println(Arrays.toString(this.labNumbers));
        }


        //Assign value to end of maze
        this.labNumbers[curPos] = 10;
        this.end = curPos;

        candidatesFish.remove(new Integer(curPos)); //Remove last
        candidatesFish.remove(new Integer(this.start)); //Remove first

        Collections.shuffle(candidatesFish);

        this.fishes = 0;
        for (int i=0; i < (this.round/3) + 2; i++) { //Assign random indexes for round/3 + 1 fishes
            this.fishes++;
            this.labNumbers[candidatesFish.get(i)] =  9;
        }

        /*System.out.println("End");
        System.out.println(Arrays.toString(this.labNumbers));*/
    }

    public int getLabWidth(){
        return this.labSize/4; //5 columns
    }

    public int getLabHeight(){
        return this.labSize/5; //4 rows
    }

    public boolean isLaberClosed(int curPos){
        boolean ans = true;
        int width = this.getLabWidth();
        if(((curPos % width)+1) < width && this.labNumbers[curPos + 1] == -1){ //Validate go right
            ans = false;
        }else if((curPos - width) >= 0 && this.labNumbers[curPos - width] == -1){ //Validate go up
            ans = false;
        }else if(((curPos % width)- 1) >= 0  && this.labNumbers[curPos - 1] == -1){//Validate go left
            ans = false;
        }else if((curPos + width) < this.labSize && this.labNumbers[curPos + width] == -1){ //Validate go down
            ans = false;
        }

        return ans;
    }


}
