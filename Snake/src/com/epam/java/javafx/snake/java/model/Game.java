package com.epam.java.javafx.snake.java.model;

import javafx.scene.canvas.GraphicsContext;
import sun.util.resources.CalendarData;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.LinkedList;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Game{
    private Snake snake;
    private LinkedList<Frog>frogs;
    private int score;
    //0 - cell is empty;
    //1 - there is a part of snake on the cell;
    //2 - there is a frog on the cell;

    private int[][] field;
    private Random number;

    public Game(){
        this.score=0;
        this.field=new int[Options.getRow()][Options.getCol()];
        this.number=new Random(Calendar.getInstance().getTimeInMillis());
        this.snake=new Snake(field);
        fillFieldSnake();
        this.frogs=placeForgs();
        fillFieldFrogs();

    }

    public Snake getSnake(){
        return snake;
    }
    public int getScore(){
        return score;
    }

    public LinkedList<Frog> getFrogs(){
        return frogs;
    }

    public void update(long currentTick){
        if(currentTick==Options.getSpeed())
        {
            snake.move();
            if(ateItself()){
                //Game over
                System.out.println("Snake ate himself");
            }

            if(isFrogOnCell(snake.getHead().getX(),snake.getHead().getY())){
                snake.growSnake();
                removeFrog(snake.getHead().getX(),snake.getHead().getY());
                snake.track();
                addFrog();
                score++;
            }
            snake.track();

            for(int i=0;i<field.length;i++){
                for(int j=0;j<field.length;j++){
                    System.out.print(field[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------");
        }
    }

    private boolean ateItself(){
        SnakePart head=snake.getHead();
        LinkedList<SnakePart>body=snake.getBody();
        for(int i=1;i<snake.getBody().size();i++){
            if((body.get(i).getX()==head.getX())
                    &&
                    (body.get(i).getY()==head.getY())){
                return true;
            }
        }
        return false;
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
        Frog newFrog=placeFrog();
        this.frogs.add(newFrog);
    }

    private void removeFrog(int x, int y){
        field[x][y]=0;
        for(Frog i: frogs){
            if(i.getX()==x
                    &&
                    i.getY()==y){
                frogs.remove(i);
                System.out.println("Delete frog success");
                break;
            }
        }
    }

    private Frog placeFrog(){
        int x=0;
        int y=0;
        do{
            x=number.nextInt(Options.getRow());
            y=number.nextInt(Options.getCol());
        }
        while(field[x][y]!=0);

        field[x][y]=2;
        return new RedFrog(x,y);
    }

    private void fillFieldSnake(){
        for(SnakePart i:snake.getBody()){
            field[i.getX()][i.getY()]=1;
        }
    }

    private void fillFieldFrogs(){
        for(Frog i:frogs){
            field[i.getX()][i.getY()]=2;
        }
    }

}
