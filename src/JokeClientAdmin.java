/*

1. Jeff Wiand / 1-27-19
2. Java 1.8
3. Compilation Instructions:
    > javac JokeClientAdmin.java

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

import java.io.*;       //Pull in the Java Input - Output libraries for JokeServer.java use
import java.net.*;      //Pull in the Java networking libraries for JokeServer.java use

public class JokeClientAdmin {
    public static void main(String args[]) throws IOException {
        String serverName;
        if (args.length < 1) serverName = "localhost";
        else serverName = args[0];

        System.out.println("Now Communicating with : " + serverName + ", Port: 45000");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String mode="JOKE";                                                                                      //Start with JOKE mode by default
            String anotherMode;

            do {
                anotherMode = in.readLine();
                toggleMode(mode, serverName);
                if(mode.equals("JOKE")) mode = "PROVERB";
                else if (mode.equals("PROVERB")) mode = "JOKE";
            } while (anotherMode.indexOf("quit") < 0);
            System.out.println ("Cancelled by user request.");

        } catch (IOException x) {x.printStackTrace ();}
    }

    static void toggleMode(String mode, String serverName){
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try{
            sock = new Socket(serverName, 45000);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(mode);
            toServer.flush();

            for(int i=0; i<1; i++) {
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


class AdminWorker extends Thread {
    Socket sock;

    public AdminWorker(Socket s) {
        sock = s;
    }

    public void run() {
        PrintStream out = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());
            try {
                String mode;
                mode = in.readLine();

                System.out.println("The Mode from AdminWorker = " + mode);
                out.println("Mode = " + mode);

            } catch (IndexOutOfBoundsException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}



