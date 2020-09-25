package com.wordtribe.data;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class OpenedPathsTest {


    // Test if the method returns a Timed Path stored in it
    @Test
    void checkTimedPath() {
        Path path = Paths.get("texts" + File.separator + "bible.txt");
        TimedPath timedPath = new TimedPath(path);
        OpenedPaths.getOpenedPaths().add(timedPath);
        assertEquals(timedPath, OpenedPaths.getOpenedPaths().checkTimedPath(path));
    }


    // Test that the method returns null if a null path is passed to it
    @Test
    void checkTimedPathReturnsNull() {
        assertNull(OpenedPaths.getOpenedPaths()
                .checkTimedPath(Paths.get("texts" + File.separator + "bible.txt")));
    }


    // Test to see if the path list exists
    @Test
    void loadPathsList() {
        assertNotNull(OpenedPaths.getOpenedPaths().getTimedPaths());
    }


    // Test if the savePaths method doesn't throw an exception
    @Test
    void savePaths() {
        assertDoesNotThrow(() -> OpenedPaths.getOpenedPaths().savePaths());
    }
}