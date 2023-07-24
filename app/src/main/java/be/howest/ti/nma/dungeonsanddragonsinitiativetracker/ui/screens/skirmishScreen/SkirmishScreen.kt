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
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object SkirmishScreenDestination : NavigationDestination {
    override val route: String = "skirmish_screen"
    override val titleRes: Int = R.string.skirmish_screen
}

val campaignPlayerCharacters: List<CampaignPlayerCharacterDetail> = listOf(
    CampaignPlayerCharacterDetail(
        name = "Ibun",
        armorClass = 16,
        initiativeModifier = -2,
        initiative = 13,
        isPrimaryCharacter = true
    ),
    CampaignPlayerCharacterDetail(
        name = "Rhys",
        armorClass = 18,
        initiativeModifier = 1,
        initiative = 11,
        isPrimaryCharacter = true
    ),
    CampaignPlayerCharacterDetail(
        name = "Tinuviel",
        armorClass = 14,
        initiativeModifier = 2,
        initiative = 14,
        isPrimaryCharacter = true
    ),
    CampaignPlayerCharacterDetail(
        name = "Stool",
        armorClass = 12,
        initiativeModifier = 0,
        initiative = 15,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Sushi",
        armorClass = 14,
        initiativeModifier = 1,
        initiative = 3,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Hemmeth",
        armorClass = 16,
        initiativeModifier = 0,
        initiative = 8,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Ann",
        armorClass = 20,
        initiativeModifier = +5,
        initiative = 25,
        isEnemy = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Koen",
        armorClass = 20,
        initiativeModifier = +5,
        initiative = 25,
        isEnemy = true,
    ),
)

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
        },
        bottomBar = {
            NavigateToCharacterScreenButton(navigateToCharacterScreen)
        }
    ) { innerPadding ->
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
        modifier = modifier
    ) {
        item {
            Text(text = "content")
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