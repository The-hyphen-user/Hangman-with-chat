import java.io.IOException;

public class Chat {
    /*
    main goal of chat:
    keeps a log of recent messages
    takes new messages from listener,adds to log, sends to all clients
    when new client is made, sends whole chat log to them
    uses string array with pointer to current positon
    just rewrites over its self,
    max size cannot grow or shrink(could be implemented but no reason)
     */

    private String[] chatlog;
    private int currentLength;
    private int currentPosition;
    private int maxSize;//can never be 0
    private boolean isFull;
    private int grabLength;
    //public static boolean chatLogInUse = false;




    Chat(int desiredLength){
        chatlog = new String[desiredLength];
        currentLength = 0;
        currentPosition = 0;
        maxSize = desiredLength;
        isFull = false;


    }
    public int getMaxLength(){//how do i make sure these lines happen together before a new word is made
        grabLength = currentLength;
        return currentLength;
    }

    public String[] getWord(int wordsPulled){//get string []
        String[] words = new String[wordsPulled];
        int tempCurrentPosition = currentPosition;
        int tempCurrentLength = currentLength;
        for (int i = 0; i < wordsPulled; i++) {
            if(tempCurrentPosition+i<tempCurrentLength) {
                words[i] = chatlog[tempCurrentPosition+i];
            } else {
                words[i] = chatlog[tempCurrentPosition+i-tempCurrentLength];
            }
        }

        return words;
    }

    public void addString(String msg) throws IOException {
        if (isFull){
            if (currentPosition != maxSize-1){
                currentPosition++;
            } else {
                currentPosition = 0;
            }
        }else if (currentPosition != maxSize-1){
            currentPosition++;
        } else {
            isFull = true;
            currentPosition = 0;
        }
        chatlog[currentPosition] = msg;
        updateClients(msg);
    }

    private void updateClients(String msg) throws IOException {
        for (SocketHolder aSocketHolder:Server.bagOfSockets) {
            aSocketHolder.writeToClient(msg);
        }
    }


}
