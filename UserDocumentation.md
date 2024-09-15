# Uživatelská dokumentace
## Úvod
Jedná se o hru inspirovanou známou deskovou hrou Osadníci z Katanu. Cílem hry je jako první dosáhnout 10 bodů.
## Pravidla
### Začátek hry
- Na začátku si lze vybrat počet hráčů 2-4 (yellow, white, red, blue).
- Každý hráč obdrží na začátku jednu kartu od každé suroviny (brick, wood, sheep wheat, stone).
- Každý hráč obdrží na začátku příslušný počet figurek:
    - cesty: 15x
    - vesnice: 5x
    - města: 4x
- V prvním kole každý hráč umístí nejdříve jednu svoji vesnici, poté cestu, druhou vesnici,  a druhou cestu. Další akce nejsou povoleny. jakmile vše dokončí dá tlačítko Switch, aby se přepnul hráč. Pravidla umístění se řídí pokyny viz Stavba.
### Průběh tahu
#### Hod kostkou
- Na počátku každého běžného tahu (ne umisťování figurek na začátku hry) dojde k hodu kostkou.
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

#### Kupování
- Hráč si ve hře může koupit a poté umístit na pole figurky typu cesta, vesnice a město. 
- Pro nákup je potřeba, aby měl hráč potřebné suroviny. Suroviny potřebné k nákupu jednotlivých figurek jsou tyto:
    - cesta: 1x brick, 1x wood
    - vesnice: 1x brick, 1x wood, 1x lamb, 1x wheat
    - město: 3x stone a 2x wheat 
- Nákup se provede stisknutím příslušného tlačítka ve spod obrazovky začínající slovem buy
- Po nákupu probíhá stavba viz Stavba.

#### Prodej
- ve hře je možné obchodovat s jinými hráči.
- použijeme tlačítko dole na obrazovce a vybereme surovinu, co chceme prodat.
- poté se objeví nabídka všech hráčů ve hře a my vybereme toho s kým chceme směňovat a po výběru se objeví možnosti, co od hráče můžeme obdržet po stisknutí je obchod dokončen.
- směna s hráči funguje 1 má surovina za 1 hráčovu
- obchod nemůže protihráč nijak odmítnout.
- na oknech je viditelné tlačítko cancel, kterým se můžeme vrátit na hrací plochu bez toho, aby obchod proběhl.

#### Stavba 
- Při stavbě ubývá daných figurek.
- Stavba jednotlivých figurek se řídí následujícími pravidly:
    - Nová cesta musí být napojena na město nebo vesnici.
    - Vesnice/města musí mít mezi sebou vynechané jedno místo pro vesnici/město.
    - Nová vesnice musí být napojena na cestu (vyjímkou je stavba na počátku hry).
    - Město lze postavit je upgradovaním na místě vlastní vesnice. Po stavbě města dostanu figurku vesnice zpět.
    - Nelze stavět na místě, kde má figurku jiný hráč.
- Stavbu nelze zrušit.
- Stavba samotná probíhá kliknutím na tlačítko na šestiúhelníkové hrací ploše. Pokud je místo validní, objeví se stavba, pokud ne, nic se neobjeví a je třeba vybrat jiné tlačítko.
- Pro stavbu města a vesnice se vybírá právě jedno tlačítko. Pro stavbu cesty je třeba stisknou dva tlačítka za sebou, to je začátek a konec cesty, pokud jsou tlačítka nevalidní, je třeba vybrat dva od znova, jako by k předchozímu výběru nedošlo.

#### Změna hráče
- Přepnout na dalšího hráče, může současný hráč tlačítkem switch.
- Hráče není povoleno přepnout, pokud probíhá počáteční stavění, byla koupena figurka, ale nebyla umístěna.


### Body a výhra
- Vítězem se stane ten, kdo jako první dostane 10 bodů. Hra automatick zobrazí zprávu o výhře.
- Body lze získat:
    - Stavba vesnice: 1 bod
    - Stavba města: 1 bod
   
### Statistika hráče
- vlevo lze vidět informace o tom, kdo je právě na tahu a jeho informace
- jaké má figurky co nejsou na hrací ploše
- jaké má karty
- počet bodů

