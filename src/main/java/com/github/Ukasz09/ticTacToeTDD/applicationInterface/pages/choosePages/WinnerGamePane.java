package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.GameControlButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.OscarStatue;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import javafx.scene.control.Button;

import java.util.HashSet;
import java.util.Set;

public class WinnerGamePane extends CenteredPane implements IDrawingGraphic {
    private static final double OSCAR_STATUE_WIDTH_PROPORTION = 7 / 8d;
    private static final double OSCAR_STATUE_HEIGHT_PROPORTION = 7 / 8d;

//    private Set<IEventKindObserver> observers;
    private OscarStatue oscarStatue;
    private double headerHeight;
    private double oscarPositionX;

    //----------------------------------------------------------------------------------------------------------------//
    public WinnerGamePane(double width, double headerHeight, double oscarPagePositionX, double panePagePositionX) {
        super();
//        observers = new HashSet<>();
        setUpPane(width, headerHeight, panePagePositionX);
        addOscarStatue(oscarPagePositionX);
//        addButtons();
//        setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0), new Insets(0))));
    }

    private void setUpPane(double width, double headerHeight, double pagePositionX) {
        this.headerHeight = headerHeight;
        setPageWidth(width);
//        setPageHeight(manager.getBottomFrameBorder());
    }

    private void setPageWidth(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

//    private void setPageHeight(double height) {
//        setHeight(height);
//        setMinHeight(height);
//        setPrefHeight(height);
//    }

    private void addOscarStatue(double oscarPositionX) {
        this.oscarPositionX = oscarPositionX;
        double statueWidth = getPrefWidth() * OSCAR_STATUE_WIDTH_PROPORTION;
        double statueHeight = (manager.getBottomFrameBorder() - headerHeight) * OSCAR_STATUE_HEIGHT_PROPORTION;
        oscarStatue = new OscarStatue(statueWidth, statueHeight, getStatueCenterPositionX(statueWidth), headerHeight);

//        ImageView iv = oscarStatue.getSpriteImageView();
//        iv.setVisible(true);
//        getChildren().add(iv); //todo: test
    }

    private double getStatueCenterPositionX(double statueWidth) {
        return (oscarPositionX + getWidth() / 2 - statueWidth / 2);
    }

//    private void addButtons() {
//        addRepeatGameButton();
//        addStartGameButton();
//        addEndGameButton();
//    }
//
//    private void addRepeatGameButton() {
//        Button button = new GameControlButton("REPEAT GAME");
//        button.setOnMouseClicked(event -> notifyObservers(EventKind.REPEAT_GAME_BUTTON));
//        getChildren().add(button);
//    }
//
//    private void addStartGameButton() {
//        Button button = new GameControlButton("NEW GAME");
//        button.setOnMouseClicked(event -> notifyObservers(EventKind.START_BUTTON_CLICKED));
//        getChildren().add(button);
//    }
//
//    private void addEndGameButton() {
//        Button button = new GameControlButton("END GAME");
//        button.setOnMouseClicked(event -> notifyObservers(EventKind.END_GAME_BUTTON_CLICKED));
//        getChildren().add(button);
//    }

//    @Override
//    public void attachObserver(IEventKindObserver observer) {
//        observers.add(observer);
//    }
//
//    @Override
//    public void detachObserver(IEventKindObserver observer) {
//        observers.remove(observer);
//    }
//
//    @Override
//    public void notifyObservers(EventKind eventKind) {
//        for (IEventKindObserver observer : observers)
//            observer.updateObserver(eventKind);
//    }

    @Override
    public void render() {
        oscarStatue.render();
    }

    @Override
    public void update() {
        oscarStatue.update();
    }
}
