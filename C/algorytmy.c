#include <stdio.h>
#include <stdlib.h>

#include "czytaniePliku.h"
#include "algorytmy.h"
#include "wyjscie.h"

#define DBL_MAX 999999999

void segregujKopiec(struct Kopiec* kopiec, int rodzic){                         //utrzymuje strukture kopca segregując go

    int najmniejszy, leweDziecko, praweDziecko;
    najmniejszy = rodzic;
    leweDziecko = 2 * rodzic + 1;                                               //nadawanie indeksow w kopcu dzieciom rodzica
    praweDziecko = 2 * rodzic + 2;

    //     jest w kopcu         &&               sciezka prawego dziecka jest mniejsza niz rodzica
    if (praweDziecko < kopiec->rozmiar && kopiec->listaKrawedzi[praweDziecko]->odleglosc < kopiec->listaKrawedzi[najmniejszy]->odleglosc)
      najmniejszy = praweDziecko;

    //     jest w kopcu        &&               sciezka lewego dziecka jest mniejsza niz rodzica
    if (leweDziecko < kopiec->rozmiar && kopiec->listaKrawedzi[leweDziecko]->odleglosc < kopiec->listaKrawedzi[najmniejszy]->odleglosc)
      najmniejszy = leweDziecko;

    if (najmniejszy != rodzic){                                                 //jesli rodzic nie jest najmniejszy

        kopiec->indeks[kopiec->listaKrawedzi[najmniejszy]->wezel] = rodzic;       //zamieniamy indeksy najmniejszy z rodzicem
        kopiec->indeks[kopiec->listaKrawedzi[rodzic]->wezel] = najmniejszy;

        struct KrawedzKopca* chwilowka = kopiec->listaKrawedzi[najmniejszy];
        kopiec->listaKrawedzi[najmniejszy] = kopiec->listaKrawedzi[rodzic];         //zamieniamy krawedzie najmniejsza z rodzicem
        kopiec->listaKrawedzi[rodzic] = chwilowka;

        segregujKopiec(kopiec, najmniejszy);                                    //powtorz az bedzie posegregowany
    }
}

struct KrawedzKopca* wyjmijNajmniejszy(struct Kopiec* kopiec){                       //zwraca najmniejszy element z kopca

    struct KrawedzKopca* korzen = kopiec->listaKrawedzi[0];                          //przechowaj korzen

    kopiec->listaKrawedzi[0] = kopiec->listaKrawedzi[kopiec->rozmiar - 1];           //zastap korzen ostatnim

    kopiec->indeks[korzen->wezel] = kopiec->rozmiar-1;                               //aktualizacja indeksu korzen
    kopiec->indeks[kopiec->listaKrawedzi[kopiec->rozmiar - 1]->wezel] = 0;           //aktualizacja indeksu ostatniego elementu

    kopiec->rozmiar--;
    segregujKopiec(kopiec, 0);     //posegreguj kopiec

    return korzen;    //zwraca korzen(czyli najmniejszy element)
}

