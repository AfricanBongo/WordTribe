package com.wordtribe.data.savable.implementations;

import com.wordtribe.data.TimedPath;
import com.wordtribe.data.savable.interfaces.SavableIterable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Saves a list of paths to the disk
 */
public class SavePathList implements SavableIterable<TimedPath> {

    @Override
    public void save(Path savePath, Iterable<TimedPath> savableCollection) throws IOException {
        try (ObjectOutputStream pathsSaveStream = new ObjectOutputStream(Files.newOutputStream(savePath))) {

            // Loop through list writing objects to memory
            for (TimedPath timedPath : savableCollection) {
                pathsSaveStream.writeObject(timedPath);
            }
        }
    }
}
