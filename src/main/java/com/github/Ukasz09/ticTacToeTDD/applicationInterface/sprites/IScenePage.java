package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

public interface IScenePage extends IDrawingGraphic {
    void setSceneVisible(boolean value);

    boolean playBackgroundSound();

    boolean stopBackgroundSound();

    void removeFromActionNode();
}
