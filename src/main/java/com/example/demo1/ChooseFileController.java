package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ChooseFileController implements Initializable, CSVManipulator {

    private Stage stage;
    private String filePath;
    Parent root;

    @FXML
    private Button loadButton;

    @FXML
    private Button toDiagramDetailsButton;

    @FXML
    private Label chosenFile;


    @FXML
    private ChoiceBox<String> typesOfCharts = new ChoiceBox<>();

    @FXML
    public void onLoadButtonClick(ActionEvent event) {

        // object to open a window from which the user will pick the CSV FILE
        FileChooser fileChooser = new FileChooser();

        //
        File file = fileChooser.showOpenDialog(stage);


        filePath = file.getAbsolutePath();

        // retrieves the extension of the chosen file
        String fileType = FilenameUtils.getExtension(file.getAbsolutePath());

        // we check to see if the chosen file is a CSV file
        if (fileType.compareTo("csv") != 0) {
            Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
            wrongTypeAlert.setTitle("Type error");
            wrongTypeAlert.setHeaderText("The required format for files is CSV. Please choose another file.");
            wrongTypeAlert.show();
        } else {
            chosenFile.setText(file.getName());
        }
    }


    public void initialize(URL location, ResourceBundle resources) {

        typesOfCharts.setItems(FXCollections.observableArrayList("Pie chart", "Bar chart", "Bubble chart", "Line chart", "Scatter chart"));
    }

    @FXML
    public void onToDiagramDetailsButtonClick(ActionEvent event) throws IOException {


        if ("Pie chart".equals(typesOfCharts.getSelectionModel().getSelectedItem())) {

            // pie charts only accept files with 2 columns
            if (getNumberOfColumns(filePath) != 2) {
                Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
                wrongTypeAlert.setTitle("File format error");
                wrongTypeAlert.setHeaderText("The uploaded file cannot be transposed into a pie chart.");
                wrongTypeAlert.show();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pie-chart-details-view.fxml"));
            root = loader.load();
            PieChartDetailsController pieChartDetailsController = loader.getController();
            pieChartDetailsController.statsFunction(filePath);

        } else if ("Bar chart".equals(typesOfCharts.getSelectionModel().getSelectedItem())) {

            // bar charts only accept files with 2 columns
            if (getNumberOfColumns(filePath) < 2) {
                Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
                wrongTypeAlert.setTitle("File format error");
                wrongTypeAlert.setHeaderText("The uploaded file cannot be transposed into a bar chart.");
                wrongTypeAlert.show();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("xy-chart-details-view.fxml"));
            root = loader.load();
            XYChartDetailsController barChartDetailsController = loader.getController();
            barChartDetailsController.statsFunction(filePath, typesOfCharts.getSelectionModel().getSelectedItem());

        } else if ("Bubble chart".equals(typesOfCharts.getSelectionModel().getSelectedItem())) {

            // bubble charts only accept files with at least 2 columns
            if (getNumberOfColumns(filePath) < 2) {
                Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
                wrongTypeAlert.setTitle("File format error");
                wrongTypeAlert.setHeaderText("The uploaded file cannot be transposed into a bubble chart.");
                wrongTypeAlert.show();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("xy-chart-details-view.fxml"));
            root = loader.load();
            XYChartDetailsController bubbleChartDetailsController = loader.getController();
            bubbleChartDetailsController.statsFunction(filePath, typesOfCharts.getSelectionModel().getSelectedItem());

        } else if ("Scatter chart".equals(typesOfCharts.getSelectionModel().getSelectedItem())) {

            // scatter charts only accept files with at least columns
            if (getNumberOfColumns(filePath) < 2) {
                Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
                wrongTypeAlert.setTitle("File format error");
                wrongTypeAlert.setHeaderText("The uploaded file cannot be transposed into a scatter chart.");
                wrongTypeAlert.show();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("xy-chart-details-view.fxml"));
            root = loader.load();
            XYChartDetailsController scatterChartDetailsController = loader.getController();
            scatterChartDetailsController.statsFunction(filePath, typesOfCharts.getSelectionModel().getSelectedItem());


        } else { // LINE CHART

            // line charts only accept files with 2 columns
            if (getNumberOfColumns(filePath) < 2) {
                Alert wrongTypeAlert = new Alert(Alert.AlertType.ERROR);
                wrongTypeAlert.setTitle("File format error");
                wrongTypeAlert.setHeaderText("The uploaded file cannot be transposed into a line chart.");
                wrongTypeAlert.show();
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("xy-chart-details-view.fxml"));
            root = loader.load();
            XYChartDetailsController lineChartDetailsController = loader.getController();
            lineChartDetailsController.statsFunction(filePath, typesOfCharts.getSelectionModel().getSelectedItem());


        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
