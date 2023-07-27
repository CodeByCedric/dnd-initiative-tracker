package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.EnemyScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.PrimaryCharacters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.SecondaryCharacters
import kotlinx.coroutines.launch

/*
* Todo: when manually typing in the initiative, the cursor is placed before the previous number
*  (e.g. typing in a 1 and a 5 results in 51)
* Todo: when a card is selected and then the roll initiative button is pressed, the selection is
*   removed
* */


object CharacterScreenDestination : NavigationDestination {
    override val route: String = "character_overview_screen"
    override val titleRes: Int = R.string.character_overview_screen
}

enum class CharacterTab {
    Characters,
    SecondaryCharacters,
    Enemies
}

@Composable
fun CharacterScreen(
    campaignId: Long,
    navigateToCreateCharacterScreen: (Long) -> Unit,
    navigateToSkirmishScreen: (Long) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    characterViewModel: CharacterViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()

    //Load primary and secondary characters on screen
    if (!characterUiState.isInitialized) {
        characterViewModel.loadCharacters(campaignId)
        characterViewModel.updateIsInitialized(true)
    }

    val tabItems = listOf(
        "Characters" to CharacterTab.Characters,
        "Secondary Characters" to CharacterTab.SecondaryCharacters,
        "Enemies" to CharacterTab.Enemies
    )
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(CharacterTab.Characters) }

    val primaryCharacters by characterUiState.primaryCharacters.collectAsState(initial = emptyList())
    val secondaryCharacters by characterUiState.secondaryCharacters.collectAsState(initial = emptyList())
    val enemies by characterUiState.enemyCharacters.collectAsState(initial = emptyList())

    val selectedCharacters = characterUiState.selectedCharacters

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CharacterScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp

            )
        },
        content = { innerPadding ->
            CharacterScreenBody(
                campaignId = campaignId,
                characterViewModel = characterViewModel,
                selectedTab = selectedTab,
                primaryCharacters = primaryCharacters,
                secondaryCharacters = secondaryCharacters,
                enemies = enemies,
                selectedCharacters = selectedCharacters,
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToCreateCharacterScreen(campaignId) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .testTag("addCharacterFOB")
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.create_character)
                )
            }
        },
        bottomBar = {
            Column {
                CharacterTabBar(
                    tabItems = tabItems,
                    selectedTab = selectedTab,
                    onTabSelected = setSelectedTab
                )
                NavigateToSkirmishScreenButton(
                    characterViewModel = characterViewModel,
                    selectedCharacters = selectedCharacters,
                    navigateToSkirmishScreen = { navigateToSkirmishScreen(campaignId) }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreenBody(
    selectedTab: CharacterTab,
    modifier: Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
    primaryCharacters: List<CampaignPlayerCharacterDetail>,
    secondaryCharacters: List<CampaignPlayerCharacterDetail>,
    enemies: List<CampaignPlayerCharacterDetail>,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = getSubtitle(selectedTab),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            when (selectedTab) {
                CharacterTab.Characters -> {
                    PrimaryCharacters(
                        campaignId = campaignId,
                        characterViewModel = characterViewModel,
                        primaryCharacters = primaryCharacters,
                        selectedCharacters = selectedCharacters,
                    )
                }

                CharacterTab.SecondaryCharacters -> {
                    SecondaryCharacters(
                        campaignId = campaignId,
                        characterViewModel = characterViewModel,
                        secondaryCharacters = secondaryCharacters,
                        selectedCharacters = selectedCharacters,
                    )
                }

                CharacterTab.Enemies -> {
                    EnemyScreen(
                        campaignId = campaignId,
                        characterViewModel = characterViewModel,
                        enemies = enemies,
                        selectedCharacters = selectedCharacters,
                    )
                }
            }
        }
    }

}


@Composable
fun CharacterCard(
    campaignId: Long,
    playerCharacter: CampaignPlayerCharacterDetail,
    characterViewModel: CharacterViewModel,
    isSelected: Boolean,
    onCardClick: (CampaignPlayerCharacterDetail) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val backgroundCardColor = if (playerCharacter.isPrimaryCharacter) {
        MaterialTheme.colorScheme.secondaryContainer
    } else if (playerCharacter.isSecondaryCharacter) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else if (playerCharacter.isEnemy) {
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
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable { onCardClick(playerCharacter) },
        border = if (isSelected) BorderStroke(
            width = dimensionResource(id = R.dimen.border_medium),
            color = MaterialTheme.colorScheme.outline
        ) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row {
                Text(text = playerCharacter.name)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "AC: ${playerCharacter.armorClass}")
                Text(
                    text = "Initiative Modifier: ${
                        when {
                            playerCharacter.initiativeModifier > 0 -> "+${playerCharacter.initiativeModifier}"
                            playerCharacter.initiativeModifier < 0 -> playerCharacter.initiativeModifier.toString()
                            else -> "0"
                        }
                    }"
                )
                Column {
                    //TODO Add a title here to clarify that initiative is to be entered in this field?
                    BasicTextField(
                        value = if (playerCharacter.initiative == null) {
                            ""
                        } else {
                            playerCharacter.initiative.toString()
                        },
                        onValueChange = { newValue ->
                            characterViewModel.updateInitiativeForCharacters(
                                playerCharacter,
                                newValue
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = dimensionResource(
                                id = R.dimen
                                    .fontSize_medium
                            ).value.sp
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),

                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.initiative_textfield_width))
                            //TODO create a color for the textfield in the theme and use this
                            .background(Color.LightGray)
                            .padding(dimensionResource(R.dimen.padding_small))
                    )
                }

                // Button to roll for initiative
                Button(
                    onClick = {
                        val rolledInitiative = characterViewModel.rollInitiative(
                            playerCharacter
                                .initiativeModifier
                        )
                        characterViewModel.updateInitiativeForCharacters(
                            playerCharacter,
                            rolledInitiative
                        )
                    },
                ) {
                    Text(text = "Roll Initiative")
                }
                //Button to delete characters
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            characterViewModel.deleteCharacter(
                                campaignId,
                                playerCharacter
                            )
                        }
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
fun CharacterTabBar(
    tabItems: List<Pair<String, CharacterTab>>,
    selectedTab: CharacterTab,
    onTabSelected: (CharacterTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.character_tab_bar_height))
    ) {
        tabItems.forEach { (label, tab) ->
            val isSelected = tab == selectedTab
            val textColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme
                .colorScheme.onSurface
            val indicatorColor =
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            val indicatorHeight = dimensionResource(id = R.dimen.character_tab_indicator_height)

            Tab(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.character_tab_bar_height))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = label,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(indicatorHeight)
                            .background(indicatorColor)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigateToSkirmishScreenButton(
    navigateToSkirmishScreen: () -> Unit,
    selectedCharacters: MutableList<CampaignPlayerCharacterDetail>,
    characterViewModel: CharacterViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            Log.d(
                "Selected Characters:",
                selectedCharacters.toString()
            )
            coroutineScope.launch {
                characterViewModel.clearSkirmishCharacterTable()
                characterViewModel.insertSkirmishParticipants(selectedCharacters)
            }
            navigateToSkirmishScreen()
        },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(R.string.to_skirmish_screen_button),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun getSubtitle(selectedTab: CharacterTab): String {
    return when (selectedTab) {
        CharacterTab.Characters -> stringResource(id = R.string.characters_subtitle)
        CharacterTab.SecondaryCharacters -> stringResource(id = R.string.secondary_characters_subtitle)
        CharacterTab.Enemies -> stringResource(id = R.string.enemies_subtitle)
    }
}
