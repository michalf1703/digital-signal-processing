package com.example.zad1;

import com.example.zad1.simulation.AnimationThread;
import com.example.zad1.simulation.DistanceSensor;
import com.example.zad1.simulation.Environment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;



public class AnimationPanel {
    @FXML
    private TextField czestotliwoscProbkowania, dlugoscBufora, liczbaPomiarowF, jednostkaCzasowa, okresRaportowania, okresSygnaluSondujacego, predkoscPrzedmiotu,predkoscRzeczywista;

    AnimationThread animationThread = new AnimationThread();


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
        predkoscPrzedmiotu.setText("30");
        okresSygnaluSondujacego.setText("1");
        czestotliwoscProbkowania.setText("40");
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
            Environment environment = new Environment(jednostkaCzasu, predkoscRzeczywi, predkoscPrzed, distanceSensor,25);
            animationThread.startAnimation(environment);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void createSignal() {
        //TODO
    }



}
