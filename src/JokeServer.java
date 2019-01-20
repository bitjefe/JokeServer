/*

1. Jeff Wiand / 1-27-19
2. Java 1.8
3. Compilation Instructions:
    > javac JokeServer.java

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

    *Jokes taken from http://pun.me/pages/dad-jokes.php
    *Proverbs taken from https://web.sonoma.edu/users/d/daniels/chinaproverbs.html

     joke bit representation          proverb bit representation      jokes sent bitrep    proverbs sent bitrep

       ABCD = 000001 = 1 = 0x01         ABCD = 000001 = 1 = 0x01          0 = 000 = 0x0         0 = 000 = 0x0
       ABDC = 000010 = 2 = 0x02         ABDC = 000010 = 2 = 0x02          1 = 001 = 0x1         1 = 001 = 0x1
       ACBD = 000011 = 3 = 0x03         ACBD = 000011 = 3 = 0x03          2 = 010 = 0x2         2 = 010 = 0x2
       ACDB = 000100 = 4 = 0x04         ACDB = 000100 = 4 = 0x04          3 = 011 = 0x3         3 = 011 = 0x3
       ABDC = 000101 = 5 = 0x05         ABDC = 000101 = 5 = 0x05          4 = 100 = 0x4         4 = 100 = 0x4
       ADCB = 000110 = 6 = 0x06         ADCB = 000110 = 6 = 0x06

       BACD = 000111 = 7 = 0x07         BACD = 000111 = 7 = 0x07
       BADC = 001000 = 8 = 0x08         BADC = 001000 = 8 = 0x08
       BCAD = 001001 = 9 = 0x09         BCAD = 001001 = 9 = 0x09
       BCDA = 001010 = 10 = 0x0A        BCDA = 001010 = 10 = 0x0A
       BDAC = 001011 = 11 = 0x0B        BDAC = 001011 = 11 = 0x0B
       BDCA = 001100 = 12 = 0x0C        BDCA = 001100 = 12 = 0x0C

       CABD = 001101 = 13 = 0x0D        CABD = 001101 = 13 = 0x0D
       CADB = 001110 = 14 = 0x0E        CADB = 001110 = 14 = 0x0E
       CBAD = 001111 = 15 = 0x0F        CBAD = 001111 = 15 = 0x0F
       CBDA = 010000 = 16 = 0x10        CBDA = 010000 = 16 = 0x10
       CDAB = 010001 = 17 = 0x11        CDAB = 010001 = 17 = 0x11
       CDBA = 010010 = 18 = 0x12        CDBA = 010010 = 18 = 0x12

       DABC = 010011 = 19 = 0x13        DABC = 010011 = 19 = 0x13
       DACB = 010100 = 20 = 0x14        DACB = 010100 = 20 = 0x14
       DBAC = 010101 = 21 = 0x15        DBAC = 010101 = 21 = 0x15
       DBCA = 010110 = 22 = 0x16        DBCA = 010110 = 22 = 0x16
       DCAB = 010111 = 23 = 0x17        DCAB = 010111 = 23 = 0x17
       DCBA = 011000 = 24 = 0x18        DCBA = 011000 = 24 = 0x18


        * make an array that represents all states of
        * joke order + proverb order + # jokes sent + # proverbs sent   =      binary        = hex    = decimal      mod4       mod16

            ABCD     +     ABCD      +       0      +       0           = 000001 000001 000 000 = 0x1040 = 4160        0        0
            ABCD     +     ABCD      +       0      +       1           = 000001 000001 000 001 = 0x1041 = 4161        1        1
            ABCD     +     ABCD      +       0      +       2           = 000001 000001 000 010 = 0x1042 = 4162        2        2
            ABCD     +     ABCD      +       0      +       3           = 000001 000001 000 011 = 0x1043 = 4163        3        3
            ABCD     +     ABCD      +       0      +       4           = 000001 000001 000 100 = 0x1044 = 4164        0        4

            ABCD     +     ABCD      +       1      +       0           = 000001 000001 001 000 = 0x1048 = 4168        0        8
            ABCD     +     ABCD      +       1      +       1           = 000001 000001 001 001 = 0x1049 = 4169        1        9
            ABCD     +     ABCD      +       1      +       2           = 000001 000001 001 010 = 0x104A = 4170        2        10
            ABCD     +     ABCD      +       1      +       3           = 000001 000001 001 011 = 0x104B = 4171        3        11
            ABCD     +     ABCD      +       1      +       4           = 000001 000001 001 100 = 0x104C = 4172        0        12

            ABCD     +     ABCD      +       2      +       0           = 000001 000001 010 000 = 0x1050 = 4176        0        0
            ABCD     +     ABCD      +       2      +       1           = 000001 000001 010 001 = 0x1051 = 4177        1        1
            ABCD     +     ABCD      +       2      +       2           = 000001 000001 010 010 = 0x1052 = 4178        2        2
            ABCD     +     ABCD      +       2      +       3           = 000001 000001 010 011 = 0x1053 = 4179        3        3
            ABCD     +     ABCD      +       2      +       4           = 000001 000001 010 100 = 0x1054 = 4180        0        4

            ABCD     +     ABCD      +       3      +       0           = 000001 000001 011 000 = 0x1058 = 4184        0        8
            ABCD     +     ABCD      +       3      +       1           = 000001 000001 011 001 = 0x1059 = 4185        1        9
            ABCD     +     ABCD      +       3      +       2           = 000001 000001 011 010 = 0x105A = 4186        2        10
            ABCD     +     ABCD      +       3      +       3           = 000001 000001 011 011 = 0x105B = 4187        3        11
            ABCD     +     ABCD      +       3      +       4           = 000001 000001 011 100 = 0x105c = 4188        0        12

            ABCD     +     ABCD      +       4      +       0           = 000001 000001 100 000 = 0x1060 = 4192        0        0
            ABCD     +     ABCD      +       4      +       1           = 000001 000001 100 001 = 0x1061 = 4193        1        1
            ABCD     +     ABCD      +       4      +       2           = 000001 000001 100 010 = 0x1062 = 4194        2        2
            ABCD     +     ABCD      +       4      +       3           = 000001 000001 100 011 = 0x1063 = 4195        3        3
            ABCD     +     ABCD      +       4      +       4           = 000001 000001 100 100 = 0x1064 = 4196        0        4


        //iterate over the 1st column on jState array looking to see if userId matches. then process jokes/ proverbs
        // lock after joke mode while loop or before? what's the critical section??


        *Next combination

            ABCD     +     ABDC      +       0      +       0           = 000001 000010 000 000 = 0x1080 = 4224        0

            ABCD     +     ABCD      +       1      +       0           = 000001 000001 001 000 = 0x1048 = 4168        0
            ABCD     +     ABDC      +       0      +       1           = 000001 000010 000 001 = 0x1081 = 4225        1


       *example jState search:
       * Joke Mode Enabled, Order = ABCD
       * Proverb Mode Disabled, Order = ABCD
       * At start the jState =  000001 000001 000000 = 0x1040 = 4160
       * Joke A is delivered, State updates to = 000001 000001 001000 = 0x1048 = 4168           + 8 decimal
       * Joke B is delivered, jState updates to = 000001 000001 010000 = 0x1050 = 4176           + 8 decimal
       * Joke C is delivered, jState updates to = 000001 000001 011000 = 0x1058 = 4184           + 8 decimal
       * Joke D is delivered, jState updates to = 000001 000001 100000 = 0x1060 = 4192           + 8 decimal
       * Message announces "JOKE CYCLE COMPLETED"
       * State resets to no jokes delivered = 000001 000001 000000 = 0x1040 = 4160              (4192-4160 = 32 decimal)
       * Repeat until switched to proverb mode or quit


                   //while(mode == joke){
            for(int i = 0; i< jState.length; i++){
                if (jState[i][0] == userId ){
                    if(jState[i[9] == 0)                                     //will adding one to jState work if proverbs != 0 (need another loop for this)
                        System.out.println(jState[i][4]);
                    userId = jState[i+1][0] ;                                 //how to handle the 4 different jState incrementers?  Do this 4 different times to handle breaks and switches to proverb
                else (jState[i][9] == 1)
                    System.out.println(jState[i][5]);
                    userId = jState[i+1][0];
                else (jState[i][9] == 2)
                    System.out.println(jState[i][6]);
                    userId = jState[i+1][0];
                else (jState[i][9] == 3)
                    System.out.println(jState[i][7]);
                    userId = jState[i+1][0];
                else (jState[i][9] == 4)
                    System.out.println(jState[i][8]);
                    userId = jState[i-4][0];
                }
            }


 */

