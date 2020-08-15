package com.wordtribe.Data.PathHandlers;

import com.wordtribe.Loggers.DataLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


// Paths show in Home Screen
public class OpenedTimedPaths {

    private static OpenedTimedPaths instance = new OpenedTimedPaths();
    private TimedPathsHandler openedPaths;
    private TimedPath openedPath;
    private Path pathsFile = Paths.get("Pathes" + File.separator+ "paths.dat");

    private OpenedTimedPaths() {
        openedPaths = new TimedPathsHandler();
        openedPath = null;
    }

    public TimedPath getOpenedPath() {
        return openedPath;
    }


    // Set current opened path
    public void openPath(Path path) {

        // Retrieve TimedPath from paths if it already exists
        TimedPath pathToOpen = openedPaths.checkTimedPath(path);

        if (pathToOpen != null) {
            this.openedPath = pathToOpen;
        } else {
            // Set opened path to given path if pathToOpen is null
            openedPath = new TimedPath(path.toString());

            // Add to paths object instantaneously after instantiating the object
            openedPaths.addPath(openedPath);
        }

        // To allow to open file immediately when the app is opened again
        openedPath.setLastOpened(true);

    }


    // Save paths to disk
    public void savePaths() {

        // Create new thread and save paths to disk
        new Thread() {
            @Override
            public void run() {
                // Save paths to disk
                try {
                    openedPaths.save(pathsFile);
                } catch (IOException e) {
                    // Log exception into data logger file
                    DataLogger.getDataLogger().severe(e.getMessage());
                }
            }
        }.start();
    }


    // Load paths from disk
    public void loadPaths() {

        // Create new thread and load paths from disk
        new Thread() {
            @Override
            public void run() {
                // Load paths from disk
                try {
                    openedPaths.load(pathsFile);
                } catch (IOException e) {
                    DataLogger.getDataLogger().severe(e.getMessage());
                }
            }
        }.start();
    }


    // Add a new file
    public void addNewFile(Path path) throws IOException {

        // Check if the file already exists
        if (Files.exists(path)) {
            throw new IOException("File already exists");
        } else {
            // Create new file and set as openedPath
              try {
                  Files.createFile(path);
                  openedPath = new TimedPath(path.toString());
                  openedPaths.addPath(openedPath);
              } catch (IOException e) {
                  DataLogger.getDataLogger().severe("Error creating new file");
                  throw new IOException("Error creating new file");
              }

        }
    }


    public static OpenedTimedPaths getInstance() {
        return instance;
    }

    public TimedPathsHandler getOpenedPaths() {
        return openedPaths;
    }
}
