package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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


public class CryptoController {

    private double xOffset = 0;
    private double yOffset = 0;


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
    Text bitcoinRateText;

    @FXML
    Text ethereumRateText;

    @FXML
    Text moneroRateText;


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
        primaryStage.setScene(new Scene(pane, 1200, 460));
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
        bitcoinRateText.setText(String.valueOf(getRate(requestRate("BTC"))));
        ethereumRateText.setText(String.valueOf(getRate(requestRate("ETH"))));
        moneroRateText.setText(String.valueOf(getRate(requestRate("XMR"))));

    }

    /**
     * Request rate coin api
     * API key emailed
     * 100 Daily request
     *
     * @param symbol cryptocurrency ticker symbol
     * @return JSON string
     */
    private static String requestRate(String symbol) {
        String coinJSON = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rest.coinapi.io/v1/exchangerate/" + symbol + "/USD")
                .addHeader("X-CoinAPI-Key", "API-KEY:)")
                .build();

        try {
            Response response = client.newCall(request).execute();
            coinJSON = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coinJSON;

    }

    //Parse JSON for rate
    private static int getRate(String response) {
        JSONObject obj = new JSONObject(response);
        return obj.getInt("rate");
    }

}
