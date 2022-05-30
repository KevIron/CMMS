package com.keviron.cmms.Controllers;

import com.keviron.cmms.Database.H2DatabaseConnector;
import com.keviron.cmms.TableModels.RepairModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class RepairsController implements Initializable {

    @FXML
    private TableView<RepairModel> repairsList;

    @FXML
    private TableColumn<RepairModel, String> col_CarBrand;

    @FXML
    private TableColumn<RepairModel, String> col_CarName;

    @FXML
    private TableColumn<RepairModel, String> col_ClientName;

    @FXML
    private TableColumn<RepairModel, String> col_ClientSurname;

    @FXML
    private TableColumn<RepairModel, String> col_Date;

    @FXML
    private TableColumn<RepairModel, String> col_ID;

    @FXML
    private TableColumn<RepairModel, String> col_RepairActivities;

    @FXML
    private TableColumn<RepairModel, String> col_WIN;

    @FXML
    private TextField SearchField;

    @FXML
    private Button RefreshButton;

    //Statements query
    String repairsQuery = "SELECT * FROM Repairs";

    //Connection definition
    Connection connection = null;

    //Table observable list
    ObservableList<RepairModel> RepairList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addSearchListener();
    }

    //Loading data into table using h2 database connection
    public void loadData() throws SQLException {
        connection = H2DatabaseConnector.getConnection();

        col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_ClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        col_ClientSurname.setCellValueFactory(new PropertyValueFactory<>("clientSurname"));
        col_CarBrand.setCellValueFactory(new PropertyValueFactory<>("carBrand"));
        col_CarName.setCellValueFactory(new PropertyValueFactory<>("carName"));
        col_WIN.setCellValueFactory(new PropertyValueFactory<>("WIN"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_RepairActivities.setCellValueFactory(new PropertyValueFactory<>("repairActivities"));

        refreshData();
    }


    //Refreshing data in the table by adding columns
    public void refreshData() throws SQLException {
        RepairList.clear();

        //Getting data from database and putting it into table
        PreparedStatement ps = connection.prepareStatement(repairsQuery);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {

            String clientID = resultSet.getString("clientID");
            PreparedStatement clientInfoStatement = connection.prepareStatement("SELECT * FROM Customers WHERE ID = ' " + clientID + "'");
            ResultSet rs = clientInfoStatement.executeQuery();
            rs.next();

            RepairList.add(new RepairModel( resultSet.getString("id"),
                                            rs.getString("clientName"),
                                            rs.getString("clientSurname"),
                                            resultSet.getString("carBrand"),
                                            resultSet.getString("carName"),
                                            resultSet.getString("win"),
                                            resultSet.getString("date"),
                                            resultSet.getString("repairActivities")));
            repairsList.setItems(RepairList);
            addSearchListener();
        }
    }

    @FXML
    private void addNewRepair(ActionEvent evt) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/subMenus/AddRepair.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Create new repair record");
        stage.setScene(new Scene(root, 850, 600));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void refreshTable(ActionEvent evt) throws SQLException {
        refreshData();
    }

    private void addSearchListener() {
        FilteredList<RepairModel> filteredRepairs = new FilteredList<>(RepairList, b ->true);
        SearchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredRepairs.setPredicate(RepairModel -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (RepairModel.getClientName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getClientSurname().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getCarBrand().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getCarName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getWIN().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getDate().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (RepairModel.getRepairActivities().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<RepairModel> repairsSortedList = new SortedList<>(filteredRepairs);
        repairsSortedList.comparatorProperty().bind(repairsList.comparatorProperty());

        repairsList.setItems(repairsSortedList);
    }

}
