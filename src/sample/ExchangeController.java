package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class ExchangeController {

    private double xOffset = 0;
    private double yOffset = 0;
    private double convertRate = 0;


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
    Text kenyaShillingText;

    @FXML
    Text poundText;

    @FXML
    Text euroText;

    @FXML
    Text yuanText;

    @FXML
    Text swissFrancText;

    @FXML
    Text canadianDollarText;

    @FXML
    Button kenyaExchangeButton;

    @FXML
    Button poundExchangeButton;

    @FXML
    Button euroExchangeButton;

    @FXML
    Button yuanExchangeButton;

    @FXML
    Button swissFrancExchangeButton;

    @FXML
    Button canadianDollarExchangeButton;

    @FXML
    TextField exchangeAmountTextField;

    @FXML
    Text currencyText;

    @FXML
    Text amountConvert;

    @FXML
    Text amountFee;

    @FXML
    Text amountExchange;


    @FXML
    private void handleCalculateExchange() {
        double amount = 0;
        if (exchangeAmountTextField != null) {
            amount = Double.parseDouble(exchangeAmountTextField.getText());
        }
        amountConvert.setText(exchangeAmountTextField.getText());

        double exchange = amount * convertRate;
        amountExchange.setText(String.valueOf(exchange));

        double fee = exchange * 0.5;
        amountFee.setText(String.valueOf(fee));
    }

    @FXML
    public void handleKenyaExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[0];
        currencyText.setText("KES");
    }

    @FXML
    public void handlePoundExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[1];
        currencyText.setText("GBP");
    }

    @FXML
    public void handleEuroExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[2];
        currencyText.setText("EUR");
    }


    @FXML
    public void handleYuanExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[3];
        currencyText.setText("CNY");
    }

    @FXML
    public void handleSwissFrancExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[4];
        currencyText.setText("CHF");

    }

    @FXML
    public void handleCanadianDollarExchange() {
        double[] currenciesRatesArray = getRate(request());
        convertRate = currenciesRatesArray[5];
        currencyText.setText("CAD");
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
        primaryStage.setScene(new Scene(pane, 1200, 470));
        primaryStage.show();
    }


    @FXML
    public void handleChatButtonMouseEntered() {
        chatImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_chat_bubble_blue_A700_48dp.png"));
    }

    @FXML
    public void handleChatButtonMouseExited() {
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
    public void handleLockButtonMouseEntered() {
        lockImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_lock_blue_A700_48dp.png"));
    }

    @FXML
    public void handleLockButtonMouseExited() {
        lockImageView.setImage(new Image("sample/res/icons/drawable-xxxhdpi/ic_lock_outline_blue_A700_48dp.png"));
    }


    @FXML
    public void handleSyncRates() {
        double[] currenciesRatesArray = getRate(request());
        kenyaShillingText.setText(String.valueOf(currenciesRatesArray[0]));
        poundText.setText(String.valueOf(currenciesRatesArray[1]));
        euroText.setText(String.valueOf(currenciesRatesArray[2]));
        yuanText.setText(String.valueOf(currenciesRatesArray[3]));
        swissFrancText.setText(String.valueOf(currenciesRatesArray[4]));
        canadianDollarText.setText(String.valueOf(currenciesRatesArray[5]));
    }

    //Get excahnge rate data
    private static String request() {
        String coinJSON = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://openexchangerates.org/api/latest.json?app_id=dd7ef3115d2447dc8b5eb49eec3838b1")
                .build();

        try {
            Response response = client.newCall(request).execute();
            coinJSON = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coinJSON;

    }

    /**
     * @param response currency rate from api
     * @return array[KES, GBP, EUR, CNY, CHF,CAD]
     */
    private static double[] getRate(String response) {
        double[] currenciesArray = new double[6];

        JSONObject obj = new JSONObject(response);
        JSONObject rates = obj.getJSONObject("rates");
        currenciesArray[0] = rates.getDouble("KES");
        currenciesArray[1] = rates.getDouble("GBP");
        currenciesArray[2] = rates.getDouble("EUR");
        currenciesArray[3] = rates.getDouble("CNY");
        currenciesArray[4] = rates.getDouble("CHF");
        currenciesArray[5] = rates.getDouble("CAD");
        return currenciesArray;

    }

}



