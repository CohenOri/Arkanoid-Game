package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import biuoop.GUI;
import gamelogic.AnimationRunner;
import gamelogic.HighScoresAnimation;
import gamelogic.HighScoresTable;
import gamelogic.MenuAnimation;
import gamelogic.QuitTask;
import gamelogic.ShowHighScoreTask;
import gamelogic.StartGameTask;
import gamelogic.Task;
import io.LevelSetReader;
import level.LevelInformation;

/**
 * A class contain only main method to run the Ass6Game.
 */
public class Ass6Game {

    /**
     * Main method to run the Ass6Game.
     * @param args the "level_sets.txt" file path, if empty uses "level_sets.txt".
     */
    public static void main(String[] args) {
        // initalizition
        GUI gui = new GUI("Arkanoid", 800, 600);
        HighScoresTable hST = createHighScoresTable();
        HighScoresAnimation hsa = new HighScoresAnimation(hST);
        AnimationRunner runner = new AnimationRunner(gui);
        InputStream is = null;
        int lives = 7;
        while (true) {
            try {
                String path;
                if (args.length == 0) {
                    path = "level_sets.txt";
                } else {
                    path = args[0];
                }
                // read the level_sets file.
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
                Reader reader = new BufferedReader(new InputStreamReader(is));
                // create Main Menu animation, add the options to it.
                MenuAnimation<Task<Void>> mAnimation = new MenuAnimation<Task<Void>>(gui, runner);
                mAnimation.addSelection("h", "High Scores", new ShowHighScoreTask(runner, hsa, gui));
                mAnimation.addSelection("q", "Quit", new QuitTask());
                // Create subMenu
                MenuAnimation<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(gui, runner);
                // Create level set reader in order to read the level set file
                LevelSetReader levelSetReader = new LevelSetReader(gui);
                // Receives symbol to levels map from the levelSetReader.
                Map<Character, String> keyToDescription = levelSetReader.getLevelSetDescription();
                Map<Character, List<LevelInformation>> keyToLvlList = levelSetReader.fromReader(reader);
                // add the the the levels set to the sub menu as options (eg. easy, hard...)
                for (Character key : keyToDescription.keySet()) {
                    subMenu.addSelection(Character.toString(key), keyToDescription.get(key),
                            new StartGameTask(gui, hST, keyToLvlList.get(key), lives));
                }
                // add the subMenu to Main menu
                mAnimation.addSubMenu("s", "Start", subMenu);
                // run the main menu
                runner.run(mAnimation);
                // wait for user selection
                Task<Void> task = mAnimation.getStatus();
                task.run();
                mAnimation.setToRun();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            // try to close the input stream reader.
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return HighScoresTable read from file (if there is no file create one).
     */
    private static HighScoresTable createHighScoresTable() {
        // if file "highScores" exist read from it
        File f = new File("highscores");
        HighScoresTable hst = new HighScoresTable(3);
        if (f.exists() && !f.isDirectory()) {
            try {
                hst.load("highscores");
            } catch (IOException e) {
                System.out.println("couldn't read using Load");
                e.printStackTrace();
            }
        } else {
            // if it doesn't exist create new table and save it to "high Scores"
            // file
            try {
                hst.save("highscores");
            } catch (IOException e) {
                System.out.println("problem saving file");
                e.printStackTrace();
            }
        }
        return hst;
    }
}
