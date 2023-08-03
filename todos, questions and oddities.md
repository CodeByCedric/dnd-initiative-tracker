# Todos (the important ones)

## General

!! Loss of functionality when display size and or font size of the device (phone) is set to large !!

Remove implementation of CampaignPlayerCharacterDetail since this is exactly the same as the more
recently implemented SkirmishCharacter

API: implement try catch, and handle errors (e.g. no network connection = no data to populate form)

## CampaignScreen

Calendar picker: de datum en tijd worden eerst gekozen, en dan doorgegeven aan de calendar, maar wat
als de gebruiker de datum en tijd nog wijzigt in de calendar

## CreateCampaignScreen
Clarify what the required fields are
Make the email fields nullable. The email fields should not be required (+ editable from the
campaignscreen)

## CharacterScreen

Change font settings to materialTheme.typography.h1, h2, h3, etc.
Tapping on roll initiative clears the selection, fix this
Adjust calculation of column width, give every column a fraction? Right now the elements on the card
move slightly, presumably based on the name (since that's the only element which varies at the
moment)

# Questions and Oddities

## Dimens

Waarom moet in res/values/dimens.xml de waarden als bv. 8dp worden meegeven, terwijl voor gebruik in
code, het 8.dp is

## De CreateCharacterButton en validatie

In het CreateCharacterScreen is een knop om de character aan te maken, die knop is disabled als niet
alle vereiste velden ingevuld zijn, de validatie hiervan gebeurt in de viewmodel.

Voorheen gebeurde de validatie in het CreateCharacterScreen zelf, daarom dat de parameter
createCharacterUiState: CreateCharacterUiState werd meegegeven.

Omdat die parameter nu niet meer nodig is, had ik ze verwijderd, maar dan stopt de validatie ook met
werken om de een of andere reden (die geen gebruik maakt van die parameter)