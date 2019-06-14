package com.org.house.Controllers;

import com.org.house.DialogMessage;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    ControllerTabOne controllerTabOne;

    @FXML
    ControllerTabTwo controllerTabTwo;

    @FXML
    ControllerTabThree ControllerTabThree;

    @FXML
    private MenuItem menuItemContact;

    @FXML
    private MenuItem menuItemHelp;

    @FXML
    private void initialize() {

        menuItemContact.setOnAction(e -> {
            new DialogMessage().messageContact();
        });

        menuItemHelp.setOnAction(e -> {
            new DialogMessage().messageHelp();
        });

    }

}
