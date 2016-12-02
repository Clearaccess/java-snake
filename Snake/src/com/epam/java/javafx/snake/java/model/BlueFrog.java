package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr on 11/27/2016.
 */
public class BlueFrog extends Frog {
    private int x;
    private int y;
    private int type;
    private Game game;

    public BlueFrog(int x,int y){
        this.x=x;
        this.y=y;
        type=2;
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
        while (Running.running){
            int[][] field=game.takeField();

            if(field[x][y]==1){
                game.removeFrog();
                game.leaveField();
                break;
            }

            //Move
            game.leaveField();

            try {
                Thread.sleep(Options.getSpeedFrog());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
