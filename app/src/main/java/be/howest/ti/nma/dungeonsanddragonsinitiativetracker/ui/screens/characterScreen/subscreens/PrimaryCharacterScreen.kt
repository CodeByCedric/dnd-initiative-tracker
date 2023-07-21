package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun PrimaryCharacters(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()

    val primaryCharacters by characterUiState.primaryCharacters.collectAsState(
        initial =
        emptyList()
    )

    primaryCharacters.forEach { primaryCharacter ->
        CharacterCard(primaryCharacter)
    }

    Text(text = campaignId.toString())
}

@Composable
fun CharacterCard(primaryCharacter: CampaignPlayerCharacterDetail) {
    Card() {
        Column() {
            Text(text = primaryCharacter.name)
            Text(text = primaryCharacter.armorClass.toString())
            Text(text = primaryCharacter.initiativeModifier.toString())

        }
    }
}
