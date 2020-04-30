package com.github.Ukasz09.ticTacToe.ui.scenes;

import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.scenes.pages.*;
import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToe.ui.scenes.pages.IScenePage;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.scenes.pages.GameBoardPage;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IGuiObservable, IGuiObserver {
    private IScenePage actualScene;
    private Set<IGuiObserver> observers;

    private GameBoardPage gameBoardPage;
    private StartGamePage startGamePage;
    private NickPage nickPage;
    private AvatarPage avatarPage;
    private SignPage signPage;
    private BoardSizePage boardSizePage;

    //----------------------------------------------------------------------------------------------------------------//
    public PagesManager() {
        observers = new HashSet<>();
        initializeStartGamePage();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializeStartGamePage() {
        startGamePage = new StartGamePage();
        startGamePage.attachObserver(this);
    }

    public void showGamePage(int startedPlayerIndex, ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        initializeGamePage(avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize, startedPlayerIndex);
        removeActualSceneFromRoot();
        gameBoardPage.showGameBoard(true);
        gameBoardPage.showVisibleOnlyActualPlayer(startedPlayerIndex);
        actualScene = gameBoardPage;
        setActualSceneVisible(true);
    }

    private void initializeGamePage(ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize, int startedPlayerId) {
        gameBoardPage = new GameBoardPage();
        gameBoardPage.attachObserver(this);
        gameBoardPage.initializeGameGrid(boardSize, this);
        gameBoardPage.initializePlayerInfoPage(avatar1, nick1, sign1, startedPlayerId);
        gameBoardPage.initializePlayerInfoPage(avatar2, nick2, sign2, Gui.getNextPlayerNumb(startedPlayerId));
        gameBoardPage.setStartedPlayerId(startedPlayerId);
    }

    public void showHomePage() {
        stopBackgroundSound();
        setSceneToHomePage();
        setActualSceneVisible(true);
        playBackgroundSound();
    }

    private void playBackgroundSound() {
        if (actualScene != null)
            actualScene.playBackgroundSound();
    }

    private void stopBackgroundSound() {
        if (actualScene != null)
            actualScene.stopBackgroundSound();
    }

    private void setSceneToHomePage() {
        if (actualScene != startGamePage) {
            removeActualSceneFromRoot();
            actualScene = startGamePage;
        }
    }

    public void sceneToBoardSizePage() {
        initBoardSizePage();
        changeScene(boardSizePage);
    }

    private void initBoardSizePage() {
        boardSizePage = new BoardSizePage();
        boardSizePage.attachObserver(this);
    }

    public void sceneToNickPage() {
        initNickPage();
        changeScene(nickPage);
    }

    private void initNickPage() {
        nickPage = new NickPage();
        nickPage.attachObserver(this);
    }

    public void sceneToAvatarPage(String playerNick) {
        initializeAvatarPage(playerNick);
        changeScene(avatarPage);
    }

    private void initializeAvatarPage(String playerNick) {
        avatarPage = new AvatarPage(playerNick);
        avatarPage.attachObserver(this);
    }

    public void sceneToSignPage() {
        initSignPage();
        changeScene(signPage);
    }

    private void initSignPage() {
        signPage = new SignPage();
        signPage.attachObserver(this);
    }

    private void changeScene(IScenePage page) {
        removeActualSceneFromRoot();
        actualScene = page;
        setActualSceneVisible(true);
    }

    private void removeActualSceneFromRoot() {
        if (actualScene != null)
            actualScene.removeFromActionNodes();
    }

    //todo: tmp - pozniej zmienic na wait_scene i private
    public void setActualSceneVisible(boolean value) {
        if (actualScene != null)
            actualScene.setSceneVisible(value);
    }

    public void showVisibleOnlyActualPlayerPane(int actualPlayerNumber) {
        gameBoardPage.showVisibleOnlyActualPlayer(actualPlayerNumber);
    }

    //todo: zmienic na optmalna wersje
    public void addPlayerSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoardPage.getGameBoard().addPlayerSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        gameBoardPage.getGameBoard().changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinGamePage(int winningPlayerNumber, String playerNick) {
        gameBoardPage.changeSceneToWinResultPage(winningPlayerNumber, playerNick);
    }

    public void changeSceneToDrawGamePage() {
        gameBoardPage.changeSceneToDrawResultPage();
    }

    //todo: zmienic na narzucenie jakiegos page ze aktualnie ruch przeciwnika
    public void interactionWithAllBoxes(boolean allowed) {
        gameBoardPage.interactionWithAllBoxes(allowed);
    }

    public IDrawingGraphic getActualScene() {
        return actualScene;
    }

    public String getChosenCorrectName() {
        return nickPage.getLastChosenCorrectName();
    }

    public ImageView getAvatarImage(int avatarIndex) {
        return AvatarPage.getAvatarImage(avatarIndex);
    }

    public int getChosenAvatarId() {
        return avatarPage.getChosenAvatarId();
    }

    public int getChosenSignId() {
        return signPage.getChosenSignId();
    }

    public ImageSheetProperty getChosenSignSheet() {
        return SignPage.getSign(signPage.getChosenSignId());
    }

    public ImageSheetProperty getSignSheet(int signId) {
        return SignPage.getSign(signId);
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        notifyObservers(guiEvents);
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GuiEvents event) {
        for (IGuiObserver observer : observers)
            observer.updateGuiObserver(event);
    }

    public Point2D getLastChosenBoxCoords() {
        return gameBoardPage.getLastChosenBoxCoords();
    }

    public int getGameBoardSize() {
        return boardSizePage.getActualChosenBoardSize();
    }
}
