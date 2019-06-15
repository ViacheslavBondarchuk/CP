package com.org.house.Controllers;

import com.org.house.WorkDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerTabThree {

    private static ObservableList<String> tableList = FXCollections.observableArrayList();

    public ObservableList<String> getTableList() {
        return tableList;
    }

    private static WorkDB workDB = new WorkDB();

    @FXML
    private AnchorPane AnchPainConnect;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private TextField txtFieldURL;

    @FXML
    private TextField txtFieldUser;

    @FXML
    private PasswordField txtFieldPassword;

    @FXML
    private TextField txtFieldBD;

    @FXML
    private ImageView imgViewConnect;

    @FXML
    private ComboBox<String> comboBoxTable;

    @FXML
    private void initialize() {
        imgViewConnect.setImage(new Image("/Image/ConRed.png"));
        btnConnect.setOnAction(e -> {
            try {
                connect();
                workDB.metaData();
                comboBoxTable.setItems(tableList);
            } catch (SQLException ex) {
                Logger.getLogger(ControllerTabThree.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnDisconnect.setOnAction(e -> {
            try {
                disconnect();
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

        imgViewConnect.setImage(new Image("/Image/ConGreen.png"));
    }

    private void disconnect() throws SQLException {
        workDB.closeConnection();
        imgViewConnect.setImage(new Image("/Image/ConRed.png"));
    }
}
