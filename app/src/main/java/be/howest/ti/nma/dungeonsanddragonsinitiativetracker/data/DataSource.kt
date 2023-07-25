package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail

object DataSource {
    val campaigns = listOf(
        Campaign(
            campaignName = "Curse of Strahd",
            campaignImageDrawable = R.drawable.curse_of_strahd
        ),
        Campaign(
            campaignName = "Ghosts Of Saltmarsh",
            campaignImageDrawable = R.drawable.ghosts_of_saltmarsh
        )
    )
    val participants = listOf(
        Participant(
            participantName = "Tim",
            email = "tim@tim.be",
            isDungeonMaster = true
        ),
        Participant(
            participantName = "Laura",
            email = "laura@laura.be"
        ),
        Participant(
            participantName = "Eveline",
            email = "eveline@eveline.be"
        ),
        Participant(
            participantName = "Cedric",
            email = "cedric@cedric.be"
        ),
    )

    val playerCharacters = listOf(
        PlayerCharacter(
            name = "Ibun",
            armorClass = 16,
            initiativeModifier = -2,
            isPrimaryCharacter = true
        ),
        PlayerCharacter(
            name = "Rhys",
            armorClass = 18,
            initiativeModifier = 1,
            isPrimaryCharacter = true
        ),
        PlayerCharacter(
            name = "Tinuviel",
            armorClass = 14,
            initiativeModifier = 2,
            isPrimaryCharacter = true
        ),
        PlayerCharacter(
            name = "Stool",
            armorClass = 12,
            initiativeModifier = 0,
            isSecondaryCharacter = true,
        ),
        PlayerCharacter(
            name = "Sushi",
            armorClass = 14,
            initiativeModifier = 1,
            isSecondaryCharacter = true,
        ),
        PlayerCharacter(
            name = "Hemmeth",
            armorClass = 16,
            initiativeModifier = 0,
            isSecondaryCharacter = true,
        ),
        PlayerCharacter(
            name = "Ann",
            armorClass = 20,
            initiativeModifier = +5,
            isEnemy = true,
        ),
        PlayerCharacter(
            name = "Koen",
            armorClass = 20,
            initiativeModifier = +5,
            isEnemy = true,
        ),
    )

    val skirkmishScreenCharacters: List<CampaignPlayerCharacterDetail> = listOf(
        CampaignPlayerCharacterDetail(
            name = "Ibun",
            armorClass = 16,
            initiativeModifier = -2,
            initiative = 13,
            isPrimaryCharacter = true
        ),
        CampaignPlayerCharacterDetail(
            name = "Rhys",
            armorClass = 18,
            initiativeModifier = 1,
            initiative = 11,
            isPrimaryCharacter = true
        ),
        CampaignPlayerCharacterDetail(
            name = "Tinuviel",
            armorClass = 14,
            initiativeModifier = 2,
            initiative = 14,
            isPrimaryCharacter = true
        ),
        CampaignPlayerCharacterDetail(
            name = "Stool",
            armorClass = 12,
            initiativeModifier = 0,
            initiative = 15,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Sushi",
            armorClass = 14,
            initiativeModifier = 1,
            initiative = 3,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Hemmeth",
            armorClass = 16,
            initiativeModifier = 0,
            initiative = 8,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Eldeth",
            armorClass = 16,
            initiativeModifier = 0,
            initiative = 14,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Rumpadump",
            armorClass = 16,
            initiativeModifier = 0,
            initiative = 8,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Topsy",
            armorClass = 16,
            initiativeModifier = 0,
            initiative = 8,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Turvy",
            armorClass = 16,
            initiativeModifier = 0,
            initiative = 8,
            isSecondaryCharacter = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Ann",
            armorClass = 20,
            initiativeModifier = +5,
            initiative = 25,
            isEnemy = true,
        ),
        CampaignPlayerCharacterDetail(
            name = "Koen",
            armorClass = 20,
            initiativeModifier = +5,
            initiative = 25,
            isEnemy = true,
        ),
    )


}
