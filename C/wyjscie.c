#include <stdio.h>
#include <stdlib.h>

#include "algorytmy.h"

int zapisPliku(struct ListaSasiedztwa *graf, int wiersze, int kolumny, char *nazwaPliku)
{
    FILE *f;
    if((f=fopen(nazwaPliku, "w"))==NULL)
		return 1;
    fprintf(f,"%d %d", wiersze, kolumny);
    int i;
    for(i=0;i<wiersze*kolumny;i++){
        struct KrawedzListySasiedztwa* nowyWezel3 = (struct KrawedzListySasiedztwa*) malloc(sizeof(struct KrawedzListySasiedztwa));
        nowyWezel3 = graf[i].wezel;
        if(!nowyWezel3){
            fprintf(f,"\n\t -1 :0.0000000000000000 ");
            }
        else{
            fprintf(f,"\n\t");
            while(nowyWezel3){
                fprintf(f," %d :%.16lf ",nowyWezel3->cel, nowyWezel3->waga);
                nowyWezel3 = nowyWezel3->nastepny;
            }
        }
    }
    fprintf(f,"\n");
    fclose(f);
    return 0;
}

void wydrukSciezki(int trybWyswietlania, int zrodlo, int cel, double *odleglosc, int v, int *droga, struct ListaSasiedztwa *graf, int *przejscia)
{
    printf("odleglosc z %d do %d wynosi %lf \n",zrodlo,cel,odleglosc[cel]);
    if(trybWyswietlania){
        int g = cel;
        int k=(v/2)+1;
        while(1){
            k--;
            if (przejscia[g] == - 1)
                break;
                // printf("%d ", g);
                droga[k]=g;
                g = przejscia[g];
        }
        int f;
        droga[k] = zrodlo;
        for(f = k; f < v/2; f++){
            //nic();
            struct KrawedzListySasiedztwa* chwilowka = graf[droga[f]].wezel;
            while(chwilowka != NULL){
                if(chwilowka->cel == droga[f+1])
                    break;
                chwilowka=chwilowka->nastepny;
            }
            printf("%d--(%lf)-->",droga[f],chwilowka->waga);
        }
        printf("%d\n",cel);
    }
}
