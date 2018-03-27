package gamelogic;

import biuoop.DrawSurface;
import collisiondetection.Counter;

/**
 * GameOver animation to display score and if Won or Lost.
 */
public class GameOver implements Animation {

    private boolean stop;
    private Counter score;
    private Counter lives;
    /**
     * Create animation.
     * @param endlives - how much lives have now (to check if lost or finished the game)
     * @param endScore - current score.
     */
    public GameOver(Counter endlives, Counter endScore) {
        this.stop = false;
        this.score = endScore;
        this.lives = endlives;
    }
    /**
     * runs one frame of the Animation. define what to show in each scenario.
     * @param d surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // if lost because of lives
        if (this.lives.getValue() <= 0) {
            d.drawText(d.getWidth() / 2 - 200, d.getHeight() / 2 - 50, "Game Over. Your score is: "
                            + this.score.getValue(),
                    32);
            d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 , "press space to High Scores Table", 20);
        } else {
            // if won because cleared all lvls
            d.drawText(d.getWidth() / 2 - 200, d.getHeight() / 2 - 50, "You Won! Your score is: "
                            + this.score.getValue(),
                    32);
            d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 , "press space to High Scores Table", 20);
        }
    }
    /**
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
