package com.projekt;



import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class Sciezka {
//    private static Color kolorPoczatkowy = Color.color(Math.random(), Math.random(), Math.random());
    private static Color[] listaKolorow = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.ORANGE,
                    Color.PURPLE,Color.AQUA,Color.BROWN,Color.BLUEVIOLET,Color.DARKGREEN,Color.CRIMSON,Color.DARKGRAY,
                    Color.SEAGREEN,Color.FIREBRICK,Color.PALEGREEN,Color.LIMEGREEN,Color.DARKMAGENTA,Color.WHEAT,
                    Color.STEELBLUE,Color.MEDIUMPURPLE,Color.ORCHID};
    private static Sciezka[] tablicaSciezek = new Sciezka[20];
    private final int[] przebiegSciezki;
    private final double[] sciezkaWagi;
    private final VBox panel;
    private final int zrodlo;
    private final int cel;
    private final int kolumny;
    private final double odleglosc;
    private int liczba;
    private final ArrayList<ArrayList<Krawedz>> graf;
    private static int ileJestSciezek=1;
    private Node[][] tablicaGrafu;
    private Color kolor;
    private final int nrSciezki;
    public Sciezka(int zrodlo, int cel, double odleglosc, int[] przebiegSciezki, double[] sciezkaWagi, VBox panel, ArrayList<ArrayList<Krawedz>> graf, int kolumny, Node[][] tablicaGrafu){
        this.kolumny = kolumny;
        this.tablicaGrafu = tablicaGrafu;
        this.graf = graf;
        this.panel = panel;
        this.przebiegSciezki = przebiegSciezki;
        this.sciezkaWagi = sciezkaWagi;
        this.cel = cel;
        this.zrodlo = zrodlo;
        this.odleglosc = odleglosc;
        this.liczba = new Random().nextInt(20);
        this.nrSciezki = ileJestSciezek-1;

        if(ileJestSciezek>20) {
//            System.out.println("max liczba sciezek");
            new Alert(Alert.AlertType.ERROR,"max liczba sciezek").show();
        }
        else {
            while (listaKolorow[liczba] == null)
                this.liczba = new Random().nextInt(20);
            tablicaSciezek[nrSciezki] = this;
            ileJestSciezek++;
//            System.out.println(ileJestSciezek);
            rysujSciezke();
        }
    }
    public static void resetKolorow(){
        listaKolorow = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.ORANGE,
                Color.PURPLE,Color.AQUA,Color.BROWN,Color.BLUEVIOLET,Color.DARKGREEN,Color.CRIMSON,Color.DARKGRAY,
                Color.SEAGREEN,Color.FIREBRICK,Color.PALEGREEN,Color.LIMEGREEN,Color.DARKMAGENTA,Color.WHEAT,
                Color.STEELBLUE,Color.MEDIUMPURPLE,Color.ORCHID};
        ileJestSciezek=1;
    }
    private void rysujSciezke(){
        VBox sciezka = new VBox();
        HBox wyswietlanieProste = new HBox();
        VBox wyswietlanieSzczegolowe = new VBox();
        wyswietlanieSzczegolowe.setMaxWidth(300);
        wyswietlanieSzczegolowe.setPadding(new Insets(0,20,5,10));
        kolor = listaKolorow[liczba];
        listaKolorow[liczba] = null;
        String tekstSzczegolowy = new String("");
        Rectangle kwadratKoloru = new Rectangle(15,15, kolor);
        Label opisKrotki = new Label("Ścieżka z "+zrodlo+" do "+cel+" wynosi: "+ odleglosc);
        opisKrotki.setMaxWidth(230);
        opisKrotki.setMinWidth(230);
        opisKrotki.setFont(new Font(14));
        Label strzalka = new Label("\\/");
        strzalka.setTextFill(Color.BLUE);
        Rectangle dolnaKrawedz = new Rectangle(300,1,Color.LIGHTGREY);
        wyswietlanieProste.getChildren().addAll(kwadratKoloru,opisKrotki,strzalka);
        wyswietlanieProste.setAlignment(Pos.CENTER);
        wyswietlanieProste.setPadding(new Insets(5,20,5,10));
        wyswietlanieProste.setSpacing(5);

        for(int i = 0; i < przebiegSciezki.length-1; i++){
            if(przebiegSciezki[i] != -1){
//                System.out.print(przebiegSciezki[i]+"--("+sciezkaWagi[i+1]+")-->");
                tekstSzczegolowy = tekstSzczegolowy + przebiegSciezki[i]+"--("+sciezkaWagi[i+1]+")-->";
            }
        }
//        System.out.print(przebiegSciezki[przebiegSciezki.length-1]);
        tekstSzczegolowy = "Odległość: " + odleglosc + " " + tekstSzczegolowy + przebiegSciezki[przebiegSciezki.length-1];
        System.out.println(tekstSzczegolowy);
        Label sciezkaSzczegolowa = new Label(tekstSzczegolowy);
        sciezkaSzczegolowa.setWrapText(true);
        sciezka.getChildren().addAll(wyswietlanieProste,wyswietlanieSzczegolowe,dolnaKrawedz);

        strzalka.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(wyswietlanieSzczegolowe.getChildren().isEmpty()) {
                    wyswietlanieSzczegolowe.getChildren().add(sciezkaSzczegolowa);
                    strzalka.setText(">");
                    strzalka.setFont(new Font(17));
                } else {
                    wyswietlanieSzczegolowe.getChildren().remove(sciezkaSzczegolowa);
                    strzalka.setText("\\/");
                    strzalka.setFont(new Font(11));
                }
            }
        });
        sciezka.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                    panel.getChildren().remove(sciezka);
                    wymazanieSciezkiZGrafu();

                    tablicaSciezek[nrSciezki] = null;
                    ileJestSciezek--;
                    for(int k = 0; k < tablicaSciezek.length; k++){
                        if(tablicaSciezek[k] != null){
                            tablicaSciezek[k].zaznaczenieSciezkiNaGrafie(tablicaSciezek[k].kolor);
                        }
                    }
                }
            }
        });

        panel.getChildren().add(sciezka);
        zaznaczenieSciezkiNaGrafie(kolor);
    }
    private void zaznaczenieSciezkiNaGrafie(Color kolor){
        System.out.println("zaznaczenieSciezkiNaGrafie");

        for(int i = 0; i < przebiegSciezki.length-1; i++) {
            if (przebiegSciezki[i] != -1) {
                int kolumnaWierzcholka = przebiegSciezki[i] % kolumny;
                int wierszWierzcholka = (int)przebiegSciezki[i]/kolumny;
                System.out.println(przebiegSciezki[i]-przebiegSciezki[i+1]);
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == -1){
                    //w prawo
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka+1];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka+1] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == 1){
                    //w lewo
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka-1];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka-1] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == kolumny){
                    //w górę
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka-1][2*kolumnaWierzcholka];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka-1][2*kolumnaWierzcholka] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == -kolumny){
                    //w dół
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka+1][2*kolumnaWierzcholka];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka+1][2*kolumnaWierzcholka] = tmp;
                }
                Circle tmp2 = (Circle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka];
                tmp2.setFill(kolor);
                tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka] = tmp2;
            }
        }
        int ostatniWierzcholek = przebiegSciezki[przebiegSciezki.length-1];
        Circle tmp2 = (Circle) tablicaGrafu[2*(int)(ostatniWierzcholek/kolumny)][2*(ostatniWierzcholek % kolumny)];
        tmp2.setFill(kolor);
        tablicaGrafu[2*(int)(ostatniWierzcholek/kolumny)][2*(ostatniWierzcholek % kolumny)] = tmp2;
    }
    private void wymazanieSciezkiZGrafu(){
        System.out.println("wymazanieSciezkiZGrafu");

        for(int i = 0; i < przebiegSciezki.length-1; i++) {
            if (przebiegSciezki[i] != -1) {
                int kolumnaWierzcholka = przebiegSciezki[i] % kolumny;
                int wierszWierzcholka = (int)przebiegSciezki[i]/kolumny;
                System.out.println(przebiegSciezki[i]-przebiegSciezki[i+1]);
                Color kolor = Color.BLUE;
                for(int k = 0; k < graf.get(przebiegSciezki[i]).size(); k++){
                    if(graf.get(przebiegSciezki[i]).get(k).getCel() == przebiegSciezki[i+1]){
                        kolor = graf.get(przebiegSciezki[i]).get(k).getKolorKrawedzi();
                    }
                }
//                for (int k = 0; k < graf.get(numerWezla).size(); k++) {
//                    if (graf.get(numerWezla).get(k).getCel() == numerWezla + 1) {
//                        czyJestPrawaKrawedz = true;
//                        idPrawej = k;
//                        graf.get(numerWezla).get(k).setKolorKrawedzi(wagaOd,wagaDo);
//                    }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == -1){
                    //w prawo
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka+1];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka+1] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == 1){
                    //w lewo
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka-1];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka-1] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == kolumny){
                    //w górę
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka-1][2*kolumnaWierzcholka];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka-1][2*kolumnaWierzcholka] = tmp;
                }
                if(przebiegSciezki[i]-przebiegSciezki[i+1] == -1 * kolumny){
                    //w dół
                    Rectangle tmp = (Rectangle) tablicaGrafu[2*wierszWierzcholka+1][2*kolumnaWierzcholka];
                    tmp.setFill(kolor);
                    tablicaGrafu[2*wierszWierzcholka+1][2*kolumnaWierzcholka] = tmp;
                }
                Circle tmp2 = (Circle) tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka];
                tmp2.setFill(Color.BLACK);
                tablicaGrafu[2*wierszWierzcholka][2*kolumnaWierzcholka] = tmp2;
            }
        }
        int ostatniWierzcholek = przebiegSciezki[przebiegSciezki.length-1];
        Circle tmp2 = (Circle) tablicaGrafu[2*(int)(ostatniWierzcholek/kolumny)][2*(ostatniWierzcholek % kolumny)];
        tmp2.setFill(Color.BLACK);
        tablicaGrafu[2*(int)(ostatniWierzcholek/kolumny)][2*(ostatniWierzcholek % kolumny)] = tmp2;
    }
}
