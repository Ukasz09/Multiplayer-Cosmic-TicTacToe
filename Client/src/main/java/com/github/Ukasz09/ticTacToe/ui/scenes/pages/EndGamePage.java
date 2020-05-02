package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.control.textFields.HeaderTextField;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.LabelPane;
import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EndGamePage extends ChoosePage {
    private static final String LABEL_TEXT = "Thanks for playing...";
    private static final double AUTHORS_NAME_FONT_SIZE_PROP = 3 / 108d;
    private static final double AUTHORS_CONTENT_FONT_SIZE_PROP = 2 / 108d;

    private final Image animImage = ImagesProperties.endAnimation();
    private ImageSprite endAnimation;

    //----------------------------------------------------------------------------------------------------------------//
    public EndGamePage(String optionalMsg) {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        initEndAnimation();
        addLabels(optionalMsg);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initEndAnimation() {
        double spriteWidth = manager.getRightFrameBorder() / 3;
        double spriteHeight = (manager.getBottomFrameBorder() - getHeaderPaneHeight()) / 2;
        double posX = getSpriteCenterPositionX(spriteWidth);
        endAnimation = new ImageSprite(spriteWidth, spriteHeight, animImage, posX, manager.getBottomFrameBorder() - spriteHeight, false);
    }

    private void addLabels(String optionalMsg) {
        int fontSize = (int) (AUTHORS_NAME_FONT_SIZE_PROP * manager.getBottomFrameBorder());
        if (!optionalMsg.isEmpty())
            addToContentPane(new HeaderTextField(optionalMsg, fontSize, false));
        addToContentPane(new HeaderTextField("Łukasz Gajerski (www.github.com/Ukasz09)", fontSize, false));
        addToContentPane(new HeaderTextField("Press ESC to continue...", fontSize, false));
        addToContentPane(getImageViewForAnim());
    }

    private Node getImageViewForAnim() {
        ImageView iv = new ImageView(animImage);
        iv.fitHeightProperty();
        iv.fitWidthProperty();
        iv.setVisible(false);
        return iv;
    }

    @Override
    public void render() {
        super.render();
        endAnimation.render();
    }

    @Override
    public void update() {
        //nothing to do
    }
}
