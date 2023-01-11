package connectfour.client;

import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Represents the client side of a Connect Four game. Establishes a connection
 * with the server and then responds to requests from the server (often by
 * prompting the real user).
 *
 * @author Zack Hudgins
 */
public class ConnectFourClient implements ConnectFourProtocol {

    public Socket socket;
    public InputStream input;
    public OutputStream output;
    public Scanner scanner;
    public PrintStream printer;
    public Scanner user_input;

    /**
     * constructor for ConnectFourClient . initializes I/O and connects to server
     * @param hostname
     * @param port
     * @throws IOException
     */
    public ConnectFourClient(String hostname, int port) throws IOException {
        System.out.println("yuh");

        socket = new Socket(hostname, port);

        input = socket.getInputStream();
        output = socket.getOutputStream();
        scanner = new Scanner(input);
        printer = new PrintStream(output);
        user_input = new Scanner(System.in);

        if(scanner.nextLine().equals(CONNECT)) {
            System.out.println("Connected!");
        }else{
            System.out.println("PROBLEM!!!");
        }
    }

    /**
     * responds to all protocol requests (with a protocol of its own if necessary)
     */
    public void respond(){
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.equals(MAKE_MOVE)) {
                System.out.println("Your turn! Enter a column: ");
                String text = user_input.nextLine();
                printer.println(MOVE + " " + text);
            }else if(line.split(" ")[0].equals(MOVE_MADE)){
                for (int i = 0; i<6; i++){
                    System.out.println(scanner.nextLine());
                }
                System.out.println("A move has been made in column " + line.split(" ")[1]);
            }else if (line.equals(GAME_WON)){
                System.out.println("You Win! Yay!");
            }else if(line.equals(GAME_LOST)){
                System.out.println("You Lost! Boo!");
            }else if(line.equals(GAME_TIED)){
                System.out.println("You Tied!");
            }else if(line.equals(ERROR)){
                System.out.println("An Error Has Occurred.");
            }
        }
    }

    /**
     * Starts a new {@link ConnectFourClient}.
     *
     * @param args Used to specify the hostname and port of the Connect Four
     *             server through which the client will play.
     * @throws ConnectFourException If there is a problem creating the client
     *                              or connecting to the server.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println(
                    "Usage: java ConnectFourClient hostname port");
            System.exit(1);
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ConnectFourClient cfc = new ConnectFourClient(hostname, port);
        cfc.respond();

    }
}
