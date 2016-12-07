package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.controller.Game;
import com.epam.java.javafx.snake.java.model.impl.IMoveFrog;

/**
 * Created by Aleksandr_Vaniukov on 11/24/2016.
 */
public class GreenFrog extends Frog {
    private int x;
    private int y;
    private int type;
    private Game game;
    private boolean death;
    private IMoveFrog strategyMove;

    public GreenFrog(int x,int y){
        this.x=x;
        this.y=y;
        this.type=Constants.GREEN_FROG;
        this.death=true;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int value){
        this.x=value;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int value){
        this.y=value;
    }

    public int getType(){
        return this.type;
    }

    public void run() {
        game=Game.getGame();
        strategyMove=new MoveGreenFrog(game);

        while (Running.running && !Thread.currentThread().isInterrupted() && death){
            if(!Running.pause) {

                int[][] field = game.takeField();

                if (Thread.currentThread().isInterrupted()) {
                    game.leaveField();
                    break;
                }

                move(game.getSnake(),field);

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

    public void kill(){
        death=false;
    }

    private void move(Snake snake, int[][]field){
        strategyMove.move(this,snake,field);
    }
}
