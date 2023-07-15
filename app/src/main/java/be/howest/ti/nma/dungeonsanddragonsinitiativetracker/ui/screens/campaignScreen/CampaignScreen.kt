package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
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
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipantDetails
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

/*TODO
Add more details to the expanded card, viz. the email addresses of the participants
Add functionality to edit campaign participants from the card
Add functionality to add new participants to a campaign from the card
*/

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
    val participants = campaignViewModel.getCampaignParticipantsWithDetails(
        campaign
            .campaignId
    ).collectAsState(initial = emptyList()).value

    val selectedDateTime = remember { mutableStateOf<Long?>(null) }

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
                Column(
                ) {
                    CampaignInformation(campaign)
                    Text(
                        text = "Next session:",
                        fontSize = dimensionResource(id = R.dimen.fontSize_medium).value.sp,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = "dd/mm - hh:mm",
                        fontSize = dimensionResource(R.dimen.fontSize_small).value.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Column {
                    ExpandCampaignCardButton(
                        expanded = isExpanded,
                        onClickExpandCard = { isExpanded = !isExpanded },
                    )
                    RemoveCampaignButton(
                        campaignViewModel,
                        campaign
                    )
                }

            }
        }

        if (isExpanded) {
            CampaignParticipants(
                campaign = campaign,
                participants = participants,
                campaignViewModel = campaignViewModel
            )
            NextSessionButton(onDateSelected = { dateTime ->
                selectedDateTime.value = dateTime
            })
        }

    }

}


@Composable
fun CampaignParticipants(
    campaign: Campaign,
    campaignViewModel: CampaignViewModel,
    participants: List<CampaignParticipantDetails>
) {

    Column(
        modifier = Modifier.padding(
            dimensionResource(id = R.dimen.padding_medium),
            dimensionResource(id = R.dimen.padding_small),
            dimensionResource(id = R.dimen.padding_medium),
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        participants.forEach { participant ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = participant.participantName,
                    modifier = Modifier.weight(1f)
                )
                // Remove Participant Button
                RemoveParticipantButton(
                    campaignViewModel,
                    participant
                )
            }
        }
    }


}


@Composable
fun ExpandCampaignCardButton(
    expanded: Boolean,
    onClickExpandCard: () -> Unit,
) {
    IconButton(onClick = onClickExpandCard) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
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
            fontSize = dimensionResource(id = R.dimen.fontSize_large).value.sp,
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

@Composable
fun RemoveParticipantButton(
    campaignViewModel: CampaignViewModel,
    campaignParticipantDetails: CampaignParticipantDetails
) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(
        onClick = {
            coroutineScope.launch {
                campaignViewModel.deleteParticipant(
                    CampaignParticipant(
                        participantId = campaignParticipantDetails.participantId,
                        campaignId = campaignParticipantDetails.campaignId
                    )
                )
            }


        },
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_player_icon_label)
        )
    }
}

//@Composable
//fun NextSessionButton(
//    campaign: Campaign,
//) {
//    val context = LocalContext.current
//
//    Button(
//        onClick = {
//            // Launch the date picker or time picker dialog here
//            val title = campaign.campaignName
//            val begin = Calendar.getInstance().timeInMillis
//            val end = begin + 60 * 60 * 4000 // Add 1 hour
//
//            val intent = Intent(Intent.ACTION_INSERT)
//                .setData(CalendarContract.Events.CONTENT_URI)
//                .putExtra(CalendarContract.Events.TITLE, title)
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
//
//            context.startActivity(intent)
//        },
//        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
//    ) {
//        Text(text = "Set Next Session")
//    }
//}

@Composable
fun NextSessionButton(onDateSelected: (Long) -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            showDatePickerDialog(
                context,
                onDateSelected
            )
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(text = "Select Date and Time")
    }
}

private fun showDatePickerDialog(
    context: Context,
    onDateSelected: (Long) -> Unit
) {
    val currentDateTime = Calendar.getInstance()
    val year = currentDateTime.get(Calendar.YEAR)
    val month = currentDateTime.get(Calendar.MONTH)
    val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
    val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentDateTime.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            showTimePickerDialog(
                context,
                selectedYear,
                selectedMonth,
                selectedDay,
                hour,
                minute,
                onDateSelected
            )
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

private fun showTimePickerDialog(
    context: Context,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    onDateSelected: (Long) -> Unit
) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val selectedDateTime = Calendar.getInstance()
            selectedDateTime.set(
                year,
                month,
                day,
                selectedHour,
                selectedMinute
            )
            onDateSelected(selectedDateTime.timeInMillis)
        },
        hour,
        minute,
        false
    )
    timePickerDialog.show()
}



