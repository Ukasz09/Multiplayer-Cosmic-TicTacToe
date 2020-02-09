package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.AnimatedSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.SignChooseBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.buttons.SignButton;
import javafx.scene.control.Button;

public class SignChoosePanel extends ChoosePanel {
    private static final ImageSheetProperty[] DEFAULT_SHEET_PROPERTIES = SpritesProperties.signSheetsProperties();
    private static final double BUTTONS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT = "Choose your game sign!";

    private SignButton[] signButtons;

    public SignChoosePanel() {
        super(new SignChooseBackground(), LABEL_TEXT);
        addSignButtons();
    }

    private void addSignButtons() {
        int signButtonsQty = DEFAULT_SHEET_PROPERTIES.length;
        signButtons = new SignButton[signButtonsQty];
        for (int i = 0; i < signButtonsQty; i++)
            signButtons[i] = new SignButton(DEFAULT_SHEET_PROPERTIES[i], 0, 0);
        setSignButtonsCorrectPositions(BUTTONS_PADDING_TO_SCREEN_PROPORTION * manager.getRightFrameBorder());
    }

    private void setSignButtonsCorrectPositions(double buttonPadding) {
        double buttonPositionX = getFirstButtonXPositionToCenterWithOthers(signButtons.length, buttonPadding, signButtons[0].getWidth());
        double buttonPositionY = getButtonCenterYPositionInContentPane(signButtons[0].getHeight());

        for (SignButton signButton : signButtons) {
            signButton.setPositionX(buttonPositionX);
            buttonPositionX += (signButton.getWidth() + buttonPadding);
            signButton.setPositionY(buttonPositionY);
        }
    }

    private double getButtonCenterYPositionInContentPane(double buttonHeight) {
        double labelPaneHeight = getLabelPaneHeight();
        return labelPaneHeight + (manager.getBottomFrameBorder() - labelPaneHeight) / 2 - buttonHeight / 2;
    }

    private double getFirstButtonXPositionToCenterWithOthers(int buttonsQty, double buttonsPadding, double buttonWidth) {
        return (manager.getRightFrameBorder() - buttonsQty * buttonWidth - (buttonsQty - 1) * buttonsPadding) / 2;
    }

    @Override
    public void render() {
        super.render();
        renderSignButtons();
    }

    private void renderSignButtons() {
        for (AnimatedSprite signButton : signButtons)
            signButton.render();
    }

    @Override
    public void update() {
        updateSignButtons();
    }

    private void updateSignButtons() {
        for (AnimatedSprite signButton : signButtons)
            signButton.update();
    }

    public void attachObserver(IEventKindObserver observer) {
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IEventKindObserver observer) {
        for (SignButton signButton : signButtons)
            signButton.attachObserver(observer);
    }
}
