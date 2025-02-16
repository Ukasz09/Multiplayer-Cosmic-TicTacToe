package com.github.Ukasz09.ticTacToe.ui.scenes;

import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.ViewManager;
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
    private int boardSize = 0;
    private ViewManager manager = ViewManager.getInstance();

    //----------------------------------------------------------------------------------------------------------------//
    public PagesManager() {
        observers = new HashSet<>();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void sceneToServerAddrPage() {
        ServerAddrPage addrPage = new ServerAddrPage();
        addrPage.attachObserver(this);
        changeScene(addrPage);
    }

    public void sceneToEndGamePage() {
        sceneToEndGamePage("");
    }

    public void sceneToEndGamePage(String optionalMsg) {
        manager.clearActionNodes();
        EndGamePage endPage = new EndGamePage(optionalMsg);
        changeScene(endPage);
    }

    public void showHomePage() {
        sceneToHomePage();
        setActualSceneVisible(true);
    }

    private void sceneToHomePage() {
        manager.clearActionNodes();
        StartGamePage gamePage = new StartGamePage();
        gamePage.attachObserver(this);
        changeScene(gamePage);
    }

    public void sceneToFinishGameDecisionPage(ImageView avatar, String labelText) {
        manager.clearActionNodes();
        FinishGameDecisionPage decisionPage = new FinishGameDecisionPage(avatar, labelText);
        decisionPage.attachObserver(this);
        changeScene(decisionPage);
    }

    public void sceneToGamePage(int startedPlayerIndex, ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2) {
        manager.clearActionNodes();
        System.gc();
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

    public void sceneToBoardSizePage() {
        manager.clearActionNodes();
        BoardSizePage boardSizePage = new BoardSizePage();
        boardSizePage.attachObserver(this);
        changeScene(boardSizePage);
    }

    public void sceneToNickPage(String otherPlayerNick) {
        manager.clearActionNodes();
        NickPage nickPage = new NickPage(otherPlayerNick);
        nickPage.attachObserver(this);
        changeScene(nickPage);
    }

    public void sceneToAvatarPage(Image disabledAvatar) {
        manager.clearActionNodes();
        AvatarPage avatarPage = new AvatarPage(disabledAvatar);
        avatarPage.attachObserver(this);
        changeScene(avatarPage);
    }

    public void sceneToSignPage(ImageSheetProperty disabledSign) {
        manager.clearActionNodes();
        SignPage signPage = new SignPage(disabledSign);
        signPage.attachObserver(this);
        changeScene(signPage);
    }

    public void sceneToWaitingPage() {
        manager.clearActionNodes();
        changeScene(new WaitingPage());
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

    public void animationIsEnable(boolean value) {
        ((GameBoardPage) actualScene).getGameBoard().animationIsEnable(value);
    }

    public void showWinResultPane(int winningPlayerNumber) {
        ((GameBoardPage) actualScene).sceneToWinResultPage(winningPlayerNumber);
    }

    public void showLoseResultPane(int losePlayerNumber) {
        ((GameBoardPage) actualScene).sceneToLoseResultPage(losePlayerNumber);
    }

    public void showDrawResultPane() {
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

    public String getServerAddr() {
        return ((ServerAddrPage) actualScene).getIpAddress();
    }

    public void incorrectServerAddress() {
        ((ServerAddrPage) actualScene).incorrectAddressAction();
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        if (guiEvents == GuiEvents.BOARD_SIZE_CHOSEN)
            boardSize = ((BoardSizePage) actualScene).getChosenBoardSize();
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
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void disableInteractionWithBox(int rowIndex, int columnIndex, boolean value, boolean withRemovingFromRoot) {
        ((GameBoardPage) actualScene).getGameBoard().disableInteractionWithBox(rowIndex, columnIndex, value, withRemovingFromRoot);
    }

}
