package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant

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
}