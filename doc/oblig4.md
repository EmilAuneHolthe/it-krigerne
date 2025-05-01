# Rapport – innlevering 4

### A1: Oppsett av Git-gruppe/repo, README-fil, teamorganisering

# IT-Krigerne:

* Emil Aune Holthe / Map tester
* Martin Rønning   / Pixel-art
* Daniel Bjørnstad / Team leader
* Vemund Handeland / Custumer support

`Roller:`
* Team‑lead / Scrum Master 
    • Klargjør sprint‑mål, leder daglige stand‑ups, fjerner hindringer.
    • Rollen fungerer, men bør kunne delegeres i travle perioder.

* Kundekontakt / Product Owner 
    • Samler krav, prioriterer backlog, godkjenner leveranser.

* Map tester 
    • Finner bugs, hovedansvar for tester til kode

* Pixel art
    • Laget map, sprites og visuell retning til spillet

### A2: Beskrivelse av konsept

* Spillet er et 2D-fugleperspektiv action-RPG inspirert av The Legend of Zelda (1986).

*  Dette er et action adventure spill med RPG elementer der du må beseire den allmektige G. 
For å nå G må du kjempe deg gjennom fiender og hindre. Utforsk, samle gjenstander 
og finn hemmeligheter for å gjøre deg sterkere. Dør du så er du ute, og spillet må startes på nytt. 
Hvert run av spillet vil kreve at du finner våpen og annet loot slik at du kan beseire fiendene. 
Kan du klare denne utfordringen?

* Spillet disse spill-funksjonene:
   - Spiler kan angripe monstre.
   - Monstre som angriper spiller.
   - Spiller kan plukke opp "Items" og åpne dører.
   - "Maps" som er bygget opp ved hjelp av et program kalt "tiled".
   - Spiller kan bevege seg mellom flere "maps".
   - Spiller kolliderer med objekter og kan bevege seg bak objekter (f.eks; trær).

### A3: Prosess og prosjektorganisering

Vi har organisert prosjektet med:

`Parprogrammering som del av XP (Extreme Programming)`
Vi har brukt parprogrammering, en kjernepraksis i XP (Extreme Programming), der to utviklere samarbeider om samme kodebase på én maskin. Den ene fungerer som "driver" og skriver koden, mens den andre er "navigator" og fokuserer på strategi, gjennomgang og feil.

Fordeler:
   - Kombinerer ulik faglig kunnskap for bedre løsninger.
   - Mer motiverende og sosialt å jobbe i par.
   - Økt kvalitet og raskere feiloppdagelse gjennom kontinuerlig kodediskusjon.

Utfordringer:
   - Risiko for at én blir passiv i prosessen.
   - Stor kunnskapsforskjell kan hemme læringstempoet for den med minst erfaring.

`Idémyldring og iterativ utvikling`
   Vi har brukt iterativ utvikling, som anbefalt i både Scrum og XP, med fokus på kontinuerlig forbedring. Vi  startet med idémyldring for å finne kreative og funksjonelle løsninger sammen, og vi evaluerte designvalg fortløpende underveis.
   - Alle i gruppen bidro aktivt til ideer og konsepter.
   - Spillet reflekterer kreativiteten og kompetansen til hele teamet.
   - Løsningsvalg ble testet og evaluert gjennom hele prosessen.

`Ukentlige møter og team-synkronisering (Scrum-inspirert)`
   Vi hadde minst to fysiske møter per uke, inspirert av Scrum's stand-up-møter, for å sikre kontinuerlig fremdrift og team-synkronisering.
   - Møtene ga struktur og forpliktelse i en ellers fleksibel arbeidsform.
   - Vi kunne raskt løse blokkeringer og diskutere fremgang.
   - Fysisk tilstedeværelse styrket samarbeidet og engasjementet.

`Oppgavehåndtering med Trello (Kanban)`
   Vi brukte Trello som et digitalt Kanban-brett for å visualisere og organisere arbeidsoppgaver.
   - Vi fordelte oppgaver i kolonner som "To do", "In progress" og "Done". 
   - Trello gjorde det enkelt å delegere, følge med og oppdatere fremgang.
   - Det ga oss oversikt og hjalp oss med å holde fokus på leveranser.

`Testing og kontinuerlig forbedring`
   - Vi jobbet med testing gjennom utviklingsprosessen, i tråd med XP-prinsippet om kontinuerlig testing.
   Tester ble skrevet og kjørt underveis for å sikre stabil funksjonalitet.
   - Dette bidro til at vi raskt kunne oppdage og rette feil.

### A4: Oppsett av kodeskjelett og eksperimentering

* Mappestrukturen er bygget opp med Model-view-controller(MVC) i /src, /doc for generell dokumentasjo, 
   /src../resources for bilder, lyder, tiled map også vidre.

Minimum Viable Product (MVP):
1. Vise et spillebrett
2. Vise spiller på spillebrett
3. Flytte spiller
4. Kan angripe
5. Kan plukke opp items
6. Spiller interagerer med terreng
7. Vise fiender/monstre; de skal interagere med terreng og spiller
8. Spiller kan dø (Miste alle "healthpoints" til en fiende)
9. Mål for spillbrett (Beseire bosser)
10. Start-skjerm ved oppstart / game over

Bruekerhistrier og kjente bugs:
[BrukerHistorier](brukerhistorier.md)

### A5: Oppsummering / retrospektiv

