package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;

public class SpritesProperties {
    private static final int AVAILABLE_SIGNS_QTY = 5;

    public static ImageSheetProperty[] signSheetsProperties() {
        ImageSheetProperty[] sheetProperties = new ImageSheetProperty[AVAILABLE_SIGNS_QTY];
        sheetProperties[0] = sign1SheetProperty();
        sheetProperties[1] = sign2SheetProperty();
        sheetProperties[2] = sign3SheetProperty();
        sheetProperties[3] = sign4SheetProperty();
        sheetProperties[4] = sign5SheetProperty();
        return sheetProperties;
    }

    public static ImageSheetProperty sign1SheetProperty() {
        String spritePath = "sprites/signs/1.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
        return sheetProperty;
    }

    public static ImageSheetProperty sign2SheetProperty() {
        String spritePath = "sprites/signs/2.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 27)
                .build();
        return sheetProperty;
    }

    public static ImageSheetProperty sign3SheetProperty() {
        String spritePath = "sprites/signs/3.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 28)
                .build();
        return sheetProperty;
    }

    public static ImageSheetProperty sign4SheetProperty() {
        String spritePath = "sprites/signs/4.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 19)
                .build();
        return sheetProperty;
    }

    public static ImageSheetProperty sign5SheetProperty() {
        String spritePath = "sprites/signs/5.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(256, 256)
                .withDefaultDurationPerOneFrame(3)
                .withAddActionState(SpriteStates.STANDBY, 0, 60)
                .build();
        return sheetProperty;
    }

//    public static ImageSheetProperty gridBoxProperty() {
//        String spritePath = "sprites/gridBox.png";
//        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
//                .withImagePath(spritePath)
//                .withSizeOfOneFrame(400, 400)
//                .withDefaultDurationPerOneFrame(6)
//                .withAddActionState(SpriteStates.STANDBY, 0, 8)
//                .build();
//        return sheetProperty;
//    }

    public static ImageSheetProperty gridBoxProperty() {
        String spritePath = "sprites/gridBox.png";
        ImageSheetProperty sheetProperty = ImageSheetProperty.builder()
                .withImagePath(spritePath)
                .withSizeOfOneFrame(267, 266)
                .withDefaultDurationPerOneFrame(4)
                .withAddActionState(SpriteStates.STANDBY, 24, 20)
                .withAddActionState(SpriteStates.NO_ANIMATION, 0, 1)
                .build();
        return sheetProperty;
    }

}
