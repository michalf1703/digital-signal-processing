package com.example.zad1;

import com.example.zad1.simulation.DistanceSensor;
import com.example.zad1.simulation.Environment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;



public class AnimationPanel {
    @FXML
    private TextField czestotliwoscProbkowania;

    @FXML
    private TextField dlugoscBufora;
    @FXML
    private TextField liczbaPomiarowF;

    @FXML
    private TextField jednostkaCzasowa;

    @FXML
    private TextField okresRaportowania;

    @FXML
    private TextField okresSygnaluSondujacego;

    @FXML
    private TextField predkoscRzeczywista;


    @FXML
    private TextField predkoscPrzedmiotu;

    @FXML
    private TextField textFieldResultCalculatedDistance;

    @FXML
    private TextField textFieldResultRealDistance;

    @FXML
    private TextField textFieldResultTimeStamp;



    @FXML
    void onZakonczAnimacje(ActionEvent event) {


    }

    @FXML
    protected void initialize() {
        setSimulationPanel();


    }

    public void setSimulationPanel() {
        liczbaPomiarowF.setText("10");
        jednostkaCzasowa.setText("10");
        predkoscRzeczywista.setText("10");
        predkoscPrzedmiotu.setText("0.5");
        okresSygnaluSondujacego.setText("1");
        czestotliwoscProbkowania.setText("20");
        dlugoscBufora.setText("60");
        okresRaportowania.setText("0.5");
    }
    @FXML
    void startAnimationAction(ActionEvent event) {
        try {
            int liczbaPomiarow = Integer.parseInt(liczbaPomiarowF.getText());
            double jednostkaCzasu = Double.parseDouble(jednostkaCzasowa.getText());
            double predkoscRzeczywi = Double.parseDouble(predkoscRzeczywista.getText()); //d
            double predkoscPrzed = Double.parseDouble(predkoscPrzedmiotu.getText());
            double okresSygnaluSond = Double.parseDouble(okresSygnaluSondujacego.getText()); //d
            double czestotliwoscProbkowa = Double.parseDouble(czestotliwoscProbkowania.getText()); //d

            double dlugoscBuff = Double.parseDouble(dlugoscBufora.getText()); //d
            double okresRaportowani = Double.parseDouble(okresRaportowania.getText()); //d

            DistanceSensor distanceSensor = new DistanceSensor(okresSygnaluSond, czestotliwoscProbkowa, (int) dlugoscBuff, okresRaportowani, predkoscRzeczywi);
            Environment environment = new Environment(jednostkaCzasu, predkoscRzeczywi, predkoscPrzed, distanceSensor, liczbaPomiarow);

            for (int i = 0; i < liczbaPomiarow; i++) {
                environment.step();
                System.out.println("Czas: " + environment.getTimestamp());
                System.out.println("Rzeczywista odległość: " + environment.getItemDistance());
                System.out.println("Obliczona odległość: " + environment.getDistanceSensor().getDistance());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }




}
