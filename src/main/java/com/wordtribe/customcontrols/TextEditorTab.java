package com.wordtribe.customcontrols;

import com.wordtribe.data.file.AbstractFileData;
import com.wordtribe.data.file.TextFileData;
import javafx.scene.control.Tab;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

/*
 Custom Tab class used to populate the tab pane of the text editor
 */
public class TextEditorTab extends Tab {
    // The file data being displayed within the tab
    private AbstractFileData openFileData;
    // The menu item associated with the tab
    private RecentFileMenuItem recentFileMenuItem;

    private CodeArea codeArea;

    public TextEditorTab(AbstractFileData openFileData, CodeArea codeArea, RecentFileMenuItem recentFileMenuItem) {
        // Set the name of the tab to be the file name of the open file
        super(openFileData.getFileTimedPath().getPath().getFileName().toString(), new VirtualizedScrollPane<>(codeArea));
        this.openFileData = openFileData;
        this.codeArea = codeArea;
        this.recentFileMenuItem = recentFileMenuItem;

        // Bind the file data text field with the code Area text field
        ((TextFileData) openFileData).textProperty().bind(codeArea.textProperty());
    }

    public RecentFileMenuItem getRecentFileMenuItem() {
        return recentFileMenuItem;
    }

    public void setRecentFileMenuItem(RecentFileMenuItem recentFileMenuItem) {
        this.recentFileMenuItem = recentFileMenuItem;
    }

    public AbstractFileData getOpenFileData() {
        return openFileData;
    }

    // Might be used when the file is saved as a new file
    public void setOpenFileData(AbstractFileData openFileData) {
        this.openFileData = openFileData;
        // Bind the file data text field with the code Area text field
        ((TextFileData) this.openFileData).textProperty().bind(codeArea.textProperty());
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }
}
