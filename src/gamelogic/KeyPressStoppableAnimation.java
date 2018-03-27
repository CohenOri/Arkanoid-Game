package gamelogic;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Class KeyPressStoppableAnimation.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor ks;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new key press stoppable animation.
     * Runs the animation as long the stop key isn't pressed.
     * @param sensor the KeyboardSensor
     * @param stopKey the stop key
     * @param animation the animation to stop
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String stopKey, Animation animation) {
        this.ks = sensor;
        this.key = stopKey;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * runs one frame of the Animation. define what to show in each scenario.
     * @param d surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // run the animation
        animation.doOneFrame(d, dt);
        // if the stopping key is pressed stop the animation
        if (this.ks.isPressed(this.key) && !isAlreadyPressed) {
            this.stop = true;
        }
        // if key is not pressed
        if (!this.ks.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * Should stop.
     * @return if should stop run the Animation.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
