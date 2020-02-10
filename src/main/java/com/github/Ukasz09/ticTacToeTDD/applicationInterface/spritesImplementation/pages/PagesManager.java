package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.SignButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.others.PlayerViewProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.SignChoosePanel;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage.GamePanel;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GamePanel gamePanel;
    private SignChoosePanel signChoosePanel;

    public PagesManager() {
        observers = new HashSet<>();
        initializeHomePage();
        initializeGamePanel();
    }

    private void initializeHomePage() {
        signChoosePanel = new SignChoosePanel();
        signChoosePanel.attachObserver(this);
        signChoosePanel.setVisible(false);
    }

    private void initializeGamePanel() {
        gamePanel = new GamePanel(new GameBackground());
        gamePanel.setVisible(false);
    }

    public void showHomePage() {
        if (actualScene != null)
            actualScene.setVisible(false);
        setSceneToHomePage();
        actualScene.setVisible(true);
    }

    public void showGamePage() {
        actualScene.setVisible(false);
        gamePanel.showGameBoard(true);
        actualScene = gamePanel;
        actualScene.setVisible(true);
    }

    private void setSceneToHomePage() {
        actualScene = signChoosePanel;
    }

    public IDrawingGraphic getActualScene() {
        return actualScene;
    }

    public ImageSheetProperty getSignSheetFromButton(EventKind buttonKind) {
        return signChoosePanel.getSignSheetFromButton(buttonKind);
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
