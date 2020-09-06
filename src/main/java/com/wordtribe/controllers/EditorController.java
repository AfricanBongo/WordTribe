package com.wordtribe.controllers;

import com.wordtribe.customcontrols.DisplayMessages;
import com.wordtribe.customcontrols.RecentFileMenuItem;
import com.wordtribe.customcontrols.RecentFileMenuItemList;
import com.wordtribe.customcontrols.TextEditorTab;
import com.wordtribe.data.OpenedPaths;
import com.wordtribe.data.TimedPath;
import com.wordtribe.data.file.TextFileData;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Function;

public class EditorController {

    @FXML private BorderPane textEditorBorderPane;
    @FXML private Menu openRecentMenu;
    @FXML private Label caretPositionLabel;
    @FXML private TabPane editorTabPane;

    // Use to display messages to user
    private DisplayMessages displayMessages = new DisplayMessages();

    @FXML
    public void initialize() {
        // If a RecentFileMenuItem is clicked it should open a new Tab
        RecentFileMenuItemList.getMenuItemList().getMenuItems().forEach(recentFileMenuItem -> {
            recentFileMenuItem.setOnAction(event -> addTabFromRecents(recentFileMenuItem));
        });

        // Functionality to menu items upon change
        RecentFileMenuItemList.getMenuItemList().getMenuItems().addListener(new ListChangeListener<RecentFileMenuItem>() {
            @Override
            public void onChanged(Change<? extends RecentFileMenuItem> c) {
                // Add on click action to new items added to the list
                while (c.next()) {
                    if (c.wasAdded()) {
                        c.getAddedSubList().forEach(recentFileMenuItem ->
                                recentFileMenuItem.setOnAction(event -> addTabFromRecents(recentFileMenuItem)));
                    }
                }
            }
        });

        // Populate recent file menu
        // Get paths which aren't open
        openRecentMenu.getItems().setAll(RecentFileMenuItemList.getMenuItemList()
                                                                .getMenuItems()
                                                                .filtered(recentFileMenuItem ->
                                                                        !recentFileMenuItem.getTimedPath().isLastOpened()));

        // Set the Stage Title to always be the title of the selected Tab
        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!editorTabPane.getTabs().isEmpty()) {
                LoadStages.getLoader().getCurrentStage().setTitle(newValue.getText());
            } else {
                LoadStages.getLoader().getCurrentStage().setTitle("Word Tribe");
                caretPositionLabel.setText("");
            }
        });
    }


    // When a file is being opened from the disk
    @FXML
    public void openFile() {
        // Load File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        // Get file given by user and create a TimedPath
        Path path = fileChooser.showOpenDialog(LoadStages.getLoader().getCurrentStage()).toPath();

        // Check if the path already exists within the app
        TimedPath timedPath = OpenedPaths.getOpenedPaths().checkTimedPath(path);

        if (timedPath != null) {
            // Check if timed path already has a menu item
            RecentFileMenuItem recentFileMenuItem = RecentFileMenuItemList
                    .getMenuItemList()
                    .checkMenuItem(timedPath);

            if (recentFileMenuItem != null) {
                // Check if the menu item is already open as a tab
                for (Tab tab : editorTabPane.getTabs()) {
                    if (((TextEditorTab) tab).getOpenFile().getFileTimedPath().equals(timedPath)) {
                        // Focus tab if path already exists as tab
                        editorTabPane.getSelectionModel().select(tab);
                        return;
                    }
                }

                // Open new tab if one isn't open for this menu item
                addTabFromRecents(recentFileMenuItem);

            } else {
                // Create new menu item and open as Tab;
                addTabFromRecents(RecentFileMenuItemList.getMenuItemList().addNewMenuItem(timedPath));
            }
        } else {
            // Create new timed path, add to OpenedPaths list and open as Tab
            timedPath = new TimedPath(path.toString());
            OpenedPaths.getOpenedPaths().add(timedPath);
            addTabFromRecents(RecentFileMenuItemList.getMenuItemList().addNewMenuItem(timedPath));
        }
    }

    // Opens a new tab in the tab pane
    public void addTab(TimedPath timedPath) {

    }


    // Code run for files opened from the recents menu
    public void addTabFromRecents(RecentFileMenuItem recentFileMenuItem) {
        openRecentMenu.getItems().remove(recentFileMenuItem);
        recentFileMenuItem.getTimedPath().setLastOpened(true);
        addTab(recentFileMenuItem.getTimedPath());

        Platform.runLater(() -> {
            // Create new data instance to read data of the file
            TextFileData textFileData = new TextFileData(recentFileMenuItem.getTimedPath());

            // Load data into memory
            try {
                textFileData.load();
            } catch (IOException | ClassNotFoundException e) {
                displayMessages.showError("Couldn't load file from disk, please try again");
            }

            // Produce string used to specifically identity the tab
            Function<String, String> produceUnchangingName = filename -> {
                char[] name = filename.substring(0, filename.indexOf(".")).toCharArray();
                StringBuilder unchangingName = new StringBuilder();
                for (char Char : name) {
                    unchangingName.append(Char + 3);
                }

                return unchangingName.toString();
            };

            // Create and configure new code area for the tab
            CodeArea codeArea = new CodeArea(textFileData.getInfo());
            codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

            // Bind file data text with code area text property
            textFileData.textProperty().bind(codeArea.textProperty());
            TextEditorTab textEditorTab = new TextEditorTab(textFileData, codeArea,
                    produceUnchangingName.apply(recentFileMenuItem.getTimedPath().getPath().getFileName().toString()));


            textEditorTab.setClosable(true);

            // Update menu item and timed path when the tab is closed
            textEditorTab.setOnClosed(e -> {
                recentFileMenuItem.getTimedPath().setLastOpened(false);
                openRecentMenu.getItems().add(recentFileMenuItem);
                recentFileMenuItem.getTimedPath().updateTimeModified();
                // Sort items when the menu item is added to the list
                openRecentMenu.getItems().sort(new Comparator<MenuItem>() {
                    @Override
                    public int compare(MenuItem menuItem, MenuItem t1) {
                        return ((RecentFileMenuItem) menuItem).compareTo((RecentFileMenuItem) t1);
                    }
                });
            });

            codeArea.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
                caretPositionLabel.setText("Line: " + codeArea.getCurrentParagraph() + " Char: "+ codeArea.getCaretColumn());
            });

            // Create new tab and try to add it to set
            editorTabPane.getTabs().add(textEditorTab);
            editorTabPane.getSelectionModel().select(textEditorTab);
        });
    }


}
