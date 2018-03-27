package gamelogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class HighScoresTable.
 */
public class HighScoresTable implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ScoreInfo> scoreTable;
    private int tableSize;
    private int numOfScoresInTable;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.scoreTable = new ArrayList<ScoreInfo>();
        this.tableSize = size;
        this.numOfScoresInTable = 0;
    }

    /**
     * Adds the given high-score.
     * @param score the score
     */
    public void add(ScoreInfo score) {
        Integer minScoreInScoreTable = minScoreInTable();
        // if the table still not full, add any score
        if (this.numOfScoresInTable < this.tableSize) {
            this.scoreTable.add(score);
            this.numOfScoresInTable++;
        } else {
            // if table is full, check if this score should replace another
            // score or not
            if (score.getScore() > minScoreInScoreTable) {
                // score worth entering the high scores table add it and remove
                // the min score.
                this.scoreTable.add(score); // adds the new score.
                // find the ScoreInfo with min value in the table, remove it
                // from "scoreTable"
                List<ScoreInfo> scoreTableCopy1 = new ArrayList<ScoreInfo>(this.scoreTable);
                for (ScoreInfo s : scoreTableCopy1) {
                    if (s.getScore() == minScoreInScoreTable) {
                        this.scoreTable.remove(s);
                    }
                }
            } else {
                int nothing = 0;
                // when score doesn't worth entering the table do nothing.
            }
        }
    }
    /**
     * @return min Score In Table.
     */
    public Integer minScoreInTable() {
        // define min Score In Table
        Integer minScoreInScoreTable = Integer.MAX_VALUE;
        // find the min value of the scoreTable
        List<ScoreInfo> scoreTableCopy = new ArrayList<ScoreInfo>(this.scoreTable);
        for (ScoreInfo s : scoreTableCopy) {
            if (s.getScore() <= minScoreInScoreTable) {
                minScoreInScoreTable = s.getScore();
            }
        }
        return minScoreInScoreTable;
    }
    /**
     * @return the current scoreTable size.
     */
    public int size() {
        return this.scoreTable.size();
    }
    /**
     * Return the current high scores as list of ScoreInfo.
     * The list is sorted such that the highest scores come first.
     * @return high scores list
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreTable;
    }
    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     * @param score the current score
     * @return the rank
     */
    public int getRank(int score) {
        int rank = 1;
        for (ScoreInfo s : this.scoreTable) {
            if (score > s.getScore()) {
                return rank;
            }
            rank++;
        }
        return rank;
    }
    /**
     * Clears the high score table.
     */
    public void clear() {
        this.scoreTable.clear();
    }
    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void load(String fileName) throws IOException {
        this.clear();
        this.setNumOfScoresInTable(HighScoresTable.loadFromFile(fileName).getNumOfScoresInTable());
        this.setScoreTable(HighScoresTable.loadFromFile(fileName).getScoreTable());
        this.setTableSize(HighScoresTable.loadFromFile(fileName).getTableSize());
    }
    /**
     * Gets the score table.
     * @return the scoreTable
     */
    public List<ScoreInfo> getScoreTable() {
        return scoreTable;
    }

    /**
     * Sets the score table.
     * @param scoreTableToSet the scoreTable to set
     */
    public void setScoreTable(List<ScoreInfo> scoreTableToSet) {
        this.scoreTable = scoreTableToSet;
    }

    /**
     * Gets the table size.
     * @return the tableSize
     */
    public int getTableSize() {
        return tableSize;
    }

    /**
     * Sets the table size.
     * @param setTableSize the tableSize to set
     */
    public void setTableSize(int setTableSize) {
        this.tableSize = setTableSize;
    }

    /**
     * Gets the num of scores in table.
     * @return the numOfScoresInTable
     */
    public int getNumOfScoresInTable() {
        return numOfScoresInTable;
    }

    /**
     * Sets the num of scores in table.
     * @param newNumOfScoresInTable the new num of scores in table
     */
    public void setNumOfScoresInTable(int newNumOfScoresInTable) {
        this.numOfScoresInTable = newNumOfScoresInTable;
    }

    /**
     * Save table data to the specified file.
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void save(String fileName) throws IOException {
        Collections.sort(this.scoreTable);
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(this);
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }
    /**
     * Load a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param fileName the file name
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(String fileName) {
        ObjectInputStream oin = null; // in order to finally to recognize it.
        // if file exist try to read from it
        try {
            FileInputStream fis = new FileInputStream(fileName);
            oin = new ObjectInputStream(fis);
            return (HighScoresTable) oin.readObject();
        } catch (Exception e) {
            // Couldn't find or read the file
            return new HighScoresTable(0);
        } finally {
            // close the ObjectInputStream
            try {
                if (oin != null) {
                    oin.close();
                }
            } catch (Exception e2) {
                System.out.println("Odd problem while reading: " + e2);
            }
        }
    }

}
