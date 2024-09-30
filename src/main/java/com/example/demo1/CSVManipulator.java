package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface CSVManipulator {

    // function to retrieve the number of columns in a CSV file
    default int getNumberOfColumns(String filePath) {

        int len = 0;

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            // reads the first line in the file
            // number of columns = length of the string array resulted from splitting the line by ","
            len = br.readLine().split(",").length;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return len;
    }

    // function to read a CSV file for a Pie chart input
    default ObservableList<PieChart.Data> pieInput(String filePath) {


        // hashmap which will hold name and value for each slice of the piechart
        HashMap<String, Float> frequencies = new HashMap<>();

        String line;
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            // we read the first line outside the while loop because it contains the columns, not the data
            br.readLine();
            while ((line = br.readLine()) != null) { // reads file line by line

                // first element of the line = name of the slice
                // second element of the line = value correspondent to the slice
                frequencies.put(line.split(splitBy)[0], Float.parseFloat(line.split(splitBy)[1]));    // use comma as separator
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // observableList object which will hold the data for the piechart
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();


        // we iterate through the hashmap containing slice names and values
        for (String x : frequencies.keySet())

            // we add each pair from the hashmap into the observable list
            chartData.add(new PieChart.Data(x, frequencies.get(x)));

        return chartData;
    }


    // method to read a CSV file for a XY-type chart input
    default ArrayList<List<String>> XYInput(String filePath) {


        String line;
        String splitBy = ",";

        // list containing all the lines in the CSV file
        ArrayList<List<String>> lines = new ArrayList<>();

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filePath));


            /*
            we read the line containing the column names outside the while loop because
            we will not include it in the data which will be provided to the chart
            */
            br.readLine();


            while ((line = br.readLine()) != null) { // reads file line by line

                // helper list to hold each entry in the file
                List<String> currLine = Arrays.stream(line.split(splitBy)).toList();

                // we add the current line to the list of lines
                lines.add(currLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;

    }

    // method for adding all categories for each line of data into a list
    default ObservableList<String> getSeries(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        // we only read the first line because it contains the column names
        ObservableList<String> categories = FXCollections.observableArrayList(Arrays.asList(br.readLine().split(",")));

        // we remove the first column name from the categories list because
        // this line contains the name for a serie, not a category of each entry
        categories.remove(0);
        return categories;
    }


}
