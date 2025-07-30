import javax.imageio.IIOException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    //TODO FIX NAMES BUTTONS MY HAVING MESSAGE SEND TO SERVER AND THEN GETTING A RESPONSE OF STRINGS WITH NAMES
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has conected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e) {

        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
