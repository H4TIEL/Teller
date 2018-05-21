package sample;

import com.google.common.hash.Hashing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.sql.*;


public class LoginController {


    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    ImageView usernameImage;
    @FXML
    ImageView passwordImage;

    @FXML
    Button loginButton;

    @FXML
    Button exitButton;

    @FXML
    Pane loginPane;


    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Pane forgotPasswordPane;

    @FXML
    Button forgotPassword;

    @FXML
    public void handleForgotPassword() {
        forgotPasswordPane.setVisible(true);
    }

    @FXML
    public void usernameClicked() {
        usernameImage.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_account_circle_blue_A700_48dp.png"));
    }

    @FXML
    public void passwordClicked() {
        passwordImage.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_vpn_key_blue_A700_48dp.png"));
    }

    @FXML
    public void handleLogin() throws IOException {


        String mUsername = usernameTextField.getText();
        String mPassword = passwordTextField.getText();

        if (confirmLoginDetails(mUsername, mPassword)) {

            Pane pane = FXMLLoader.load(getClass().getResource("services.fxml"));
            Stage primaryStage = (Stage) loginPane.getScene().getWindow();

            pane.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            pane.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });
            primaryStage.setScene(new Scene(pane, 1200, 460));
            primaryStage.show();
        } else {
            usernameTextField.setStyle(" -fx-border-width: 3;" + "-fx-border-color: #FF1744");
            usernameImage.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_account_circle_red_A400_48dp.png"));
            usernameTextField.setPromptText("Invalid");
            passwordTextField.setStyle(" -fx-border-width: 3;" + "-fx-border-color: #FF1744");
            passwordImage.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_vpn_key_red_A400_48dp.png"));
            passwordTextField.setPromptText("Invalid");
        }
    }

    @FXML
    public void handleExit() {
        Stage primaryStage = (Stage) exitButton.getScene().getWindow();
        exitButton.setOnAction(actionEvent -> primaryStage.close());
    }


    private static String generateHash(String salt, String password) {
        return Hashing.sha256()
                .hashString(salt.concat(password), StandardCharsets.UTF_8)
                .toString();
    }

    /**
     * @return login true or false
     */
    private static boolean confirmLoginDetails(String username, String password) {
        boolean login = false;
        String salt = "";
        String hash = "";
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);

            PreparedStatement preparedStatement = connect.prepareStatement("SELECT hash, salt from TheBank.TellerLogin WHERE username=(?);");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hash = resultSet.getString("hash");
                salt = resultSet.getString("salt");
            }

            if (hash.contentEquals(generateHash(salt, password)))
                login = true;

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return login;
    }


}

