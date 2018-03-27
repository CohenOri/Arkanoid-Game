package gamelogic;

/**
 * The Class QuitTask.
 */
public class QuitTask implements Task<Void> {

    /**
     * Instantiates a new quit task.
     */
    public QuitTask() {
    }

    /**
     * Quit the game when set to run.
     * @return the t
     */
    public Void run() {
        System.exit(0);
        return null;
    }

}
