package com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.pages.*;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.pages.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.pages.GameBoardPage;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GameBoardPage gameBoardPage;
    private StartGamePage startGamePage;
    private NickChoosePage nickChoosePage;
    private AvatarChoosePage avatarChoosePage;
    private SignChoosePage signChoosePage;
    private BoardSizeChoosePage boardSizeChoosePage;

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

    public void showGamePage(int startedPlayerIndex, ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1,
                             ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        initializeGamePage();
        initializeGameBoard(avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
        removeActualSceneFromRoot();
        gameBoardPage.showGameBoard(true);
        gameBoardPage.showVisibleOnlyActualPlayer(startedPlayerIndex);
        actualScene = gameBoardPage;
        setActualSceneVisible(true);
    }

    private void initializeGameBoard(ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1,
                                     ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        gameBoardPage.initializeGameGrid(boardSize, this);
        gameBoardPage.initializePlayerInfoPage(avatar1, nick1, sign1, 0);
        gameBoardPage.initializePlayerInfoPage(avatar2, nick2, sign2, 1);
    }

    private void initializeGamePage() {
        gameBoardPage = new GameBoardPage();
        gameBoardPage.attachObserver(this);
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

    public void changeSceneToNewBoardSizeChoosePage() {
        initializeBoardSizeChoosePage();
        changeScene(boardSizeChoosePage);
    }

    private void initializeBoardSizeChoosePage() {
        boardSizeChoosePage = new BoardSizeChoosePage();
        boardSizeChoosePage.attachObserver(this);
    }

    public void changeSceneToNewNickChoosePage() {
        initializeNickChoosePage();
        changeScene(nickChoosePage);
    }

    private void initializeNickChoosePage() {
        nickChoosePage = new NickChoosePage();
        nickChoosePage.attachObserver(this);
    }

    public void changeSceneToNewAvatarChoosePage(String firstPlayerNick) {
        initializeAvatarsChoosePage();
        avatarChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
        changeScene(avatarChoosePage);
    }

    private void initializeAvatarsChoosePage() {
        avatarChoosePage = new AvatarChoosePage("unknown");
        avatarChoosePage.attachObserver(this);
    }

    public void changeSceneToNewSignChoosePage(String firstPlayerNick) {
        initializeSignChoosePage();
        signChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
        changeScene(signChoosePage);
    }

    private void initializeSignChoosePage() {
        signChoosePage = new SignChoosePage("unknown");
        signChoosePage.attachObserver(this);
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

    public void setActualInitializedPlayerNick(String playerNick) {
        if (avatarChoosePage != null)
            avatarChoosePage.setActualInitializedPlayerNick(playerNick);
        if (signChoosePage != null)
            signChoosePage.setActualInitializedPlayerNick(playerNick);
    }

    private void setActualSceneVisible(boolean value) {
        if (actualScene != null)
            actualScene.setSceneVisible(value);
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoardPage.addPlayerSignToBox(rowIndex, columnIndex, signSheetProperty);
    }


    public void showVisibleOnlyActualPlayer(int playerIndex) {
        if (gameBoardPage != null)
            gameBoardPage.showVisibleOnlyActualPlayer(playerIndex);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        gameBoardPage.changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinGamePage(int winningPlayerIndex, String playerNick) {
        gameBoardPage.changeSceneToWinResultPage(winningPlayerIndex, playerNick);
    }

    public void changeSceneToDrawGamePage() {
        gameBoardPage.changeSceneToDrawResultPage();
    }

    public void denyInteractionWithAllBoxes() {
        gameBoardPage.denyInteractionWithAllBoxes();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public IDrawingGraphic getActualScene() {
        return actualScene;
    }

    public String getLastChosenCorrectName() {
        return nickChoosePage.getLastChosenCorrectName();
    }

    public ImageView getLastChosenAvatar() {
        return avatarChoosePage.getChosenImage();
    }

    public ImageSheetProperty getLastChosenSignSheet() {
        return signChoosePage.getLastChosenSign();
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        notifyObservers(eventKind);
    }

    @Override
    public void attachObserver(IEventKindObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IEventKindObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventKind eventKind) {
        for (IEventKindObserver observer : observers)
            observer.updateObserver(eventKind);
    }

    public Point2D getLastChosenBoxCoords() {
        return gameBoardPage.getLastChosenBoxCoords();
    }

    public int getGameBoardSize() {
        return boardSizeChoosePage.getActualChosenBoardSize();
    }
}
