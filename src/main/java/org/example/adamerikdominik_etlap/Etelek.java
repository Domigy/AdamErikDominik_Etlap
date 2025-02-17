package org.example.adamerikdominik_etlap;

public class Etelek {
    private Integer id;
    private String nev;
    private String leiras;
    private Integer ar;
    private String kategoria;

    public Etelek(Integer id, String nev, String leiras, Integer ar, String kategoria) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kategoria = kategoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public Integer getAr() {
        return ar;
    }

    public void setAr(Integer ar) {
        this.ar = ar;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return "Etelek{" +
                "id=" + id +
                ", nev='" + nev + '\'' +
                ", leiras='" + leiras + '\'' +
                ", ar=" + ar +
                ", kategoria='" + kategoria + '\'' +
                '}';
    }
}
