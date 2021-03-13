# Hangman-with-chat
hangman gui that people can connect to and guess together, while a chat window for people to chat together


server

  -can connect to multiple clients, with unique names

  -if a socket is dropped,name gets take off name list/hash table
  
gui hang man client

  more advanced:

  -makes a gui that allows your to connect to server

    >when gui connects it loads game state

    >requests and loads last 10 messages

    >allows for guesses on current hangman game

    >allows you to type new messages to everyone
    
    
 less advanced:

  -ask for ip

  -loads current game

  -has message window for incoming chats

  -has text window for you to write messages

  -has text window for you to guess letters

  -has game pic  
 
 hang man game run by client

 either use words from an online dictionary or text file

  -picks word, starts game

  -after game is over restart after 10-15 sec
  
  if more advanced 
  
  -allows people to pick a word, keeps track of words picked in a que

  if no words in que then picks a word either from online or text file
  
  
  
