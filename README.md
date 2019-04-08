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
 
Gramatyka zostałą napisana w notacji __EBNF__. Do wygenerowania diagramu wykorzystany został portal: [Railroad Diagram Generator](https://www.bottlecaps.de/rr/ui)

Diagram:
[diagram](diagram.xhtml)

### Źródła :
- "Kompilatory. Reguły, metody i narzędzia" - D.Ullman
- Wykłady z przedmiotu __[TKOM]__
- [EBNF Tutorial](https://tomassetti.me/ebnf/)
- [Railroad Diagram Generator](https://www.bottlecaps.de/rr/ui)