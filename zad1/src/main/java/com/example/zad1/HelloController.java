package com.example.zad1;

import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import com.example.zad1.FileOperationException;
import com.example.zad1.FileReader;
import com.example.zad1.Signals.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.*;

public class HelloController {
    @FXML
    private ChoiceBox<String> rodzajSygnalu, wybierzOperacje, przedzialHistogramu;
    @FXML
    private TextField amplitudaF, czasTrwaniaF, okresPodstawowyF, poczatkowyF, wspolczynnikWypelnieniaF, czasSkokuF, czestoscProbkowaniaF, numerProbkiSkokuF, prawdopodobienstwoF;
    @FXML
    private Text skutecznaWynik, sredniaBezwWynik, wariancjaWynik, wartoscSredniaWynik, mocSredniaWynik;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReader<Signal> signalFileReader;

    private String[] opcje = {"szum o rozkładzie jednostajnym", "szum gaussowski", "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo", "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny", "sygnał prostokątny symetryczny", "sygnał trójkątny", "skok jednostkowy",
            "impuls jednostkowy", "szum impulsowy"};

    private String[] opcjeOperacje = {"dodawanie", "odejmowanie", "mnożenie", "dzielenie"};
    private String[] opcjePrzedzial = {"5", "10", "15", "20"};
    private Integer tabIndex = 1;
    private Window stage;

