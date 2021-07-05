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

    private boolean playerXTurn;
    private int playerXScore = 0, playerOScore = 0;

    @FXML
    private GridPane board;

    @FXML
    private Label currentPlayerLabel, xScoreLabel, oScoreLabel;

    @FXML
    private void squareClicked(ActionEvent e) {
        if (playerXTurn && ((Button) e.getSource()).getText().equals("")) {
            ((Button) e.getSource()).setText("X");
            if (checkForWin("X")) {
                playerXScore++;
                xScoreLabel.setText("X score: " + String.valueOf(playerXScore));
                showMessage("X wins!", e);
            }
            playerXTurn = !playerXTurn;
            setPlayerLabel();
        } else if (!playerXTurn && ((Button) e.getSource()).getText().equals("")) {
            ((Button) e.getSource()).setText("O");
            if (checkForWin("O")) {
                playerOScore++;
                oScoreLabel.setText("X score: " + String.valueOf(playerOScore));
                showMessage("O wins!", e);
            }
            playerXTurn = !playerXTurn;
            setPlayerLabel();
        }
        if (!checkForWin("X") && !checkForWin("O")) {
            if (checkForDraw()) {
                showMessage("It's a draw!", e);
            }
        }
    }

    private void showMessage(String message, ActionEvent e) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("GAME OVER");
        window.setMinWidth(250);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill:white;");

        Button playAgainButton = new Button("Play again");
        playAgainButton.setStyle("-fx-background-color:black;-fx-text-fill:white;-fx-border-color:white;");

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color:black;-fx-text-fill:white;-fx-border-color:white;");

        playAgainButton.setOnAction(ev -> {
            restartGame();
            window.close();
        });
        exitButton.setOnAction(ev -> {
            window.close();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, playAgainButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color:black;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void restartGame() {
        for (Node n : board.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setText("");
            }
        }
    }

    private boolean checkForWin(String playerLetter) {
        String[] boardArray = new String[9];
        for (int pos = 0; pos < 9; pos++) {
            if (board.getChildren().get(pos) instanceof Button) {
                boardArray[pos] = ((Button) board.getChildren().get(pos)).getText();
            }
        }
        return ((boardArray[6].equals(playerLetter) && boardArray[7].equals(playerLetter) && boardArray[8].equals(playerLetter)) // top
                || (boardArray[3].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[5].equals(playerLetter)) // middle
                || (boardArray[0].equals(playerLetter) && boardArray[1].equals(playerLetter) && boardArray[2].equals(playerLetter)) // bottom
                || (boardArray[6].equals(playerLetter) && boardArray[3].equals(playerLetter) && boardArray[0].equals(playerLetter)) // left side
                || (boardArray[7].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[1].equals(playerLetter)) // center
                || (boardArray[8].equals(playerLetter) && boardArray[5].equals(playerLetter) && boardArray[2].equals(playerLetter)) // right side
                || (boardArray[6].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[2].equals(playerLetter)) // diagonal
                || (boardArray[8].equals(playerLetter) && boardArray[4].equals(playerLetter) && boardArray[0].equals(playerLetter)) // diagonal
                );

    }

    private boolean checkForDraw() {
        boolean draw = true;
        for (Node node : board.getChildren()) {
            if (node instanceof Button) {
                if (((Button) node).getText().equals("")) {
                    draw = false;
                }
            }
        }
        return draw;
    }

    private void setPlayerLabel() {
        if (playerXTurn) {
            currentPlayerLabel.setText("Current Player: X");
        } else {
            currentPlayerLabel.setText("Current Player: O");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerXTurn = true;
        setPlayerLabel();
        xScoreLabel.setText("X score: 0");
        oScoreLabel.setText("X score: 0");
    }

}
