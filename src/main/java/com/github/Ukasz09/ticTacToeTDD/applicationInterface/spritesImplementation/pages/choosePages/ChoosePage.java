package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ImageGameBackground;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IEventKindObservable {
    private static final Effect DEFAULT_INCORRECT_DATA_EFFECT = new InnerShadow(100, Color.DARKRED);


    protected static final Image DEFAULT_BACKGROUND = ImagesProperties.nightSky1Background();

    //todo: tmp - zorbic osobna klase na text fielda
    protected static final String DEFAULT_FONT_COLOR = "lightgray";
    protected static final double BUTTON_CORNER_RADIUS = 25;


    private CenteredPane contentPanel;
    private LabelPane labelPane;
    private Set<IEventKindObserver> observers;

    public ChoosePage(ImageGameBackground background, String labelText) {
        super(background);
        observers = new HashSet<>();
        initializePanel(labelText);
    }

    private void initializePanel(String labelText) {
        labelPane = new LabelPane(labelText);
        contentPanel = new CenteredPane();
        setCenter(contentPanel);
        setTop(labelPane);
    }

    public static void setDefaultTextFieldFont(TextField textField, String fontColor, int fontSize) {
        Font font = FontProperties.chargenRegularFont(fontSize);
        textField.setStyle("-fx-text-inner-color: " + fontColor + ";");
        textField.setFont(font);
    }

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
