package com.example.zad1;

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

}