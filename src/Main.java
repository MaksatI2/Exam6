import server.BasicServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BasicServer server = new BasicServer(9000);
        server.start();
    }
}