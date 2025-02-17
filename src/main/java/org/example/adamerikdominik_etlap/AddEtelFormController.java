package org.example.adamerikdominik_etlap;


import javafx.application.Platform;
import javafx.scene.control.*;

import java.sql.SQLException;


public class AddEtelFormController {
    @javafx.fxml.FXML
    private TextArea etelLeirasTextField;
    @javafx.fxml.FXML
    private TextField etelNevTextField;
    @javafx.fxml.FXML
    private Spinner<Integer> etelArSpinner;
    private EtlapService etlapService;
    @javafx.fxml.FXML
    private ComboBox<String> kategoriaCBox;

    public void setEtlapService(EtlapService etlapService) {
        this.etlapService = etlapService;
        try {
            kategoriaCBox.setItems(etlapService.getKategoria());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(){
        etelArSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 100, 100));
    }


    @javafx.fxml.FXML
    public void addEtelButton() {
        String nev = etelNevTextField.getText();
        String leiras = etelLeirasTextField.getText();
        Integer ar = etelArSpinner.getValue();
        String kategoria = kategoriaCBox.getValue();
        Etelek etelek = new Etelek(null, nev, leiras, ar, kategoria);
        try {
            etlapService.create(etelek);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sikeres felvétel!");
            alert.showAndWait();
            etelNevTextField.clear();
            etelLeirasTextField.clear();
            etelArSpinner.getEditor().clear();
            kategoriaCBox.setValue(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setHeaderText("Hiba történt a felvétel során!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
