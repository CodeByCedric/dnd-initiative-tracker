package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.provider.CalendarContract
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignParticipantDetails
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.notifications.NextSessionNotificationService
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Locale

/*
Todo: Add functionality to edit campaign participants from the card
Todo: Add functionality to add new participants to a campaign from the card
*/

object CampaignScreenDestination : NavigationDestination {
    override val route: String = "campaign_screen"
    override val titleRes: Int = R.string.campaign_overview_screen

}
@Composable
fun CampaignScreen(
    navigateToCharacterScreen: (Long) -> Unit,
    navigateToAddCampaignScreen: () -> Unit,
    modifier: Modifier = Modifier,
    campaignViewModel: CampaignViewModel = viewModel(factory = AppViewModelProvider.Factory),
    canNavigateBack: Boolean = false,
) {
    val campaignUiState by campaignViewModel.campaignUiState.collectAsState()

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CampaignScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddCampaignScreen,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .testTag("addCampaignFOB")
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_campaign_screen)
                )
            }
        },
        bottomBar = {
            NavigateToCharacterScreenButton(
                navigateToCharacterScreen = navigateToCharacterScreen,
                selectedCampaignId = campaignUiState.selectedCampaignId
            )
        }
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CampaignBody(
    campaignViewModel: CampaignViewModel,
    campaignUiState: CampaignUiState,
    modifier: Modifier
) {
    val campaigns by campaignUiState.campaigns.collectAsState(initial = emptyList())

    val context = LocalContext.current

    val postNotificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val nextSessionNotificationService =
        remember { NextSessionNotificationService(context = context) }

    LaunchedEffect(
        key1 = true,
    ) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(campaigns) { campaign ->
            CampaignCard(
                campaign = campaign,
                isSelected = campaign.campaignId == campaignUiState.selectedCampaignId,
                onSelect = { campaignViewModel.updateSelectedCampaignId(campaign.campaignId) },
                campaignViewModel = campaignViewModel
            )
        }
//        item {
//            Button(onClick = { nextSessionNotificationService.showBasicNotification() }) {
//                Text(text = "Show basic notification")
//            }
//        }
    }
}

@Composable
private fun NavigateToCharacterScreenButton(
    navigateToCharacterScreen: (Long) -> Unit,
    selectedCampaignId: Long
) {
    Button(
        onClick = {
            navigateToCharacterScreen(selectedCampaignId)
        },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        enabled = selectedCampaignId != -1L
    ) {
        if (selectedCampaignId != -1L) {
            Text(
                text = stringResource(R.string.to_character_overview_screen_button),
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = stringResource(R.string.greyed_out_select_campaign_button),
                textAlign = TextAlign.Center
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

    var isCampaignCardExpanded by remember { mutableStateOf(false) }
    val participants = campaignViewModel.getCampaignParticipantsWithDetails(
        campaign.campaignId
    ).collectAsState(initial = emptyList()).value
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable { onSelect(campaign) }
            .border(
                width = dimensionResource(id = R.dimen.border_medium),
                color = if (isSelected) MaterialTheme.colorScheme.outline else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .testTag("campaignCard")
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier.weight(0.2f)
                ) {
                    CampaignImage(campaign)
                }

                Column(
                    modifier = modifier.weight(0.7f)
                ) {
                    CampaignInformation(campaign)
                    Text(
                        text = "Next session:",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = campaign.nextSession?.let { convertLongToString(it) }
                            ?: stringResource(id = R.string.no_next_session_planned),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                }

                Column(modifier = modifier.weight(0.1f)) {
                    ExpandCampaignCardButton(
                        expanded = isCampaignCardExpanded,
                        onClickExpandCard = { isCampaignCardExpanded = !isCampaignCardExpanded },
                    )
                    RemoveCampaignButton(
                        campaignViewModel,
                        campaign
                    )
                }

            }
        }
        if (isCampaignCardExpanded) {
            CampaignParticipants(
                participants = participants,
                campaignViewModel = campaignViewModel
            )
            NextSessionButton(
                campaign = campaign,
                campaignViewModel = campaignViewModel
            )
        }
    }
}

@Composable
fun CampaignParticipants(
    campaignViewModel: CampaignViewModel,
    participants: List<CampaignParticipantDetails>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))

    ) {
        Text(
            text = stringResource(id = R.string.participant_section_title),
            style = MaterialTheme.typography.headlineSmall,
        )
//        IconButton(
//            onClick = {
//                // Todo: add participant, remove text composable above
//            },
//            )
//        {
//            Icon(
//                imageVector = Icons.Default.PersonAdd,
//                contentDescription = stringResource(id = R.string.expand_button_content_description)
//            )
//        }
    }

    participants.forEach { participant ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_small)
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.40f)
            ) {
                Text(
                    text = participant.participantName,
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.40f)
            ) {
                Text(
                    text = participant.email
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.1f)
            ) {
//                EditParticipantButton(
//                    campaignViewModel,
//                    participant
//                )
            }

            Column(
                modifier = Modifier
                    .weight(0.1f)
            ) {
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
        model = if (campaign.campaignImageUri != null) {
            campaign.campaignImageUri
        } else if (campaign.campaignImageDrawable != null) {
            campaign.campaignImageDrawable
        } else {
            R.drawable.placeholder_view_vector
        },
//        model = campaign.campaignImageDrawable ?: campaign.campaignImageUri,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .padding(dimensionResource(id = R.dimen.padding_small))
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
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            modifier = modifier
                .padding(top = dimensionResource(id = R.dimen.padding_small))
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
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_player_icon_label)
        )
    }
}

