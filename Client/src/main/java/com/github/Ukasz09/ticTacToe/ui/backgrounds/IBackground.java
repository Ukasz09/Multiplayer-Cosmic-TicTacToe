package com.github.Ukasz09.ticTacToe.ui.backgrounds;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;

public interface IBackground extends IDrawingGraphic {
    boolean playBackgroundSound();

    boolean stopBackgroundSound();
}
