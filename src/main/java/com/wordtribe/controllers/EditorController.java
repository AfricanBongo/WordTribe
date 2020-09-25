package com.wordtribe.controllers;

import com.wordtribe.Logger.WordInkLogger;
import com.wordtribe.customcontrols.RecentFileMenuItem;
import com.wordtribe.customcontrols.RecentFileMenuItemList;
import com.wordtribe.customcontrols.TextEditorTab;
import com.wordtribe.data.OpenedPaths;
import com.wordtribe.data.TimedPath;
import com.wordtribe.data.file.TextFileData;
import display.DisplayMessages;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EditorController {

    @FXML private BorderPane textEditorBorderPane;
    @FXML private Menu openRecentMenu;
    @FXML private Label caretPositionLabel;
    @FXML private TabPane editorTabPane;

    @FXML private MenuItem saveItem;
    @FXML private MenuItem saveAsItem;

    // Extensions whose files can be opened using string methods
    private ObservableList<FileChooser.ExtensionFilter> stringExtensionFilters;

    // Use to display messages to user
    private DisplayMessages displayMessages = new DisplayMessages();

    @FXML
    public void initialize() {
        // If a RecentFileMenuItem is clicked it should open a new Tab
        RecentFileMenuItemList.getMenuItemList().getMenuItems().forEach(recentFileMenuItem -> {
            recentFileMenuItem.setOnAction(event -> addTabFromRecents(recentFileMenuItem));

            // Load tabs which weren't closed the last time the app was open
            if (recentFileMenuItem.getTimedPath().isLastOpened()) {
                addTabFromRecents(recentFileMenuItem);
            }
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
                saveItem.setVisible(true);
                saveAsItem.setVisible(true);
            } else {
                // Do not show save and saveAs menu items when no tab is open
                saveItem.setVisible(false);
                saveAsItem.setVisible(false);
                LoadStages.getLoader().getCurrentStage().setTitle("Word Tribe");
                caretPositionLabel.setText("");
            }
        });


        // Do not show save and saveAs menu items when no tab is open when the app is started
        if (editorTabPane.getTabs().isEmpty()) {
            saveItem.setVisible(false);
            saveAsItem.setVisible(false);
        }

        // Extensions able to be opened
        stringExtensionFilters = FXCollections.observableArrayList(
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("Java", "*.java"),
                new FileChooser.ExtensionFilter("Python", "*.py"),
                new FileChooser.ExtensionFilter("Gradle", "*.gradle"),
                new FileChooser.ExtensionFilter("Bat file", "*.bat")
        );

        // All extensions option
        List<String> allExtensions = new ArrayList<>();
        stringExtensionFilters.forEach(extensionFilter -> {
            allExtensions.add(extensionFilter.getExtensions().get(0));
        });

        stringExtensionFilters.add(new FileChooser.ExtensionFilter("All", allExtensions));

    }


    // Used to create a new file
    @FXML
    public void newFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create new file");
        Path path = fileChooser.showSaveDialog(LoadStages.getLoader().getCurrentStage().getOwner()).toPath();

        // Validate the path
        checkOpenTab(path);
    }


    // When a file is being opened from the disk
    @FXML
    public void openFile() {
        // Load File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(stringExtensionFilters);

        // Set the all files extension as it's the last extension to be added
        fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters()
                .get(stringExtensionFilters.size() - 1));

        Path path;
        // Get file given by user and create a TimedPath
        try {
            path = fileChooser.showOpenDialog(LoadStages.getLoader().getCurrentStage()).toPath();
        } catch (NullPointerException exception){
            // Do nothing as no valid file path has been sent back
            return;
        }

        // Validate the path
        checkOpenTab(path);
    }


    // Check if path exists as timed path with an open tab
    public void checkOpenTab(Path path) {
        // Check if the path already exists within the app
        TimedPath timedPath = OpenedPaths.getOpenedPaths().checkTimedPath(path);

        if (timedPath != null) {
            // Focus tab if tab is already open for the file
            for (Tab tab : editorTabPane.getTabs()) {
                if (((TextEditorTab) tab).getOpenFileData().getFileTimedPath().equals(timedPath)) {
                    editorTabPane.getSelectionModel().select(tab);
                    return;
                }
            }

            // Open new tab if tab not already open
            addTabFromRecents(RecentFileMenuItemList.getMenuItemList().checkMenuItem(timedPath));
        } else {
            // Create new timed path, add to OpenedPaths list and open as Tab
            timedPath = new TimedPath(path.toString());
            OpenedPaths.getOpenedPaths().add(timedPath);
            addTabFromRecents(RecentFileMenuItemList.getMenuItemList().addNewMenuItem(timedPath));
        }
    }


    // Opens a new tab in the tab pane
    public void addTab(TimedPath timedPath) {
        // To-do
    }


    // Code run for files opened from the recents menu
    public void addTabFromRecents(RecentFileMenuItem recentFileMenuItem) {

        if (recentFileMenuItem != null) {
            openRecentMenu.getItems().remove(recentFileMenuItem);
            recentFileMenuItem.getTimedPath().setLastOpened(true);

            Platform.runLater(() -> {
                // Create new data instance to read data of the file
                TextFileData textFileData = new TextFileData(recentFileMenuItem.getTimedPath());

                // Load data into memory
                try {
                    textFileData.load();
                } catch (IOException | ClassNotFoundException e) {
                    displayMessages.showError("Couldn't load file from disk, please try again");
                }

                // Create and configure new code area for the tab
                CodeArea codeArea = new CodeArea(textFileData.getInfo());
                codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

                // Create new text editor Tab
                TextEditorTab textEditorTab = new TextEditorTab(textFileData, codeArea, recentFileMenuItem);


                textEditorTab.setClosable(true);

                // Update menu item and timed path when the tab is closed
                textEditorTab.setOnClosed(e -> {
                    updateMenuList(textEditorTab.getRecentFileMenuItem());
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


    // Save current file data
    public void save(TextEditorTab textEditorTab) {
        // Save the current file data
        try {
            textEditorTab.getOpenFileData().save();
        } catch (IOException e) {
            WordInkLogger.getLogger().warning(e.getMessage() + "\n");
            e.printStackTrace();
        }
    }


    // Save method called from UI
    @FXML
    public void save() {
        // Retrieve the selected tab
        TextEditorTab textEditorTab = (TextEditorTab) editorTabPane.getSelectionModel().getSelectedItem();
        save(textEditorTab);
    }


    // Rename file or move file to another location
    @FXML
    public void saveAs() {
        FileChooser fileChooser = new FileChooser();
        Path path = fileChooser.showSaveDialog(LoadStages.getLoader().getCurrentStage().getOwner()).toPath();

        TimedPath timedPath = OpenedPaths.getOpenedPaths().checkTimedPath(path);
        // If the path doesn't already exist in the app then add it
        if (timedPath == null) {
            // Retrieve the selected tab and rename the tab name
            TextEditorTab textEditorTab = (TextEditorTab) editorTabPane.getSelectionModel().getSelectedItem();
            textEditorTab.setText(path.getFileName().toString());

            // Save the current file data
            save(textEditorTab);

            // Close the file path and add its corresponding menu item back to the list
            timedPath = textEditorTab.getOpenFileData().getFileTimedPath();
            updateMenuList(textEditorTab.getRecentFileMenuItem());

            // Create a new timed path and menu item for the new saved file
            // Then link the timed path with the text editor tab and its file data
            timedPath = new TimedPath(path.toString());
            textEditorTab.setRecentFileMenuItem(RecentFileMenuItemList.getMenuItemList().addNewMenuItem(timedPath));
            textEditorTab.setOpenFileData(new TextFileData(timedPath));

            // Save the new file path and its data
            save(textEditorTab);

        } else {
            // Show info alert to user
            displayMessages.showInfo("File error", "The file you're trying to save already exists");
        }
    }

    // Add menu item back to the recent menu
    public void updateMenuList(RecentFileMenuItem recentFileMenuItem) {
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
    }


}
