package com.epam.java.javafx.snake.java.model;

import javafx.scene.image.Image;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class SnakePart {

    private int x;
    private int y;
    //If
    //type==1 that is head of snake
    //type==2 that is body of snake
    //type==3 that is queue of snake
    private int type;

    public SnakePart(int x, int y){
        this.x=x;
        this.y=y;
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
}
