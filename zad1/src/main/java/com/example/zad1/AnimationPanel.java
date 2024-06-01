package com.example.zad1;

import com.example.zad1.simulation.AnimationThread;
import com.example.zad1.simulation.DistanceSensor;
import com.example.zad1.simulation.Environment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AnimationPanel {
    @FXML
    private TextField czestotliwoscProbkowania, dlugoscBufora, liczbaPomiarowF, jednostkaCzasowa, okresRaportowania, okresSygnaluSondujacego, predkoscPrzedmiotu, predkoscRzeczywista;

    AnimationThread animationThread = new AnimationThread();


    @FXML
    void onZakonczAnimacje(ActionEvent event) {


    }

    @FXML
    protected void initialize() {
        setSimulationPanel();


    }

    public void setSimulationPanel() {
        liczbaPomiarowF.setText("0");
        jednostkaCzasowa.setText("0.1");
        predkoscRzeczywista.setText("100");
        predkoscPrzedmiotu.setText("0.5");
        okresSygnaluSondujacego.setText("1");
        czestotliwoscProbkowania.setText("20");
        dlugoscBufora.setText("60");
        okresRaportowania.setText("0.5");
    }

    @FXML
    void startAnimationAction(ActionEvent event) {
        try {
            double jednostkaCzasu = Double.parseDouble(jednostkaCzasowa.getText());
            double predkoscRzeczywi = Double.parseDouble(predkoscRzeczywista.getText()); //to bedzie signalVelocity
            double predkoscPrzed = Double.parseDouble(predkoscPrzedmiotu.getText()); //to bedzie itemVelocity
            double okresSygnaluSond = Double.parseDouble(okresSygnaluSondujacego.getText()); //okres
            double czestotliwoscProbkowa = Double.parseDouble(czestotliwoscProbkowania.getText()); //d

            Integer dlugoscBuff = Integer.parseInt(dlugoscBufora.getText()); //d
            double okresRaportowani = Double.parseDouble(okresRaportowania.getText()); //d

            DistanceSensor distanceSensor = new DistanceSensor(okresSygnaluSond, czestotliwoscProbkowa, dlugoscBuff, okresRaportowani, predkoscRzeczywi);
            Environment environment = new Environment(jednostkaCzasu, predkoscRzeczywi, predkoscPrzed, distanceSensor, 0);
            animationThread.startAnimation(environment);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void createSignal() {
        //TODO
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}