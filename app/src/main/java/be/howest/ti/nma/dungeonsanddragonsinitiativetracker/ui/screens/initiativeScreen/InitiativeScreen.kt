package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.initiativeScreen

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

object InitiativeScreenDestination : NavigationDestination {
    override val route: String = "initiative_screen"
    override val titleRes: Int = R.string.initative_screen
}

@Composable
fun InitiativeScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateToSkirmishScreen: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = InitiativeScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        }) { innerPadding ->
        InitiativeScreenBody(
            navigateToSkirmishScreen,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }

}

@Composable
fun InitiativeScreenBody(
    navigateToSkirmishScreen: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            NavigateToSkirmishScreenBody(navigateToSkirmishScreen)
        }
    }


}

@Composable
private fun NavigateToSkirmishScreenBody(navigateToSkirmishScreen: () -> Unit) {
    Button(
        onClick = { navigateToSkirmishScreen() },
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

@Preview
@Composable
fun InitiativePreviewScreen() {
    DungeonsAndDragonsInitiativeTrackerTheme() {
        InitiativeScreen(
            navigateBack = { },
            onNavigateUp = { }
        )
    }
}