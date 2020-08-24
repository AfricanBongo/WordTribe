package com.wordtribe.data.file;

import com.wordtribe.data.TimedPath;
import com.wordtribe.data.loadable.implementations.LoadTextFile;
import com.wordtribe.data.savable.implementations.SaveTextFile;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/*
Class that extends AbstractFileData, used to handle text files displayed in the text editor
StringProperty instance is used to allow text data to be observable
 */
public class TextFileData extends AbstractFileData{

    private StringProperty text;

    public TextFileData(TimedPath filePath) {
        super(filePath, new LoadTextFile(), new SaveTextFile());
    }

    @Override
    public void save() throws IOException {
        getSavable().save(getFileTimedPath().getPath(), text.getValue());
    }

    @Override
    public void load() throws IOException, ClassNotFoundException{
        text.setValue((String) getLoadable().load(getFileTimedPath().getPath()));
    }

    public StringProperty textProperty() {
        return text;
    }

    @Override
    public void setInfo(String string) {
        text.setValue(string);
    }

    @Override
    public String getInfo() {
        return text.get();
    }
}
