package com.wordtribe.customcontrols;

import javafx.scene.control.Alert;

/**
 * Class used to display alert and dialog messages to the user
 * @author africanbongo (Donell Mtabvuri)
 */
public class DisplayMessages {

    /**
     * Displays an error message to the user
     * @param error message to be displayed to the user
     */
    public Alert showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(error);
        alert.showAndWait();

        return alert;
    }

    /**
     * Displays an informative message to the user
     * @param sourceArea Where the method is called from
     * @param context The message to be displayed in the context text area
     */
    public Alert showInfo(String sourceArea, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Take note");
        alert.setHeaderText(sourceArea);
        alert.setContentText(context);
        alert.showAndWait();
        return alert;
    }
}
