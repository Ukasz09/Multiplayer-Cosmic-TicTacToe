package com.github.Ukasz09.ticTacToe.logic.guiObserver;


import com.github.Ukasz09.ticTacToe.logic.client.Messages;

public enum GuiEvents {
    SIGN_BTN_CLICKED(Messages.SIGN_BTN_CLICKED),
    AVATAR_BTN_CLICKED(Messages.AVATAR_BTN_CLICKED),
    CHOSEN_VALID_NAME(Messages.CHOSEN_VALID_NAME),
    START_BTN_CLICKED(Messages.START_BTN_CLICKED),
    BOARD_SIZE_CHOSEN(Messages.BOARD_SIZE_CHOSEN),
    BOX_BTN_CLICKED(Messages.BOX_BTN_CLICKED),
    END_GAME_BTN_CLICKED(Messages.END_GAME_BTN_CLICKED),
    REPEAT_GAME_BTN(Messages.REPEAT_GAME_BTN),
    RESPONSE_READ("None");

    //----------------------------------------------------------------------------------------------------------------//
    private String msg;

    GuiEvents(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
