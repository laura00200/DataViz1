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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class GeneratedScatterController implements Initializable, CSVManipulator, PDFExport {
    Boolean legend;
    String title;
    String filePath;
    String savePath;
    Stage stage;

    @FXML
    final NumberAxis xAxis = new NumberAxis();

    @FXML
    final NumberAxis yAxis = new NumberAxis();
    @FXML
    private ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

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

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onExportPDFButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(stage);
        savePath = file.getAbsolutePath();
        save(scatterChart, savePath.concat(".pdf"));

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Chart exported");
        confirmation.setHeaderText("Your chart pdf has been successfully saved at the desired location");
        confirmation.show();
    }

    public void statsFromScatterChartController(ObservableList<String> groupings, String xName, String yName, Boolean legend, String title, String filePath) throws ParseException {

        this.legend = legend;
        this.title = title;
        this.filePath = filePath;

        scatterChart.setLegendVisible(legend);
        scatterChart.setTitle(title);

        scatterChart.getXAxis().setLabel(xName);
        scatterChart.getYAxis().setLabel(yName);

        ArrayList<List<String>> fileContents = XYInput(filePath);
        HashMap<String, XYChart.Series<Number, Number>> grouping = new HashMap<>();

        for (List<String> currentLine : fileContents) {

            if (grouping.containsKey(currentLine.get(0))) {
                int y = 2;

                while (y < currentLine.size()) {
                    grouping.get(currentLine.get(0)).getData().add(new XYChart.Data<>(NumberFormat.getInstance().parse(currentLine.get(y - 1)), NumberFormat.getInstance().parse(currentLine.get(y))));
                    y++;

                }

            } else {

                // create a grouping for each first element of a line
                XYChart.Series<Number, Number> serie = new XYChart.Series<>();
                //  grouping.put(currentLine.get(0), serie);
                serie.setName(currentLine.get(0));

                int y = 2;

                while (y < currentLine.size()) {
                    serie.getData().add(new XYChart.Data<>(NumberFormat.getInstance().parse(currentLine.get(y - 1)), NumberFormat.getInstance().parse(currentLine.get(y))));
                    y++;

                }
                grouping.put(currentLine.get(0), serie);


            }

        }
        for (XYChart.Series<Number, Number> aux : grouping.values())
            scatterChart.getData().add(aux);
    }
}
