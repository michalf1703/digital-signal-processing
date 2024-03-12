package com.example.zad1;

import com.example.zad1.Signals.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ChoiceBox<String> rodzajSygnalu;
    @FXML
    private TextField amplitudaF, czasTrwaniaF, okresPodstawowyF, poczatkowyF, wspolczynnikWypelnieniaF, okresF;
    @FXML
    private TextField czasSkokuF;
    @FXML
    private TextField czestoscProbkowaniaF;
    @FXML
    private TextField numerProbkiSkokuF;
    @FXML
    private TextField prawdopodobienstwoF;

    @FXML
    private ChoiceBox<String> wybierzOperacje;
    @FXML
    private ChoiceBox<String> przedzialHistogramu;
    @FXML
    private Button wczytajWykres;
    @FXML
    private Text skutecznaSygnalu;

    @FXML
    private Text sredniaBezwzglednaSygnalu;
    @FXML
    private Text mocSredniaSygnalu;

    @FXML
    private Text sredniaSygnalu;

    @FXML
    private Text wariancja;
    @FXML
    private Button zapiszWykres;
    @FXML
    private Text skutecznaWynik;

    @FXML
    private Text sredniaBezwWynik;

    @FXML
    private Text wariancjaWynik;

    @FXML
    private Text wartoscSredniaWynik;
    @FXML
    private Text mocSredniaWynik;

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
            Double amplitude = null;
            Double rangeStart = null;
            Double rangeLength = null;
            Double term = null;
            Double fulfillment = null;
            Double jumpMoment = null;
            Double probability = null;
            Double sampleRate = null;
            
            try {
                Signal s = null;
                amplitude = Double.parseDouble(amplitudaF.getText());
                rangeStart = Double.parseDouble(poczatkowyF.getText());
                rangeLength = Double.parseDouble(czasTrwaniaF.getText());

                if (signal.equals("szum o rozkładzie jednostajnym")) {
                    s = new UniformNoise(rangeStart, rangeLength, amplitude);
                }
                if (signal.equals("sygnał sinusoidalny")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term);
                }
                if (signal.equals("sygnał prostokątny")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("szum gaussowski")) {
                    s = new GaussianNoise(rangeStart, rangeLength, amplitude);
                }
                if (signal.equals("sygnał sinusoidalny wyprostowany jednopołówkowo")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new HalfwaveRectifiedSinusoidalSignal(amplitude,rangeStart, rangeLength, term);
                }
                if (signal.equals("sygnał sinusoidalny wyprostowany dwupołówkowo")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    s = new FullwaveRectifiedSinusoidalSignal(amplitude,rangeStart, rangeLength, term);
                }
                if (signal.equals("sygnał prostokątny")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("sygnał prostokątny symetryczny")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new SymmetricRectangularSignal(amplitude,rangeStart, rangeLength, term, fulfillment);
                }
                if (signal.equals("sygnał trójkątny")) {
                    term = Double.parseDouble(okresPodstawowyF.getText());
                    fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                    s = new TriangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment);
                }
                if (signal.equals("skok jednostkowy")) {
                    jumpMoment = Double.parseDouble(czasSkokuF.getText());
                    s = new UnitStep(rangeStart, rangeLength, amplitude, jumpMoment);
                }
                if (signal.equals("impuls jednostkowy")) {
                    jumpMoment = Double.parseDouble(czasSkokuF.getText());
                    sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());
                    s = new UnitImpulse(rangeStart, rangeLength,sampleRate, amplitude, jumpMoment.intValue());
                }
                if (signal.equals("szum impulsowy")) {
                    probability = Double.parseDouble(prawdopodobienstwoF.getText());
                    sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());
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

    }

}