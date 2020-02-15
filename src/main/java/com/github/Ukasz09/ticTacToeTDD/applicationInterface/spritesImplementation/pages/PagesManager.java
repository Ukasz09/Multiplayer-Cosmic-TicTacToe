package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.*;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage.GamePage;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GamePage gamePanel;
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
        gamePanel = new GamePage();
        gamePanel.setVisible(false);
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

    public void showGamePage() {
        setActualSceneVisible(false);
        gamePanel.showGameBoard(true);
        actualScene = gamePanel;
        setActualSceneVisible(true);
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
}
