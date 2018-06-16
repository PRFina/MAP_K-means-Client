package actions;

import exceptions.ServerException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Action {

    ObjectInputStream in;
    ObjectOutputStream out;

    protected Action(ObjectInputStream in, ObjectOutputStream out){
        this.in = in;
        this.out = out;
    }

    public abstract String execute() throws ServerException;
}
