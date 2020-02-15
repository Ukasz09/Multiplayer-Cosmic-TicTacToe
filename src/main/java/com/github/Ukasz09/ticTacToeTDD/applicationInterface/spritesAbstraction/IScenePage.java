package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction;

public interface IScenePage extends IDrawingGraphic {
    void setSceneVisible(boolean value);

    boolean playBackgroundSound();

    boolean stopBackgroundSound();
}
