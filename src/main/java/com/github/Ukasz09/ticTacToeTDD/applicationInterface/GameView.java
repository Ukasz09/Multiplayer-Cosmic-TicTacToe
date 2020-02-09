package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.GameBoard;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.SignChoosePanel;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.buttons.SignButton;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.*;

import javafx.animation.AnimationTimer;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private ViewManager manager;
    private IDrawingGraphic actualScene;

    //todo: game panel zrobic, ktory bedzie przechowywal gamboarda i backgrounda
    private MyBackground gameBackground;
    private GameBoard gameBoard;
    private SignChoosePanel signChoosePanel;

    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            actualScene.render();
            actualScene.update();
        }
    }

    public GameView() {
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, true);
    }

    public void startGame() {
        initializeGameApplication();
        manager.getMainStage().show();
        new GameAnimationTimer().start();
    }

    private void initializeGameApplication() {
        signChoosePanel = new SignChoosePanel();
        gameBackground = new GameBackground();
        gameBoard = new GameBoard();
        gameBoard.setVisible(false);
        setSceneToNewGame();
    }

    private void setSceneToNewGame() {
        actualScene = signChoosePanel;
    }

    public void attachObserver(IEventKindObserver observer) {
        signChoosePanel.attachObserver(observer);
    }

    public SignButton getSignButton(EventKind buttonKind) {
        return signChoosePanel.getSignButton(buttonKind);
    }
}
