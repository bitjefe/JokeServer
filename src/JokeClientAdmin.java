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
                    //String mode = "JOKE";
                    String mode = "PROVERB";
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

class AdminLooper implements Runnable {
    public static boolean adminControlSwitch = true;

    public void run() { // RUNning the Admin listen loop
        System.out.println("In the admin looper thread");

        int q_len = 6; /* Number of requests for OpSys to queue */
        int port = 5050;  // We are listening at a different port for Admin clients
        Socket sock;

        try {
            ServerSocket servsock = new ServerSocket(port, q_len);
            while (adminControlSwitch) {
                // wait for the next ADMIN client connection:
                sock = servsock.accept();
                new AdminWorker(sock).start();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}


public class JokeClientAdmin {

    public static void main(String a[]) throws IOException {
        int q_len = 6;
        int port = 4545;
        Socket sock;

        AdminLooper AL = new AdminLooper();
        Thread t = new Thread(AL);
        t.start();  //

        ServerSocket servsock = new ServerSocket(port, q_len);

        System.out.println("Jeff Wiand's Joke server starting up at port 4545.\n");
        while (true) {
            // wait for the next client connection:
            sock = servsock.accept();
            new Worker(sock).start();
        }
    }
}