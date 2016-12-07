package com.epam.java.javafx.snake.java.controller;

import com.epam.java.javafx.snake.java.model.*;
import com.epam.java.javafx.snake.java.view.impl.IObsever;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Game{

    private static int[][] field;
    private static Lock lock=new ReentrantLock();
    private static Game game;
    private boolean gameOver;
    private boolean pause;
    private int score;
    private Snake snake;
    private Thread threadSnake;
    private LinkedList<Frog>frogs;
    private LinkedList<Thread> threadsFrogs;
    private Random number;
    private GenerateFrogs generateFrogs;
    private IObsever obsever;


    private Game(){

        this.generateFrogs=new GenerateFrogs();
        this.score=0;
        this.field=new int[Options.getCol()][Options.getRow()];
        this.number=new Random(Calendar.getInstance().getTimeInMillis());
        this.snake=new Snake();
        fillFieldSnake();
        this.frogs=placeFrogs();
        fillFieldFrogs();

    }

    public synchronized static Game getGame(){

        if (game==null) {
            game=new Game();
        }

        return game;
    }

    public boolean isPause(){
        return pause;
    }

    public boolean getGameOver(){
        return gameOver;
    }

    public void setGameOver(){
        gameOver=true;
    }

    public Snake getSnake(){
        return this.snake;
    }

    public LinkedList<Thread> getThreads(){
        return threadsFrogs;
    }

    public LinkedList<Frog> getFrogs(){
        return frogs;
    }

    public int getScore(){
        return this.score;
    }

    public void increaseScore(int value){
        this.score+=value;
    }

    public int[][] takeField(){
        while(!Game.lock.tryLock()){

        }
        //Game.lock.lock();
        return this.field;
    }

    public void leaveField(){
        Game.lock.unlock();
    }

    public Frog getFrog(int x,int y){

        for(Frog i:frogs){
            if(i.getX()==x && i.getY()==y)
            {
                return i;
            }
        }

        return null;
    }

    public void startGame(){

        threadSnake=new Thread(snake);
        threadsFrogs=new LinkedList<Thread>();
        for(int i=0;i<frogs.size();i++){
            threadsFrogs.add(new Thread(frogs.get(i)));
        }

        Running.running=true;
        threadSnake.start();
        for(int i=0;i<frogs.size();i++){
            threadsFrogs.get(i).start();
        }
    }

    public void pauseGame(){
        Running.pause=!Running.pause;
        pause=!pause;
    }

    public void stopGame(){
        Running.running=!Running.running;
    }

    public void addObserver(IObsever obsever){
        this.obsever=obsever;
    }

    public IObsever getObserver(){
        return obsever;
    }

    public void removeFrog(Frog frog){
        generateFrogs.decrementNumFrog(frog);
        int index=this.frogs.indexOf(frog);
        threadsFrogs.get(index).interrupt();
        threadsFrogs.remove(index);
        frog.kill();
        frogs.remove(index);
        addFrog();
        threadsFrogs.get(threadsFrogs.size()-1).start();
    }

    private LinkedList<Frog> placeFrogs(){
        int i=0;
        LinkedList<Frog>frogs=new LinkedList<Frog>();
        while(i<Options.getNumFrog()){
            frogs.add(placeFrog());
            i++;
        }
        return frogs;
    }

    private void addFrog(){
        Frog newFrog=placeFrog();
        frogs.add(newFrog);
        threadsFrogs.add(new Thread(newFrog));
    }

    private Frog placeFrog(){
        int x=0;
        int y=0;
        do{
            x=number.nextInt(Options.getCol());
            y=number.nextInt(Options.getRow());
        }
        while(field[x][y]!=Constants.EMPTY_CELL);

        field[x][y]=Constants.FROG_INTO_CELL;

        return generateFrogs.getFrog(x,y);
    }

    private void fillFieldFrogs(){
        for(Frog i:frogs){
            field[i.getX()][i.getY()]=Constants.FROG_INTO_CELL;
        }
    }

    private void fillFieldSnake(){
        for(SnakePart i:snake.getBody()){
            field[i.getX()][i.getY()]=Constants.SNAKE_INTO_CELL;
        }
    }

}
