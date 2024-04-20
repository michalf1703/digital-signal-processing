package com.example.zad1;

import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import com.example.zad1.Signals.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
import javax.swing.JOptionPane;
import java.util.*;

public class AppController {
    @FXML
    private ChoiceBox<String> rodzajSygnalu, wybierzOperacje, przedzialHistogramu;
    @FXML
    private TextField amplitudaF, czasTrwaniaF, okresPodstawowyF, poczatkowyF, wspolczynnikWypelnieniaF, czasSkokuF, czestoscProbkowaniaF, numerProbkiSkokuF, prawdopodobienstwoF;
    @FXML
    private Text skutecznaWynik, sredniaBezwWynik, wariancjaWynik, wartoscSredniaWynik, mocSredniaWynik;
    @FXML
    private Text bladSrednioResult,czasTransformacjiResult,efektywnaLiczbaBitowResult,maksymalnaRoznicaResult, stosunekSygnalSzumResult, szczytowyStosunekSygnalSzumResult;
    @FXML
    private ChoiceBox<String> wybierzMetodeRekonstrukcjaChoiceBox;
    @FXML
    private Button generuj2;

    @FXML
    private ChoiceBox<String> wybierzMetodeKwantyChoiceBox;
    @FXML
    private TextField liczbaPoziomowKwantField;
    @FXML
    private TextField parametrFunkcjiSincField;
    @FXML
    private ChoiceBox<String> wybierzOperacjeJednoChoiceBox;
    @FXML
    private Button generujPorownanieBotton;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReader<Signal> signalFileReader;
    @FXML
    private TextField liczbaProbekField;
    @FXML
    private Text poprawnyOdczyt1;
    ADC adc = new ADC();
    DAC dac = new DAC();

    @FXML
    private Text poprawnyOdczyt2;

    private String[] opcje = {"szum o rozkładzie jednostajnym", "szum gaussowski", "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo", "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny", "sygnał prostokątny symetryczny", "sygnał trójkątny", "skok jednostkowy",
            "impuls jednostkowy", "szum impulsowy"};

    private String[] opcjeOperacje = {"dodawanie", "odejmowanie", "mnożenie", "dzielenie"};
    private String[] opcjeJedno = {"próbkowanie", "kwantyzacja", "rekonstrukcja sygnału"};
    private String[] opcjeKwantyzacja = {"kwantyzacja równomierna z obcięciem", "kwantyzacja równomierna z zaokrągleniem"};
    private String [] opcjeRekonstrukcja = {"ekstrapolacja zerowego rzędu", "interpolacja pierwszego rzędu", "rekonstrukcja metodą sinc"};
    private String[] opcjePrzedzial = {"5", "10", "15", "20"};
    private Integer tabIndex = 1;
    private Window stage;

