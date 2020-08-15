package com.wordtribe.Data.PathHandlers;

import com.wordtribe.Data.Interfaces.Loadable;
import com.wordtribe.Data.Interfaces.Saveable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class TimedPathsHandler implements Saveable, Loadable {

    private ObservableList<TimedPath> timedPaths;
    private SortedList<TimedPath> sortedTimedPaths;

    // Initialize the list at first
    public TimedPathsHandler() {

        timedPaths = FXCollections.observableArrayList();

        // Sorted list for each time the timedPaths list is update
        sortedTimedPaths = new SortedList<>(timedPaths, new Comparator<TimedPath>() {
            @Override
            public int compare(TimedPath timedPath, TimedPath t1) {
                return t1.getTimeModified().compareTo(timedPath.getTimeModified());
            }
        });

    }


    // Save paths to disk
    public synchronized void save(Path path) throws IOException {

        // Throw exception if the file doesn't exist
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File doesn't exist in this path");
        }

        // Write TimedPath objects into disk from memory
        try (ObjectOutputStream writePaths = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            for (TimedPath pathRead : timedPaths) {
                writePaths.writeObject(pathRead);
            }
        } catch (IOException e){
            // Log exception
            throw new IOException("Couldn't write paths into memory");
        }

    }


    // Load paths from disk
    public synchronized void load(Path path) throws IOException {

        // Throw exception if the file doesn't exist
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File doesn't exist in this path");
        }

        // Read TimedPath objects from disk to memory
        try (ObjectInputStream readPaths = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            boolean eof = false;

            while(!eof) {
                try {
                    timedPaths.add((TimedPath) (readPaths.readObject()));
                } catch (EOFException e) {
                    eof = true;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Couldn't load paths from disk");
        }

    }


    // Add TimedPath to list
    public void addPath(TimedPath path) {
        timedPaths.add(path);
    }

    // Remove path
    public void removePath(TimedPath timedPath) {
        timedPaths.remove(timedPath);
    }


    // Check if path exists
    public TimedPath checkTimedPath(Path checkPath) {
        for (TimedPath timedPath : timedPaths) {
            if (timedPath.getPath().equals(checkPath)) {
                return timedPath;
            }
        }

        return null;
    }

    // Replace path in list with new path
    public boolean replacePath(Path oldPath, Path newPath) {

        TimedPath checkedPath = checkTimedPath(oldPath);

        // Change path if checkedPath is an actual TimedPath
        if (checkedPath != null) {
            checkedPath.setPath(newPath.toString());
            return true;
        }

        return false;
    }




    // Return the sorted list of TimedPaths
    public ObservableList getTimedPaths() {
        return sortedTimedPaths;
    }
}
