package com.wordtribe.customcontrols;

import com.wordtribe.data.TimedPath;
import javafx.scene.control.MenuItem;

/*
A custom menu item, when clicked opens a Tab with file path.
Has a timedPath instance used to locate a file in the disk.
 */
public class RecentFileMenuItem extends MenuItem implements Comparable<RecentFileMenuItem>{
    private TimedPath timedPath;

    public RecentFileMenuItem(TimedPath timedPath) {
        super(timedPath.getPath().toString());
        this.timedPath = timedPath;
    }

    public TimedPath getTimedPath() {
        return timedPath;
    }

    // When the path is updated change the text displayed by the menu item
    public void setTimedPath(TimedPath timedPath) {
        this.timedPath = timedPath;
        this.setText(timedPath.getPath().toString());
    }

    @Override
    public int compareTo(RecentFileMenuItem recentFileMenuItem) {
        return getTimedPath().compareTo(recentFileMenuItem.getTimedPath());
    }
}
