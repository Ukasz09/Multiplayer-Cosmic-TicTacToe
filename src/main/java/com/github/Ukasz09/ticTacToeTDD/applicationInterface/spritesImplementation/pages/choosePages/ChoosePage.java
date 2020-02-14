package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IEventKindObservable {
//    protected static final String DEFAULT_FONT_COLOR = "lightgray";
//    protected static final Color BUTTON_BACKGROUND_COLOR = new Color(0.23, 0.23, 0.23, 0.5);
//    protected static final Effect BUTTON_EXITED_EFFECT = new Lighting(new Light.Distant(0, 5, Color.GRAY));
//    protected static final double BUTTON_CORNER_RADIUS = 25;
//    protected static final Effect BUTTON_HOVERED_EFFECT = new SepiaTone(0); //no effect

    private CenteredPane contentPanel;
    private LabelPane labelPane;
    private Set<IEventKindObserver> observers;

    public ChoosePage(MyBackground background, String labelText) {
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
