package com.epam.java.javafx.snake.java.model;

import javafx.scene.canvas.GraphicsContext;
import sun.util.resources.CalendarData;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Aleksandr_Vaniukov on 11/23/2016.
 */
public class Game{
    private Snake snake;
    private Thread threadSnake;
    private LinkedList<Frog>frogs;
    private LinkedList<Thread> threadsFrogs;
    private int score;
    private static Lock lock=new ReentrantLock();
    private Random number;
    //0 - cell is empty;
    //1 - there is a part of snake on the cell;
    //2 - there is a frog on the cell;

    private static int[][] field;
    private static Game game;

    private Game(){
        this.score=0;
        this.field=new int[Options.getRow()][Options.getCol()];
        this.number=new Random(Calendar.getInstance().getTimeInMillis());
        snake=new Snake();
        fillFieldSnake();
        frogs=placeFrogs();
        fillFieldFrogs();
    }

    public synchronized static Game getGame(){

        if (game==null) {
            game=new Game();
        }

        return game;
    }

    public Snake getSnake(){
        return this.snake;
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
        System.out.println("Try lock");
        Game.lock.lock();
        System.out.println("Lock");
        return this.field;
    }

    public void leaveField(){
        System.out.println("UnLock");
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
    }

    public void stopGame(){
        Running.running=!Running.running;
    }


    private LinkedList<Frog> placeFrogs(){
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
        this.threadsFrogs.add(new Thread(newFrog));
    }

    public void removeFrog(){
        for(int i=0;i<threadsFrogs.size();i++){
            if(!threadsFrogs.get(i).isAlive()){
                threadsFrogs.remove(i);
                frogs.remove(i);
                break;
            }
        }

        addFrog();
    }

    private Frog placeFrog(){
        int x=0;
        int y=0;
        do{
            x=number.nextInt(Options.getRow());
            y=number.nextInt(Options.getCol());
        }
        while(field[x][y]!=Constants.EMPTY_CELL);

        field[x][y]=Constants.FROG_INTO_CELL;
        return new RedFrog(x,y);
    }

    private void fillFieldFrogs(){
        for(Frog i:frogs){
            field[i.getX()][i.getY()]=2;
        }
    }
    private void fillFieldSnake(){
        for(SnakePart i:snake.getBody()){
            field[i.getX()][i.getY()]=1;
        }
    }

}
