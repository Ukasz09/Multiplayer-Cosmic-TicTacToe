package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.HoveredActiveImageButton;
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
        super(new ImageGameBackground(DEFAULT_BACKGROUND), DEFAULT_LABEL_TEXT_PREFIX + firstPlayerName);
        actualInitializedPlayerNick = firstPlayerName;
        addAvatarButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addAvatarButtons() {
        for (Image avatarImage : DEFAULT_AVATARS_IMAGES) {
            Button button = new HoveredActiveImageButton(avatarImage);
            addMouseClickedActionToButton(button);
            getContentPane().getChildren().add(button);
        }
    }

    private void addMouseClickedActionToButton(Button button) {
        button.setOnMouseClicked(event -> {
            chosenImage = ((ImageView) button.getGraphic());
            button.setDisable(true);
            notifyObservers(EventKind.AVATAR_BUTTON_CLICKED);
        });
    }

    @Override
    public void update() {
        setHeaderText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    public ImageView getChosenImage() {
        return chosenImage;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }
}
