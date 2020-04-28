package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties;

import com.github.Ukasz09.ticTacToeTDD.ResourceTemplate;
import javafx.scene.text.Font;

public class FontProperties {
    public static Font chargenRegularFont(int fontSize) {
        String fontPath = "/fonts/chargenRegular.ttf";
        return Font.loadFont(ResourceTemplate.class.getResource(fontPath).toExternalForm(), fontSize);
    }
}
