package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.animated;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SignButtonSprite extends AnimatedButtonSprite {
    public final static double WIDTH_TO_FRAME_PROPORTION = 15 / 192d;
    private static final Effect EFFECT_FOR_DISABLE_STATUS = new Lighting(new Light.Distant(10, 10, Color.GRAY));

    private Image signSheetToRender;
    private Image disableSignSheetToRender = null;
    private Image normalSheet;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignButtonSprite(ImageSheetProperty sheetProperty, boolean withImageViewInRoot) {
        this(sheetProperty, ViewManager.getInstance().getScaledWidth(WIDTH_TO_FRAME_PROPORTION), withImageViewInRoot);
    }

    public SignButtonSprite(ImageSheetProperty sheetProperty, double size, boolean withImageViewInRoot) {
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
        if (disableSignSheetToRender == null)
            disableSignSheetToRender = getSheetWithEffect(signSheetToRender, EFFECT_FOR_DISABLE_STATUS, 1);
        signSheetToRender = disableSignSheetToRender;
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

    /**
     * Bloat operation. Use judiciously
     */
    public void setVisible(boolean visible) {
        if (visible)
            signSheetToRender = normalSheet;
        else signSheetToRender = getSheetWithEffect(normalSheet, null, 0);
    }
}
