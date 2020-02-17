package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.IGameButtonProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.ImageButton;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.SignButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.GameTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.LabelTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PlayerInfoPage extends FlowPane implements IDrawingGraphic {
    private static final double SIGN_PADDING_PROPORTION = 75 / 1080d;
    private static final String FONT_COLOR_CSS_FOR_DISABLE = "darkslategrey";

    private ImageButton avatar;
    private SignButtonSprite sign;
    private LabelTextField nickField;
    private final double headerHeight;
    private final double pagePositionX;

    //----------------------------------------------------------------------------------------------------------------//
    public PlayerInfoPage(double width, double headerHeight, double pagePositionX) {
        this.headerHeight = headerHeight;
        this.pagePositionX = pagePositionX;
        setPageSize(width);
        setOrientation(Orientation.HORIZONTAL);
        setAlignment(Pos.BASELINE_CENTER);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setPageSize(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    public void initialize(ImageView avatarImageView, String nick, ImageSheetProperty signSheetProperty) {
        addNicField(nick);
        addAvatar(avatarImageView);
        addSign(signSheetProperty);
    }

    private void addAvatar(ImageView avatarImageView) {
        this.avatar = new ImageButton(avatarImageView, getAvatarSize(), getAvatarSize());
        getChildren().add(avatar);
    }

    private void addNicField(String nick) {
        nickField = new LabelTextField(nick, false);
        getChildren().add(nickField);
    }

    private void addSign(ImageSheetProperty signSheetProperty) {
        double signSize = getAvatarSize() / 2;
        this.sign = new SignButtonSprite(signSheetProperty, signSize, false);
        sign.setPositionX(getSignCenterPositionX(signSize));
        sign.setPositionY(getSignPositionY());
    }

    private double getSignCenterPositionX(double signSize) {
        return (pagePositionX + getWidth() / 2 - signSize / 2);
    }

    private double getSignPositionY() {
        return nickField.getMinHeight() + getAvatarSize() + headerHeight + ViewManager.getInstance().getScaledHeight(SIGN_PADDING_PROPORTION);
    }

    private double getAvatarSize() {
        return getWidth() / 2;
    }

    @Override
    public void render() {
        sign.render();
    }

    @Override
    public void update() {
        sign.update();
    }

    public void disablePage(boolean value) {
        if (value)
            disablePage();
        else enablePage();
    }

    private void disablePage() {
        avatar.setDisable(true);
        avatar.setEffect(IGameButtonProperties.BUTTON_EXITED_EFFECT);
        sign.disable();
        nickField.setFontColor(FONT_COLOR_CSS_FOR_DISABLE);
    }

    private void enablePage() {
        avatar.setDisable(false);
        avatar.setEffect(null);
        sign.enable();
        nickField.setDefaultFontColor();
    }

}
