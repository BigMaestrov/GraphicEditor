module com.example.graphiceditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphiceditor to javafx.fxml;
    exports com.example.graphiceditor;
}