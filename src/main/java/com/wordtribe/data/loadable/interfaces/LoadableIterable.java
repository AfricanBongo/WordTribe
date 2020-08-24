package com.wordtribe.data.loadable.interfaces;

import java.io.IOException;
import java.nio.file.Path;

// Used to load iterable items from the disk
public interface LoadableIterable<T> {
    abstract Iterable<T> load(Path loadPath) throws IOException;
}
