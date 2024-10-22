package com.github.Ukasz09.ticTacToe.ui.sprites;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;

public abstract class Sprite implements IDrawingGraphic {
    protected ViewManager manager;
    protected double positionX, positionY;
    protected double width, height;

    //----------------------------------------------------------------------------------------------------------------//
    public Sprite(double width, double height, double positionX, double positionY) {
        manager = ViewManager.getInstance();
        this.width = width;
        this.height = height;

        this.positionX = positionX;
        this.positionY = positionY;
    }

    //----------------------------------------------------------------------------------------------------------------//
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}