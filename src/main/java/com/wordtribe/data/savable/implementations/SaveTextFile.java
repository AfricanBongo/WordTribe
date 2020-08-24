package com.wordtribe.data.savable.implementations;

import com.wordtribe.data.savable.interfaces.Savable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Saves a text file into the disk
 */
public class SaveTextFile implements Savable<String> {

    @Override
    public void save(Path path, String saveItem) throws IOException {
        try (BufferedWriter textWriter = Files.newBufferedWriter(path)) {
            textWriter.write(saveItem);
        }
    }
}
