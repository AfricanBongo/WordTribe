package com.wordtribe.data.savable.interfaces;

import java.io.IOException;
import java.nio.file.Path;

// Used to save iterable items to the disk
public interface SavableIterable<T> {
    void save(Path savePath, Iterable<T> savableCollection) throws IOException;
}
