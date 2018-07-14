package actions;

import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

/**
 * This class model a discover action.
 * The discover action is performed when user wants
 * to mine a data-set stored in database
 * table
 */
public final class DiscoverAction extends Action {

    private final String tableName;
    private final String clusters;

    public DiscoverAction(final ObjectInputStream in,
                          final ObjectOutputStream out,
                          final String tableName,
                          final String clusters) {
        super(in, out);
        this.tableName = tableName;
        this.clusters = clusters;
    }

    @Override
    public String execute() throws ServerException {
        RequestMessage req = new RequestMessage(MessageType.DISCOVER);
        ResponseMessage resp = null;

        req.addBodyField("clusters", clusters);
        req.addBodyField("table", tableName);

        try {
            out.writeObject(req);
            resp = (ResponseMessage) in.readObject();

            if (resp.getStatus().equals("ERROR")) {
                throw new ServerException(resp.getBodyField("errorMsg"));
            }

        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null,
                    "There's some problem with connection",
                    "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resp.getBodyField("data");

    }
}
