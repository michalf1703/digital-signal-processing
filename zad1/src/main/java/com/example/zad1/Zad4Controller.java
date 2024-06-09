package com.example.zad1;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.w3c.dom.Text;

public class Zad4Controller {

    @FXML
    private ChoiceBox<String> operacjeZad4;
    private String[] opcjeZad4 = {"Dyskretna transformacja Fouriera z definicji", "Szybka transformacja Fouriera in situ", "Szybka transformacja Fouriera rekurencyjna"
            ,"Transformacja Walsha-Hadamarda z definicji", "Szybka transformacja Walsha-Hadamarda rekurencyjna","Szybka transformacja Walsha-Hadamarda in situ" };

    @FXML
    protected void initialize() {
        operacjeZad4.getItems().addAll(opcjeZad4);
    }
}
