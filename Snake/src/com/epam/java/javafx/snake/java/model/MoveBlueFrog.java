package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.controller.Game;
import com.epam.java.javafx.snake.java.model.impl.IMoveFrog;

/**
 * Created by Aleksandr_Vaniukov on 12/6/2016.
 */
public class MoveBlueFrog implements IMoveFrog {

    private Game game;

    public MoveBlueFrog(Game game){
        this.game=game;
    }

    public void move(Frog frog, Snake snake, int[][] field){
        int x=frog.getX();
        int y=frog.getY();

        SnakePart head=snake.getHead();

        int diffXdirection=Math.abs(frog.getX()-head.getX());
        int diffYdirection=Math.abs(frog.getY()-head.getY());

        int diffXthrowBorder=Options.getCol()-diffXdirection;
        int diffYthrowBorder=Options.getRow()-diffYdirection;

        if(Constants.UP==snake.getDirection() || Constants.DOWN==snake.getDirection()){
            if(diffYdirection>diffYthrowBorder){
                if(frog.getY()-head.getY()>0){
                    y++;
                }
                else{
                    y--;
                }
            }
            else{
                if(frog.getY()-head.getY()>0){
                    y--;
                }
                else{
                    y++;
                }
            }
        }

        if(Constants.LEFT==snake.getDirection() || Constants.RIGHT==snake.getDirection()){
            if(diffXdirection>diffXthrowBorder){
                if(frog.getX()-head.getX()>0){
                    x++;
                }
                else{
                    x--;
                }
            }
            else{
                if(frog.getX()-head.getX()>0){
                    x--;
                }
                else{
                    x++;
                }
            }
        }

        if(x<0){
            x=Options.getCol()-1;
        }

        if(x>Options.getCol()-1){
            x=0;
        }

        if(y<0){
            y=Options.getRow()-1;
        }

        if(y>Options.getRow()-1){
            y=0;
        }

        if(canMove(x,y,field)) {
            eraseTrace(frog, field);
            frog.setX(x);
            frog.setY(y);
            track(frog, field);
        }
    }

    private boolean canMove(int x, int y, int[][] field){
        return field[x][y]==Constants.CLEAR_CELL;
    }

    private void eraseTrace(Frog frog, int[][]field){
        field[frog.getX()][frog.getY()]=Constants.CLEAR_CELL;
        game.getObserver().update((BlueFrog) frog,frog.getX(),frog.getY(),Constants.CLEAR_CELL);
    }

    private void track(Frog frog, int[][]field){
        field[frog.getX()][frog.getY()]=Constants.FROG_INTO_CELL;
        game.getObserver().update((BlueFrog) frog,frog.getX(),frog.getY(),Constants.FILL_CELL);
    }
}
