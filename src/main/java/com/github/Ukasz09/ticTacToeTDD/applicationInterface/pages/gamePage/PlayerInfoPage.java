package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.GameControlButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.IGameButtonProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.ImageButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.SignButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.GameTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.LabelTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.Confetti;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class PlayerInfoPage extends FlowPane implements IDrawingGraphic, IEventKindObservable {
    private static final double SIGN_PADDING_PROPORTION = 75 / 1080d;
    private static final String FONT_COLOR_CSS_FOR_DISABLE = "darkslategrey";

    private ImageButton avatar;
    private SignButtonSprite sign;
    private LabelTextField nickField;
    private final double headerHeight;
    private final double pagePositionX;
    private Set<IEventKindObserver> observers;
    private Confetti confetti;
    //    private Confetti confetti = null;

    //----------------------------------------------------------------------------------------------------------------//
    public PlayerInfoPage(double width, double headerHeight, double pagePositionX) {
        observers = new HashSet<>();
        this.headerHeight = headerHeight;
        this.pagePositionX = pagePositionX;
        setPageSize(width);
        setOrientation(Orientation.HORIZONTAL);
        setAlignment(Pos.BASELINE_CENTER);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setPageSize(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    public void initialize(ImageView avatarImageView, String nick, ImageSheetProperty signSheetProperty) {
        addNicField(nick);
        addAvatar(avatarImageView);
        addSign(signSheetProperty);
    }

    private void addAvatar(ImageView avatarImageView) {
        this.avatar = new ImageButton(avatarImageView, getAvatarSize(), getAvatarSize());
        getChildren().add(avatar);
    }

    private void addNicField(String nick) {
        nickField = new LabelTextField(nick, false);
        getChildren().add(nickField);
    }

    private void addSign(ImageSheetProperty signSheetProperty) {
        double signSize = getAvatarSize() / 2;
        this.sign = new SignButtonSprite(signSheetProperty, signSize, false);
        sign.setPositionX(getSignCenterPositionX(signSize));
        sign.setPositionY(getSignPositionY());
    }

    private double getSignCenterPositionX(double signSize) {
        return (pagePositionX + getWidth() / 2 - signSize / 2);
    }

    private double getSignPositionY() {
        return nickField.getMinHeight() + getAvatarSize() + headerHeight + ViewManager.getInstance().getScaledHeight(SIGN_PADDING_PROPORTION);
    }

    private double getAvatarSize() {
        return getWidth() / 2;
    }

    @Override
    public void render() {
        sign.render();
        if (confetti != null) confetti.render();
    }

    @Override
    public void update() {
        sign.update();
//        if (confetti != null)
//            confetti.update();
    }

    public void disablePage(boolean value) {
        if (value)
            disablePage();
        else enablePage();
    }

    private void disablePage() {
        avatar.setDisable(true);
        avatar.setEffect(IGameButtonProperties.BUTTON_EXITED_EFFECT);
        sign.disable();
        nickField.setFontColor(FONT_COLOR_CSS_FOR_DISABLE);
    }

    private void enablePage() {
        avatar.setDisable(false);
        avatar.setEffect(null);
        sign.enable();
        nickField.setDefaultFontColor();
    }

    public double getPagePositionX() {
        return pagePositionX;
    }

    public void removeSignSpriteFromRoot() {
        sign.removeNodeFromRoot();
    }

    public void setSignVisible(boolean value) {
        sign.setVisible(value);
    }

    public void addWinButtons() {
        addRepeatGameButton();
        addStartGameButton();
        addEndGameButton();
    }

    private void addRepeatGameButton() {
        Button button = new GameControlButton("REPEAT GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.REPEAT_GAME_BUTTON));
        getChildren().add(button);
    }

    private void addStartGameButton() {
        Button button = new GameControlButton("NEW GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.START_BUTTON_CLICKED));
        getChildren().add(button);
    }

    private void addEndGameButton() {
        Button button = new GameControlButton("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.END_GAME_BUTTON_CLICKED));
        getChildren().add(button);
    }

    public void addConfetti(double windowWidth, double windowHeight) {
        confetti = new Confetti(windowWidth, windowHeight, 0, 0);
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
