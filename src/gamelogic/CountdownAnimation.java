package gamelogic;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {

    private double countDownTime;
    private int countFromValToZero;
    private SpriteCollection sprites;
    private boolean stop;

    private Sleeper s = new Sleeper();
    private int countDownOneIntervalInMiliSeconds;
    private boolean firstRun;
    /**
     * Create countDownAnimation.
     * @param numOfSeconds to display on screen
     * @param countFrom to 1 (then GO!!!)
     * @param gameScreen SpriteCollection to draw - the sprites to draw at at background - the game.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countDownTime = numOfSeconds;
        this.countFromValToZero = countFrom;
        this.sprites = gameScreen;
        this.stop = false;
        this.countDownOneIntervalInMiliSeconds = (int) ((this.countDownTime * 1000) / (countFromValToZero + 1));
        this.firstRun = true;
    }
    /**
     * runs one frame of the Animation. define what to do in each second of the countDown.
     * @param d surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (!this.firstRun) {
            s.sleepFor(countDownOneIntervalInMiliSeconds);
        }
        this.sprites.drawAllOn(d);
        if (countFromValToZero > 0) {
            d.setColor(java.awt.Color.MAGENTA);
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(countFromValToZero), 40);
        }
        if (countFromValToZero == 0) {
            d.setColor(java.awt.Color.YELLOW);
            d.drawText(d.getWidth() / 2 - 10, d.getHeight() / 2, "GO!", 40);
        }
        if (countFromValToZero < 0) {
            this.stop = true;
        }
        this.firstRun = false;
        countFromValToZero--;
    }
    /**
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
