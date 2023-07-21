package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun SecondaryCharacters(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()
    val secondaryCharacters by characterUiState.secondaryCharacters.collectAsState(initial = emptyList())

    val selectedCharacters = characterUiState.selectedCharacters

    secondaryCharacters.forEach { secondaryCharacter ->
        CharacterCard(
            secondaryCharacter,
            characterViewModel,
            characterUiState,
            isSelected = secondaryCharacter in selectedCharacters
        ) { clickedCharacter ->
            if (selectedCharacters.contains(clickedCharacter)) {
                characterViewModel.deselectCharacter(clickedCharacter)
            } else {
                characterViewModel.selectCharacter(clickedCharacter)
            }
        }
    }
}