package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;

public abstract class ChoosePage extends Page {
    private CenteredPane contentPanel;
    private LabelPane labelPage;

    public ChoosePage(MyBackground background, String labelText) {
        super(background);
        initializePanel(labelText);
    }

    private void initializePanel(String labelText) {
        labelPage = new LabelPane(labelText);
        contentPanel = new CenteredPane();
        setCenter(contentPanel);
        setTop(labelPage);
    }

    public CenteredPane getContentPane() {
        return contentPanel;
    }

    public double getLabelPaneHeight() {
        return labelPage.getMinHeight();
    }
}
