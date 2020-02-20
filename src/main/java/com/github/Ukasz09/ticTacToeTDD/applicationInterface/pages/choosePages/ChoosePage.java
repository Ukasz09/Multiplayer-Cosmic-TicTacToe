package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.IBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.Page;
import javafx.geometry.Pos;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IEventKindObservable {
    protected static final Image DEFAULT_BACKGROUND = ImagesProperties.startGameBackground();

    private CenteredPane contentPanel;
    private LabelPane headerPane;
    private Set<IEventKindObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public ChoosePage(IBackground background, String labelText) {
        super(background);
        observers = new HashSet<>();
        initializePanel(labelText);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializePanel(String labelText) {
        headerPane = new LabelPane(labelText);
        setTop(headerPane);
        contentPanel = new CenteredPane();
        setCenter(contentPanel);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public CenteredPane getContentPane() {
        return contentPanel;
    }

    public double getHeaderPaneHeight() {
        return headerPane.getMinHeight();
    }

    protected void setHeaderText(String text) {
        headerPane.setLabelText(text);
    }

    @Override
    public void attachObserver(IEventKindObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IEventKindObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventKind eventKind) {
        for (IEventKindObserver observer : observers)
            observer.updateObserver(eventKind);
    }

    protected double getFirstButtonXPositionToCenterWithOthers(int buttonsQty, double buttonsPadding, double buttonWidth) {
        return (manager.getRightFrameBorder() - buttonsQty * buttonWidth - (buttonsQty - 1) * buttonsPadding) / 2;
    }
}
