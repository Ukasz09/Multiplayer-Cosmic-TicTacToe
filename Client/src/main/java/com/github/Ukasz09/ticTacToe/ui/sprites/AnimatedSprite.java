package com.github.Ukasz09.ticTacToe.ui.sprites;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.FrameStatePositions;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.IKindOfState;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import javafx.scene.image.Image;

public class AnimatedSprite extends ImageSprite implements IAnimatedSpriteGraphic {
    private ImageSheetProperty sheet;
    private FrameStatePositions actualAnimationState;
    private double actualFramePositionX;
    private double actualFramePositionY;
    private double millisOnFrame;
    private double timerStartTime;

    //-----------------------------------------------------------------------------------------------------------------//
    public AnimatedSprite(double width, double height, double positionX, double positionY, ImageSheetProperty sheetProperty, boolean withImageViewInRoot) {
        this(width, height, positionX, positionY, sheetProperty, sheetProperty.getAction(SpriteStates.STANDBY), withImageViewInRoot);
    }

    public AnimatedSprite(double width, double height, double positionX, double positionY, ImageSheetProperty sheet, FrameStatePositions startedAnimationState,
                          boolean withImageViewInRoot) {
        super(width, height, ImagesProperties.schemeSpriteForImageView(), positionX, positionY, withImageViewInRoot);
        this.sheet = sheet;
        this.actualAnimationState = startedAnimationState;
        this.millisOnFrame = sheet.getMillisOnFrame();
        this.timerStartTime = System.currentTimeMillis();
        setRandomStartedFramePosition();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    protected void setRandomStartedFramePosition() {
        int randomFrameIndex = sheet.getAction(SpriteStates.STANDBY).getRandomIndex();
        actualFramePositionX = sheet.getPositionOfIndex(randomFrameIndex).getX();
        actualFramePositionY = sheet.getPositionOfIndex(randomFrameIndex).getY();
    }

    @Override
    public void update() {
        super.update();
        updateSpriteSheetFrame();
    }

    private void updateSpriteSheetFrame() {
        if (needToChangeFrame()) {
            double minPositionX = actualAnimationState.getMinX();
            double maxPositionX = actualAnimationState.getMaxX();
            double minPositionY = actualAnimationState.getMinY();
            double maxPositionY = actualAnimationState.getMaxY();
            setPositionOfNextFrame(minPositionX, maxPositionX, minPositionY, maxPositionY, sheet.getSheetWidth());
            timerStartTime = System.currentTimeMillis();
        }
    }

    private boolean needToChangeFrame() {
        double elapsedTime = Math.abs(System.currentTimeMillis() - timerStartTime);
        return elapsedTime >= millisOnFrame;
    }

    private void setPositionOfNextFrame(double minXPosition, double maxXPosition, double minYPosition, double maxYPosition, double sheetWidth) {
        //Finished one cycle
        actualFramePositionX += sheet.getWidthOfOneFrame();
        if (actualFramePositionX >= maxXPosition && actualFramePositionY >= maxYPosition) {
            actualFramePositionX = minXPosition;
            actualFramePositionY = minYPosition;
        }
        //Stepped out of sheet
        else if (actualFramePositionX >= sheetWidth) {
            actualFramePositionX = 0;
            actualFramePositionY += sheet.getHeightOfOneFrame();
        }
    }

    @Override
    public void render() {
        renderSprite(sheet.getSheet());
    }

    protected void renderSprite(Image spriteSheet) {
        double widthOfOneFrame = sheet.getWidthOfOneFrame();
        double heightOfOneFrame = sheet.getHeightOfOneFrame();
        manager.getGraphicContext().drawImage(spriteSheet, actualFramePositionX, actualFramePositionY,
                widthOfOneFrame, heightOfOneFrame, positionX, positionY, width, height);
    }

    @Override
    public void changeState(IKindOfState state) {
        actualAnimationState = sheet.getAction(state);
    }
}
