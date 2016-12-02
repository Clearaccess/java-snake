package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 11/24/2016.
 */
public class RedFrog extends Frog{
    private int x;
    private int y;
    private int type;
    private Game game;

    public RedFrog(int x,int y){
        this.x=x;
        this.y=y;
        type=1;
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
        while (Running.running){
            int[][] field=game.takeField();

            if(field[x][y]==1){
                game.removeFrog();
                game.leaveField();
                break;
            }

            //Move
            game.leaveField();
        }
    }
}
