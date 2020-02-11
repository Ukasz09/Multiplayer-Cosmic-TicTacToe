package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class CenteredPane extends FlowPane {
    protected ViewManager manager;

    public CenteredPane() {
        super();
        manager = ViewManager.getInstance();
        initializeContentPane();
    }

    private void initializeContentPane() {
        setOrientation(Orientation.HORIZONTAL);
        setAlignment(Pos.CENTER);
    }

    protected double getHeightAfterScaling(double heightToScreenProportion) {
        return heightToScreenProportion * manager.getBottomFrameBorder();
    }

    protected double getWidthAfterScaling(double widthToScreenProportion) {
        return widthToScreenProportion * manager.getRightFrameBorder();
    }
}
