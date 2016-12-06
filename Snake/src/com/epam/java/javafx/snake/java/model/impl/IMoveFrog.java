package com.epam.java.javafx.snake.java.model.impl;

import com.epam.java.javafx.snake.java.model.Frog;
import com.epam.java.javafx.snake.java.model.Snake;

/**
 * Created by Aleksandr_Vaniukov on 12/6/2016.
 */
public interface IMoveFrog {
    void move(Frog frog, Snake snake, int[][] field);
}
