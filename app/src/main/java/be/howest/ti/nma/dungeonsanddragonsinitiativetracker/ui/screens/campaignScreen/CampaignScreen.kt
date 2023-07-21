package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignParticipantDetails
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Locale

/*TODO
Add more details to the expanded card, viz. the email addresses of the participants
Add functionality to edit campaign participants from the card
Add functionality to add new participants to a campaign from the card
Move NavigateToCharacterScreenButton to bottom bar in scaffold
*/

object CampaignScreenDestination : NavigationDestination {
    override val route: String = "campaign_screen"
    override val titleRes: Int = R.string.campaign_overview_screen

}

//@Composable
//fun CampaignScreen(
//    navigateToCharacterScreen: (Long) -> Unit,
//    navigateToAddCampaignScreen: () -> Unit,
//    modifier: Modifier = Modifier,
//    campaignViewModel: CampaignViewModel = viewModel(factory = AppViewModelProvider.Factory),
//    canNavigateBack: Boolean = false,
//) {
//    val dndUiState = campaignViewModel.dndUiState.collectAsState()
//    Scaffold(
//        topBar = {
//            DnDInitiativeTrackerTopAppBar(
//                title = stringResource(id = CampaignScreenDestination.titleRes),
//                canNavigateBack = canNavigateBack,
//                )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = navigateToAddCampaignScreen,
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier
//                    .padding(dimensionResource(id = R.dimen.padding_large))
//                    .testTag("addCampaignFOB")
//            )
//            {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = stringResource(R.string.add_campaign_screen)
//                )
//            }
//        },
//        bottomBar = {
//            NavigateToCharacterScreenButton(
//                navigateToCharacterScreen = navigateToCharacterScreen,
//                selectedCampaignId = dndUiState.value.selectedCampaignId
//            )
//        }
//    ) { innerPadding ->
//        CampaignBody(
//            campaignViewModel = campaignViewModel,
//            dndUiState = dndUiState.value,
//            modifier = modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//        )
//
//    }
//}
//
//@Composable
//fun CampaignBody(
//    campaignViewModel: CampaignViewModel,
//    dndUiState: DnDUiState,
//    modifier: Modifier
//) {
//    Column(
//        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
//    ) {
//        val campaigns by dndUiState.campaigns.collectAsState(initial = emptyList())
//
//        campaigns.forEach { campaign: Campaign ->
//            CampaignCard(
//                campaign = campaign,
//                isSelected = campaign.campaignId == dndUiState.selectedCampaignId,
//                onCardTapped = {campaignViewModel.setSelectedCampaignId(campaign.campaignId)}
//            )
//        }
//    }
//}
//
//@Composable
//fun CampaignCard(
//    campaign: Campaign,
//    modifier: Modifier = Modifier,
//    isSelected: Boolean,
//    onCardTapped: () -> Unit
//) {
//    Card(
//        modifier = modifier
//            .padding(dimensionResource(id = R.dimen.padding_small))
//            .clickable { onCardTapped() }
//            .border(
//                width = 2.dp,
//                color = if (isSelected) Color.Blue else Color.Transparent,
//                shape = RoundedCornerShape(8.dp)
//            )
//            .testTag("campaignCard")
//    ) {
//        Column() {
//            Text(text = campaign.campaignName)
//        }
//    }
//}
//
//@Composable
//private fun NavigateToCharacterScreenButton(
//    navigateToCharacterScreen: (Long) -> Unit,
//    selectedCampaignId: Long
//) {
//    Button(
//        onClick = {navigateToCharacterScreen(selectedCampaignId) },
//        modifier = Modifier
//            .height(dimensionResource(id = R.dimen.button_height))
//            .fillMaxWidth()
//            .padding(dimensionResource(id = R.dimen.padding_small)),
//        enabled = selectedCampaignId != -1L
//    ) {
//        if (selectedCampaignId != -1L) {
//            Text(
//                text = stringResource(R.string.to_character_overview_screen_button),
//                textAlign = TextAlign.Center
//            )
//        } else {
//            Text(
//                text = stringResource(R.string.greyed_out_select_campaign_button),
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//@Composable
//fun CampaignParticipants(
//    campaignViewModel: CampaignViewModel,
//    participants: List<CampaignParticipantDetails>
//) {
//
//    Column(
//        modifier = Modifier.padding(
//            dimensionResource(id = R.dimen.padding_medium),
//            dimensionResource(id = R.dimen.padding_small),
//            dimensionResource(id = R.dimen.padding_medium),
//            dimensionResource(id = R.dimen.padding_medium)
//        )
//    ) {
//        participants.forEach { participant ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = participant.participantName,
//                    modifier = Modifier.weight(1f)
//                )
//                // Remove Participant Button
//                RemoveParticipantButton(
//                    campaignViewModel,
//                    participant
//                )
//            }
//        }
//    }
//
//}
//
//
//@Composable
//fun ExpandCampaignCardButton(
//    expanded: Boolean,
//    onClickExpandCard: () -> Unit,
//) {
//    IconButton(onClick = onClickExpandCard) {
//        Icon(
//            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
//            contentDescription = stringResource(id = R.string.expand_button_content_description)
//        )
//    }
//}
//
//
//@Composable
//fun CampaignImage(
//    campaign: Campaign,
//    modifier: Modifier = Modifier
//) {
//    AsyncImage(
//        model = campaign.campaignImageDrawable ?: campaign.campaignImageUri,
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        modifier = modifier
//            .size(100.dp)
//            .padding(dimensionResource(id = R.dimen.padding_small))
//            .clip(RoundedCornerShape(50))
//    )
//}
//
//@Composable
//fun CampaignInformation(
//    campaign: Campaign,
//    modifier: Modifier = Modifier
//) {
//    Column {
//        Text(
//            text = campaign.campaignName,
//            fontSize = dimensionResource(id = R.dimen.fontSize_large).value.sp,
//            modifier = modifier
//                .padding(top = dimensionResource(id = R.dimen.padding_small))
//        )
//    }
//}
//
//@Composable
//private fun RemoveCampaignButton(
//    campaignViewModel: CampaignViewModel,
//    campaign: Campaign
//) {
//    val coroutineScope = rememberCoroutineScope()
//    IconButton(
//        onClick = {
//            coroutineScope.launch { campaignViewModel.deleteCampaign(campaign) }
//        }
//    ) {
//        Icon(
//            Icons.Default.Delete,
//            contentDescription = stringResource(id = R.string.delete_campaign_icon_label)
//        )
//    }
//}
//
//@Composable
//fun RemoveParticipantButton(
//    campaignViewModel: CampaignViewModel,
//    campaignParticipantDetails: CampaignParticipantDetails
//) {
//    val coroutineScope = rememberCoroutineScope()
//    IconButton(
//        onClick = {
//            coroutineScope.launch {
//                campaignViewModel.deleteParticipant(
//                    CampaignParticipant(
//                        participantId = campaignParticipantDetails.participantId,
//                        campaignId = campaignParticipantDetails.campaignId
//                    )
//                )
//            }
//
//
//        },
//        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
//    ) {
//        Icon(
//            Icons.Default.Delete,
//            contentDescription = stringResource(id = R.string.delete_player_icon_label)
//        )
//    }
//}
//
//@Composable
//fun NextSessionButton(
//    campaign: Campaign,
//    campaignViewModel: CampaignViewModel
//) {
//    val context = LocalContext.current
//    Button(
//        onClick = {
//            showDatePickerDialog(
//                campaign,
//                context,
//                campaignViewModel
//            )
//        },
//        modifier = Modifier.padding(top = 16.dp)
//    ) {
//        Text(text = "Select Date and Time")
//    }
//}
//
//private fun showDatePickerDialog(
//    campaign: Campaign,
//    context: Context,
//    campaignViewModel: CampaignViewModel
//) {
//    val currentDateTime = Calendar.getInstance()
//    val year = currentDateTime.get(Calendar.YEAR)
//    val month = currentDateTime.get(Calendar.MONTH)
//    val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
//    val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
//    val minute = currentDateTime.get(Calendar.MINUTE)
//
//    val datePickerDialog = DatePickerDialog(
//        context,
//        { _, selectedYear, selectedMonth, selectedDay ->
//            showTimePickerDialog(
//                campaign,
//                context,
//                selectedYear,
//                selectedMonth,
//                selectedDay,
//                hour,
//                minute,
//                campaignViewModel
//            )
//        },
//        year,
//        month,
//        day
//    )
//    datePickerDialog.show()
//}
//
//private fun showTimePickerDialog(
//    campaign: Campaign,
//    context: Context,
//    year: Int,
//    month: Int,
//    day: Int,
//    hour: Int,
//    minute: Int,
//    campaignViewModel: CampaignViewModel
//) {
//    val timePickerDialog = TimePickerDialog(
//        context,
//        { _, selectedHour, selectedMinute ->
//            val selectedDateTimeCalendar = Calendar.getInstance().apply {
//                set(
//                    year,
//                    month,
//                    day,
//                    selectedHour,
//                    selectedMinute
//                )
//            }
//
//            val dateTimeInMillis = selectedDateTimeCalendar.timeInMillis
//            campaign.nextSession = dateTimeInMillis
//
//            launchCalendarIntent(
//                campaign,
//                context,
//                selectedDateTimeCalendar
//            )
//            campaignViewModel.updateCampaignDateTime(
//                campaign.campaignId,
//                campaign.nextSession!!
//            )
//        },
//        hour,
//        minute,
//        true
//    )
//    timePickerDialog.show()
//}
//
//private fun launchCalendarIntent(
//    campaign: Campaign,
//    context: Context,
//    selectedDateTimeCalendar: Calendar
//) {
//    //todo work with package-manager and add to manifest
//    val title = campaign.campaignName
//    val begin = selectedDateTimeCalendar.timeInMillis
//    val end = begin + 60 * 60 * 4000 // Add 4 hours
//
//    val intent = Intent(Intent.ACTION_INSERT)
//        .setData(CalendarContract.Events.CONTENT_URI)
//        .putExtra(
//            CalendarContract.Events.TITLE,
//            title
//        )
//        .putExtra(
//            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
//            begin
//        )
//        .putExtra(
//            CalendarContract.EXTRA_EVENT_END_TIME,
//            end
//        )
//
//    context.startActivity(intent)
//
//
//}
//
//
//private fun convertLongToString(dateTimeInMillis: Long): String {
//    val dateFormat = SimpleDateFormat(
//        "dd-MMMM HH:mm",
//        Locale.getDefault()
//    )
//    val dateTime = Date(dateTimeInMillis)
//    return dateFormat.format(dateTime)
//}


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
    ) { innerPadding ->
        CampaignBody(
            navigateToCharacterScreen = navigateToCharacterScreen,
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
    navigateToCharacterScreen: (Long) -> Unit,
    campaignViewModel: CampaignViewModel,
    campaignUiState: CampaignUiState,
    modifier: Modifier
) {
    val campaigns by campaignUiState.campaigns.collectAsState(initial = emptyList())
    var selectedCampaign by remember { mutableStateOf<Campaign?>(null) }

    LazyColumn(
        modifier = modifier
    ) {
        items(campaigns) { campaign ->
            CampaignCard(
                campaign = campaign,
                isSelected = campaign == selectedCampaign,
                onSelect = {
                    selectedCampaign = if (it == selectedCampaign) null else it
                },
                campaignViewModel = campaignViewModel
            )
        }
        item {
            NavigateToCharacterScreenButton(
                navigateToCharacterScreen,
                selectedCampaign
            )
        }
    }
}

@Composable
private fun NavigateToCharacterScreenButton(
    navigateToCharacterScreen: (Long) -> Unit,
    selectedCampaign: Campaign?
) {
    Button(
        onClick = {
            selectedCampaign?.let { navigateToCharacterScreen(it.campaignId) }
        },
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        enabled = selectedCampaign != null
    ) {
        if (selectedCampaign != null) {
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
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable { onSelect(campaign) }
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .testTag("campaignCard")
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
                Column {
                    CampaignInformation(campaign)
                    Text(
                        text = "Next session:",
                        fontSize = dimensionResource(id = R.dimen.fontSize_medium).value.sp,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = campaign.nextSession?.let { convertLongToString(it) }
                            ?: stringResource(id = R.string.no_next_session_planned),
                        fontSize = dimensionResource(R.dimen.fontSize_small).value.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Column {
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
            fontSize = dimensionResource(id = R.dimen.fontSize_large).value.sp,
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
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_player_icon_label)
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
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(text = "Select Date and Time")
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
    //todo work with package-manager and add to manifest
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


