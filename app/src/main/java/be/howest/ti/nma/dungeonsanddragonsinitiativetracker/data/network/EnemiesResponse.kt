package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy

data class EnemiesResponse(
    val count: Int,
    val results: List<Enemy>
)

data class EnemyInfo(
    val name: String,
    val armor_class: List<ArmorClass>,
    val dexterity: Int
) {
    data class ArmorClass(
        val type: String,
        val value: Int
    )
}
