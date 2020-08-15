package com.wordtribe.Data.PathHandlers;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TimedPath implements Serializable {
    private LocalDateTime timeModified;
    private String path;  // Save path as string, as paths cannot be written using streams
    private boolean lastOpened = false;


    public TimedPath(String path) {
        this.path = path;
        timeModified = LocalDateTime.now();
    }

    public TimedPath(String path, boolean lastOpened) {
        this(path);
        this.lastOpened = lastOpened;
    }

    public LocalDateTime getTimeModified() {
        return timeModified;
    }

    public Path getPath() {
        return Paths.get(path);
    }

    public void updateTimeModified() {
        this.timeModified = LocalDateTime.now();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isLastOpened() {
        return lastOpened;
    }

    public void setLastOpened(boolean lastOpened) {
        this.lastOpened = lastOpened;
    }

}
