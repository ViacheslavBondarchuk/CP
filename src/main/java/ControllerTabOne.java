import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerTabOne {

    private int Quantiti;
    private int Offset;

    private Thread thread1;
    private Thread thread2;
    private String path, colName, colPassw, colLiving;
    private static Parse parse;
    private static WorkDB workDB;

    private ObservableList<Date> obsListInformation = FXCollections.observableArrayList();

    public ControllerTabOne() throws SQLException {
        parse = new Parse();
        workDB = new WorkDB();

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    colName = txtFieldNameCol.getText();
                    colPassw = txtFieldPassCol.getText();
                    colLiving = txtFieldLivingCol.getText();
                    parse.parseAndWriteToFile(path, colName, colPassw, colLiving);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    parse.parseAndWriteToDB(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Date> TableView;

    @FXML
    private TableColumn<Date, Integer> tableID;

    @FXML
    private TableColumn<Date, String> tableLogin;

    @FXML
    private TableColumn<Date, String> tableLoginHash;

    @FXML
    private TableColumn<Date, String> tablePassword;

    @FXML
    private TableColumn<Date, String> tablePasswordHash;

    @FXML
    private TableColumn<Date, String> tableFileName;

    @FXML
    private TableColumn<Date, String> tablePathFile;

    @FXML
    private TableColumn<Date, String> tableCity;

    @FXML
    private TableColumn<Date, String> tablenote;

    @FXML
    private TableColumn<Date, String> tableName;

    @FXML
    private CheckBox chkBoxParseToDB;

    @FXML
    private CheckBox chkBoxExcel;

    @FXML
    private Label lblToParse;

    @FXML
    private Label lblToWrite;

    @FXML
    private Button btnOpenFileForGet;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnRefreshTableView;

    @FXML
    private Button btnCloseConnect;

    @FXML
    private Button btnStart;

    @FXML
    private TextField txtFieldPassCol;

    @FXML
    private TextField txtFieldNameCol;

    @FXML
    private TextField txtFieldLivingCol;

    @FXML
    private Button btnClose;

    @FXML
    private ToggleButton tglBtnPause;

    @FXML
    private Label lblLoad;

    @FXML
    private TextField txtFieldQuantity;

    @FXML
    private TextField txtFieldOrder;

    @FXML
    private Button btnIncOrder;

    @FXML
    private Button btnDecOrder;

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnStop;

    @FXML
    void initialize() {
        //////////////////////////////////////////////////////////////////////////////////////////////
        TableView.setItems(obsListInformation);

        tableID.setCellValueFactory(new PropertyValueFactory<Date, Integer>("id"));
        tableLogin.setCellValueFactory(new PropertyValueFactory<Date, String>("login"));
        tableLoginHash.setCellValueFactory(new PropertyValueFactory<Date, String>("loginHash"));
        tablePassword.setCellValueFactory(new PropertyValueFactory<Date, String>("password"));
        tablePasswordHash.setCellValueFactory(new PropertyValueFactory<Date, String>("passwordHash"));
        tableFileName.setCellValueFactory(new PropertyValueFactory<Date, String>("fileName"));
        tablePathFile.setCellValueFactory(new PropertyValueFactory<Date, String>("filePath"));
        tableCity.setCellValueFactory(new PropertyValueFactory<Date, String>("city"));
        tableName.setCellValueFactory(new PropertyValueFactory<Date, String>("name"));
        tablenote.setCellValueFactory(new PropertyValueFactory<Date, String>("note"));

        //////////////////////////////////////////////////////////////////////////////////////////////
        btnStart.setOnAction(e -> {
            if (chkBoxParseToDB.isSelected()) {
                thread1.start();
            } else if (chkBoxExcel.isSelected()) {
                thread2.start();
            }
        });
        btnClose.setOnAction(e -> {
            System.gc();
            System.exit(0);
        });
        btnOpenFileForGet.setOnAction(e -> {
            openFileForGetPath();
        });
        chkBoxParseToDB.setOnAction(e -> {
            if (chkBoxParseToDB.isSelected()) {
                chkBoxExcel.setDisable(true);
                txtFieldLivingCol.setDisable(true);
                txtFieldNameCol.setDisable(true);
                txtFieldPassCol.setDisable(true);
            } else {
                chkBoxExcel.setDisable(false);
                txtFieldLivingCol.setDisable(false);
                txtFieldNameCol.setDisable(false);
                txtFieldPassCol.setDisable(false);
            }
        });
        chkBoxExcel.setOnAction(e -> {
            if (chkBoxExcel.isSelected()) {
                chkBoxParseToDB.setDisable(true);
            } else {
                chkBoxParseToDB.setDisable(false);
            }
        });
        btnConnect.setOnAction(e -> {
            try {
                workDB.Connect();
                imgView.setImage(new Image("Image/ConGreen.png"));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnCloseConnect.setOnAction(e -> {
            try {
                workDB.closeConnection();
                imgView.setImage(new Image("Image/ConRed.png"));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        tglBtnPause.setOnAction(e -> {
            pause();
        });

        btnRefreshTableView.setOnAction(e -> {
            Quantiti = Integer.parseInt(txtFieldQuantity.getText());
            Offset = Integer.parseInt(txtFieldOrder.getText());

            try {
                getDateInTableView(Quantiti, Offset);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        btnIncOrder.setOnAction(e -> {
            orderInc();
            try {
                getDateInTableView(Quantiti, Offset);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnDecOrder.setOnAction(e -> {
            orderDec();
            try {
                getDateInTableView(Quantiti, Offset);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        btnStop.setOnAction(e -> {
            if (chkBoxParseToDB.isSelected()) {
                thread1.stop();
                new DialogMessage().message("Process was stoped", AlertType.INFORMATION);
            } else if (chkBoxExcel.isSelected()) {
                thread2.stop();
                new DialogMessage().message("Process was stoped", AlertType.INFORMATION);
            }
        });

        imgView.setImage(new Image("Image/ConRed.png"));
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void openFileForGetPath() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retValue = chooser.showOpenDialog(null);
        if (retValue == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
            lblToParse.setText(path);

        }
    }

    private void pause() {
        if (tglBtnPause.isSelected()) {
            if (chkBoxParseToDB.isSelected()) {
                thread1.suspend();
                System.out.println("th1 pause");
            } else {
                thread2.suspend();
                System.out.println("th2 pause");
            }
        } else {
            if (chkBoxParseToDB.isSelected()) {
                thread1.resume();
                System.out.println("th1 resume");
            } else {
                thread2.resume();
                System.out.println("th2 resume");
            }
        }
    }

    private void getDateInTableView(int n, int ofst) throws SQLException {
        obsListInformation.clear();
        workDB.getDate(n, ofst);
        for (int i = 0; i < workDB.getListInformation().size(); i++) {
            obsListInformation.add(new Date(workDB.getListInformation().get(i).getId(), workDB.getListInformation().get(i).getLogin(), workDB.getListInformation().get(i).getLoginHash(), workDB.getListInformation().get(i).getPassword(), workDB.getListInformation().get(i).getPasswordHash(), workDB.getListInformation().get(i).getFileName(), workDB.getListInformation().get(i).getFilePath(), workDB.getListInformation().get(i).getCity(), workDB.getListInformation().get(i).getName(), workDB.getListInformation().get(i).getNote()));
        }
        workDB.getListInformation().clear();
    }

    private void orderInc() {
        Offset += Integer.parseInt(txtFieldOrder.getText());
        System.out.println(Offset);
    }

    private void orderDec() {
        if (Offset - Integer.parseInt(txtFieldOrder.getText()) >= 0) {
            Offset -= Integer.parseInt(txtFieldOrder.getText());
        } else {
            System.out.println("Offset was minus");
        }
    }

}
