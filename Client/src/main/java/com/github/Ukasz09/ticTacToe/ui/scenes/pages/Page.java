package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.backgrounds.GameBackground;
import javafx.scene.layout.*;

public abstract class Page extends BorderPane implements IScenePage {
    protected GameBackground background;
    protected ViewManager manager;

    //-----------------------------------------------------------------------------------------------------------------//
    public Page(GameBackground background) {
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
    public void setSceneVisible(boolean value) {
        setVisible(value);
    }

    @Override
    public void removeFromActionNodes() {
        manager.removeNode(this);
    }
}
