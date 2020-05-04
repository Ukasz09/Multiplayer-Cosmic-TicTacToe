package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlBtn;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.HeaderTextField;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class FinishGameDecisionPage extends ChoosePage {
    private static final String HEADER_TEXT = "WHAT DO YOU WANT NOW ?";
    private static final double TEXT_FONT_SIZE_PROP = 3 / 108d;

    //----------------------------------------------------------------------------------------------------------------//
    public FinishGameDecisionPage(ImageView avatar, String labelText) {
        super(StartGamePage.GAME_BACKGROUND, HEADER_TEXT, Orientation.VERTICAL, 0);
        addToContentPane(avatar);
        addTextLabel(labelText);
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addTextLabel(String text) {
        int fontSize = (int) (TEXT_FONT_SIZE_PROP * manager.getBottomFrameBorder());
        String labelText = "Other player want to " + text;
        addToContentPane(new HeaderTextField(labelText, fontSize, false));
    }

    private void addButtons() {
        addStartGameBtn();
        addRepeatGameBtn();
        addEndGameBtn();
    }

    private void addStartGameBtn() {
        Button button = new GameControlBtn("NEW GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.START_BTN_CLICKED));
        addToContentPane(button);
    }

    private void addRepeatGameBtn() {
        Button button = new GameControlBtn("REPEAT GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.REPEAT_GAME_BTN));
        addToContentPane(button);
    }

    private void addEndGameBtn() {
        Button button = new GameControlBtn("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.END_GAME_BTN_CLICKED));
        addToContentPane(button);
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
