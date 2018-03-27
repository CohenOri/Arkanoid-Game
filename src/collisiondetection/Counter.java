package collisiondetection;

/**
 * Counter is a simple class that is used for counting things
 * support increase, decrease and create.
 */
public class Counter {
    private int count;
    /**
     * create Counter.
     * @param counter give it base number.
     */
    public Counter(int counter) {
        this.count = counter;
    }
    /**
     * @param number adds given number to Counter.
     */
    public void increase(int number) {
        this.count += number;
    }
    /**
     * @param number subtracts given number from Counter.
     */
    public void decrease(int number) {
        this.count -= number;
    }
    /**
     * @return get Counter value.
     */
    public int getValue() {
        return this.count;
    }
    /**
     * @param newVal set Counter value.
     */
    public void setValue(int newVal) {
        this.count = newVal;
    }
}