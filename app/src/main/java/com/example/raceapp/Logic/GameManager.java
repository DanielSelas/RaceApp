package com.example.raceapp.Logic;

import java.util.Random;

public class GameManager {
    public static final int ROWS = 5, COLS = 3, START_LIVE=3;
    private int hitPoint, currentLive=START_LIVE,playerPlace;
    public boolean hit;
    private boolean[] studentLife;
    public boolean[][] gameMat;

// constractur
    public GameManager() {
        gameMat = new boolean[ROWS][COLS];
        initHearts();
        playerPlace=1;
    }



// getters setters
    public void setHit(boolean hit) {
    this.hit = hit;
}
    public boolean[] getStudentLife() {
        return studentLife;
    }
    public void setPlayerPlace(int playerPlace) {
        this.playerPlace = playerPlace;
    }

    // get rows and cols so i can use them in the main activity
    public  int getROWS() {
        return ROWS;
    }
    public  int getCOLS() {
        return COLS;
    }



//  all the game manager responsabilities
    public boolean activeGame(int row,int col) {
        return gameMat[row][col];
    }
    public void initHearts(){
        studentLife=new boolean[START_LIVE];
        for(int i=0;i< studentLife.length;i++)
            studentLife[i]=true;
    }
    public void addNewObs(){
        int col =getRandomLocation();
            for(int j = 0;j<getCOLS();j++){
                gameMat[0][j]=col==j;
          }
    }
    public void refreshGame(){
        int endRow=getROWS()-1;
        for(int i=endRow;i>=0;i--) {
            for (int j = 0; j < COLS; j++) {
                if (activeGame(i, j) && i == endRow) {
                    gameMat[i][j] = false;
                    if(j==playerPlace) {
                        hit = true;
                        if (currentLive == 0)
                            currentLive = START_LIVE;
                        gotHit();
                        studentLife[currentLive]=false;
                        if(!studentLife[0])
                            initHearts();
                    }
                }else if(i != endRow){
                    gameMat[i+1][j]= gameMat[i][j];
                }
            }
        }
    }
    public void refresh(){
        refreshGame();
        addNewObs();
    }
    public int getRandomLocation(){
        Random rand = new Random();
        return rand.nextInt(COLS);
    }
    public void gotHit(){
        currentLive--;
    }

}
