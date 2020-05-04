package com.github.Ukasz09.ticTacToe.ui.backgrounds;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import javafx.scene.image.Image;

public class GameBackground implements IDrawingGraphic {
    private Image backgroundImage;
    private ViewManager manager = ViewManager.getInstance();

    //----------------------------------------------------------------------------------------------------------------//
    public GameBackground(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void render() {
        drawBackground();
    }

    @Override
    public void update() {
        //nothing to do
    }

    private void drawBackground() {
        manager.getGraphicContext().drawImage(backgroundImage, 0, 0, manager.getRightFrameBorder(), manager.getBottomFrameBorder());
    }
}
