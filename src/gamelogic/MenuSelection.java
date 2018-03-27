package gamelogic;

/**
 * Class to define MenuSelection object (variable).
 * @param <T> the generic type
 */
public class MenuSelection<T> {
    private String key;
    private String message;
    private T returnVal;

    /**
     * Instantiates a new menu selection.
     * Key to select option, Message = description of what pressing does, and return val
     * is what happens when pressed.
     * @param setKey the key
     * @param setMessage the message
     * @param setReturnVal the return val
     */
    public MenuSelection(String setKey, String setMessage, T setReturnVal) {
        this.key = setKey;
        this.message = setMessage;
        this.returnVal = setReturnVal;
    }

    /**
     * Gets the key.
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * @param setKey the new key
     */
    public void setKey(String setKey) {
        this.key = setKey;
    }

    /**
     * Gets the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param setMessage the new message
     */
    public void setMessage(String setMessage) {
        this.message = setMessage;
    }

    /**
     * Gets the return val.
     * @return the returnVal
     */
    public T getReturnVal() {
        return returnVal;
    }

    /**
     * Sets the return val.
     * @param setReturnVal the new return val
     */
    public void setReturnVal(T setReturnVal) {
        this.returnVal = setReturnVal;
    }
}
