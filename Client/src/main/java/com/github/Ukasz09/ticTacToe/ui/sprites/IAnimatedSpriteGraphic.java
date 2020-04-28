package com.github.Ukasz09.ticTacToe.ui.sprites;

import com.github.Ukasz09.ticTacToe.ui.sprites.states.IKindOfState;

public interface IAnimatedSpriteGraphic extends ISpriteGraphic {
    void changeState(IKindOfState state);
}
