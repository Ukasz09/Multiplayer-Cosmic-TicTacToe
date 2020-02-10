package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IScenePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.AvatarChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.SignChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage.GamePage;

import java.util.HashSet;
import java.util.Set;

public class PagesManager implements IEventKindObservable, IEventKindObserver {
    private IScenePage actualScene;
    private Set<IEventKindObserver> observers;

    private GamePage gamePanel;
    private SignChoosePage signChoosePanel;
    private AvatarChoosePage avatarChoosePage;

    public PagesManager() {
        observers = new HashSet<>();
        initializeAvatarsChoosePage();
        initializeSignChoosePage();
        initializeGamePanel();
    }

    //todo: tmp hard name
    private void initializeAvatarsChoosePage() {
        avatarChoosePage = new AvatarChoosePage("Lukasz");
        avatarChoosePage.attachObserver(this);
        avatarChoosePage.setVisible(false);
    }

    private void initializeSignChoosePage() {
        signChoosePanel = new SignChoosePage();
        signChoosePanel.attachObserver(this);
        signChoosePanel.setVisible(false);
    }

    private void initializeGamePanel() {
        gamePanel = new GamePage(new GameBackground());
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
        actualScene = avatarChoosePage;
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
