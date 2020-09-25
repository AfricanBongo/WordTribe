package com.wordtribe.data.loadable.implementations;

import com.wordtribe.data.loadable.interfaces.Loadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Loads a text file from disk
public class LoadTextFile implements Loadable<String> {

    // Return string from file
    // If file doesn't exists create new one and return empty string
    @Override
    public String load(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return Files.readString(path);
    }
}
