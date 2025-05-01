# Inventory-system:

* Kan bruke items: (Implementert)

    Brukerhistorie: 
    Som spiller ønsker jeg å ha en oversikt over hvilke items jeg har plukket opp, slik at jeg vet hva jeg kan bruke og når.

    Akseptansekriterier: 
    Når spilleren plukker opp items vises de i Inventory.
    Inventory skal alltid vises enten på bunnen av skjermen eller vises ved å f.eks trykke «I».
    Spilleren kan se «healing-potion», «mana-potion», sverd og rustning i inventory.
    Inventory skal oppdateres korrekt ved plukk opp og bruk av items.

    Arbeidsoppgaver:
    Lage datastruktur for Items (gjort)
    Koble når spiller plukker opp items til inventoryLage GUI som viser inventory
    Legge til logikk for bruk av items (i gang)
 
# Fiender:

    Kjente bugs:
    Fiender kan bli sittende fast bak gjenstander (har ikke logikk til å gå rundt)
    Fiender kan bli angrepet gjennom collsion la

* Implementerte fiender med angrep og skade: (Implementert)

    Brukerhistorie: Som spiller ønsker jeg at fiendene skal kunne angripe meg og ta skade, slik at jeg må være forsiktig og strategisk i møte med dem.

    Akseptansekriterier:
    Fiender har angrepsanimasjoner og lydeffekter som viser deres angrepsmønstre.
    Spilleren mottar skade når de blir truffet av fiendens angrep.
    Fiender kan ta skade fra spillerens angrep og har synlig helsestatus.
    Når fiendenes helse går til null, dør de og fjernes fra spillet.

    Arbeidsoppgaver:
    Implementere angrepslogikk og kollisjonshåndtering for fiender.
    Lage animasjoner og lydeffekter for fiendens angrep.
    Implementere helsestyringssystem for fiender.
    Teste og feilsøke fiendens angreps- og helsehåndtering for å sikre en jevn spillopplevelse.

* Bossfight: (Implementert)

    Brukerhistorie: 
    Som spiller ønsker jeg en utfordrende og brukervennelig bossfight, slik at det føles som en ekte finale i spillet.

    Akseptansekriterier:
    Bossene kan angripe og bli angrepet.
    Bossen beveger seg på et eget designet kart
    Bossen dør og utløser en klar seier når livet går til 0

    Arbeidsoppgaver:
    Oppdatere kartet for bossen
    Designe og implementere angrep
    Lage en vinner skjerm som spilles av når bossen dør.


# Spillforståeøse

* En type tutorial: (implementert)

    Brukerhistorie: 
    Som spiller vil jeg vite hva som er målet med spillet og hva ulike items gjør, slik at jeg forstår hva jeg bør gjøre og hvorfor jeg bør plukke opp items.

    Akseptansekriterier:
    Spillet bør vise en plass hva items gjør.
    Spillet bør vise til spiller hvordan man åpner en dør (tutorial).
    Spilleren får en kort forklaring (f.eks via tooltips, tekst eller ikonforklaring) når de plukker opp items.
    Det er en indikasjon (tekst, ikon, oppgave) på at målet er å finne nøkkelen for å komme til bossen.

    Arbeidsoppgaver:
    Lagee et bilde i settings som viser hva items gjør.
    Legge til en dør tidlig i spillet slik at spiller forstår hvordan dører åpnes.
    Lagge til en NPC eller en tavle viser hva du skal gjøre målet

* Tydelig map: (Implementert)

    Brukerhistorie: 
    Som en spiller ønsker jeg å forstå tydelig hva som befinner seg på spillerbrettet.

    Akseptansekriterie:
    Enkle, men detaljerte sprites.
    Plassere items på en måte at spiller kan tydelig se de.
    Ting som spiller kan itereagere med ser likt ut for hver gang, slik at spiller enkelt fortår dette ting de kan itereagere med. Items, dører, stiger og bruer er enkelt å forstå akkurat hva det er.

    Arbeidsoppgave: 
    Implementere tilemap som enkelt kan endres med tydelige sprites.
    Implemntere en måte å "rendre" items.

* Startskjerm med innstillinger og startknapp: (Implementert)

    Brukerhistorie: 
    Som spiller ønsker jeg å kunne navigere startskjermen for å forstå spillkontrollene og starte spillet.

    Akseptansekriterier:
    Startskjermen viser tydelig en "Start" knapp for å begynne spillet.
    En "Settings" knapp er tilgjengelig for å gi spillkontrollinformasjon og justere innstillinger.    
    Trykke på "Start" knappen fører spilleren til spillets hovedscenario eller introduksjonssekvens.
    Trykke på "Settings" knappen viser en oversikt over spillkontroller og andre relevante innstillinger.

    Arbeidsoppgaver:
    Implementere knapper og visuelle elementer for "Start" og "Settings".
    Utforme og implementere en skjerm for spillkontroller og innstillinger.
    Koble "Start" knappen til at spillet startes.

* Spillmål og progresjon: (Implementert)

    Brukerhistorie:
    Som spiller ønsker jeg å forstå hva målet med spillet er, slik at jeg vet hvordan jeg skal komme meg videre og hva jeg må gjøre for å vinne.

    Akseptansekriterier:
    Spillet kommuniserer tidlig at målet er å komme seg videre til neste område (map) ved å bekjempe fiender og finne en nøkkel.
    Spilleren får en beskjed når han har kommet seg vidre, enten ved lyd eller visuellt.
    Det er tydelig i bossfighten, og at målet da er å beseire bossen for å vinne spillet.
    Når bossen er beseiret, vises en seier-skjerm eller animasjon som bekrefter at spillet er fullført.

    Arbeidsoppgaver:
    Legge til visuell eller tekstlig informasjon i starten av spillet som forklarer målet (f.eks. tavle, NPC, dialog, tekst-popup).
    Implementere logikk for å låse opp neste map.
    Lage en lyd når du plukker opp nøkkel for å gi en følelse av progresjon.
    Lage en tydelig avslutning til bossfighten, seier-skjerm.

# Karakter

* Bevegelse: (Implementert)

    Brukerhistorie: 
    Som en spiller ønsker jeg å kunne bevege meg opp, ned, venstre og høyre.

    Akseptansekriterie: 
    Karakteren må kunne bevege seg i alle nødvendige retninger.

    Arbeidsoppgave: 
    Implementere kontroller som styrer bevegelse for karakteren og gode animasjoner.

* Kollisjon: (Implementert)

    Brukerhistorie:
    Spiller interagerer med terreng.

    Akseptansekriterie:
    Spiller skal kunne bli stoppet ved å bevege seg i vegger, fiender også videre.

    Arbeidsoppgave:
    Implementere box2d og få det til å fungere i samspill med hitboxes til sprites.

* Lage GUI for valg av karakter (Ikke blit implementert)

    Brukerhistorie: 
    Som spiller ønsker jeg å kunne velge hva slags karakter jeg skal bruke før spillet begynner.

    Akseptansekriterier:
    Spillet viser karakteren som brukeren har valgt, og animasjoner og sprites blir satt til riktig karakter.

    Arbeidsoppgaver:
    Lage en meny før spillet begynner hvor brukeren kan velge karakter
    Lage GUI for valg av karakter
    Sette spillerkarakter ut ifra valget



