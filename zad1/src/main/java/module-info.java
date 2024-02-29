module com.example.zad1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zad1 to javafx.fxml;
    exports com.example.zad1;
}