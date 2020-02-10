package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.SignChooseBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.SignButton;

import java.util.HashMap;
import java.util.Map;

public class SignChoosePanel extends ChoosePanel {
    private static final ImageSheetProperty[] DEFAULT_SHEET_PROPERTIES = SpritesProperties.signSheetsProperties();
    private static final EventKind[] buttonsEvents = {
            EventKind.SIGN_BUTTON_1_CLICKED,
            EventKind.SIGN_BUTTON_2_CLICKED,
            EventKind.SIGN_BUTTON_3_CLICKED,
            EventKind.SIGN_BUTTON_4_CLICKED,
            EventKind.SIGN_BUTTON_5_CLICKED,
    };
    private static final double BUTTONS_PADDING_TO_SCREEN_PROPORTION = 10 / 192d;
    private static final String LABEL_TEXT = "Choose your game sign!";

    private Map<EventKind, SignButton> signButtons;

    public SignChoosePanel() {
        super(new SignChooseBackground(), LABEL_TEXT);
        addSignButtons();
    }

    private void addSignButtons() {
        int signButtonsQty = DEFAULT_SHEET_PROPERTIES.length;
        signButtons = new HashMap<>();
        for (int i = 0; i < signButtonsQty; i++)
            signButtons.put(buttonsEvents[i], new SignButton(DEFAULT_SHEET_PROPERTIES[i], buttonsEvents[i]));
        setSignButtonsCorrectPositions(BUTTONS_PADDING_TO_SCREEN_PROPORTION * manager.getRightFrameBorder());
    }

    private void setSignButtonsCorrectPositions(double buttonPadding) {
        double buttonPositionX = getFirstButtonXPositionToCenterWithOthers(signButtons.size(), buttonPadding, signButtons.get(EventKind.SIGN_BUTTON_1_CLICKED).getWidth());
        double buttonPositionY = getButtonCenterYPositionInContentPane(signButtons.get(EventKind.SIGN_BUTTON_1_CLICKED).getHeight());

        for (Map.Entry<EventKind, SignButton> entry : signButtons.entrySet()) {
            SignButton signButton = entry.getValue();
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
        for (Map.Entry<EventKind, SignButton> entry : signButtons.entrySet()) {
            SignButton signButton = entry.getValue();
            signButton.render();
        }
    }

    @Override
    public void update() {
        updateSignButtons();
    }

    private void updateSignButtons() {
        for (Map.Entry<EventKind, SignButton> entry : signButtons.entrySet()) {
            SignButton signButton = entry.getValue();
            signButton.update();
        }
    }

    public void attachObserver(IEventKindObserver observer) {
        addSignButtonsObserver(observer);
    }

    private void addSignButtonsObserver(IEventKindObserver observer) {
        for (Map.Entry<EventKind, SignButton> entry : signButtons.entrySet()) {
            SignButton signButton = entry.getValue();
            signButton.attachObserver(observer);
        }
    }

    public ImageSheetProperty getSignSheetFromButton(EventKind buttonKind){
        return (signButtons.get(buttonKind)).getSpriteSheetProperty();
    }
}
