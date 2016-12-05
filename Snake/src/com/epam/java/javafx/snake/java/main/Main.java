package com.epam.java.javafx.snake.java.main;

import com.epam.java.javafx.snake.java.model.Constants;
import com.epam.java.javafx.snake.java.model.Options;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/view.fxml"));
        primaryStage.setTitle("Snake");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        if(args.length==5){
            Options.setRow(Integer.parseInt(args[0]));
            Options.setCol(Integer.parseInt(args[1]));
            Options.setLength(Integer.parseInt(args[2]));
            Options.setNumFrog(Integer.parseInt(args[3]));
            Options.setSpeed(Integer.parseInt(args[4]));
        }
        launch(args);
    }
}