@Composable
fun EditParticipantButton(
    campaignViewModel: CampaignViewModel,
    participant: CampaignParticipantDetails
) {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
    }
}

@Composable
fun NextSessionButton(
    campaign: Campaign,
    campaignViewModel: CampaignViewModel
) {
    val context = LocalContext.current
    Button(
        onClick = {
            showDatePickerDialog(
                campaign,
                context,
                campaignViewModel
            )
        },
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(text = stringResource(id = R.string.plan_next_session_button_label))
    }
}

private fun showDatePickerDialog(
    campaign: Campaign,
    context: Context,
    campaignViewModel: CampaignViewModel
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
                campaign,
                context,
                selectedYear,
                selectedMonth,
                selectedDay,
                hour,
                minute,
                campaignViewModel
            )
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

private fun showTimePickerDialog(
    campaign: Campaign,
    context: Context,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    campaignViewModel: CampaignViewModel
) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val selectedDateTimeCalendar = Calendar.getInstance().apply {
                set(
                    year,
                    month,
                    day,
                    selectedHour,
                    selectedMinute
                )
            }

            val dateTimeInMillis = selectedDateTimeCalendar.timeInMillis
            campaign.nextSession = dateTimeInMillis

            launchCalendarIntent(
                campaign,
                context,
                selectedDateTimeCalendar
            )
            campaignViewModel.updateCampaignDateTime(
                campaign.campaignId,
                campaign.nextSession!!
            )
        },
        hour,
        minute,
        true
    )
    timePickerDialog.show()
}

private fun launchCalendarIntent(
    campaign: Campaign,
    context: Context,
    selectedDateTimeCalendar: Calendar
) {
    // todo work with package-manager and add to manifest
    // todo return date and time from the calendar, and compare with date and time selected, if different, update dateTime for use in app, if null, don't set next session
    val title = campaign.campaignName
    val begin = selectedDateTimeCalendar.timeInMillis
    val end = begin + 60 * 60 * 4000 // Add 4 hours

    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(
            CalendarContract.Events.TITLE,
            title
        )
        .putExtra(
            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            begin
        )
        .putExtra(
            CalendarContract.EXTRA_EVENT_END_TIME,
            end
        )

    context.startActivity(intent)


}


private fun convertLongToString(dateTimeInMillis: Long): String {
    val dateFormat = SimpleDateFormat(
        "dd-MMMM HH:mm",
        Locale.getDefault()
    )
    val dateTime = Date(dateTimeInMillis)
    return dateFormat.format(dateTime)
}


