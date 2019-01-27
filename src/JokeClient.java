/*

1. Jeff Wiand / 1-27-19
2. Java 1.8
3. Compilation Instructions:
    > javac JokeClient.java

4. Run Instructions
    > java JokeServer
    > java JokeClient
    > java JokeClientAdmin

   List of files needed for running the program
    - checklist.html
    - JokeServer.java
    - JokeClient.java
    - JokeClientAdmin.java

5. My Notes

 */


import java.io.*;               //Pull in the Java Input - Output libraries for JokeClient.java use
import java.net.*;              //Pull in the Java networking libraries for JokeClient.java use
import java.util.*;             //Pull in the Java utility libraries for JokeClient.java use


public class JokeClient {                                                                           // JokeClient class declaration

    public static void main (String args[]) {                                                       // main function that will execute when JokeClient is run
        String serverName;                                                                          // Local JokeClient definition "serverName" of type String
        int jokeIndex=0;                                                                            // Local JokeClient definition "jokeIndex" of type int, set equal to zero
        int proverbIndex=0;                                                                         // Local JokeClient definition "proverbIndex" of type int, set equal to zero
        ArrayList<Integer> indexArray = new ArrayList<>();                                          // Local JokeClient instantiation of indexArray ArrayList
        if (args.length < 1) serverName = "localhost";                                              // Sets serverName to localhost if no client input on the initial execution of JokeClient
        else serverName = args[0];                                                                  // Sets serverName to the first indexArray of the client input

        System.out.println("Now Communicating with : " + serverName + ", Port: 43000");             // print statement to the console that tell the host name and port number
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));                   // launches one new BufferReader object to handle client input


        List<String> jokeRandOrder = new ArrayList<>();                                             // create a random joke order ArrayList and add "A", "B", "C", "D" to it
        jokeRandOrder.add("A");
        jokeRandOrder.add("B");
        jokeRandOrder.add("C");
        jokeRandOrder.add("D");

        Collections.shuffle(jokeRandOrder);                                                         // randomize our jokes arrayList

        StringBuilder jokeOrderString = new StringBuilder(jokeRandOrder.size());                    // build the random order string of jokes to be sent to JokeServer for processing
        for(String s: jokeRandOrder){
            jokeOrderString.append(s);
        }

        List<String> proverbRandOrder = new ArrayList<>();                                          // create a random proverb order ArrayList and add "A", "B", "C", "D" to it
        proverbRandOrder.add("A");
        proverbRandOrder.add("B");
        proverbRandOrder.add("C");
        proverbRandOrder.add("D");

        Collections.shuffle(proverbRandOrder);                                                      //randomize our proverbs array

        StringBuilder proverbOrderString = new StringBuilder(proverbRandOrder.size());              // build the random order string of proverbs to be sent to JokeServer for processing
        for(String s: proverbRandOrder){
            proverbOrderString.append(s);
        }

        try {                                                                                       // start of error catching with try block
            String userName;                                                                        // local definition of user's name of type String
            String anotherJoke;                                                                     // local definition of anotherJoke of type String

            System.out.print("Enter your name please, (quit) to end: ");                            // Ask the user for their name once.
            System.out.flush();                                                                     // clears out the "out buffer"
            userName = in.readLine();                                                               // store user response as userName type string outside the loop

            System.out.print("\nPress Enter to receive a joke or proverb: \n ");
            System.out.flush();

            String userId = UUID.randomUUID().toString();                                           // generate UUID and cast to String for future refactors. It is only stored on the Server but doesn't effect this version of the code implementation

            do {
                anotherJoke = in.readLine();                                                             // if the client hits enter, it will print another joke in the server
                indexArray.clear();                                                                      // clear out the indexArray ArrayList each loop

                if(anotherJoke.indexOf("quit") < 0 && userName.indexOf("quit")<0) {                                                                                     // if the client doesn't initially type quit or type quit in the console in any subsequent iterations, execute the function call
                    indexArray = getJokeProverb(userName, userId, jokeOrderString.toString(), proverbOrderString.toString(), jokeIndex, proverbIndex, serverName);      // set indexArray equal to return value of getJokeProverb (jokeIndex in first indexArray, proverbIndex in second indexArray)

                    jokeIndex = indexArray.get(0);                                                           // set jokeIndex to the first indexArray of arrayList named indexArray
                    proverbIndex = indexArray.get(1);                                                        // set jokeIndex to the first indexArray of arrayList named indexArray
                }

            } while (anotherJoke.indexOf("quit") < 0 && userName.indexOf("quit")<0);                        // continue the loop until the user types quit on the initial prompt or in any subsequent joke/proverb iterations
            System.out.println ("Cancelled by user request.");

        } catch (IOException x) {x.printStackTrace ();}                                                     // handles any IOExceptions and prints the error trail to the client
    }


    static ArrayList<Integer> getJokeProverb(String userName, String userId, String jokeOrderString, String proverbOrderString,Integer jokeIndex, Integer proverbIndex, String serverName){             //custom method that returns the appropriate joke or proverb from JokeServer

        Socket sock;                                                                    // local definition of sock of type Socket
        BufferedReader fromServer;                                                      // local definition of fromServer of type BufferedReader
        PrintStream toServer;                                                           // local definition of toServer of type PrintStream
        String textFromServer;                                                          // local definition of textFromServer of type String

        ArrayList<Integer> index = new ArrayList<>();                                   // instantiate index of type ArrayList to handle the incoming jokeIndex and proverbIndex states
        index.clear();                                                                  // make sure the index is empty at the start of each call

        try{
            sock = new Socket(serverName, 43000);                                                      // Declare a new socket object and bind our new communication channel to port 43000

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));                  //Launch new BufferedReader object and set equal to locally defined fromServer
            toServer = new PrintStream(sock.getOutputStream());                                             //Launch new PrintStream object and set equal to locally defined toServer

            toServer.println(userId);                                                                       // sends UUID string to JokeServer
            toServer.println(userName+":"+jokeOrderString+":"+proverbOrderString);                          // sends username, jokeOrder, and proverbOrder in one string to JokeServer
            toServer.println(jokeIndex);                                                                    // sends jokeIndex integer to JokeServer
            toServer.println(proverbIndex);                                                                 // sends proverbIndex integer to JokeServer

            toServer.flush();                                                                               // clears out the toServer buffer

            for(int i=0; i<3; i++) {                                                            // receives a 3 line response from JokeServer if no exceptions on server.
                textFromServer = fromServer.readLine();
                if (textFromServer != null && i==1) {                                           // Receives the jokeIndex from the JokeServer, converts to an integer, and adds to index arrayList
                    jokeIndex = Integer.parseInt(textFromServer);
                    index.add(jokeIndex);
                } else if (textFromServer != null && i==2) {                                    // Receives the proverbIndex from the JokeServer, converts to an integer, and adds to index arrayList
                    proverbIndex = Integer.parseInt(textFromServer);
                    index.add(proverbIndex);
                }
                else if (textFromServer !=null && i==0) System.out.println(textFromServer);     // Receives either a joke or a proverb from the JokeServer and prints it to the JokeClient console
            }

            sock.close();                                                                       // closes only the current connection
        }
        catch(IOException x) {                                                                  //handles any IOException then displays the error trail to the client
            System.out.println ("Socket error.");
            x.printStackTrace ();
        }

        return index;                                                                           // return the index arrayList contain our joke and proverb states as indices 0 and 1 respectively
    }
}


