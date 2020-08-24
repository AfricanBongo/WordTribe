package com.wordtribe.data.savable.interfaces;

import java.io.IOException;
import java.nio.file.Path;

// For saving objects into the disk
public interface Savable<T> {
    void save(Path path, T saveItem) throws IOException;
}
