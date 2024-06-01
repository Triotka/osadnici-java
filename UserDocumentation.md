# Uživatelská dokumentace
## Úvod
Jedná se o hru inspirovanou známou deskovou hrou Osadníci z Katanu. Cílem hry je jako první dosáhnout 10 bodů.
## Pravidla
### Začátek hry
- Na začátku si lze vybrat počet hráčů 2-4 (yellow, green, red, blue).
- Každý hráč obdrží na začátku jednu kartu od každé suroviny (brick, wood, sheep wheat, stone).
- Každý hráč obdrží na začátku příslušný počet figurek:
    - cesty: 15x
    - vesnice: 5x
    - města: 4x
- V prvním kole každý hráč umístí nejdříve jednu svoji vesnici, poté cestu, druhou vesnici a druhou cestu. Další akce nejsou povoleny. Pravidla se řídí pokyny viz Stavba.
### Průběh tahu
#### Hod kostkou
- Na počátku každého tahu dojde k hodu kostkou.
- Po hodu kostkou obdrží všichni hráči karty surovin podle padlého čísla na kostce. Množství, které hráč dostane, je určeno tím, zda se na poli s padlým číslem nachází hráčova figurka a jejím druhem. To jakou surovinu hráč dostane je pak určeno barvou pole.
- Suroviny podle barev polí jsou:
    - Červená: Brick
    - Žlutá: Wheat
    - Zelená: Wood
    - Fialová: Sheep
    - Modrá: Stone

- Množství surovin podle typu figurky:
    - za každou vesnici, jedna karta suroviny
    - za každé město, dvě karty suroviny

- Jediná vyjímka, kdy hráči neobdrží po hodu kostkou karty je pokud padne 7.
- Hráči co mají více než 7 karet ztrácí polovinu.

#### Kupování (příkaz sell)
- Hráč si ve hře může koupit a poté umístit na pole figurky typu cesta, vesnice a město. 
- Pro nákup je potřeba, aby měl hráč potřebné suroviny. Suroviny potřebné k nákupu jednotlivých figurek jsou tyto:
    - cesta: 1x brick, 1x wood
    - vesnice: 1x brick, 1x wood, 1x lamb, 1x wheat
    - město: 3x stone a 2x wheat 
- Po nákupu probíhá stavba viz Stavba.

#### Prodej (příkaz buy)
- ve hře je možné obchodovat s jinými hráči.
- napíšeme podle instrukcí na obrazovce surovinu, kterou chceme obdržet, prodat a s kým chceme obchodovat
- směna s hráči funguje 1 má surovina za 1 hráčovu
- obchod nemůže protihráč nijak odmítnout.

#### Stavba 
- Při stavbě ubývá daných figurek.
- Stavba jednotlivých figurek se řídí následujícími pravidly:
    - Nová cesta musí být napojena na město nebo vesnici.
    - Vesnice/města musí mít mezi sebou vynechané jedno místo pro vesnici/město.
    - Nová vesnice musí být napojena na cestu (vyjímkou je stavba na počátku hry).
    - Město lze postavit je upgradovaním na místě vlastní vesnice. Po stavbě města dostanu figurku vesnice zpět.
    - Nelze stavět na místě, kde má figurku jiný hráč.
- Pokud zjistím, že figurku nelze umístit nebo si hráč umístění rozmyslel, může zrušit stavbu. Zrušením stavby, ale dojde ke ztrátě surovin, které byly potřeba ke koupi. Neztratí se ale potřebná figurka. Provede se to zadáním hodnoty -1.
- Stavba samotná probíhá zobrazením nejdříve hrací plochy bez čísel ale se všemi existujícími stavbami a poté hrací plochy, kde jsou místo měst a vesnic čísla. Pro stavbu města nebo vesnice zadávám jedno číslo pro cesty zadávám odkud kam (na pořadí nezáleží).

#### Změna hráče (příkaz switch)
- Přepnout na dalšího hráče, může současný hráč tlačítek NEXT PLAYER.
- Hráče není povoleno přepnout, pokud probíhá počáteční stavění, neproběhl hod kostkou, byla koupena figurka, ale nebyla umístěna.


### Body a výhra
- Vítězem se stane ten, kdo jako první dostane 10 bodů. Hra automatick zobrazí zprávu o výhře.
- Body lze získat:
    - Stavba vesnice: 1 bod
    - Stavba města: 1 bod
   
### Statistika hráče (příkaz stats)
- zobrazí se kdo je právě na tahu a jeho informace
- jaké má figurky co nejsou na hrací ploše
- jaké má karty
- po4et bodů

