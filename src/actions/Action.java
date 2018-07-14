package actions;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provide a base implementation for an action.
 * <p>
 * Since each class communicate with server,
 * the class constructor provides, through constructor, the IO streams
 * injection needed to send and receive data from server.
 * </p>
 * <p>
 * The execute method must be defined in child classes and provides
 * the actual action logic.
 * </p>
 */
public abstract class Action {

    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    /**
     * Construct the Action instance with IO streams.
     *
     * @param in input stream obtained from associated socket
     * @param out output stream obtained from associated socket
     */
    protected Action(final ObjectInputStream in,
                     final ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Execute the current action.
     *
     * @return Server response encoded as string
     * @throws ServerException when response message have an error status.
     */
    public abstract String execute() throws ServerException;
}
