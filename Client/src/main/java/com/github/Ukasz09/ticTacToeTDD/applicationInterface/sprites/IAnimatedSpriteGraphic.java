package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.IKindOfState;

public interface IAnimatedSpriteGraphic extends ISpriteGraphic {
    void changeState(IKindOfState state);
}
