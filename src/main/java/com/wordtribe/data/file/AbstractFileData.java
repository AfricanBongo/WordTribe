package com.wordtribe.data.file;

import com.wordtribe.data.TimedPath;
import com.wordtribe.data.loadable.interfaces.Loadable;
import com.wordtribe.data.savable.interfaces.Savable;

import java.io.IOException;

/**
 * @author africanbongo(Donell Mtabvuri)
 */
/*
Abstract Class used to handle a file data viewed in the text editor
TimedPath instance has path used access file location in the disk
Loadable and Savable interfaces save and load file data from the disk
 */
public abstract class AbstractFileData<T> {
    private TimedPath fileTimedPath;
    private Loadable<T> loadable;
    private Savable<T> savable;

    public AbstractFileData(TimedPath fileTimedPath, Loadable<T> loadable, Savable<T> savable) {
        this.fileTimedPath = fileTimedPath;
        this.loadable = loadable;
        this.savable = savable;
    }

    // Methods to handle data in disk
    abstract public void save() throws IOException;
    abstract public void load() throws IOException, ClassNotFoundException;

    // Methods to handle data in memory
    abstract public void setInfo(String o);
    abstract public String getInfo();


    public TimedPath getFileTimedPath() {
        return fileTimedPath;
    }

    public void setFileTimedPath(TimedPath fileTimedPath) {
        this.fileTimedPath = fileTimedPath;
    }

    public Loadable<T> getLoadable() {
        return loadable;
    }

    public void setLoadable(Loadable<T> loadable) {
        this.loadable = loadable;
    }

    public Savable<T> getSavable() {
        return savable;
    }

    public void setSavable(Savable<T> savable) {
        this.savable = savable;
    }



}
