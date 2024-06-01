#ifndef WYJSCIE_H_INCLUDED
#define WYJSCIE_H_INCLUDED

void zapisPliku(struct ListaSasiedztwa *graf, int wiersze, int kolumny, char *nazwaPliku);
void wydrukSciezki(int trybWyswietlania, int zrodlo, int cel, double *odleglosc, int v, int *droga, struct ListaSasiedztwa *graf, int *przejscia);

#endif // WYJSCIE_H_INCLUDED
