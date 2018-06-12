package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseMessage implements Serializable {
    private MessageType type;
    private String status;
    private Map<String,String> body;

    public ResponseMessage(){

        body = new HashMap<>();
    }

    public void setResponseType(MessageType type){
        this.type = type;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public void addBodyField(String key, String value){
        body.put(key,value);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "type=" + type +
                ", status='" + status + '\'' +
                ", body=" + body +
                '}';
    }
}
