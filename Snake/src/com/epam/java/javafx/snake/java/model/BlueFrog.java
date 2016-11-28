package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr on 11/27/2016.
 */
public class BlueFrog implements Frog {
    private int x;
    private int y;
    private int type;

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
}
