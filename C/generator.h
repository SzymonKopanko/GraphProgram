#ifndef GENERATOR_H_INCLUDED
#define GENERATOR_H_INCLUDED

#include "algorytmy.h"

int wygenerowanoPlik(int wiersze, int kolumny, int wagaOd, int wagaDo, int trybGeneracji, int **wezly, int trybWyswietlania, int iloscParWezlow, int ziarno);
int testGeneracjiPliku(char *nazwaPliku, int wiersze, int kolumny, int wagaOd, int wagaDo, int trybGeneracji);

#endif // GENERATOR_H_INCLUDED
