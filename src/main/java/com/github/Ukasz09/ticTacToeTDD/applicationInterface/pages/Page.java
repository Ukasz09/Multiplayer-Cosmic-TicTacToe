package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.GameBackground;
import javafx.scene.layout.*;

public abstract class Page extends BorderPane implements IScenePage {
    protected GameBackground background;
    protected ViewManager manager;

    //-----------------------------------------------------------------------------------------------------------------//
    public Page(GameBackground background) {
        this.background = background;
        manager = ViewManager.getInstance();
        initializePanel();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializePanel() {
        setDefaultPanelSize();
        manager.addNode(this);
    }

    private void setDefaultPanelSize() {
        setMinWidth(manager.getRightFrameBorder());
        setMinHeight(manager.getBottomFrameBorder());
    }

    @Override
    public void render() {
        background.render();
    }

    public boolean playBackgroundSound() {
        return background.playBackgroundSound();
    }

    public boolean stopBackgroundSound() {
        return background.stopBackgroundSound();
    }

    @Override
    public void setSceneVisible(boolean value) {
        setVisible(value);
    }
}
