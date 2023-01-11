package connectfour.gui;

import connectfour.ConnectFourException;
import connectfour.client.ConnectFourBoard;
import connectfour.client.ConnectFourNetworkClient;
import connectfour.client.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * A JavaFX GUI for the networked Connect Four game.
 *
 * @author James Heloitis @ RIT CS
 * @author Sean Strout @ RIT CS
 * @author Zack Hudgins
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {
    /**
     * columns: width of
     */

    private final int columns = 7;
    private final int rows = 6;

    ConnectFourBoard board;
    ConnectFourNetworkClient serverConn;

    Text text;
    GridPane grid;

    @Override
    public void init() {
        try {
            List<String> args = getParameters().getRaw();

            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            this.board = new ConnectFourBoard();

            this.board.addObserver(this);

            this.serverConn = new ConnectFourNetworkClient(host, port, this.board);
        } catch( ConnectFourException |
                ArrayIndexOutOfBoundsException |
                NumberFormatException e ) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start( Stage stage ) {
        Group root = new Group();
        Scene scene = new Scene(root, 650, 448);

        BorderPane border = new BorderPane();

        grid = new GridPane();
        for (int i = 0; i<columns; i++){
            for (int j = 0; j<rows; j++){
                Image e = new Image(getClass().getResourceAsStream( "empty.png" ));
                ImageView empty = new ImageView(e);
                grid.add(empty, i, j);
            }
            Button button = new Button("Drop\nPiece\nHere");
            button.setMaxWidth(64);
            final int column = i;
            button.setOnAction(e -> {if(board.isValidMove(column) && this.board.isMyTurn()) {serverConn.sendMove(column);}});
            grid.add(button, i, rows);
        }
        border.setCenter(grid);

        text = new Text("Waiting for game to start...");
        border.setRight(text);

        root.getChildren().add(border);
        stage.setScene(scene);
        stage.show();

        this.serverConn.startListener();
    }

    /**
     * GUI is closing, so close the network connection. Server will get the message.
     */
    @Override
    public void stop() {
        this.serverConn.close();
    }

    /**
     * Do your GUI updates here.
     */
    private void refresh() {
        if (!this.board.isMyTurn()) {
            /**update board*/
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    if (this.board.getContents(i, j) == ConnectFourBoard.Move.PLAYER_ONE) {
                        Image e = new Image(getClass().getResourceAsStream("p1black.png"));
                        ImageView empty = new ImageView(e);
                        grid.add(empty, i, j);
                    }else if (this.board.getContents(i, j) == ConnectFourBoard.Move.PLAYER_TWO) {
                        Image e = new Image(getClass().getResourceAsStream("p2red.png"));
                        ImageView empty = new ImageView(e);
                        grid.add(empty, i, j);
                    }
                }

                ConnectFourBoard.Status status = this.board.getStatus();
                switch (status) {
                    case ERROR:
                        this.text.setText(status.toString());
                        break;
                    case I_WON:
                        this.text.setText("You won. Yay!");
                        break;
                    case I_LOST:
                        this.text.setText("You lost. Boo!");
                        break;
                    case TIE:
                        this.text.setText("Tie game. Meh.");
                        break;
                    default:
                        this.text.setText("Opponent's Turn");
                }
            }
        }else{
            text.setText("Your turn!");
        }
    }

    /**
     * Called by the model, client.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param connectFourBoard
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {
        if ( Platform.isFxApplicationThread() ) {
            this.refresh();
        }
        else {
            Platform.runLater( () -> this.refresh() );
        }
    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConnectFourGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
