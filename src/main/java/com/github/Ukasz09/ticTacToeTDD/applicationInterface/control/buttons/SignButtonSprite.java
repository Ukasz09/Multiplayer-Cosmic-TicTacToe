package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SignButtonSprite extends AnimatedButtonSprite {
    public final static double WIDTH_TO_FRAME_PROPORTION = 20 / 192d;
    public final static double HEIGHT_TO_FRAME_PROPORTION = 20 / 192d;
    private static final Effect EFFECT_FOR_DISABLE_STATUS = new Lighting(new Light.Distant(10, 10, Color.GRAY));

    private Image signSheetToRender;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignButtonSprite(ImageSheetProperty sheetProperty) {
        super(ViewManager.getInstance().getScaledWidth(WIDTH_TO_FRAME_PROPORTION), ViewManager.getInstance().getScaledHeight(HEIGHT_TO_FRAME_PROPORTION), sheetProperty);
        signSheetToRender = sheetProperty.getSheet();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    @Override
    public void disable() {
        super.disable();
        signSheetToRender = getSheetEffectForDisabledButton(EFFECT_FOR_DISABLE_STATUS);
    }

    @Override
    public void render() {
        renderSprite(signSheetToRender);
    }
}
