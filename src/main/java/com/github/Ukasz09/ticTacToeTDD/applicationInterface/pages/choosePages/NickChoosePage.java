package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.EditableTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.UserNameValidator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NickChoosePage extends ChoosePage {
    private static final String HEADER_TEXT_PREFIX = "Choose name of player no. ";
    private static final int FIRST_PLAYER_NUMBER = 1;
    private static final String DEFAULT_PROMPT_TEXT = "Choose nick ... ";

    private int actualInitializedPlayerNumber = FIRST_PLAYER_NUMBER;
    private String lastChosenCorrectName = null;

    //-----------------------------------------------------------------------------------------------------------------//
    public NickChoosePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND), HEADER_TEXT_PREFIX + FIRST_PLAYER_NUMBER);
        initializeTextField();
    }

    private void initializeTextField() {
        EditableTextField textField = new EditableTextField(DEFAULT_PROMPT_TEXT);
        setOnEnterKeyPressedEvent(textField);
        getContentPane().getChildren().add(textField);
    }

    private void setOnEnterKeyPressedEvent(EditableTextField textField) {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String textFromTextField = textField.getText();
                if (UserNameValidator.validate(textFromTextField) && !textFromTextField.equals(lastChosenCorrectName)) {
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
        setHeaderText(HEADER_TEXT_PREFIX + actualInitializedPlayerNumber);
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
