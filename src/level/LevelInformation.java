package level;

import java.util.List;

import block.Block;
import geometryprimitives.Velocity;
import sprites.Sprite;

/**
 * interface to define the base info each level should carry.
 */
public interface LevelInformation {
    /**
     * @return number Of Balls.
     */
    int numberOfBalls();

    /**
     * @return List with the initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    List<Velocity> initialBallVelocities();
    /**
     * @return paddleSpeed.
     */
    int paddleSpeed();
    /**
     * @return paddleWidth.
     */
    int paddleWidth();
    /**
     * @return level name as String will be displayed at the top of the screen.
     */
    String levelName();
    /**
     * @return a sprite with the background of the level
     */
    Sprite getBackground();
    /**
     * @return List contains
     * The Blocks that make up this level, each block contains its size, color and location.
     */
    List<Block> blocks();

    /**
     * @return Number of blocks that should be removed before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();

    /**
     * @return initial ball radius in this level.
     */
    int ballsRadius();
    /**
     * @return initial ball color in this level.
     */
    java.awt.Color ballsColor();
}