import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketListener implements Runnable{
    /*
    main goal of listener:
    listens to client
    directs message to either game/chat
     */

    private Socket socket;
    private DataInputStream dataIn;


    SocketListener(Socket socket) throws IOException {
        this.socket = socket;
        dataIn = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

        while(true){
            try {
                String msg = dataIn.readUTF();
                if (msg.charAt(0) == 1){//chat message
                    System.out.println("Chat: "+msg.substring(1));
                    Server.serverChatLog.addString(msg.substring(1));
                } else if (msg.charAt(0) == 2){//game letter guess
                    System.out.println("Game: "+msg.substring(1));
                    Server.game.playerGuess(msg.substring(1));
                } else if (msg.charAt(0) == 3){//word suggestion
                    System.out.println("add word to que: "+msg.substring(1));
                    Server.game.addWord(msg.substring(1));

                } else {
                    System.out.println("error: dataIn invalid read, in socketlistener: "+msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
