package com.example.zad1;

import com.example.zad1.Base.Data;
import com.example.zad1.Base.Range;
import com.example.zad1.Signals.*;
import com.example.zad1.SignalsTask3.*;
import com.example.zad1.window.Blackman;
import com.example.zad1.window.Hamming;
import com.example.zad1.window.Hanning;
import com.example.zad1.window.Rectangular;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AppController {
    @FXML
    private ChoiceBox<String> rodzajSygnalu, wybierzOperacje, przedzialHistogramu;
    @FXML
    private TextField amplitudaF, czasTrwaniaF, okresPodstawowyF, poczatkowyF, wspolczynnikWypelnieniaF, czasSkokuF, czestoscProbkowaniaF, numerProbkiSkokuF, prawdopodobienstwoF, rzadFiltraF, czestotliwoscOdcienciaF;
    @FXML
    private Text skutecznaWynik, sredniaBezwWynik, wariancjaWynik, wartoscSredniaWynik, mocSredniaWynik;
    @FXML
    private Text bladSrednioResult,efektywnaLiczbaBitowResult,maksymalnaRoznicaResult, stosunekSygnalSzumResult, szczytowyStosunekSygnalSzumResult;
    @FXML
    private ChoiceBox<String> wybierzMetodeRekonstrukcjaChoiceBox;
    @FXML
    private ChoiceBox<String> operacjeZad4;

    @FXML
    private Button generuj2;
    @FXML
    private TextField sasiadProbki;

    @FXML
    private ChoiceBox<String> wybierzMetodeKwantyChoiceBox;
    @FXML
    private TextField liczbaPoziomowKwantField;

    @FXML
    private ChoiceBox<String> wybierzOperacjeJednoChoiceBox;
    @FXML
    private Button generujPorownanieBotton;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReader<Signal> signalFileReader;
    private FileReaderWriter<Signal> signalFileReaderWriter;
    @FXML
    private TextField liczbaProbekField;

    @FXML
    private Text poprawnyOdczyt1;
    ADC adc = new ADC();
    DAC dac = new DAC();

    @FXML
    private Text poprawnyOdczyt2;
    @FXML
    private Button wczytajFiltr;
    @FXML
    private ChoiceBox<String> typOkna;

    private String[] opcje = {"szum o rozkładzie jednostajnym", "szum gaussowski", "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo", "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny", "sygnał prostokątny symetryczny", "sygnał trójkątny", "skok jednostkowy",
            "impuls jednostkowy", "szum impulsowy", "filtr dolnoprzepustowy", "filtr górnoprzepustowy", "filtr pasmowy"};

    private String[] opcjeOperacje = {"dodawanie", "odejmowanie", "mnożenie", "dzielenie", "splot", "korelacja"};
    private String[] opcjeJedno = {"próbkowanie", "kwantyzacja", "rekonstrukcja sygnału"};
    private String[] opcjeKwantyzacja = {"kwantyzacja równomierna z obcięciem", "kwantyzacja równomierna z zaokrągleniem"};
    private String [] opcjeRekonstrukcja = {"ekstrapolacja zerowego rzędu", "interpolacja pierwszego rzędu", "rekonstrukcja metodą sinc"};
    private String [] opcjeOkno = {"Okno Prostokątne", "Okno Hamminga", "Okno Hanninga", "Okno Blackmana"};
    private String[] opcjePrzedzial = {"5", "10", "15", "20"};
    private Integer tabIndex = 1;
    private Window stage = new Stage();

    @FXML
    protected void initialize() {
        rodzajSygnalu.getItems().addAll(opcje);
        wybierzOperacje.getItems().addAll(opcjeOperacje);
        wybierzOperacjeJednoChoiceBox.getItems().addAll(opcjeJedno);
        wybierzMetodeKwantyChoiceBox.getItems().addAll(opcjeKwantyzacja);
        wybierzMetodeRekonstrukcjaChoiceBox.getItems().addAll(opcjeRekonstrukcja);
        przedzialHistogramu.getItems().addAll(opcjePrzedzial);
        typOkna.getItems().addAll(opcjeOkno);
        rodzajSygnalu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            checkSignal();
        });
        checkSignal();
        wybierzOperacjeJednoChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("próbkowanie".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(true);
                wybierzMetodeKwantyChoiceBox.setDisable(true);
                liczbaPoziomowKwantField.setDisable(true);
                liczbaProbekField.setDisable(false);
            } else if ("kwantyzacja".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(true);
                wybierzMetodeKwantyChoiceBox.setDisable(false);
                liczbaPoziomowKwantField.setDisable(false);
                liczbaProbekField.setDisable(false);
            } else if ("rekonstrukcja sygnału".equals(newValue)) {
                wybierzMetodeRekonstrukcjaChoiceBox.setDisable(false);
                wybierzMetodeKwantyChoiceBox.setDisable(true);
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
                    setupFields(true, true, false, true, false, false, true, false, false, false, false);
                    break;
                case "sygnał sinusoidalny":
                case "sygnał sinusoidalny wyprostowany jednopołówkowo":
                case "sygnał sinusoidalny wyprostowany dwupołówkowo":
                    setupFields(true, true, true, true, false, false, true, false, false, false, false);
                    break;
                case "sygnał prostokątny":
                case "sygnał prostokątny symetryczny":
                case "sygnał trójkątny":
                    setupFields(true, true, true, true, true, false, true, false, false, false, false);
                    break;
                case "skok jednostkowy":
                    setupFields(true, true, false, true, false, true, true, false,false, false, false);
                    break;
                case "impuls jednostkowy":
                    setupFields(true, true, false, true, false, false, true, true,false, false, false);
                    break;
                case "szum impulsowy":
                    setupFields(true, true, false, true, false, false, true, false,true, false, false);
                    break;
                case "filtr dolnoprzepustowy":
                case "filtr górnoprzepustowy":
                case "filtr pasmowy":
                    setupFields(false, false, false, false, false, false, true, false,false,true,true);
                    break;
                default:
                    setupFields(true, true, true, true, true, true, true, true,true,true,true);
            }
        }
    }

    public void performOneOperation() {
        String operation = wybierzOperacjeJednoChoiceBox.getValue();
        double numberOfSamples = Double.parseDouble(liczbaProbekField.getText());
        if (operation != null) {
            Signal s1 = signals.get(1);
            if(s1 instanceof OperationSignal) {
                s1 = ((OperationSignal) s1).toContinuousSignal();
            }
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
                            saveChartProbka(result7);
                            break;
                        case "interpolacja pierwszego rzędu":
                            DiscreteSignal result8 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            ContinuousSignal result9 = dac.firstOrderHold(result8);
                            rekonstrukcja(result8, s1, result9);
                            saveChartProbka(result9);
                            break;
                        case "rekonstrukcja metodą sinc":
                            int neighbourSamples = Integer.parseInt(sasiadProbki.getText());
                            DiscreteSignal result10 = adc.sampling((ContinuousSignal) s1, numberOfSamples);
                            ContinuousSignal result11 = dac.sincBasic(result10, neighbourSamples);
                            rekonstrukcja(result10, s1, result11);
                            saveChartProbka(result11);
                            break;
                    }
                    break;
            }

        }

    }

    private void setupFields(boolean amplituda, boolean czasTrwania, boolean okresPodstawowy, boolean poczatkowy, boolean wspolczynnikWypelnienia, boolean czasSkoku, boolean czestoscProbkowania, boolean numerProbkiSkoku, boolean prawdopodobienstwo, boolean rzadFiltra, boolean czestotliwoscOdciencia) {
        amplitudaF.setDisable(!amplituda);
        czasTrwaniaF.setDisable(!czasTrwania);
        okresPodstawowyF.setDisable(!okresPodstawowy);
        poczatkowyF.setDisable(!poczatkowy);
        wspolczynnikWypelnieniaF.setDisable(!wspolczynnikWypelnienia);
        czasSkokuF.setDisable(!czasSkoku);
        czestoscProbkowaniaF.setDisable(!czestoscProbkowania);
        numerProbkiSkokuF.setDisable(!numerProbkiSkoku);
        prawdopodobienstwoF.setDisable(!prawdopodobienstwo);
        rzadFiltraF.setDisable(!rzadFiltra);
        czestotliwoscOdcienciaF.setDisable(!czestotliwoscOdciencia);
    }

    public void computeSignals() {
        String signal = rodzajSygnalu.getValue();
        if (signal != null) {
            try {
                Signal s = null;
                double sampleRate = Double.parseDouble(czestoscProbkowaniaF.getText());

                switch (signal) {
                    case "szum o rozkładzie jednostajnym":
                        double amplitude = Double.parseDouble(amplitudaF.getText());
                        double rangeStart = Double.parseDouble(poczatkowyF.getText());
                        double rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        s = new UniformNoise(rangeStart, rangeLength, amplitude, sampleRate);
                        break;
                    case "sygnał sinusoidalny":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        double term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał prostokątny":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        double fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new RectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "szum gaussowski":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        s = new GaussianNoise(rangeStart, rangeLength, amplitude, sampleRate);
                        break;
                    case "sygnał sinusoidalny wyprostowany jednopołówkowo":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new HalfwaveRectifiedSinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał sinusoidalny wyprostowany dwupołówkowo":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        s = new FullwaveRectifiedSinusoidalSignal(rangeStart, rangeLength, amplitude, term, sampleRate);
                        break;
                    case "sygnał prostokątny symetryczny":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new SymmetricRectangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "sygnał trójkątny":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        term = Double.parseDouble(okresPodstawowyF.getText());
                        fulfillment = Double.parseDouble(wspolczynnikWypelnieniaF.getText());
                        s = new TriangularSignal(rangeStart, rangeLength, amplitude, term, fulfillment, sampleRate);
                        break;
                    case "skok jednostkowy":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        double jumpMoment = Double.parseDouble(czasSkokuF.getText());
                        s = new UnitStep(rangeStart, rangeLength, amplitude, jumpMoment, sampleRate);
                        break;
                    case "impuls jednostkowy":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        int jumpSampleNumber = Integer.parseInt(numerProbkiSkokuF.getText());
                        s = new UnitImpulse(rangeStart, rangeLength, sampleRate, amplitude, jumpSampleNumber);
                        break;
                    case "szum impulsowy":
                        amplitude = Double.parseDouble(amplitudaF.getText());
                        rangeStart = Double.parseDouble(poczatkowyF.getText());
                        rangeLength = Double.parseDouble(czasTrwaniaF.getText());
                        double probability = Double.parseDouble(prawdopodobienstwoF.getText());
                        s = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude, probability);
                        break;
                    case "filtr dolnoprzepustowy":
                        int filterOrder = Integer.parseInt(rzadFiltraF.getText());
                        double cuttingFrequency = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                        Hamming windowH = new Hamming(filterOrder);
                        Hanning windowHann = new Hanning(filterOrder);
                        Blackman windowB = new Blackman(filterOrder);
                        if (typOkna.getValue() != null) {
                            String windowType = typOkna.getValue();
                            if ("Okno Prostokątne".equals(windowType)) {
                                Rectangular window = new Rectangular();
                                LowPassFilter filter = new LowPassFilter(filterOrder,cuttingFrequency, sampleRate , window);
                                s = new LowPassFilter(filterOrder,cuttingFrequency, sampleRate , window);

                            }
                            if ("Okno Hamminga".equals(windowType)) {
                                s = new LowPassFilter(filterOrder,cuttingFrequency, sampleRate , windowH);

                            }
                            if ("Okno Hanninga".equals(windowType)) {
                                s = new LowPassFilter(filterOrder,cuttingFrequency, sampleRate , windowHann);

                            }
                            if ("Okno Blackmana".equals(windowType)) {
                                s = new LowPassFilter(filterOrder,cuttingFrequency, sampleRate , windowB);
                            }

                        } else {
                            throw new NullPointerException();
                        }
                        break;
                    case "filtr górnoprzepustowy":
                        if (typOkna.getValue() != null) {
                            String windowType = typOkna.getValue();
                            if ("Okno Prostokątne".equals(windowType)) {
                                Rectangular window = new Rectangular();
                                int filterOrder1 = Integer.parseInt(rzadFiltraF.getText());
                                double cuttingFrequency1 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                s = new HighPassFilter(filterOrder1,cuttingFrequency1, sampleRate , window);
                            }
                            if ("Okno Hamminga".equals(windowType)) {
                                int filterOrder2 = Integer.parseInt(rzadFiltraF.getText());
                                double cuttingFrequency2 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                Hamming windowH1 = new Hamming(filterOrder2);
                                s = new HighPassFilter(filterOrder2,cuttingFrequency2, sampleRate , windowH1);

                            }
                            if ("Okno Hanninga".equals(windowType)) {
                                int filterOrder3 = Integer.parseInt(rzadFiltraF.getText());
                                double cuttingFrequency3 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                Hanning windowHann1 = new Hanning(filterOrder3);
                                s = new HighPassFilter(filterOrder3,cuttingFrequency3, sampleRate , windowHann1);

                            }
                            if ("Okno Blackmana".equals(windowType)) {
                                int filterOrder4 = Integer.parseInt(rzadFiltraF.getText());
                                double cuttingFrequency4 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                Blackman windowB1 = new Blackman(filterOrder4);
                                s = new HighPassFilter(filterOrder4,cuttingFrequency4, sampleRate , windowB1);
                            }

                        } else {
                            throw new NullPointerException();
                        }
                        break;
                        case "filtr pasmowy":
                            if (typOkna.getValue() != null) {
                                String windowType = typOkna.getValue();
                                if ("Okno Prostokątne".equals(windowType)) {
                                    Rectangular window = new Rectangular();
                                    int filterOrder1 = Integer.parseInt(rzadFiltraF.getText());
                                    double cuttingFrequency1 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                    s = new BandPassFilter(filterOrder1,cuttingFrequency1, sampleRate , window);

                                }
                                if ("Okno Hamminga".equals(windowType)) {
                                    int filterOrder2 = Integer.parseInt(rzadFiltraF.getText());
                                    double cuttingFrequency2 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                    Hamming windowH1 = new Hamming(filterOrder2);
                                    s = new BandPassFilter(filterOrder2,cuttingFrequency2, sampleRate , windowH1);
                                }
                                if ("Okno Hanninga".equals(windowType)) {
                                    int filterOrder3 = Integer.parseInt(rzadFiltraF.getText());
                                    double cuttingFrequency3 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                    Hanning windowHann1 = new Hanning(filterOrder3);
                                    s = new BandPassFilter(filterOrder3,cuttingFrequency3, sampleRate , windowHann1);
                                }
                                if ("Okno Blackmana".equals(windowType)) {
                                    int filterOrder4 = Integer.parseInt(rzadFiltraF.getText());
                                    double cuttingFrequency4 = Double.parseDouble(czestotliwoscOdcienciaF.getText());
                                    Blackman windowB1 = new Blackman(filterOrder4);
                                    s = new BandPassFilter(filterOrder4,cuttingFrequency4, sampleRate , windowB1);
                                }

                            } else {
                                throw new NullPointerException();
                            }
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
    public void saveChartProbka(Signal signal) {
        try {
            signalFileReader = new FileReader<>(new FileChooser()
                    .showSaveDialog(stage)
                    .getName());
            signalFileReader.write(signal);
        }
        catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd", null, "Nie zapisano sygnału.");
        }
    }
    public void saveChart2(Signal signal) {
        try {
                signalFileReaderWriter = new FileReaderWriter<>(
                        new FileChooser().showSaveDialog(stage)
                                .getName());
                signalFileReaderWriter.write(signal);
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd", null, "Nie zapisano sygnału.");
        }
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
            JOptionPane.showMessageDialog(null, "Wczytywanie sygnału się powiodło. Sukces!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException | FileOperationException e) {
            e.printStackTrace();
            showAlert("Błąd!", null, "Wczytywanie sygnału się nie powiodło.");
        }
        tabIndex++;
    }

    public void performOperations() {
        String operation = wybierzOperacje.getValue();
        if (operation != null) {
            Signal s1 = signals.get(1);
            Signal s2 = signals.get(2);
            Signal result = null;
            switch (operation) {
                case "dodawanie":
                    if (!haveSameSampleRate()) {
                        showAlert("Błąd", null, "Nie można wykonać operacji na sygnałach o różnych częstotliwościach próbkowania.");
                        return;
                    }
                    result = new OperationSignal(s1, s2,"dodawanie");
                    saveChart2(result);
                    break;
                case "odejmowanie":
                    if (!haveSameSampleRate()) {
                        showAlert("Błąd", null, "Nie można wykonać operacji na sygnałach o różnych częstotliwościach próbkowania.");
                        return;
                    }
                    result = new OperationSignal(s1, s2, "odejmowanie");
                    saveChart2(result);
                    break;
                case "mnożenie":
                    if (!haveSameSampleRate()) {
                        showAlert("Błąd", null, "Nie można wykonać operacji na sygnałach o różnych częstotliwościach próbkowania.");
                        return;
                    }
                    result = new OperationSignal(s1, s2, "mnożenie");
                    saveChart2(result);
                    break;
                case "dzielenie":
                    if (!haveSameSampleRate()) {
                        showAlert("Błąd", null, "Nie można wykonać operacji na sygnałach o różnych częstotliwościach próbkowania.");
                        return;
                    }
                    result = new OperationSignal(s1, s2, "dzielenie");
                    saveChart2(result);
                    break;
                    case "splot":
                        result = new ConvolutionSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
                        break;
                    case "korelacja":
                        result = new CorrelationSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
                        break;
            }
            calculateSignal(result);
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
        List<Data> data1 = signal.generateDiscreteRepresentation();
        List<Data> data2 = signal2.getData();

        NumberAxis xAxis1 = new NumberAxis();
        NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Czas");
        yAxis1.setLabel("Wartość");

        ScatterChart<Number, Number> scatterChart1 = new ScatterChart<>(xAxis1, yAxis1);
        scatterChart1.setTitle("Oryginalny spróbkowany sygnał");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Oryginalny spróbkowany sygnał");
        for (Data point : data1) {
            series1.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        scatterChart1.getData().add(series1);

        NumberAxis xAxis2 = new NumberAxis();
        NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Czas");
        yAxis2.setLabel("Wartość");

        LineChart<Number, Number> lineChart2 = new LineChart<>(xAxis2, yAxis2);
        lineChart2.setTitle("Sygnał po kwantyzacji");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Sygnał po kwantyzacji");

        double previousY = 0;
        for (Data point : data2) {
            double currentY = point.getY();
            double currentX = point.getX();
            series2.getData().add(new XYChart.Data<>(currentX, previousY));
            series2.getData().add(new XYChart.Data<>(currentX, currentY));
            previousY = currentY;
        }
        lineChart2.getData().add(series2);
        lineChart2.setCreateSymbols(false);

        VBox vbox = new VBox(scatterChart1, lineChart2);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 800, 600);
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
        xAxis.setLabel("Indeks próbki");
        yAxis.setLabel("Wartość");

        String przedzial = przedzialHistogramu.getValue();
        int przedzialInt = Integer.parseInt(przedzial);

        Parent chart;
        String title = signal.getName();

        if (title.equals("szum impulsowy") || title.equals("impuls jednostkowy") || title.equals("filtr dolnoprzepustowy") || title.equals("filtr górnoprzepustowy") || title.equals("filtr pasmowy") || title.equals("Wynik operacji splotu") || title.equals("Wynik operacji korelacji")) {
            ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
            scatterChart.setTitle(title);

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
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

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            List<Data> data3 = signal.getData();
            for (Data point : data3) {
                series.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
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

    @FXML
    void onWczytajFiltr() {

    }

    @FXML
    void przejdzDoPaneluSymulacji(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation_panel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void przejdzDoPaneluZad4(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("panel-zad4.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void generateS1(ActionEvent event) {
        double rangeStart = 0.0;
        double rangeLength = 8.0;
        double sampleRate = 300.0;

        S1Generator S1 = new S1Generator(rangeStart, rangeLength, sampleRate);
        saveChart2(S1);
        calculateSignal(S1);
    }


    @FXML
    void generateS2(ActionEvent event) {
        double rangeStart = 0.0;
        double rangeLength = 8.0;
        double sampleRate = 300.0;

        S2Generator S2 = new S2Generator(rangeStart, rangeLength, sampleRate);
        saveChart2(S2);
        calculateSignal(S2);
    }


    @FXML
    void generateS3(ActionEvent event) {
        double rangeStart = 0.0;
        double rangeLength = 8.0;
        double sampleRate = 100.0;

        S3Generator S3 = new S3Generator(rangeStart, rangeLength, sampleRate);
        saveChart2(S3);
        calculateSignal(S3);
    }


    // ----------------------------------------------------ZAD4----------------------------------------------------

}
