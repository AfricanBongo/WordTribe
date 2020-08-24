package com.wordtribe.controllers;

import com.wordtribe.Logger.WordInkLogger;
import com.wordtribe.data.OpenedPaths;
import com.wordtribe.data.TimedPath;
import com.wordtribe.data.file.OpenFiles;
import com.wordtribe.data.file.TextFileData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class SplashController {

    @FXML private ProgressBar splashProgressBar;
    private static Task<Void> checkOpenFiles;

    // Create thread in the initialize block of the Controller
    @FXML
    public void initialize() {

        // Check if there are file that where left open from the previous activity
        checkOpenFiles = new Task<>() {
            @Override
            protected Void call() {
                // Determine thread sleep time
                int threadSleepTime;
                if (OpenedPaths.getOpenedPaths().size() < 100) {
                    threadSleepTime = 100;
                } else {
                    threadSleepTime = 25;
                }

                // Loop through path list
                for (TimedPath timedPath : OpenedPaths.getOpenedPaths().getTimedPaths()) {

                    // For now allow only text files to opened in editor
                    if (timedPath.isLastOpened()) {
                        TextFileData textFileData = new TextFileData(timedPath);
                        OpenFiles.getOpenFiles().getFileList().add(textFileData);
                    }

                    // Update progress and sleep the thread to simulate the loading of the app
                    updateProgress(OpenedPaths.getOpenedPaths().indexOf(timedPath), OpenedPaths.getOpenedPaths().size());
                    try {
                        Thread.sleep(threadSleepTime);
                    } catch (InterruptedException e) {
                        WordInkLogger.getLogger().severe("Sleeping thread in splash screen was interrupted");
                        e.printStackTrace();
                    }
                }

                LoadStages.getLoader().setLoadEditor(true);

                return null;
            }
        };

        // Bind the progressBar to the checkOpenFiles task
        splashProgressBar.progressProperty().bind(checkOpenFiles.progressProperty());

        checkOpenFiles.setOnSucceeded(e -> Platform.runLater(() -> {
            // Load editor when the splash screen closes
            LoadStages.getLoader().loadEditor();

        }));

        // Launch the checkOpenFiles thread
        new Thread(checkOpenFiles).start();
    }
}
