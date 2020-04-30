package com.github.Ukasz09.ticTacToe.ui.scenes;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import javafx.scene.image.ImageView;

public class PlayerViewProperties {
    private static final ImageView DEFAULT_AVATAR = new ImageView(ImagesProperties.schemeSpriteForImageView());
    private static final ImageSheetProperty DEFAULT_SIGN_SHEET_PROPERTY = SpritesProperties.sign1SheetProperty();

    private ImageView avatar;
    private ImageSheetProperty signSheetProperty;
    private String nick;

    //----------------------------------------------------------------------------------------------------------------//
    public PlayerViewProperties() {
        this.avatar = DEFAULT_AVATAR;
        this.signSheetProperty = DEFAULT_SIGN_SHEET_PROPERTY;
        this.nick = "";
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public void setSignSheetProperty(ImageSheetProperty signSheetProperty) {
        this.signSheetProperty = signSheetProperty;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public ImageSheetProperty getSignSheetProperty() {
        return signSheetProperty;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
