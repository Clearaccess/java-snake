package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Options {
    private static int row=10;
    private static int col=10;
    private static int length=2;
    private static int maxLength=50;
    private static int numFrog=1;
    private static long speed=500;
    private static long speedFrog=750;

    public static int getRow(){
        return row;
    }

    public static void setRow(int value){
        if(value>row && Constants.MAX_ROW>=value) {
            row=value;
        }
    }

    public static int getCol(){
        return col;
    }

    public static void setCol(int value){
        if(value>col && Constants.MAX_COL>=value) {
            col=value;
        }
    }

    public static int getLength(){
        return length;
    }

    public static void setLength(int value){
        if(value>length && row>=value) {
            length=value;
            maxLength=row*col/10*5;
        }
    }

    public static int getMaxLength(){
        return maxLength;
    }

    public static int getNumFrog(){
        return numFrog;
    }

    public static void setNumFrog(int value){
        if(value>numFrog && row*col/10*2>=value) {
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
        setSpeedFrog(speed+speed/2);
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
