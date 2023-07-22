package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.EnemiesScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.PrimaryCharacters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.SecondaryCharacters

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
    characterViewModel: CharacterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToSkirmishScreen: () -> Unit = {},
    navigateToCreateCharacterScreen: () -> Unit,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    navController: NavController
) {
//        Load primary and secondary characters on screen
    characterViewModel.loadCharacters(campaignId)

    val characterUiState by characterViewModel.characterUiState.collectAsState()

    val tabItems = listOf(
        "Characters" to CharacterTab.Characters,
        "Secondary Characters" to CharacterTab.SecondaryCharacters,
        "Enemies" to CharacterTab.Enemies
    )
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(CharacterTab.Characters) }

    val primaryCharacters by characterUiState.primaryCharacters.collectAsState(initial = emptyList())
    val secondaryCharacters by characterUiState.secondaryCharacters.collectAsState(initial = emptyList())
    val enemies by characterUiState.enemies.collectAsState(initial = emptyList())

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
                navigateToCreatePrimaryCharacterScreen = navigateToCreateCharacterScreen,
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
        bottomBar = {
            Column() {
                CharacterTabBar(
                    tabItems = tabItems,
                    selectedTab = selectedTab,
                    onTabSelected = setSelectedTab
                )
                NavigateToEncounterBuilderScreenButton(
                    navigateToEncounterBuilderScreen = navigateToSkirmishScreen
                )
            }

        }
    )
}

@Composable
fun CharacterScreenBody(
    selectedTab: CharacterTab,
    modifier: Modifier,
    characterViewModel: CharacterViewModel,
    navigateToCreatePrimaryCharacterScreen: () -> Unit,
    campaignId: Long,
    primaryCharacters: List<CampaignPlayerCharacterDetail>,
    secondaryCharacters: List<CampaignPlayerCharacterDetail>,
    enemies: List<Enemy>,
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
                        modifier = modifier
                    )
                }

                CharacterTab.SecondaryCharacters -> {
                    SecondaryCharacters(
                        campaignId = campaignId,
                        characterViewModel = characterViewModel,
                        secondaryCharacters = secondaryCharacters,
                        selectedCharacters = selectedCharacters,
                        modifier = modifier
                    )
                }

                CharacterTab.Enemies -> {
                    EnemiesScreen(
                        campaignId = campaignId,
                        characterViewModel = characterViewModel,
                        enemies = enemies,
                        selectedCharacters = selectedCharacters,
                        modifier = modifier
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
fun CharacterCard(
    playerCharacter: CampaignPlayerCharacterDetail,
    characterViewModel: CharacterViewModel,
    isSelected: Boolean,
    onCardClick: (CampaignPlayerCharacterDetail) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(playerCharacter) },
        border = if (isSelected) BorderStroke(
            2.dp,
            Color.Blue
        ) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                //Textfield for manual initiative input
                BasicTextField(
                    value = if (playerCharacter.initiative == null) {
                        ""
                    } else {
                        playerCharacter.initiative.toString()
                    },
                    onValueChange = { newValue ->
                        characterViewModel
                            .updateInitiativeForCharacters(
                                playerCharacter,
                                newValue.toInt()
                            )
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .background(Color.LightGray)
                        .padding(8.dp)
                )

                // Button to roll for initiative
                Button(
                    onClick = {
                        val initiativeValue = characterViewModel.rollInitiative(
                            playerCharacter
                                .initiativeModifier
                        )
                        characterViewModel.updateInitiativeForCharacters(
                            playerCharacter,
                            initiativeValue
                        )
                    },
                ) {
                    Text(text = "Roll Initiative")
                }
            }
        }
    }
}
@Composable
fun NavigateToEncounterBuilderScreenButton(
    navigateToEncounterBuilderScreen: () -> Unit,
) {
    Button(
        onClick = { navigateToEncounterBuilderScreen() },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(R.string.to_encounter_builder_screen_button),
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
