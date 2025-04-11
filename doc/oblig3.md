# Rapport – innlevering 2
**Team:** *IT-Krigerne* – *Martin, Emil, Vemmund, Daniel*...

### A1: Oppsett av Git-gruppe/repo, README-fil, teamorganisering

# Navn/ Team navn

* Emil Aune Holthe / Map tester
* Martin Rønning   / Pixel-art
* Daniel Bjørnstad / Team leader
* Vemund Handeland / Custumer support

### A2: Beskrivelse av konsept

# Spillet er et 2D-fugleperspektiv action-RPG inspirert av The Legend of Zelda (1986).

* Dette er et rouge-like RGG spill der du må bekjempe flere bosses. For å nå disse bossene så må du kjempe deg gjennom fiender og hindre. Utforsk, samle gjenstander og finn hemmeligheter for å gjøre deg sterkere. Dør du så er du ute, og spillet må startes på nytt. Hvert run av spillet vil være forskjellige, med random fiender, våpen og annet loot. Kan du klare denne utfordringen?

* Kontroll: Spilleren kan bevege seg i fire retninger: opp, ned, venstre, høyre.
* Gameplay: Inneholder items, upgrades, quests, bosser og poengsystem.
* Ingen lagring: Spillet er run-based, der hver gjennomspilling starter fra begynnelsen.
* Referanse: The Legend of Zelda:  https://en.wikipedia.org/wiki/The_Legend_of_Zelda_(video_game)

### A3: Prosess og prosjektorganisering

# Vi har organisert prosjektet med:

* Parprogrammering for effektiv kodeutvikling
   Fordeler:
   - Kan hjelpe hverandre med forskjellig kunnskap.
   - Mer motiverende/morsommere å jobbe i lag med noe andre.
   - Pushe hverandre, lettere å sette krav til sidemann.

   Ulemper:
   - En i paret kan for bli passiv og ikke bidrar.
   - Hvis en i paret har mye mer kunnskap enn den andre så kan det bli vanskelig å henge med.
  
* Idémyldring for å iterere over designvalg
   - Alle sine ideer skal bli hørt.
   - Spillet skal være et resultat av kreativiteten på hele gruppen, og ikke bare noen få.
   - Finne gode løsninger på problemer som oppstår.

* Ukentlige møter (minst to ganger i uken) for oppfølging og oppdateringer
   - Pushe hverandre til å møte opp å jobbe sammen.
   - Uten fysiske ukentlige møter kan man lett bli passiv i utviklingen av spillet.
   - Hjelpe hverandre og være aktiv i samarbeid, ikke like lett over feks discoard.

* Trello for oppgavehåndtering
   - Holde et system på hva vi vil få gjort.
   - Lett å oppdatere andre hva som er gjort.

### A4: Oppsett av kodeskjelett og eksperimentering

# Vi har opprettet et grunnleggende kodeskjelett i repositoryet. Dette inkluderer:

* Mappestruktur: src/ for kildekode, assets/ for grafikk, doc/ for dokumentasjon
* Eksperimentering med rammeverk: Vi har testet ulike verktøy og biblioteker for å håndtere 2D-grafikk og spillmotor
* Foreløpig utvikling av spillkart og bevegelsessystem

Krav til MVP
1. Vise et spillebrett
2. Vise spiller på spillebrett
3. Flytte spiller
4. Kan angripe
5. Kan plukke opp items
6. Spiller interagerer med terreng
7. Vise fiender/monstre; de skal interagere med terreng og spiller
8. Spiller kan dø (Miste alle “healthpoints” til en fiende)
9. Mål for spillbrett (Beseire bosser)
10. Start-skjerm ved oppstart / game over

Krav og spesifikasjon ovenfor MVP:
- Punkt 1 til 3, 6 og 10 er løst, men kan videreutvikles. Vi har ikke fått inn animasjoner, spillbrettet er ikke ferdigutviklet og vi har ingen game-over.
- Punkt 5, 7, 8 og 9 er ikke løst. Disse punktene er høyeste prioritet fremover. Disse er basic funksjoner i spillet vårt og må være på plass.

Brukerhistorie: Som en ny spiller ønsker jeg å forstå hvordan spillet fungerer.
Akseptansekriterie: Controls enkelte kunne vises på main menu. Spillet skal ha enkle funksjoner i starten slik at det er lett å forstå.
Arbeidsoppgave: Implementere controls i main menu.

Brukerhistorie: Som en spiller ønsker jeg å forstå tydelig hva som befinner seg på spillerbrettet.
Akseptansekriterie: Enkle, men detaljerte sprites.
Arbeidsoppgave: Implementere tilemap som enkelt kan endres med tydelige sprites.

