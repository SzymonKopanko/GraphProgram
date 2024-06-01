package com.projekt;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.*;


public class Algorytmy {
    static boolean[] odwiedzone;
    static double ostatniaOdleglosc;

    static void szukajSciezki(ArrayList<ArrayList<Krawedz>> graf, int zrodlo, int cel, VBox panel, int kolumny, Node[][] tablicaGrafu, Label konsolaBledow){
        if(odwiedzone != null)
            if(odwiedzone[zrodlo] != odwiedzone[cel]){
                konsolaBledow.setText("Nie ma połączenia");
                return;
            }
        int rozmiar = graf.size();
        double[] odleglosc = new double[rozmiar];
        double[] sciezkaWagi = new double[rozmiar];
        int[] informacjePrzejsc = new int[rozmiar];
        int[] przebiegSciezki = new int[rozmiar];
        for (int i = 0; i < rozmiar; i++) {
            przebiegSciezki[i] = -1;
            sciezkaWagi[i] = -1;
        }
        informacjePrzejsc[zrodlo]=-1;
        for (int i = 0; i < rozmiar; i++)
            odleglosc[i] = Double.MAX_VALUE;
        odleglosc[zrodlo] = 0;
        PriorityQueue<Krawedz> kolejka = new PriorityQueue<>(rozmiar, new porownywacz());
        kolejka.add(new Krawedz(zrodlo,0));
        while(kolejka.size() > 0) {
            Krawedz krawedzSzukajaca = kolejka.poll();
            for(int i = 0; i < graf.get(krawedzSzukajaca.getCel()).size(); i++){
                Krawedz krawedzObserwowana = graf.get(krawedzSzukajaca.getCel()).get(i);
                if(odleglosc[krawedzSzukajaca.getCel()] + krawedzObserwowana.getWaga() < odleglosc[krawedzObserwowana.getCel()]){
                    odleglosc[krawedzObserwowana.getCel()] = krawedzObserwowana.getWaga() + odleglosc[krawedzSzukajaca.getCel()];
                    informacjePrzejsc[krawedzObserwowana.getCel()] = krawedzSzukajaca.getCel();
                    kolejka.add(new Krawedz(krawedzObserwowana.getCel(),odleglosc[krawedzObserwowana.getCel()]));
                }
            }
        }
        int g = cel,k=rozmiar;
        while(informacjePrzejsc[g] != -1){
            k--;
            przebiegSciezki[k]=g;
            g = informacjePrzejsc[g];
        }
        k--;
        przebiegSciezki[k]=zrodlo;
        for(int i = k; i < rozmiar-1; i++){
            for(int j = 0; j < graf.get(przebiegSciezki[i]).size(); j++){
//                System.out.println("j = "+j+" size = " + graf.get(przebiegSciezki[i]).size());
                if(graf.get(przebiegSciezki[i]).get(j).getCel() == przebiegSciezki[i+1]){
                    sciezkaWagi[i+1] = graf.get(przebiegSciezki[i]).get(j).getWaga();
                }
            }
        }
        ostatniaOdleglosc = odleglosc[cel];
        if(panel != null)
            new Sciezka(zrodlo,cel,odleglosc[cel], przebiegSciezki,sciezkaWagi,panel,graf,kolumny,tablicaGrafu);
//        for (int i = 0; i < rozmiar; i++)
//            System.out.println("["+i+"] "+informacjePrzejsc[i]);
//        System.out.println("teraz druga");
//        for (int i = 0; i < rozmiar; i++)
//            System.out.println("["+i+"] "+przebiegSciezki[i]);
//        System.out.println("teraz wagi");
//        for (int i = 0; i < rozmiar; i++)
//            System.out.println("["+i+"] "+sciezkaWagi[i]);
    }
    static boolean grafNieSpojny(ArrayList<ArrayList<Krawedz>> graf){
        int start = 0;
        odwiedzone = new boolean[graf.size()];
        LinkedList<Krawedz> kolejka = new LinkedList<>();
        odwiedzone[start] = true;
        kolejka.addAll(graf.get(start));
        while(kolejka.size() != 0) {
            int cel = kolejka.poll().getCel();
            if(!odwiedzone[cel]){
                kolejka.addAll(graf.get(cel));
                odwiedzone[cel] = true;
            }
        }
//        System.out.print("\n" + Arrays.toString(odwiedzone));
        for(int i = 0; i < odwiedzone.length; i++)
            if(!odwiedzone[i])
                return false;
        return true;
    }
    static class porownywacz implements Comparator<Krawedz>{
        @Override
        public int compare(Krawedz k1, Krawedz k2) {
            if (k1.getWaga() < k2.getWaga())
                return 1;
            else if (k1.getWaga() > k2.getWaga())
                return -1;
            return 0;
        }
    }
}
