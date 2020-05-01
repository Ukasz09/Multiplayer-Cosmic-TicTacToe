package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlBtn;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

import java.util.Arrays;

public class BoardSizePage extends ChoosePage {
    private static final String HEADER_TEXT = "Choose board size ";
    private static final double HGAP_PROPORTION = 50 / 1920d;
    private static final int[] AVAILABLE_BOARD_SIZES = {3, 5, 7};

    private int chosenBoardSize = 0;

    //----------------------------------------------------------------------------------------------------------------//
    public BoardSizePage() {
        super(StartGamePage.GAME_BACKGROUND, HEADER_TEXT, Orientation.HORIZONTAL, getHGap(ViewManager.getInstance()));
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static double getHGap(ViewManager manager) {
        return manager.getScaledWidth(HGAP_PROPORTION);
    }

    private void addButtons() {
        for (int boardSize : AVAILABLE_BOARD_SIZES) {
            Button button = new GameControlBtn(boardSize + " X " + boardSize);
            button.setOnMouseClicked(event -> {
                chosenBoardSize = Integer.parseInt(Arrays.stream(button.getText().split(" X ")).toArray(String[]::new)[0]);
                notifyObservers(GuiEvents.BOARD_SIZE_CHOSEN);
            });
            addToContentPane(button);
        }
    }

    @Override
    public void update() {
        //nothing to do
    }

    public int getChosenBoardSize() {
        return chosenBoardSize;
    }
}
