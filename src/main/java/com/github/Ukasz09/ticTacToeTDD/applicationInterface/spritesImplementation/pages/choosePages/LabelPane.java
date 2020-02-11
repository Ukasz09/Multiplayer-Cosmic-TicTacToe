package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

public class LabelPane extends CenteredPane {
    private static final double LABEL_HEIGHT_TO_SCREEN_PROPORTION = 1 / 5d;
    public static final double FONT_SIZE_TO_SCREEN_PROPORTION = 8 / 108d;

    private TextField textField;

    public LabelPane(String text) {
        initializePane(text);
        getChildren().add(textField);
    }

    private void initializePane(String text) {
        setProperPaneSize();
        initializeTextField(text);
    }

    private void setProperPaneSize() {
        setMinHeight(getHeightAfterScaling(LABEL_HEIGHT_TO_SCREEN_PROPORTION));
        setMinWidth(manager.getRightFrameBorder());
    }

    private void initializeTextField(String text) {
        textField = new TextField(text);
        textField.setEditable(false);
        textField.setMinWidth(getMinWidth());
        textField.setMinHeight(getMinHeight());
        textField.setAlignment(Pos.CENTER);
        textField.setBackground(Background.EMPTY);
        textField.setFocusTraversable(false);
        int fontSize = (int) (FONT_SIZE_TO_SCREEN_PROPORTION * manager.getBottomFrameBorder());
        ChoosePage.setDefaultTextFieldFont(textField, ChoosePage.DEFAULT_FONT_COLOR, fontSize);
    }

    public void setLabelText(String text){
        textField.setText(text);
    }
}
