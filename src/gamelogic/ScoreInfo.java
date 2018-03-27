package gamelogic;

import java.io.Serializable;

/**
 * The Class ScoreInfo.
 */
public class ScoreInfo implements Serializable, Comparable<ScoreInfo> {
    private String playerName;
    private int playerScore;

    /**
     * Instantiates a new score info.
     * @param name the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Gets the score.
     * @return the score
     */
    public int getScore() {
        return this.playerScore;
    }
    /**
     * @return defines how to print the score.
     */
    public String toString() {
        return this.playerName + " ---> " + this.playerScore;
    }

    /**
     * Define comparable of scoreInfo object, sort score from high to low.
     * @param o the other scoreInfo to compare to
     * @return  sort score from high to low.
     */
    public int compareTo(ScoreInfo o) {
        if (o.getScore() < this.getScore()) {
            return -1;
        }
        if (o.getScore() == this.getScore()) {
            return 0;
        }
        return 1;
    }
}
