package gamelogic;

import biuoop.DrawSurface;
/**
 * Pause Screen animation if the user pressed Pause.
 */
public class PauseScreen implements Animation {

    private boolean stop;
    /**
     * Create Pause screen Animation.
     */
    public PauseScreen() {
        this.stop = true;
    }
    /**
     * runs one frame of the Animation. define what to show in each scenario.
     * @param d surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(150, d.getHeight() / 2, "paused - press space to continue", 32);
    }
    /**
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}