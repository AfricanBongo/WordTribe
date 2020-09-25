package com.wordtribe.Logger;

import java.io.*;
import java.util.logging.Logger;

/*
General logger for the whole program
 */
public class WordInkLogger {

    private static final Logger logger = Logger.getLogger("com.wordtribe");


    // Create or find a new logger when constructed
    private WordInkLogger() {
    }

    // Set up the logging file
    static {

        try {
        // Get the log file path
        File directory = new File("wordtribe.log");

        // Create new log file if one doesn't already exist
        if (!directory.exists()) {
            directory.createNewFile();
        }

        // Change system default to write exceptions into log file
        System.setErr(new PrintStream(new FileOutputStream(directory.getPath())));

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static Logger getLogger() {
        return logger;
    }
}
