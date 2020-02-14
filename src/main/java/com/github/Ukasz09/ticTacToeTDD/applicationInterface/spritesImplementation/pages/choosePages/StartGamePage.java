package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class StartGamePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT = "Cosmic Tic-Tac-Toe Game: ";
    private static final Image DEFAULT_IMAGE = ImagesProperties.startGameBackground();
    private static final double DEFAULT_VOLUME = 0.5;
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    //todo: zrobic inaczej background, wyrzucic stylizacje button do warstwy wyzej
    //todo: tmp
    public StartGamePage() {
        super(new MyBackground(DEFAULT_IMAGE, DEFAULT_MUSIC), DEFAULT_LABEL_TEXT);
        addButtons();
    }

    private void addButtons() {
        addStartGameButton();
    }

    private void addStartGameButton() {
        getContentPane().getChildren().add(getNewStylizedButton());
    }

    //todo: tmp
    private Button getNewStylizedButton() {
        Button button = new Button();
        double btnWidth = getContentPane().getWidthAfterScaling(FIELD_WIDTH_TO_SCREEN_PROPORTION);
        double btnHeight = getContentPane().getHeightAfterScaling(FIELD_HEIGHT_TO_SCREEN_PROPORTION);
        button.setMinSize(btnWidth, btnHeight);
        button.setPrefSize(btnWidth, btnHeight);
        button.setBackground(new Background(new BackgroundFill(BUTTON_BACKGROUND_COLOR, new CornerRadii(BUTTON_CORNER_RADIUS), Insets.EMPTY)));
        button.setOnMouseEntered(event -> button.setEffect(DEFAULT_HOVERED_EFFECT));
        button.setOnMouseExited(event -> button.setEffect(BUTTON_HOVERED_EFFECT));

//        int fontSize = (int) (FONT_SIZE_TO_SCREEN_PROPORTION * manager.getBottomFrameBorder());
        int fontSize = 35;
        setDefaultButtonFont(button, DEFAULT_FONT_COLOR, fontSize);
        button.setText("START GAME");
        return button;
    }

    @Override
    public void update() {
        //nothing to do
    }
}
