package com.epam.java.javafx.snake.java.model;

import com.epam.java.javafx.snake.java.model.impl.IMoveSnake;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Aleksandr_Vaniukov on 12/6/2016.
 */
public class MoveSnake implements IMoveSnake {
    public void move(Snake snake, int[][] field){
        if(!snake.getWouldGrow()) {
            snake.eraseTrace(field);
        }

        LinkedList<SnakePart> body=snake.getBody();

        for(int i=body.size()-1, j=body.size()-2;i>0;i--,j--){
            body.get(i).setX(body.get(j).getX());
            body.get(i).setY(body.get(j).getY());
        }

        SnakePart head=body.get(0);

        switch (snake.getDirection()){
            case Constants.UP: head.setY(head.getY()-1); break;
            case Constants.LEFT: head.setX(head.getX()-1); break;
            case Constants.DOWN: head.setY(head.getY()+1); break;
            case Constants.RIGHT: head.setX(head.getX()+1); break;
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

        snake.setWasTurn(false);
        snake.setWouldGrow(false);
    }
}
