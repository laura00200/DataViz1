package com.example.demo1;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GeneratedPieController implements Initializable, CSVManipulator, PDFExport {

    Boolean legend;
    String title;
    String filePath;
    String savePath;
    Stage stage;
    Scene scene;


    @FXML
    private PieChart chart;

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
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void onExportPDFButtonClick(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(stage);


        savePath = file.getAbsolutePath();
        save(chart, savePath.concat(".pdf"));

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Chart exported");
        confirmation.setHeaderText("Your chart pdf has been successfully saved at the desired location");
        confirmation.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void statsFromPieChartDetails(Boolean legend, String title, String filePath) {
        this.legend = legend;
        this.title = title;
        this.filePath = filePath;

        ObservableList<PieChart.Data> chartData = pieInput(filePath);

        chart.setData(chartData);

        chart.setTitle(title);

        final int total;
        int add = 0;
        for (PieChart.Data data : chartData) {
            add += data.getPieValue();
        }
        total = add;

        chartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), "\n", String.format("%.2f", data.getPieValue() / total * 100), "%")
                )
        );

        chart.setLegendVisible(legend);
    }

}
