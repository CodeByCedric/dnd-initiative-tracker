# Todos (the important ones)

## General

!! Loss of functionality when display size and or font size of the device (phone) is set to large !!

Remove implementation of CampaignPlayerCharacterDetail since this is exactly the same as the more
recent implemented SkirmishCharacter

Move color of textfields to Color.kt and implement value for darktheme

API: implement try catch, and handle errors (e.g. no network connection = no data to populate form)

Calendar picker: de datum en tijd worden eerst gekozen, en dan doorgegeven aan de calendar, maar wat
als de gebruiker de datum en tijd nog wijzigt in de calendar

## CampaignScreen

Change font settings to materialTheme.typography.h1, h2, h3, etc.

## CharacterScreen

Change font settings to materialTheme.typography.h1, h2, h3, etc.
Tapping on roll initiative clears the selection, fix this

# Questions and Oddities

## Dimens

Waarom moet in res/values/dimens.xml de waarden als bv. 8dp worden meegeven, terwijl voor gebruik in
code, het 8.dp is
