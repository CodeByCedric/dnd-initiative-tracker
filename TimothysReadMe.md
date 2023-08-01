# Een ReadMe speciaal voor Timothy

## Algemeen

Er zijn wat problement met het responiveness van de app. Nl. als je font en display size van je
telefoon op te groot staan, dan gaat er het een en ander mis.
Maar presumably ben je nog niet zo oud dat alles op gigantisch staan, dus dat zal wel meevallen.

Maakt gebruik van een API, maar moet nog een catch voorzien indien er geen netwerk is.

'k wil de "+" knop nog van kleur veranderen.

## Campaign Scherm

De eerste twee campaigns die er al in zitten, is voor het testen. Niet de bedoeling dat er standaard
twee campaigns in zitten.

## Add a New Campaign Scherm

Hier moet enerzijds nog validatie gebeuren (je kan een lege campaign creëren, wat niet de bedoeling
is).

- zijn de nodige velden ingevuld
- bestaat er al een campaign met die naam
- wil ook nog een derde knop onder de players om te selecteren uit reeds bestaande players
- voeg je players toe die al bestaan?

Anderzijds is de "select from your contacts" nog niet volledig geïmplementeerd. Je kan wel al een
contact selecteren, maar het wordt nog niet opgeslagen.

## Character Scherm

Bij het manueel invullen van iniative wordt de cursur steeds voor het ingegeven cijfer geplaatst.
Dit is niet de bedoeling, maar ik heb nog niet gevonden hoe ik dit kan oplossen.
bv. je wil 10 invullen, dan moet je 01 typen, want anders staat de cursor voor de 1 en wordt het 01.
negatieve nummers invullen ligt ook niet voor de hand (beetje een edge case, maar toch)

Als je een character hebt geselecteerd, en je duwt dan op roll initiative, dan verdwijnt de
selectie. Nog niet kunnen oplossen.

Will ook nog foto's toevoegen voor de characters, dan kan je foto's trekken van de geschilderde
mini's

## Create Character Scherm

Moet nog kijken om een manuele input toe te laten, heb het uitgezet omdat met de "+" en "-" knop je
wel gemakkelijk negatieve getallen kan ingeven.
Hier zou je ook nog moeten kunnen kiezen uit reeds bestaande characters.

De lijst aan enemies heeft plots beslist om er lang over te doen om te laden, maar het werkt wel

Zou hier ook nog het aanmaken van de characters willen scheiden van toe te voegen aan het
overzicht (gelijk aan het toevoegen van players aan een campaign, met die pillboxen, dan moet je
niet telkens heen en weer van scherm als je er een aantal wil toevoegen)

Je kan nog door naar het volgende scherm zonder dat er initiative is ingevuld. Moet ook nog op
worden gevalideerd.

## Battle Screen

Heel basic voor 't moment.

zou death saves en status effects willen toevoegen, maar dat is voor later. Ook expandable maken met
details voor de enemies (aanvallen enzo)