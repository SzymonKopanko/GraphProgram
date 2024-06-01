#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#include "algorytmy.h"
#include "czytaniePliku.h"
#include "wyjscie.h"

#define ENTER 10
#define CARRIAGE_RETURN 13

int przeczytanoPlik(char *nazwaPliku, int **wezly, int iloscParWezlow, int n, int trybWyswietlania)
{
	FILE *f;
    int wiersze, kolumny,i,tmp1,v,a=0;
    double tmp2;
	if((f=fopen(nazwaPliku, "r"))==NULL)                        //
		return 1;                                               //
                                                                //  dosyc oszczedne bledy pliku
	if(fscanf(f, "%d %d", &wiersze, &kolumny)!=2)               //
        return 1;                                               //

    v = wiersze*kolumny;
    struct ListaSasiedztwa* graf = (struct ListaSasiedztwa*) malloc(v * sizeof(struct ListaSasiedztwa)); //alokacja pamieci

    for (i = 0; i < v; i++)
        graf[i].wezel = NULL;   //upewnienie sie ze nie ma smieci w danych miejscach w pamieci
    i=0;
    char czyEnter;
	while(i<v) // wlasciwe czytanie pliku start
	{
        fscanf(f, "%d :%lf", &tmp1, &tmp2);
        if(tmp1==-1 && tmp2==0)
        {
            i++;
            continue;
        }
        else if(tmp1>v-1 || tmp1<0 || tmp2<=0 /*|| (tmp1!=i+1 && tmp1!=i-1 && tmp1!=i-kolumny && tmp1!=i+kolumny) || isdigit(tmp1)==0 || isdigit (tmp2) == 0*/)
            return 1;
        struct KrawedzListySasiedztwa* nowyWezel = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel->cel = tmp1;                                                                                             //
        nowyWezel->waga = tmp2;                                                                                            //  wpisanie przeczytanych danych do struktury
        nowyWezel->nastepny = graf[i].wezel;                                                                            //
        graf[i].wezel = nowyWezel;                                                                                      //  przesuniecie wskaznika na nastepny element
        fgetc(f);

        czyEnter = fgetc(f);
        if (czyEnter==ENTER || czyEnter==CARRIAGE_RETURN)//przeczytano znak enter
        {
            i++;
        }
        if(czyEnter==EOF)
            break;
    }
    fclose(f);
    if(czySpojny(graf,v))
         printf("graf nie jest spojny\n");
    szukanieSciezek(graf, wezly, iloscParWezlow, v, trybWyswietlania);
	return 0;
}

int testCzytaniaPliku(char *nazwaPliku)
{
    FILE *f;
    int wiersze, kolumny,i,tmp1,v,a=0;
    double tmp2;
	if((f=fopen("wyjscie.txt", "r"))==NULL)                        //
		return 1;                                               //
                                                                //  dosyc oszczedne bledy pliku
	if(fscanf(f, "%d %d", &wiersze, &kolumny)!=2)               //
        return 1;                                               //

    v = wiersze*kolumny;
    struct ListaSasiedztwa* graf = (struct ListaSasiedztwa*) malloc(v * sizeof(struct ListaSasiedztwa)); //alokacja pamieci

    for (i = 0; i < v; i++)
        graf[i].wezel = NULL;   //upewnienie sie ze nie ma smieci w danych miejscach w pamieci
    i=0;
    char czyEnter;
	while(i<v) // wlasciwe czytanie pliku start
	{
        fscanf(f, "%d :%lf", &tmp1, &tmp2);
        if(tmp1==-1 && tmp2==0)
        {
            i++;
            continue;
        }
        else if(tmp1>v-1 || tmp1<0 || tmp2<=0 || (tmp1!=i+1 && tmp1!=i-1 && tmp1!=i-kolumny && tmp1!=i+kolumny))
            return 1;
        struct KrawedzListySasiedztwa* nowyWezel = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel->cel = tmp1;                                                                                             //
        nowyWezel->waga = tmp2;                                                                                            //  wpisanie przeczytanych danych do struktury
        nowyWezel->nastepny = graf[i].wezel;                                                                            //
        graf[i].wezel = nowyWezel;                                                                                      //  przesuniecie wskaznika na nastepny element
        fgetc(f);
        czyEnter = fgetc(f);
        if (czyEnter==ENTER || czyEnter==CARRIAGE_RETURN)//przeczytano znak enter
        {
            i++;
        }
        if(czyEnter==EOF)
            break;
    }
    fclose(f);
    zapisPliku(graf, wiersze, kolumny, nazwaPliku);
	if((f=fopen(nazwaPliku, "r"))==NULL)                        //
		return 1;                                               //
                                                                //  dosyc oszczedne bledy pliku
	if(fscanf(f, "%d %d", &wiersze, &kolumny)!=2)               //
        return 1;                                               //

    v = wiersze*kolumny;
    for (i = 0; i < v; i++)
        graf[i].wezel = NULL;   //upewnienie sie ze nie ma smieci w danych miejscach w pamieci
    i=0;
	while(i<v) // wlasciwe czytanie pliku start
	{
        fscanf(f, "%d :%lf", &tmp1, &tmp2);
        if(tmp1==-1 && tmp2==0)
        {
            i++;
            continue;
        }
        else if(tmp1>v-1 || tmp1<0 || tmp2<=0 || (tmp1!=i+1 && tmp1!=i-1 && tmp1!=i-kolumny && tmp1!=i+kolumny))
            return 1;
        struct KrawedzListySasiedztwa* nowyWezel = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel->cel = tmp1;                                                                                             //
        nowyWezel->waga = tmp2;                                                                                            //  wpisanie przeczytanych danych do struktury
        nowyWezel->nastepny = graf[i].wezel;                                                                            //
        graf[i].wezel = nowyWezel;                                                                                      //  przesuniecie wskaznika na nastepny element
        fgetc(f);
        czyEnter = fgetc(f);
        if (czyEnter==ENTER || czyEnter==CARRIAGE_RETURN)//przeczytano znak enter
        {
            i++;
        }
        if(czyEnter==EOF)
            break;
    }
    fclose(f);
    zapisPliku(graf, wiersze, kolumny, nazwaPliku);
}
