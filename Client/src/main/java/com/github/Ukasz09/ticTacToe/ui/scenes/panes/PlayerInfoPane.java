package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlBtn;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.IGameBtnProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameImageBtn;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.animated.SignBtnSprite;
import com.github.Ukasz09.ticTacToe.ui.control.textFields.GameTextField;
import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.HashSet;
import java.util.Set;

public class PlayerInfoPane extends FlowPane implements IDrawingGraphic, IGuiObservable {
    private static final double SIGN_PADDING_PROPORTION = 75 / 1080d;
    private static final String FONT_COLOR_CSS_FOR_DISABLE = "darkslategrey";

    private GameImageBtn avatar;
    private SignBtnSprite sign;
    private GameTextField nickField;
    private double headerHeight;
    private double pagePositionX;
    private Set<IGuiObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public PlayerInfoPane(double width, double headerHeight, double pagePositionX) {
        observers = new HashSet<>();
        this.headerHeight = headerHeight;
        this.pagePositionX = pagePositionX;
        setUpPane(width, pagePositionX);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setUpPane(double paneWidth, double pagePositionX) {
        setPaneWidth(paneWidth);
        setOrientation(Orientation.HORIZONTAL);
        setAlignment(Pos.BASELINE_CENTER);
        setLayoutX(pagePositionX);
    }

    private void setPaneWidth(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    public void initialize(ImageView avatar, String nick, ImageSheetProperty signSheet) {
        addNicField(nick);
        addAvatar(avatar);
        addPlayerSign(signSheet);
    }

    private void addNicField(String nick) {
        nickField = new GameTextField(nick, false);
        getChildren().add(nickField);
    }

    private void addAvatar(ImageView avatar) {
        this.avatar = new GameImageBtn(avatar, getAvatarSize(), getAvatarSize());
        getChildren().add(this.avatar);
    }

    private double getAvatarSize() {
        return getWidth() / 2;
    }

    private void addPlayerSign(ImageSheetProperty signSheet) {
        double signSize = getAvatarSize() / 2;
        this.sign = new SignBtnSprite(signSheet, signSize, false);
        sign.setPositionX(getSignCenterPositionX(signSize));
        sign.setPositionY(getSignPositionY());
    }

    private double getSignCenterPositionX(double signSize) {
        return (pagePositionX + getWidth() / 2 - signSize / 2);
    }

    private double getSignPositionY() {
        return nickField.getMinHeight() + getAvatarSize() + headerHeight + ViewManager.getInstance().getScaledHeight(SIGN_PADDING_PROPORTION);
    }

    @Override
    public void render() {
        sign.render();
    }

    @Override
    public void update() {
        sign.update();
    }

    public void disablePane(boolean value) {
        if (value)
            disablePane();
        else enablePage();
    }

    private void disablePane() {
        avatar.setDisable(true);
        avatar.setEffect(IGameBtnProperties.BUTTON_EXITED_EFFECT);
        sign.disable();
        nickField.setFontColor(FONT_COLOR_CSS_FOR_DISABLE);
    }

    private void enablePage() {
        avatar.setDisable(false);
        avatar.setEffect(null);
        sign.enable();
        nickField.setDefaultFontColor();
    }

    public void addGameOverButtons() {
        addRepeatGameButton();
        addStartGameButton();
        addEndGameButton();
    }

    private void addRepeatGameButton() {
        Button button = new GameControlBtn("REPEAT GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.REPEAT_GAME_BTN));
        getChildren().add(button);
    }

    private void addStartGameButton() {
        Button button = new GameControlBtn("NEW GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.START_BTN_CLICKED));
        getChildren().add(button);
    }

    private void addEndGameButton() {
        Button button = new GameControlBtn("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.END_GAME_BTN_CLICKED));
        getChildren().add(button);
    }

    public void setPaneVisible(boolean value) {
        setSignVisible(value);
        setVisible(value);
    }

    public void setSignVisible(boolean value) {
        sign.setVisible(value);
    }

    public void removeAvatarNode() {
        getChildren().remove(avatar);
    }

    public void removeNickFieldNode() {
        getChildren().remove(nickField);
    }

    public void centerButtonInPane() {
        setOrientation(Orientation.VERTICAL);
        setAlignment(Pos.CENTER);
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
}
