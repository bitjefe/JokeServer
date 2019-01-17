//Jeff Wiand
//InetServer.java File

import java.io.*;       //Pull in the Java Input - Output libraries for InetServer.java use
import java.net.*;      //Pull in the Java networking libraries for InetServer.java use

class Worker extends Thread {           //Class declaration for Worker which will be a subclass of Thread class
    Socket sock;                        //Local Worker definition "sock" of type Socket

    Worker(Socket s) {
        sock = s;           //constructor to accept the incoming sockets and set to local Socket definition called "sock"
    }

    public void run() {         // method launched with the .start() call in InetServer class

        PrintStream out = null;         //sets our output stream to null. PrintStream's can be flushed and don't throw IOExceptions
        BufferedReader in = null;       //sets our input to null
        try {                                                                           //start of error checking with first try block
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));      //launched new object to obtain our input
            out = new PrintStream(sock.getOutputStream());                              //launched new object to print our output

            try {                                                          //second try block for error checking input
                String name;
                name = in.readLine();                                       //gets the hostName or IP
                System.out.println("Looking up " + name);
                printRemoteAddress(name, out);                              //calls our custom printRemoteAddress method below with our string input and output
            } catch (IOException x) {                                       //if there's an IOException...do the following below:
                System.out.println("Server read error");                    //handles the IOException's and displays the error trail to the client
                x.printStackTrace();
            }
            sock.close();                                       //closes only the current connection
        } catch (IOException ioe) {                             //if there's an IOException...do the following below:
            System.out.println(ioe);                            //handles the IOException's and displays the error trail to the client
        }
    }


    static void printRemoteAddress(String name, PrintStream out) {          //custom method that accepts a string and PrintStream for the client's viewing
        try {
            out.println("Looking up " + name + "...");
            InetAddress machine = InetAddress.getByName(name);              //gets IP address of provided host
            out.println("Host name : " + machine.getHostName());            //prints string representing the hostName
            out.println("Host IP : " + toText(machine.getAddress()));       //prints the newly transformed IP address string using our custom method
        } catch (UnknownHostException ex) {                                 //if there's an IOException...do the following below:
            out.println("Failed in attempt to look up " + name);            //Let the client know that there was a failed attempt and where
        }
    }

    static String toText (byte ip[]) {                      //custom method that transforms client input to String for use in the printRemoteAddress method
        StringBuffer result = new StringBuffer();
        for(int i=0; i < ip.length; ++i){
            if(i > 0) result.append(".");
            result.append(0xff & ip[i]);
        }
        return result.toString();
    }
}

public class InetServer {

    public static void main(String a[]) throws IOException {
        int q_len = 6;      // the amount of requests to hold in line before not accepting more requests
        int port = 43000;   // Use port=43000 since it's not listed in Apple Support but high enough to avoid issues.  Also Learned that ports 42000-42999 are used for iTunes Radio on Mac.
        Socket sock;        // Local InetSever definition "sock" of type Socket

        ServerSocket servsock = new ServerSocket(port, q_len);  // Local InetServer object declaration "servsock" as type ServerSocket that will wait for requests at port 43000 with possible 6 incoming connections

        System.out.println("Jeff Wiand's Inet Server 1.8 starting up, listening at port 43000. \n");
        while (true) {
            sock = servsock.accept();  // continuously listening to set incoming connections to feed into our worker object
            new Worker(sock).start();  // launches a new worker object with the incoming connection
        }
    }
}
