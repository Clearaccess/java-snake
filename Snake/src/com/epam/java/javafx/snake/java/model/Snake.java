package com.epam.java.javafx.snake.java.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Snake implements Runnable {

    private LinkedList<SnakePart> body;

    private final int UP=0;
    private final int LEFT=1;
    private final int DOWN=2;
    private final int RIGHT=3;
    private int growth;
    //Help to organize one turn
    private boolean wasTurn;
    private boolean wouldGrow;
    private Game game;

    private int direction=2;

    public Snake(){

        this.growth=Options.getLength();
        this.body=new LinkedList<SnakePart>();
        this.wasTurn=false;
        this.wouldGrow=false;
        for(int i=Options.getLength()-1;i>=0;i--){
            this.body.add(new SnakePart(0, i));
        }
    }

    public LinkedList<SnakePart> getBody(){
        return body;
    }

    public SnakePart getHead(){
        return this.body.get(0);
    }

    public SnakePart getQueue(){
        return this.body.get(this.body.size()-1);
    }

    public void run(){
        game=Game.getGame();
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

                for (int i = 0; i < Options.getRow(); i++) {
                    for (int j = 0; j < Options.getCol(); j++) {
                        System.out.print(field[i][j]);
                    }
                    System.out.println();
                }
                System.out.println("__________");
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

    private void move(int[][] field){

        if(!wouldGrow) {
            eraseTrace(field);
        }

        for(int i=body.size()-1, j=body.size()-2;i>0;i--,j--){
            body.get(i).setX(body.get(j).getX());
            body.get(i).setY(body.get(j).getY());
        }

        SnakePart head=body.get(0);

        switch (direction){
            case UP: head.setY(head.getY()-1); break;
            case LEFT: head.setX(head.getX()-1); break;
            case DOWN: head.setY(head.getY()+1); break;
            case RIGHT: head.setX(head.getX()+1); break;
        }

        if(head.getX()<0){
            head.setX(Options.getCol()-1);
        }
        if(head.getX()>Options.getCol()-1){
            head.setX(0);
        }

        if(head.getY()<0){
            head.setY(Options.getRow()-1);
        }
        if(head.getY()>Options.getRow()-1){
            head.setY(0);
        }

        wasTurn=false;
        wouldGrow=false;
    }

    public void turnLeft(){
        if(!wasTurn) {
            direction += 1;
            if (direction > RIGHT) {
                direction = UP;
            }
            wasTurn=true;
        }
    }

    public void turnRight(){
        if(!wasTurn) {
            direction -= 1;
            if (direction < UP) {
                direction = RIGHT;
            }
            wasTurn=true;
        }
    }

    private void eat(Frog frog, int[][] field){
        switch (frog.getType()){
            case 0: growSnake();break;
            case 1: decreaseSnake(field);break;
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

    private void eraseTrace(int[][]field){
        SnakePart queue=getQueue();
        field[queue.getX()][queue.getY()]=0;
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
            case 0:game.increaseScore(1);break;
            case 1:game.increaseScore(2);break;
        }
    }
}
