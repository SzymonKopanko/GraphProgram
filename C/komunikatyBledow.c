#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

void bladDanychWejsciowych(int blad, char flaga){

    switch (blad){
        case 1:
            printf("Niepoprawny argument wywolania -%c, ta flaga oczekuje liczby całkowitej\n",flaga);
            break;

        case 2:
            printf("Niepoprawny argument wywolania -%c, ta flaga oczekuje dwoch liczb naturalnych polaczonych myslnikiem np 2-6\n",flaga);
            break;

        case 3:
            printf("Niepoprawny argument wywolania -%c, ta flaga oczekuje liczby 1, 2 lub 3, odpowiadających trybom wywołania. 1 - graf posiada wszystkie krawedzie i losowe wagi, 2 - graf nie musi posiadać wszystkich krawedzi ale jest spójny, losowe wagi. 3 - graf nie musi być spójny, losowe wagi\n",flaga);
            break;

        case 4:
            printf("Niepoprawny argument wywolania -%c, ta flaga oczekuje par liczb nie ujemnych, mniejszych od liczby węzłów w grafie, polaczonych myslnikiem, oddzielonych od innych par przecinkiem np 2-6,3-8\n",flaga);
            break;

        case 5:
            printf("Niepoprawny argument wywolania -%c, ta flaga oczekuje litery 'r' w celu wyświetlenia rozbudowanego komunikatu wyjściowego, w innym wypadku należy te flagę pominąć\n",flaga);
            break;
        case 6:
            printf("Niepoprawna flaga wywołania. Program nie rozpoznaje flagi: -%c\n",flaga);
            break;
        case 7:
        printf("Prosze podac argument ostatniej flagi\n");
        break;
    }
}
void bladCzytaniaPliku(char *plik){
    printf("Czytanie pliku '%s' nie powiodło sie, nalezy upewnic sie ze podano poprawna sciezke do pliku oraz ze plik ma poprawny format zapisu danych zgodny ze specyfikacją\n",plik);
}
void bladGeneracjPliku(){
    printf("Generacja pliku nie powiodła sie\n");
}
void bladAlgorytmu(int x, int y){
    printf("Blad, sciezka z %d do %d, nie istnieje\n",x,y);
}
