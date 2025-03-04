package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination


/*
TODO: add deathSaves
TODO: add statuses (e.g. poison, petrification, etc.)
TODO: add expand functionality with more enemy details from API
 */
object SkirmishScreenDestination : NavigationDestination {
    override val route: String = "skirmish_screen"
    override val titleRes: Int = R.string.skirmish_screen
}

@Composable
fun SkirmishScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    skirmishViewModel: SkirmishViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToCharacterScreen: () -> Unit = {},
) {
    val skirmishUiState = skirmishViewModel.skirmishUiState.collectAsState()
    val sortedListOfSkirmishCharacters = skirmishUiState.value.sortedListOfSkirmishCharacters

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = SkirmishScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val updatedList =
                            sortedListOfSkirmishCharacters.drop(1) + sortedListOfSkirmishCharacters.take(1)
                        skirmishViewModel.updateSortedCharacters(updatedList)
                    },
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.button_height))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(id = R.string.end_turn),
                    )
                }
                NavigateToCharacterScreenButton(navigateToCharacterScreen)
            }
        }
    ) { innerPadding ->
        SkirmishScreenBody(
            skirmishCharacters = sortedListOfSkirmishCharacters,
            skirmishViewModel = skirmishViewModel,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SkirmishScreenBody(
    skirmishCharacters: List<SkirmishCharacter>,
    skirmishViewModel: SkirmishViewModel,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        skirmishCharacters.forEach { skirmishCharacter ->
            item {
                SkirmishCharacterCard(
                    skirmishCharacter = skirmishCharacter,
                    skirmishViewModel = skirmishViewModel,
                )
            }
        }
    }
}

@Composable
fun SkirmishCharacterCard(
    skirmishCharacter: SkirmishCharacter,
    skirmishViewModel: SkirmishViewModel,
) {
    val backgroundCardColor = if (skirmishCharacter.isPrimaryCharacter) {
        MaterialTheme.colorScheme.secondaryContainer
    } else if (skirmishCharacter.isSecondaryCharacter) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else if (skirmishCharacter.isEnemy) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        Color.Transparent
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundCardColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Column {
                Text(text = skirmishCharacter.name)
                Text(text = "AC: ${skirmishCharacter.armorClass}")
            }
            Column {
                IconButton(
                    onClick = {
                        skirmishViewModel.deleteSkirmishCharacter(skirmishCharacter)
                    }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_campaign_icon_label)
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigateToCharacterScreenButton(navigateToCharacterScreen: () -> Unit) {
    Button(
        onClick = { navigateToCharacterScreen() },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(R.string.to_character_overview_button),
            textAlign = TextAlign.Center
        )
    }
}