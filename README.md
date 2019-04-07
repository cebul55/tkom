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


### Źródła :
- "Kompilatory. Reguły, metody i narzędzia" - D.Ullman
- Wykłady z przedmiotu __TKOM__