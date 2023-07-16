package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

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

object SkirmishScreenDestination : NavigationDestination {
    override val route: String = "skirmish_screen"
    override val titleRes: Int = R.string.skirmish_screen
}

@Composable
fun SkirmishScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateToCharacterScreen: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = SkirmishScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp

            )
        }) { innerPadding ->
        SkirmishScreenBody(
            navigateToCharacterScreen,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }


}

@Composable
fun SkirmishScreenBody(
    navigateToCharacterScreen: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    ) {
        item {
            NavigateToCharacterScreenButton(navigateToCharacterScreen)
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

@Preview
@Composable
fun SkirmishScreenPreview() {
    DungeonsAndDragonsInitiativeTrackerTheme {
        SkirmishScreen(
            navigateBack = { },
            onNavigateUp = { }
        )
    }
}