package gamelogic;

/**
 * The Interface Task.
 * @param <T> the generic type
 */
public interface Task<T> {
    /**
     * Define what to do when task is set to run.
     * @return the t
     */
    T run();
}
