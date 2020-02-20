package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.panes;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.OscarStatue;

public class OscarStatuePane extends CenteredPane implements IDrawingGraphic {
    private static final double OSCAR_STATUE_WIDTH_PROPORTION = 7 / 8d;
    private static final double OSCAR_STATUE_HEIGHT_PROPORTION = 7 / 8d;

    private OscarStatue oscarStatue;

    //----------------------------------------------------------------------------------------------------------------//
    public OscarStatuePane(double width, double positionX, double positionY) {
        super();
        setUpPane(width, positionX, positionY);
        addOscarStatue();
    }

    private void setUpPane(double width, double positionX, double positionY) {
        setPageWidth(width);
        setLayoutX(positionX);
        setLayoutY(positionY);
    }

    private void setPageWidth(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    private void addOscarStatue() {
        double statueWidth = getPrefWidth() * OSCAR_STATUE_WIDTH_PROPORTION;
        oscarStatue = new OscarStatue(statueWidth, getOscarStatueHeight(), getStatueCenterPositionX(statueWidth), getLayoutY());
    }

    private double getOscarStatueHeight() {
        return (manager.getBottomFrameBorder() - getLayoutY()) * OSCAR_STATUE_HEIGHT_PROPORTION;
    }


    private double getStatueCenterPositionX(double statueWidth) {
        return (getLayoutX() + getWidth() / 2 - statueWidth / 2);
    }

    @Override
    public void render() {
        oscarStatue.render();
    }

    @Override
    public void update() {
        oscarStatue.update();
    }
}
