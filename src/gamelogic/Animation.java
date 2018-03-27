package gamelogic;

import biuoop.DrawSurface;
/**
 * interface to define base methods animation should contain.
 */
public interface Animation {
    /**
     * @param d How to draw one frame on d surface.
     * @param dt the dt
     */
    void doOneFrame(DrawSurface d, double dt);
    /**
     * @return when the animation should stop.
     */
    boolean shouldStop();

}