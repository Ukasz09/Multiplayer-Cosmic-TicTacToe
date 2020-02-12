package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ChooseBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.others.UserNameValidator;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class NameChoosePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT_PREFIX = "Choose avatar of player no. ";
    private static final String DEFAULT_PROMPT_TEXT = "Your nick ... ";
    private static final int FIRST_PLAYER_NUMBER = 1;
    private static final double FIELD_WIDTH_TO_SCREEN_PROPORTION = 35 / 192d;
    private static final double FIELD_HEIGHT_TO_SCREEN_PROPORTION = 10 / 108d;
    private static final double FONT_SIZE_TO_SCREEN_PROPORTION = 4 / 108d;
    private static final Color DEFAULT_TEXTFIELD_COLOR = new Color(0, 0, 0, 0.5);
    private static final Effect DEFAULT_TEXTFIELD_HOVERED_EFFECT = new InnerShadow(1, Color.DARKGRAY);
    private static final Effect DEFAULT_TEXTFIELD_INCORRECT_EFFECT = new InnerShadow(100, Color.DARKRED);

    private String lastChosenCorrectName = null;
    private TextField textField;
    private UserNameValidator nameValidator;
    private int actualInitializedPlayerNumber = FIRST_PLAYER_NUMBER;

    public NameChoosePage() {
        super(new ChooseBackground(), DEFAULT_LABEL_TEXT_PREFIX + FIRST_PLAYER_NUMBER);
        initializeTextField();
        nameValidator = new UserNameValidator();
    }

    private void initializeTextField() {
        textField = new TextField();
        setTextFieldDefaultSize();
        setTextFieldDefaultAppearance();
        setTextFieldDefaultEffects();
        setTextFieldActionEvent();
        getContentPane().getChildren().add(textField);
    }

    private void setTextFieldDefaultSize() {
        double textFieldWidth = getContentPane().getWidthAfterScaling(FIELD_WIDTH_TO_SCREEN_PROPORTION);
        double textFieldHeight = getContentPane().getHeightAfterScaling(FIELD_HEIGHT_TO_SCREEN_PROPORTION);
        textField.setMinSize(textFieldWidth, textFieldHeight);
        textField.setPrefSize(textFieldWidth, textFieldHeight);
    }

    private void setTextFieldDefaultAppearance() {
        textField.setBackground(new Background(new BackgroundFill(DEFAULT_TEXTFIELD_COLOR, new CornerRadii(BUTTON_CORNER_RADIUS), Insets.EMPTY)));
        int fontSize = (int) (FONT_SIZE_TO_SCREEN_PROPORTION * manager.getBottomFrameBorder());
        setDefaultTextFieldFont(textField, DEFAULT_FONT_COLOR, fontSize);
        textField.setFocusTraversable(false);
        textField.setPromptText(DEFAULT_PROMPT_TEXT);
    }

    private void setTextFieldDefaultEffects() {
        textField.setEffect(null);
        textField.setOnMouseEntered(event -> textField.setEffect(DEFAULT_TEXTFIELD_HOVERED_EFFECT));
        textField.setOnMouseExited(event -> textField.setEffect(null));
    }

    private void setTextFieldEffectWhenIncorrectData() {
        textField.setEffect(DEFAULT_TEXTFIELD_INCORRECT_EFFECT);
    }

    private void setTextFieldActionEvent() {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String textFromTextField = textField.getText();
                if (nameValidator.validate(textFromTextField) && !textFromTextField.equals(lastChosenCorrectName)) {
                    lastChosenCorrectName = textFromTextField;
                    actualInitializedPlayerNumber++;
                    updateLabelText();
                    resetTextField();
                    notifyObservers(EventKind.CHOSEN_VALID_NAME);
                } else setTextFieldEffectWhenIncorrectData();
            }
        });
    }

    private void resetTextField() {
        setTextFieldDefaultEffects();
        textField.clear();
    }

    private void updateLabelText() {
        setLabelText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNumber);
    }

    public String getLastChosenCorrectName() {
        return lastChosenCorrectName;
    }

    @Override
    public void update() {
        //nothing to do
    }
}
