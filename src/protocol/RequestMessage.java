package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestMessage implements Serializable {

    private MessageType type;
    private Map<String,String> body;

    public RequestMessage(MessageType msgType) {
        this.type = msgType;
        body = new HashMap<>();
    }

    public MessageType getRequestType(){return type;}

    public void addBodyField(String key, String value){
        body.put(key,value);
    }

    public String getBodyField(String fieldKey){
        return body.get(fieldKey);
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "type=" + type +
                ", body=" + body +
                '}';
    }
}