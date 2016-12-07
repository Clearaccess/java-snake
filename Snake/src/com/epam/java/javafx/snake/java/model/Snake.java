package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.controller.Game;
import com.epam.java.javafx.snake.java.model.impl.IMoveSnake;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Snake implements Runnable {

    private boolean wasTurn;
    private boolean wouldGrow;
    private int direction=2;
    private int growth;
    private Game game;
    private LinkedList<SnakePart> body;
    private IMoveSnake stratageMove;

    public Snake(){

        this.growth=Options.getLength();
        this.body=new LinkedList<SnakePart>();
        this.wasTurn=false;
        this.wouldGrow=false;
        for(int i=Options.getLength()-1;i>=0;i--){
            this.body.add(new SnakePart(0, i));
        }
        stratageMove=new MoveSnake();
    }

    public boolean getWasTurn(){
        return wasTurn;
    }

    public void setWasTurn(boolean value){
        wasTurn=value;
    }

    public boolean getWouldGrow(){
        return wouldGrow;
    }

    public void setWouldGrow(boolean value){
        wouldGrow=value;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int value){
        direction=value;
    }

    public SnakePart getHead(){
        return this.body.get(0);
    }

    public LinkedList<SnakePart> getBody(){
        return body;
    }

    public SnakePart getQueue(){
        return this.body.get(this.body.size()-1);
    }

    public void run(){
        game=Game.getGame();
        System.out.println(Options.getRow()+" "+Options.getCol());
        System.out.println(Options.getNumFrog());
        while(Running.running) {

            if (!Running.pause) {

                int[][] field = game.takeField();


                move(field);

                if (checkEndGame(field)) {
                    Running.running = false;
                    game.leaveField();
                    game.setGameOver();
                    System.out.println("GAME OVER");
                    break;
                }

                if (isFrogOnCell(field)) {
                    Frog frog = game.getFrog(getHead().getX(), getHead().getY());
                    eat(frog, field);
                    changeScore(frog);
                    game.removeFrog(frog);
                }

                track(field);

                game.leaveField();

                try {
                    Thread.sleep(Options.getSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void turnLeft(){
        if(!wasTurn) {
            direction += 1;
            if (direction > Constants.RIGHT) {
                direction = Constants.UP;
            }
            wasTurn=true;
        }
    }

    public void turnRight(){
        if(!wasTurn) {
            direction -= 1;
            if (direction < Constants.UP) {
                direction = Constants.RIGHT;
            }
            wasTurn=true;
        }
    }

    public void growSnake(){
        if(growth+1<Options.getMaxLength()) {
            growth++;
            wouldGrow = true;
            body.add(new SnakePart(getQueue().getX(), getQueue().getY()));
        }
    }

    public void decreaseSnake(int[][] field){
        if(growth-1>1) {
            SnakePart queue=getQueue();
            growth--;
            field[queue.getX()][queue.getY()]=Constants.CLEAR_CELL;
            game.getObserver().update(this,queue.getX(),queue.getY(),Constants.CLEAR_CELL);
            body.remove(getQueue());
        }
    }

    public void track(int[][]field){
        SnakePart head=getHead();
        field[head.getX()][head.getY()]=Constants.SNAKE_INTO_CELL;
        game.getObserver().update(this,head.getX(),head.getY(),Constants.CLEAR_CELL);
        game.getObserver().update(this,head.getX(),head.getY(),Constants.FILL_CELL);
    }

    public void eraseTrace(int[][]field){
        SnakePart queue=getQueue();
        field[queue.getX()][queue.getY()]=Constants.CLEAR_CELL;
        game.getObserver().update(this,queue.getX(),queue.getY(),Constants.CLEAR_CELL);
    }

    private void move(int[][] field){
        stratageMove.move(this,field);
    }

    private void eat(Frog frog, int[][] field){
        switch (frog.getType()){
            case Constants.GREEN_FROG: growSnake();break;
            case Constants.RED_FROG: decreaseSnake(field);break;
        }
    }

    private boolean isFrogOnCell(int[][] field){
        SnakePart head=getHead();
        return field[head.getX()][head.getY()]==Constants.FROG_INTO_CELL;
    }

    private boolean checkEndGame(int[][] field){
        return ateItself() || ateBlueFrog(field);
    }

    private boolean ateItself(){
        SnakePart head=this.getHead();
        LinkedList<SnakePart>body=this.getBody();
        for(int i=1;i<this.getBody().size();i++){
            if((body.get(i).getX()==head.getX())
                    &&
                    (body.get(i).getY()==head.getY())){
                return true;
            }
        }
        return false;
    }

    private boolean ateBlueFrog(int[][]field){
        SnakePart head=getHead();

        if(field[head.getX()][head.getY()]==Constants.FROG_INTO_CELL){
            Frog frog=game.getFrog(head.getX(),head.getY());
            return frog.getType()==Constants.BLUE_FROG;
        }

        return false;
    }

    private void changeScore(Frog frog){
        switch (frog.getType()){
            case Constants.GREEN_FROG:game.increaseScore(1);break;
            case Constants.RED_FROG:game.increaseScore(2);break;
        }
    }
}
