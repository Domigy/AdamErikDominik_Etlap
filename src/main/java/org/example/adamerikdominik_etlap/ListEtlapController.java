package org.example.adamerikdominik_etlap;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ListEtlapController {

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
    private ObservableList<Etelek> selectedItems;
    @FXML
    private Spinner<Integer> szazalekNovelSpinner;
    @FXML
    private Spinner<Integer> osszegNovelSpinner;
    private EtlapService etlapService;

    public void initialize() {
        nameTabeCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoryTableCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        priceTableCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        szazalekNovelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5,50,5,5));
        osszegNovelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50,3000,50,50));
        selectedItems =
                etlapTable.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Etelek>() {
            @Override
            public void onChanged(Change<? extends Etelek> change) {
                leirasTextArea.setText(selectedItems.get(0).getLeiras());
            }
        });
        try {
            etlapService = new EtlapService();
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
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Törlés");
        alert.setHeaderText("Biztos törli?");
        try {
            alert.setContentText(selectedItems.get(0).getNev());
        }catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setContentText("Nincs kiválasztva!");
        }

        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                EtlapService etlapService = new EtlapService();
                etlapService.delete(selectedItems.get(0));
                listEtlap(etlapService);
            }catch (SQLException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Hiba!");
                alert1.setHeaderText("Nem sikerül kapcsolودn az adatbazishoz!");
                alert1.setContentText(e.getMessage());
                alert1.showAndWait();
            }
        }
    }

    @FXML
    public void adButtonClick() {
        try {
            Stage stage= new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(EtlapApplication.class.getResource("add-etel-form.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 340);
            AddEtelFormController controller = fxmlLoader.getController();
            controller.setEtlapService(etlapService);
            stage.setTitle("Új étel hozzáadása!");
            stage.setScene(scene);
            stage.setOnHidden(event-> {
                try {
                    listEtlap(etlapService);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void arNovelButton() throws SQLException {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Emelés");
        alert.setHeaderText("Biztos emeli az árat?");

        if(selectedItems.size() == 0) {
            alert.setContentText("Összes étel árát " + osszegNovelSpinner.getValue() + " Ft-tal!");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                etlapService.updateAllPrices(osszegNovelSpinner.getValue());
                etlapTable.getItems().clear();
                etlapTable.getItems().addAll(etlapService.getAll());
            }
        }
        else {
            alert.setContentText(selectedItems.get(0).getNev() + " árát " + osszegNovelSpinner.getValue() + " Ft-tal!");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                selectedItems.get(0).setAr(selectedItems.get(0).getAr() + osszegNovelSpinner.getValue());
                etlapService.updatePrice(selectedItems.get(0));
                etlapTable.getItems().clear();
                etlapTable.getItems().addAll(etlapService.getAll());
            }
        }
    }

    @FXML
    public void szazalekEmelButton() throws SQLException {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Emelés");
        alert.setHeaderText("Biztos emeli az árat?");
        if(selectedItems.size() == 0) {
            alert.setContentText("Összes étel árát " + szazalekNovelSpinner.getValue() + " %-al!");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                etlapService.updateAllPricesSzazalek(szazalekNovelSpinner.getValue());
                etlapTable.getItems().clear();
                etlapTable.getItems().addAll(etlapService.getAll());
            }
        }
        else {
            alert.setContentText(selectedItems.get(0).getNev() + " árát " + szazalekNovelSpinner.getValue() + " %-al!");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
            selectedItems.get(0).setAr(selectedItems.get(0).getAr() +
                    (selectedItems.get(0).getAr() * szazalekNovelSpinner.getValue() / 100));
            etlapService.updatePrice(selectedItems.get(0));
            etlapTable.getItems().clear();
            etlapTable.getItems().addAll(etlapService.getAll());
            }
        }
    }
}