    @FXML
    protected void initialize() {
        rodzajSygnalu.getItems().addAll(opcje);
        wybierzOperacje.getItems().addAll(opcjeOperacje);
        przedzialHistogramu.getItems().addAll(opcjePrzedzial);
        rodzajSygnalu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            checkSignal();
        });
        checkSignal();
    }

    void checkSignal() {
        String signal = rodzajSygnalu.getValue();
        if (signal != null) {
            switch (signal) {
                case "szum o rozkładzie jednostajnym":
                case "szum gaussowski":
                    setupFields(true, true, false, true, false, false, true, false, false);
                    break;
                case "sygnał sinusoidalny":
                case "sygnał sinusoidalny wyprostowany jednopołówkowo":
                case "sygnał sinusoidalny wyprostowany dwupołówkowo":
                    setupFields(true, true, true, true, false, false, true, false, false);
                    break;
                case "sygnał prostokątny":
                case "sygnał prostokątny symetryczny":
                case "sygnał trójkątny":
                    setupFields(true, true, true, true, true, false, true, false, false);
                    break;
                case "skok jednostkowy":
                    setupFields(true, true, false, true, false, true, true, false,false);
                    break;
                case "impuls jednostkowy":
                    setupFields(true, true, false, true, false, false, true, true,false);
                    break;
                case "szum impulsowy":
                    setupFields(true, true, false, true, false, false, true, false,true);
                    break;
                default:
                    setupFields(true, true, true, true, true, true, true, true,false);
            }
        }
    }

    private void setupFields(boolean amplituda, boolean czasTrwania, boolean okresPodstawowy, boolean poczatkowy, boolean wspolczynnikWypelnienia, boolean czasSkoku, boolean czestoscProbkowania, boolean numerProbkiSkoku, boolean prawdopodobienstwo){
        amplitudaF.setDisable(!amplituda);
        czasTrwaniaF.setDisable(!czasTrwania);
        okresPodstawowyF.setDisable(!okresPodstawowy);
        poczatkowyF.setDisable(!poczatkowy);
        wspolczynnikWypelnieniaF.setDisable(!wspolczynnikWypelnienia);
        czasSkokuF.setDisable(!czasSkoku);
        czestoscProbkowaniaF.setDisable(!czestoscProbkowania);
        numerProbkiSkokuF.setDisable(!numerProbkiSkoku);
        prawdopodobienstwoF.setDisable(!prawdopodobienstwo);
    }

    public void computeSignals() {
        String signal = rodzajSygnalu.getValue();
        if (signal != null) {
            try {
                Signal s = null;
                double amplitude = Double.parseDouble(amplitudaF.getText());
                double rangeStart = Double.parseDouble(poczatkowyF.getText());
                double rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                double sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());

                switch (signal) {
                    case "szum o rozkładzie jednostajnym":
                        s = new UniformNoise(rangeStart, rangeLength, amplitude, sampleRate);
                        break;
                    case "sygnał sinusoidalny":
                        double term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał prostokątny":
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "szum gaussowski":
                        s = new GaussianNoise(rangeStart, rangeLength, amplitude, sampleRate);
                        break;
                    case "sygnał sinusoidalny wyprostowany jednopołówkowo":
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new HalfwaveRectifiedSinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał sinusoidalny wyprostowany dwupołówkowo":
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new FullwaveRectifiedSinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał prostokątny symetryczny":
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new SymmetricRectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "sygnał trójkątny":
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new TriangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "skok jednostkowy":
                        double jumpMoment = Double.parseDouble(czasSkokuF.getText());
                        s = new UnitStep(rangeStart, rangeLength, amplitude, jumpMoment, sampleRate);
                        break;
                    case "impuls jednostkowy":
                        int jumpSampleNumber = Integer.parseInt(numerProbkiSkokuF.getText());
                        s = new UnitImpulse(rangeStart, rangeLength, sampleRate, amplitude, jumpSampleNumber);
                        break;
                    case "szum impulsowy":
                        double probability = Double.parseDouble(prawdopodobienstwoF.getText());
                        s = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude, probability);
                        break;
                }
                calculateSignal(s);
            } catch (NumberFormatException e) {
                showAlert("Błąd", "Błędne dane", "Upewnij się, że wszystkie pola zawierają poprawne wartości liczbowe.");
            }
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void saveChart() {
        try {
            if (signals.get(tabIndex) != null) {
                signalFileReader = new FileReader<>(new FileChooser()
                        .showSaveDialog(stage)
                        .getName());
                signalFileReader.write(signals.get(tabIndex));
            } else {
                showAlert("Błąd", null, "Nie zapisano sygnału.");
            }
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd", null, "Nie zapisano sygnału.");
        }
    }

    public void loadChart() {
        try {
            signalFileReader = new FileReader<>(new FileChooser()
                    .showOpenDialog(stage)
                    .getName());
            signals.put(tabIndex, signalFileReader.read());
            calculateSignal(signals.get(tabIndex));
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd", null, "Nie załadowano sygnału.");
        }
    }

    public void loadSignalForOperation() {
        try {
            signalFileReader = new FileReader<>(new FileChooser()
                    .showOpenDialog(stage)
                    .getName());
            signals.put(tabIndex, signalFileReader.read());
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd", null, "Wczytywanie sygnału się nie powiodło.");
        }
        tabIndex++;
    }

    public void performOperations() {
        if (!haveSameSampleRate()) {
            showAlert("Błąd", null, "Nie można wykonać operacji na sygnałach o różnych częstotliwościach próbkowania.");
            return;
        }
        String operation = wybierzOperacje.getValue();
        if (operation != null) {
            Signal s1 = signals.get(1);
            Signal s2 = signals.get(2);
            Signal result = null;
            switch (operation) {
                case "dodawanie":
                    result = new OperationSignal(s1, s2, (a, b) -> a + b);
                    break;
                case "odejmowanie":
                    result = new OperationSignal(s1, s2, (a, b) -> a - b);
                    break;
                case "mnożenie":
                    result = new OperationSignal(s1, s2, (a, b) -> a * b);
                    break;
                case "dzielenie":
                    result = new OperationSignal(s1, s2, (a, b) -> a / b);
                    break;
            }
            calculateOperationResult(result);
        }
    }

    public void calculateSignal(Signal signal) {
        if (przedzialHistogramu.getValue() == null) {
            showAlert("Błąd", null, "Nie został wybrany przedział histogramu.");
            return;
        }

        signal.generate();
        skutecznaWynik.setText("" + signal.rmsValue());
        sredniaBezwWynik.setText("" + signal.absMeanValue());
        mocSredniaWynik.setText("" + signal.meanPowerValue());
        wartoscSredniaWynik.setText("" + signal.meanValue());
        wariancjaWynik.setText("" + signal.varianceValue());
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");
        String przedzial = przedzialHistogramu.getValue();
        int przedzialInt = Integer.parseInt(przedzial);

        Parent chart;
        String title = signal.getName();

        if (title.equals("szum impulsowy") || title.equals("impuls jednostkowy")) {
            ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
            scatterChart.setTitle(title);

            XYChart.Series series = new XYChart.Series();
            List<Data> data = signal.getData();
            for (Data point : data) {
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
        } else {
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle(title);

            XYChart.Series series = new XYChart.Series();
            List<Data> data = signal.getData();
            for (Data point : data) {
                series.getData().add(new XYChart.Data(point.getX(), point.getY()));
            }
            lineChart.getData().add(series);
            lineChart.setAnimated(false);
            lineChart.setCreateSymbols(false);

            chart = lineChart;
        }
        List<Range> histogram = signal.generateHistogram(przedzialInt);
        CategoryAxis barXAxis = new CategoryAxis();
        NumberAxis barYAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(barXAxis, barYAxis);
        barChart.setTitle("Histogram");
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        for (Range range : histogram) {
            barSeries.getData().add(new XYChart.Data<>(String.format("%.2f - %.2f", range.getBegin(), range.getEnd()), range.getQuantity()));
        }
        barChart.getData().add(barSeries);
        VBox vbox = new VBox(chart, barChart);
        Scene scene = new Scene(vbox, 800, 600);
        signals.put(tabIndex, signal);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void calculateOperationResult(Signal result) {
        if (przedzialHistogramu.getValue() == null) {
            showAlert("Błąd", null, "Nie został wybrany przedział histogramu.");
            return;
        }

        result.generate();
        skutecznaWynik.setText("" + result.rmsValue());
        sredniaBezwWynik.setText("" + result.absMeanValue());
        mocSredniaWynik.setText("" + result.meanPowerValue());
        wartoscSredniaWynik.setText("" + result.meanValue());
        wariancjaWynik.setText("" + result.varianceValue());
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");
        String przedzial = przedzialHistogramu.getValue();
        int przedzialInt = Integer.parseInt(przedzial);
        String title = "wykresy po operacji";
        Parent chart;
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        XYChart.Series series = new XYChart.Series();
        List<Data> data = result.getData();
        for (Data point : data) {
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);

        chart = lineChart;

        List<Range> histogram = result.generateHistogram(przedzialInt);
        CategoryAxis barXAxis = new CategoryAxis();
        NumberAxis barYAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(barXAxis, barYAxis);
        barChart.setTitle("Histogram");
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        for (Range range : histogram) {
            barSeries.getData().add(new XYChart.Data<>(String.format("%.2f - %.2f", range.getBegin(), range.getEnd()), range.getQuantity()));
        }
        barChart.getData().add(barSeries);
        VBox vbox = new VBox(chart, barChart);
        Scene scene = new Scene(vbox, 800, 600);
        signals.put(tabIndex, result);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private boolean haveSameSampleRate() {
        if (signals.size() < 2) {
            return true;
        }

        double sampleRate = signals.get(1).getSampleRate();
        for (Signal signal : signals.values()) {
            if (signal.getSampleRate() != sampleRate) {
                return false;
            }
        }
        return true;
    }
}
