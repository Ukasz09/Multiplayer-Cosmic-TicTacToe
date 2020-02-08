package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction;

public interface ISpriteGraphic extends IDrawingGraphic, IEventHandler, ILayout {
    void setImageViewVisible(boolean visible);
}
