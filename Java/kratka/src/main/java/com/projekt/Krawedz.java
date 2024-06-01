package com.projekt;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Krawedz{
    private int cel;
    private double waga;
    private Color kolorKrawedzi;

    public Krawedz(int cel, double waga) {
        this.cel = cel;
        this.waga = waga;
    }
    public Krawedz(int cel, double waga, double wagaOd,double wagaDo) {
        this.cel = cel;
        this.waga = waga;
        kolorKrawedzi = Color.color(1-1/((wagaDo-wagaOd)/(waga-wagaOd)),1-1/((wagaDo-wagaOd)/(waga-wagaOd)),1);
    }
    public void setKolorKrawedzi(double wagaOd, double wagaDo){
        kolorKrawedzi = Color.color(1-1/((wagaDo-wagaOd)/(waga-wagaOd)),1-1/((wagaDo-wagaOd)/(waga-wagaOd)),1);
    }
    public Color getKolorKrawedzi(){ return kolorKrawedzi; }
    public double getWaga(){ return this.waga; }
    public int getCel(){ return this.cel; }
    public String toString() {
        return cel + ":"+ waga + " kolor: " + kolorKrawedzi;
    }
}
