package com.wordtribe.Controllers;

import com.wordtribe.AppStart;
import com.wordtribe.Data.PathHandlers.OpenedTimedPaths;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SplashController {

    @FXML private AnchorPane splashAnchorPane;
    @FXML private ProgressBar splashProgressBar;

    // Values to be used when dragging the stage
    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    public void initialize() {

        // Load recently opened filename and paths into memory
        OpenedTimedPaths.getInstance().loadPaths();

        /*
        Allow the window to be draggable.
        Assign an event handler when the mouse is pressed on the window,
        Also for when the mouse is dragged with the window.
         */
        splashAnchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        splashAnchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                splashAnchorPane.getScene().getWindow().setX(event.getScreenX() - xOffset);
                splashAnchorPane.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });


        // Thread that starts progress bar animation and launches to next window
        new Thread() {
            @Override
            public void run() {
                // Splash progress bar animation

                double splashProgress = 0;

                while(splashProgressBar.getProgress() <= 1) {
                    splashProgress = splashProgress + (Math.random() * 0.1);

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        AppStart.wordInkLogger.getLogger().severe("SplashThreadSleep was interrupted... " + e.getMessage());
                    }
                    splashProgressBar.setProgress(splashProgress);
                }

                // Display the home screen
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HomeScreenController.displayMe();
                    }
                });
            }
        }.start();

    }
}
