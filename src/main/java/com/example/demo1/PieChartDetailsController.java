package com.example.demo1;

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
import java.util.ResourceBundle;

public class PieChartDetailsController implements Initializable {

    private String filePath;

    @FXML
    private TextField chartName;

    @FXML
    private RadioButton yesButton;

    @FXML
    private RadioButton noButton;

    @FXML
    private Button nextButton;

    @FXML
    private ToggleGroup legendToggleGroup;

    @FXML
    public void onNextButtonClick(ActionEvent event) throws IOException {

        boolean includeLegend;

        includeLegend = yesButton.isSelected();


        if (chartName.getText().isBlank() || chartName.getText().isEmpty()) {

            Alert missingTitle = new Alert(Alert.AlertType.ERROR);
            missingTitle.setTitle("Missing title");
            missingTitle.setHeaderText("Please enter a title for the chart.");
            missingTitle.show();

        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("generated-pie-view.fxml"));
            Parent root = loader.load();
            GeneratedPieController generatedChartController = loader.getController();

            generatedChartController.statsFromPieChartDetails(includeLegend, chartName.getText(), filePath);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void statsFunction(String filePath) {
        this.filePath = filePath;

    }
}
