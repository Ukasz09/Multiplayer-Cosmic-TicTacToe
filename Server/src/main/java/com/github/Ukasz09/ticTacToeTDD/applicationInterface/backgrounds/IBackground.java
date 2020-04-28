package com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;

public interface IBackground extends IDrawingGraphic {
    boolean playBackgroundSound();

    boolean stopBackgroundSound();
}