import java.io.*;       //Pull in the Java Input - Output libraries for JokeServer.java use
import java.net.*;      //Pull in the Java networking libraries for JokeServer.java use
import java.util.*;


class Worker extends Thread {
    Socket sock;

    Worker(Socket s) {
        sock = s;
    }

    public void run() {

        String jokeIndexString;
        int jokeIndex=0;
        PrintStream out = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());


            try {
                String userName;
                String userId;
                String mode = "JOKE";
                //String mode = "PROVERB";
                
                //mode = in.readLine();
                userId = in.readLine();
                userName = in.readLine();
                jokeIndexString = in.readLine();
                jokeIndex = Integer.parseInt(jokeIndexString);

                getJokeProverb(userName, userId, jokeIndex, mode, out);

            } catch (IndexOutOfBoundsException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    static void getJokeProverb(String userName, String userId, Integer jokeIndex, String mode, PrintStream out) {

        List<String> userIdArray = new ArrayList<>();
        List<String> userState = new ArrayList<>();


        String[] jokes = {"JA " + userName + ": 5/4 of people admit that they’re bad with fractions.",
                "JB " + userName + ": Why did the coffee file a police report? It got mugged",
                "JC " + userName +": What do you call an elephant that doesn't matter? An irrelephant",
                "JD " + userName + ": Why did the scarecrow win an award? Because he was outstanding in his field."};

        String[] proverbs = {"PA " + userName + ": Good is the Enemy of Great",
                "PB " + userName + ": Wonder is the beginning of wisdom. ",
                "PC " + userName + ": To have principles first have courage",
                "PD " + userName + ": Determination tempers the sword of your character."};


        String[][] jState = {

                {"ABCBABCD00", "000001000001000000", "4160", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD10", "000001000001001000", "4168", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD20", "000001000001010000", "4176", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD30", "000001000001011000", "4184", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD40", "000001000001100000", "4192", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},

        };


        if(mode.equals("JOKE")){
            try {

                if(userIdArray.contains(userId))
                    System.out.println("user already exists");
                else
                    userIdArray.add(userId);

                if (jokeIndex == 4) {
                    out.println("Joke Cycle Complete");

                } else {
                    out.println(jokes[jokeIndex]);
                }
            } catch (IndexOutOfBoundsException ex) {
                out.println("Failed in attempt to look up " + userId);
            }
        }

        else if (mode.equals("PROVERB")){
            try {

                if(userIdArray.contains(userId))
                    System.out.println("user already exists");
                else
                    userIdArray.add(userId);

                if (jokeIndex == 4) {
                    out.println("Proverb Cycle Complete");

                } else {
                    out.println(proverbs[jokeIndex]);
                }
            } catch (IndexOutOfBoundsException ex) {
                out.println("Failed in attempt to look up " + userId);
            }
        }
    }

}

public class JokeServer {

    public static void main(String a[]) throws IOException {
        int q_len = 6;
        int port = 43000;
        Socket sock;

        AdminLooper AL = new AdminLooper(); // create a DIFFERENT thread
        Thread t = new Thread(AL);
        t.start();  // ...and start it, waiting for administration input

        ServerSocket servsock = new ServerSocket(port, q_len);

        System.out.println("Joke Server starting up, listening at port 43000. \n");
        while (true) {
            sock = servsock.accept();
            new Worker(sock).start();
        }
    }
}

class AdminLooper implements Runnable {
    public static boolean adminControlSwitch = true;

    public void run() { // Running the Admin listen loop
        int q_len = 6; /* Number of requests for OpSys to queue */
        int port = 45000;  // We are listening at a different port for Admin clients
        Socket sock;

        try {
            ServerSocket servsock = new ServerSocket(port, q_len);
            System.out.println("Mode Server starting up, listening at port 45000. \n");
            while (adminControlSwitch) {
                // wait for the next ADMIN client connection:
                sock = servsock.accept();
                new AdminWorker(sock).start();
            }
        } catch (IOException ioex) {
            System.out.println(ioex);
        }
    }
}
