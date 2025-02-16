package org.example.adamerikdominik_etlap;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class HelloController {

    @FXML
    private TableView<Etelek> etlapTable;
    @FXML
    private TableColumn<Etelek, Integer> priceTableCol;
    @FXML
    private TableColumn<Etelek, String> nameTabeCol;
    @FXML
    private TableColumn<Etelek, String> kategoryTableCol;
    @FXML
    private TextArea leirasTextArea;

    public void initialize() {
        nameTabeCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoryTableCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        priceTableCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        ObservableList<Etelek> selectedItems =
                etlapTable.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Etelek>() {
            @Override
            public void onChanged(Change<? extends Etelek> change) {
                leirasTextArea.setText(selectedItems.get(0).getLeiras());
            }
        });
        try {
            EtlapService etlapService = new EtlapService();
            listEtlap(etlapService);
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setHeaderText("Nem sikerült kapcsolódni az adatbázishoz!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }
    public void listEtlap(EtlapService etlapService) throws SQLException {
        etlapTable.getItems().clear();
        etlapTable.getItems().addAll(etlapService.getAll());
    }

    @FXML
    public void deleteButtonClick() {
    }

    @FXML
    public void adButtonClick() {
    }
}