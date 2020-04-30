package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.UserNameValidator;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.EditableTextField;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import javafx.geometry.Orientation;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NickPage extends ChoosePage {
    private static final String LABEL_TEXT = "Choose nick";
    private static final String PROMPT_TEXT = "Your nickname ";

    private String chosenCorrectName = null;
    private String nickChosenByOtherPlayer;

    //-----------------------------------------------------------------------------------------------------------------//
    public NickPage(String nickChosenByOtherPlayer) {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        initializeTextField();
        this.nickChosenByOtherPlayer = nickChosenByOtherPlayer;
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeTextField() {
        EditableTextField textField = new EditableTextField(PROMPT_TEXT);
        setOnEnterKeyPressedEvent(textField);
        addToContentPane(textField);
    }

    private void setOnEnterKeyPressedEvent(EditableTextField textField) {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (usernameIsValid(textField.getText()))
                    correctDataInTextFieldAction(textField);
                else textField.applyIncorrectDataEffect();
            }
        });
    }

    private boolean usernameIsValid(String username) {
        return (UserNameValidator.validate(username) && !username.equals(nickChosenByOtherPlayer));
    }

    private void correctDataInTextFieldAction(EditableTextField textField) {
        chosenCorrectName = textField.getText();
        clearTextField(textField);
        notifyObservers(GuiEvents.CHOSEN_VALID_NAME);
    }

    public String getChosenCorrectName() {
        return chosenCorrectName;
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
