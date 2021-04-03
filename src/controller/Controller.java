package controller;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import model.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField keyField;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button encryptBtn;

    @FXML
    private Button decryptBtn;

    @FXML
    void initialize() {
        assert keyField != null : "fx:id=\"keyField\" was not injected: check your FXML file 'main.fxml'.";
        assert resultArea != null : "fx:id=\"resultArea\" was not injected: check your FXML file 'main.fxml'.";
        assert encryptBtn != null : "fx:id=\"encryptBtn\" was not injected: check your FXML file 'main.fxml'.";
        assert decryptBtn != null : "fx:id=\"decryptBtn\" was not injected: check your FXML file 'main.fxml'.";

    }

    @FXML
    private void handleEncrypt() {

        if (!isKeyCorrect())
            return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose input file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(/*"TXT files (*.txt)", "*.txt"*/"All Files", "*.*");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File inputFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (inputFile == null) {
            return;
        }
        FileInput fileInput = new FileInput(inputFile.getPath());
        byte[] inputMessage = fileInput.readBytes();

        LFSRCipher cipher = new LFSRCipher(Long.parseLong(keyField.getText(), 2),
                inputMessage);
        FileOutput fileOutput = new FileOutput("output_encrypted");
        fileOutput.write(cipher.encrypt());
        resultArea.setText(cipher.getKeyBits().toString() + "\n" + cipher.getEncryptedBits().toString()
                + "\n" + cipher.getOriginalBits().toString());
    }

    @FXML
    private void handleDecrypt() {

        if (!isKeyCorrect())
            return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose input file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(/*"TXT files (*.txt)", "*.txt"*/"All Files", "*.*");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File inputFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (inputFile == null) {
            return;
        }
        FileInput fileInput = new FileInput(inputFile.getPath());
        byte[] inputMessage = fileInput.readBytes();

        LFSRCipher cipher = new LFSRCipher(Long.parseLong(keyField.getText(), 2),
                inputMessage);
        FileOutput fileOutput = new FileOutput("output_decrypted");
        fileOutput.write(cipher.decrypt());
        resultArea.setText(cipher.getKeyBits().toString() + "\n" + cipher.getEncryptedBits().toString()
                + "\n" + cipher.getOriginalBits().toString());
    }

    private boolean isKeyCorrect() {
        if (keyField.getText().length() < 1 || keyField.getText().length() > 37) {
            showAlert("Неверная длина ключа");
            return false;
        } else return true;
    }

    private void showAlert(String message) {
        Alert isEmpty = new Alert(Alert.AlertType.INFORMATION);
        isEmpty.setHeaderText(message);
        isEmpty.setContentText("Проверьте введенные данные.");

        isEmpty.showAndWait();
    }

}
