package com.github.Ukasz09.ticTacToe.ui.sprites.properties;

import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class SpritesProperties {
    public static ImageSheetProperty[] signSheetsProperties() {
        ImageSheetProperty[] sheetProperties = new ImageSheetProperty[7];
        sheetProperties[0] = sign1SheetProperty();
        sheetProperties[1] = sign2SheetProperty();
        sheetProperties[2] = sign3SheetProperty();
        sheetProperties[3] = sign4SheetProperty();
        sheetProperties[4] = sign5SheetProperty();
        sheetProperties[5] = sign6SheetProperty();
        sheetProperties[6] = sign7SheetProperty();
        return sheetProperties;
    }

    public static ImageSheetProperty sign1SheetProperty() {
        String spritePath = "sprites/signs/1.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
    }

    public static ImageSheetProperty sign2SheetProperty() {
        String spritePath = "sprites/signs/2.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 27)
                .build();
    }

    public static ImageSheetProperty sign3SheetProperty() {
        String spritePath = "sprites/signs/3.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
    }

    public static ImageSheetProperty sign4SheetProperty() {
        String spritePath = "sprites/signs/4.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(242, 243)
                .withDefaultDurationPerOneFrame(4)
                .withAddActionState(SpriteStates.STANDBY, 0, 36)
                .build();
    }

    public static ImageSheetProperty sign5SheetProperty() {
        String spritePath = "sprites/signs/5.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 47)
                .build();
    }

    public static ImageSheetProperty sign6SheetProperty() {
        String spritePath = "sprites/signs/6.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(369, 330)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 72)
                .build();
    }

    public static ImageSheetProperty sign7SheetProperty() {
        String spritePath = "sprites/signs/7.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(337, 326)
                .withDefaultDurationPerOneFrame(1)
                .withAddActionState(SpriteStates.STANDBY, 0, 48)
                .build();
    }

    public static ImageSheetProperty gridBoxProperty() {
        String spritePath = "sprites/gridBox.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(267, 266)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 24, 20)
                .withAddActionState(SpriteStates.NO_ANIMATION, 0, 1)
                .withAddActionState(SpriteStates.IS_WIN_BOX_ANIMATION, 0, 31)
                .build();
    }

    public static ImageSheetProperty oscarProperty() {
        String spritePath = "sprites/oscar.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(395, 603)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 69)
                .build();
    }
}