Brukerhistorie: Som en spiller ønsker jeg å kunne bevege meg opp, ned, venstre og høyre.
Akseptansekriterie: Karakteren må kunne bevege seg i alle nødvendige retninger.
Arbeidsoppgave: Implementere kontroller som styrer bevegelse for karakteren og gode animasjoner.

Brukerhistorie: Spiller interagerer med terreng.
Akseptansekriterie: Spiller skal kunne bli stoppet ved å bevege seg i vegger, fiender også videre.
Arbeidsoppgave: Implementere box2d og få det til å fungere i samspill med hitboxes til sprites.

### A5: Oppsummering / retrospektiv

- Se prosjektrapport nedenfor.

# DEl B:

`TEAM-MØTER`

# 03.03.2025: Martin, Emil

* Hva gjorde vi:

Endret spillet til liggdx
- Vi brukte først jframe, men vi bestemte oss på dette møte å bytte til libgdx
- Satte opp basic funksjon for å bytte mellom screens (loadingscreen, gamescreen ect)

* Hva skal vi gjøre neste gang:

Videreutvikle basic funksjoner i spillet:
- Bevege på karakter
- Main menu
- Collison med spiller

# 07.03.2025: Martin, Emil, Daniel, Vemund

* Hva gjorde vi:

Ferdigstilte MVP:
- Fikk collision til å funke med box2d, men ikke klart å implementert det med tile-mappet vårt enda.
- Lå grafikk for spillerkart og spiller

* Hva skal vi gjøre neste gang:

Videreutvikle funksjoner i spillet:
- tile-map
- box2d kollisjon med tilemap

# 08.03.2025: Emil, Daniel, Vemund

* Hva gjorde vi:

Ferdigstilte MVP:
- Lagde spillerkart med tilemap-editor som at man enkelt kan endre mappet senere.
- Main menu med startknapp
- Lå inn kollisjon med spiller og tile.mappet, men støtter på bugs.

* Hva skal vi gjøre neste gang:

Videreutvikle funksjoner i spillet:
- Fikse kollisjon bugs
- Oppdater oblig2.md

# 09.03.2025: Martin, Emil, Daniel, Vemund

* Hva gjorde vi:

Ferdigstilte MVP:
- Spiller kan bevege seg med kollisjon til tile-mappet uten problemer

Rapport skriving
- Ferdig stilte oblig2.md der vi diskuterte alle punktene. Kom frem til ting som funket og ikke funket.
God diskusjon.

* Hva skal vi gjøre neste gang:

Videreutvikle funksjoner i spillet:
- Jobbe med MVP punktene som vi ikke ble ferdig med.

# 20.03.25: Vemund, Martin, Emil

* Hva gjorde vi:

- Startet å jobbe med entity kontroller og hvordan vi tar inn bruker innput

- Laget kollisjon på tile kartet

- Startet prosessen med  mocke ulike klasser, slik at de kunne bli testet uten at spillet kjørte. 

* Hva skal vi gjøre neste gang:

- Fikse MVC, altså filstrukturen. Få relevant kode inn i deres allegerte plass

- Lage entity kontroller

* Hva skal vi gjøre neste gang

- Lage flere sprites til player + enemys

- Implementere healthbar og toolslot i GameScreen.

- 


# 24.03.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Fikset filstruktur og fikk laget litt mer tester. 

- Implementerte entity kontroller

- Implementert healthbar og toolslot i GameScreen


* Hva skal vi gjøre neste gang

- Begynne på dynamisk Healthbar koblet opp til livet til spilleren.

# 26.03.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Begynt på dynamisk Healthbar koblet opp til livet til spilleren.

* Hva skal vi gjøre neste gang

- Starte å jobbe på egne klasser for spiller og enemies

# 27.03.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Jobbet med egne klasser for spiller og enemies

- Fikset litt på grafikken for gøy og lagde flere metoder for entity interfacet

- Jobbed med GameScreen for å implementere nye endringene for Player klassen

- smp endringer og fiksing av tester

- Implementert Healthbar, samsvarer med playerhealth

- Lagt til bakgrunn på healthbar og tallene på healthbar

- Lagt til sound effect når du tar skade.

- Fikset krasj ved map-bytte ved å filtrere duplikate punkter i ChainShape

* Hva skal vi gjøre neste gang

# 07.04.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi



