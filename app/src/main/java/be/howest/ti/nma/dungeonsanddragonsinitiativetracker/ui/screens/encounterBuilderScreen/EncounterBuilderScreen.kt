package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.encounterBuilderScreen

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.theme.DungeonsAndDragonsInitiativeTrackerTheme


object EncounterBuilderScreenDestination : NavigationDestination {
    override val route: String = "encounter_builder_screen"
    override val titleRes: Int = R.string.encounter_builder_screen
}

@Composable
fun EncounterBuilderScreen(
    navigateToInitiativeScreen: () -> Unit = {},
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = EncounterBuilderScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        }) { innerPadding ->
        EncounterBuilderScreenBody(
            navigateToInitiativeScreen,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }


}

@Composable
fun EncounterBuilderScreenBody(
    navigateToInitiativeScreen: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            NavigateToInitiativeScreenButton(navigateToInitiativeScreen)
        }
    }
}

@Composable
private fun NavigateToInitiativeScreenButton(navigateToInitiativeScreen: () -> Unit) {
    Button(
        onClick = { navigateToInitiativeScreen() },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(R.string.to_initiative_screen_button),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun EncounterBuilderPreviewScreen() {
    DungeonsAndDragonsInitiativeTrackerTheme() {
        EncounterBuilderScreen(
            navigateBack = { },
            onNavigateUp = { }
        )
    }
}