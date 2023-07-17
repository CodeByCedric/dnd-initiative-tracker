package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.EnemiesScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.PrimaryCharacters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens.SecondaryCharacters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.theme.DungeonsAndDragonsInitiativeTrackerTheme

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
    characterViewModel: CharacterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToEncounterBuilderScreen: () -> Unit = {},
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val tabItems = listOf(
        "Characters" to CharacterTab.Characters,
        "Secondary Characters" to CharacterTab.SecondaryCharacters,
        "Enemies" to CharacterTab.Enemies
    )
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(CharacterTab.Characters) }

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CharacterScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        },
        content = { innerPadding ->
            CharacterScreenBody(
                characterViewModel = characterViewModel,
                navigateToEncounterBuilderScreen = navigateToEncounterBuilderScreen,
                selectedTab = selectedTab,
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
                    navigateToEncounterBuilderScreen = navigateToEncounterBuilderScreen
                )
            }

        }
    )
}

@Composable
fun CharacterScreenBody(
    navigateToEncounterBuilderScreen: () -> Unit,
    selectedTab: CharacterTab,
    modifier: Modifier,
    characterViewModel: CharacterViewModel
) {
    LazyColumn(modifier = modifier) {
        item {
            when (selectedTab) {
                CharacterTab.Characters -> {
                    PrimaryCharacters(
                        characterViewModel = characterViewModel,
                        modifier = modifier
                    )
                }

                CharacterTab.SecondaryCharacters -> {
                    SecondaryCharacters(
                        characterViewModel = characterViewModel,
                        modifier = modifier
                    )
                }

                CharacterTab.Enemies -> {
                    EnemiesScreen(
                        characterViewModel = characterViewModel,
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

@Preview
@Composable
fun CharacterPreviewScreen() {
    DungeonsAndDragonsInitiativeTrackerTheme() {
        CharacterScreen(
            navigateBack = { },
            onNavigateUp = { },
            navController = NavController(context = LocalContext.current)
        )
    }
}