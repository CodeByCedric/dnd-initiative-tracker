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

    fun updateCharacterArmorClass(armorClass: String) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(armorClass = armorClass)
    }

    fun incrementArmorClassByOne() {
        val currentUiState = createCharacterUiState.value
        val newArmorClass = if (currentUiState.armorClass == "") {
            +1
        } else {
            currentUiState.armorClass.toInt() + 1
        }
        _createCharacterUiState.value = currentUiState.copy(armorClass = newArmorClass.toString())
    }

    fun decrementArmorClassByOne() {
        val currentUiState = createCharacterUiState.value
        val newArmorClass = if (currentUiState.armorClass == "") {
            -1
        } else {
            currentUiState.armorClass.toInt() - 1
        }
        _createCharacterUiState.value = currentUiState.copy(armorClass = newArmorClass.toString())
    }

    fun incrementInitiativeModifierByOne() {
        val currentUiState = createCharacterUiState.value
        val newInitiativeModifier = if (currentUiState.initiativeModifier == "") {
            +1
        } else {
            currentUiState.initiativeModifier.toInt() + 1
        }
        _createCharacterUiState.value =
            currentUiState.copy(initiativeModifier = newInitiativeModifier.toString())
    }

    fun decrementInitiativeModifierByOne() {
        val currentUiState = createCharacterUiState.value
        val newInitiativeModifier = if (currentUiState.initiativeModifier == "") {
            -1
        } else {
            currentUiState.initiativeModifier.toInt() - 1
        }
        _createCharacterUiState.value =
            currentUiState.copy(initiativeModifier = newInitiativeModifier.toString())
    }


    fun updateCharacterInitiativeModifier(initiativeModifier: String) {
        val currentUiState = createCharacterUiState.value

        _createCharacterUiState.value = currentUiState.copy(
            initiativeModifier = initiativeModifier
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

    suspend fun createCharacter(campaignId: Long) {
        val characterName = createCharacterUiState.value.characterName
        val characterArmorClass = createCharacterUiState.value.armorClass.toInt()
        val characterInitiativeModifier = createCharacterUiState.value.initiativeModifier.toInt()
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
        var enemyName = ""
        var dexterityModifier = 0
        var armorClass = 0

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

    fun validateForm(): Boolean {
        val currentUiState = createCharacterUiState.value

        return currentUiState.characterName.isNotBlank() &&
                currentUiState.initiativeModifier.isNotBlank() &&
                currentUiState.armorClass.isNotBlank() &&
                (currentUiState.isPrimaryCharacter || currentUiState.isSecondaryCharacter || currentUiState.isEnemy)
    }
}