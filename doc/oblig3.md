# Rapport – Innlevering 2

**Team:** *IT-Krigerne*  
**Medlemmer:** Martin, Emil, Vemund, Daniel

---

### A1: Oppsett av Git-gruppe/repo, README-fil, teamorganisering

#### Navn / Teamroller

- Emil Aune Holthe – Map tester  
- Martin Rønning – Pixel-art  
- Daniel Bjørnstad – Team leader  
- Vemund Handeland – Customer support

---

### A2: Beskrivelse av konsept

#### Spillet

Spillet er et 2D-fugleperspektiv action-RPG inspirert av *The Legend of Zelda (1986)*.

Dette er et action-adventure med RPG-elementer, der du må bekjempe flere bosser. For å nå disse bossene må du kjempe deg gjennom fiender og hindre. Utforsk, samle gjenstander og finn hemmeligheter for å gjøre deg sterkere.

- **Dør du, må spillet startes på nytt.**
- **Hvert run** krever at du finner våpen og loot for å kunne vinne.

##### Kontroll og gameplay

- Spilleren kan bevege seg i fire retninger: opp, ned, venstre, høyre.
- Inneholder items, upgrades, quests, bosser og poengsystem.
- Run-based: ingen lagring – hver gjennomspilling starter på nytt.
- Referanse: [The Legend of Zelda (Wikipedia)](https://en.wikipedia.org/wiki/The_Legend_of_Zelda_(video_game))

---

### A3: Prosess og prosjektorganisering

#### Verktøy og metoder

- **Parprogrammering**
  - Fordeler: kunnskapsdeling, motivasjon, ansvar
  - Ulemper: ubalansert kunnskapsnivå kan skape utfordringer

- **Idémyldring**
  - Alle skal høres
  - Skape et kreativt sluttprodukt

- **Ukentlige møter**
  - Minst to møter i uka
  - Bedre samarbeid og fremdrift

- **Trello**
  - Holder oversikt over oppgaver og fremdrift

---

### A4: Oppsett av kodeskjelett og eksperimentering

#### MVP

Vi har opprettet et grunnleggende kodeskjelett og fullført følgende MVP-punkter:

1. Vise spillebrett  
2. Vise spiller på spillebrett  
3. Flytte spiller  
4. Angripe  
5. Plukke opp items  
6. Interaksjon med terreng  
7. Fiender vises og fungerer  
8. Spiller kan dø  
9. Spillet har mål (bosser)  
10. Start- og Game Over-skjerm

#### Pågående arbeid: Inventory System

**Brukerhistorie:** Som spiller ønsker jeg å ha oversikt over hvilke items jeg har.

**Akseptansekriterier:**

- Items vises i inventory når de plukkes opp
- Inventory vises alltid eller ved knappetrykk (f.eks. “I”)
- Viser potion, sverd, rustning, etc.
- Inventory oppdateres korrekt

**Arbeidsoppgaver:**

- [x] Lage datastruktur
- [x] Koble til item-pickup
- [ ] Lage GUI
- [ ] Logikk for bruk

---

#### Forbedringer til bossfight

**Brukerhistorie:** Som spiller ønsker jeg en variert og utfordrende bossfight.

**Akseptansekriterier:**

- Flere angrepsmønstre
- Designet bosskart
- Økende vanskelighetsgrad
- Klar seier ved 0 HP

**Arbeidsoppgaver:**

- Oppdatere kart
- Implementere angrepsmønstre
- Triggere og animasjoner

---

#### Klart mål og forståelse av items

**Brukerhistorie:** Som spiller vil jeg vite hva ulike items gjør og hva målet er.

**Akseptansekriterier:**

- Melding ved pickup av nøkkel
- Forklaring/tooltips ved pickup
- Indikasjon på mål (tekst, ikon, oppgave)

**Arbeidsoppgaver:**

- Tutorialtekst ved start eller nøkkel-pickup
- Beskrivelse for hjerte/mana
- NPC/tavle med mål

---

#### Karaktervalg

**Brukerhistorie:** Som spiller ønsker jeg å velge karakter i starten.

**Akseptansekriterier:**

- Valgt karakter vises med riktig sprite og animasjon

**Arbeidsoppgaver:**

- Meny for valg
- GUI
- Oppsett av spiller basert på valg

---

#### Kjente bugs

- Man kan plukke opp liv med fullt liv  
- Fiender setter seg fast  
- Inventory mangler bruk-funksjon  
- Bossfight ikke ferdig

---

#### Prioriteringer fremover

- Mer dybde og variasjon i gameplay
- Fokus på inventory og bossfight

---

### Brukerhistorier (kort oppsummert)

- Tydelige sprites og tilemap
- Bevegelse i fire retninger
- Interaksjon med terreng via Box2D

---

### A5: Oppsummering / Retrospektiv

**Se prosjektrapport nedenfor.**

---

# DEL B – TEAMMØTER

## 03.03.2025 – Martin, Emil

**Gjort:**

- Byttet fra JFrame til LibGDX
- Oppsett for screens

**Neste:**

- Bevegelse
- Meny
- Kollisjon

---

## 07.03.2025 – Martin, Emil, Daniel, Vemund

**Gjort:**

- MVP ferdig
- Box2D kollisjon OK, ikke implementert med tilemap
- Grafikk lagt til

**Neste:**

- Tilemap + kollisjon

---

## 08.03.2025 – Emil, Daniel, Vemund

**Gjort:**

- Tilemap-editor brukt
- Meny med startknapp
- Delvis kollisjon, bugs

**Neste:**

- Fikse bugs
- Oppdatere oblig2.md

---

## 09.03.2025 – Hele gruppa

**Gjort:**

- Bevegelse + kollisjon OK
- Skrev ferdig rapport

**Neste:**

- Jobbe med uferdige MVP-punkter

---

## 20.03.2025 – Vemund, Martin, Emil

**Gjort:**

- Entity controller + brukerinput
- Kollisjon
- Mocking og testing

**Neste:**

- Rydding av filstruktur
- Lage flere sprites
- Implementere UI

---

## 24.03.2025 – Alle

**Gjort:**

- Filstruktur og tester
- Entity controller
- Healthbar og toolslot

**Neste:**

- Dynamisk healthbar

---

## 26.03.2025 – Alle

**Gjort:**

- Start på dynamisk healthbar

**Neste:**

- Lage egne klasser for spiller og fiender

---

## 27.03.2025 – Alle

**Gjort:**

- Implementert egne klasser
- Grafikk og metoder
- GameScreen + Player
- Tester og animasjoner
- Sound effects
- Fikset ChainShape bug

**Neste:**

- Mer fiende-arbeid

---

## 07.04.2025 – Alle

**Gjort:**

- GameScreen-opprydding
- Ny player- og rendererklasse
- Death screen
- Flere fiender på mappet
- Skade og død for fiender
- Fiende-bevegelse og angrep påbegynt

**Neste:**

- Fiende-kategorisering
- Oppstart av items
- UI + map change

---

## 09.04.2025 – Alle

**Gjort:**

- Fysisk møte
- Flere typer fiender
- U
