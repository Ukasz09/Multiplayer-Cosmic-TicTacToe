package com.github.Ukasz09.ticTacToe.logic.guiObserver;

public interface IGuiObservable {
    void attachObserver(IGuiObserver observer);

    void detachObserver(IGuiObserver observer);

    void notifyObservers(GuiEvents event);
}
