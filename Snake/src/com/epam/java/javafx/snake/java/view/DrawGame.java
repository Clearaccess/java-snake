package com.epam.java.javafx.snake.java.view;

import com.epam.java.javafx.snake.java.model.*;
import com.epam.java.javafx.snake.java.view.impl.IObsever;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Created by Aleksandr_Vaniukov on 12/7/2016.
 */
public class DrawGame implements IObsever {

    private GraphicsContext gc;

    public DrawGame(GraphicsContext gc){
        this.gc=gc;
    }

    public void update(Snake snake, int x,int y, int state){

        SnakePart head=snake.getHead();
        SnakePart secondHead=snake.getBody().get(1);
        SnakePart queue=snake.getQueue();

        switch(state){
            case Constants.CLEAR_CELL:
                drawCell(Color.BLACK,x,y);
                break;
            case Constants.FILL_CELL:
                drawCell(Color.BLACK,head.getX(),head.getY());
                drawCell(Color.BLACK,queue.getX(),queue.getY());
                drawCell(Color.BLACK,secondHead.getX(),secondHead.getY());
                drawHeadSnake(head.getX(),head.getY());
                drawBodySnake(secondHead.getX(),secondHead.getY());
                drawQueueSnake(queue.getX(),queue.getY());
                break;
        }
    }

    public void update(GreenFrog frog, int x,int y, int state){
        switch (state){
            case Constants.CLEAR_CELL:
                drawCell(Color.BLACK,x,y);
                break;
            case Constants.FILL_CELL:
                drawFrog(x,y,Color.LIGHTGREEN);
                break;
        }
    }

    public void update(RedFrog frog, int x,int y, int state){
        switch (state){
            case Constants.CLEAR_CELL:
                drawCell(Color.BLACK,x,y);
                break;
            case Constants.FILL_CELL:
                drawFrog(x,y,Color.RED);
                break;
        }
    }

    public void update(BlueFrog frog, int x,int y, int state){
        switch (state){
            case Constants.CLEAR_CELL:
                drawCell(Color.BLACK,x,y);
                break;
            case Constants.FILL_CELL:
                drawFrog(x,y,Color.BLUE);
                break;
        }
    }

    private void drawHeadSnake(int x,int y){
        gc.setFill(Color.YELLOW);
        double offsetX=((double) Constants.WIDTH_CELL)/(2.0*2);
        double offsetY=((double)Constants.HEIGHT_CELL)/(2.0*2);
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+(offsetX), y * Constants.OFFSET + y * Constants.HEIGHT_CELL+(offsetY), ((double)Constants.WIDTH_CELL)/2.0, ((double)Constants.HEIGHT_CELL)/2.0);
    }
    private void drawQueueSnake(int x,int y){
        gc.setFill(Color.YELLOW);
        double offsetX=((double)Constants.WIDTH_CELL)/2.0-((double)Constants.WIDTH_CELL)/8.0;
        double offsetY=((double)Constants.HEIGHT_CELL)/2.0-((double)Constants.WIDTH_CELL)/8.0;
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+offsetX, y * Constants.OFFSET + y * Constants.HEIGHT_CELL+offsetY, ((double)Constants.WIDTH_CELL)/4.0, ((double)Constants.HEIGHT_CELL)/4.0);
    }
    private void drawFrog(int x,int y, Color clr){
        gc.setFill(clr);
        double offsetX=((double)Constants.WIDTH_CELL)/(2.0*2);
        double offsetY=((double)Constants.HEIGHT_CELL)/(2.0*2);
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+(offsetX), y * Constants.OFFSET + y * Constants.HEIGHT_CELL+(offsetY), ((double)Constants.WIDTH_CELL)/2.0, ((double)Constants.HEIGHT_CELL)/2.0);
    }
    private void drawCell(Color clr,int x,int y){
        gc.setFill(clr);
        gc.fillRect(x * Constants.OFFSET + x * Constants.WIDTH_CELL, y * Constants.OFFSET + y * Constants.HEIGHT_CELL, Constants.WIDTH_CELL, Constants.HEIGHT_CELL);
    }
    private void drawBodySnake(int x,int y){
        gc.setFill(Color.YELLOW);
        double offsetX=((double)Constants.WIDTH_CELL)/2.0-((double)Constants.WIDTH_CELL)/6.0;
        double offsetY=((double)Constants.HEIGHT_CELL)/2.0-((double)Constants.HEIGHT_CELL)/6.0;
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+offsetX, y * Constants.OFFSET + y * Constants.HEIGHT_CELL+offsetY, ((double)Constants.WIDTH_CELL)/3.0, ((double)Constants.HEIGHT_CELL)/3.0);
    }
}
