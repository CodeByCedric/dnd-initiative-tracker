package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun SecondaryCharacters(
    campaignId: Long,
    characterViewModel: CharacterViewModel,
    secondaryCharacters: List<CampaignPlayerCharacterDetail>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {

    secondaryCharacters.forEach { secondaryCharacter ->
        CharacterCard(
            campaignId = campaignId,
            playerCharacter = secondaryCharacter,
            characterViewModel = characterViewModel,
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