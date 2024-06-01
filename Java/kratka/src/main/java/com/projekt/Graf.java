package com.projekt;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Graf {
    private ArrayList<ArrayList<Krawedz>> graf = new ArrayList<>();
    private Node[][] tablicaGrafu = null;
    private int wiersze;
    private int kolumny;
    private int klikniety=-1;
    private double wagaDo=0, wagaOd=Double.MAX_VALUE;
    private final GUI ekran;
    private final static int DLUGOSC_WAGI=60;
    public Graf(GUI ekran){
        this.ekran = ekran;
    }
    public boolean czytajPlik(String nazwaPliku) throws IOException {
        wagaDo=0;
        wagaOd=Double.MAX_VALUE;
        File plikDoSciezkiPliku = new File(nazwaPliku);
        String sciezka = plikDoSciezkiPliku.getAbsolutePath();
        File plik = new File(sciezka);
        Scanner skanerPliku = new Scanner(plik);
        int wiersze = skanerPliku.nextInt();
        int kolumny = skanerPliku.nextInt();
        this.wiersze = wiersze;
        this.kolumny = kolumny;
        skanerPliku.nextLine();
        int i = 0;
        int j;
        String liniaTekstu;
        ArrayList<String []> tmpDaneSasiada = new ArrayList();
        String [] tmpTablica;
        int tmpCel;
        double tmpWaga;
        graf.clear();
        Krawedz tmpKrawedz;
        while(skanerPliku.hasNext()) {
            liniaTekstu = skanerPliku.nextLine();
            tmpDaneSasiada.add(liniaTekstu.split(("  ")));
            if(tmpDaneSasiada.get(i).length>4){
                return false;
            }
            graf.add(new ArrayList<>());
            j = 0;
            while (j < tmpDaneSasiada.get(i).length) {
                tmpTablica = tmpDaneSasiada.get(i)[j].split(":");
                if(tmpTablica.length!=2){
                    if(!tmpTablica[0].isEmpty()){
                        return false;
                    }
                    j=tmpDaneSasiada.get(i).length;
                }
                else{
                    tmpTablica[0] = tmpTablica[0].replaceAll(" ", "").replaceAll("\t", "");
                    tmpTablica[1] = tmpTablica[1].replaceAll(" ", "").replaceAll("\t", "");
                    try{
                        tmpCel = Integer.parseInt(tmpTablica[0]);
                        tmpWaga = Double.parseDouble(tmpTablica[1]);
                        if((tmpCel != i+1 && tmpCel != i+kolumny && tmpCel != i-1 && tmpCel != i-kolumny) || tmpCel>wiersze*kolumny-1 || tmpCel<0 || tmpWaga<0 || tmpTablica[1].length()>DLUGOSC_WAGI){
                            return false;
                        }
                        tmpKrawedz = new Krawedz(tmpCel, tmpWaga);
                        if(tmpWaga > wagaDo)
                            wagaDo = tmpWaga;
                        if(tmpWaga < wagaOd)
                            wagaOd = tmpWaga;
                        graf.get(i).add(j, tmpKrawedz);
                        j++;
                    } catch (NumberFormatException exception){
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }
    public void rysujGraf() throws IOException {
        GridPane narysowanyGraf = new GridPane();
        narysowanyGraf.setPadding(new Insets(20,20,20,20));
        int i,j,numerWezla = 0,skala=1;
        for(i = 0; i < wiersze; i++) {
            for(j = 0; j < kolumny; j++) {
                //generacja wezla
                Circle kolo = new Circle(15*skala, Color.BLACK);
                kolo.setOnMouseClicked(sluchaczNaKropkach(numerWezla));
                narysowanyGraf.add(kolo, j*2, i*2);

                //generacja krawedzi poziomych
                boolean czyJestPrawaKrawedz = false;
                boolean czyJestDolnaKrawedz = false;
                int idPrawej = 0;
                int idDolnej = 0;

                for (int k = 0; k < graf.get(numerWezla).size(); k++) {
                    if (graf.get(numerWezla).get(k).getCel() == numerWezla + 1) {
                        czyJestPrawaKrawedz = true;
                        idPrawej = k;
                        graf.get(numerWezla).get(k).setKolorKrawedzi(wagaOd,wagaDo);
                        for (int z = 0; z < graf.get(numerWezla + 1).size(); z++) {
                            if (graf.get(numerWezla + 1).get(z).getCel() == numerWezla) {
                                graf.get(numerWezla + 1).get(z).setKolorKrawedzi(wagaOd, wagaDo);
                            }
                        }
                    }
                    if (graf.get(numerWezla).get(k).getCel() == numerWezla + kolumny) {
                        czyJestDolnaKrawedz = true;
                        idDolnej = k;
                        graf.get(numerWezla).get(k).setKolorKrawedzi(wagaOd,wagaDo);
                        for (int z = 0; z < graf.get(numerWezla + kolumny).size(); z++) {
                            if (graf.get(numerWezla + kolumny).get(z).getCel() == numerWezla) {
                                graf.get(numerWezla + kolumny).get(z).setKolorKrawedzi(wagaOd, wagaDo);
                            }
                        }
                    }
                }
                if(j+1<kolumny && czyJestPrawaKrawedz) {
                    Rectangle poziomaKrawedz = new Rectangle(15*skala, 5*skala, graf.get(numerWezla).get(idPrawej).getKolorKrawedzi());
                    narysowanyGraf.add(poziomaKrawedz, j * 2 + 1, i * 2);
                }

                //generacja krawedzi pionowych
                if(i+1<wiersze && czyJestDolnaKrawedz) {
                    Rectangle pionowaKrawedz = new Rectangle(5*skala, 15*skala, graf.get(numerWezla).get(idDolnej).getKolorKrawedzi());
                    GridPane.setHalignment(pionowaKrawedz, HPos.CENTER);
                    narysowanyGraf.add(pionowaKrawedz, j * 2, i * 2 + 1);
                }
                numerWezla++;
            }
        }
        rzutowanieGridaNaTablice(narysowanyGraf);
//        narysowanyGraf.setGridLinesVisible(true);
        ekran.getPanelGrafu().setContent(narysowanyGraf);

    }
    private EventHandler<MouseEvent> sluchaczNaKropkach(int numerWezla) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(klikniety == -1) {
                    klikniety = numerWezla;
                } else {
//                            System.out.println("\ndroga z: " + klikniety +" do: "+ finalNumerWezla);
                    Algorytmy.szukajSciezki(graf, klikniety,numerWezla,ekran.getPanelSciezek(),kolumny,tablicaGrafu,ekran.getKonsolaBledow());
                    klikniety = -1;
                }
            }

        };
    }
    private void rzutowanieGridaNaTablice(GridPane narysowanyGraf)
    {
        tablicaGrafu = new Node[2 * wiersze][2 * kolumny];
        for(int i = 0; i < narysowanyGraf.getChildren().size(); i++)
        {
            Node krawedz = narysowanyGraf.getChildren().get(i);
            this.tablicaGrafu[GridPane.getRowIndex(krawedz)][GridPane.getColumnIndex(krawedz)] = krawedz;
//            System.out.println("rr");
        }
    }

    public void generujGraf(int wiersze, int kolumny, double wagaOd, double wagaDo, int trybGeneracji){
        int i=0;
        losujCzyJestSasiad(trybGeneracji);
        losujWage(wagaOd, wagaDo);
        this.wiersze = wiersze;
        this.kolumny = kolumny;
        this.wagaDo = wagaDo;
        this.wagaOd = wagaOd;
        graf.clear();
        while(i<wiersze*kolumny){
            graf.add(new ArrayList<>());
            i++;
        }
        i=0;
        double tmpWaga;
        Krawedz tmpKrawedz;
        while(i<wiersze*kolumny-1){
            if(i%kolumny==kolumny-1){
                if(losujCzyJestSasiad(trybGeneracji)==1){
                    tmpWaga = losujWage(wagaOd, wagaDo);
                    tmpKrawedz = new Krawedz(i+kolumny, tmpWaga);
                    graf.get(i).add(tmpKrawedz);
                    tmpKrawedz = new Krawedz(i, tmpWaga);
                    graf.get(i+kolumny).add(tmpKrawedz);
                }
            }
            else if (i>=(wiersze-1) * kolumny) {
                if(losujCzyJestSasiad(trybGeneracji)==1){
                    tmpWaga = losujWage(wagaOd, wagaDo);
                    tmpKrawedz = new Krawedz(i+1, tmpWaga);
                    graf.get(i).add(tmpKrawedz);
                    tmpKrawedz = new Krawedz(i, tmpWaga);
                    graf.get(i+1).add(tmpKrawedz);
                }
            }
            else{
                if(losujCzyJestSasiad(trybGeneracji)==1){
                    tmpWaga = losujWage(wagaOd, wagaDo);
                    tmpKrawedz = new Krawedz(i+1, tmpWaga);
                    graf.get(i).add(tmpKrawedz);
                    tmpKrawedz = new Krawedz(i, tmpWaga);
                    graf.get(i+1).add(tmpKrawedz);
                }
                if(losujCzyJestSasiad(trybGeneracji)==1){
                    tmpWaga = losujWage(wagaOd, wagaDo);
                    tmpKrawedz = new Krawedz(i+kolumny, tmpWaga);
                    graf.get(i).add(tmpKrawedz);
                    tmpKrawedz = new Krawedz(i, tmpWaga);
                    graf.get(i+kolumny).add(tmpKrawedz);
                }
            }
            i++;
        }
        if(trybGeneracji==2){
            if (!Algorytmy.grafNieSpojny(graf))
                generujGraf(wiersze, kolumny, wagaOd, wagaDo, trybGeneracji);
        }
    }
    private double losujWage(double wagaOd, double wagaDo){
        Random liczba = new Random();
        double waga = wagaOd+liczba.nextDouble()*(wagaDo-wagaOd);
        return waga;
    }
    private int losujCzyJestSasiad(int trybGeneracji){
        if(trybGeneracji==1) {
            return 1;
        }
        else{
            Random liczba = new Random();
            int losowana = liczba.nextInt(11);
            if(losowana<10){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
    public ArrayList<ArrayList<Krawedz>> getGraf(){
        return this.graf;
    }
    public String toString() {
        return String.valueOf(graf);
    }

    public void setKolorKrawedzi(Color kolor, int i, int j) {
        Rectangle tmp = (Rectangle) this.tablicaGrafu[i][j];
        tmp.setFill(kolor);
        this.tablicaGrafu[i][j] = tmp;
    }
    public int getWiersze() {
        return wiersze;
    }
    public int getKolumny() {
        return kolumny;
    }

    public void zapiszPlik(String test) throws IOException {
        File plikDoSciezkiPliku = new File("mygraph.txt");
        String sciezka = plikDoSciezkiPliku.getAbsolutePath();
        sciezka = sciezka.replaceAll("mygraph.txt", "wyjscie0.txt");
        int i=0;
        if(test!= null){sciezka = test;}
        else {
            while (i < Integer.MAX_VALUE) {
                File plik = new File(sciezka);
                if (plik.createNewFile()) {
                    System.out.println("Stworzono plik: " + plik.getName());
                    break;
                } else {
                    i++;
                    sciezka = sciezka.replaceAll("wyjscie" + Integer.toString(i - 1), "wyjscie" + Integer.toString(i));
                }
            }
        }
        FileWriter writer = new FileWriter(sciezka);
        writer.write(wiersze + " " + kolumny + "\n");
        int j;
        i=0;
        while(i<graf.size()){
            writer.write("\t");
            j=0;
            while(j<graf.get(i).size()) {
                writer.write(" " + graf.get(i).get(j).getCel() + " :" + graf.get(i).get(j).getWaga() + " ");
                j++;
            }
            writer.write("\n");
            i++;
        }

        writer.close();
    }
}