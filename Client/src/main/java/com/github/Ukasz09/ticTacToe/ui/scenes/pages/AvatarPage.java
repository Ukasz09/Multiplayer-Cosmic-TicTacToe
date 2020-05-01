package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.IGameBtnProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameImageBtn;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.HoveredActiveImageBtn;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class AvatarPage extends ChoosePage {
    private static final String LABEL_TEXT = "Choose avatar";
    private static final Image[] AVATARS_IMAGES = ImagesProperties.avatars();
    private static final ImageView[] AVATARS_IMAGE_VIEW = initializeImageViews();

    private HashMap<Button, Integer> avatars;

    private int chosenAvatarId = -1;

    //----------------------------------------------------------------------------------------------------------------//
    public AvatarPage(Image disabledAvatar) {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        avatars = new HashMap<>(AVATARS_IMAGES.length);
        addAvatarButtons(disabledAvatar);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static ImageView[] initializeImageViews() {
        ImageView[] avatarViews = new ImageView[AVATARS_IMAGES.length];
        for (int i = 0; i < AVATARS_IMAGES.length; i++)
            avatarViews[i] = GameImageBtn.getProperSizeImageView(AVATARS_IMAGES[i]);
        return avatarViews;
    }

    private void addAvatarButtons(Image disabledAvatar) {
        for (int i = 0; i < AVATARS_IMAGES.length; i++) {
            Button btn = new HoveredActiveImageBtn(AVATARS_IMAGES[i]);
            if (AVATARS_IMAGES[i].equals(disabledAvatar)) {
                btn.setDisable(true);
                btn.setEffect(IGameBtnProperties.BUTTON_DISABLED_EFFECT);
            }
            addMouseClickedActionToButton(btn);
            addToContentPane(btn);
            avatars.put(btn, i);
        }
    }

    private void addMouseClickedActionToButton(Button button) {
        button.setOnMouseClicked(event -> {
            button.setDisable(true);
            chosenAvatarId = avatars.get(button);
            notifyObservers(GuiEvents.AVATAR_BTN_CLICKED);
        });
    }

    @Override
    public void update() {
        //nothing to do
    }

    public static ImageView getAvatarImage(int avatarIndex) {
        return AVATARS_IMAGE_VIEW[avatarIndex];
    }

    public int getChosenAvatarId() {
        return chosenAvatarId;
    }
}
