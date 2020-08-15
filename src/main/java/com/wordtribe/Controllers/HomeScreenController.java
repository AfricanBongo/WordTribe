package com.wordtribe.Controllers;

import com.wordtribe.Data.PathHandlers.OpenedTimedPaths;
import com.wordtribe.Data.PathHandlers.TimedPath;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

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
}
