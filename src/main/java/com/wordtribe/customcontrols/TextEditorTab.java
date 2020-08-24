package com.wordtribe.customcontrols;

import com.wordtribe.data.file.AbstractFileData;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;

import java.util.Comparator;

/*
 Custom Tab class used to populate the tab pane of the text editor
 */
public class TextEditorTab<T> extends Tab implements Comparator<TextEditorTab> {
    // The file data being displayed within the tab
    private AbstractFileData<T> openFile;
    // Used to make sure a file is not open twice
    private final String unchangingName;

    private CodeArea codeArea;

    public TextEditorTab(AbstractFileData<T> openFile, CodeArea codeArea, String unchangingName) {
        // Set the name of the tab to be the file name of the open file
        super(openFile.getFileTimedPath().getPath().getFileName().toString(), codeArea);
        this.openFile = openFile;
        this.unchangingName = unchangingName;
        this.codeArea = codeArea;
    }

    public AbstractFileData<T> getOpenFile() {
        return openFile;
    }

    // Might be used when the file is saved as a new file
    public void setOpenFile(AbstractFileData<T> openFile) {
        this.openFile = openFile;
    }

    public String getUnchangingName() {
        return unchangingName;
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    @Override
    public int compare(TextEditorTab textEditorTab, TextEditorTab t1) {
        return textEditorTab.getUnchangingName().compareTo(t1.getUnchangingName());
    }
}
