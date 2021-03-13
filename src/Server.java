import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
main goal of main:
creates game/chat/serversocket
creates array list of socketholders
if new socket connects makes a socketholder, adds to list
need to implement checking for if socket disconnected
need to implement checking if 21st socket/at capacity and
telling them server full then terminated connection
could check for valid connections after new socket connects???
 */
public class Server {

    public static Chat serverChatLog;
    public static ServerSocket serverSocket;
    public static Game game;
    public static boolean chatLogInUse = false;
    private static int port;


    public static ArrayList<SocketHolder> bagOfSockets;
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting up");

        serverChatLog = new Chat(10);
        System.out.println("Chat init.");
        game = new Game(8);
        System.out.println("game init.");


        bagOfSockets = new ArrayList<SocketHolder>(21);
        port = 9090;
        serverSocket = new ServerSocket(port);
        System.out.println("Accepting Sockets through port "+port);

        //21st socket is connected to,told server is at limit, then dropped
        //should i make an socket interface object that connects with each socket???
        //do i need 2 seperate objects for each server/socket one for input one for output
        //how do i differ client messaging all a message vs guessing a hangman letter



        //after everything is set up




        boolean full = false;
        while (!full){
            Socket newSocket = serverSocket.accept();
            if (bagOfSockets.size() < 21) {
                bagOfSockets.add(new SocketHolder(newSocket));
            }
        }


    }
}
/*
still need to figure out which part of program
 decides if its a valid new word to use
 i could put it into client and test if a few times
 no spaces, all lower case, etc...

 also letter guesses need to be changed to lower case

 need to decide which part of program updates all clients
 when someone writes a chat message to everyone else

 need to impliment chat function that relays
 all recent messages when client connect for first time
 */