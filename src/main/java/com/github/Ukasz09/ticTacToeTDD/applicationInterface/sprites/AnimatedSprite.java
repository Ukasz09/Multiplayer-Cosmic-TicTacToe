package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.FrameStatePositions;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.IKindOfState;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import javafx.scene.image.Image;

public abstract class AnimatedSprite extends ImageSprite implements IAnimatedSpriteGraphic {
    private ImageSheetProperty spriteSheetProperty;
    private FrameStatePositions actualAnimationState;
    private double actualCooldownOnFrame;
    private double actualFramePositionX;
    private double actualFramePositionY;

    public AnimatedSprite(double width, double height, double positionX, double positionY, ImageSheetProperty sheetProperty, FrameStatePositions startedAnimationState,
                          boolean withImageViewInRoot) {
        super(width, height, ImagesProperties.schemeSpriteForImageView(), positionX, positionY, withImageViewInRoot);
        this.spriteSheetProperty = sheetProperty;
        this.actualAnimationState = startedAnimationState;
        setRandomStartedFramePosition(sheetProperty);
        actualCooldownOnFrame = 0;
    }

    private void setRandomStartedFramePosition(ImageSheetProperty sheetProperty) {
        int randomFrameIndex = sheetProperty.getAction(SpriteStates.STANDBY).getRandomIndex();
        actualFramePositionX = sheetProperty.getPositionOfIndex(randomFrameIndex).getX();
        actualFramePositionY = sheetProperty.getPositionOfIndex(randomFrameIndex).getY();
    }

    @Override
    public void update() {
        super.update();
        updateSpriteSheetFrame();
    }

    private void updateSpriteSheetFrame() {
        updateActualCooldownOnFrame();
        if (needToChangeFrame()) {
            double minPositionX = actualAnimationState.getMinX();
            double maxPositionX = actualAnimationState.getMaxX();
            double minPositionY = actualAnimationState.getMinY();
            double maxPositionY = actualAnimationState.getMaxY();
            setPositionOfNextFrame(minPositionX, maxPositionX, minPositionY, maxPositionY, spriteSheetProperty.getSheetWidth());
            restoreActualCooldown();
        }
    }

    private void updateActualCooldownOnFrame() {
        actualCooldownOnFrame -= 1;
    }

    private boolean needToChangeFrame() {
        return (actualCooldownOnFrame <= 0);
    }

    private void setPositionOfNextFrame(double minXPosition, double maxXPosition, double minYPosition, double maxYPosition, double sheetWidth) {
        //Finished one cycle
        actualFramePositionX += spriteSheetProperty.getWidthOfOneFrame();
        if (actualFramePositionX >= maxXPosition && actualFramePositionY >= maxYPosition) {
            actualFramePositionX = minXPosition;
            actualFramePositionY = minYPosition;
        }
        //Stepped out of sheet
        else if (actualFramePositionX >= sheetWidth) {
            actualFramePositionX = 0;
            actualFramePositionY += spriteSheetProperty.getHeightOfOneFrame();
        }
    }

    private void restoreActualCooldown() {
        actualCooldownOnFrame = spriteSheetProperty.getTimeOnFrameInAnimation();
    }

    @Override
    public void render() {
        renderSprite(spriteSheetProperty.getSheet());
    }

    protected void renderSprite(Image spriteSheet) {
        double widthOfOneFrame = spriteSheetProperty.getWidthOfOneFrame();
        double heightOfOneFrame = spriteSheetProperty.getHeightOfOneFrame();
        manager.getGraphicContext().drawImage(spriteSheet, actualFramePositionX, actualFramePositionY,
                widthOfOneFrame, heightOfOneFrame, positionX, positionY, width, height);
    }

    @Override
    public void changeState(IKindOfState state) {
        actualAnimationState = spriteSheetProperty.getAction(state);
    }

    public ImageSheetProperty getSpriteSheetProperty() {
        return spriteSheetProperty;
    }
}
