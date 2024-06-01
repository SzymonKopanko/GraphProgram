#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#include "komunikatyBledow.h"
#include "czytaniePliku.h"
#include "generator.h"


int debug=0;
int
main (int argc, char **argv)
{
    int opt, k, i, j=0, wiersze=2,kolumny=2,blad,wagaOd=0,wagaDo=1,trybGeneracji=1,**wezly,trybWyswietlania=0,brakMyslnika, iloscParWezlow=0,test=0;
    char *plik=NULL;
    if(argc>1)
    test=atoi(argv[1]);

    while ((opt = getopt (argc, argv, "a:b:c:d:e:f:g:h:i:j:k:l:m:n:o:p:q:r:s:t:u:v:w:x:y:z:A:B:C:D:E:F:G:H:I:J:K:L:M:N:O:P:Q:R:S:T:U:V:W:X:Y:Z")) != -1) {
        blad=1;
        switch (opt) {
        case 'f':
            i = strlen(optarg);
            plik = malloc(sizeof(plik)*(i+1));
            strcpy(plik,optarg);
            break;

        case 'r':
                        if(optarg[0] == '0'){
                    blad=1;
                    break;
                }
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;

                if (isdigit(optarg[i]) == 0){
                    blad=1;
                    printf("sadf");
                    break;
                }
            }
            if (blad){
                bladDanychWejsciowych(1,opt);
                return 1;
            }
            wiersze = atoi(optarg);
            break;

        case 'c':
            if(optarg)
                            if(optarg[0] == '0'){
                    blad=1;
                    break;
                }
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;
                if (isdigit(optarg[i]) == 0){
                    blad=1;
                    break;
                }
            }
            if (blad){
                bladDanychWejsciowych(1,opt);
                return 1;
            }
            kolumny = atoi(optarg);
            break;

        case 'w':
            brakMyslnika=1;
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;
                if ((isdigit(optarg[i]) == 0 && optarg[i] != '-') || optarg[i] == ','){
                    blad=1;
                    break;
                }
                if(optarg[i] == '-')
                    brakMyslnika=0;
            }
            if(optarg[i-1] == '-' || i < 2  || optarg[0] == '-')
                blad=1;
            if(blad || brakMyslnika){
                bladDanychWejsciowych(2,opt);
                return 1;
            }
            wagaOd=atoi(&optarg[0]);
            k=wagaOd;
            if(k==0)
                    j++;
                while(k){
                    k=k/10;
                    j++;                  //umozliwia czytanie liczb wielocyfrowych, sprawdza ile cyfr ma liczba i o tyle przesuwa kursor
                }
            j--;
            wagaDo=atoi(&optarg[2+j]);
            break;

        case 'g':
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;
                if (optarg[i] != '1' && optarg[i] != '2' && optarg[i] != '3'){
                    blad=1;
                    break;
                }
            }
            if (blad){
                bladDanychWejsciowych(3,opt);
                return 1;
            }
            trybGeneracji = atoi(optarg);
            break;

        case 'p':
            brakMyslnika=1;
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;
                if ((isdigit(optarg[i]) == 0 && optarg[i] != '-' && optarg[i] != ',') || (optarg[i] == '-' && optarg[i-1] == '-') || (optarg[i] == ',' && optarg[i-1] == ',') || (optarg[i] == '-' && optarg[i-1] == ',') || (optarg[i] == ',' && optarg[i-1] == '-')){
                    blad=1;
                    break;
                }
                if(optarg[i] == '-'){
                    brakMyslnika=0;
                    iloscParWezlow++;   //znaleziony myslnik, wiec jest wiecej par wezlow
                }
            }
            if(optarg[i-1] == '-' || optarg[i-1] == ',' | i < 2  || optarg[0] == '-' || optarg[0] == ',')
                blad=1;
            if (blad || brakMyslnika){
                bladDanychWejsciowych(4,opt);
                return 1;
            }
            wezly = malloc(iloscParWezlow*sizeof(wezly));
            for(i = 0; i < iloscParWezlow; i ++){
                wezly[i] = malloc(sizeof(wezly[i])*2);
                wezly[i][0]=atoi(&optarg[4*i]+j);
                k = wezly[i][0];
                if(k==0)
                    j++;
                while(k){
                    k=k/10;
                    j++;                  //umozliwia czytanie liczb wielocyfrowych, sprawdza ile cyfr ma liczba i o tyle przesuwa kursor
                }
                j--;
                wezly[i][1]=atoi(&optarg[(4*i)+2+j]);
                k = wezly[i][1];
                if(k==0)
                    j++;
                while(k)
                {
                    k=k/10;
                    j++;                  //umozliwia czytanie liczb wielocyfrowych, sprawdza ile cyfr ma liczba i o tyle przesuwa kursor
                }
                j--;
            }
            break;

        case 'd':
            for (i = 0; optarg[i] != '\0'; i++){
                blad=0;
                if (optarg[i] != 'r' || i > 0){
                    blad=1;
                    break;
                }
            }
            if (blad){
                bladDanychWejsciowych(5,opt);
                return 1;
            }
            if (optarg[0] == 'r')
                trybWyswietlania = 1;
            break;

        case '?':
            bladDanychWejsciowych(7,opt);
            return 1;

        default:
            bladDanychWejsciowych(6,opt);
            return 1;
        }
    }
    if(test){
        if(test==1){
            printf("test:%d\n",test);
            if(plik){
                testCzytaniaPliku(plik);
                testCzytaniaPliku("wyjscie.txt");
                FILE *f;
                FILE *g;
                if((f=fopen(plik, "r"))==NULL)
                    return 1;
                if((g=fopen("wyjscie.txt", "r"))==NULL)
                    return 1;
                int znakId=0;
                char znakZWejscia;
                char znakZWyjscia;
                while(znakZWejscia!=EOF || znakZWyjscia!=EOF){
                    znakId++;
                    znakZWejscia=fgetc(f);
                    znakZWyjscia=fgetc(g);
                    if(znakZWejscia!=znakZWyjscia){
                        printf("[%c] != [%c]\n", znakZWejscia, znakZWyjscia);
                        printf("Pliki sie roznia przy znaku nr %d.\n", znakId);
                        return 1;
                    }
                }
                printf("Test czytania plikow przebiegl pomyslnie.\n");
                return 0;
            }
            else
                printf("Nie podano pliku do testu czytania plikow.\n");
            return 1;
        }
        if(test==2){
            printf("test:%d\n",test);
            testGeneracjiPliku("wyjscie.txt", wiersze, kolumny, wagaOd, wagaDo, trybGeneracji);
            testCzytaniaPliku("wyjscie2.txt");
            FILE *f;
            FILE *g;
            if((f=fopen("wyjscie.txt", "r"))==NULL)
                return 1;
            if((g=fopen("wyjscie2.txt", "r"))==NULL)
                return 1;
            int znakId=0;
            char znakZWejscia;
            char znakZWyjscia;
            while(znakZWejscia!=EOF || znakZWyjscia!=EOF){
                znakId++;
                znakZWejscia=fgetc(f);
                znakZWyjscia=fgetc(g);
                if(znakZWejscia!=znakZWyjscia){
                    printf("[%c] != [%c]\n", znakZWejscia, znakZWyjscia);
                    printf("Pliki sie roznia przy znaku nr %d.\n", znakId);
                    return 1;
                }
            }
                printf("Test generatora plikow przebiegl pomyslnie.\n");
                return 0;
        }
    }
    if(plik){
        if(przeczytanoPlik(plik, wezly, iloscParWezlow, wiersze*kolumny,trybWyswietlania)){
            bladCzytaniaPliku(plik);
        }
    }
    else{
        if(wygenerowanoPlik(wiersze, kolumny, wagaOd, wagaDo, trybGeneracji, wezly, trybWyswietlania, iloscParWezlow, 0)){
                bladGeneracjPliku();
        }
    }
    return 0;
}
