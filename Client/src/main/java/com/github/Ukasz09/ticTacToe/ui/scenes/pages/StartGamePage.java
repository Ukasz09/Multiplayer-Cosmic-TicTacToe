package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlBtn;
import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartGamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Cosmic Tic-Tac-Toe Game";
    private static final double MUSIC_VOLUME = 0.8;
    public static final GameBackground GAME_BACKGROUND = new ImageGameBackground(DEFAULT_BACKGROUND, SoundsProperties.gameBackground(MUSIC_VOLUME));

    private final Image animImage = ImagesProperties.startAnimation();
    private ImageSprite startAnimation;

    //----------------------------------------------------------------------------------------------------------------//
    public StartGamePage() {
        super(GAME_BACKGROUND, HEADER_TEXT, Orientation.VERTICAL, 0);
        addButtons();
        initWaitingAnimation();
        addToContentPane(getImageViewForAnim());
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initWaitingAnimation() {
        double spriteWidth = manager.getRightFrameBorder() * 0.4;
        double spriteHeight = (manager.getBottomFrameBorder() - getHeaderPaneHeight()) / 2;
        double posX = getSpriteCenterPositionX(spriteWidth);
        double posY = manager.getBottomFrameBorder() - spriteHeight ;
        startAnimation = new ImageSprite(spriteWidth, spriteHeight, animImage, posX, posY, false);
    }

    private Node getImageViewForAnim() {
        ImageView iv = new ImageView(ImagesProperties.schemeSpriteForImageView());
        double btnWidth = manager.getScaledWidth(GameControlBtn.BTN_WIDTH_PROP);
        iv.setFitWidth(btnWidth);
        iv.setFitHeight(startAnimation.getHeight());
        iv.setVisible(false);
        return iv;
    }

    private void addButtons() {
        addStartGameButton();
        addEndGameButton();
    }

    private void addStartGameButton() {
        Button button = new GameControlBtn("START GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.START_BTN_CLICKED));
        addToContentPane(button);
    }

    private void addEndGameButton() {
        Button button = new GameControlBtn("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.END_GAME_BTN_CLICKED));
        addToContentPane(button);
    }

    @Override
    public void render() {
        super.render();
        startAnimation.render();
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
