package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Options {
    private static int row=10;
    private static int col=10;
    private static int length=1;
    private static int numFrog=1;
    private static long speed=100;
    private static long speedFrog=150;

    public static int getRow(){
        return row;
    }

    public static void setRow(int value){
        if(value>row) {
            row=value;
        }
    }

    public static int getCol(){
        return col;
    }

    public static void setCol(int value){
        if(value>col) {
            col=value;
        }
    }

    public static int getLength(){
        return length;
    }

    public static void setLength(int value){
        if(value>length) {
            length=value;
        }
    }

    public static int getFrog(){
        return numFrog;
    }

    public static void setFrog(int value){
        if(value>numFrog) {
            numFrog = value;
        }
    }

    public static long getSpeed(){
        return speed;
    }

    public static void setSpeed(long value){
        if(value>speed) {
            speed=value;
        }
        setSpeedFrog(speed/2);
    }

    public static long getSpeedFrog(){
        return speedFrog;
    }

    public static void setSpeedFrog(long value){
        if(value>speedFrog) {
            speedFrog=value;
        }
    }
}