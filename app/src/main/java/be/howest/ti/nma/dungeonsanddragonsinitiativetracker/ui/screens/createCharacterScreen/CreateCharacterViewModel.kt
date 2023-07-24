package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.PlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.network.EnemiesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateCharacterViewModel(
    private val playerCharacterRepository: PlayerCharacterRepository,
    private val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository,
    private val enemyRepository: EnemyRepository
) : ViewModel() {
    private val _createCharacterUiState = MutableStateFlow(CreateCharacterUiState())
    val createCharacterUiState: StateFlow<CreateCharacterUiState> =
        _createCharacterUiState.asStateFlow()

    init {
        _createCharacterUiState.value = CreateCharacterUiState(
            enemyFlow = getEnemiesFromDB()
        )
    }

    fun updateCharacterName(name: String) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(characterName = name)
    }

    private fun updateCharacterArmorClass(armorClass: String) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(armorClass = armorClass.toInt())
    }

    fun incrementArmorClassByOne() {
        val currentUiState = createCharacterUiState.value
        val newArmorClass = currentUiState.armorClass + 1
        _createCharacterUiState.value = currentUiState.copy(armorClass = newArmorClass)
    }

    fun decrementArmorClassByOne() {
        val currentUiState = createCharacterUiState.value
        val newArmorClass = currentUiState.armorClass - 1
        _createCharacterUiState.value = currentUiState.copy(armorClass = newArmorClass)
    }

    fun incrementInitiativeModifierByOne() {
        val currentUiState = createCharacterUiState.value
        val newInitiativeModifier = currentUiState.initiativeModifier + 1
        _createCharacterUiState.value =
            currentUiState.copy(initiativeModifier = newInitiativeModifier)
    }

    fun decrementInitiativeModifierByOne() {
        val currentUiState = createCharacterUiState.value
        val newInitiativeModifier = currentUiState.initiativeModifier - 1
        _createCharacterUiState.value =
            currentUiState.copy(initiativeModifier = newInitiativeModifier)
    }


    private fun updateCharacterInitiativeModifier(initiativeModifier: String) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(
            initiativeModifier =
            initiativeModifier.toInt()
        )
    }

    fun updateCharacterType(
        isPrimaryCharacter: Boolean = false,
        isSecondaryCharacter: Boolean = false,
        isEnemy: Boolean = false
    ) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(
            isPrimaryCharacter = isPrimaryCharacter,
            isSecondaryCharacter = isSecondaryCharacter,
            isEnemy = isEnemy,
        )
    }

    fun checkCharacterType(): String {
        return if (createCharacterUiState.value.isPrimaryCharacter) {
            "Primary Character"
        } else if (createCharacterUiState.value.isSecondaryCharacter) {
            "Secondary Character"
        } else if (createCharacterUiState.value.isEnemy) {
            "Enemy"
        } else {
            ""
        }
    }

    private fun getEnemiesFromDB(): Flow<List<Enemy>> {
        return enemyRepository.getAllEnemies()
    }

    fun getEnemiesFromUiState(): Flow<List<Enemy>> {
        return _createCharacterUiState.value.enemyFlow
    }

    //todo
    suspend fun createCharacter(campaignId: Long) {
        val characterName = createCharacterUiState.value.characterName
        val characterArmorClass = createCharacterUiState.value.armorClass
        val characterInitiativeModifier = createCharacterUiState.value.initiativeModifier
        val isPrimaryCharacter = createCharacterUiState.value.isPrimaryCharacter
        val isSecondaryCharacter = createCharacterUiState.value.isSecondaryCharacter
        val isEnemy = createCharacterUiState.value.isEnemy

        val playerCharacterId = playerCharacterRepository.insertPlayerCharacter(
            PlayerCharacter(
                name = characterName,
                armorClass = characterArmorClass,
                initiativeModifier = characterInitiativeModifier,
                isPrimaryCharacter = isPrimaryCharacter,
                isSecondaryCharacter = isSecondaryCharacter,
                isEnemy = isEnemy
            )
        )

        campaignPlayerCharacterRepository.insertCampaignPlayerCharacter(
            CampaignPlayerCharacter(
                campaignId = campaignId,
                playerCharacterId = playerCharacterId
            )
        )
    }

    suspend fun addEnemy(
        enemyIndex: String
    ) {
        val enemyResponseBody = EnemiesApi.retrofitService.getEnemy(enemyIndex).body()
        var enemyName: String = ""
        var dexterityModifier: Int = 0
        var armorClass: Int = 0

        if (enemyResponseBody != null) {
            enemyName = enemyResponseBody.name
            val dexterity: Double = ((enemyResponseBody.dexterity - 10) / 2).toDouble()
            dexterityModifier = kotlin.math.floor(dexterity).toInt()
            armorClass = enemyResponseBody.armor_class.first().value
        }

        updateCharacterName(enemyName)
        updateCharacterArmorClass(armorClass.toString())
        updateCharacterInitiativeModifier(dexterityModifier.toString())
        updateCharacterType(isEnemy = true)

    }
}