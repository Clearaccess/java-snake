package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public abstract class Frog implements Runnable {
    //Types frogs
    // 0 - Green
    // 1 - Red
    // 2 - Blue

    public abstract int getX();
    public abstract void setX(int value);
    public abstract int getY();
    public abstract void setY(int value);
    public abstract int getType();
    public abstract void kill();
}
