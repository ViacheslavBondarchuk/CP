package com.org.house.Controllers;

import com.org.house.WorkDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControllerTabThree {

    private WorkDB workDB;

    public ControllerTabThree() {
        workDB = new WorkDB();
    }

    @FXML
    private AnchorPane AnchPainConnect;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField txtFieldURL;

    @FXML
    private TextField txtFieldUser;

    @FXML
    private PasswordField txtFieldPassword;

    @FXML
    private TextField txtFieldBD;

    @FXML
    private void initialize() {
        btnConnect.setOnAction(e -> {
            try {
                connect();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerTabThree.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void connect() throws SQLException {
        String url = null, db = null, user = null, password = null;
        url = txtFieldURL.getText();
        db = txtFieldBD.getText();
        user = txtFieldUser.getText();
        password = txtFieldPassword.getText();

        workDB.Connect(url, db, user, password);
    }
}
