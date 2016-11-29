package com.epam.java.javafx.snake.java.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Snake {

    private LinkedList<SnakePart> body;

    private final int UP=0;
    private final int LEFT=1;
    private final int DOWN=2;
    private final int RIGHT=3;
    private int growth;
    //Help to organize one turn
    private boolean wasTurn;
    private boolean wouldGrow;
    private int[][] field;

    private int direction=2;

    public Snake(int[][] field){

        this.growth=Options.getLength();
        this.body=new LinkedList<SnakePart>();
        this.wasTurn=false;
        this.wouldGrow=false;
        this.field=field;
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

    public void move(){

        if(!wouldGrow) {
            eraseTrace();
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

    public void growSnake(){
        if(growth+1<Options.getMaxLength()) {
            growth++;
            wouldGrow = true;
            body.add(new SnakePart(getQueue().getX(), getQueue().getY()));
        }
    }

    public void track(){
        field[getHead().getY()][getHead().getX()]=1;
    }

    private void eraseTrace(){
        field[getQueue().getY()][getQueue().getX()]=0;
    }
}
