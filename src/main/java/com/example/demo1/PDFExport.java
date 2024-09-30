package com.example.demo1;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public interface PDFExport {

    // method which transposes the chart object into an image, adds the image to a pdf file
    // which will be saved in the location specified by the path string
    default void save(Chart chart, String path) {

        // creates image containing the chart
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);

        File chartImage = new File("chart.png");

        try {

            // write the image to the file
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", chartImage);

        } catch (IOException ignored) {

        }

        // create empty PDF document
        PDDocument doc = new PDDocument();

        // creates a page for a pdf document
        PDPage page = new PDPage();

        PDImageXObject pdimage;

        PDPageContentStream content;

        try {

            pdimage = PDImageXObject.createFromFile("chart.png", doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            // set the positioning of the image in the file
            page.setCropBox(new PDRectangle(10f, 10f, 600f, 600f));

            // add page containing the chart into the PDF document
            doc.addPage(page);

            // save the document at the specified path
            doc.save(path);

            // close the document
            doc.close();

            // close the image content stream
            content.close();

            chartImage.delete();

        } catch (IOException ignored) {

        }

    }


}

