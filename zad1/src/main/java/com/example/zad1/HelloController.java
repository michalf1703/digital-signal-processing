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
    private TextField amplitudaF,czasTrwaniaF,okresPodstawowyF,poczatkowyF,wspolczynnikWypelnieniaF;

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
            "sygnał sinusoidalny wyprostowany jednopołówkowo","sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny","sygnał prostokątny symetryczny","sygnał trójkątny","skok jednostkowy",
            "impuls jednostkowy", "szum impulsowy"};

    private String[] opcjeOperacje = {"dodawanie", "odejmowanie", "mnożenie",
            "dzielenie"};
    private String[] opcjePrzedzial = {"5", "10", "15", "20"};

    @FXML
    protected void initialize() {
        rodzajSygnalu.getItems().addAll(opcje);
        wybierzOperacje.getItems().addAll(opcjeOperacje);
        przedzialHistogramu.getItems().addAll(opcjePrzedzial);
    }

}
