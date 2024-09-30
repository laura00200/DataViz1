package com.example.demo1;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GeneratedBarController implements Initializable, CSVManipulator, PDFExport {

    Boolean legend;
    String title;
    String filePath;
    String savePath;
    Stage stage;

    @FXML
    final CategoryAxis xAxis = new CategoryAxis();

    @FXML
    final NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    @FXML
    private Button generateAnotherChartButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button exportPDFButton;

    @FXML
    public void onGenerateAnotherChartButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("choose-file-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onExportPDFButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(stage);
        savePath = file.getAbsolutePath();
        save(barChart, savePath.concat(".pdf"));

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Chart exported");
        confirmation.setHeaderText("Your chart pdf has been successfully saved at the desired location");
        confirmation.show();
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // function which receives all data necessary for the representation of the bar chart
    public void statsFromBarChartDetails(ObservableList<String> groupings, String xName, String yName, Boolean legend, String title, String filePath) {

        // boolean value which indicates whether the chart should contain a legend or not
        this.legend = legend;


        // title of the chart
        this.title = title;

        // file path of the CSV
        this.filePath = filePath;

        barChart.setLegendVisible(legend);

        barChart.setTitle(title);

        barChart.getXAxis().setLabel(xName);

        barChart.getYAxis().setLabel(yName);

        xAxis.setCategories(groupings);


        // list containing the contents of the CSV file
        ArrayList<List<String>> fileContents = XYInput(filePath);


        try {
            for (List<String> currentLine : fileContents) {


                // create a series object for each entry in the CSV file
                XYChart.Series<String, Number> serie = new XYChart.Series<>();

                // set the name of the serie to the first element of the line
                serie.setName(currentLine.get(0));

                // start from 1 because position 0 contains the name of the serie
                int y = 1;

                while (y < currentLine.size()) {


                    serie.getData().add(new XYChart.Data<>(groupings.get(y - 1), NumberFormat.getInstance().parse(currentLine.get(y))));
                    y++;

                }

                barChart.getData().add(serie);

            }
        } catch (Exception ignored) {

        }
    }

}
