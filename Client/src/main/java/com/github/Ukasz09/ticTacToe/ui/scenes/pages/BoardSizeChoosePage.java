package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlButton;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

import java.util.Arrays;

public class BoardSizeChoosePage extends ChoosePage {
    private static final String HEADER_TEXT_PREFIX = "Choose board size ";
    private static final double HORIZONTAL_GAP_PROPORTION = 50 / 1920d;
    private static final int[] AVAILABLE_BOARD_SIZES = {3, 5, 7};

    private int actualChosenBoardSize = 0;

    //----------------------------------------------------------------------------------------------------------------//
    public BoardSizeChoosePage() {
        super(StartGamePage.GAME_BACKGROUND, HEADER_TEXT_PREFIX, Orientation.HORIZONTAL, getHGap(ViewManager.getInstance()));
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static double getHGap(ViewManager manager) {
        return manager.getScaledWidth(HORIZONTAL_GAP_PROPORTION);
    }

    private void addButtons() {
        for (int boardSize : AVAILABLE_BOARD_SIZES) {
            Button button = new GameControlButton(boardSize + " X " + boardSize);
            button.setOnMouseClicked(event -> {
                String buttonText = button.getText();
                actualChosenBoardSize = Integer.parseInt(Arrays.stream(buttonText.split(" X ")).toArray(String[]::new)[0]);
                notifyObservers(GuiEvents.BOARD_SIZE_CHOSEN);
            });
            addToContentPane(button);
        }
    }

    @Override
    public void update() {
        //nothing to do
    }

    public int getActualChosenBoardSize() {
        return actualChosenBoardSize;
    }
}
