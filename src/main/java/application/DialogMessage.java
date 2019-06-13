package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogMessage {

    public void message(String str, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(str);
        alert.showAndWait();
    }

    public void messageContact() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Contacts");
        alert.setContentText("Developer: Viacheslav Bondarchuk" + "\n"
                + "E-mail: slava.777.bondarchuk@outlook.com" + "\n"
                + "Telegram: @ViacheslavBondarchuk");
        alert.showAndWait();
    }

    public void messageHelp() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Help");
        alert.setContentText("*.txt files" + "\n"
                + "1. Press on button 'Connect'" + "\n"
                + "2. Select checkBox 'PTWIDB'" + "\n"
                + "3. Choose folder" + "\n"
                + "4. Press on button 'Start'" + "\n" + "\n"
                + "*.xlsx or *.xls files" + "\n"
                + "1. Press on button 'Connect'" + "\n"
                + "2. Select checkBox 'PEWIDB'" + "\n"
                + "3. Choose folder" + "\n"
                + "4. Fill in the empty fields" + "\n"
                + "5. Press on button 'Start'");
        alert.showAndWait();
    }
}
