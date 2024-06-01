module com.projekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.projekt to javafx.fxml;
    exports com.projekt;
}
