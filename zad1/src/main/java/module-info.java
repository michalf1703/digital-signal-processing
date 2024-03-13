module com.example.zad1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.example.zad1 to javafx.fxml;
    exports com.example.zad1;
    exports com.example.zad1.Signals;
    exports com.example.zad1.Charts;
    opens com.example.zad1.Signals to javafx.fxml;
}