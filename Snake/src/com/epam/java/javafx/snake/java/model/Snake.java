package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.controller.Game;
import com.epam.java.javafx.snake.java.model.impl.IMoveSnake;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Snake implements Runnable {

    private CopyOnWriteArrayList<SnakePart> body;

    private int growth;
    //Help to organize one turn
    private boolean wasTurn;
    private boolean wouldGrow;
    private Game game;
    private IMoveSnake stratageMove;

    private int direction=2;

    public Snake(){

        this.growth=Options.getLength();
        this.body=new CopyOnWriteArrayList<SnakePart>();
        this.wasTurn=false;
        this.wouldGrow=false;
        for(int i=Options.getLength()-1;i>=0;i--){
            this.body.add(new SnakePart(0, i));
        }
        stratageMove=new MoveSnake();
    }

    public CopyOnWriteArrayList<SnakePart> getBody(){
        return body;
    }

    public SnakePart getHead(){
        return this.body.get(0);
    }

    public SnakePart getQueue(){
        return this.body.get(this.body.size()-1);
    }

    public boolean getWouldGrow(){
        return wouldGrow;
    }

    public void setWouldGrow(boolean value){
        wouldGrow=value;
    }

    public boolean getWasTurn(){
        return wasTurn;
    }

    public void setWasTurn(boolean value){
        wasTurn=value;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int value){
        direction=value;
    }

    public void run(){
        game=Game.getGame();
        while(Running.running) {

            if (!Running.pause) {

                int[][] field = game.takeField();

                System.out.println(1);

                move(field);

                if (checkEndGame(field)) {
                    Running.running = false;
                    game.leaveField();
                    game.setGameOver();
                    System.out.println("GAME OVER");
                    break;
                }
                System.out.println(1);

                if (isFrogOnCell(field)) {
                    Frog frog = game.getFrog(getHead().getX(), getHead().getY());
                    eat(frog, field);
                    changeScore(frog);
                    game.removeFrog(frog);
                }
                System.out.println(1);

                track(field);

                System.out.println(1);
                for (int i = 0; i < Options.getCol(); i++) {
                    for (int j = 0; j < Options.getRow(); j++) {
                        System.out.print(field[i][j]);
                    }
                    System.out.println();
                }
                System.out.println("__________");
                System.out.println("Threads: "+game.getThreads().size());
                game.leaveField();

                System.out.println(2);

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

    private void move(int[][] field){
        stratageMove.move(this,field);
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
            field[queue.getX()][queue.getY()]=0;
            body.remove(getQueue());
        }
    }

    public void track(int[][]field){
        SnakePart head=getHead();
        field[head.getX()][head.getY()]=1;
    }

    public void eraseTrace(int[][]field){
        SnakePart queue=getQueue();
        field[queue.getX()][queue.getY()]=0;
    }

    private void eat(Frog frog, int[][] field){
        switch (frog.getType()){
            case 0: growSnake();break;
            case 1: decreaseSnake(field);break;
        }
    }

    private boolean isFrogOnCell(int[][] field){
        SnakePart head=getHead();
        return field[head.getX()][head.getY()]>=2;
    }

    private boolean checkEndGame(int[][] field){
        return ateItself() || ateBlueFrog(field);
    }

    private boolean ateItself(){
        SnakePart head=this.getHead();
        CopyOnWriteArrayList<SnakePart>body=this.getBody();
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
            case 0:game.increaseScore(1);break;
            case 1:game.increaseScore(2);break;
        }
    }
}
