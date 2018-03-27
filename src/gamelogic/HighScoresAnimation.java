package gamelogic;

import biuoop.DrawSurface;

/**
 * The Class HighScoresAnimation.
 */
public class HighScoresAnimation implements Animation {

    private boolean stop;
    private HighScoresTable hScoresTable;

    /**
     * Instantiates a new high scores animation.
     * high scores animation show nicely the HighScoresTable
     * @param scores HighScoresTable to show.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.stop = false;
        this.hScoresTable = scores;
    }

    /**
     * runs one frame of the Animation. define what to show in each scenario.
     * @param d surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // print HighScoresTable to the screen
        d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 - 220, "High Scores", 50);
        int i = 0;
        for (ScoreInfo scoreInfo : this.hScoresTable.getHighScores()) {
            d.drawText(d.getWidth() / 2 - 200, d.getHeight() / 2 - 150 + i * 35, "" + scoreInfo.getName(), 32);
            d.drawText(d.getWidth() / 2 + 150, d.getHeight() / 2 - 150 + i * 35, "" + scoreInfo.getScore(), 32);
            i++;
        }
        d.drawText(d.getWidth() / 2 - 300, d.getHeight() / 2 - 150 + i * 35 + 50, "Press Space to Main Menu", 50);
    }

    /**
     * Should stop.
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
