#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "czytaniePliku.h"
#include "algorytmy.h"
#include "wyjscie.h"

int losujCzyJestSasiad(int tryb)
{
    if(tryb==1)
        return 1;
    else if(tryb==2 || tryb==3){
        int ilosc=1+rand()%10;
        if(ilosc>7)
    		return 0;
        else
            return 1;
    }
}
void *dodanieSasiadowDoStruktury(int wierzcholek, int sasiad, double wagaOd, double wagaDo, struct ListaSasiedztwa* graf, int tryb)
{
    int wynik = losujCzyJestSasiad(tryb);
    if(wynik){
        struct KrawedzListySasiedztwa* nowyWezel = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel->cel = sasiad;
        nowyWezel->waga = wagaOd + (double) rand() /RAND_MAX* (wagaDo-wagaOd);
        nowyWezel->nastepny = graf[wierzcholek].wezel;
        graf[wierzcholek].wezel = nowyWezel;
        struct KrawedzListySasiedztwa* nowyWezel2 = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel2->cel = wierzcholek;
        nowyWezel2->waga = wagaOd + (double) rand() /RAND_MAX* (wagaDo-wagaOd);
        nowyWezel2->nastepny = graf[sasiad].wezel;
        graf[sasiad].wezel = nowyWezel2;
    }
}

int wygenerowanoPlik(int wiersze, int kolumny, int wagaOd, int wagaDo, int trybGeneracji, int **wezly, int trybWyswietlania, int iloscParWezlow, int ziarno)
{
    ziarno++;
    srand(time(NULL)/ziarno);
    int i;
    int v=wiersze*kolumny;
    struct ListaSasiedztwa* graf = (struct ListaSasiedztwa*) malloc(v * sizeof(struct ListaSasiedztwa)); //alokacja pamieci
    if(!graf){
	    printf("Brak miejsca w pamieci na graf\n");
	    return 1;
    }
    for (i = 0; i < v; i++)
        graf[i].wezel = NULL;   //upewnienie sie ze nie ma smieci w danych miejscach w pamieci
    int s=0;
    for(i=0;i<v;i++){
        if(i==kolumny-1 || (i%kolumny==kolumny-1 && i<wiersze*kolumny-1)){
            dodanieSasiadowDoStruktury(i, i+kolumny, wagaOd, wagaDo, graf, trybGeneracji);
        }
        else if(i == (wiersze-1) * kolumny || (i>(wiersze-1) * kolumny && i<wiersze*kolumny-1)){
            dodanieSasiadowDoStruktury(i, i+1, wagaOd, wagaDo, graf, trybGeneracji);
        }
        else if(i<wiersze*kolumny-1){
            dodanieSasiadowDoStruktury(i, i+kolumny, wagaOd, wagaDo, graf, trybGeneracji);
            dodanieSasiadowDoStruktury(i, i+1, wagaOd, wagaDo, graf, trybGeneracji);
        }
    }
    if(trybGeneracji==2 && czySpojny(graf,v)){
                wygenerowanoPlik(wiersze, kolumny, wagaOd, wagaDo, trybGeneracji, wezly, trybWyswietlania, iloscParWezlow, ziarno);
                return 0;
    }
    szukanieSciezek(graf, wezly, iloscParWezlow, v, trybWyswietlania);
    zapisPliku(graf, wiersze, kolumny, "wyjscie.txt");
    return 0;
}

int testGeneracjiPliku(char *nazwaPliku, int wiersze, int kolumny, int wagaOd, int wagaDo, int trybGeneracji)
{
    srand(time(NULL));
    int i;
    int v=wiersze*kolumny;
    struct ListaSasiedztwa* graf = (struct ListaSasiedztwa*) malloc(v * sizeof(struct ListaSasiedztwa));
    for (i = 0; i < v; i++)
        graf[i].wezel = NULL;
    int s=0;
    for(i=0;i<v;i++){
        if(i==kolumny-1 || (i%kolumny==kolumny-1 && i<wiersze*kolumny-1)){
            dodanieSasiadowDoStruktury(i, i+kolumny, wagaOd, wagaDo, graf, trybGeneracji);
        }
        else if(i == (wiersze-1) * kolumny || (i>(wiersze-1) * kolumny && i<wiersze*kolumny-1)){
            dodanieSasiadowDoStruktury(i, i+1, wagaOd, wagaDo, graf, trybGeneracji);
        }
        else if(i<wiersze*kolumny-1){
            dodanieSasiadowDoStruktury(i, i+kolumny, wagaOd, wagaDo, graf, trybGeneracji);
            dodanieSasiadowDoStruktury(i, i+1, wagaOd, wagaDo, graf, trybGeneracji);
        }
    }
    zapisPliku(graf, wiersze, kolumny, nazwaPliku);
    return 0;
}
