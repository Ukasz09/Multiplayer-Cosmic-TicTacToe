package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

public interface ISpriteGraphic extends IDrawingGraphic, IEventHandler, ILayout {
    void setImageViewVisible(boolean visible);
}
