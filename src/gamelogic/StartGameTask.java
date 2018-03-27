package gamelogic;

import java.util.List;
import biuoop.GUI;
import collisiondetection.Counter;
import level.GameFlow;
import level.LevelInformation;

/**
 * The Class StartGameTask.
 */
public class StartGameTask implements Task<Void> {

    private GUI gui;
    private HighScoresTable hst;
    private List<LevelInformation> lvls;
    private GameFlow gFlow;

    /**
     * Instantiates a new start game task.
     * Runs the given lvlList.
     * @param g the gui
     * @param highScoresTable the high scores table
     * @param lvlsList the lvls list
     * @param lives the lives of the player
     */
    public StartGameTask(GUI g, HighScoresTable highScoresTable, List<LevelInformation> lvlsList, int lives) {
        this.gui = g;
        this.hst = highScoresTable;
        this.lvls = lvlsList;
        this.gFlow = new GameFlow(gui, new Counter(lives), this.hst);
    }

    /**
     * When task is set to run lvlsList using given gameFlow.
     * @return void
     */
    public Void run() {
        this.gFlow.runLevels(lvls);
        return null;
    }

}
