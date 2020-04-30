package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.UserNameValidator;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.EditableTextField;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import javafx.geometry.Orientation;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NickPage extends ChoosePage {
    private static final String HEADER_TEXT_PREFIX = "Choose name of player no. ";
    private static final int FIRST_PLAYER_NUMBER = 1;
    private static final String DEFAULT_PROMPT_TEXT = "Your nickname ";

    private int actualInitializedPlayerNumber = FIRST_PLAYER_NUMBER;
    private String lastChosenCorrectName = null;

    //-----------------------------------------------------------------------------------------------------------------//
    public NickPage() {
        super(StartGamePage.GAME_BACKGROUND, HEADER_TEXT_PREFIX + FIRST_PLAYER_NUMBER, Orientation.HORIZONTAL, 0);
        initializeTextField();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeTextField() {
        EditableTextField textField = new EditableTextField(DEFAULT_PROMPT_TEXT);
        setOnEnterKeyPressedEvent(textField);
        addToContentPane(textField);
    }

    private void setOnEnterKeyPressedEvent(EditableTextField textField) {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (usernameIsValid(textField.getText()))
                    actionWhenCorrectDataInTextField(textField);
                else textField.applyIncorrectDataEffect();
            }
        });
    }

    private boolean usernameIsValid(String username) {
        return (UserNameValidator.validate(username) && !username.equals(lastChosenCorrectName));
    }

    private void actionWhenCorrectDataInTextField(EditableTextField textField) {
        lastChosenCorrectName = textField.getText();
        actualInitializedPlayerNumber++;
        updateLabelText();
        clearTextField(textField);
        notifyObservers(GuiEvents.CHOSEN_VALID_NAME);
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
