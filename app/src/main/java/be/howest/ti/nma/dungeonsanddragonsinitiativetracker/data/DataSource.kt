package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign

object DataSource {
    val campaigns = listOf(
        Campaign(campaignName = "Curse of Strahd"),
        Campaign(campaignName = "Ghosts Of Saltmarsh")
    )
}