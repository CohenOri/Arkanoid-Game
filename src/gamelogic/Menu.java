package gamelogic;

/**
 * The Interface Menu.
 * @param <T> the generic type
 */
public interface Menu<T> extends Animation {

    /**
     * Adds the selection to menu.
     * @param key the key to press to select aka 'a'
     * @param message the message to display aka (start game)
     * @param returnVal the return val - the task to perform
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Gets the status.
     * @return the status
     */
    T getStatus();

    /**
     * Adds the sub menu.
     * key = the key to press to enter the subMenu, message is the description
     * of the key (aka: start game)
     * menu is the menu to enter when pressing the key.
     * @param key the key
     * @param message the message
     * @param subMenu the sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
