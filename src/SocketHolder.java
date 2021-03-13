
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*main goal of socket handler:
gets passed socket
creates output stream
creates new thread/listener with inputstream with a
while loop that listens to socket/client
listener handles input and talks to chat/game on its own
when chat/game needs to write to socket, uses function in handler
 */

public class SocketHolder {
    private Socket socket;
    private DataOutputStream dataOut;
    private boolean connected;


    SocketHolder(Socket newSocket) throws IOException, InterruptedException {
        this.socket = newSocket;
        dataOut = new DataOutputStream(socket.getOutputStream());
        new Thread(new SocketListener(socket)).start();
        this.connected = true;
        //SocketListener listener = new SocketListener(socket);
        //new Thread(listener).start();

        grabChatLog(dataOut);


    }

    private void grabChatLog(DataOutputStream outStream) throws InterruptedException, IOException {
        int chatLogPullAttempts = 0;
        while (chatLogPullAttempts < 10){
            if (!Server.chatLogInUse){//if not in use take use
                //turn serverinuse into a chat new thread that stops controll after 10 secs.
                //what do i do to socket after 10 secs?
                Server.chatLogInUse = true;
                int length = Server.serverChatLog.getMaxLength();
                String[] words = new String[length];
                words = Server.serverChatLog.getWord(length);

                outStream.writeInt(length);//chat size
                for (int i = 0; i < length; i++) {
                    String msg = "1".concat(words[i]);//1 for chat
                    outStream.writeChars(msg);
                }
                outStream.writeChars(Server.game.getGuessed()); //passed guesed letters



            } else{
                chatLogPullAttempts++;
                Thread.sleep(5000);

            }
        }


    }

    public void writeToClient(String msg) throws IOException {//writes to chat log
        dataOut.writeBytes(msg);
    }

/*
    After connecting first time:
    first message (int) is how many chat messages its going to recieve
    then sends chat log 1 string at a time
    then sends current word
    then sends string of guessed letters


    begins with 4 string of updated word
    begins with 5 string of 1 char wrong guess
    begins with 6 new word

 */
    public void updateGame(String gameState) throws IOException{//
        dataOut.writeBytes(gameState);
    }


}



