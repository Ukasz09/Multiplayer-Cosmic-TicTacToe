package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.ImageButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class AvatarChoosePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT_PREFIX = "Choose avatar of player: ";
    private static final Image[] DEFAULT_AVATARS_IMAGES = ImagesProperties.avatars();

    private ImageView chosenImage = null;
    private String actualInitializedPlayerNick;

    public AvatarChoosePage(String firstPlayerName) {
        super(new ImageGameBackground(DEFAULT_BACKGROUND), DEFAULT_LABEL_TEXT_PREFIX + firstPlayerName);
        actualInitializedPlayerNick = firstPlayerName;
        addAvatarButtons();
    }

    private void addAvatarButtons() {
        for (Image avatarImage : DEFAULT_AVATARS_IMAGES) {
            Button button = new ImageButton(avatarImage);
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
        setLabelText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    public ImageView getChosenImage() {
        return chosenImage;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }
}
