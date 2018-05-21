package sample;

import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CreateAccountController {

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    Button createButton;

    @FXML
    ImageView createImageView;

    @FXML
    ProgressBar progressBar;

    @FXML
    Text basicsText;

    @FXML
    Text documentsText;

    @FXML
    Text policyText;

    @FXML
    Text activationText;

    @FXML
    Text generatedAccountNumberText;

    @FXML
    TextField nameTextField;
    @FXML
    Line nameLine;

    @FXML
    TextField locationTextField;

    @FXML
    Line locationLine;

    @FXML
    TextField contactNumberTextField;

    //;)
    @FXML
    Line contactNumberLine;
    @FXML
    TextField contactEmailTextField;

    @FXML
    Line contactEmailLine;

    @FXML
    TextField taxPinTextField;

    @FXML
    Line taxPinLine;

    @FXML
    TextField depositTextField;

    @FXML
    Line depositLine;

    @FXML
    Button backButton;

    @FXML
    ImageView backImageView;

    @FXML
    Button nextButton0;

    @FXML
    Pane contactPane;
    @FXML
    Pane documentsPane;

    @FXML
    Pane policyPane;

    @FXML
    Pane activationPane;

    @FXML
    CheckBox checkingCheckBox;

    @FXML
    CheckBox savingCheckBox;


    @FXML
    public String handleAccountType() {
        String accountType = "Checking";
        if (checkingCheckBox.isSelected())
            savingCheckBox.setSelected(false);
        else if (savingCheckBox.isSelected()) {
            checkingCheckBox.setSelected(false);
            accountType = "Saving";
        }
        return accountType;
    }

    @FXML
    public void handleNextButton0() {
        progressBar.setProgress(0.25);
        contactPane.setOpacity(1.0);
        documentsPane.setOpacity(0.7);
        policyPane.setOpacity(0.5);
        activationPane.setOpacity(0.2);
    }

    @FXML
    public void handleNextButton1() {
        progressBar.setProgress(0.5);
        documentsPane.setOpacity(1);
        policyPane.setOpacity(0.7);
        activationPane.setOpacity(0.5);
    }

    @FXML
    public void handleNextButton2() {
        progressBar.setProgress(0.75);
        policyPane.setOpacity(1);
        activationPane.setOpacity(0.7);
    }

    @FXML
    public void handleNextButton3() {
        progressBar.setProgress(1);
        activationPane.setOpacity(1);
    }

    @FXML
    public void handleBackButtonMouseEntered() {
        backImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_arrow_back_black_48dp.png"));
    }

    @FXML
    public void handleBackButtonMouseExited() {
        backImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_keyboard_arrow_left_blue_A700_48dp.png"));
    }

    @FXML
    public void handleBackButton() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("services.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        pane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            primaryStage.setY(event.getScreenY() - yOffset);
            primaryStage.setX(event.getScreenX() - xOffset);
        });
        primaryStage.setScene(new Scene(pane, 1200, 465));
        primaryStage.show();
    }

    @FXML
    public String handleName() {
        nameLine.setStrokeWidth(3);
        return nameTextField.getText();
    }

    @FXML
    public String handleLocation() {
        locationLine.setStrokeWidth(3);
        return locationTextField.getText();
    }

    @FXML
    public String handleContactNumber() {
        contactNumberLine.setStrokeWidth(3);
        return contactNumberTextField.getText();
    }

    @FXML
    public String handleContactEmail() {
        contactEmailLine.setStrokeWidth(3);
        boolean valid = EmailValidator.getInstance().isValid(contactEmailTextField.getText());
        if (valid)
            return contactEmailTextField.getText();
        else {
            contactEmailLine.setStyle("-fx-stroke: #F50057;");
            contactEmailTextField.clear();
        }
        return "nomail@mail.com";
    }

    @FXML
    public String handleTaxPin() {
        taxPinLine.setStrokeWidth(3);
        return taxPinTextField.getText();
    }

    @FXML
    public double handleDeposit() {
        depositLine.setStrokeWidth(3);
        if(!depositTextField.isFocused())
            return Double.parseDouble(depositTextField.getText());
        else
            return 1000.00;
    }

    @FXML
    public void handleCreateButton() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);


            // PreparedStatements can use variables and are more efficient
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "insert into TheBank.Clients(accountNumber,accountType,name,location, contactNumber, contactEmail, taxpin,balance )" +
                            " values ( ?, ?,?,?,?,?,?,?)");
            preparedStatement.setString(1, String.valueOf(generateAccountNumber()));
            preparedStatement.setString(2, handleAccountType());
            preparedStatement.setString(3, handleName());
            preparedStatement.setString(4, handleLocation());
            preparedStatement.setString(5, handleContactNumber());
            preparedStatement.setString(6, handleContactEmail());
            preparedStatement.setString(7, handleTaxPin());
            preparedStatement.setString(8, String.valueOf(handleDeposit()));
            preparedStatement.executeUpdate();

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCreateButtonEntered() {
        createImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_add_circle_blue_A200_48dp.png"));
    }

    @FXML
    public void handleCreateButtonExited() {
        createImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_add_circle_blue_A700_48dp.png"));
    }

    private Long generateAccountNumber() {
        Random random = new Random();
        Long accountNumber = Math.abs(random.nextLong());
        generatedAccountNumberText.setText(String.valueOf(accountNumber));
        return accountNumber;
    }

}
