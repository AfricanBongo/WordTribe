package com.wordtribe.data;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


/*
A TimedPath is an object that has a String name object, time object, path, and boolean instance.
It can be saved to memory, used to access a file in the local disk
 */
public class TimedPath implements Serializable, Comparable<TimedPath> {
    private String path;  // Save path as string, as paths cannot be written using streams
    private LocalDateTime timeModified;
    private boolean lastOpened;


    public TimedPath(Path path) {
        this.path = path.toString();
        timeModified = LocalDateTime.now();
    }

    public TimedPath(Path path, boolean lastOpened) {
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

    @Override
    public int compareTo(TimedPath timedPath) {
        return timedPath.getTimeModified().compareTo(this.getTimeModified());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof TimedPath) {
            return this.getPath().equals(((TimedPath) obj).getPath());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.getPath().hashCode();
    }
}
