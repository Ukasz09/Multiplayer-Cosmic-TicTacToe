package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.SignButtonSprite;
import javafx.scene.input.MouseEvent;

public class SignChoosePage extends ChoosePage {
    private static final ImageSheetProperty[] DEFAULT_SHEET_PROPERTIES = SpritesProperties.signSheetsProperties();
    private static final double BUTTONS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT_PREFIX = "Choose game sign for player: ";

    private SignButtonSprite[] signButtonSprites;
    private ImageSheetProperty lastChosenSign = null;
    private String actualInitializedPlayerNick;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignChoosePage(String firstPlayerName) {
        super(new ImageGameBackground(DEFAULT_BACKGROUND), LABEL_TEXT_PREFIX + firstPlayerName);
        actualInitializedPlayerNick = firstPlayerName;
        addSignButtons();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void addSignButtons() {
        int signButtonsQty = DEFAULT_SHEET_PROPERTIES.length;
        signButtonSprites = new SignButtonSprite[signButtonsQty];
        for (int i = 0; i < signButtonsQty; i++) {
            SignButtonSprite signButtonSprite = new SignButtonSprite(DEFAULT_SHEET_PROPERTIES[i], true);
            signButtonSprites[i] = signButtonSprite;
            signButtonSprite.addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (signButtonSprite.isActive()) {
                    lastChosenSign = signButtonSprite.getSpriteSheetProperty();
                    signButtonSprite.disable();
                    signButtonSprite.notifyObservers(EventKind.SIGN_BUTTON_CLICKED);
                }
            });
        }
        setSignButtonsCorrectPositions(manager.getScaledWidth(BUTTONS_PADDING_TO_SCREEN_PROPORTION));
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
        setLabelText(LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    private void updateSignButtons() {
        for (SignButtonSprite button : signButtonSprites)
            button.update();
    }

    @Override
    public void attachObserver(IEventKindObserver observer) {
        super.attachObserver(observer);
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IEventKindObserver observer) {
        for (SignButtonSprite button : signButtonSprites)
            button.attachObserver(observer);
    }

    @Override
    public void detachObserver(IEventKindObserver observer) {
        super.detachObserver(observer);
        removeSignButtonsObserver(observer);
    }

    private void removeSignButtonsObserver(IEventKindObserver observer) {
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
            button.setImageViewVisible(value); //todo: dodac usuwanie view z roota
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public ImageSheetProperty getLastChosenSign() {
        return lastChosenSign;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }
}
