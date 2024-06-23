package com.example.zad1;

import com.example.zad1.Base.Data;
import com.example.zad1.Signals.*;
import com.example.zad1.Task4.Data.ComplexSignal;
import com.example.zad1.Task4.Transformer;
import com.example.zad1.fileOperations.ComplexSignalFileReader;
import com.example.zad1.fileOperations.ComplexSignalFileWriter;
import com.example.zad1.fileOperations.RealSignalFileReader;
import com.example.zad1.fileOperations.RealSignalFileWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Zad4Controller {
    private Map<Integer, Signal> signals = new HashMap<>();
    @FXML
    private TextField czasObliczen;
    @FXML
    private ChoiceBox<String> operacjeZad4;
    private FileReader<Signal> signalFileReader;
    private Window stage = new Stage();
    private Integer tabIndex = 1;
    private String[] opcjeZad4 = {"Dyskretna transformacja Fouriera z definicji", "Szybka transformacja Fouriera in situ"
            , "Transformacja Walsha-Hadamarda z definicji", "Szybka transformacja Walsha-Hadamarda in situ"};

    @FXML
    protected void initialize() {
        operacjeZad4.getItems().addAll(opcjeZad4);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void loadSignalForOperation() {
        try {
            signalFileReader = new FileReader<>(new FileChooser()
                    .showOpenDialog(stage)
                    .getName());
            signals.put(tabIndex, signalFileReader.read());
            JOptionPane.showMessageDialog(null, "Wczytywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Wczytywanie sygnału się nie powiodło.");
        }
        tabIndex++;
    }
    private Signal calculateInvocationTime(Callable<Signal> callable,
                                           TextField textField) throws Exception {
        long begin = System.nanoTime();
        Signal signal = callable.call();
        double end = ((System.nanoTime() - begin) / 1_000_000.0);
        textField.setText(String.format("%.3f ms", end));

        return signal;
    }
    public void performOneArgsOperationOnCharts() {
        String operation = operacjeZad4.getValue();
        final Transformer transformer = new Transformer();

        try {
           // long startTime = System.currentTimeMillis();
            Signal s1 = signals.get(1);
            Signal signal = null;
            switch (operation) {

                case "Dyskretna transformacja Fouriera z definicji":
                     signal = calculateInvocationTime(() -> transformer
                                    .discreteFourierTransform((DiscreteSignal) s1),
                            czasObliczen);
                    break;
                case "Szybka transformacja Fouriera in situ":
                     signal = calculateInvocationTime(() -> transformer.fastFourierTransformInSitu((DiscreteSignal) s1),czasObliczen);
                    break;
                case "Transformacja Walsha-Hadamarda z definicji":
                    signal = calculateInvocationTime(() ->transformer.walshHadamardTransform((DiscreteSignal) s1),czasObliczen);
                    signal.getNumberOfSamples();
                    break;
                case "Szybka transformacja Walsha-Hadamarda in situ":
                    signal = calculateInvocationTime(() ->transformer.fastWalshHadamardTransform((DiscreteSignal) s1),czasObliczen);
                    break;
            }
            if (signal instanceof ComplexSignal) {
                System.out.println("Number of samples: " + signal.getNumberOfSamples());
                signals.put(tabIndex, signal);
                representComplexSignal(signal);
            }
            else {
                System.out.println("Number of samples: " + signal.getNumberOfSamples());
                signals.put(tabIndex, signal);
                scatterRepresent(signal);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private List<ChartRecord<Number, Number>> convertDiscreteRepresentationToChartRecord(
            ComplexSignal complexSignal,
            ComplexSignal.DiscreteRepresentationType discreteRepresentationType) {
        complexSignal.setDiscreteRepresentationType(discreteRepresentationType);

        return complexSignal.generateDiscreteRepresentation()
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY()))
                .collect(Collectors.toList());
    }
    public void representComplexSignal(Signal signal) {
        ComplexSignal complexSignal = (ComplexSignal) signal;
        List<ChartRecord<Number, Number>> chartDataReal =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.REAL);

        List<ChartRecord<Number, Number>> chartDataImaginary =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.IMAGINARY);

        List<ChartRecord<Number, Number>> chartDataAbs =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.ABS);

        List<ChartRecord<Number, Number>> chartDataArgument =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.ARG);

        LineChart<Number, Number> realChart = createLineChart(chartDataReal, "Część rzeczywista amplitudy w funkcji częstotliwości");
        LineChart<Number, Number> imaginaryChart = createLineChart(chartDataImaginary, "Część urojona amplitudy w funkcji częstotliwości");
        LineChart<Number, Number> absChart = createLineChart(chartDataAbs, "Moduł liczby zespolonej");
        LineChart<Number, Number> argumentChart = createLineChart(chartDataArgument, "Argument liczby w funkcji częstotliwości");

        VBox mainPane = new VBox();
        mainPane.getChildren().addAll(realChart, imaginaryChart, absChart, argumentChart);

        Scene scene = new Scene(mainPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private LineChart<Number, Number> createLineChart(List<ChartRecord<Number, Number>> data, String title) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (ChartRecord<Number, Number> record : data) {
            series.getData().add(new XYChart.Data<>(record.getX(), record.getY()));
        }

        lineChart.getData().add(series);

        return lineChart;
    }

    @FXML
    void backToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void scatterRepresent(Signal signal) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");
        Parent chart;
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Wykres sygnału");
        XYChart.Series series = new XYChart.Series();
        List<Data> data2 = signal.getData();
        for (Data point : data2) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(point.getX(), point.getY());
            Circle circle = new Circle(3);
            circle.setFill(Color.ORANGE);
            dataPoint.setNode(circle);
            series.getData().add(dataPoint);
        }
        scatterChart.getData().add(series);
        scatterChart.setAnimated(false);
        scatterChart.setLegendVisible(false);
        chart = scatterChart;
        VBox vbox = new VBox(chart);
        Scene scene = new Scene(vbox, 800, 600);
        signals.put(tabIndex, signal);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private ComplexSignalFileReader complexSignalFileReader;
    private ComplexSignalFileWriter complexSignalFileWriter;

    public void loadComplexSignal() {
        try {
            complexSignalFileReader = new ComplexSignalFileReader(new FileChooser()
                    .showOpenDialog(stage)
                    .getName());
            signals.put(tabIndex, complexSignalFileReader.read());
            JOptionPane.showMessageDialog(null, "Wczytywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Wczytywanie sygnału się nie powiodło.");
        }
        tabIndex++;
        representComplexSignal((ComplexSignal) signals.get(tabIndex - 1));
    }

    public void saveComplexSignal() {
        try {
            complexSignalFileWriter = new ComplexSignalFileWriter(new FileChooser()
                    .showSaveDialog(stage)
                    .getName());
            complexSignalFileWriter.write((ComplexSignal) signals.get(tabIndex));
            JOptionPane.showMessageDialog(null, "Zapisywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Zapisywanie sygnału się nie powiodło.");
        }
    }

    private RealSignalFileReader realSignalFileReader;
    private RealSignalFileWriter realSignalFileWriter;

    public void loadRealSignal() {
        try {
            realSignalFileReader = new RealSignalFileReader(new FileChooser()
                    .showOpenDialog(stage)
                    .getName());
            signals.put(tabIndex, realSignalFileReader.read());
            JOptionPane.showMessageDialog(null, "Wczytywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Wczytywanie sygnału się nie powiodło.");
        }
        tabIndex++;
        scatterRepresent(signals.get(tabIndex - 1));
    }

    public void saveRealSignal() {
        try {
            realSignalFileWriter = new RealSignalFileWriter(new FileChooser()
                    .showSaveDialog(stage)
                    .getName());
            realSignalFileWriter.write(signals.get(tabIndex));
            JOptionPane.showMessageDialog(null, "Zapisywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Zapisywanie sygnału się nie powiodło.");
        }
    }

}