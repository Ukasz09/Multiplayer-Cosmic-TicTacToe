package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import javafx.scene.image.ImageView;

public class PlayerViewProperties {
    private static final ImageView DEFAULT_AVATAR = new ImageView(ImagesProperties.schemeSpriteForImageView());
    private static final ImageSheetProperty DEFAULT_SIGN_SHEET_PROPERTY = SpritesProperties.sign1SheetProperty();
    private static final String DEFAULT_NAME = "Janek";

    private ImageView avatar;
    private ImageSheetProperty signSheetProperty;
    private String name;
    private int winQty = 0;

    public PlayerViewProperties(ImageView avatar, ImageSheetProperty signSheetProperty, String name) {
        this.avatar = avatar;
        this.signSheetProperty = signSheetProperty;
        this.name = name;
    }

    public PlayerViewProperties() {
        this.avatar = DEFAULT_AVATAR;
        this.signSheetProperty = DEFAULT_SIGN_SHEET_PROPERTY;
        this.name = DEFAULT_NAME;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public void setSignSheetProperty(ImageSheetProperty signSheetProperty) {
        this.signSheetProperty = signSheetProperty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWinQty(int winQty) {
        this.winQty = winQty;
    }

    public String getName() {
        return name;
    }

    public ImageSheetProperty getSignSheetProperty() {
        return signSheetProperty;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
