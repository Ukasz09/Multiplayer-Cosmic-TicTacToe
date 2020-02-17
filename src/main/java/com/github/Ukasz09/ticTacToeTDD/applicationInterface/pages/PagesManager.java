package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.*;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage.GamePage;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GamePage gamePage;
    private StartGamePage startGamePage;
    private NickChoosePage nickChoosePage;
    private AvatarChoosePage avatarChoosePage;
    private SignChoosePage signChoosePanel;
    private BoardSizeChoose boardSizeChoosePage;

    //----------------------------------------------------------------------------------------------------------------//
    public PagesManager() {
        observers = new HashSet<>();
        initializeBoardSizeChoosePage();
        initializeStartGamePage();
        initializeNameChoosePage();
        initializeAvatarsChoosePage();
        initializeSignChoosePage();
        initializeGamePanel();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializeBoardSizeChoosePage() {
        boardSizeChoosePage = new BoardSizeChoose();
        boardSizeChoosePage.attachObserver(this);
        boardSizeChoosePage.setVisible(false);
    }

    private void initializeStartGamePage() {
        startGamePage = new StartGamePage();
        startGamePage.attachObserver(this);
        startGamePage.setVisible(false);
    }

    private void initializeNameChoosePage() {
        nickChoosePage = new NickChoosePage();
        nickChoosePage.attachObserver(this);
        nickChoosePage.setVisible(false);
    }

    private void initializeAvatarsChoosePage() {
        avatarChoosePage = new AvatarChoosePage("unknown");
        avatarChoosePage.attachObserver(this);
        avatarChoosePage.setVisible(false);
    }

    private void initializeSignChoosePage() {
        signChoosePanel = new SignChoosePage("unknown");
        signChoosePanel.attachObserver(this);
        signChoosePanel.setVisible(false);
    }

    private void initializeGamePanel() {
        gamePage = new GamePage();
        gamePage.setVisible(false);
    }

    public void showHomePage() {
        setActualSceneVisible(false);
        stopBackgroundSound();
        setSceneToHomePage();
        setActualSceneVisible(true);
        playBackgroundSound();
    }

    private void setSceneToHomePage() {
        actualScene = startGamePage;
    }

    public void showBoardSizeChoosePage() {
        setActualSceneVisible(false);
        actualScene = boardSizeChoosePage;
        setActualSceneVisible(true);
    }

    public void showNickChoosePage() {
        setActualSceneVisible(false);
        actualScene = nickChoosePage;
        setActualSceneVisible(true);
    }

    public void showAvatarChoosePage(String firstPlayerNick) {
        setActualSceneVisible(false);
        avatarChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
        actualScene = avatarChoosePage;
        setActualSceneVisible(true);
    }

    public void setActualInitializedPlayerNick(String firstPlayerNick) {
        avatarChoosePage.setActualInitializedPlayerNick(firstPlayerNick);
        signChoosePanel.setActualInitializedPlayerNick(firstPlayerNick);
    }

    public void showSignChoosePage(String firstPlayerNick) {
        setActualSceneVisible(false);
        signChoosePanel.setActualInitializedPlayerNick(firstPlayerNick);
        actualScene = signChoosePanel;
        setActualSceneVisible(true);
    }

    public void showGamePage(int startedPlayerIndex) {
        setActualSceneVisible(false);
        gamePage.showGameBoard(true);
        gamePage.showVisibleOnlyActualPlayer(startedPlayerIndex);
        actualScene = gamePage;
        setActualSceneVisible(true);
    }

    public void initializeGameBoard(
            ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        gamePage.initializeGameGrid(boardSize, this);
        gamePage.initializePlayerInfoPage(avatar1, nick1, sign1, true);
        gamePage.initializePlayerInfoPage(avatar2, nick2, sign2, false);
    }

    private void setActualSceneVisible(boolean value) {
        if (actualScene != null)
            actualScene.setSceneVisible(value);
    }

    private void playBackgroundSound() {
        if (actualScene != null)
            actualScene.playBackgroundSound();
    }

    private void stopBackgroundSound() {
        if (actualScene != null)
            actualScene.stopBackgroundSound();
    }

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
        return signChoosePanel.getLastChosenSign();
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
        return gamePage.getLastChosenBoxCoords();
    }

    public void addSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gamePage.addSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public int getGameBoardSize() {
        return boardSizeChoosePage.getActualChosenBoardSize();
    }

    public void showVisibleOnlyActualPlayer(int playerIndex){
        gamePage.showVisibleOnlyActualPlayer(playerIndex);
    }
}
