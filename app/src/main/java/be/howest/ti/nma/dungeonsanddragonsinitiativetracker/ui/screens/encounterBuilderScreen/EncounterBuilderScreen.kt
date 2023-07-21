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
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination


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
    modifier: Modifier = Modifier,
    campaignId: Long
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = EncounterBuilderScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        },
        bottomBar = {
            NavigateToInitiativeScreenButton(
                navigateToInitiativeScreen = navigateToInitiativeScreen
            )
        }
    ) { innerPadding ->
        EncounterBuilderScreenBody(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }


}

@Composable
fun EncounterBuilderScreenBody(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(text = "Content")
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