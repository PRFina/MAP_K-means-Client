package protocol.protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class RequestMessage {

    private MessageType type;
    private Map<String,String> body;

    RequestMessage(MessageType msgType) {
        this.type = msgType;
        body = new HashMap<>();
    }

    void addBodyField(String key, String value){
        body.put(key,value);
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            RequestMessage msg = new RequestMessage(MessageType.DISCOVER);
            msg.addBodyField("iterations","10");
            msg.addBodyField("table","someTable");
            msg.addBodyField("file","someTable.dat");

            out.writeObject(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
