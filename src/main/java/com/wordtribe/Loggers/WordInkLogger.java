package com.wordtribe.Loggers;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.logging.Logger;

/*
General logger for the whole program
 */
public class WordInkLogger {

    private final Logger logger;
    private final String logArea;


    // Create or find a new logger when constructed
    public WordInkLogger(String logArea) {
        this.logger = Logger.getLogger("com.wordink." + logArea);
        this.logArea = logArea + ".log";
    }

    // Set up the logging file
    public void initialize() {

        try {
            // Get the log file path
            File directory = FileSystemView.getFileSystemView().getHomeDirectory();
            String logsFile = directory.getPath() + File.separator + "Documents" + File.separator + logArea;

            directory = new File(logsFile);

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

    public Logger getLogger() {
        return logger;
    }
}
