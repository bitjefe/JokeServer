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



                    /*
        String[][] jState = {

                {"ABCBABCD00", "000001000001000000", "4160", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD10", "000001000001001000", "4168", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD20", "000001000001010000", "4176", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD30", "000001000001011000", "4184", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},
                {"ABCBABCD40", "000001000001100000", "4192", jokes[0], jokes[1], jokes[2], jokes[3], "Joke Cycle Complete"},

        };
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

        String clientNameAndOrderString;
        String jokeIndexString;
        String proverbIndexString;
        String jokeOrderString;
        String proverbOrderString;

        int jokeIndex=0;
        int proverbIndex = 0;
        PrintStream out = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());

            try {
                String userName;
                String userId;

                userId = in.readLine();
                clientNameAndOrderString = in.readLine();

                String[] clientNameAndOrderArray = clientNameAndOrderString.split(":");

                userName = clientNameAndOrderArray[0];
                jokeOrderString = clientNameAndOrderArray[1];
                proverbOrderString =  clientNameAndOrderArray[2];

                jokeIndexString = in.readLine();
                jokeIndex = Integer.parseInt(jokeIndexString);

                proverbIndexString = in.readLine();
                proverbIndex = Integer.parseInt(proverbIndexString);


                getJokeProverb(userName, userId, jokeOrderString, proverbOrderString, jokeIndex, proverbIndex, out);

            } catch (IndexOutOfBoundsException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    static void getJokeProverb(String userName, String userId, String jokeOrderString, String proverbOrderString, Integer jokeIndex, Integer proverbIndex, PrintStream out) {

        List<String> userIdArray = new ArrayList<>();
        List<String> jokeOrderList = new ArrayList<>();
        List<String> proverbOrderList = new ArrayList<>();
        List<String> jokesList = new ArrayList<>();
        List<String> proverbsList = new ArrayList<>();


        // create own function to handle this parsing /adding
        String joke1 = String.valueOf(jokeOrderString.charAt(0));
        String joke2 = String.valueOf(jokeOrderString.charAt(1));
        String joke3 = String.valueOf(jokeOrderString.charAt(2));
        String joke4 = String.valueOf(jokeOrderString.charAt(3));

        jokeOrderList.add(joke1);
        jokeOrderList.add(joke2);
        jokeOrderList.add(joke3);
        jokeOrderList.add(joke4);

        System.out.println(jokeOrderList);

        String proverb1 = String.valueOf(proverbOrderString.charAt(0));
        String proverb2 = String.valueOf(proverbOrderString.charAt(1));
        String proverb3 = String.valueOf(proverbOrderString.charAt(2));
        String proverb4 = String.valueOf(proverbOrderString.charAt(3));

        proverbOrderList.add(proverb1);
        proverbOrderList.add(proverb2);
        proverbOrderList.add(proverb3);
        proverbOrderList.add(proverb4);

        System.out.println(proverbOrderList);


        String [][] jokesArr = {

                {"A", "JA " + userName + ": 5/4 of people admit that they’re bad with fractions."},
                {"B", "JB " + userName + ": Why did the coffee file a police report? It got mugged"},
                {"C", "JC " + userName + ": What do you call an elephant that doesn't matter? An irrelephant"},
                {"D", "JD " + userName + ": Why did the scarecrow win an award? Because he was outstanding in his field."}
        };

        String [][] proverbsArr = {

                {"A", "PA " + userName + ": Good is the Enemy of Great"},
                {"B", "PB " + userName + ": Wonder is the beginning of wisdom. "},
                {"C", "PC " + userName + ": To have principles first have courage"},
                {"D", "PD " + userName + ": Determination tempers the sword of your character."}
        };

        jokesList.add("JA " + userName + ": 5/4 of people admit that they’re bad with fractions.");
        jokesList.add("JB " + userName + ": Why did the coffee file a police report? It got mugged");
        jokesList.add("JC " + userName + ": What do you call an elephant that doesn't matter? An irrelephant");
        jokesList.add("JD " + userName + ": Why did the scarecrow win an award? Because he was outstanding in his field.");

        proverbsList.add("PA " + userName + ": Good is the Enemy of Great");
        proverbsList.add("PB " + userName + ": Wonder is the beginning of wisdom. ");
        proverbsList.add("PC " + userName + ": To have principles first have courage");
        proverbsList.add("PD " + userName + ": Determination tempers the sword of your character.");


        // do i need to store the UUID / jokeOrder / ProverbOrder / ProverbIndex / Joke Index

        if(JokeServer.JokeMode){
            try {
                if(userIdArray.contains(userId))
                    System.out.println("user already exists");
                else
                    userIdArray.add(userId);                        //UUID is added to the userIdArray (however the state is not recorded in the list with it since its only an arrayList. Refactor to array?

                if (jokeIndex == 4) {
                    out.println("Joke Cycle Complete");
                    jokeIndex = 0;

                } else {
                    if(jokeIndex ==0) {
                        if(jokeOrderList.get(0).equals("A")){
                            out.println(jokesArr[0][1]);
                            System.out.println((jokesArr[0][1]));
                        } else if(jokeOrderList.get(0).equals("B")){
                            out.println(jokesArr[1][1]);
                            System.out.println((jokesArr[1][1]));
                        } else if(jokeOrderList.get(0).equals("C")) {
                            out.println(jokesArr[2][1]);
                            System.out.println((jokesArr[2][1]));
                        } else if(jokeOrderList.get(0).equals("D")) {
                            out.println(jokesArr[3][1]);
                            System.out.println((jokesArr[3][1]));
                        }
                    }

                    else if(jokeIndex ==1) {
                        if(jokeOrderList.get(1).equals("A")){
                            out.println(jokesArr[0][1]);
                            System.out.println((jokesArr[0][1]));
                        } else if(jokeOrderList.get(1).equals("B")){
                            out.println(jokesArr[1][1]);
                            System.out.println((jokesArr[1][1]));
                        } else if(jokeOrderList.get(1).equals("C")) {
                            out.println(jokesArr[2][1]);
                            System.out.println((jokesArr[2][1]));
                        } else if(jokeOrderList.get(1).equals("D")) {
                            out.println(jokesArr[3][1]);
                            System.out.println((jokesArr[3][1]));
                        }
                    }

                    else if(jokeIndex ==2) {
                        if(jokeOrderList.get(2).equals("A")){
                            out.println(jokesArr[0][1]);
                            System.out.println((jokesArr[0][1]));
                        } else if(jokeOrderList.get(2).equals("B")){
                            out.println(jokesArr[1][1]);
                            System.out.println((jokesArr[1][1]));
                        } else if(jokeOrderList.get(2).equals("C")) {
                            out.println(jokesArr[2][1]);
                            System.out.println((jokesArr[2][1]));
                        } else if(jokeOrderList.get(2).equals("D")) {
                            out.println(jokesArr[3][1]);
                            System.out.println((jokesArr[3][1]));
                        }
                    }
                    else if(jokeIndex ==3) {
                        if(jokeOrderList.get(3).equals("A")){
                            out.println(jokesArr[0][1]);
                            System.out.println((jokesArr[0][1]));
                        } else if(jokeOrderList.get(3).equals("B")){
                            out.println(jokesArr[1][1]);
                            System.out.println((jokesArr[1][1]));
                        } else if(jokeOrderList.get(3).equals("C")) {
                            out.println(jokesArr[2][1]);
                            System.out.println((jokesArr[2][1]));
                        } else if(jokeOrderList.get(3).equals("D")) {
                            out.println(jokesArr[3][1]);
                            System.out.println((jokesArr[3][1]));
                        }
                    }

                    jokeIndex++;
                }
            } catch (IndexOutOfBoundsException ex) {
                out.println("Failed in attempt to look up " + userId);
            }
        }

        else {
            try {

                if(userIdArray.contains(userId))
                    System.out.println("user already exists");
                else
                    userIdArray.add(userId);

                if (proverbIndex == 4) {
                    out.println("Proverb Cycle Complete");
                    proverbIndex = 0;

                } else {
                    if(proverbIndex ==0) {
                        if(proverbOrderList.get(0).equals("A")){
                            out.println(proverbsArr[0][1]);
                            System.out.println((proverbsArr[0][1]));
                        } else if(proverbOrderList.get(0).equals("B")){
                            out.println(proverbsArr[1][1]);
                            System.out.println((proverbsArr[1][1]));
                        } else if(proverbOrderList.get(0).equals("C")) {
                            out.println(proverbsArr[2][1]);
                            System.out.println((proverbsArr[2][1]));
                        } else if(proverbOrderList.get(0).equals("D")) {
                            out.println(proverbsArr[3][1]);
                            System.out.println((proverbsArr[3][1]));
                        }
                    }

                    else if(proverbIndex ==1) {
                        if(proverbOrderList.get(1).equals("A")){
                            out.println(proverbsArr[0][1]);
                            System.out.println((proverbsArr[0][1]));
                        } else if(proverbOrderList.get(1).equals("B")){
                            out.println(proverbsArr[1][1]);
                            System.out.println((proverbsArr[1][1]));
                        } else if(proverbOrderList.get(1).equals("C")) {
                            out.println(proverbsArr[2][1]);
                            System.out.println((proverbsArr[2][1]));
                        } else if(proverbOrderList.get(1).equals("D")) {
                            out.println(proverbsArr[3][1]);
                            System.out.println((proverbsArr[3][1]));
                        }
                    }

                    else if(proverbIndex ==2) {
                        if(proverbOrderList.get(2).equals("A")){
                            out.println(proverbsArr[0][1]);
                            System.out.println((proverbsArr[0][1]));
                        } else if(proverbOrderList.get(2).equals("B")){
                            out.println(proverbsArr[1][1]);
                            System.out.println((proverbsArr[1][1]));
                        } else if(proverbOrderList.get(2).equals("C")) {
                            out.println(proverbsArr[2][1]);
                            System.out.println((proverbsArr[2][1]));
                        } else if(proverbOrderList.get(2).equals("D")) {
                            out.println(proverbsArr[3][1]);
                            System.out.println((proverbsArr[3][1]));
                        }
                    }
                    else if(proverbIndex ==3) {
                        if(proverbOrderList.get(3).equals("A")){
                            out.println(proverbsArr[0][1]);
                            System.out.println((proverbsArr[0][1]));
                        } else if(proverbOrderList.get(3).equals("B")){
                            out.println(proverbsArr[1][1]);
                            System.out.println((proverbsArr[1][1]));
                        } else if(proverbOrderList.get(3).equals("C")) {
                            out.println(proverbsArr[2][1]);
                            System.out.println((proverbsArr[2][1]));
                        } else if(proverbOrderList.get(3).equals("D")) {
                            out.println(proverbsArr[3][1]);
                            System.out.println((proverbsArr[3][1]));
                        }
                    }

                    proverbIndex++;
                }
            } catch (IndexOutOfBoundsException ex) {
                out.println("Failed in attempt to look up " + userId);
            }
        }

        out.println(jokeIndex);
        out.println(proverbIndex);

    }
}

public class JokeServer {

    static boolean JokeMode = true;
    //static boolean shutdown = false;

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
