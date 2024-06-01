package com.projekt;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorytmyTest {

    @Test
    public void szukajSciezki() throws IOException {
        Graf graf = new Graf(null);
        graf.czytajPlik("testplik.txt");
        Algorytmy.szukajSciezki(graf.getGraf(),1,3,null,2,null, null);
        Assert.assertFalse(Algorytmy.ostatniaOdleglosc != 4.82056951917044);
    }
    @Test
    public void grafNieSpojny() throws IOException {
        Graf graf = new Graf(null);
        graf.czytajPlik("testplik.txt");
        Graf graf2 = new Graf(null);
        graf2.czytajPlik("niespojny.txt");
        Assert.assertTrue("1", Algorytmy.grafNieSpojny(graf.getGraf()));
//        Assert.assertFalse("2",Algorytmy.grafNieSpojny(graf2.getGraf()));
    }
}