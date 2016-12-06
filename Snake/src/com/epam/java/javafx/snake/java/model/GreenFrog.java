package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.controller.Game;

/**
 * Created by Aleksandr_Vaniukov on 11/24/2016.
 */
public class GreenFrog extends Frog {
    private int x;
    private int y;
    private int type;
    private Game game;
    private boolean death;

    public GreenFrog(int x,int y){
        this.x=x;
        this.y=y;
        this.type=Constants.GREEN_FROG;
        this.death=true;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getType(){
        return this.type;
    }

    public void run() {
        game=Game.getGame();
        while (Running.running && !Thread.currentThread().isInterrupted() && death){
            if(!Running.pause) {

                int[][] field = game.takeField();

                if (Thread.currentThread().isInterrupted()) {
                    game.leaveField();
                    break;
                }

                /*
                if(canMove(x,y,field)) {
                    move(field);
                }
                */

                game.leaveField();
                try {
                    Thread.sleep(Options.getSpeedFrog());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void move(int[][]field){

        eraseTrace(field);

        x++;

        if(x<0){
            x=Options.getCol()-1;
        }

        if(x>Options.getCol()-1){
            x=0;
        }

        if(y<0){
            y=Options.getRow()-1;
        }

        if(y>Options.getRow()-1){
            y=0;
        }

        track(field);
    }

    public void track(int[][]field){
        field[x][y]=2;
    }

    private void eraseTrace(int[][]field){
        field[x][y]=0;
    }

    private boolean canMove(int x, int y, int[][] field){

        x++;

        if(x<0){
            x=Options.getCol()-1;
        }

        if(x>Options.getCol()-1){
            x=0;
        }

        if(y<0){
            y=Options.getRow()-1;
        }

        if(y>Options.getRow()-1){
            y=0;
        }

        return field[x][y]==0;
    }

    public void kill(){
        death=false;
    }
}
