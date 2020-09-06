package com.wordtribe.customcontrols;

import com.wordtribe.data.file.AbstractFileData;
import javafx.scene.control.Tab;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.util.Comparator;

/*
 Custom Tab class used to populate the tab pane of the text editor
 */
public class TextEditorTab extends Tab implements Comparator<TextEditorTab> {
    // The file data being displayed within the tab
    private AbstractFileData openFile;
    // Used to make sure a file is not open twice
    private final String unchangingName;

    private CodeArea codeArea;

    public TextEditorTab(AbstractFileData openFile, CodeArea codeArea, String unchangingName) {
        // Set the name of the tab to be the file name of the open file
        super(openFile.getFileTimedPath().getPath().getFileName().toString(), new VirtualizedScrollPane<>(codeArea));
        this.openFile = openFile;
        this.unchangingName = unchangingName;
        this.codeArea = codeArea;
    }

    public AbstractFileData getOpenFile() {
        return openFile;
    }

    // Might be used when the file is saved as a new file
    public void setOpenFile(AbstractFileData openFile) {
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
