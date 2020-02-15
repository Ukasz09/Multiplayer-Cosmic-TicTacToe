package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.control.buttons.GameControlButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.control.textFields.GameTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BoardSizeChoose extends ChoosePage {
    private static final String HEADER_TEXT_PREFIX = "Choose board size ";
    private static final double HORIZONTAL_GAP_PROPORTION = 50 / 1920d;
    private static final int[] AVAILABLE_BOARD_SIZES = {3, 5, 7, 9};

    private int actualChosenBoardSize = 0;

    //----------------------------------------------------------------------------------------------------------------//
    public BoardSizeChoose() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND), HEADER_TEXT_PREFIX);
        getContentPane().setHgap(manager.getScaledWidth(HORIZONTAL_GAP_PROPORTION));
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addButtons() {
        for (int boardSize : AVAILABLE_BOARD_SIZES) {
            Button button = new GameControlButton(boardSize + " X " + boardSize);
            button.setOnMouseClicked(event -> {
                String buttonText = button.getText();
                actualChosenBoardSize = Integer.parseInt(Arrays.stream(buttonText.split(" X ")).toArray(String[]::new)[0]);
                notifyObservers(EventKind.BOARD_SIZE_CHOSEN);
            });
            getContentPane().getChildren().add(button);
        }
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
