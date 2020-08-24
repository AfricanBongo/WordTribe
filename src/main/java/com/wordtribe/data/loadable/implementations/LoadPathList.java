package com.wordtribe.data.loadable.implementations;

import com.wordtribe.data.TimedPath;
import com.wordtribe.data.loadable.interfaces.LoadableIterable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Loads paths from disk into memory
 */
public class LoadPathList implements LoadableIterable<TimedPath> {

    @Override
    public Iterable<TimedPath> load(Path loadPath) throws IOException {
        ObservableList<TimedPath> timedPaths = FXCollections.observableArrayList();

        try (ObjectInputStream pathsLoadStream = new ObjectInputStream(Files.newInputStream(loadPath))) {
            boolean eof = false;

            // Read file until the End Of File marker is reached
            while (!eof) {
                try {
                    timedPaths.add((TimedPath) pathsLoadStream.readObject());
                } catch (ClassNotFoundException e) {
                    throw new IOException("Couldn't match class with path objects in file");
                } catch (EOFException e) {
                    eof = true;
                }
            }
        }

        return timedPaths;
    }

}
