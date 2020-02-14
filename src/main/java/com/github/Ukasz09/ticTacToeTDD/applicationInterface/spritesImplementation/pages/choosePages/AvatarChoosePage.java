package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ChooseBackground;
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
    private static final double AVATAR_SIZE_TO_SCREEN_PROPORTION = 14 / 108d;

    private ImageView chosenImage = null;

    private String actualInitializedPlayerNick;

    public AvatarChoosePage(String firstPlayerName) {
        super(new ChooseBackground(), DEFAULT_LABEL_TEXT_PREFIX + firstPlayerName);
        actualInitializedPlayerNick = firstPlayerName;
        addAvatarButtons();
    }

    private void addAvatarButtons() {
        for (Image defaultAvatarsImage : DEFAULT_AVATARS_IMAGES) {
            ImageView imageView = getAvatarImageViewFromImage(defaultAvatarsImage);
            Button avatarButton = new Button("", imageView);
            setButtonEffects(avatarButton);
            addMouseClickedActionToButton(avatarButton);
            getContentPane().getChildren().add(avatarButton);
        }
    }

    private void setButtonEffects(Button button) {
        button.setEffect(BUTTON_EXITED_EFFECT);
        button.setBackground(new Background(new BackgroundFill(BUTTON_BACKGROUND_COLOR, new CornerRadii(BUTTON_CORNER_RADIUS), Insets.EMPTY)));
        button.setOnMouseEntered(event -> button.setEffect(BUTTON_HOVERED_EFFECT));
        button.setOnMouseExited(event -> button.setEffect(BUTTON_EXITED_EFFECT));
    }

    private void addMouseClickedActionToButton(Button button) {
        button.setOnMouseClicked(event -> {
            chosenImage = ((ImageView) button.getGraphic());
            button.setDisable(true);
            notifyObservers(EventKind.AVATAR_BUTTON_CLICKED);
        });
    }

    private ImageView getAvatarImageViewFromImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(manager.getScaledWidth(AVATAR_SIZE_TO_SCREEN_PROPORTION));
        imageView.setFitHeight(manager.getScaledWidth(AVATAR_SIZE_TO_SCREEN_PROPORTION));
        return imageView;
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
