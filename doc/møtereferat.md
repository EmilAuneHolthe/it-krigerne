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

- Begynne på siste mappet. 
- lage hotbar (altså noe som viser hvilke item vi holder på, f.eks. sverd type)
- Fikse i sonarqube feil

# 24.04.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi 

- Startet prosessen med dannelen av nytt map
- Bestemte oss for at dette mappet blir et mellomledd mellom start mappet og boss mappet til slutt, i stedet for enda et bossmap
- Fjernet litt hardcoding og gjorde byttingen av sverd dynamisk

* Hva skal vi gjøre neste gang

- Jobbe videre med mana implementasjon
- Oppdatere test skeleton, i henhold til oppdatert logikk
- Legge til key_counter og koble dette opp mot player.hasKeys
- Se på plassering av key og key counter.

# 25.04.25: Emil, Martin, Vemund, Daniel

* Hva gjorde vi

- Løste bugs, rettet opp mot mana (visuelt sammenlignet med logikk)
- Fikk oppdatert testene til å teste ny logikk
- Fikk rettet opp i keys og plassering på skjerpen. 

* Hva skal vi gjøre neste gang

- Gjøre ferdig det mellomledd mappet
- Få inn riktig implementasjon av dører/kollisjon med dører
- Få til at aktuelle dører åpnes med key i inventory, slik at man kan komme seg til neste map. 
- Få inn et minimap/taskboard med spawn, som viser til de ulike items vi må samhandle med
- Fullføre game-loopén slik at vi kan fullføre spillet. 
- Legge til resterende sound effects

# 27.04.25: Emil, Daniel, Vemund, Martin

* Hva gjorde vi

- Gjorde ferdig mellomledd mappet + bossmappet
- Game-loopen er nå fullført
- Rendering og logikken med dører, funker som forvnetet
- Etter mye frem og tilbake, fungerer minimap/taskboard + vises om man står på designert område
- Diverse sound effects er lagt inn (angripe, bli angrepet, interact med gjenstander, bakgrunnsmusikk)


* Hva skal vi gjøre neste gang

- Starte prosessen med å finpusse på koden. Se på tilbakemelding på oblig3, for å fikse på potensielle problemer
- Se over nylig implementert kode, fikse på bugs og få koden så generisk som mulig. 
- Oppdatere sprites (player, enemy´s, sword upgrades og items). 
- Oppdatere settings-screen, til å bli mer en (how to play) screen + keybinds

# 28.04.25: Emil, Daniel, Vemund, Martin

* Hva gjorde vi

- Fikk tilbakemelding om å fikse på MVC og SOLID prinsippene, så startet denne prosessen
- FIkset opp i små bugs (visuelt og logisk)
- Oppdatert de aktuelle spriteséne vi skulle endre. 
- Startet på å fikse opp i settings screen. 

* Hva skal vi gjøre neste gang


- Starte prosessen med å flytte rundt på logikk, for å forbedre strukturen i koden i henhold til MVC og SOLID. 
- Legge til Victory screen, med achievement. 
- Oppdatere readMe og oblig4.md

# 29.04.25: Emil, Daniel, Vemund, Martin

* Hva gjorde vi

- Endret GameScreen og WorldFunctions for bedre mvc. Nå er funksjoner flyttet ut av gamescreen og inn i worldfunctions
- Oppdatert readMe og pom.xml fil. Har også endret på resoruce fil struktur.
- Laget ny egen fil til møtereferat og linket til den i oblig4.
- Fikset bug med knaper i main meny.
- Lagt inn victory screen. Game loopén er offisielt i boks. 

# 30.04.25: Emil, Daniel, Vemund, Martin

* Hva gjorde vi

- Vi ønsker å få innsyn av gruppeleder, i håp om å få hjelp oppmot perfeksjon angående MVC/SOLID prinsippene


* Hva skal vi gjøre neste gang

- 

