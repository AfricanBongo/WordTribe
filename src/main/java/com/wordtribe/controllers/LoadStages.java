package com.wordtribe.controllers;

import com.wordtribe.AppStart;
import com.wordtribe.Logger.WordInkLogger;
import display.DisplayMessages;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenecontroller.SceneController;

import java.io.File;
import java.io.IOException;

/*
Used to handle the loading of stages of the app
 */
public class LoadStages {
    private static LoadStages loader = new LoadStages();
    private static SceneController sceneController;
    private DisplayMessages displayMessages = new DisplayMessages();

    // Used to check which screen to load
    private boolean loadEditor = false;

    private LoadStages() {}

    public static LoadStages getLoader() {
        return loader;
    }

    public boolean isLoadEditor() {
        return loadEditor;
    }

    public void setLoadEditor(boolean loadEditor) {
        this.loadEditor = loadEditor;
    }

    public Stage getCurrentStage() {
        return ((Stage) sceneController.getMainScene().getWindow());
    }

    // Used only once
    public void setMain(Scene main) {
        sceneController = new SceneController(main);
    }

    // Load the text editor
    public void loadEditor() {
        Platform.runLater(() -> {
            // Close current window open
            getCurrentStage().close();

            // Assign the home fxml to the scene
            try {
                sceneController.addScreen("editor", FXMLLoader.load(AppStart.class.getResource(".." + File.separator +".." + File.separator +
                        "fxmls"+ File.separator +"texteditor.fxml")));
                sceneController.displayPane("editor");
            } catch (IOException e) {
                WordInkLogger.getLogger().severe("Failed to load the text editor..." + e.getMessage());
                e.printStackTrace();
                // Display an error message
                displayMessages.showError("Couldn't start the app properly, please restart the app\n" +
                        "If the problem persists please report to the developer");
                getCurrentStage().close();
            }

            // Configure the stage on which the Text Editor is to displayed on
            Stage textEditorStage = new Stage();
            textEditorStage.setScene(sceneController.getMainScene());
            textEditorStage.setTitle("Word Tribe");
            textEditorStage.show();
        });
    }
}
