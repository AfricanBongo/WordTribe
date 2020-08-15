package com.wordtribe.Controllers;

import com.wordtribe.AppStart;
import com.wordtribe.Data.PathHandlers.OpenedTimedPaths;
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

        // Allow the splash screen to be draggable
        splashAnchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        new Thread() {
            @Override
            public void run() {
                // Splash progress bar animation

                double splashProgress = 0;

                while(splashProgressBar.getProgress() != 1) {
                    splashProgress = splashProgress + (Math.random() * 0.1);

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        AppStart.wordInkLogger.getLogger().severe("SplashThreadSleep was interrupted... " + e.getMessage());
                    }
                    splashProgressBar.setProgress(splashProgress);
                }

                // Display the home screen

            }
        }.start();


    }
}
