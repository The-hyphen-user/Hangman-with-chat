import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    /*
    main goal of game:
    sets up word
    takes guesses
    takes words to add to list
    if guess is valid => informs all clients on location
    if guess is invalid => informs clients
    if game over => sets next word, informs clients
     */

    private Queue<String> playerQueue;//word list made by users
    private Queue<String> computerQueue;//word list made by server
    public int maxGuesses;
    public int currentGuesses;
    private String word;
    //private String hidden;//letters in the word that have not been guessed
    //private String revealedWord;//letters in word that have been guessed
    //private String alreadyGuessedLetters;//hash table of guessed
    //private hashtable;

    private HashSet<Character> hidden;
    private HashSet<Character> alreadyGuessedLetters;


    Game(int maxGuesses){
        playerQueue = new LinkedList<>();
        computerQueue = new LinkedList<>();

        computerQueue.add("bacon");
        computerQueue.add("hangman");
        computerQueue.add("computer");
        computerQueue.add("fireworks");
        computerQueue.add("shrubbery");

        this.maxGuesses = maxGuesses;
        this.currentGuesses = 0;


        alreadyGuessedLetters = new HashSet(30);
        hidden = new HashSet(30);
        nextWord();
        //alreadyGuessedLetters = "";
    }

    private void gameOver() {
    }

    public String getGuessed(){
        String[] array = new String[alreadyGuessedLetters.size()];
        alreadyGuessedLetters.toArray(array);
        String temp = array;

        return new String("word");
    }

    public void addWord(String msg){
        playerQueue.add(msg);
    }

    public void playerGuess(String letter) throws IOException {
        if (alreadyGuessedLetters.contains(letter)){
            //already guessed, do nothing
        } else if (word.contains(letter)){//correct guess
                alreadyGuessedLetters.add(letter);
                hidden.remove(letter);
                //hidden.replaceAll(letter, "0");

            } else{//bad guess
            currentGuesses++;
            if (currentGuesses>maxGuesses){//game over
                gameOver();
            }
            //not game over bad guess

        }
        if (hidden.isEmpty()){

            for (SocketHolder aHolder : Server.bagOfSockets) {
                aHolder.updateGame("6");
            }
            nextWord();
        }
        /*
        if (!alreadyGuessedLetters.contains(letter)) {//not repeat guess
            String stringOfUpdatedWord;
            if (hidden.contains(letter)) {//correct guess
                char[] updatedWord = revealedWord.toCharArray();
                while (hidden.contains(letter)) {
                    int locationOfGuess = hidden.indexOf(letter);//first index
                    hidden.replaceFirst(letter, "0");//no longer in hidden
                    updatedWord[locationOfGuess - 1] = word.charAt(locationOfGuess);
                }
                stringOfUpdatedWord = new String(updatedWord);
                stringOfUpdatedWord = "4".concat(stringOfUpdatedWord);
            } else {//wrong guess
                stringOfUpdatedWord = "5".concat(letter);
                currentGuesses++;
            }
            for (SocketHolder aSocket : Server.bagOfSockets) {//update word
                aSocket.updateGame(stringOfUpdatedWord);
            }
        }
        */
    }

    /*//terrrible original code
    public void playerGuess(String letter) throws IOException {
        if (!alreadyGuessedLetters.contains(letter)) {//not repeat guess
            String stringOfUpdatedWord;
            if (hidden.contains(letter)) {//correct guess
                char[] updatedWord = revealedWord;
                while (hidden.contains(letter)) {
                    int locationOfGuess = hidden.indexOf(letter);//first index
                    hidden.replaceFirst(letter, "0");//no longer in hidden
                    updatedWord[locationOfGuess - 1] = word.charAt(locationOfGuess);
                }
                stringOfUpdatedWord = new String(updatedWord);
                stringOfUpdatedWord = "4".concat(stringOfUpdatedWord);
            } else {//wrong guess
                stringOfUpdatedWord = "5".concat(letter);
                currentGuesses++;
            }
            for (SocketHolder aSocket : Server.bagOfSockets) {//update word
                aSocket.updateGame(stringOfUpdatedWord);
            }
        }
        //is a repeat, do nothing
    }
     */


    private void nextWord(){
        currentGuesses = 0;
        if (playerQueue.isEmpty()){
            if (!computerQueue.isEmpty()) {
                word = computerQueue.poll();
            } else {//both empty
                //code to get more words from file/online database
                System.out.println("out of words");//error message for now
            }
        } else {
            word = playerQueue.poll();
        }
        for (int i = 0; i < word.length(); i++) {
            hidden.add(word.charAt(i));
        }
        alreadyGuessedLetters.clear();
    }
}
