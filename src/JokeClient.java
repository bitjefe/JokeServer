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
import java.util.UUID;  //Pull in the Java library for creating a unique identifier


public class JokeClient {
    public static void main (String args[]) {
        String serverName;
        int jokeIndex=0;
        if (args.length < 1) serverName = "localhost";
        else serverName = args[0];

        System.out.println("Now Communicating with : " + serverName + ", Port: 43000");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
                getJokeProverb(userName, userId, jokeIndex, serverName);
                jokeIndex++;
                if(jokeIndex==5){
                    jokeIndex=0;
                }
            } while (anotherJoke.indexOf("quit") < 0);
            System.out.println ("Cancelled by user request.");

        } catch (IOException x) {x.printStackTrace ();}
    }


    static void getJokeProverb(String userName, String userId, Integer jokeIndex, String serverName){
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try{
            sock = new Socket(serverName, 43000);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(userId);
            toServer.println(userName);
            toServer.println(jokeIndex);
            toServer.flush();

            for(int i=0; i<5; i++) {
                textFromServer = fromServer.readLine();
                if (textFromServer != null) System.out.println(textFromServer);
            }

            sock.close();
        }
        catch(IOException x) {
            System.out.println ("Socket error.");
            x.printStackTrace ();
        }
    }
}
