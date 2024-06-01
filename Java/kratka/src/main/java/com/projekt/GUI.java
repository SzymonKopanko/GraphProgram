package com.projekt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public class GUI {
    private Graf myGraf = new Graf(this);
    private final ScrollPane panelGrafu;
    private final VBox panelSciezek;
    private final Label konsolaBledow;

    public GUI(Stage stage){

        TextField kolumny, wiersze, wagaod, wagado, trybGeneracji, nazwaPliku;

        Rectangle rect3, margin, margin2,margin3,margin4,margin5;

        Button generuj, zapisz,czytaj;

        HBox hbox, hbox2, hbox3;

        VBox vbox, vbox2,vbox3;

        ScrollPane sp2;

        Label czySpojny, usunSciezki, X;

        hbox = new HBox();
        hbox.setPadding(new Insets(20,20,20,20));

        hbox2 = new HBox();

        panelGrafu = new ScrollPane();
        panelGrafu.setPrefSize(300, 300);
        panelGrafu.setMaxHeight(540);
        panelGrafu.setContent(hbox2);

        vbox = new VBox();
        kolumny = new TextField();
        kolumny.setPromptText("Liczba kolumn");
        wiersze = new TextField();
        wiersze.setPromptText("Liczba wierszy");
        wagaod = new TextField();
        wagaod.setPromptText("Waga od");
        wagado = new TextField();
        wagado.setPromptText("Waga do");
        trybGeneracji = new TextField();
        trybGeneracji.setPromptText("Tryb generacji");
        nazwaPliku = new TextField();
        nazwaPliku.setPromptText("Nazwa pliku");
        //        rect2.setFill(rect.getFill());
        generuj = new Button("Generuj");
        czytaj = new Button("Wczytaj z pliku");
        zapisz = new Button("Zapisz do pliku");
        czySpojny = new Label("");
        margin = new Rectangle(5, 5, Color.TRANSPARENT);
        margin2 = new Rectangle(5, 5, Color.TRANSPARENT);
        margin3 = new Rectangle(5, 5, Color.TRANSPARENT);
        margin4 = new Rectangle(15, 15, Color.TRANSPARENT);
        margin5 = new Rectangle(5, 5, Color.TRANSPARENT);
        vbox.getChildren().addAll(new Label("Kolumny:"),kolumny,new Label("Wiersze:"),wiersze,new Label("Waga od:"),
                wagaod,new Label("Waga do:"),wagado,new Label("Tryb generacji:"),trybGeneracji,margin,generuj,margin2,
                new Label("Nazwa pliku:"),nazwaPliku,margin3,czytaj,margin4,zapisz,margin5,czySpojny);
        vbox.spacingProperty().set(7);
        vbox.setPadding(new Insets(0,20,0,20));

        vbox2 = new VBox();
        panelSciezek = new VBox();
        hbox3 = new HBox();
        usunSciezki = new Label("Usuń wszystkie ścieżki:");
        usunSciezki.setFont(new Font(18));
        X = new Label("X");
        X.setFont(new Font(18));
        X.setTextFill(Color.RED);

        hbox3.getChildren().addAll(usunSciezki,X);
        hbox3.setSpacing(74);
        rect3 = new Rectangle(300, 1, Color.LIGHTGREY);
        hbox3.setPadding(new Insets(0,0,0,13));
        vbox2.getChildren().addAll(hbox3,rect3, panelSciezek);

        sp2 = new ScrollPane();
        sp2.setPrefSize(300, 500);
        sp2.setContent(vbox2);
        sp2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        konsolaBledow = new Label();
        konsolaBledow.setMaxSize(300,50);
        konsolaBledow.setMinSize(300,50);
        konsolaBledow.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));

        vbox3 = new VBox();
        vbox3.setSpacing(20);
        vbox3.getChildren().addAll(sp2,konsolaBledow);

        hbox.getChildren().add(panelGrafu);
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(vbox3);

        Scene scena = new Scene(hbox, 820, 580);
        stage.setScene(scena);
        stage.show();

        X.setOnMouseClicked(usunWszystkieSciezki());

        generuj.setOnAction(czytajDaneGeneratora(kolumny, wiersze, wagaod, wagado, trybGeneracji, czySpojny));

        czytaj.setOnAction(czytajDaneCzytacza(nazwaPliku, czySpojny));

        zapisz.setOnAction(zapiszDoPliku());
    }
    private EventHandler<ActionEvent> zapiszDoPliku() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    myGraf.zapiszPlik(null);
                } catch (IOException ex) {
                    konsolaBledow.setText("Błąd zapisu pliku");
                }
            }
        };
    }

    private EventHandler<ActionEvent> czytajDaneCzytacza(TextField nazwaPliku, Label czySpojny) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    panelSciezek.getChildren().clear();
                    Sciezka.resetKolorow();
                    if (nazwaPliku.getText().length() != 0) {
                        if(myGraf.czytajPlik(nazwaPliku.getText())){
                            myGraf.rysujGraf();
                            if (Algorytmy.grafNieSpojny(myGraf.getGraf()))
                                czySpojny.setText("Graf jest spójny");
                            else
                                czySpojny.setText("Graf jest nie spójny");
                        }
                    }

                } catch (FileNotFoundException ex) {
                    konsolaBledow.setText("Nie znaleziono pliku " + nazwaPliku.getText());
                } catch (IOException ex) {
                    konsolaBledow.setText("Błąd czytania pliku");
                }
            }
        };
    }

    private EventHandler<ActionEvent> czytajDaneGeneratora(TextField kolumny, TextField wiersze, TextField wagaod, TextField wagado, TextField trybGeneracji, Label czySpojny) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                panelSciezek.getChildren().clear();
                Sciezka.resetKolorow();
                try {
                    int nWiersze = Integer.parseInt(wiersze.getText());
                    int nKolumny = Integer.parseInt(kolumny.getText());
                    double nWagaOd = Double.parseDouble(wagaod.getText());
                    double nWagaDo = Double.parseDouble(wagado.getText());
                    int nTrybGeneracji = Integer.parseInt(trybGeneracji.getText());
                    if(nWiersze>400 || nKolumny>400 || nWiersze<1 || nKolumny<1){
                        konsolaBledow.setText("Podano złe wymiary dla generacji grafu");
                    }
                    else if(nWagaOd>nWagaDo || nWagaOd<0){
                        konsolaBledow.setText("Podano zły zakres wag");
                    }
                    else if(nTrybGeneracji>3 || nTrybGeneracji<1){
                        konsolaBledow.setText("Podano nieisniejący tryb generacji");
                    }else{
                        myGraf.generujGraf(nWiersze, nKolumny, nWagaOd, nWagaDo, nTrybGeneracji);
                        myGraf.rysujGraf();
                        if (Algorytmy.grafNieSpojny(myGraf.getGraf())) {
                            czySpojny.setText("Graf jest spójny");
                        } else {
                            czySpojny.setText("Graf jest nie spójny");
                        }
                        konsolaBledow.setText("Wygenerowano graf");
                    }
                } catch (NumberFormatException exception) {
                    konsolaBledow.setText("Podano złe dane do generacji");
                } catch (NoSuchElementException exception) {
                    konsolaBledow.setText("Brakuje danych do generacji");
                } catch (IOException ex) {
                    konsolaBledow.setText("Podano dane do generacji w zły sposób");
                }

            }
        };
    }

    private EventHandler<MouseEvent> usunWszystkieSciezki() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                panelSciezek.getChildren().clear();
                Sciezka.resetKolorow();
                try {
                    myGraf.rysujGraf();
                } catch (IOException e) {
                }
                System.out.println("remove all");
            }
        };
    }

    public ScrollPane getPanelGrafu() {
        return panelGrafu;
    }
    public VBox getPanelSciezek() {
        return panelSciezek;
    }
    public Label getKonsolaBledow(){return konsolaBledow;}
    public void setKonsolaBledow(String komunikat){
        konsolaBledow.setText(komunikat);
    }

}
