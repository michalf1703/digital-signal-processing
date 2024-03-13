package com.example.zad1;

import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import com.example.zad1.Signals.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private ChoiceBox<String> rodzajSygnalu,wybierzOperacje, przedzialHistogramu;
    @FXML
    private TextField amplitudaF, czasTrwaniaF, okresPodstawowyF, poczatkowyF, wspolczynnikWypelnieniaF, okresF, czasSkokuF, czestoscProbkowaniaF,numerProbkiSkokuF,prawdopodobienstwoF;

    private boolean isChartGenerated = false;

    @FXML
    private Button wczytajWykres,zapiszWykres;

    @FXML
    private Text skutecznaWynik,sredniaBezwWynik,wariancjaWynik,wartoscSredniaWynik,mocSredniaWynik;



    private String[] opcje = {"szum o rozkładzie jednostajnym", "szum gaussowski", "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo", "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny", "sygnał prostokątny symetryczny", "sygnał trójkątny", "skok jednostkowy",
            "impuls jednostkowy", "szum impulsowy"};

    private String[] opcjeOperacje = {"dodawanie", "odejmowanie", "mnożenie", "dzielenie"};
    private String[] opcjePrzedzial = {"5", "10", "15", "20"};

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


    private boolean isChartGenerated() {
        return isChartGenerated;
    }

    private void setChartGenerated(boolean generated) {
        this.isChartGenerated = generated;
    }
    void checkSignal() {
        String signal = rodzajSygnalu.getValue();
        if (signal != null) {
            if (signal.equals("szum o rozkładzie jednostajnym")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(true);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("szum gaussowski")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(true);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał sinusoidalny")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał sinusoidalny wyprostowany jednopołówkowo")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał sinusoidalny wyprostowany dwupołówkowo")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał prostokątny")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(false);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał prostokątny symetryczny")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(false);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("sygnał trójkątny")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(false);
                wspolczynnikWypelnieniaF.setDisable(false);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("skok jednostkowy")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(true);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(false);
                czestoscProbkowaniaF.setDisable(true);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("impuls jednostkowy")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(true);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(false);
                numerProbkiSkokuF.setDisable(false);
                prawdopodobienstwoF.setDisable(true);
            } else if (signal.equals("szum impulsowy")) {
                amplitudaF.setDisable(false);
                poczatkowyF.setDisable(false);
                czasTrwaniaF.setDisable(false);
                okresPodstawowyF.setDisable(true);
                wspolczynnikWypelnieniaF.setDisable(true);
                czasSkokuF.setDisable(true);
                czestoscProbkowaniaF.setDisable(false);
                numerProbkiSkokuF.setDisable(true);
                prawdopodobienstwoF.setDisable(false);
            }
        }
    }
    public void computeSignals() {
        String signal = rodzajSygnalu.getValue();
        if (signal != null) {
            Double jumpMoment = null;
            try {
                Signal s = null;
                double amplitude = Double.parseDouble(amplitudaF.getText());
                double rangeStart = Double.parseDouble(poczatkowyF.getText());
                double rangeLength = Double.parseDouble(czasTrwaniaF.getText());

                if (signal.equals("szum o rozkładzie jednostajnym")) {
                    s = new UniformNoise(rangeStart, rangeLength, amplitude);
                }
                if (signal.equals("sygnał sinusoidalny")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term);
                }
                if (signal.equals("sygnał prostokątny")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("szum gaussowski")) {
                    s = new GaussianNoise(rangeStart, rangeLength, amplitude);
                }
                if (signal.equals("sygnał sinusoidalny wyprostowany jednopołówkowo")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new HalfwaveRectifiedSinusoidalSignal(rangeStart, rangeLength, amplitude,term);
                }
                if (signal.equals("sygnał sinusoidalny wyprostowany dwupołówkowo")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new FullwaveRectifiedSinusoidalSignal(rangeStart, rangeLength,amplitude, term);
                }
                if (signal.equals("sygnał prostokątny")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("sygnał prostokątny symetryczny")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new SymmetricRectangularSignal(rangeStart, rangeLength,amplitude, term, fulfillment);
                }
                if (signal.equals("sygnał trójkątny")) {
                    double term = Double.parseDouble(okresPodstawowyF.getText());
                    double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new TriangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("skok jednostkowy")) {
                    jumpMoment = Double.parseDouble(czasSkokuF.getText());
                    s = new UnitStep(rangeStart, rangeLength, amplitude, jumpMoment);
                }
                if (signal.equals("impuls jednostkowy")) {

                    int jumpSampleNumber = Integer.parseInt(numerProbkiSkokuF.getText());
                    double sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());
                    s = new UnitImpulse(rangeStart, rangeLength,sampleRate, amplitude, jumpSampleNumber);
                }
                if (signal.equals("szum impulsowy")) {
                    double probability = Double.parseDouble(prawdopodobienstwoF.getText());
                    double sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());
                    s = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude, probability);
                }
                calculateSignal(s);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    public void calculateSignal(Signal signal) {
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
        String title = rodzajSygnalu.getValue();

        if (title == "szum impulsowy" || title == "impuls jednostkowy") {
            ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
            scatterChart.setTitle(title);

            XYChart.Series series = new XYChart.Series();
            List<Data> selectedData = selectDataPoints(signal.getData(), 1000);
            for (Data data : selectedData) {
                series.getData().add(new XYChart.Data(data.getX(), data.getY()));
            }
            scatterChart.getData().add(series);
            scatterChart.setAnimated(false);
            scatterChart.setLegendVisible(false);

            chart = scatterChart;
        } else {
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle(title);

            XYChart.Series series = new XYChart.Series();
            List<Data> selectedData = selectDataPoints(signal.getData(), 1000);
            for (Data data : selectedData) {
                series.getData().add(new XYChart.Data(data.getX(), data.getY()));
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

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    private List<Data> selectDataPoints(List<Data> allData, int maxPoints) {
        List<Data> selectedData = new ArrayList<>();
        int step = Math.max(1, allData.size() / maxPoints);
        for (int i = 0; i < allData.size(); i += step) {
            selectedData.add(allData.get(i));
        }
        return selectedData;
    }




}