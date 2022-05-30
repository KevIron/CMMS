package com.keviron.cmms.Controllers;

import com.keviron.cmms.Database.H2DatabaseConnector;
import com.keviron.cmms.TableModels.RepairModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddRepairController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField carBrandField;

    @FXML
    private TextField carNameField;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField clientSurnameField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextArea repairDescriptionField;

    @FXML
    private TextField winField;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField phoneNumberField;

    //Class object definitions:
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    Connection connection = null;

    //Variables definition for future usage :
    String ClientName;
    String ClientSurname;
    String CarBrand;
    String CarName;
    String WIN;
    String Date;
    String RepairActivities;
    String PhoneNumber;
    Integer ClientID;
    Boolean isValid = true;

    public void initialize(URL url, ResourceBundle rb) {
        //Winfield max length
        winField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 16) {
                newValue = null;
                winField.setText(oldValue);
            }
        });

        //PhoneNumberField Only Numbers
        phoneNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberField.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });
    }

    @FXML
    void repairAddButtonClicked(ActionEvent event) throws SQLException, RuntimeException, IOException {
        assignValues();

        if (isValid) {
            connection = H2DatabaseConnector.getConnection();
            insertValuesToDatabase();
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
    }

    public void assignValues() {

        //Variables check and assign
        if (clientNameField.getText().isEmpty()){
            isValid = false;
        } else if (clientSurnameField.getText().isEmpty()) {
            isValid = false;
        } else if (carBrandField.getText().isEmpty()) {
            isValid = false;
        } else if (carNameField.getText().isEmpty()) {
            isValid = false;
        } else if (winField.getText().isEmpty()) {
           isValid = false;
        } else if (repairDescriptionField.getText().isEmpty()) {
            isValid = false;
        } else if (dateField.getValue() == null) {
            isValid = false;
        } else if (phoneNumberField.getText().isEmpty()) {
            isValid = false;
        } else {
            ClientName = clientNameField.getText();
            ClientSurname = clientSurnameField.getText();
            CarBrand = carBrandField.getText();
            CarName = carNameField.getText();
            WIN = winField.getText();
            RepairActivities = repairDescriptionField.getText();
            Date = dateField.getValue().format(formatter);
            PhoneNumber = phoneNumberField.getText();

            isValid = true;
        }

        //Final Check
        if (isValid) {
            errorMessage.setOpacity(0);

        } else {
            errorMessage.setOpacity(1);
            return;
        }
    }

    private void insertValuesToDatabase() throws SQLException, IOException{
        String addClientQuery = "INSERT INTO Customers (ClientName, ClientSurname, PhoneNumber) VALUES ('"
                + ClientName + "'," + "'" + ClientSurname + "'," + "'" + PhoneNumber + "')";

        String getClientIDQuery = "SELECT ID FROM CUSTOMERS WHERE ClientName = '" + ClientName + "'" +
                "AND ClientSurname = '" + ClientSurname + "'" + "AND PhoneNumber = '" + PhoneNumber + "'";

        PreparedStatement addClientStatement = connection.prepareStatement(addClientQuery);
        addClientStatement.execute();

        PreparedStatement getClientIDStatement = connection.prepareStatement(getClientIDQuery);
        ResultSet getClientIDResult = getClientIDStatement.executeQuery();
        getClientIDResult.next();

        ClientID = getClientIDResult.getInt("ID");;
        System.out.println(ClientID);

        String addRepairQuery = "INSERT INTO Repairs (ClientID, CarBrand, CarName, WIN, Date, RepairActivities)" +
                "VALUES ('" + ClientID + "','" + CarBrand + "','" + CarName + "','" + WIN + "','" + Date + "','" +
                RepairActivities + "')";

        PreparedStatement addRepairStatement = connection.prepareStatement(addRepairQuery);
        addRepairStatement.execute();

    }
}
