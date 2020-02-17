package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.LabelTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.FontProperties;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

public class LabelPane extends CenteredPane {
    private static final double HEIGHT_PROPORTION = 1 / 5d;
    public static final double FONT_SIZE_PROPORTION = 6 / 108d;

    private TextField textField;

    //-----------------------------------------------------------------------------------------------------------------//
    public LabelPane(String text) {
        initializePane(text);
        getChildren().add(textField);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializePane(String text) {
        setDefaultPaneSize();
//        initializeTextField(text);
        LabelTextField labelField = new LabelTextField(text, (int) (manager.getScaledHeight(FONT_SIZE_PROPORTION)), false);
        labelField.maximizeWidth();
        textField = labelField;
    }

    private void setDefaultPaneSize() {
        setMinHeight(manager.getScaledHeight(HEIGHT_PROPORTION));
        setMinWidth(manager.getRightFrameBorder());
    }

//    private void initializeTextField(String text) {
//        textField = new TextField(text);
//        textField.setEditable(false);
//        textField.setMinWidth(getMinWidth());
//        textField.setMinHeight(getMinHeight());
//        textField.setAlignment(Pos.CENTER);
//        textField.setBackground(Background.EMPTY);
//        textField.setFocusTraversable(false);
//        int fontSize = (int) (FONT_SIZE_PROPORTION * manager.getBottomFrameBorder());
//        setDefaultLabelFont(textField, ChoosePage.DEFAULT_FONT_COLOR, fontSize);
//    }
//
//    public void setDefaultLabelFont(TextField textField, String fontColor, int fontSize) {
//        Font font = FontProperties.chargenRegularFont(fontSize);
//        textField.setStyle("-fx-text-inner-color: " + fontColor + ";");
//        textField.setFont(font);
//    }

    public void setLabelText(String text) {
        textField.setText(text);
    }
}
