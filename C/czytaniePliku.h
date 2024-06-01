#ifndef CZYTANIEPLIKU_H_INCLUDED
#define CZYTANIEPLIKU_H_INCLUDED

struct KrawedzListySasiedztwa{
    int cel;
    double waga;
    struct KrawedzListySasiedztwa* nastepny;
};

struct ListaSasiedztwa{
    struct KrawedzListySasiedztwa *wezel;
};

int przeczytanoPlik(char *nazwaPliku, int **wezly, int iloscParWezlow, int n, int trybWyswietlania);
int testCzytaniaPliku(char *nazwaPliku);

#endif // CZYTANIEPLIKU_H_INCLUDED
