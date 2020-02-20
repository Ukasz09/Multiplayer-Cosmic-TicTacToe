package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.IBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.panes.CenteredPane;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.panes.LabelPane;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.Page;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IEventKindObservable {
    protected static final Image DEFAULT_BACKGROUND = ImagesProperties.startGameBackground();

    private CenteredPane contentPane;
    private LabelPane headerPane;
    private Set<IEventKindObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public ChoosePage(IBackground background, String labelText, Orientation orientation, double horizontalGap) {
        super(background);
        observers = new HashSet<>();
        initializePage(labelText, orientation, horizontalGap);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializePage(String labelText, Orientation orientation, double horizontalGap) {
        headerPane = new LabelPane(labelText);
        setTop(headerPane);
        initializeContentPane(orientation, horizontalGap);
    }

    private void initializeContentPane(Orientation orientation, double horizontalGap) {
        contentPane = new CenteredPane();
        setCenter(contentPane);
        contentPane.setOrientation(orientation);
        contentPane.setHgap(horizontalGap);
    }

    public void addToContentPane(Node node) {
        contentPane.getChildren().add(node);
    }

    //----------------------------------------------------------------------------------------------------------------//
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
