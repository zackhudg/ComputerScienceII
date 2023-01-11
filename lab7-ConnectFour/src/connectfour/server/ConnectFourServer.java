package connectfour.server;

import connectfour.ConnectFour;
import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The server waits for incoming client connections and pairs them off
 * to play the game.
 *
 * @author Zack Hudgins
 */
public class ConnectFourServer implements ConnectFourProtocol {

    public ServerSocket server;
    public Socket client1;
    public Socket client2;
    public PrintStream printer1;
    public PrintStream printer2;
    public InputStream input1;
    public InputStream input2;
    public OutputStream output1;
    public OutputStream output2;
    public Scanner scanner1;
    public Scanner scanner2;
    public ConnectFour cf;
    public boolean flag;

    /**
     * Constructor for ConnectFourServer. Initializes I/O and connects to clients, creates game
     * @param port the port server is hosted on
     * @throws IOException
     */
    public ConnectFourServer(int port) throws IOException{

        this.server = new ServerSocket(port);

        this.client1 = server.accept();
        this.input1 = client1.getInputStream();
        this.output1 = client1.getOutputStream();
        this.printer1 = new PrintStream(output1);
        this.scanner1 = new Scanner(input1);
        this.printer1.println(CONNECT);

        this.client2 = server.accept();
        this.input2 = client2.getInputStream();
        this.output2 = client2.getOutputStream();
        this.scanner2 = new Scanner(input2);
        this.printer2 = new PrintStream(output2);
        this.printer2.println(CONNECT);

        this.cf = new ConnectFour();

        this.flag = true;

        System.out.println("Players have connected");

    }
    /**
     * runs the game in a sequence of turns alternating between players 1 and 2
     * @param active player number (1 or 2)
     * @throws ConnectFourException
     */
    public void turn(int active) throws ConnectFourException {
        System.out.println("Player "+active+"'s turn");

        Scanner scanner;
        PrintStream printer1;
        PrintStream printer2;
        if (active == 1){
            scanner = this.scanner1;
            printer1 = this.printer1;
            printer2 = this.printer2;
        }else{
            scanner = this.scanner2;
            printer1 = this.printer2;
            printer2 = this.printer1;
        }

        printer1.println(MAKE_MOVE);
        System.out.println(MAKE_MOVE + "to Player" + active);

        try {
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            if (split[0].equals(MOVE)) {
                if (0 <= Integer.parseInt(split[1]) && Integer.parseInt(split[1]) <= 6) {
                    this.cf.makeMove(Integer.parseInt(split[1]));

                    printer1.println(MOVE_MADE + " " + split[1]);
                    printer1.println(this.cf.toString());
                    System.out.println(MOVE_MADE + " to Player1");
                    printer2.println(MOVE_MADE + " " + split[1]);
                    printer2.println(this.cf.toString());
                    System.out.println(MOVE_MADE + " to Player2");

                    if (this.cf.hasWonGame_old()) {

                        printer1.println(GAME_WON);
                        printer2.println(GAME_LOST);

                        System.out.println(GAME_WON + " to Player" + active);
                        int loser = 1;
                        if (active == 1) {
                            loser = 2;
                        }
                        System.out.println(GAME_LOST + " to Player" + loser);

                        this.flag = false;

                    } else if (this.cf.hasTiedGame()) {

                        printer1.println(GAME_TIED);
                        printer2.println(GAME_TIED);
                        System.out.println(GAME_TIED + "to Player1");
                        System.out.println(GAME_TIED + "to Player2");
                        this.flag = false;

                    }
                } else {
                    error();
                }
            }
        }catch(NoSuchElementException e) {
            error();
        }

    }

    /**
     * called if an error occurrs
     */
    public void error(){
        printer1.println(ERROR);
        printer2.println(ERROR);
        System.out.println(ERROR + "to Player1");
        System.out.println(ERROR + "to Player2");
        this.flag = false;
    }

    /**
     * Starts a new sever.
     *
     * @param args Used to specify the port on which the server should listen
     *             for incoming client connections.
     *
     * @throws ConnectFourException If there is an error starting the server.
     */
    public static void main(String[] args) throws ConnectFourException, IOException {

        if (args.length != 1) {
            System.out.println("Usage: java ConnectFourServer port");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        ConnectFourServer cfs = new ConnectFourServer(port);

        while (cfs.flag){
            cfs.turn(1);
            if(cfs.flag) {
                cfs.turn(2);
            }
        }
    }

}