void zmniejszOdleglosc(struct Kopiec* kopiec, int v, int odleglosc){                //zmienia odleglosc do wezla v w kopcu

    int dziecko = kopiec->indeks[v];
    int rodzic = (dziecko - 1) / 2;
    kopiec->listaKrawedzi[dziecko]->odleglosc = odleglosc;
    // dziecko !=0 &&        odleglosc do dziecka mniejsza od odleglosci do rodzica
    while (dziecko && kopiec->listaKrawedzi[dziecko]->odleglosc < kopiec->listaKrawedzi[rodzic]->odleglosc){

        kopiec->indeks[kopiec->listaKrawedzi[dziecko]->wezel] = rodzic;             //zamieniamy indeksy: najmniejszy z rodzicem
        kopiec->indeks[kopiec->listaKrawedzi[rodzic]->wezel] = dziecko;

        struct KrawedzKopca* chwilowka = kopiec->listaKrawedzi[dziecko];
        kopiec->listaKrawedzi[dziecko] = kopiec->listaKrawedzi[rodzic];             //zamieniamy krawedzie: najmniejsza z rodzicem
        kopiec->listaKrawedzi[rodzic] = chwilowka;

        dziecko = rodzic;               //indeks dziecka sie staje indeksem rodzica
        rodzic = (dziecko - 1) / 2;     //wyznaczenie nastepnego indeksu rodzica
    }
}
void szukanieSciezek(struct ListaSasiedztwa* graf,int **wezly, int iloscParWezlow, int v, int trybWyswietlania){

    int j=0;
    while(iloscParWezlow){                                                      //petla w celu wyswietlenia sciezek do wszystkich par wezlow
        iloscParWezlow--;
        int zrodlo = wezly[j][0];
        int cel = wezly[j][1];
        j++;
        if(zrodlo >= v || cel >= v){
            bladAlgorytmu(zrodlo,cel);
            return;
        }
        double odleglosc[v];
        int przejscia[v], droga[(v/2)+1], k,f;
        przejscia[zrodlo]=-1;

        struct Kopiec* kopiec = (struct Kopiec*) malloc(sizeof(struct Kopiec));                     //stworzenie kopca i alokacja pamięci
        kopiec->listaKrawedzi = (struct KrawedzKopca**) malloc(v * sizeof(struct KrawedzKopca*));
        for(k = 0; k < v; k++)
            kopiec->listaKrawedzi[k] = (struct KrawedzKopca*) malloc(sizeof(struct KrawedzKopca));
        kopiec->indeks = (int *) malloc(v * sizeof(int));

        kopiec->rozmiar = 0;
        for (k = 0; k < v; k++){
            odleglosc[k] = DBL_MAX;                                             //inicjalizacja sciezek jako mksymalne wartosc
            kopiec->listaKrawedzi[k]->wezel = k;
            kopiec->listaKrawedzi[k]->odleglosc = odleglosc[k];                 //wpisywanie parametrów krawedzi do kopca
            kopiec->indeks[k] = k;
        }
        for(k = 0; k < v/2+1; k++)
            droga[k]=-1;
        kopiec->indeks[zrodlo] = zrodlo;
        odleglosc[zrodlo] = 0;                                                  //ustawienie odleglosci dla wezla startowego na 0
        zmniejszOdleglosc(kopiec, zrodlo, odleglosc[zrodlo]);

        kopiec->rozmiar = v;

        // poczatek faktycznego algorytmu

        while (!kopiec->rozmiar == 0){                                          //kopiec nie pusty

            struct KrawedzKopca* krawedzKopca = wyjmijNajmniejszy(kopiec);

            int u = krawedzKopca->wezel;    // u to indeks wezla do którego prawadzi krawedz na ktora patrzymy
            struct KrawedzListySasiedztwa* krawedzSzukajaca = graf[u].wezel;
            while (krawedzSzukajaca != NULL){                                       //przejdz po wszystkich sasiadach danego wezla
                int v = krawedzSzukajaca->cel;                                    //to nie to samo v co wczesniej, mozemy zrobic nowa zmeinna v bo jest w innej przestrzeni nazw

                //       znajduje sie w kopcu           &&  zmieniona wartosc poczatkowa && nowo znaleziona sciezka jest krotsza od obecnej
                if ((kopiec->indeks[v] < kopiec->rozmiar) && odleglosc[u] != DBL_MAX && krawedzSzukajaca->waga + odleglosc[u] < odleglosc[v]){
                    przejscia[v] = u;
                    odleglosc[v] = odleglosc[u] + krawedzSzukajaca->waga;         //znaleziono krotsza sciezke wiec aktualizujemy ja w tablicy odleglosc
                    zmniejszOdleglosc(kopiec, v, odleglosc[v]);                 //i w kopcu tez
                }
                krawedzSzukajaca = krawedzSzukajaca->nastepny;                      //nastepny sasiad
            }
        }
        wydrukSciezki(trybWyswietlania, zrodlo, cel, odleglosc, v, droga, graf, przejscia);
    }
}
struct KrawedzListySasiedztwa* dodajSasiadowDoKolejki(struct ListaSasiedztwa* graf, struct KrawedzListySasiedztwa* kolejka,int *odwiedzone, int zrodlo){
    struct KrawedzListySasiedztwa *chwilowka = graf[zrodlo].wezel;
    while(chwilowka != NULL){
        struct KrawedzListySasiedztwa *chwilowka2 = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        if(!odwiedzone[chwilowka->cel]){
            chwilowka2->cel = chwilowka->cel;
            chwilowka2->nastepny = NULL;
            struct KrawedzListySasiedztwa *chwilowka3 = kolejka;
            while (1){
                if(chwilowka3->nastepny == NULL){
                    chwilowka3->nastepny = chwilowka2;
                    break;
                }
                chwilowka3 = chwilowka3->nastepny;
            }
        }
            chwilowka = chwilowka->nastepny;
    }
    return kolejka;
}
int czySpojny(struct ListaSasiedztwa* graf, int v){
    int odwiedzone[v],i;
    struct KrawedzListySasiedztwa* kolejka = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
    for(i = 1; i < v; i++)
        odwiedzone[i]=0;
    odwiedzone[0]=1;
    kolejka->cel = 0;
    kolejka->nastepny = NULL;

    while(kolejka != NULL){
        int id = kolejka->cel;
        odwiedzone[id]=1;
        kolejka = dodajSasiadowDoKolejki(graf,kolejka,odwiedzone,id);
        kolejka = kolejka->nastepny;
    }

    for(i = 0; i < v; i++)
        if(odwiedzone[i]==0)
            return 1;
    return 0;
}
