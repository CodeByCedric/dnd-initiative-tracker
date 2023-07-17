package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network

data class EnemyResponse(
    val count: Int,
    val results: List<Enemy>
)
