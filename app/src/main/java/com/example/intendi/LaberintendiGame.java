package com.example.intendi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class LaberintendiGame extends AppCompatActivity {

    RecyclerView laberBoard;
    RecyclerView ansBoard;

    ImageView thunderOne;
    ImageView thunderTwo;
    ImageView thunderThree;
    TextView scoreLbl;

    LayoutAnimationController layoutAnimationController;
    LaberManager laberManager;

    CardView cardMove;
    CardView cardLeft;
    CardView cardRight;
    CardView cardEat;

    CardView cardPlay;
    CardView cardDelete;

    FloatingActionButton go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laberintendi_game);

        cardMove = findViewById(R.id.cardViewMove);
        cardLeft = findViewById(R.id.cardViewLeft);
        cardRight = findViewById(R.id.cardViewRight);
        cardEat = findViewById(R.id.cardViewEat);
        cardPlay = findViewById(R.id.playCard);
        cardDelete = findViewById(R.id.deleteCard);

        laberManager = new LaberManager();

        thunderOne = findViewById(R.id.thunderOneImg);
        thunderTwo = findViewById(R.id.thunderTwoImg);
        thunderThree = findViewById(R.id.thunderThreeImg);

        scoreLbl = findViewById(R.id.scoreLbl);

        laberBoard = findViewById(R.id.laberBoard);
        ansBoard = findViewById(R.id.ansBoard);

        //Setup laberintendi board
        laberBoard.setAdapter(new LaberBoardAdapter(this, laberManager));

        laberBoard.setHasFixedSize(true);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.recyclerview_grid_layout_animation);

        laberBoard.setLayoutAnimation(layoutAnimationController);
        laberBoard.setLayoutManager(new GridLayoutManager(this, 5));

        //Setup user answers board
        ansBoard.setAdapter(new AnswersBoardAdapter(this, laberManager));
        ansBoard.setHasFixedSize(true);
        ansBoard.setLayoutManager(new GridLayoutManager(this, 5));
    }

    public void clickMove(View v){
        this.laberManager.getAnswerUser().add(1);
        ansBoard.getAdapter().notifyItemInserted(laberManager.getAnswerUser().size()-1);
    }

    public void clickLeft(View v){
        this.laberManager.getAnswerUser().add(2);
        ansBoard.getAdapter().notifyItemInserted(laberManager.getAnswerUser().size()-1);
    }

    public void clickRigth(View v){
        this.laberManager.getAnswerUser().add(3);
        ansBoard.getAdapter().notifyItemInserted(laberManager.getAnswerUser().size()-1);
    }

    public void clickEat(View v){
        this.laberManager.getAnswerUser().add(4);
        ansBoard.getAdapter().notifyItemInserted(laberManager.getAnswerUser().size()-1);
    }

    public void deleteAnswers(View v){
        this.laberManager.deleteAllAnswers();
        ansBoard.getAdapter().notifyDataSetChanged();
    }

    public void updateEnergy(int energy){
        if(energy == 3){
            thunderThree.setImageResource(R.drawable.thunder_yellow);
            thunderTwo.setImageResource(R.drawable.thunder_yellow);
            thunderOne.setImageResource(R.drawable.thunder_yellow);
        } else if(energy == 2){
            thunderThree.setImageResource(R.drawable.thunder_gray);
            thunderTwo.setImageResource(R.drawable.thunder_yellow);
            thunderOne.setImageResource(R.drawable.thunder_yellow);
        }else if(energy == 1){
            thunderThree.setImageResource(R.drawable.thunder_gray);
            thunderTwo.setImageResource(R.drawable.thunder_gray);
            thunderOne.setImageResource(R.drawable.thunder_yellow);
        }else{
            thunderThree.setImageResource(R.drawable.thunder_gray);
            thunderTwo.setImageResource(R.drawable.thunder_gray);
            thunderOne.setImageResource(R.drawable.thunder_gray);
        }
    }

    private void animateLaberCellChange(int delay, ImageView image, int state){

        int newImage = laberManager.getImageResource(state);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(image, "alpha", image.getAlpha(), 0);
        fadeIn.setRepeatCount(1);
        fadeIn.setRepeatMode(ValueAnimator.REVERSE);
        fadeIn.setDuration(200);
        fadeIn.setStartDelay(delay);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                image.setImageResource(newImage);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        fadeIn.start();
    }

    public void disableClicks(){
        cardMove.setEnabled(false);
        cardLeft.setEnabled(false);
        cardRight.setEnabled(false);
        cardEat.setEnabled(false);
        cardPlay.setEnabled(false);
        cardDelete.setEnabled(false);
    }

    public void enableClicks(){
        cardMove.setEnabled(true);
        cardLeft.setEnabled(true);
        cardRight.setEnabled(true);
        cardEat.setEnabled(true);
        cardPlay.setEnabled(true);
        cardDelete.setEnabled(true);
    }

    public void runAnswers(View v) {

        disableClicks();

        //Copy actual board
        int[] laberBoardNumbers = laberManager.getLabNumbers();
        int newLen = laberBoardNumbers.length;
        int[] copyLaberBoard = Arrays.copyOf(laberBoardNumbers, newLen);

        ArrayList<Integer> userAnswers = laberManager.getAnswerUser();
        int curPos = laberManager.getStart();
        int state = copyLaberBoard[curPos];
        int copyFishes= laberManager.getFishes();

        ArrayList<ArrayList<Integer>> changes = new ArrayList<ArrayList<Integer>>();
        int width = laberManager.getLabWidth();

        for(int i =0; i < userAnswers.size(); i++) {
            int action = userAnswers.get(i); //1 -> move, 2 -> turnLeft, 3 -> turnRight, 4 -> eat

            ArrayList<Integer> currChange = new ArrayList<Integer>();
            currChange.add(curPos);

            //Move
            if(action == 1) {
                if (state == 1 || state == 5 || state == 11) { //Intendi is in right orientation
                    if (((curPos % width) + 1) < width && copyLaberBoard[curPos + 1] != -1) { //Validate go right
                        //Modify cell pre movement
                        if (state == 5) { //Fish in position before movement
                            copyLaberBoard[curPos] = 9; //Leave cell with fish
                            currChange.add(9);
                        }else if(state == 1){ //No fish in position before movement
                            copyLaberBoard[curPos] = 0; //Leave cell available
                            currChange.add(0);
                        }else{ //Flag in position before movement
                            copyLaberBoard[curPos] = 10; //Leave cell with flag
                            currChange.add(10);
                        }

                        //Check if after movement fish is found
                        if (copyLaberBoard[curPos + 1] == 9) { //Fish in next cell
                            copyLaberBoard[curPos + 1] = 5; //Next cell with IntendiRightFish
                            currChange.add(curPos + 1);
                            currChange.add(5);
                            state = 5;
                        }else if (copyLaberBoard[curPos + 1] == 0) { //Next cell available
                            copyLaberBoard[curPos + 1] = 1; //Next cell with IntendiRight
                            currChange.add(curPos + 1);
                            currChange.add(1);
                            state = 1;
                        }else{ //Next cell with flag
                            copyLaberBoard[curPos + 1] = 11; //Next cell with IntendiRightFlag
                            currChange.add(curPos + 1);
                            currChange.add(11);
                            state = 11;
                        }
                        curPos = curPos + 1;
                    } else {
                        currChange.add(state); //Flash without changing image
                    }
                }

                if (state == 2 || state == 6 || state == 12) { //Intendi is in up orientation
                    if ((curPos - width) >= 0 && copyLaberBoard[curPos - width] != -1) { //Validate go up
                        //Modify cell pre movement
                        if (state == 6) { //Fish in position before movement
                            copyLaberBoard[curPos] = 9; //Leave cell with fish
                            currChange.add(9);
                        }else if(state == 2){ //No fish in position before movement
                            copyLaberBoard[curPos] = 0; //Leave cell available
                            currChange.add(0);
                        }else{ //Flag in position before movement
                            copyLaberBoard[curPos] = 10; //Leave cell with flag
                            currChange.add(10);
                        }

                        //Check if after movement fish is found
                        if (copyLaberBoard[curPos - width] == 9) { //Fish in next cell
                            copyLaberBoard[curPos - width] = 6; //Next cell with IntendiUpFish
                            currChange.add(curPos - width);
                            currChange.add(6);
                            state = 6;
                        }else if (copyLaberBoard[curPos - width] == 0) { //Next cell available
                            copyLaberBoard[curPos - width] = 2; //Next cell with IntendiUp
                            currChange.add(curPos - width);
                            currChange.add(2);
                            state = 2;
                        }else{ //Next cell with flag
                            copyLaberBoard[curPos - width] = 12; //Next cell with intendi and no fish
                            currChange.add(curPos - width);
                            currChange.add(12);
                            state = 12;
                        }
                        curPos = curPos - width;
                    } else {
                        currChange.add(state); //Flash without changing image
                    }
                }

                if (state == 3 || state == 7 || state == 13) { //Intendi is in left orientation
                    if (((curPos % width)- 1) >= 0 && copyLaberBoard[curPos - 1] != -1) { //Validate go left
                        //Modify cell pre movement
                        if (state == 7) { //Fish in position before movement
                            copyLaberBoard[curPos] = 9; //Leave cell with fish
                            currChange.add(9);
                        }else if(state == 3){ //No fish in position before movement
                            copyLaberBoard[curPos] = 0; //Leave cell available
                            currChange.add(0);
                        }else{ //Flag in position before movement
                            copyLaberBoard[curPos] = 10; //Leave cell with flag
                            currChange.add(10);
                        }

                        //Check if after movement fish is found
                        if (copyLaberBoard[curPos - 1] == 9) { //Fish in next cell
                            copyLaberBoard[curPos - 1] = 7; //Next cell with IntendiUpFish
                            currChange.add(curPos - 1);
                            currChange.add(7);
                            state = 7;
                        }else if (copyLaberBoard[curPos - 1] == 0) { //Next cell available
                            copyLaberBoard[curPos - 1] = 3; //Next cell with IntendiLeft
                            currChange.add(curPos - 1);
                            currChange.add(3);
                            state = 3;
                        }else{ //Next cell with flag
                            copyLaberBoard[curPos - 1] = 13; //Next cell with IntendiLeftFlag
                            currChange.add(curPos - 1);
                            currChange.add(13);
                            state = 13;
                        }
                        curPos = curPos - 1;
                    } else {
                        currChange.add(state); //Flash without changing image
                    }
                }

                if (state == 4 || state == 8 || state == 14) { //Intendi is in down orientation
                    if ((curPos + width) < laberManager.getLabSize() && copyLaberBoard[curPos + width] != -1) { //Validate go down
                        //Modify cell pre movement
                        if (state == 8) { //Fish in position before movement
                            copyLaberBoard[curPos] = 9; //Leave cell with fish
                            currChange.add(9);
                        }else if(state == 4){ //No fish in position before movement
                            copyLaberBoard[curPos] = 0; //Leave cell available
                            currChange.add(0);
                        }else{ //Flag in position before movement
                            copyLaberBoard[curPos] = 10; //Leave cell with flag
                            currChange.add(10);
                        }

                        //Check if after movement fish is found
                        if (copyLaberBoard[curPos + width] == 9) { //Fish in next cell
                            copyLaberBoard[curPos + width] = 8; //Next cell with IntendiDownFish
                            currChange.add(curPos + width);
                            currChange.add(8);
                            state = 8;
                        }else if (copyLaberBoard[curPos + width] == 0) { //Next cell available
                            copyLaberBoard[curPos + width] = 4; //Next cell with IntendiDown
                            currChange.add(curPos + width);
                            currChange.add(4);
                            state = 4;
                        }else{ //Next cell with flag
                            copyLaberBoard[curPos + width] = 14; //Next cell with IntendiDownFlag
                            currChange.add(curPos + width);
                            currChange.add(14);
                            state = 14;
                        }
                        curPos = curPos + width;
                    } else {
                        currChange.add(state); //Flash without changing image
                    }
                }
            }

            //Turn left
            if(action == 2){
                if(state == 4){ // IntendiDown to IntendiRight
                    state = 1;
                }else if(state == 8){ //IntendiDownFish to IntendiRightFish
                    state = 5;
                }else{
                    state++;
                }
                copyLaberBoard[curPos] = state;
                currChange.add(state);
            }

            //Turn right
            if(action == 3){
                if(state == 1){ //IntendiRight to IntendiDown
                    state = 4;
                }else if(state == 5){ //IntendiRightFish to IntendiDownFish
                    state = 8;
                }else{
                    state--;
                }
                copyLaberBoard[curPos] = state;
                currChange.add(state);
                //animateLaberCellChange(i*400, image, state);
                //image.setImageResource(laberManager.getImageResource(state));
            }

            //Eat
            if(action == 4){
                if(state >= 5 && state <=8){
                    copyFishes--;
                    state = state - 4; //Leave orientation, remove fish
                    copyLaberBoard[curPos] = state;
                    currChange.add(state);
                }else{ //No fish at that cell
                    copyLaberBoard[curPos] = state;
                    currChange.add(state);
                }
            }

            changes.add(currChange);
        }

        int prevEnergy = laberManager.getEnergy() - 1;
        int response = laberManager.compareSolution(curPos, copyFishes);
        updateEnergy(prevEnergy);

        for(int i=0; i<changes.size(); i++){
            if(changes.get(i).size() == 2){ //Turn movement, invalid movement or eat
                int position = changes.get(i).get(0);
                int animateState = changes.get(i).get(1);
                ImageView imageAnim =  laberBoard.getLayoutManager().getChildAt(position).findViewById(R.id.imageCard);
                animateLaberCellChange(i*1000, imageAnim, animateState);
            }else{ //Valid movement, modify two cells
                int position1 = changes.get(i).get(0);
                int animateState1 = changes.get(i).get(1);
                ImageView imageAnim1 =  laberBoard.getLayoutManager().getChildAt(position1).findViewById(R.id.imageCard);
                animateLaberCellChange(i*1000, imageAnim1, animateState1);

                int position2 = changes.get(i).get(2);
                int animateState2 = changes.get(i).get(3);
                ImageView imageAnim2 =  laberBoard.getLayoutManager().getChildAt(position2).findViewById(R.id.imageCard);
                animateLaberCellChange(i*1000, imageAnim2, animateState2);
            }
        }

        int scoreEnergyDelay = changes.size()*1000 + 200;
        final Handler handlerRound = new Handler(Looper.getMainLooper());
        handlerRound.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(response == 0){ //Correct answer
                    System.out.println(laberManager.getEnergy());
                    updateEnergy(laberManager.getEnergy());
                    scoreLbl.setText(String.valueOf(laberManager.getScore()));
                }
            }
        }, scoreEnergyDelay);

        final Handler handlerHideGrid = new Handler(Looper.getMainLooper());
        handlerHideGrid.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(response == 0){
                    laberManager.randomizePattern();
                }
                laberManager.deleteAllAnswers();
                ansBoard.getAdapter().notifyDataSetChanged();
                laberBoard.setVisibility(View.INVISIBLE);
            }
        }, scoreEnergyDelay + 200);

        final Handler handlerShowGrid = new Handler(Looper.getMainLooper());
        handlerShowGrid.postDelayed(new Runnable() {
            @Override
            public void run() {
                laberBoard.setVisibility(View.VISIBLE);
                laberBoard.getAdapter().notifyDataSetChanged();
                layoutAnimationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.recyclerview_grid_layout_animation);
                laberBoard.setLayoutAnimation(layoutAnimationController);
                enableClicks();
            }
        }, scoreEnergyDelay + 500);

    }
}