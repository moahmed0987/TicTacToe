package tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIController implements Initializable {

    private boolean playerOneTurn;
    private int playerOneScore = 0, playerTwoScore = 0;

    @FXML
    private GridPane board;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private void squareClicked(ActionEvent e) {
        if (playerOneTurn && ((Button) e.getSource()).getText().equals("")) {
            ((Button) e.getSource()).setText("X");
            if (checkForWin("X")) {
                showWin("X");
            }
            playerOneTurn = !playerOneTurn;
            setPlayerLabel();
        } else if (!playerOneTurn && ((Button) e.getSource()).getText().equals("")) {
            ((Button) e.getSource()).setText("O");
            if (checkForWin("O")) {
                showWin("O");
            }
            playerOneTurn = !playerOneTurn;
            setPlayerLabel();
        }
    }
    
    private void showWin(String playerLetter){
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("GAME OVER");
        window.setMinWidth(250);
        
        Label messageLabel = new Label(playerLetter + " wins!");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e-> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private boolean checkForWin(String playerLetter) {
        String[] boardArray = new String[9];
        for (int pos = 0; pos < 9; pos++) {
            if (board.getChildren().get(pos) instanceof Button) {
                boardArray[pos] = ((Button) board.getChildren().get(pos)).getText();
            }
        }
        return ((boardArray[6].equals(playerLetter) && boardArray[7].equals(playerLetter) && boardArray[8].equals(playerLetter)) || // across the top
                (boardArray[3].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[5].equals(playerLetter)) || // across the middle
                (boardArray[0].equals(playerLetter) && boardArray[1].equals(playerLetter) && boardArray[2].equals(playerLetter)) || // across the bottom
                (boardArray[6].equals(playerLetter) && boardArray[3].equals(playerLetter) && boardArray[0].equals(playerLetter)) || // down the left side
                (boardArray[7].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[1].equals(playerLetter)) || // down the middle
                (boardArray[8].equals(playerLetter) && boardArray[5].equals(playerLetter) && boardArray[2].equals(playerLetter)) || // down the right side
                (boardArray[6].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[2].equals(playerLetter)) || // diagonal
                (boardArray[8].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[0].equals(playerLetter))); // diagonal

    }

    private void setPlayerLabel() {
        if (playerOneTurn) {
            currentPlayerLabel.setText("Current Player: Player One");
        } else {
            currentPlayerLabel.setText("Current Player: Player Two");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerOneTurn = true;
        setPlayerLabel();
    }

}
