package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;

public interface IScenePage extends IDrawingGraphic {
    void setSceneVisible(boolean value);

    void removeFromActionNodes();
}
