package com.wordtribe.customcontrols;

import com.wordtribe.data.OpenedPaths;
import com.wordtribe.data.TimedPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/*
*********************** Singleton class ********************
The recent file menu items displayed in a sorted observable list
 */
public class RecentFileMenuItemList {
    private ObservableList<RecentFileMenuItem> menuItems;
    ObservableList<RecentFileMenuItem> observableList;
    private static RecentFileMenuItemList menuItemList = new RecentFileMenuItemList();

    private RecentFileMenuItemList() {
        // Initialize list
        observableList = FXCollections.observableArrayList();

        // Configure the menuItems list
        menuItems = new SortedList<>(observableList, RecentFileMenuItem::compareTo);

        // Populate the first observable list, using the openedPaths list
        OpenedPaths.getOpenedPaths().getTimedPaths().forEach(timedPath -> {
            RecentFileMenuItem recentFileMenuItem = new RecentFileMenuItem(timedPath);
            observableList.add(recentFileMenuItem);
        });
    }


    public ObservableList<RecentFileMenuItem> getMenuItems() {
        return menuItems;
    }


    // Add a new file to menu list and to openedpaths list
    public RecentFileMenuItem addNewMenuItem(TimedPath timedPath) {
        OpenedPaths.getOpenedPaths().add(timedPath);
        RecentFileMenuItem recentFileMenuItem = new RecentFileMenuItem(timedPath);

        // Add to menu list
        observableList.add(recentFileMenuItem);
        return recentFileMenuItem;
    }

    // Retrieve a menu item if it exists in the list
    public RecentFileMenuItem checkMenuItem(TimedPath timedPath) {
        for (RecentFileMenuItem recentFileMenuItem : menuItems) {
            if (recentFileMenuItem.getTimedPath().equals(timedPath)) {
                return recentFileMenuItem;
            }
        }

        return null;
    }


    public static RecentFileMenuItemList getMenuItemList() {
        return menuItemList;
    }
}
