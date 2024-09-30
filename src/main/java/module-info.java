module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jfr;
    requires org.apache.commons.io;
    requires opencsv;
    requires javafx.swing;
    requires org.apache.pdfbox;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}