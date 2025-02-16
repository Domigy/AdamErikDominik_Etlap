module org.example.adamerikdominik_etlap {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.adamerikdominik_etlap to javafx.fxml;
    exports org.example.adamerikdominik_etlap;
}