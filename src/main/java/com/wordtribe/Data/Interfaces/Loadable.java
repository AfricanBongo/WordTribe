package com.wordtribe.Data.Interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface Loadable {
    void load(Path filePath) throws IOException;
}
