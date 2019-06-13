import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControllerTabTwo {

    private String str;
    private String[] strMass;
    private String name, city, login, loginHash, password, passwordHash, filePath, fileName, note;

    private Cipher cipher = new Cipher();
    private WorkDB workDB = new WorkDB();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtFieldDate;

    @FXML
    private Button btnAdd;

    @FXML
    void initialize() {

        btnAdd.setOnAction(e -> {
            try {
                getDate();
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

    }

    private void getDate() throws NoSuchAlgorithmException, SQLException {
        str = txtFieldDate.getText();
        strMass = str.split(",");

        name = strMass[0];
        city = strMass[1];
        login = strMass[2];
        loginHash = cipher.cipherLogin(login);
        password = strMass[3];
        passwordHash = cipher.cipherPassword(password);
        filePath = strMass[4];
        fileName = strMass[5];
        note = strMass[6];

        workDB.writeInDBfromExcel(name, city, login, loginHash, password, passwordHash, filePath, fileName, note);
        new DialogMessage().message("Was executed succefull", AlertType.INFORMATION);
    }

}
