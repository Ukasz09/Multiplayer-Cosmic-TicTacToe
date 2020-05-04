package com.github.Ukasz09.ticTacToe.ui.scenes;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.scene.image.ImageView;

public class PlayerViewProperties {
    private static final ImageView DEFAULT_AVATAR = new ImageView(ImagesProperties.schemeSpriteForImageView());

    private ImageView avatar;
    private ImageSheetProperty signSheet;
    private String nick;

    //----------------------------------------------------------------------------------------------------------------//
    public PlayerViewProperties() {
        clearPlayerData();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public void setSignSheet(ImageSheetProperty signSheet) {
        this.signSheet = signSheet;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public ImageSheetProperty getSignSheet() {
        return signSheet;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void clearPlayerData() {
        this.avatar = DEFAULT_AVATAR;
        this.signSheet = null;
        this.nick = "";
    }
}
