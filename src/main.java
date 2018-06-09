import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 9999);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            RequestMessage msg = new RequestMessage(MessageType.DISCOVER);
            msg.addBodyField("iterations","10");
            msg.addBodyField("table","someTable");
            msg.addBodyField("file","someTable.dat");

            out.writeObject(msg);

            try {
                ResponseMessage resp = (ResponseMessage) in.readObject();
                System.out.println(resp);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
