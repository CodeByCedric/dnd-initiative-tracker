package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun EnemyScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
    enemies: List<Enemy>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()

    Column(
    ) {
        TextField(
            value = characterUiState.searchQuery,
            onValueChange = { searchQuery -> characterViewModel.updateSearchQuery(searchQuery) },
            label = { Text(stringResource(id = R.string.search_enemies)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
                .border(
                    dimensionResource(id = R.dimen.border_small),
                    Color.Gray,
                    RoundedCornerShape(4.dp)
                )
        )
    }
}
