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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IGuiObservable, IGuiObserver {
    private IScenePage actualScene;
    private Set<IGuiObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public PagesManager() {
        observers = new HashSet<>();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void showHomePage() {
        stopBackgroundSound();
        setSceneToHomePage();
        setActualSceneVisible(true);
        playBackgroundSound();
    }

    private void setSceneToHomePage() {
        StartGamePage gamePage = new StartGamePage();
        gamePage.attachObserver(this);
        changeScene(gamePage);
    }

    public void sceneToGamePage(int startedPlayerIndex, ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        GameBoardPage gamePage = getGamePage(avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize, startedPlayerIndex);
        gamePage.showVisibleOnlyActualPlayer(startedPlayerIndex);
        changeScene(gamePage);
    }

    private GameBoardPage getGamePage(ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize, int startedPlayerId) {
        GameBoardPage gameBoardPage = new GameBoardPage();
        gameBoardPage.attachObserver(this);
        gameBoardPage.initializeGameGrid(boardSize, this);
        gameBoardPage.initLeftPlayerPane(avatar1, nick1, sign1, startedPlayerId);
        gameBoardPage.initRightPlayerPane(avatar2, nick2, sign2, Gui.getNextPlayerNumber(startedPlayerId));
        return gameBoardPage;
    }

    private void playBackgroundSound() {
        if (actualScene != null)
            actualScene.playBackgroundSound();
    }

    private void stopBackgroundSound() {
        if (actualScene != null)
            actualScene.stopBackgroundSound();
    }

    public void sceneToBoardSizePage() {
        BoardSizePage boardSizePage = new BoardSizePage();
        boardSizePage.attachObserver(this);
        changeScene(boardSizePage);
    }

    public void sceneToNickPage(String otherPlayerNick) {
        NickPage nickPage = new NickPage(otherPlayerNick);
        nickPage.attachObserver(this);
        changeScene(nickPage);
    }

    public void sceneToAvatarPage(Image disabledAvatar) {
        AvatarPage avatarPage = new AvatarPage(disabledAvatar);
        avatarPage.attachObserver(this);
        changeScene(avatarPage);
    }

    public void sceneToSignPage(ImageSheetProperty disabledSign) {
        SignPage signPage = new SignPage(disabledSign);
        signPage.attachObserver(this);
        changeScene(signPage);
    }

    public void sceneToWaitingPage() {
        changeScene(new WaitingPage());
    }

    private void changeScene(IScenePage page) {
        removeActualSceneFromRoot();
        actualScene = page;
        setActualSceneVisible(true);
        System.gc();
    }

    private void removeActualSceneFromRoot() {
        if (actualScene != null)
            actualScene.removeFromActionNodes();
    }

    private void setActualSceneVisible(boolean value) {
        if (actualScene != null)
            actualScene.setSceneVisible(value);
    }

    public void showVisibleOnlyActualPlayerPane(int actualPlayerNumber) {
        ((GameBoardPage) actualScene).showVisibleOnlyActualPlayer(actualPlayerNumber);
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        ((GameBoardPage) actualScene).getGameBoard().addPlayerSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        ((GameBoardPage) actualScene).getGameBoard().changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinGamePage(int winningPlayerNumber) {
        ((GameBoardPage) actualScene).sceneToWinResultPage(winningPlayerNumber);
    }

    public void changeSceneToLoseGamePage(int losePlayerNumber) {
        ((GameBoardPage) actualScene).sceneToLoseResultPage(losePlayerNumber);
    }

    public void changeSceneToDrawGamePage() {
        ((GameBoardPage) actualScene).changeSceneToDrawResultPage();
    }

    public void interactionWithAllBoxes(boolean allowed) {
        ((GameBoardPage) actualScene).getGameBoard().interactionWithAllBoxes(allowed);
    }

    public void changeAllGridBoxStates(SpriteStates state) {
        ((GameBoardPage) actualScene).getGameBoard().changeAllGridBoxState(state);
    }

    public void changeGameBoardPageHeader(String headerText) {
        ((GameBoardPage) actualScene).setHeaderText(headerText);
    }

    public IDrawingGraphic getActualScene() {
        return actualScene;
    }

    public String getChosenCorrectName() {
        return ((NickPage) actualScene).getChosenCorrectName();
    }

    public ImageView getAvatarImage(int avatarIndex) {
        return AvatarPage.getAvatarImage(avatarIndex);
    }

    public int getChosenAvatarNumber() {
        return ((AvatarPage) actualScene).getChosenAvatarId();
    }

    public int getChosenSignId() {
        return ((SignPage) actualScene).getChosenSignNumber();
    }

    public ImageSheetProperty getChosenSignSheet() {
        return SignPage.getSign(((SignPage) actualScene).getChosenSignNumber());
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
        return ((GameBoardPage) actualScene).getGameBoard().getLastChosenBoxCoords();
    }

    public int getGameBoardSize() {
        return ((BoardSizePage) actualScene).getChosenBoardSize();
    }

    public void disableInteractionWithBox(int rowIndex, int columnIndex, boolean value, boolean withRemovingFromRoot) {
        ((GameBoardPage) actualScene).getGameBoard().disableInteractionWithBox(rowIndex, columnIndex, value, withRemovingFromRoot);
    }

}
