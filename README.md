# Documentation

## Google Developer Profile

[Cedric Puystjens' Google Developer Profile](https://developers.google.com/profile/u/cedricp)

## Android Basics with Compose - Projects

- Unit
  1: [Business Card](https://git.ti.howest.be/TI/2022-2023/s4/native-mobile-apps/students/cedric-puystjens/business-card)
- Unit
  2: [Art Space App](https://git.ti.howest.be/TI/2022-2023/s4/native-mobile-apps/students/cedric-puystjens/art-space-app)
- Unit
  3: [30 Days App](https://git.ti.howest.be/TI/2022-2023/s4/native-mobile-apps/students/cedric-puystjens/30-days-app)

## Project: [Dungeons And Dragons Initiative Tracker](https://git.ti.howest.be/TI/2022-2023/s4/native-mobile-apps/students/cedric-puystjens/herexamen)

### Project Videos

- [App presentation](https://youtu.be/X3rxI7s9E3U)
- [Code showcase](https://youtu.be/eG7Jhm8tkns)

### Implemented Technical Requirements

- Build a multiscreen app (at least 4 screens) using the Jetpack Navigation component
- Use Jetpack Compose with Kotlin to build your screen layout (use at least 4 different elements
  with mandatory a scrollable List, Button, Text and Image)
- Create a menu-based navigation and bottom navigation
- Use the Android app architecture (ViewModel – State in Compose)
- Use a local Room database to store user data persistently
- Use Retrofit to communicate with an API service:

The API service used is the [D&D 5e API](https://www.dnd5eapi.co/) and is used to populate the
enemies table via a workmanager.
It is also used upon selecting an enemy from the dropdown menu on the Create a Character screen,
where it populates the form with the selected enemy's data using the API.

- Schedule at least 1 background task using the Workmanager
  Two background tasks are scheduled using the Workmanager. The first one, however, was not
  implemented by me, but by Ann Audenaert and expanded upon by me. It is used to populate the
  database in case of an empty campaign screen (i.e. there are no campaigns). This is for production
  purposes only.

The second workmanager is used to populate the database with enemies from the D&D 5e API. This is
done by first getting all the enemies from the API, then inserting them into the database. This is
done in the `EnemySyncWorker` class.

- Use at least 2 Implicit Intents

  1. Schedule next sessions (implicit intent to calendar)
  2. Pick an image from the gallery (implicit intent to gallery)
  3. Pick a contact from the contacts (implicit intent to contacts) (partially implemented, opens
     contacts, but result is not used)

- Write significant tests to make a robust app

  1. Navigation testing is implemented

- Implement a custom application theme using MaterialDesign with a custom app icon

