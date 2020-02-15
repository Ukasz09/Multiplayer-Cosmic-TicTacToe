package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.Page;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IEventKindObservable {
    protected static final Image DEFAULT_BACKGROUND = ImagesProperties.startGameBackground();
    protected static final String DEFAULT_FONT_COLOR = "lightgray";

    private CenteredPane contentPanel;
    private LabelPane labelPane;
    private Set<IEventKindObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public ChoosePage(ImageGameBackground background, String labelText) {
        super(background);
        observers = new HashSet<>();
        initializePanel(labelText);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializePanel(String labelText) {
        labelPane = new LabelPane(labelText);
        contentPanel = new CenteredPane();
        setCenter(contentPanel);
        setTop(labelPane);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public CenteredPane getContentPane() {
        return contentPanel;
    }

    public double getLabelPaneHeight() {
        return labelPane.getMinHeight();
    }

    protected void setLabelText(String text) {
        labelPane.setLabelText(text);
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
}
