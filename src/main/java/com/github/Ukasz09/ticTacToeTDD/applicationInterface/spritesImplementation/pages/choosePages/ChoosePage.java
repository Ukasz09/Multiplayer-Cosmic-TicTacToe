package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class ChoosePage extends Page {
    protected static final String DEFAULT_FONT_COLOR = "lightgray";
    protected static final Color BUTTON_BACKGROUND_COLOR = new Color(0.23, 0.23, 0.23, 0.5);
    protected static final Effect BUTTON_EXITED_EFFECT = new Lighting(new Light.Distant(0, 5, Color.GRAY));
    protected static final double BUTTON_CORNER_RADIUS = 25;
    protected static final Effect BUTTON_HOVERED_EFFECT = new SepiaTone(0); //no effect

    private CenteredPane contentPanel;
    private LabelPane labelPane;

    public ChoosePage(MyBackground background, String labelText) {
        super(background);
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

    protected void setLabelText(String text){
        labelPane.setLabelText(text);
    }
}
