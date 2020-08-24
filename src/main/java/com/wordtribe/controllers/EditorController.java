package com.wordtribe.controllers;

import com.wordtribe.customcontrols.DisplayMessages;
import com.wordtribe.customcontrols.TextEditorTab;
import com.wordtribe.data.OpenedPaths;
import com.wordtribe.data.TimedPath;
import com.wordtribe.data.file.TextFileData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.fxmisc.richtext.CodeArea;

import java.io.IOException;
import java.util.function.Function;

public class EditorController {

    @FXML private Menu openRecentMenu;

    // Use to display messages to user
    private DisplayMessages displayMessages = new DisplayMessages();

    // Store open tabs
    private ObservableSet<TextEditorTab> openTabs = FXCollections.observableSet();


    @FXML
    public void initialize() {
        // Populate recent file menu with only last 15 viewed files
        for (int i = 0; i < 15; i++) {
            TimedPath timedPath = OpenedPaths.getOpenedPaths().get(i);
            openRecentMenu.getItems().add(new MenuItem(timedPath.getPath().toString()));
        }

        /*
        Write out predicates to use for Recent Files
         */

    }

    @FXML
    public void openFromRecent(ActionEvent actionEvent) {

    }
    public void addTab(TimedPath timedPath) {
        // Create new data instance to read data of the file
        TextFileData textFileData = new TextFileData(timedPath);

        // Load data into memory
        try {
            textFileData.load();
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = displayMessages.showError("Couldn't load file from disk, please try again");
            alert.initOwner(LoadStages.getLoader().getCurrentStage());
        }

        // String used to specifically identity the tab
        Function<String, String> produceUnchangingName = filename -> {
            char[] name = filename.substring(0, filename.indexOf(".")).toCharArray();
            StringBuilder unchangingName = new StringBuilder();
            for (char Char: name) {
                unchangingName.append(Char + 3);
            }

            return unchangingName.toString();
        };

        // Create and configure new code area for the tab
        CodeArea codeArea = new CodeArea();
        codeArea.replaceText(0, 0, textFileData.getInfo());

        // Bind file data text with code area text property
        textFileData.textProperty().bind(codeArea.textProperty());

        // Create new tab and try to add it to set
        openTabs.add(new TextEditorTab(textFileData, codeArea,
                produceUnchangingName.apply(timedPath.getPath().getFileName().toString())));
    }

}
