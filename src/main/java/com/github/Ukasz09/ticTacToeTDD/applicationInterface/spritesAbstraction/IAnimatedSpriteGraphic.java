package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.states.IKindOfState;

public interface IAnimatedSpriteGraphic extends ISpriteGraphic {
    void changeState(IKindOfState state);
}
