package com.org.house.Controllers;

import com.org.house.Cipher;
import com.org.house.DialogMessage;
import com.org.house.WorkDB;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerTabTwo {

    private Cipher cipher = new Cipher();
    private WorkDB workDB = new WorkDB();
    private DialogMessage mg = new DialogMessage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtFieldName;

    @FXML
    private TextField txtFieldCity;

    @FXML
    private TextField txtFieldPassword;

    @FXML
    private TextField txtFieldLogin;

    @FXML
    private TextField txtFieldFilePath;

    @FXML
    private TextField txtFieldNote;

    @FXML
    private TextField txtFieldFileName;

    @FXML
    private Button btnAdd;

    @FXML
    void initialize() {

        btnAdd.setOnAction(e -> {
            try {
                getDate();
                mg.notification();
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

    }

    private void getDate() throws NoSuchAlgorithmException, SQLException {
        String name = txtFieldFileName.getText();
        String city = txtFieldCity.getText();
        String login = txtFieldLogin.getText();
        String loginHash = cipher.cipherLogin(login);
        String password = txtFieldPassword.getText();
        String passwordHash = cipher.cipherPassword(password);
        String filePath = txtFieldFilePath.getText();
        String fileName = txtFieldFileName.getText();
        String note = txtFieldNote.getText();

        workDB.writeInDBfromExcel(name, city, login, loginHash, password, passwordHash, filePath, fileName, note);
    }

}
