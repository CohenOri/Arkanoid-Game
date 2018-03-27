package level;

import java.io.IOException;
import java.util.List;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collisiondetection.Counter;
import gamelogic.AnimationRunner;
import gamelogic.GameOver;
import gamelogic.HighScoresAnimation;
import gamelogic.HighScoresTable;
import gamelogic.KeyPressStoppableAnimation;
import gamelogic.ScoreInfo;

/**
 * Class in charge of Game Flow, run each given level one after another.
 */
public class GameFlow {

    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private GUI gui;
    private KeyboardSensor ks;
    private HighScoresTable highScoresTable;

    /**
     * Start to run the levels on the given GUI, with the given Lives.
     * @param g gui to run levels on
     * @param lives lives to start game with
     * @param hScoresTable current HighScoresTable
     */
    public GameFlow(GUI g, Counter lives, HighScoresTable hScoresTable) {
        this.gui = g;
        this.score = new Counter(0);
        this.numberOfLives = lives;
        this.runner = new AnimationRunner(g);
        this.ks = g.getKeyboardSensor();
        this.highScoresTable = hScoresTable;
    }

    /**
     * @param levels List contain the levels to run on the GUI.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(this.score, this.numberOfLives, levelInfo, this.gui);
            level.initialize();
            while (level.getRemainedBlocks().getValue() > 0 && this.numberOfLives.getValue() > 0) {
                level.run();
            }
            if (this.numberOfLives.getValue() < 0) {
                // end game lost because of lives
                this.runner.run(
                        new KeyPressStoppableAnimation(ks, ks.SPACE_KEY, new GameOver(this.numberOfLives, this.score)));
                checkIfScoreShouldEnterHighScore();
                this.runner.run(new KeyPressStoppableAnimation(ks, ks.SPACE_KEY,
                        new HighScoresAnimation(this.highScoresTable)));
                return;
            }
        }
        // end game finished all the lvls
        this.runner.run(new KeyPressStoppableAnimation(ks, ks.SPACE_KEY, new GameOver(this.numberOfLives, this.score)));
        checkIfScoreShouldEnterHighScore();
        this.runner
                .run(new KeyPressStoppableAnimation(ks, ks.SPACE_KEY, new HighScoresAnimation(this.highScoresTable)));
    }

    /**
     * check if the player's score entitles him to be listed on the high-scores
     * table. If it does, ask the player for his name, add his name to the
     * table, and save the table.
     */
    private void checkIfScoreShouldEnterHighScore() {
        // if current score should enter the HighScores table (because it should
        // replace\there is still free slots)
        if (this.score.getValue() > this.highScoresTable.minScoreInTable()
                || this.highScoresTable.getNumOfScoresInTable() < this.highScoresTable.getTableSize()) {
            DialogManager dialog = this.gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
            try {
                this.highScoresTable.save("highscores");
            } catch (IOException e) {
                System.out.println("problem saving file");
                e.printStackTrace();
            }
        }
    }

}