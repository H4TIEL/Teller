package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.DoubleValidator;

import java.io.IOException;
import java.sql.*;

public class AccessAccountController {


    private double xOffset = 0;
    private double yOffset = 0;
    private int countGraph = 0;
    @FXML
    Button backButton;

    @FXML
    ImageView backImageView;

    @FXML
    Button chatButton;

    @FXML
    Button lockButton;

    @FXML
    ImageView chatImageView;

    @FXML
    ImageView lockImageView;


    @FXML
    BarChart<String, Number> barChart;

    @FXML
    Text accountName;

    @FXML
    Text creditAvailableText;
    @FXML
    Text availableCashText;
    @FXML
    Text outstandingText;
    @FXML
    Text bonusText;


    @FXML
    TextField accountNumberTextField;

    @FXML
    TextField amountTextField;


    @FXML
    public void handleSyncAccount() {
        countGraph++;
        if (countGraph == 1)
            handleGraph();
        handleAccountNumber();
        handleAccountCard();
    }

    @FXML
    public void handleAccountNumber() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);

            PreparedStatement preparedStatement = connect.prepareStatement
                    ("SELECT * from TheBank.Clients WHERE accountNumber=(?)");
            preparedStatement.setLong(1, Long.parseLong(accountNumberTextField.getText()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountName.setText(resultSet.getString("name"));
                availableCashText.setText(resultSet.getString("balance"));
            }

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAccountCard() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);

            PreparedStatement preparedStatement = connect.prepareStatement
                    ("SELECT * from TheBank.Card WHERE accountNumber=(?)");
            preparedStatement.setLong(1, Long.parseLong(accountNumberTextField.getText()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                creditAvailableText.setText(resultSet.getString("creditAvailable"));
                bonusText.setText(resultSet.getString("bonus"));
                outstandingText.setText(resultSet.getString("outstanding"));
            }

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleWithdraw() {
        try {

            Double withdraw = Double.valueOf(amountTextField.getText());
            Double balance = Double.valueOf(availableCashText.getText());
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            if (withdraw <= balance) {
                PreparedStatement preparedStatement = connect.prepareStatement("UPDATE TheBank.Clients SET balance = (?) WHERE accountNumber= (?);");
                preparedStatement.setDouble(1, balance - withdraw);
                preparedStatement.setLong(2, Long.parseLong(accountNumberTextField.getText()));
                preparedStatement.executeUpdate();
            }
            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeposit() {
        try {
            Double deposit = Double.valueOf(amountTextField.getText());
            Double balance = Double.valueOf(availableCashText.getText());
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);

            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE TheBank.Clients SET balance = (?) WHERE accountNumber= (?);");
            preparedStatement.setDouble(1, deposit + balance);
            preparedStatement.setLong(2, Long.parseLong(accountNumberTextField.getText()));
            preparedStatement.executeUpdate();

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    private void handleGraph() {
        //Debit
        XYChart.Series<String, Number> debit = new XYChart.Series<>();
        debit.setName("2017");
        debit.getData().add(new XYChart.Data<>("January", -400));
        debit.getData().add(new XYChart.Data<>("February", -600));
        debit.getData().add(new XYChart.Data<>("March", -1000));
        debit.getData().add(new XYChart.Data<>("April", -1500));
        debit.getData().add(new XYChart.Data<>("May", -800));
        debit.getData().add(new XYChart.Data<>("June", -600));
        debit.getData().add(new XYChart.Data<>("July", -500));
        debit.getData().add(new XYChart.Data<>("August", -1800));
        debit.getData().add(new XYChart.Data<>("September", -1800));
        debit.getData().add(new XYChart.Data<>("October", -1500));
        debit.getData().add(new XYChart.Data<>("November", -1400));
        debit.getData().add(new XYChart.Data<>("December", -1300));

        //Credit
        XYChart.Series<String, Number> credit = new XYChart.Series<>();
        credit.setName("2018");
        credit.getData().add(new XYChart.Data<>("January", 2000));
        credit.getData().add(new XYChart.Data<>("February", 1700));
        credit.getData().add(new XYChart.Data<>("March", 1500));
        credit.getData().add(new XYChart.Data<>("April", 1300));
        credit.getData().add(new XYChart.Data<>("May", 1200));
        credit.getData().add(new XYChart.Data<>("June", 1300));
        credit.getData().add(new XYChart.Data<>("July", 1400));
        credit.getData().add(new XYChart.Data<>("August", 1080));
        credit.getData().add(new XYChart.Data<>("September", 2050));
        credit.getData().add(new XYChart.Data<>("October", 2025));
        credit.getData().add(new XYChart.Data<>("November", 2010));
        credit.getData().add(new XYChart.Data<>("December", 2005));

        barChart.setLegendVisible(false);
        barChart.getData().addAll(credit, debit);

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
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setScene(new Scene(pane, 1200, 465));
        primaryStage.show();
    }

    @FXML
    public void handleDelete() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String databaseUrl = "jdbc:mysql://localhost/coin";
            String databaseUser = "root";
            String databasePassword = "";
            Connection connect = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);

            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM TheBank.Clients WHERE accountNumber= (?);");
            preparedStatement.setLong(1, Long.parseLong(accountNumberTextField.getText()));
            preparedStatement.executeUpdate();

            connect.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

