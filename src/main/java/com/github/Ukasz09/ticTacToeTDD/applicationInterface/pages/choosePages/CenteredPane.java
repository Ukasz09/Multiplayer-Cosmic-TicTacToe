package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class CenteredPane extends FlowPane {
    protected ViewManager manager;

    //-----------------------------------------------------------------------------------------------------------------//
    public CenteredPane() {
        super();
        manager = ViewManager.getInstance();
        initializeContentPane();
    }


    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeContentPane() {
        setOrientation(Orientation.HORIZONTAL);
        setAlignment(Pos.CENTER);
    }
}
