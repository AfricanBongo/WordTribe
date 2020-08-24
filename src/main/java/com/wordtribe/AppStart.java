package com.wordtribe;

import com.wordtribe.Logger.WordInkLogger;
import com.wordtribe.controllers.LoadStages;
import com.wordtribe.customcontrols.DisplayMessages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class AppStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private DisplayMessages displayMessages = new DisplayMessages();


    @Override
    public void start(Stage primaryStage) {
        Pane pane;

        // Load splashscreen fxml from disk
        try {
            pane = FXMLLoader.load(getClass().getResource(".." + File.separator +".." + File.separator +
                    "fxmls"+ File.separator +"splashscreen.fxml"));

            // Configure the main scene and open splash screen
            Scene main = new Scene(pane);
            main.setFill(Color.TRANSPARENT);
            LoadStages.getLoader().setMain(main);

            // Configure stage
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(main);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            WordInkLogger.getLogger().severe("Couldn't load the splash screen");
            e.printStackTrace();

            // Display an error message and close the app
            displayMessages.showError("Couldn't start the app properly, please restart the app\n" +
                    "If the problem persists please report to the developer");
            primaryStage.close();
        }


    }

}
