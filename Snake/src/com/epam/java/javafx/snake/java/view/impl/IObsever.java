package com.epam.java.javafx.snake.java.view.impl;

import com.epam.java.javafx.snake.java.model.BlueFrog;
import com.epam.java.javafx.snake.java.model.GreenFrog;
import com.epam.java.javafx.snake.java.model.RedFrog;
import com.epam.java.javafx.snake.java.model.Snake;

/**
 * Created by Aleksandr_Vaniukov on 12/7/2016.
 */
public interface IObsever {

    void update(Snake snake, int x, int y, int state);
    void update(GreenFrog frog, int x, int y, int state);
    void update(RedFrog frog, int x, int y, int state);
    void update(BlueFrog frog, int x, int y, int state);
}