- Ryddet opp i gameScreen. Laget ny klasse for å lage player, og ny klassse for å render spillet.
- Kan nå lage og vise en enemy. men man kan ikke interact med denne enemien
- Lagt til death screen, måte å komme seg tilbake til main menu etter død og resetting av spillet når en dør.
- Gjort litt endringer slik at man kan ha flere enemies på mappet samtidig
- Implimentert at fiender kan ta skade og dø
- startet å jobbe med å få fiender til å bevege seg
- startet å jobbe med fienders angrep

* Hva skal vi gjøre neste gang

- Jobbe videre med enemys, slik at de kan angripe + bevege seg
- Kategorisere enemys, slik at vi kan gi dem ulike egenskaper
- Finpusse kode, dele den mer opp
- Starte implementasjonen av items

# 09.04.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Fysisk møte med hele gruppen hvor vi delte ideer og implementerte dette

- Fikset slik at vi kan lage ulike enemys på map
- At de kan ha ulik mengde liv ut i fra hvilke enemy de er
- Fjernet key handler fra game entity
- Fjernet unødvendige imports og fikset i sonarqube
- Ga animation til enemys
- Prøvde oss på items
- Fått inn at enemys kan bevege seg mot player + angripe

* Hva skal vi gjøre neste gang

- Fortsette på dannelsen av items
- Fortsette med UI (inventory + health + tiledmap)
- Fikse map change, ettersom det krasjet
- 

# 10.04.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Satt fysisk sammen, hele gruppen
- Litt programmering hver for oss med felles møter, etterfulgt av parprogrammering på noen ac pcéne

- Fått til å lage en boss
- Forbedret UI, healthbar til player og enemy´s
- Satt lenge med items. Prøver å implementere det på en måte som minner om dannelsen av characters
- Fikse map change, ettersom det krasjet

* Hva skal vi gjøre neste gang

-



# Prosjektrapport/Oppsumering:

`Hva har fungert bra?`
* God kjemi mellom gruppemedlemmene
* Jobbet ukentlig
* Lært masse om libgdx
* Vi lærte om hvilke prosesser som gjøres
* God kommunikasjon, meldinger over snapchat og dicoard er effektivt og fungerer godt.

`Hva gikk dårlig`
* Ikke fått jobbet så mye vi hadde ønsket, flere på gruppen hadde flere konteeksamener som har blitt prioritert de siste ukene.
* Har ikke kommet skikkelig i gang med parprogrammeringen. Mye på grunn av at vi ikke har fått møtt hverandre like mye som vi hadde ønsket før eksamenene vi hadde. Den siste uken har parprogammeringen vært effektiv derimot.
* Vi har ikke fått kontakt med han siste på gruppen etter flere forsøk. Ser han er fjernet fra gruppen vår på mitt Uib så vi regner med dette er tatt hånd om.
* Box2d slet vi lenge med å få implementert og vi har ikke enda klart helt å få kollisjon funksjon inn med i spillet.
* Ikke klart å implementere alt vi ville i fra vår MVP.


`Oppsumering:`
* Vi har ikke skikkelig fått testet rollene vi har satt, da vi i starten har jobbet jevnt med forskjellige deler av spillet for å få til en grunnleggende MVP. Dette har ført til at alle har tatt på seg oppgaver utenfor sine tiltenkte roller. For eksempel har det vært utfordrende å være map-tester uten et ferdig map å teste, og det har vært vanskelig å jobbe med pixel art uten å ha grunnleggende funksjonalitet på plass i spillet. Likevel har vi tro på at de rollene vi nå har definert, er gode, og vi forventer å få mer nytte av dem fremover. Kommunikasjonen har vært god, og vi samarbeider godt. Vi er flinke til å lytte til hverandre, og alle kan komme med innspill. Spesielt har Snapchat vist seg å være en effektiv kanal for kommunikasjon. Selv om vi er fornøyde med det vi har oppnådd så langt og har lagt en solid grunnmur vi er motiverte til å bygge videre på, har vi ikke nådd alle målene vi satte for MVP. Hovedårsaken til dette er at vi hadde eksamener mellom oblig1 og oblig2, noe som begrenset tiden vi kunne bruke på prosjektet. Likevel er vi motiverte til å ta igjen den tapte tiden og jobbe for å nå målene våre så raskt som mulig.

`Forbedringer til neste sprint:`
* Ferdigstille MVP
* Spillet skal være gjennomførbart og skal kunne spilles som et spill. Det skal ha et sluttmål med utfordringer underveis. Få inn animasjoner, fiender og måter å angripe på. Dette skal være en god base som vi skal kunne viderebygge på. (En bedre versjon av MVP)
* Flinkere til å notere underveis, altså møte-referat, trello og skrive ned nye mål.











