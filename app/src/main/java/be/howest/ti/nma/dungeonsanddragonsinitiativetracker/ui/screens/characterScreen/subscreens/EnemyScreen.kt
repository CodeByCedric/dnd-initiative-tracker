package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@ExperimentalMaterial3Api
@Composable
fun EnemyScreen(
    campaignId: Long,
    characterViewModel: CharacterViewModel,
    enemies: List<CampaignPlayerCharacterDetail>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {

    enemies.forEach { enemy ->
        CharacterCard(
            campaignId = campaignId,
            playerCharacter = enemy,
            characterViewModel = characterViewModel,
            isSelected = enemy in selectedCharacters
        ) { clickedCharacter ->
            if (selectedCharacters.contains(clickedCharacter)) {
                characterViewModel.deselectCharacter(clickedCharacter)
            } else {
                characterViewModel.selectCharacter(clickedCharacter)
            }
        }
    }
}