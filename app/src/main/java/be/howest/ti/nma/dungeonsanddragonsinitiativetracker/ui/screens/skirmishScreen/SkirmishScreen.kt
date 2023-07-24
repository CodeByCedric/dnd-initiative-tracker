package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination


/*
TODO: add icon to remove character from list
TODO: add deathSaves
TODO: add statuses (e.g. poison, petrification, etc.)
 */
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
        name = "Eldeth",
        armorClass = 16,
        initiativeModifier = 0,
        initiative = 14,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Rumpadump",
        armorClass = 16,
        initiativeModifier = 0,
        initiative = 8,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Topsy",
        armorClass = 16,
        initiativeModifier = 0,
        initiative = 8,
        isSecondaryCharacter = true,
    ),
    CampaignPlayerCharacterDetail(
        name = "Turvy",
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

    var sortedList by remember { mutableStateOf(sortCampaignPlayerCharacters(campaignPlayerCharacters)) }
    var backgroundColors by remember { mutableStateOf(getCharacterBackgroundColors(sortedList)) }

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = SkirmishScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EndTurnButton {
                    sortedList = sortedList.drop(1) + sortedList.take(1)
                    backgroundColors = getCharacterBackgroundColors(sortedList)
                }
                NavigateToCharacterScreenButton(navigateToCharacterScreen)
            }
        }
    ) { innerPadding ->
        SkirmishScreenBody(
            campaignPlayerCharacters = sortedList,
            backgroundColors = backgroundColors,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SkirmishScreenBody(
    campaignPlayerCharacters: List<CampaignPlayerCharacterDetail>,
    backgroundColors: List<Color>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        campaignPlayerCharacters.forEachIndexed { index, campaignPlayerCharacter ->
            item {
                SkirmishCharacterCard(
                    campaignPlayerCharacter,
                    backgroundColors[index]
                )
            }
        }
    }
}

@Composable
fun SkirmishCharacterCard(
    campaignPlayerCharacter: CampaignPlayerCharacterDetail,
    backgroundColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row {
                Text(text = campaignPlayerCharacter.name)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "AC: ${campaignPlayerCharacter.armorClass}")
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

@Composable
fun EndTurnButton(
    onEndTurnClicked: () -> Unit
) {
    Button(
        onClick = { onEndTurnClicked() },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(id = R.string.end_turn),
        )
    }
}

private fun sortCampaignPlayerCharacters(campaignPlayerCharactersDetail: List<CampaignPlayerCharacterDetail>): List<CampaignPlayerCharacterDetail> {
    return campaignPlayerCharactersDetail.sortedWith(
        compareByDescending<CampaignPlayerCharacterDetail> { it.initiative }
            .thenByDescending { it.initiativeModifier }
            .thenByDescending { Math.random() }
    )
}

private fun getCharacterBackgroundColors(campaignPlayerCharacters: List<CampaignPlayerCharacterDetail>): List<Color> {
    return campaignPlayerCharacters.map { campaignPlayerCharacter ->
        when {
            campaignPlayerCharacter.isPrimaryCharacter -> Color(0xFFbafffe)
            campaignPlayerCharacter.isSecondaryCharacter -> Color(0xFFddffba)
            campaignPlayerCharacter.isEnemy -> Color(0xFFffbabb)
            else -> Color.Transparent
        }
    }
}
