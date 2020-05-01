package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.animated.SignBtnSprite;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class SignPage extends ChoosePage {
    private static final ImageSheetProperty[] SING_SHEETS = SpritesProperties.signSheetsProperties();
    private static final double BTNS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT = "Choose game sign";

    private Map<SignBtnSprite, Integer> signBtnSprites; //<btn,btn_number>
    private int chosenSignNumber = -1;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignPage(ImageSheetProperty disableSign) {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        addSignButtons(disableSign);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void addSignButtons(ImageSheetProperty disableSign) {
        signBtnSprites = new HashMap<>();
        double btnWidth = 0, btnHeight = 0;
        for (int i = 0; i < SING_SHEETS.length; i++) {
            SignBtnSprite btn = new SignBtnSprite(SING_SHEETS[i], true);
            btnWidth = btn.getWidth();
            btnHeight = btn.getHeight();
            addSignButtonEventHandler(btn);
            if (disableSign != null && disableSign.equals(SING_SHEETS[i]))
                btn.disable();
            signBtnSprites.put(btn, i);
        }
        setSignButtonsCorrectPositions(manager.getScaledWidth(BTNS_PADDING_TO_SCREEN_PROPORTION), btnWidth, btnHeight);
    }

    private void addSignButtonEventHandler(SignBtnSprite btnSprite) {
        btnSprite.addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (btnSprite.isActive()) {
                chosenSignNumber = signBtnSprites.get(btnSprite);
                btnSprite.disable();
                btnSprite.notifyObservers(GuiEvents.SIGN_BTN_CLICKED);
            }
        });
    }

    private void setSignButtonsCorrectPositions(double buttonPadding, double btnWidth, double btnHeight) {
        double nextBtnPosX = getFstBtnPosXToCenterWithOthers(signBtnSprites.size(), buttonPadding, btnWidth);
        double nextBtnPosY = getBtnCenterPosYInContentPane(btnHeight);

        for (Map.Entry<SignBtnSprite, Integer> entry : signBtnSprites.entrySet()) {
            SignBtnSprite btn = entry.getKey();
            btn.setPositionX(nextBtnPosX);
            nextBtnPosX += (btn.getWidth() + buttonPadding);
            btn.setPositionY(nextBtnPosY);
        }
    }

    private double getBtnCenterPosYInContentPane(double buttonHeight) {
        double labelPaneHeight = getHeaderPaneHeight();
        return labelPaneHeight + (manager.getBottomFrameBorder() - labelPaneHeight) / 2 - buttonHeight / 2;
    }

    @Override
    public void render() {
        super.render();
        renderSignButtons();
    }

    private void renderSignButtons() {
        signBtnSprites.forEach((btn, numb) -> btn.render());
    }

    @Override
    public void update() {
        updateSignButtons();
    }

    private void updateSignButtons() {
        signBtnSprites.forEach((btn, numb) -> btn.update());
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        super.attachObserver(observer);
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IGuiObserver observer) {
        signBtnSprites.forEach((btn, numb) -> btn.attachObserver(observer));
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        super.detachObserver(observer);
        removeSignButtonsObserver(observer);
    }

    private void removeSignButtonsObserver(IGuiObserver observer) {
        signBtnSprites.forEach((btn, numb) -> btn.detachObserver(observer));
    }

    @Override
    public void setSceneVisible(boolean value) {
        super.setSceneVisible(value);
        setButtonsImageViewVisible(value);
    }

    private void setButtonsImageViewVisible(boolean value) {
        signBtnSprites.forEach((btn, numb) -> btn.setImageViewVisible(value));
    }


    public int getChosenSignNumber() {
        return chosenSignNumber;
    }

    public static ImageSheetProperty getSign(int signId) {
        return SING_SHEETS[signId];
    }
}