package com.org.house;

import com.org.house.Controllers.ControllerTabThree;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;

public class WorkDB {

    private final String queryWriteFromTxt = "Insert INTO \"Information\" (\"Login\", \"Login hash\",\"Password\", \"Password hash\", \"File path\", \"File name\", \"Note\")" + " VALUES (?, ? , ? , ? , ? , ?, ?)";
    private final String queryWriteFromExcel = "Insert INTO \"Information\" (\"Name\", \"City\",\"Login\", \"Login hash\", \"Password\", \"Password hash\", \"File path\", \"File name\", \"Note\")" + " VALUES (?, ? , ? , ? , ? , ?, ?, ?, ?)";

    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;
    private static DatabaseMetaData metaData;

    private List<Date> listInformation = new ArrayList<Date>();

    public List<Date> getListInformation() {
        return listInformation;
    }

    private DialogMessage message = new DialogMessage();
    private ControllerTabThree controllerTabThree = new ControllerTabThree();

    public void Connect(String url, String db, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s/%s", url, db), user, password);
        statement = connection.createStatement();
        message.message("Connection has been established !", AlertType.INFORMATION);
    }

    public void closeConnection() throws SQLException {
        connection.close();
        message.message("Connection was closed !", AlertType.INFORMATION);
    }

    public void metaData() throws SQLException {
        metaData = connection.getMetaData();
        resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            String table = resultSet.getString("TABLE_NAME");
            controllerTabThree.getTableList().add(table);
        }
    }

    public synchronized void writeInDBfromTxt(String login, String loginHash, String password, String passwordHash, String path, String fileName, String note) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryWriteFromTxt);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, loginHash);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, passwordHash);
        preparedStatement.setString(5, path);
        preparedStatement.setString(6, fileName);
        preparedStatement.setString(7, note);

        preparedStatement.execute();
    }

    public synchronized void writeInDBfromExcel(String name, String city, String login, String loginHash, String password, String passwordHash, String filePath, String fileName, String note) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryWriteFromExcel);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, city);
        preparedStatement.setString(3, login);
        preparedStatement.setString(4, loginHash);
        preparedStatement.setString(5, password);
        preparedStatement.setString(6, passwordHash);
        preparedStatement.setString(7, filePath);
        preparedStatement.setString(8, fileName);
        preparedStatement.setString(9, note);

        preparedStatement.execute();

    }

    public synchronized void getDate(int n, int ofst) throws SQLException {
        listInformation.clear();
        resultSet = statement.executeQuery(String.format("select * from \"Information\" limit %d offset %d ", n, ofst));
        while (resultSet.next()) {
            listInformation.add(new Date(resultSet.getInt("id"), resultSet.getString("Login"), resultSet.getString("Login Hash"), resultSet.getString("Password"), resultSet.getString("Password Hash"), resultSet.getString("File name"), resultSet.getString("File path"), resultSet.getString("City"), resultSet.getString("Name"), resultSet.getString("Note")));
        }
    }
}
