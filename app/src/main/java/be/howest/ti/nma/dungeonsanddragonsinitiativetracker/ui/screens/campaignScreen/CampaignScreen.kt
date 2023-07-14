package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

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
    var appBarHeight: Int

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CampaignScreenDestination.titleRes),
                canNavigateBack = false,

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
    val campaigns by campaignUiState.campaigns.collectAsState(initial = emptyList())

    var selectedCampaign by remember { mutableStateOf<Campaign?>(null) }

    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
    ) {
        items(campaigns) { campaign ->
            CampaignCard(
                campaign = campaign,
                isSelected = campaign == selectedCampaign,
                onSelect = { selectedCampaign = if (it == selectedCampaign) null else it },
                campaignViewModel = campaignViewModel
            )
        }

    }

}

@Composable
fun CampaignCard(
    campaign: Campaign,
    isSelected: Boolean,
    onSelect: (Campaign) -> Unit,
    campaignViewModel: CampaignViewModel,
    modifier: Modifier = Modifier
) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onSelect(campaign) }
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                CampaignImage(campaign)
                CampaignInformation(campaign)
                Spacer(modifier = Modifier.weight(1f))
                Column {
//                    ExpandCampaignCardButton(
//                        expanded = isExpanded,
//                        onClickExpandCard = { isExpanded = !isExpanded },
//                    )
                    RemoveCampaignButton(
                        campaignViewModel,
                        campaign
                    )
                }

            }

        }

    }

}


@Composable
fun CampaignImage(
    campaign: Campaign,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = campaign.campaignImageDrawable ?: campaign.campaignImageUri,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50))
    )
}

@Composable
fun CampaignInformation(
    campaign: Campaign,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = campaign.campaignName,
            modifier = modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun RemoveCampaignButton(
    campaignViewModel: CampaignViewModel,
    campaign: Campaign
) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(
        onClick = {
            coroutineScope.launch { campaignViewModel.deleteCampaign(campaign) }
        }
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_campaign_icon_label)
        )
    }
}
