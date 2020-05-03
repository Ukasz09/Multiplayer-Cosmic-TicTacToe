package com.github.Ukasz09.ticTacToe.ui.sprites.properties;

import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class SpritesProperties {
    public static ImageSheetProperty[] signSheetsProperties() {
        ImageSheetProperty[] sheetProperties = new ImageSheetProperty[5];
        sheetProperties[0] = sign1SheetProperty();
        sheetProperties[1] = sign2SheetProperty();
        sheetProperties[2] = sign3SheetProperty();
        sheetProperties[3] = sign4SheetProperty();
        sheetProperties[4] = sign5SheetProperty();
        return sheetProperties;
    }

    public static ImageSheetProperty sign1SheetProperty() {
        String spritePath = "sprites/signs/1.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(50)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
    }

    public static ImageSheetProperty sign2SheetProperty() {
        String spritePath = "sprites/signs/2.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(50)
                .withAddActionState(SpriteStates.STANDBY, 0, 27)
                .build();
    }

    public static ImageSheetProperty sign3SheetProperty() {
        String spritePath = "sprites/signs/3.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(50)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
    }

    public static ImageSheetProperty sign4SheetProperty() {
        String spritePath = "sprites/signs/brain.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(150, 107)
                .withDefaultDurationPerOneFrame(50)
                .withAddActionState(SpriteStates.STANDBY, 0, 45)
                .build();
    }

    public static ImageSheetProperty sign5SheetProperty() {
        String spritePath = "sprites/signs/skull4.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(150, 145)
                .withDefaultDurationPerOneFrame(50)
                .withAddActionState(SpriteStates.STANDBY, 0, 46)
                .build();
    }

    public static ImageSheetProperty gridBoxProperty() {
        String spritePath = "sprites/gridBox.png";
        return ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(267, 266)
                .withDefaultDurationPerOneFrame(60)
                .withAddActionState(SpriteStates.STANDBY, 24, 20)
                .withAddActionState(SpriteStates.NO_ANIMATION, 0, 1)
                .withAddActionState(SpriteStates.IS_WIN_BOX_ANIMATION, 0, 31)
                .build();
    }
}
