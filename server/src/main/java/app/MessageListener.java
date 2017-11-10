package app;

import java.io.IOException;

public interface MessageListener {

    void accept(String message) throws IOException;
}
