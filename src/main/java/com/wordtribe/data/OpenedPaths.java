package com.wordtribe.data;

import com.wordtribe.Logger.WordInkLogger;
import com.wordtribe.data.loadable.implementations.LoadPathList;
import com.wordtribe.data.savable.implementations.SavePathList;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.SortedList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;

/*
Class that handles the TimedPaths once used or currently being used in the text editor
 */
public class OpenedPaths extends ObservableListBase<TimedPath> {
    private static ObservableList<TimedPath> timedPaths;
    private static ObservableList<TimedPath> sortedTimedPaths;
    private LoadPathList loadPathList = new LoadPathList();
    private SavePathList savePathList = new SavePathList();
    private static OpenedPaths openedPaths = new OpenedPaths();

    private OpenedPaths() {}


    //Initialize the data structures
    {
        timedPaths = loadPaths();
        // Sort according the time modified
        sortedTimedPaths = new SortedList<>(timedPaths, (Comparator.comparing(TimedPath::getTimeModified)));
    }

    @Override
    public TimedPath get(int i) {
        return sortedTimedPaths.get(i);
    }

    @Override
    public int size() {
        return timedPaths.size();
    }


    @Override
    public boolean add(TimedPath timedPath) {
        return timedPaths.add(timedPath);
    }

    @Override
    public boolean remove(Object o) {
        return timedPaths.remove(o);
    }

    @Override
    public boolean isEmpty() {
        return timedPaths.isEmpty();
    }

    @Override
    public int indexOf(Object o) {
        return timedPaths.indexOf(o);
    }

    public ObservableList<TimedPath> getTimedPaths() {
        return timedPaths;
    }

    public ObservableList<TimedPath> getSortedTimedPaths() {
        return sortedTimedPaths;
    }


    // Load paths from disk
    public ObservableList<TimedPath> loadPaths() {
        try {
            return (ObservableList<TimedPath>) loadPathList.load(Paths.get("OpenedPaths" + File.separator + "paths.dat"));
        } catch (IOException e) {
            WordInkLogger.getLogger().severe("Error loading timed paths from disk..." + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Save paths to disk
    public void savePaths() throws IOException {
        savePathList.save(Paths.get("OpenedPaths" + File.separator + "paths.dat"), timedPaths);
    }

    public static OpenedPaths getOpenedPaths() {
        return openedPaths;
    }


}
