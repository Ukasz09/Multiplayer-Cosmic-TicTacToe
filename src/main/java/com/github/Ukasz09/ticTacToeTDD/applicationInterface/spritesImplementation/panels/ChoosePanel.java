package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import javafx.scene.control.Button;

public abstract class ChoosePanel extends Panel {
    private CenteredPane contentPanel;
    private LabelPane labelPane;

    public ChoosePanel(MyBackground background, String labelText) {
        super(background);
        initializePanel(labelText);
    }

    private void initializePanel(String labelText) {
        labelPane = new LabelPane(labelText);
        contentPanel = new CenteredPane();
        setCenter(contentPanel);
        setTop(labelPane);
    }

    public CenteredPane getContentPanel() {
        return contentPanel;
    }
}
