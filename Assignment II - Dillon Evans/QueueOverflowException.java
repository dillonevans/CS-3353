/**
 * This Exception is Thrown When the Queue is Full
 */
public class QueueOverflowException extends Exception {
    private static final long serialVersionUID = 1L;
    @Override
    public String getMessage() {
        return "Error: The Queue is Full";
    }
}
