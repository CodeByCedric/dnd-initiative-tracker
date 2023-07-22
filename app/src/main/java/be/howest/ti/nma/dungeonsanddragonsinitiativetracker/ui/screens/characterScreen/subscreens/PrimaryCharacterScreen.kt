package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun PrimaryCharacters(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
    primaryCharacters: List<CampaignPlayerCharacterDetail>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>,
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()


    primaryCharacters.forEach { primaryCharacter ->
        CharacterCard(
            primaryCharacter,
            characterViewModel,
            isSelected = primaryCharacter in selectedCharacters
        ) { clickedCharacter ->
            if (selectedCharacters.contains(clickedCharacter)) {
                characterViewModel.deselectCharacter(clickedCharacter)
            } else {
                characterViewModel.selectCharacter(clickedCharacter)
            }
        }
    }
}

