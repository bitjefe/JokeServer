//Jeff Wiand
//InetServer.java File


import java.io.*;       //Pull in the Java Input - Output libraries for InetClient.java use
import java.net.*;      //Pull in the Java networking libraries for InetClient.java use

public class InetClient {                           //InetClient class declaration
    public static void main (String args[]) {           //the function that will execute when InetClient class is run
        String serverName;                                      //Local InetClient definition "serverName" of type String
        if (args.length < 1) serverName = "localhost";          //Sets serverName to localhost if no client input on the initial execution of InetClient
        else serverName = args[0];                              //Sets serverName to the first index of the client input

        System.out.println("Jeff Wiand's Inet Client, 1.8. \n");                    //print statements to the console that tell the client information, host name, and Port number
        System.out.println("Using server : " + serverName + ", Port: 43000");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));   //launches one new BufferReader object to handle client input
        try {
            String name;                                                                    //local definition "name" of type String
            do {                                                                            //Starts a do-while loop to prompt the client for Hostname or IP address input
                System.out.print("Enter a hostname or an IP Address, (quit) to end: ");
                System.out.flush();                                                         //clears out the "out buffer"
                name = in.readLine();                                                       //takes in the client input, assigns to String "name"
                if (name.indexOf("quit") < 0)                                               //If quit is not entered, call our custom method getRemoteAddress and serve it with name, Servername
                    getRemoteAddress(name, serverName);
            } while (name.indexOf("quit") < 0);                                             //continue do-while loop as long as quit isn't entered by the client
            System.out.println ("Cancelled by user request.");                              //if quit is entered by the client, print this message to the client
        } catch (IOException x) {x.printStackTrace ();}                                     //handles the IOException's and displays the error trail to the client
    }


    static String toText (byte ip[]) {                                    //custom function that makes the hostName or IP  a String and returns it
        StringBuffer result = new StringBuffer();
        for (int i=0; i < ip.length; ++i) {
            if(i > 0) result.append(".");
            result.append(0xff & ip[i]);
        }
        return result.toString();
    }

    static void getRemoteAddress (String name, String serverName){              //custom method that accepts Strings representing the client hostName or IP address and String equal to our server
        Socket sock;                                                            //Local getRemoteAddress definition "sock" of type Socket
        BufferedReader fromServer;                                              //Local getRemoteAddress definition "fromServer" of type BufferedReader
        PrintStream toServer;                                                   //Local getRemoteAddress definition "toServer" of type PrintStream
        String textFromServer;                                                  //Local getRemoteAddress definition "textFromServer" of type String

        try{                                                                    //try-block error catching

            /* Declare a new Socket Object and Bind our new communication channel to port 43000. */
            sock = new Socket(serverName, 43000);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));          //Launch new BufferedReader object and set equal to locally defined fromServer
            toServer = new PrintStream(sock.getOutputStream());                                     //Launch new PrintStream object and set equal to locally defined toServer

            toServer.println(name);                 //print our hostName or IP address over to our InetServer window
            toServer.flush();                       //clears out our "toServer" buffer

            for(int i=1; i<=3; i++){                        //receives a 3 line response from server if no exceptions on server.  Reads 2 line response from server if exceptions occur
                textFromServer = fromServer.readLine();
                if (textFromServer !=null) System.out.println(textFromServer);
            }
            sock.close();               //closes only the current connection
        }
        catch(IOException x) {                      //handles any IOException then displays the error trail to the client
            System.out.println ("Socket error.");
            x.printStackTrace ();
        }
    }
}
