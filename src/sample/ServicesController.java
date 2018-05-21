package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServicesController {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Button createButton;

    @FXML
    Button accessButton;

    @FXML
    Button chatButton;

    @FXML
    Button lockButton;

    @FXML
    ImageView chatImageView;

    @FXML
    ImageView lockImageView;

    @FXML
    Button cryptoButton;

    @FXML
    Button exchangeButton;


    @FXML
    public void handleCreate() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
        Stage primaryStage = (Stage) createButton.getScene().getWindow();
        pane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setScene(new Scene(pane, 1100, 460));
        primaryStage.show();

    }

     @FXML
     public void  handleAccessButton() throws IOException {
         Pane pane = FXMLLoader.load(getClass().getResource("accessAccount.fxml"));
         Stage primaryStage = (Stage) accessButton.getScene().getWindow();
         pane.setOnMousePressed(event -> {
             xOffset = event.getSceneX();
             yOffset = event.getSceneY();
         });

         pane.setOnMouseDragged(event -> {
             primaryStage.setX(event.getScreenX() - xOffset);
             primaryStage.setY(event.getScreenY() - yOffset);
         });
         primaryStage.setScene(new Scene(pane, 1200, 600));
         primaryStage.show();

     }

    @FXML
    public void  handleExchangeButton() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("exchange.fxml"));
        Stage primaryStage = (Stage) exchangeButton.getScene().getWindow();
        pane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setScene(new Scene(pane, 1200, 500));
        primaryStage.show();
    }

    @FXML
    public void  handleCryptoButton() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("crypto.fxml"));
        Stage primaryStage = (Stage) cryptoButton.getScene().getWindow();
        pane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setScene(new Scene(pane, 1200, 400));
        primaryStage.show();
    }


        @FXML
    public void handleChatButtonEntered() {
        chatImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_chat_bubble_blue_A700_48dp.png"));
    }

    @FXML
    public void handleChatButtonExited() {
        chatImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_chat_bubble_outline_blue_A700_48dp.png"));
    }

    @FXML
    public void handleLockButtonClicked() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage primaryStage = (Stage) lockButton.getScene().getWindow();
        primaryStage.setScene(new Scene(pane, 1100, 460));
        primaryStage.show();
    }

    @FXML
    public void handleLockButtonEntered() {
        lockImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_lock_blue_A700_48dp.png"));
    }

    @FXML
    public void handleLockButtonExited() {
        lockImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_lock_outline_blue_A700_48dp.png"));
    }

}