    @FXML
    protected void initialize() {
        rodzajSygnalu.getItems().addAll(opcje);
        wybierzOperacje.getItems().addAll(opcjeOperacje);
        wybierzOperacjeJednoChoiceBox.getItems().addAll(opcjeJedno);
        wybierzMetodeKwantyChoiceBox.getItems().addAll(opcjeKwantyzacja);
        wybierzMetodeRekonstrukcjaChoiceBox.getItems().addAll(opcjeRekonstrukcja);
        przedzialHistogramu.getItems().addAll(opcjePrzedzial);
        rodzajSygnalu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            checkSignal();
        });
        checkSignal();
        wybierzOperacjeJednoChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("próbkowanie".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(true);
                wybierzMetodeKwantyChoiceBox.setDisable(true);
                parametrFunkcjiSincField.setDisable(true);
                liczbaPoziomowKwantField.setDisable(true);
                liczbaProbekField.setDisable(false);
            } else if ("kwantyzacja".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(true);
                wybierzMetodeKwantyChoiceBox.setDisable(false);
                parametrFunkcjiSincField.setDisable(true);
                liczbaPoziomowKwantField.setDisable(false);
                liczbaProbekField.setDisable(false);
            } else if ("rekonstrukcja sygnału".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(false);
                wybierzMetodeKwantyChoiceBox.setDisable(true);
                parametrFunkcjiSincField.setDisable(false);
                liczbaPoziomowKwantField.setDisable(true);
                liczbaProbekField.setDisable(false);
            }
        });
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

    public void performOneOperation() {
        String operation = wybierzOperacjeJednoChoiceBox.getValue();
        double numberOfSamples = Double.parseDouble(liczbaProbekField.getText());
        if (operation != null) {
            Signal s1 = signals.get(1);
            switch (operation) {
                case "próbkowanie":

                    DiscreteSignal result = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                    probowanie(result);
                    saveChartProbka(result);
                    break;
                case "kwantyzacja":
                    int numberOfLevels = Integer.parseInt(liczbaPoziomowKwantField.getText());
                    String quantizationMethod = wybierzMetodeKwantyChoiceBox.getValue();
                    switch (quantizationMethod) {
                        case "kwantyzacja równomierna z obcięciem":
                            DiscreteSignal result2 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            DiscreteSignal result3 = adc.truncatingQuantization(result2, numberOfLevels);
                            saveChartProbka(result3);
                            kwantyzacja(result2, result3);
                            break;
                        case "kwantyzacja równomierna z zaokrągleniem":
                            DiscreteSignal result4 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            DiscreteSignal result5 = adc.roundingQuantization(result4, numberOfLevels);
                            saveChartProbka(result5);
                            kwantyzacja(result4, result5);
                            break;
                    }
                    break;
                case "rekonstrukcja sygnału":
                    String reconstructionMethod = wybierzMetodeRekonstrukcjaChoiceBox.getValue();
                    switch (reconstructionMethod) {
                        case "ekstrapolacja zerowego rzędu":
                            DiscreteSignal result6 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            ContinuousSignal result7 = dac.zeroOrderHold(result6);
                            rekonstrukcja(result6, s1, result7);
                            break;
                        case "interpolacja pierwszego rzędu":
                            DiscreteSignal result8 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            ContinuousSignal result9 = dac.firstOrderHold(result8);
                            rekonstrukcja(result8, s1, result9);
                            break;
                        case "rekonstrukcja metodą sinc":
                            int sincParameter = Integer.parseInt(parametrFunkcjiSincField.getText());
                            DiscreteSignal result10 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            ContinuousSignal result11 = dac.sincBasic(result10, sincParameter);
                            rekonstrukcja(result10, s1, result11);
                            saveChartProbka(result11);
                            break;
                    }
                    break;
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

    public void saveChartProbka(Signal signal) {
        try {
            if (signals.get(tabIndex) != null) {
                signalFileReader = new FileReader<>(new FileChooser()
                        .showSaveDialog(stage)
                        .getName());
                signalFileReader.write(signal);
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
            JOptionPane.showMessageDialog(null, "Wczytywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Wczytywanie sygnału się nie powiodło.");
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
    public void makeComparison() {
        Signal s1 = signals.get(1);
        Signal s2 = signals.get(2);
        List<Data> firstSignalData = s1.generateDiscreteRepresentation();
        List<Data> secondSignalData = s2.generateDiscreteRepresentation();
            double meanSquaredError = Signal.meanSquaredError(secondSignalData, firstSignalData);
            double signalToNoiseRatio = Signal.signalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double peakSignalToNoiseRatio = Signal.peakSignalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double maximumDifference = Signal.maximumDifference(secondSignalData, firstSignalData);
            double effectiveNumberOfBits = Signal.effectiveNumberOfBits(secondSignalData,
                    firstSignalData);

            bladSrednioResult.setText(String.valueOf(meanSquaredError));
            stosunekSygnalSzumResult.setText(String.valueOf(signalToNoiseRatio));
            szczytowyStosunekSygnalSzumResult.setText(String.valueOf(peakSignalToNoiseRatio));
            maksymalnaRoznicaResult.setText(String.valueOf(maximumDifference));
            efektywnaLiczbaBitowResult.setText(String.valueOf(effectiveNumberOfBits));

    }

    public void probowanie(Signal signal) {
        List<Data> data = signal.generateDiscreteRepresentation();
        skutecznaWynik.setText("" + signal.rmsValue(data));
        sredniaBezwWynik.setText("" + signal.absMeanValue(data));
        mocSredniaWynik.setText("" + signal.meanPowerValue(data));
        wartoscSredniaWynik.setText("" + signal.meanValue(data));
        wariancjaWynik.setText("" + signal.varianceValue(data));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");
        String przedzial = przedzialHistogramu.getValue();
        int przedzialInt = Integer.parseInt(przedzial);

        Parent chart;
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("wykres po próbkowaniu");

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
        List<Range> histogram = signal.generateHistogram(przedzialInt, data);
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

    public void rekonstrukcja(Signal signal, Signal signal2, Signal signal3) {
        List<Data> data = signal.generateDiscreteRepresentation();
        skutecznaWynik.setText("" + signal.rmsValue(data));
        sredniaBezwWynik.setText("" + signal.absMeanValue(data));
        mocSredniaWynik.setText("" + signal.meanPowerValue(data));
        wartoscSredniaWynik.setText("" + signal.meanValue(data));
        wariancjaWynik.setText("" + signal.varianceValue(data));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("s1 - orginalny spróbkowany sygnał");
        List<Data> data1 = signal.getData();
        for (Data point : data1) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(point.getX(), point.getY());
            Circle circle = new Circle(3);
            circle.setFill(Color.ORANGE);
            dataPoint.setNode(circle);
            series1.getData().add(dataPoint);
        }

        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.getData().add(series1);
        scatterChart.setAnimated(false);
        scatterChart.setLegendVisible(true);

        NumberAxis xAxis2 = new NumberAxis();
        NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Czas");
        yAxis2.setLabel("Wartość");

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("s2 - orginalny sygnał");
        List<Data> data2 = signal2.getData();
        for (Data point : data2) {
            series2.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("s3 - rekonstrukcja sygnału");
        List<Data> data3 = signal3.getData();
        for (Data point : data3) {
            series3.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis2, yAxis2);
        lineChart.getData().addAll(series2, series3);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(true);

        VBox vbox = new VBox(scatterChart, lineChart);
        Scene scene = new Scene(vbox, 800, 600);
        signals.put(tabIndex, signal);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void kwantyzacja(Signal signal, Signal signal2) {
        if (przedzialHistogramu.getValue() == null) {
            showAlert("Błąd", null, "Nie został wybrany przedział histogramu.");
            return;
        }
        List<Data> data = signal.generateDiscreteRepresentation();
        skutecznaWynik.setText("" + signal.rmsValue(data));
        sredniaBezwWynik.setText("" + signal.absMeanValue(data));
        mocSredniaWynik.setText("" + signal.meanPowerValue(data));
        wartoscSredniaWynik.setText("" + signal.meanValue(data));
        wariancjaWynik.setText("" + signal.varianceValue(data));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Czas");
        yAxis.setLabel("Wartość");
        String przedzial = przedzialHistogramu.getValue();
        int przedzialInt = Integer.parseInt(przedzial);

        Parent chart;
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("wykres po próbkowaniu");

        XYChart.Series series1 = new XYChart.Series();
        List<Data> data1 = signal.getData();
        for (Data point : data1) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(point.getX(), point.getY());
            Circle circle = new Circle(3);
            circle.setFill(Color.ORANGE);
            dataPoint.setNode(circle);
            series1.getData().add(dataPoint);
        }
        scatterChart.getData().add(series1);

        XYChart.Series series2 = new XYChart.Series();
        List<Data> data2 = signal2.getData();
        for (Data point : data2) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(point.getX(), point.getY());
            Circle circle = new Circle(3);
            circle.setFill(Color.BLUE);
            dataPoint.setNode(circle);
            series2.getData().add(dataPoint);
        }
        scatterChart.getData().add(series2);
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

    public void calculateSignal(Signal signal) {
        if (przedzialHistogramu.getValue() == null) {
            showAlert("Błąd", null, "Nie został wybrany przedział histogramu.");
            return;
        }
        List<Data> data = signal.generateDiscreteRepresentation();
        skutecznaWynik.setText("" + signal.rmsValue(data));
        sredniaBezwWynik.setText("" + signal.absMeanValue(data));
        mocSredniaWynik.setText("" + signal.meanPowerValue(data));
        wartoscSredniaWynik.setText("" + signal.meanValue(data));
        wariancjaWynik.setText("" + signal.varianceValue(data));
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
        } else {
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle(title);

            XYChart.Series series = new XYChart.Series();
            List<Data> data3 = signal.getData();
            for (Data point : data3) {
                series.getData().add(new XYChart.Data(point.getX(), point.getY()));
            }
            lineChart.getData().add(series);
            lineChart.setAnimated(false);
            lineChart.setCreateSymbols(false);

            chart = lineChart;
        }
        List<Range> histogram = signal.generateHistogram(przedzialInt, data);
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

        List<Data> data = result.generateDiscreteRepresentation();
        skutecznaWynik.setText("" + result.rmsValue(data));
        sredniaBezwWynik.setText("" + result.absMeanValue(data));
        mocSredniaWynik.setText("" + result.meanPowerValue(data));
        wartoscSredniaWynik.setText("" + result.meanValue(data));
        wariancjaWynik.setText("" + result.varianceValue(data));
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
        List<Data> data2 = result.getData();
        for (Data point : data2) {
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);

        chart = lineChart;

        List<Range> histogram = result.generateHistogram(przedzialInt, data);
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
