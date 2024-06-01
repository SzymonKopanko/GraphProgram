package com.projekt;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class GrafTest {

    @Test
    public void testCzytajPlik() throws IOException {
        Graf graf = new Graf(null);

            graf.czytajPlik("testplik.txt");

        Assert.assertFalse(graf.getKolumny() != 2 || graf.getWiersze()!= 2 ||
                graf.getGraf().get(0).get(0).getCel() != 1 || graf.getGraf().get(0).get(0).getWaga() != 1.3902443458782239 ||
                graf.getGraf().get(0).get(1).getCel() != 2 || graf.getGraf().get(0).get(1).getWaga() != 1.9045838356471503 ||
                graf.getGraf().get(1).get(0).getCel() != 0 || graf.getGraf().get(1).get(0).getWaga() != 1.3902443458782239 ||
                graf.getGraf().get(2).get(0).getCel() != 0 || graf.getGraf().get(2).get(0).getWaga() != 1.9045838356471503 ||
                graf.getGraf().get(2).get(1).getCel() != 3 || graf.getGraf().get(2).get(1).getWaga() != 1.5257413376450655 ||
                graf.getGraf().get(3).get(0).getCel() != 2 || graf.getGraf().get(3).get(0).getWaga() != 1.5257413376450655 );
    }

    @Test
    public void generujGraf() {
        Graf graf = new Graf(null);
        graf.generujGraf(3, 3,1,2,1);
        for(int i = 0; i < graf.getGraf().size(); i++){
            for(int j = 0; j < graf.getGraf().get(i).size(); j++){
                Assert.assertFalse(graf.getGraf().get(i).get(j).getWaga() > 2 || graf.getGraf().get(i).get(j).getWaga() < 1 ||
                        j > 4 || i > 9);
            }
        }
    }

    @Test
    public void zapiszPlik() throws IOException {
        Graf graf = new Graf(null);
        Graf graf2 = new Graf(null);
        graf.czytajPlik("testplik.txt");
        graf.zapiszPlik("testZapisu.txt");
        graf2.czytajPlik("testZapisu.txt");

        for(int i = 0; i < graf.getGraf().size(); i++){
            for(int j = 0; j < graf.getGraf().get(i).size(); j++){
                Assert.assertFalse(graf.getGraf().get(i).get(j).getCel() != graf2.getGraf().get(i).get(j).getCel() ||
                        graf.getGraf().get(i).get(j).getWaga() != graf2.getGraf().get(i).get(j).getWaga());
            }
        }
    }
}