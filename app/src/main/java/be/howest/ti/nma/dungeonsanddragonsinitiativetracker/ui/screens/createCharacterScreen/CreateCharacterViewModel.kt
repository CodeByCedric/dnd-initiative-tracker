package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.PlayerCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateCharacterViewModel(
    private val playerCharacterRepository: PlayerCharacterRepository,
    private val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository,
) : ViewModel() {
    private val _createCharacterUiState = MutableStateFlow(CreateCharacterUiState())
    val createCharacterUiState: StateFlow<CreateCharacterUiState> =
        _createCharacterUiState.asStateFlow()

    fun updateCharacterName(name: String) {
        val currentUiState = createCharacterUiState.value
        _createCharacterUiState.value = currentUiState.copy(characterName = name)
    }

    fun updateCharacterArmorClass(armorClass: String) {
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


    fun updateCharacterInitiativeModifier(initiativeModifier: String) {
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


    //todo
    fun createCharacter() {
        val characterName = createCharacterUiState.value.characterName
        val characterArmorClass = createCharacterUiState.value.armorClass
        val characterInitiativeModifier = createCharacterUiState.value.initiativeModifier
        val isPrimaryCharacter = createCharacterUiState.value.isPrimaryCharacter
        val isSecondaryCharacter = createCharacterUiState.value.isSecondaryCharacter
        val isEnemy = createCharacterUiState.value.isEnemy

        TODO("Not yet implemented")
    }
}