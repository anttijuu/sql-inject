# SQL injection

Mitä tietokantaohjelmointia opetellessa **ei saa** tehdä ja mitä **pitää** sen sijaan tehdä (Java/JDBC).

![Bobby tables](xkcd.png)

Katso myös [bobby-tables.com](https://bobby-tables.com).

Projektissa on kaksi totetutusta tietokantaluokasta:

1. `BadDatabase` joka ei tee tietokantakyselyitä kuten pitäisi, ja altistaa sovelluksen SQL Injection -hyökkäyksille.
2. `GoodDatabase` joka tekee tietokantakyselyt prepared statement:eillä eikä siten ole altis SQL Injection -hyökkäyksille.

Voit vaihtaa kumpasa tietokantatoteutusta käytät, luokan `SQLInjectionApp` metodissa `run()`. Oletusarvoisesti siellä käytetään huonoa toteutusta.

Lisäksi `GoodDatabase` salaa tallentamansa salasanat (hash+salt), toisin kuin `BadDatabase`, joka tallentaa salasanat salaamatta.

Sanomattakin selvää on se, että *alusta asti* olisi syytä opetella tekemään (nämäkin) asiat oikein:

1. Käytä *aina* prepared statementtejä kyselyiden tekemiseen, jos ne sisältävät parametreja.
2. Salaa *aina* tallennetut salasanat käyttämällä suolaa ja tiivisteitä (salting & hashing).

## Kääntäminen

Projekti on tehty Mavenilla, joten voit avata sen missä vaan Mavenia tukevalla kehitystyökalulla (IDE; Visual Studio Code, Eclipse). Suorita ohjelma IDE:stä käsin; main -metodi löytyy luokasta `SQLInjectionApp`.

 Voit kääntää ohjelman komentoriviltä, jos olet jo asentanut JDK:n (18+) ja Mavenin.

Komentorivikäännös:

```console
mvn package
```

Ohjelman käynnistäminen komentoriviltä:

```console
 java -jar target/sql-injection-1.0-SNAPSHOT-jar-with-dependencies.jar
 ```

## Miten saan aikaan ongelman?

1. Varmista että  `BadDatabase` -toteutus on käytössä.
2. Käynnistä ohjelma
3. Tekstilaatikoissa on ylhäältä alas:
  - nimi
  - sähköposti
  - salasana
4. Syötä tiedot mutta sähköpostikenttään kirjoita jotain tekstiä (vaikkapa asdf)ja sen jälkeen:

> asdf');delete from user;--

5. Paina New -nappia.

Tämä SQL injection -hyökkäys saa aikaan sen että kaikki aiemmin syötetyt henkilötiedot katoavat tietokannasta.

## Kuka teki?

* Antti Juustila
* Lehtori
* INTERACT Research Unit
* University of Oulu, Finland