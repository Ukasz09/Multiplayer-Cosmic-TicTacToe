package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.IBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.pages.IScenePage;
import javafx.scene.layout.*;

public abstract class Page extends BorderPane implements IScenePage {
    protected IBackground background;
    protected ViewManager manager;

    //-----------------------------------------------------------------------------------------------------------------//
    public Page(IBackground background) {
        manager = ViewManager.getInstance();
        this.background = background;
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

    @Override
    public boolean playBackgroundSound() {
        return background.playBackgroundSound();
    }

    @Override
    public boolean stopBackgroundSound() {
        return background.stopBackgroundSound();
    }

    @Override
    public void setSceneVisible(boolean value) {
        setVisible(value);
    }

    @Override
    public void removeFromActionNodes() {
        manager.removeNode(this);
    }
}
