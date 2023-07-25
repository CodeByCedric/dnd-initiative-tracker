package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun PrimaryCharacters(
    campaignId: Long,
    characterViewModel: CharacterViewModel,
    primaryCharacters: List<CampaignPlayerCharacterDetail>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>,
) {

    primaryCharacters.forEach { primaryCharacter ->
        CharacterCard(
            campaignId = campaignId,
            playerCharacter = primaryCharacter,
            characterViewModel = characterViewModel,
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

