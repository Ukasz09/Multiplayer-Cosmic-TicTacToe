package com.github.Ukasz09.ticTacToe.ui.control.buttons.animated;


import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.scene.image.Image;

public class SignBtnSprite extends AnimatedButtonSprite {
    public final static double WIDTH_TO_FRAME_PROPORTION = 15 / 192d;

    private Image signSheetToRender;
    private Image normalSheet;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignBtnSprite(ImageSheetProperty sheetProperty, boolean withImageViewInRoot) {
        this(sheetProperty, ViewManager.getInstance().getScaledWidth(WIDTH_TO_FRAME_PROPORTION), withImageViewInRoot);
    }

    public SignBtnSprite(ImageSheetProperty sheetProperty, double size, boolean withImageViewInRoot) {
        super(size, size, sheetProperty, withImageViewInRoot);
        normalSheet = sheetProperty.getSheet();
        signSheetToRender = normalSheet;
    }

    //-----------------------------------------------------------------------------------------------------------------//
    @Override
    public void disable() {
        super.disable();
        changeSheetToDisable();
    }

    private void changeSheetToDisable() {
        signSheetToRender = getSheetWithEffect(signSheetToRender, ButtonsProperties.signDisableEffect(), 1);
    }

    @Override
    public void enable() {
        super.enable();
        signSheetToRender = normalSheet;
    }

    @Override
    public void render() {
        renderSprite(signSheetToRender);
    }

    @Override
    public void update() {
        super.update();
    }

    public void setVisible(boolean visible) {
        if (visible)
            signSheetToRender = normalSheet;
        else signSheetToRender = ImagesProperties.empty();

    }
}
