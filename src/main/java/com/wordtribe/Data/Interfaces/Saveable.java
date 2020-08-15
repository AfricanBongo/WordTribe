package com.wordtribe.Data.Interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface Saveable {
    void save(Path path) throws IOException;
}
