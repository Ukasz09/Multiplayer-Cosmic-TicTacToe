package com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;

public interface IScenePage extends IDrawingGraphic {
    void setSceneVisible(boolean value);

    boolean playBackgroundSound();

    boolean stopBackgroundSound();

    void removeFromActionNodes();
}
