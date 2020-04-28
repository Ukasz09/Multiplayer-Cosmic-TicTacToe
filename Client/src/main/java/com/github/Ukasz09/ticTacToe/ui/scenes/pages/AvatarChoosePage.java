package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.HoveredActiveImageButton;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AvatarChoosePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT_PREFIX = "Choose avatar of player: ";
    private static final Image[] DEFAULT_AVATARS_IMAGES = ImagesProperties.avatars();

    private ImageView chosenImage = null;
    private String actualInitializedPlayerNick;

    //----------------------------------------------------------------------------------------------------------------//
    public AvatarChoosePage(String firstPlayerName) {
        super(StartGamePage.GAME_BACKGROUND, DEFAULT_LABEL_TEXT_PREFIX + firstPlayerName, Orientation.HORIZONTAL, 0);
        actualInitializedPlayerNick = firstPlayerName;
        addAvatarButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addAvatarButtons() {
        for (Image avatarImage : DEFAULT_AVATARS_IMAGES) {
            Button button = new HoveredActiveImageButton(avatarImage);
            addMouseClickedActionToButton(button);
            addToContentPane(button);
        }
    }

    private void addMouseClickedActionToButton(Button button) {
        button.setOnMouseClicked(event -> {
            chosenImage = ((ImageView) button.getGraphic());
            button.setDisable(true);
            notifyObservers(GuiEvents.AVATAR_BTN_CLICKED);
        });
    }

    @Override
    public void update() {
        setHeaderText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public ImageView getChosenImage() {
        return chosenImage;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }

}
