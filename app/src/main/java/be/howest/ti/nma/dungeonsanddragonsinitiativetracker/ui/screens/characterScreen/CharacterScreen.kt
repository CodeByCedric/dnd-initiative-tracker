package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.theme.DungeonsAndDragonsInitiativeTrackerTheme

object CharacterScreenDestination : NavigationDestination {
    override val route: String = "character_overview_screen"
    override val titleRes: Int = R.string.character_overview_screen

}

@Composable
fun CharacterScreen(
    navigateToEncounterBuilderScreen: () -> Unit = {},
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CharacterScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        }) { innerPadding ->
        CharacterScreenBody(
            navigateToEncounterBuilderScreen,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }

}

@Composable
fun CharacterScreenBody(
    navigateToEncounterBuilderScreen: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            NavigateToEncounterBuilderScreenButton(
                navigateToEncounterBuilderScreen
            )
        }
    }


}

@Composable
fun NavigateToEncounterBuilderScreenButton(
    navigateToEncounterBuilderScreen: () -> Unit
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