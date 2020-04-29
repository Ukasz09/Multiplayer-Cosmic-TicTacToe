package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.animated.SignButtonSprite;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;

public class SignChoosePage extends ChoosePage {
    private static final ImageSheetProperty[] DEFAULT_SHEET_PROPERTIES = SpritesProperties.signSheetsProperties();
    private static final double BUTTONS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT_PREFIX = "Choose game sign for player: ";

    private SignButtonSprite[] signButtonSprites;
    private ImageSheetProperty lastChosenSign = null;
    private String actualInitializedPlayerNick;
    private int lastChosenSignId = -1;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignChoosePage(String firstPlayerName) {
        super(StartGamePage.GAME_BACKGROUND, String.format("%s%s", LABEL_TEXT_PREFIX, firstPlayerName), Orientation.HORIZONTAL, 0);
        actualInitializedPlayerNick = firstPlayerName;
        addSignButtons();
        setSceneVisible(false);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public void enable() {
        setSceneVisible(true);
        for (SignButtonSprite s : signButtonSprites) {
            s.enable();
            s.setVisible(true);
        }
    }

    private void addSignButtons() {
        int signButtonsQty = DEFAULT_SHEET_PROPERTIES.length;
        signButtonSprites = new SignButtonSprite[signButtonsQty];
        for (int i = 0; i < signButtonsQty; i++) {
            SignButtonSprite signButtonSprite = new SignButtonSprite(DEFAULT_SHEET_PROPERTIES[i], true);
            signButtonSprites[i] = signButtonSprite;
            addSignButtonEventHandler(signButtonSprite);
            signButtonSprite.setVisible(false);
            signButtonSprite.disable();
        }
        setSignButtonsCorrectPositions(manager.getScaledWidth(BUTTONS_PADDING_TO_SCREEN_PROPORTION));
    }

    private void addSignButtonEventHandler(SignButtonSprite signButtonSprite) {
        signButtonSprite.addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (signButtonSprite.isActive()) {
                lastChosenSign = signButtonSprite.getSpriteSheetProperty();
                setLastChosenId(signButtonSprite);
                signButtonSprite.disable();
                signButtonSprite.notifyObservers(GuiEvents.SIGN_BTN_CLICKED);
            }
        });
    }

    //todo: tmp: dac mape
    private void setLastChosenId(SignButtonSprite signButtonSprite) {
        for (int i = 0; i < signButtonSprites.length; i++)
            if (signButtonSprites[i].equals(signButtonSprite)) {
                lastChosenSignId = i;
                return;
            }
    }

    private void setSignButtonsCorrectPositions(double buttonPadding) {
        double nextButtonPositionX = getFirstButtonXPositionToCenterWithOthers(signButtonSprites.length, buttonPadding, signButtonSprites[0].getWidth());
        double nextButtonPositionY = getButtonCenterYPositionInContentPane(signButtonSprites[0].getHeight());

        for (SignButtonSprite button : signButtonSprites) {
            button.setPositionX(nextButtonPositionX);
            nextButtonPositionX += (button.getWidth() + buttonPadding);
            button.setPositionY(nextButtonPositionY);
        }
    }

    private double getButtonCenterYPositionInContentPane(double buttonHeight) {
        double labelPaneHeight = getHeaderPaneHeight();
        return labelPaneHeight + (manager.getBottomFrameBorder() - labelPaneHeight) / 2 - buttonHeight / 2;
    }

    @Override
    public void render() {
        super.render();
        renderSignButtons();
    }

    private void renderSignButtons() {
        for (SignButtonSprite button : signButtonSprites)
            button.render();
    }

    @Override
    public void update() {
        updateSignButtons();
        setHeaderText(LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    private void updateSignButtons() {
        for (SignButtonSprite button : signButtonSprites)
            button.update();
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        super.attachObserver(observer);
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IGuiObserver observer) {
        for (SignButtonSprite button : signButtonSprites)
            button.attachObserver(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        super.detachObserver(observer);
        removeSignButtonsObserver(observer);
    }

    private void removeSignButtonsObserver(IGuiObserver observer) {
        for (SignButtonSprite button : signButtonSprites)
            button.detachObserver(observer);
    }

    @Override
    public void setSceneVisible(boolean value) {
        super.setSceneVisible(value);
        setButtonsImageViewVisible(value);
    }

    private void setButtonsImageViewVisible(boolean value) {
        for (SignButtonSprite button : signButtonSprites)
            button.setImageViewVisible(value);
    }

    public ImageSheetProperty getSign(int signId) {
        return signButtonSprites[signId].getSpriteSheetProperty();
    }

    public int getLastChosenSignId() {
        return lastChosenSignId;
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public ImageSheetProperty getLastChosenSign() {
        return lastChosenSign;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }
}