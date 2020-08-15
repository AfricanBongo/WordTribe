package com.wordtribe;

import com.wordtribe.Loggers.WordInkLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scenecontroller.SceneController;

import java.io.File;
import java.io.IOException;

public class AppStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static SceneController sceneController;

    public static SceneController getSceneController() {
        return sceneController;
    }

    public static WordInkLogger wordInkLogger = new WordInkLogger("main");

    @Override
    public void start(Stage primaryStage) {

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // Add home screen pane
        try {
            Pane pane =  FXMLLoader.load(getClass().getResource(".." + File.separator +".." + File.separator +
                    "fxmls"+ File.separator +"splashscreen.fxml"));

            // Create main scene and load to scene controller
            Scene main = new Scene(pane);
            main.setFill(Color.TRANSPARENT);

            // Launch app
            primaryStage.setResizable(false);
            primaryStage.setScene(main);
            primaryStage.show();


        } catch (IOException e){
            wordInkLogger.getLogger().severe("Error loading splash screen... " + e.getMessage());
        }
    }

}
