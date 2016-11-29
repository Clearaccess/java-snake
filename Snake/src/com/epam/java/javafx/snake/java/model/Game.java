package com.epam.java.javafx.snake.java.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Game{
    private Snake snake;
    private LinkedList<Frog>frogs;
    //0 - cell is empty;
    //1 - there is a part of snake on the cell;
    //2 - there is a frog on the cell;
    //Hello git
    private int[][] field;

    public Game(){
        this.frogs=placeForgs();
        this.field=new int[Options.getRow()][Options.getCol()];
        this.snake=new Snake(field);
    }

    public Snake getSnake(){
        return snake;
    }

    public void update(long currentTick){
        if(currentTick==Options.getSpeed())
        {
            snake.move();
            if(isFrogOnCell(snake.getBody().get(0).getX(),snake.getBody().get(0).getY())){
                snake.growSnake();
                field[snake.getBody().get(0).getX()][snake.getBody().get(0).getY()]=1;
                addFrog();
            }
        }
    }

    private boolean isFrogOnCell(int x,int y){
        return field[x][y]==2;
    }

    private LinkedList<Frog> placeForgs(){
        int i=0;
        LinkedList<Frog>frogs=new LinkedList<Frog>();
        while(i<Options.getFrog()){
            frogs.add(placeFrog());
            i++;
        }
        return frogs;
    }

    private void addFrog(){
        this.frogs.add(placeFrog());
    }

    private Frog placeFrog(){
        return new RedFrog(0,0);
    }
}
