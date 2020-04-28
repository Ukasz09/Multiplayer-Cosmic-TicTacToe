package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
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
