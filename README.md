# TKOM - Język skryptowy służący do przetwarzania stron WWW
#### Autor: Bartosz Cybulski


## 1. Analizator leksykalny 
Termin : 10.04.2019

Klasa __Lekser__ reprezentuje analizator leksykalny. Symbole leksykalne reprezentuje pakiet Token. 
- __TokenType__ - publiczny enum reprezentujący typy wykrywanych symboli.
- __Token__ - reprezentacja znalezionego symbolu (początkowy indeks, końcowy indeks, typ, ciąg znaków reprezentujący token)

Pakiet __SimpleTests__ demonstruje zachowanie poszczególnych komponentów.

## 2. Formalny opis gramatyki
Termin : 10.04.2019
 
Gramatyka zostałą napisana w notacji __EBNF__. 
Do wygenerowania diagramu wykorzystany został portal: [Railroad Diagram Generator](https://www.bottlecaps.de/rr/ui)

Diagram:
[diagram](diagram.xhtml)

## 3. Analizator składniowy

Termin 8.05.2019

Klasa __Parser__ reprezentuje analizator składniowy zstępujący typu LL.
W celu implementacji parsera, gramatyka została zmieniona na gramatykę LL(1).

Gramatyka LL(1) zdefiniowana jest w pliku: [LL1-grammar.txt](src/main/resources/LL1-grammar).
W przypadku kiedy gramatyka nie jest LL(1), program zakończy wywołanie błędem **StackOverflowError**.


Przebieg działania parsera:
- odczytanie pliku z gramatyką
- wyliczenie zbiorów First() i Follow()
- zbudowanie tablicy parsującej z wykorzystaniem funkcji First() i Follow()
- pobranie wejścia jako listy tokenów
- przeprowadzenie algorytmu parsowania


### Źródła :
- "Kompilatory. Reguły, metody i narzędzia" - D.Ullmanwas
- Wykłady z przedmiotu __[TKOM]__
- [EBNF Tutorial](https://tomassetti.me/ebnf/)
- [Railroad Diagram Generator](https://www.bottlecaps.de/rr/ui)

