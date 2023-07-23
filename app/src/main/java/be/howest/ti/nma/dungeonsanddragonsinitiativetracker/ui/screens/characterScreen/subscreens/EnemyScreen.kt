package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterCard
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel
import kotlinx.coroutines.launch

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

    var isExpanded by remember { mutableStateOf(false) }
    var selectedEnemy by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        TextField(
            value = stringResource(id = R.string.search_enemies),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            val coroutineScope = rememberCoroutineScope()
            allEnemies.forEach { enemy ->
                DropdownMenuItem(
                    text = { Text(text = enemy.name) },
                    onClick = {
                        coroutineScope.launch {
                            characterViewModel.addEnemy(
                                campaignId = campaignId,
                                enemyIndex = enemy.index
                            )
                        }
                        isExpanded = false
                    }
                )
            }
        }
    }
    Divider(
    )
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