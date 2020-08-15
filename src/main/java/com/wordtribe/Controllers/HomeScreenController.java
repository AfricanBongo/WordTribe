package com.wordtribe.Controllers;

import com.wordtribe.AppStart;
import com.wordtribe.Data.PathHandlers.OpenedTimedPaths;
import com.wordtribe.Data.PathHandlers.TimedPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;

public class HomeScreenController {

    @FXML private ListView<TimedPath> homeListView;
    @FXML private Button openFileButton;
    @FXML private Button newFileButton;

    @FXML
    public void initialize() {

        // Set the homeListView items to the OpenedTimedPaths
        homeListView.setItems(OpenedTimedPaths.getInstance().getOpenedPaths().getTimedPaths());

        // Set how TimedPaths are displayed in the listview
        homeListView.setCellFactory(new Callback<ListView<TimedPath>, ListCell<TimedPath>>() {
            @Override
            public ListCell<TimedPath> call(ListView<TimedPath> param) {
                ListCell<TimedPath> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(TimedPath item, boolean empty) {
                        super.updateItem(item, empty);

                        if (isEmpty()) {
                            setText(null);
                        } else {
                            setText(item.getPath().getFileName() + "\n" + item.getPath().toString());
                        }
                    }
                };

                return cell;
            }
        });

        homeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    // Used to load this screen from other controllers/methods
    public static void displayMe() {

        try {

            // Add home screen to the scene controller
            AppStart.getSceneController().addScreen("home", FXMLLoader.load(AppStart.class.getResource(".." + File.separator +".." + File.separator +
                    "fxmls"+ File.separator +"homescreen.fxml")));

            // Set new stage
            Stage homeStage = new Stage();

            // Close stage being currently used
            ((Stage) AppStart.getSceneController().getMainScene().getWindow()).close();

            homeStage.setScene(AppStart.getSceneController().getMainScene());

            // Load home screen
            AppStart.getSceneController().displayPane("home");

            // Show new stage
            homeStage.show();

        } catch (IOException e){
            AppStart.wordInkLogger.getLogger().severe("Couldn't load homescreen.fxml");
            System.out.println("Error!!!");
        }

    }
}
