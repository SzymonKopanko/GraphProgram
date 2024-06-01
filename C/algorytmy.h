#ifndef ALGORYTMY_H_INCLUDED
#define ALGORYTMY_H_INCLUDED

#include "czytaniePliku.h"
#include "komunikatyBledow.h"

struct KrawedzKopca {
    int wezel;
    int odleglosc;
};
 
struct Kopiec {
    int rozmiar;    
    int *indeks;   
    struct KrawedzKopca **listaKrawedzi;
};

// struct KolejkaBFS {
//     int wezel;
//     struct bfs* nastepny;
// };

void szukanieSciezek(struct ListaSasiedztwa* graf,int **wezly, int poczatek, int v, int trybWyswietlania);
int czySpojny(struct ListaSasiedztwa* graf, int v);

#endif 