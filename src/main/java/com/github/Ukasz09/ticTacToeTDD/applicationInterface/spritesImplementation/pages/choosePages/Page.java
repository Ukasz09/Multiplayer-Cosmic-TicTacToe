package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.scene.layout.*;

public abstract class Page extends BorderPane implements IScenePage {
    private MyBackground background;
    protected ViewManager manager;

    public Page(MyBackground background) {
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
