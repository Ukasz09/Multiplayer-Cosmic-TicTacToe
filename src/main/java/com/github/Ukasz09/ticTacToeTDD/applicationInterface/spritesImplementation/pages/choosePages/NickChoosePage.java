package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.textFields.GameTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.UserNameValidator;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NickChoosePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT_PREFIX = "Choose name of player no. ";
    private static final int FIRST_PLAYER_NUMBER = 1;

    private UserNameValidator nameValidator;
    private int actualInitializedPlayerNumber = FIRST_PLAYER_NUMBER;
    private String lastChosenCorrectName = null;

    public NickChoosePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND), DEFAULT_LABEL_TEXT_PREFIX + FIRST_PLAYER_NUMBER);
        initializeTextField();
        nameValidator = new UserNameValidator();
    }

    private void initializeTextField() {
        GameTextField textField = new GameTextField();
        setOnEnterKeyPressedEvent(textField);
        getContentPane().getChildren().add(textField);
    }

    private void setOnEnterKeyPressedEvent(GameTextField textField) {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String textFromTextField = textField.getText();
                if (nameValidator.validate(textFromTextField) && !textFromTextField.equals(lastChosenCorrectName)) {
                    lastChosenCorrectName = textFromTextField;
                    actualInitializedPlayerNumber++;
                    updateLabelText();
                    clearTextField(textField);
                    notifyObservers(EventKind.CHOSEN_VALID_NAME);
                } else textField.setDefaultIncorrectDataEffect();
            }
        });
    }

    private void updateLabelText() {
        setLabelText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNumber);
    }

    public String getLastChosenCorrectName() {
        return lastChosenCorrectName;
    }

    private void clearTextField(TextField textField) {
        textField.clear();
        textField.setEffect(null);
    }

    @Override
    public void update() {
        //nothing to do
    }
}
