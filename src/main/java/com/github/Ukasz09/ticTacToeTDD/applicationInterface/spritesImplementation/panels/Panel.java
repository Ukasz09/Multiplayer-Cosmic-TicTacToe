package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class Panel extends BorderPane implements IDrawingGraphic {
    private MyBackground background;
    protected ViewManager manager;

    public Panel(MyBackground background) {
        this.background = background;
        manager = ViewManager.getInstance();
        initializePanel();
    }

    private void initializePanel() {
        setProperPanelSize();
        manager.addNode(this);
    }

    private void setProperPanelSize() {
        setMinWidth(manager.getRightFrameBorder());
        setMinHeight(manager.getBottomFrameBorder());
    }

    @Override
    public void render() {
        background.render();
    }

}
