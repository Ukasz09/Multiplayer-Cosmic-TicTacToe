package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SignButtonSprite extends AnimatedButtonSprite {
    public final static double WIDTH_TO_FRAME_PROPORTION = 20 / 192d;
    public final static double HEIGHT_TO_FRAME_PROPORTION = 20 / 192d;
    private static final Effect EFFECT_FOR_DISABLE_STATUS = new Lighting(new Light.Distant(10, 10, Color.GRAY));

    //-----------------------------------------------------------------------------------------------------------------//
    public SignButtonSprite(ImageSheetProperty sheetProperty) {
        super(ViewManager.getInstance().getScaledWidth(WIDTH_TO_FRAME_PROPORTION), ViewManager.getInstance().getScaledHeight(HEIGHT_TO_FRAME_PROPORTION), sheetProperty);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    @Override
    public void disable() {
        super.disable();
        setSheetEffectToDisable();
    }

    private void setSheetEffectToDisable() {
        ImageView view = new ImageView();
        SnapshotParameters snapshot = new SnapshotParameters();
        snapshot.setFill(Color.TRANSPARENT);
        view.setImage(getSpriteSheetProperty().getSheet());
        view.setEffect(EFFECT_FOR_DISABLE_STATUS);
        Image newSheet = view.snapshot(snapshot, null);
        setSpriteSheet(newSheet);
    }
}
