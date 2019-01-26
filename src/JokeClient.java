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


import java.io.*;       //Pull in the Java Input - Output libraries for InetClient.java use
import java.net.*;      //Pull in the Java networking libraries for InetClient.java use
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;  //Pull in the Java library for creating a unique identifier


public class JokeClient {
    public static void main (String args[]) {
        String serverName;
        int jokeIndex=0;
        int proverbIndex=0;
        ArrayList<Integer> index = new ArrayList<>();
        if (args.length < 1) serverName = "localhost";
        else serverName = args[0];

        System.out.println("Now Communicating with : " + serverName + ", Port: 43000");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // put this into it's on method later (generateRandom)????
        List<String> jokeRandOrder = new ArrayList<>();
        jokeRandOrder.add("A");
        jokeRandOrder.add("B");
        jokeRandOrder.add("C");
        jokeRandOrder.add("D");

        Collections.shuffle(jokeRandOrder);                     //randomize our jokes array

        StringBuilder jokeOrderString = new StringBuilder(jokeRandOrder.size());
        for(String s: jokeRandOrder){
            jokeOrderString.append(s);
        }

        List<String> proverbRandOrder = new ArrayList<>();
        proverbRandOrder.add("A");
        proverbRandOrder.add("B");
        proverbRandOrder.add("C");
        proverbRandOrder.add("D");

        Collections.shuffle(proverbRandOrder);                  //randomize our proverbs array

        StringBuilder proverbOrderString = new StringBuilder(proverbRandOrder.size());
        for(String s: proverbRandOrder){
            proverbOrderString.append(s);
        }

        System.out.println("Joke Order: "+ jokeOrderString);
        System.out.println("Proverb Order: " + proverbOrderString);

        try {
            String userName;                                                                        //local definition of user's name of type String
            String anotherJoke;

            System.out.print("Enter your name please, (quit) to end: ");                            //Ask the user for their name once
            System.out.flush();
            userName = in.readLine();                                                           //store user response as userName type string outside the loop

            String userId = UUID.randomUUID().toString();
            System.out.println("userIdString for " + userName + " = "+ userId);

            do {
                anotherJoke = in.readLine();                                                    //if the client hits enter, it will print another joke in the server
                index.clear();
                index = getJokeProverb(userName, userId, jokeOrderString.toString(), proverbOrderString.toString(), jokeIndex, proverbIndex, serverName);

                jokeIndex = index.get(0);
                proverbIndex = index.get(1);

            } while (anotherJoke.indexOf("quit") < 0);
            System.out.println ("Cancelled by user request.");

        } catch (IOException x) {x.printStackTrace ();}
    }


    static ArrayList<Integer> getJokeProverb(String userName, String userId, String jokeOrderString, String proverbOrderString,Integer jokeIndex, Integer proverbIndex, String serverName){
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        ArrayList<Integer> index = new ArrayList<>();
        index.clear();

        try{
            sock = new Socket(serverName, 43000);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(userId);                                                                       // sends UUID string to jokeServer
            toServer.println(userName+":"+jokeOrderString+":"+proverbOrderString);                          //sends username, jokeOrder, and proverbOrder in one string to jokeServer
            toServer.println(jokeIndex);
            toServer.println(proverbIndex);

            toServer.flush();

            for(int i=0; i<4; i++) {
                textFromServer = fromServer.readLine();
                if (textFromServer != null && i==1) {
                    jokeIndex = Integer.parseInt(textFromServer);
                    index.add(jokeIndex);
                } else if (textFromServer != null && i==2) {
                    proverbIndex = Integer.parseInt(textFromServer);
                    index.add(proverbIndex);
                }
                else if (textFromServer !=null && i==0) System.out.println(textFromServer);
            }

            sock.close();
        }
        catch(IOException x) {
            System.out.println ("Socket error.");
            x.printStackTrace ();
        }

        return index;
    }
}
