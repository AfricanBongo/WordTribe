package com.wordtribe.Loggers;

import java.util.logging.Logger;

/*
Logs exceptions that are thrown by classes from the Data package
 */
public class DataLogger extends WordInkLogger {

    private static DataLogger dataLogger = new DataLogger();

    private DataLogger() {
        super("Data");
    }

    // Return logger from super class
    public static Logger getDataLogger() {
        return dataLogger.getLogger();
    }
}
