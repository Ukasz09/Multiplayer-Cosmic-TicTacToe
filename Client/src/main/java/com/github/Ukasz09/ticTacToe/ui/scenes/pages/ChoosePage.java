package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.backgrounds.IBackground;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.CenteredPane;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.LabelPane;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public abstract class ChoosePage extends Page implements IGuiObservable {
    protected static final Image DEFAULT_BACKGROUND = ImagesProperties.startGameBackground();

    private CenteredPane contentPane;
    private LabelPane headerPane;
    private Set<IGuiObserver> observers;

    //----------------------------------------------------------------------------------------------------------------//
    public ChoosePage(IBackground background, String labelText, Orientation orientation, double horizontalGap) {
        super(background);
        observers = new HashSet<>();
        initializePage(labelText, orientation, horizontalGap);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializePage(String labelText, Orientation orientation, double horizontalGap) {
        headerPane = new LabelPane(labelText);
        setTop(headerPane);
        initializeContentPane(orientation, horizontalGap);
    }

    private void initializeContentPane(Orientation orientation, double horizontalGap) {
        contentPane = new CenteredPane();
        setCenter(contentPane);
        contentPane.setOrientation(orientation);
        contentPane.setHgap(horizontalGap);
    }

    protected void addToContentPane(Node node) {
        contentPane.getChildren().add(node);
    }

    //----------------------------------------------------------------------------------------------------------------//
    protected double getHeaderPaneHeight() {
        return headerPane.getMinHeight();
    }

    public void setHeaderText(String text) {
        headerPane.setLabelText(text);
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GuiEvents event) {
        for (IGuiObserver observer : observers)
            observer.updateGuiObserver(event);
    }

    protected double getFstBtnPosXToCenterWithOthers(int buttonsQty, double buttonsPadding, double buttonWidth) {
        return (manager.getRightFrameBorder() - buttonsQty * buttonWidth - (buttonsQty - 1) * buttonsPadding) / 2;
    }

    protected double getSpriteCenterPositionX(double spriteWidth) {
        return manager.getRightFrameBorder() / 2 - spriteWidth / 2;
    }
}
