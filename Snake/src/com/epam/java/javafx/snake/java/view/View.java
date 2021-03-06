package com.epam.java.javafx.snake.java.view;

import com.epam.java.javafx.snake.java.controller.Game;
import com.epam.java.javafx.snake.java.model.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class View {

    @FXML
    private Label score;
    @FXML
    private Canvas canvas;
    @FXML
    private SplitPane sppane;

    private final int GREEN=0;
    private final int RED=1;
    private final int BLUE=2;
    private DrawGame artGame;
    private Game world;
    private GraphicsContext gc;
    private Timeline gameLoop;

    @FXML
    private void initialize() {
        canvas.setWidth(Options.getCol() * Constants.WIDTH_CELL + Options.getCol() * 1);
        canvas.setHeight(Options.getRow() * Constants.HEIGHT_CELL + Options.getRow() * 1);
        gc = canvas.getGraphicsContext2D();
        sppane.setOnMouseClicked(event-> clickedMouse(event.getButton().toString()));
        System.out.println("Start");
        world=Game.getGame();
        artGame=new DrawGame(gc);
        world.addObserver(artGame);
        drawField();
        drawSnake(world.getSnake().getBody());
        drawFrogs(world.getFrogs());

        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();
        KeyFrame kf = new KeyFrame(
                Duration.millis(34),//Duration.seconds(0.017), 60 FPS
                new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent ae)
                    {;
                        score.setText(Integer.toString(world.getScore()));
                    }
                });
        gameLoop.getKeyFrames().add( kf );
    }

    @FXML
    public void clickStart() {
        world.startGame();
        gameLoop.play();
    }

    @FXML
    public void clickPause(){
        world.pauseGame();
        if(world.isPause()) {
            gameLoop.pause();
        } else{
            gameLoop.play();
        }
    }

    @FXML
    public void clickStop(){
        world.stopGame();
        closeGame();
    }

    public void clickedMouse(String button){
        if(button.equals("PRIMARY")){
            world.getSnake().turnLeft();
        }
        if(button.equals("SECONDARY")){
            world.getSnake().turnRight();
        }
    }

    private void closeGame(){
        System.exit(1);
    }

    private void draw(long time) {
        drawField();
        drawSnake(world.getSnake().getBody());
        drawFrogs(world.getFrogs());
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

    private void drawSnake(LinkedList<SnakePart> body){

        int count=-1;
        for(SnakePart i: body) {
            count++;
            if (count == 0) {
                drawHeadSnake(i.getX(), i.getY());
                continue;
            }
            if (count == body.size() - 1) {
                drawQueueSnake(i.getX(), i.getY());
                continue;
            }
            drawBodySnake(i.getX(), i.getY());
        }
    }

    private void drawFrogs(LinkedList<Frog> frogs){
        for(Frog i:frogs){
            switch (i.getType()){
                case GREEN:
                    drawFrog(i.getX(),i.getY(),Color.LIGHTGREEN);
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
}
