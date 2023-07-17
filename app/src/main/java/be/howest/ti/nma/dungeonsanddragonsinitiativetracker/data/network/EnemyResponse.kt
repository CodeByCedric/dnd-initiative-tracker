package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy

data class EnemyResponse(
    val count: Int,
    val results: List<Enemy>
)
