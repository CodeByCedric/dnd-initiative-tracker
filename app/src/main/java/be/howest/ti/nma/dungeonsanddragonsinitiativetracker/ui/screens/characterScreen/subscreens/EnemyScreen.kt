package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@ExperimentalMaterial3Api
@Composable
fun EnemyScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
    enemies: List<CampaignPlayerCharacterDetail>,
    allEnemies: List<Enemy>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()

    enemies.forEach { enemy ->
        CharacterCard(
            enemy,
            characterViewModel,
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