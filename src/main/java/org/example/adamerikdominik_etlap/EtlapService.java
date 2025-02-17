package org.example.adamerikdominik_etlap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapService {
    private static final String DB_DRIVER= "mysql";
    private static final String DB_HOST= "localhost";
    private static final String DB_PORT= "3306";
    private static final String DB_DATABASE= "etlapdb";

    private static final String DB_USER= "root";
    private static final String DB_PASSWORD= "";
    private final Connection connection;

    public EtlapService() throws SQLException {
        String DB_URL= String.format("jdbc:%s://%s:%s/%s", DB_DRIVER,DB_HOST,DB_PORT,DB_DATABASE);
        connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }
    public ObservableList<String> getKategoria() throws SQLException {
        ObservableList<String> kategoriak = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        String sql = "SELECT kategoria FROM etlap GROUP BY kategoria";
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            String kategoria = result.getString("kategoria");
            kategoriak.add(kategoria);
        }
        return kategoriak;
    }
    public List<Etelek> getAll() throws SQLException {
        List<Etelek> etlaps = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM etlap";
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            Integer id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            Integer ar = result.getInt("ar");
            String kategoria = result.getString("kategoria");
            etlaps.add(new Etelek(id, nev, leiras, ar, kategoria));
        }
        return etlaps;
    }
    public boolean delete(Etelek etelek) throws SQLException{
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, etelek.getId());
        return statement.executeUpdate()==1;
    }
    public boolean updatePrice(Etelek etelek) throws SQLException{
    String sql = "UPDATE etlap SET ar = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, etelek.getAr());
        statement.setInt(2, etelek.getId());
        return statement.executeUpdate()==1;
    }
    public boolean updateAllPrices(Integer novel) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, novel);
        return statement.executeUpdate()==1;
    }
    public boolean updateAllPricesSzazalek(Integer novel) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + (ar * ? / 100)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, novel);
        return statement.executeUpdate()==1;
    }
    public boolean create(Etelek etelek) throws SQLException {
        String sql = "INSERT INTO etlap(nev,leiras,ar,kategoria) VALUES(?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, etelek.getNev());
        statement.setString(2, etelek.getLeiras());
        statement.setInt(3, etelek.getAr());
        statement.setString(4, etelek.getKategoria());
        return statement.executeUpdate() == 1;
    }
}
