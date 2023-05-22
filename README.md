# SQL injection

Tämän yksinkertaisen esimerkkisovelluksen tarkoitus on näyttää, mitä tietokantaohjelmointia opetellessa **ei saa** tehdä ja mitä **pitää** sen sijaan tehdä (Java/JDBC).

Yksi asioista joita ei saa tehdä on altistaa järjestelmä [SQL injektioille](https://fi.wikipedia.org/wiki/SQL-injektio). Tämä esimerkkisovellus näyttää miten sellainen voi tapahtua, ja miten tietokantaohjelmointia pitää tehdä niin ettei SQL injektiota voi tapahtua. Toinen asia mitä tämä sovellus näyttää on se, miten salasanat pitää salata ennen niiden tallentamista tietokantaan.

![Bobby tables](xkcd.png)

*Linkki:* [xkcd](https://xkcd.com/327/). Katso myös [bobby-tables.com](https://bobby-tables.com).

Projektissa on kaksi totetutusta tietokantaluokasta:

1. `BadDatabase` joka ei tee tietokantakyselyitä kuten pitäisi, ja altistaa sovelluksen SQL Injection -hyökkäyksille.
2. `GoodDatabase` joka tekee tietokantakyselyt prepared statement:eillä eikä siten ole altis SQL Injection -hyökkäyksille.

Voit vaihtaa kumpaa tietokantatoteutusta käytät, luokan `SQLInjectionApp` metodissa `run()`. Oletusarvoisesti sovellus käyttää huonoa toteutusta.

Lisäksi `GoodDatabase` salaa tallentamansa salasanat (hash+salt), toisin kuin `BadDatabase`, joka tallentaa salasanat salaamatta.

Sanomattakin selvää on se, että *alusta asti* olisi syytä opetella tekemään (nämäkin) asiat oikein:

1. Käytä *aina* prepared statementtejä kyselyiden tekemiseen, jos ne sisältävät parametreja.
2. Salaa *aina* tallennetut salasanat käyttämällä suolaa ja tiivisteitä (salting & hashing).

Havainnollistava demovideo sovelluksen käytöstä löytyy [YouTubesta](https://www.youtube.com/watch?v=FFOfpr61TFA).

## Riippuvuudet

Sovelluksen kääntäminen ja suorittaminen edellyttää seuraavia komponentteja:

* SQLite; jos tämä ei ole jo asennettu koneellesi, asenna se.
* `org.xerial.sqlite-jdbc` JDBC ajuri SQLite:a varten, määritelty valmiiksi `pom.xml` -tiedostossa.
* Apache `commons-codec`, määritelty valmiiksi `pom.xml` -tiedostossa.
* Java Developer Kit (JDK) versio 18 tai uudempi.
* Maven.


## Kääntäminen

Projekti on tehty Mavenilla, joten voit avata sen missä vaan Mavenia tukevalla kehitystyökalulla (IDE; Visual Studio Code, Eclipse, IntelliJ IDEA). Suorita ohjelma IDE:stä käsin; main -metodi löytyy luokasta `SQLInjectionApp`.

 Voit kääntää ohjelman komentoriviltä, jos olet jo asentanut JDK:n (versio 18+) ja Mavenin.

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