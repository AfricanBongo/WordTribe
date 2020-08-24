package com.wordtribe.data.file;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Used to organise the files open in the Text Editor
public class OpenFiles {
    private ObservableList<AbstractFileData> fileList;
    private static OpenFiles openFiles = new OpenFiles();

    private OpenFiles() {
        fileList = FXCollections.observableArrayList();
    }

    public static OpenFiles getOpenFiles() {
        return openFiles;
    }

    public ObservableList<AbstractFileData> getFileList() {
        return fileList;
    }
}
