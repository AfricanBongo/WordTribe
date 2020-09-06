package com.wordtribe.data.loadable.implementations;

import com.wordtribe.data.loadable.interfaces.Loadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Loads a text file from disk
public class LoadTextFile implements Loadable<String> {

    @Override
    public String load(Path path) throws IOException {
        if (Files.exists(path)) {
            return Files.readString(path);
        }

        return null;
    }
}
