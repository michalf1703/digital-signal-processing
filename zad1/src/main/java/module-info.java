module com.example.zad1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.example.zad1 to javafx.fxml;
    exports com.example.zad1;
    exports com.example.zad1.Signals;
    opens com.example.zad1.Signals to javafx.fxml;
    exports com.example.zad1.RadarSignals;
    opens com.example.zad1.RadarSignals to javafx.fxml;
}