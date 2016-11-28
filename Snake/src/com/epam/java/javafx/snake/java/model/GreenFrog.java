package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 11/24/2016.
 */
public class GreenFrog implements Frog {
    private int x;
    private int y;
    private int type;

    public GreenFrog(int x,int y){
        this.x=x;
        this.y=y;
        type=0;
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
}