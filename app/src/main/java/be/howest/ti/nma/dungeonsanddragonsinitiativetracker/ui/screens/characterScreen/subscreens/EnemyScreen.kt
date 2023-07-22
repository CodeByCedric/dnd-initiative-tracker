package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun EnemiesScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
    enemies: List<Enemy>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {

    enemies.forEach { enemy: Enemy -> EnemyCard(enemy) }


}

@Composable
fun EnemyCard(enemy: Enemy) {
    Card(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.enemy_card_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Row() {
            Text(
                text = enemy.name,
                textAlign = TextAlign.Center
            )
        }

    }

}


