package actions;

import exceptions.ServerException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Action {

    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    protected Action(final ObjectInputStream in,
                     final ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public abstract String execute() throws ServerException;
}