- Se prosjektrapport nedenfor.

# DEl B:

# Lenker til prosjekt.

* Møtereferater:
[Møtereferat](møtereferat.md)

* Klassediagram:
![klassediagram](src/main/resources/Klassediagram.png)

* Trello:
https://trello.com/invite/b/67ac74696ca3c27aff52a1d7/ATTI467b528223728134f82517d0d72f93b690C1C875/inf112-it-krigerne

# Prosjektrapport/Oppsumering:

`Hva har vi gjort siden sist sprint;`
   * MVC og filstruktur:
      - Fjernet unødvendig kode
      - Flyttet metoder ut fra view som håndterer logikk.
      - Flyttet render metoder ut av model.
      - Opprettet egen kontroller for f.eks player.
      - Ryddet i resource folder
   * Spillfunksjoner:
      - Nytt map
      - Victory screen
      - Dører og nøkkler
      - Taskboard
      - Nye fiender
   * Tester
   * Spilloptimalisering

`Dette gikk bra:`
   - Godt oppmøte og høy møtefrekvens.
   - Effektiv bruk av parprogrammering.
   - Fleksibel og fungerende rollefordeling.
   - God gruppedynamikk med rom for uenighet og diskusjon.
   - Sterk fysisk tilstedeværelse og jevn fremdrift.
   - Tilpasningsdyktighet da vi valgte å endre spillideen.

`Dette kunne vi gjort bedre:`
   - Tidlig etablering av god prosjektstruktur (som MVC og filstruktur).
   - Mer aktiv bruk av digitale verktøy som Trello tidligere i prosessen.
   - Klargjøre roller og ansvar enda tydeligere ved prosjektstart.
   - Tidligere (bedre) evaluering av hvordan spillet skulle være, sluttresultatet ligner ikke det vi så for oss på starten.

`Roller og rollefordeling i teamet:`
   Vi har hatt tydelige, men fleksible roller gjennom prosjektperioden. Teamleaden har vært ansvarlig for å koordinere møter, følge opp fremdrift og sikre at alle har en oppgave å jobbe med. Kundekontakten sørget for at vi holder oss til kravene og rammene. I praksis har vi opplevd rollene som dynamiske, ofte har flere gått inn i hverandres ansvarsområder når det har vært naturlig. Dette har fungert godt for oss og bidratt til et samarbeid der alle tar ansvar.

`Erfaringer med prosjektmetodikk og samarbeid`
   Vi har brukt flere elementer fra metodikker, blant annet Kanban gjennom Trello, og XP-praksiser som parprogrammering og hyppige iterasjoner. Det ble tidlig etablert et mål om å møtes to ganger i uken, noe vi i praksis har overgått i den siste perioden før endelig produkt. Dette har styrket både gruppedynamikk og fremdrift.

   Parprogrammering har fungert veldig godt for oss: Vi har utfylt hverandres kunnskap, avdekket feil tidlig, og motivert hverandre. Dette har også skapt en felles forståelse av koden. Vi hadde en periode i starten der vi opplevde at parprogrammeringen var vanskelig starte med, men vi har løst dette veldig godt i den siste perioden.

   Vi opplever at vi har tatt gode valg underveis, selv om vi i ettertid ser at visse tekniske avgjørelser kunne vært planlagt bedre. For eksempel brukte vi tid på å endre arkitekturen til en bedre strukturert MVC-modell, noe vi kunne ha gjort fra starten. Likevel var det vanskelig å forutse nøyaktig hva vi kom til å trenge før spillideen var mer etablert, spesielt fordi ideen har utviklet seg betydelig siden oppstarten. I utgangspunktet planla vi å lage et rogue-like spill med tilfeldige elementer for hver gjennomspilling. Dette valget gikk vi etter hvert bort fra, ettersom det ville krevd mye tid og gjort prosjektet unødvendig komplisert. I stedet bestemte vi oss for å utvikle et spill med en forhåndsdefinert rute, faste fiender, "map" og "items" fremfor å generere disse tilfeldig ved hver oppstart. Vi er veldig fornøyde med at vi tok dette valget, og vi er stolte av det ferdige resultatet.

`Gruppedynamikk og kommunikasjon:`
   Gruppedynamikken har vært svært god. Vi har hatt uenigheter, men dette har ført til konstruktive diskusjoner og bedre løsninger. Ingen har vært passive – alle har bidratt aktivt. Uenigheter har blitt sett på som en ressurs snarere enn et problem.

   Kommunikasjonen har foregått gjennom flere kanaler: Snapchat har vært mest brukt for raske beskjeder, mens vi har hatt Discord og Trello som mer prosjektorienterte verktøy. I tillegg har vi hatt mange fysiske møter, og dette har vært vår mest effektive kommunikasjonsform.

`Oppsumering:`
   Prosjektet har hatt god fremdrift, med hyppige møter og effektivt samarbeid. Rollene i teamet har vært fleksible og fungert godt, og parprogrammering har styrket både kodekvalitet og læring. Selv om vi kunne planlagt strukturen bedre fra start, har vi tilpasset oss underveis og endret spillideen til noe mer gjennomførbart, noe vi er fornøyde med. Gruppedynamikken har vært sterk, med rom for diskusjon og felles eierskap til løsninger. Kommunikasjonen har fungert godt, særlig gjennom fysiske møter, selv om vi kunne brukt verktøy som Trello mer aktivt i starten













