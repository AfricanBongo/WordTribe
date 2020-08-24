package com.wordtribe.data.loadable.interfaces;

import java.io.IOException;
import java.nio.file.Path;

// Used to load objects from the disk
public interface Loadable<T> {
    T load(Path path) throws IOException, ClassNotFoundException;
}
