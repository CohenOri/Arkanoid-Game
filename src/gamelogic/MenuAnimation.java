package gamelogic;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * The Class MenuAnimation.
 * @param <T> the generic type
 */
public class MenuAnimation<T> implements Menu<T> {

    private GUI gui;
    private boolean stop;
    private T status;
    private List<MenuSelection<T>> selections;
    private AnimationRunner runner;

    /**
     * Instantiates a new menu animation.
     * @param g the gui
     * @param animationRunner the animation runner
     */
    public MenuAnimation(GUI g, AnimationRunner animationRunner) {
        this.gui = g;
        this.stop = false;
        this.selections = new ArrayList<>();
        this.runner = animationRunner;
    }
    /**
     * How to draw the main menu on the screen.
     * @param d the surface to draw on
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // definition for readability
        KeyboardSensor ks = this.gui.getKeyboardSensor();
        // print Menu to the screen
        d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 - 220, "Main Menu", 50);
        int i = 0;
        for (MenuSelection<T> mSelection : this.selections) {
            d.drawText(d.getWidth() / 2 - 200, d.getHeight() / 2 - 150 + i * 35, "" + mSelection.getKey(), 32);
            d.drawText(d.getWidth() / 2 + 100, d.getHeight() / 2 - 150 + i * 35, "" + mSelection.getMessage(), 32);
            i++;
        }
        // check if specific option key is pressed, if pressed set status to key
        // returnVal
        for (MenuSelection<T> mSelection : this.selections) {
            if (ks.isPressed(mSelection.getKey())) {
                this.status = mSelection.getReturnVal();
                this.stop = true;
            }
        }
    }

    /**
     * Should stop.
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Adds the selection to menu.
     * @param key the key to press to select aka 'a'
     * @param message the message to display aka (start game)
     * @param returnVal the return val - the task to perform
     */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new MenuSelection<T>(key, message, returnVal));
    }

    /**
     * Gets the status.
     * @return the status
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * make sure MenuAnimation can run before Inserting it to AnimmationRunner.
     */
    public void setToRun() {
        this.stop = false;
    }
    /**
     * Adds the sub menu.
     * key = the key to press to enter the subMenu, message is the description
     * of the key (aka: start game)
     * menu is the menu to enter when pressing the key.
     * @param key the key
     * @param message the message
     * @param subMenu the sub menu
     */
    // another example
    // key = s, message = start game result = return new sub that should be run
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        Task<Void> startGame = new Task<Void>() {
            @Override
            public Void run() {
                // TODO Auto-generated method stub
                runner.run(subMenu);
                // wait for user selection
                Task<Void> task = (Task<Void>) subMenu.getStatus();
                task.run();
                ((MenuAnimation<Task<Void>>) subMenu).setToRun();
                return null;
            }
        };
        addSelection(key, message, (T) startGame);
    }
}
