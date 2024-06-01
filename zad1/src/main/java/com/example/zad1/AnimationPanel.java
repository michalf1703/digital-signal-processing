package com.example.zad1;

import com.example.zad1.simulation.DistanceSensor;
import com.example.zad1.simulation.Environment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnimationPanel {

    @FXML
    private TableView<Measurement> resultsTable;
    @FXML
    private TableColumn<Measurement, Integer> measurementNumberColumn;
    @FXML
    private TableColumn<Measurement, Double> timeColumn;
    @FXML
    private TableColumn<Measurement, Double> calculatedDistanceColumn;
    @FXML
    private TableColumn<Measurement, Double> realDistanceColumn;
    @FXML
    private TableColumn<Measurement, Double> differenceColumn;
    @FXML
    private TextField czestotliwoscProbkowania, dlugoscBufora, liczbaPomiarowF, jednostkaCzasowa, okresRaportowania, okresSygnaluSondujacego, predkoscPrzedmiotu, predkoscRzeczywista;

    private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    private AnimationThread animationThread = new AnimationThread();

    @FXML
    void onZakonczAnimacje(ActionEvent event) {
        animationThread.stopAnimation();
    }

    @FXML
    protected void initialize() {
        setSimulationPanel();

        measurementNumberColumn.setCellValueFactory(cellData -> cellData.getValue().measurementNumberProperty().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty().asObject());
        calculatedDistanceColumn.setCellValueFactory(cellData -> cellData.getValue().calculatedDistanceProperty().asObject());
        realDistanceColumn.setCellValueFactory(cellData -> cellData.getValue().realDistanceProperty().asObject());
        differenceColumn.setCellValueFactory(cellData -> cellData.getValue().differenceProperty().asObject());
    }

    public void setSimulationPanel() {
        liczbaPomiarowF.setText("10");
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
            int liczbaPomiarow = Integer.parseInt(liczbaPomiarowF.getText());
            double jednostkaCzasu = Double.parseDouble(jednostkaCzasowa.getText());
            double predkoscRzeczywi = Double.parseDouble(predkoscRzeczywista.getText()); //to bedzie signalVelocity
            double predkoscPrzed = Double.parseDouble(predkoscPrzedmiotu.getText()); //to bedzie itemVelocity
            double okresSygnaluSond = Double.parseDouble(okresSygnaluSondujacego.getText()); //okres
            double czestotliwoscProbkowa = Double.parseDouble(czestotliwoscProbkowania.getText()); //d

            Integer dlugoscBuff = Integer.parseInt(dlugoscBufora.getText()); //d
            double okresRaportowani = Double.parseDouble(okresRaportowania.getText()); //d

            DistanceSensor distanceSensor = new DistanceSensor(okresSygnaluSond, czestotliwoscProbkowa, dlugoscBuff, okresRaportowani, predkoscRzeczywi);
            Environment environment = new Environment(jednostkaCzasu, predkoscRzeczywi, predkoscPrzed, distanceSensor, 0);
            animationThread.startAnimation(environment, liczbaPomiarow, resultsTable, measurements);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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

    public class AnimationThread {

        private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);
        private AtomicBoolean isAnimationPaused = new AtomicBoolean(false);

        private Thread thread;
        private int measurementCount = 0;

        public void startAnimation(Environment environment, int measurementsCount, TableView<Measurement> resultsTable, ObservableList<Measurement> measurements) {
            isAnimationRunning.set(true);
            run(environment, measurementsCount);
        }

        public void stopAnimation() {
            isAnimationRunning.set(false);
        }

        private void run(Environment environment, int measurementsCountMAX){
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    double startTime = System.currentTimeMillis() - (long) (environment.getTimeStep() * 1000);
                    while (isAnimationRunning.get() && measurementCount < measurementsCountMAX) {
                        long callTimePeriod = 0;
                        if (!isAnimationPaused.get()) {
                            callTimePeriod += action(() -> environment.step());
                            boolean shouldReport = (System.currentTimeMillis() - startTime) >= environment.getDistanceSensor().getReportTerm() * 1000;
                            callTimePeriod += action(() -> {
                                if (shouldReport) {
                                    double timestamp = Math.round(environment.getTimestamp() * 100.0) / 100.0;
                                    double calculatedDistance = Math.round(environment.getDistanceSensor().getDistance() * 1000.0) / 1000.0;
                                    double realDistance = Math.round(environment.getItemDistance() * 1000.0) / 1000.0;
                                    double difference = Math.round(Math.abs(realDistance - calculatedDistance) * 1000.0) / 1000.0;
                                    Measurement measurement = new Measurement(measurementCount, timestamp, calculatedDistance, realDistance, difference);
                                    Platform.runLater(() -> {
                                        measurements.add(measurement);
                                        resultsTable.setItems(measurements);
                                        measurementCount++;
                                    });
                                }
                            });
                            if (shouldReport) {
                                startTime = System.currentTimeMillis();
                            }
                        }

                        long sleepTime = ((long) (environment.getTimeStep() * 1000)) - callTimePeriod;
                        try {
                            TimeUnit.MILLISECONDS.sleep(sleepTime > 0 ? sleepTime : 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };

            thread = new Thread(task);
            thread.start();
        }

        private long action(Runnable runnable) {
            long startTime = System.currentTimeMillis();
            runnable.run();
            return (System.currentTimeMillis() - startTime);
        }
    }
}