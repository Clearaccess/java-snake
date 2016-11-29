package com.epam.java.javafx.snake.java.view;

import com.epam.java.javafx.snake.java.controller.Controller;
import com.epam.java.javafx.snake.java.main.Main;
import com.epam.java.javafx.snake.java.model.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.Console;
import java.util.LinkedList;

public class View {

    private final int GREEN=0;
    private final int RED=1;
    private final int BLUE=2;
    @FXML
    private Label score;
    @FXML
    private Canvas canvas;
    @FXML
    private SplitPane sppane;

    private AnimationTimer timer;
    private Timeline gameLoop;
    private Game world;
    private GraphicsContext gc;



    @FXML
    private void initialize() {
        canvas.setWidth(Options.getCol() * Constants.WIDTH_CELL + Options.getCol() * 1);
        canvas.setHeight(Options.getRow() * Constants.HEIGHT_CELL + Options.getRow() * 1);
        gc = canvas.getGraphicsContext2D();
        sppane.setOnMouseClicked(event-> clickedMouse(event.getButton().toString()));
        System.out.println("Start");

        world=new Game();
        drawField();
        drawSnake(world.getSnake());

        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();
        KeyFrame kf = new KeyFrame(
                Duration.millis(1),//Duration.seconds(0.017), 60 FPS
                new EventHandler<ActionEvent>()
                {
                    long times=0;
                    long timesFrog=0;
                    public void handle(ActionEvent ae)
                    {
                        world.update(times);
                        draw(times);
                        if(Options.getSpeed()==times){

                            //System.out.println("Times zero out");
                            times=0;
                        }
                        times++;
                    }
                });
        gameLoop.getKeyFrames().add( kf );
    }

    private void draw(long time){
        if(time==Options.getSpeed()){
            drawField();
            drawSnake(world.getSnake());
            drawFrogs(world.getFrogs());
        }
    }
    private void drawField(){
        for (int i = 0; i < Options.getRow(); i++) {
            for (int j = 0; j < Options.getCol(); j++) {
                drawCell(Color.BLACK,j,i);
            }
        }
    }

    private void drawCell(Color clr,int x,int y){
        gc.setFill(clr);
        gc.fillRect(x * Constants.OFFSET + x * Constants.WIDTH_CELL, y * Constants.OFFSET + y * Constants.HEIGHT_CELL, Constants.WIDTH_CELL, Constants.HEIGHT_CELL);
    }

    private void drawHeadSnake(int x,int y){
        gc.setFill(Color.YELLOW);
        double offsetX=((double)Constants.WIDTH_CELL)/(2.0*2);
        double offsetY=((double)Constants.HEIGHT_CELL)/(2.0*2);
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+(offsetX), y * Constants.OFFSET + y * Constants.HEIGHT_CELL+(offsetY), ((double)Constants.WIDTH_CELL)/2.0, ((double)Constants.HEIGHT_CELL)/2.0);
    }

    private void drawBodySnake(int x,int y){
        gc.setFill(Color.YELLOW);
        double offsetX=((double)Constants.WIDTH_CELL)/2.0-((double)Constants.WIDTH_CELL)/6.0;
        double offsetY=((double)Constants.HEIGHT_CELL)/2.0-((double)Constants.HEIGHT_CELL)/6.0;
        gc.fillOval(x * Constants.OFFSET + x * Constants.WIDTH_CELL+offsetX, y * Constants.OFFSET + y * Constants.HEIGHT_CELL+offsetY, ((double)Constants.WIDTH_CELL)/3.0, ((double)Constants.HEIGHT_CELL)/3.0);
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

    private void drawSnake(Snake snake){

        int count=-1;
        for(SnakePart i: snake.getBody()) {
            count++;
            System.out.println(i.getX()+" "+i.getY());
            if (count == 0) {
                drawHeadSnake(i.getX(), i.getY());
                continue;
            }
            if (count == snake.getBody().size() - 1) {
                drawQueueSnake(i.getX(), i.getY());
                continue;
            }
            drawBodySnake(i.getX(), i.getY());
        }
    }

    private void drawFrogs(LinkedList<Frog>frogs){
        for(Frog i:frogs){
            switch (i.getType()){
                case GREEN:
                    drawFrog(i.getX(),i.getY(),Color.GREEN);
                    break;
                case RED:
                    drawFrog(i.getX(),i.getY(),Color.RED);
                    break;
                case BLUE:
                    drawFrog(i.getX(),i.getY(),Color.BLUE);
                    break;
            }
        }
    }

    @FXML
    public void clickStart(){
        //timer.start();
        gameLoop.play();
    }

    @FXML
    public void clickPause(){
        //timer.stop();
        gameLoop.pause();
    }

    @FXML
    public void clickStop(){
        //timer.stop();
        gameLoop.pause();
    }

    public void clickedMouse(String button){
        if(button.equals("PRIMARY")){
            world.getSnake().turnLeft();
            System.out.println("LEFT");
        }
        if(button.equals("SECONDARY")){
            world.getSnake().turnRight();
            System.out.println("RIGHT");
        }
    }
}
