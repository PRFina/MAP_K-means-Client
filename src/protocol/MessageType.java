package protocol;

import java.io.Serializable;

public enum MessageType implements Serializable {
    DISCOVER, //Discover new clusters from db and save it to file
    READ, // Load clusters from file
    INFO, // Get metadata information about data on the server (Check if is really needed)
}
