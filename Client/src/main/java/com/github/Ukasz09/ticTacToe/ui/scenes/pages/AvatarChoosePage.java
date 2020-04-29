package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.HoveredActiveImageButton;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class AvatarChoosePage extends ChoosePage {
    private static final String DEFAULT_LABEL_TEXT_PREFIX = "Choose avatar of player: ";
    private static final Image[] DEFAULT_AVATARS_IMAGES = ImagesProperties.avatars();

    private String actualInitializedPlayerNick;
    private int lastChosenAvatarId = -1;
    private static HashMap<Button, Integer> avatarsIdMap = initializeAvatarButtons();

    //----------------------------------------------------------------------------------------------------------------//
    public AvatarChoosePage(String firstPlayerName) {
        super(StartGamePage.GAME_BACKGROUND, DEFAULT_LABEL_TEXT_PREFIX + firstPlayerName, Orientation.HORIZONTAL, 0);
        actualInitializedPlayerNick = firstPlayerName;
        addAvatarButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static HashMap<Button, Integer> initializeAvatarButtons() {
        HashMap<Button, Integer> map = new HashMap<>();
        for (int i = 0; i < DEFAULT_AVATARS_IMAGES.length; i++) {
            Button button = new HoveredActiveImageButton(DEFAULT_AVATARS_IMAGES[i]);
            map.put(button, i);
        }
        return map;
    }

    private void addAvatarButtons() {
        for (Map.Entry<Button, Integer> entry : avatarsIdMap.entrySet()) {
            Button btn = entry.getKey();
            addMouseClickedActionToButton(btn);
            addToContentPane(btn);
        }
    }

    private void addMouseClickedActionToButton(Button button) {
        button.setOnMouseClicked(event -> {
            button.setDisable(true);
            lastChosenAvatarId = avatarsIdMap.get(button);
            notifyObservers(GuiEvents.AVATAR_BTN_CLICKED);
        });
    }

    @Override
    public void update() {
        setHeaderText(DEFAULT_LABEL_TEXT_PREFIX + actualInitializedPlayerNick);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public static ImageView getAvatarImage(int index) {
        for (Map.Entry<Button, Integer> entry : avatarsIdMap.entrySet()) {
            if (entry.getValue() == index)
                return ((ImageView) entry.getKey().getGraphic());
        }
        return null;
    }

    public void setActualInitializedPlayerNick(String actualInitializedPlayerNick) {
        this.actualInitializedPlayerNick = actualInitializedPlayerNick;
    }

    public int getLastChosenAvatarId() {
        return lastChosenAvatarId;
    }
}
