package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CampaignScreenDestination : NavigationDestination {
    override val route: String = "campaign_screen"
    override val titleRes: Int = R.string.campaign_overview_screen

}

@Composable
fun CampaignScreen(
    navigateToCharacterScreen: () -> Unit,
    navigateToAddCampaignScreen: () -> Unit,
    canNavigateBack: Boolean = false,
    campaignViewModel: CampaignViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val campaignUiState by campaignViewModel.campaignUiState.collectAsState()

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CampaignScreenDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddCampaignScreen,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_campaign_screen)
                )
            }
        },
    ) { innerPadding ->
        CampaignBody(
            campaignViewModel = campaignViewModel,
            campaignUiState = campaignUiState,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

    }
}

@Composable
fun CampaignBody(
    campaignViewModel: CampaignViewModel,
    campaignUiState: CampaignUiState,
    modifier: Modifier
) {

}
