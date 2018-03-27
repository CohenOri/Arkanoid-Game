package gamelogic;

import biuoop.GUI;

/**
 * The Class ShowHighScoreTask.
 */
public class ShowHighScoreTask implements Task<Void> {

    private GUI gui;
    private AnimationRunner runner;
    private HighScoresAnimation highScoresAnimation;

    /**
     * Instantiates a new show high score task.
     * @param runner the runner
     * @param highScoresAnimation the high scores animation
     * @param g the g
     */
    public ShowHighScoreTask(AnimationRunner runner, HighScoresAnimation highScoresAnimation, GUI g) {
        this.gui = g;
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * When task is set to run show highScoresAnimation.
     * @return void
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.gui.getKeyboardSensor(),
                this.gui.getKeyboardSensor().SPACE_KEY, this.highScoresAnimation));
        return null;
    }

}
