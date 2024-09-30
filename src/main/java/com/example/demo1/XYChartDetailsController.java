package com.example.demo1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class XYChartDetailsController implements Initializable, CSVManipulator {

    String filePath;
    String chartType;

    @FXML
    private TextField chartName;

    @FXML
    private TextField xAxisName;

    @FXML
    private TextField yAxisName;

    @FXML
    private RadioButton yesButton;

    @FXML
    private RadioButton noButton;

    @FXML
    private Button nextButton;

    @FXML
    private ToggleGroup legendToggleGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void statsFunction(String filePath, String chartType) {
        this.filePath = filePath;
        this.chartType = chartType;

    }

    @FXML
    public void onNextButtonClick(ActionEvent event) throws IOException, ParseException {

        boolean includeLegend;

        includeLegend = yesButton.isSelected();


        if (chartName.getText().isBlank() || chartName.getText().isEmpty() || xAxisName.getText().isEmpty()
                || xAxisName.getText().isBlank() || yAxisName.getText().isBlank() || xAxisName.getText().isEmpty()) {

            Alert missingTitle = new Alert(Alert.AlertType.ERROR);
            missingTitle.setTitle("Incomplete fields");
            missingTitle.setHeaderText("Please make sure you have completed every field before proceeding.");
            missingTitle.show();

        } else {

            if (chartType.equals("Bar chart")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("generated-bar-view.fxml"));
                Parent root = loader.load();
                GeneratedBarController generatedBarController = loader.getController();

                ObservableList<String> series = getSeries(filePath);

                generatedBarController.statsFromBarChartDetails(series, xAxisName.getText(), yAxisName.getText(), includeLegend, chartName.getText(), filePath);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else if (chartType.equals("Line chart")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("generated-line-view.fxml"));
                Parent root = loader.load();
                GeneratedLineController generatedlineController = loader.getController();

                ObservableList<String> series = getSeries(filePath);

                generatedlineController.statsFromXYChartDetailsController(series, xAxisName.getText(), yAxisName.getText(), includeLegend, chartName.getText(), filePath);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else if (chartType.equals("Scatter chart")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("generated-scatter-view.fxml"));
                Parent root = loader.load();
                GeneratedScatterController scatterChartController = loader.getController();

                ObservableList<String> series = getSeries(filePath);

                scatterChartController.statsFromScatterChartController(series, xAxisName.getText(), yAxisName.getText(), includeLegend, chartName.getText(), filePath);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("generated-bubble-view.fxml"));
                Parent root = loader.load();
                GeneratedBubbleController bubbleChartController = loader.getController();

                ObservableList<String> series = getSeries(filePath);

                bubbleChartController.statsFromBubbleChartController(series, xAxisName.getText(), yAxisName.getText(), includeLegend, chartName.getText(), filePath);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }

        }

    }
}
