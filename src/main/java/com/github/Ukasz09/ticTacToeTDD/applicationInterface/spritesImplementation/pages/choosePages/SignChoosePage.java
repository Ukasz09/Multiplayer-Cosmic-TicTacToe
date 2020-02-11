package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ChooseBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.SignButton;
import javafx.scene.input.MouseEvent;

public class SignChoosePage extends ChoosePage {
    private static final ImageSheetProperty[] DEFAULT_SHEET_PROPERTIES = SpritesProperties.signSheetsProperties();
    private static final double BUTTONS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT_PREFIX = "Choose game sign for player: ";

    private SignButton[] signButtons;
    private ImageSheetProperty lastChosenSign = null;
    private String actualInitializedPlayerNick;

    public SignChoosePage(String firstPlayerName) {
        super(new ChooseBackground(), LABEL_TEXT_PREFIX + firstPlayerName);
        actualInitializedPlayerNick = firstPlayerName;
        addSignButtons();
    }

    private void addSignButtons() {
        int signButtonsQty = DEFAULT_SHEET_PROPERTIES.length;
        signButtons = new SignButton[DEFAULT_SHEET_PROPERTIES.length];
        for (int i = 0; i < signButtonsQty; i++) {
            SignButton signButton = new SignButton(DEFAULT_SHEET_PROPERTIES[i]);
            signButtons[i] = signButton;
            signButton.addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (signButton.isActive()) {
                    lastChosenSign = signButton.getSpriteSheetProperty();
                    signButton.disable();
                    signButton.notifyObservers(EventKind.SIGN_BUTTON_CLICKED);
                }
            });
        }
        setSignButtonsCorrectPositions(BUTTONS_PADDING_TO_SCREEN_PROPORTION * manager.getRightFrameBorder());
    }

    private void setSignButtonsCorrectPositions(double buttonPadding) {
        double nextButtonPositionX = getFirstButtonXPositionToCenterWithOthers(signButtons.length, buttonPadding, signButtons[0].getWidth());
        double nextButtonPositionY = getButtonCenterYPositionInContentPane(signButtons[0].getHeight());

        for (SignButton button : signButtons) {
            button.setPositionX(nextButtonPositionX);
            nextButtonPositionX += (button.getWidth() + buttonPadding);
            button.setPositionY(nextButtonPositionY);
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
        for (SignButton button : signButtons)
            button.render();
    }

    @Override
    public void update() {
        updateSignButtons();
        setLabelText(LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    private void updateSignButtons() {
        for (SignButton button : signButtons)
            button.update();
    }

    public void attachObserver(IEventKindObserver observer) {
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IEventKindObserver observer) {
        for (SignButton button : signButtons)
            button.attachObserver(observer);
    }

    public ImageSheetProperty getLastChosenSign() {
        return lastChosenSign;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }
}
