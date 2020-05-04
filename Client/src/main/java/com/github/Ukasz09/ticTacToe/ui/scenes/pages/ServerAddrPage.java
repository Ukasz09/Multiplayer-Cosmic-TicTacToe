package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.EditableTextField;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.HeaderTextField;
import javafx.geometry.Orientation;
import javafx.scene.input.KeyCode;

public class ServerAddrPage extends ChoosePage {
    private static final String LABEL_TEXT = "Input server address";
    private static final String PROMPT_TEXT = "IPv4 address:";
    private static final String INCORRECT_ADDR_TXT = "Incorrect server address or server is down";
    private static final String WAITING_TXT = "Trying to connect. Please wait..";
    private static final double LABEL_FONT_SIZE_PROP = 3 / 108d;

    private String serverIp;
    private EditableTextField addressField;
    private HeaderTextField infoField;

    private boolean needToNotifyObserver = false;
    private boolean waitTextIsShown = false;

    //-----------------------------------------------------------------------------------------------------------------//
    public ServerAddrPage() {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        initTextField();
        initIncorrectDataLabel();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initTextField() {
        addressField = new EditableTextField(PROMPT_TEXT);
        setOnEnterKeyPressedEvent(addressField);
        addToContentPane(addressField);
    }

    private void initIncorrectDataLabel() {
        int fontSize = (int) (LABEL_FONT_SIZE_PROP * manager.getBottomFrameBorder());
        infoField = new HeaderTextField(INCORRECT_ADDR_TXT, fontSize, false);
        infoField.setVisible(false);
        addToContentPane(infoField);
    }

    private void setOnEnterKeyPressedEvent(EditableTextField textField) {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                serverIp = textField.getText();
                needToNotifyObserver = true;
            }
        });
    }

    @Override
    public void update() {
        //because without it waiting text will not be refresh and seen while trying to connect
        if (needToNotifyObserver) {
            if (!waitTextIsShown) {
                infoField.setText(WAITING_TXT);
                infoField.setVisible(true);
                waitTextIsShown = true;
            } else {
                needToNotifyObserver = false;
                waitTextIsShown = false;
                notifyObservers(GuiEvents.CHOSEN_IP_ADDR);
            }
        }
    }

    public String getIpAddress() {
        return serverIp;
    }

    public void incorrectAddressAction() {
        addressField.applyIncorrectDataEffect();
        infoField.setText(INCORRECT_ADDR_TXT);
        infoField.setVisible(true);
    }
}
