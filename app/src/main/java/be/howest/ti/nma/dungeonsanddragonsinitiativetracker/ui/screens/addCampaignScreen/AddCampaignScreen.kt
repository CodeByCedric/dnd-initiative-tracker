package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import coil.compose.AsyncImage

object AddCampaignScreenDestination : NavigationDestination {
    override val route: String = "add_campaign_screen"
    override val titleRes: Int = R.string.add_campaign_screen
}

@Composable
fun AddCampaignScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
) {
    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = AddCampaignScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }

    ) { innerPadding ->
        AddCampaignForm(
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun AddCampaignForm(
    modifier: Modifier,
) {

    Column(
        modifier = modifier
    ) {
        CampaignName()
        CampaignImage()
        SectionTitle(title = stringResource(id = R.string.dm_section_title))
        DungeonMaster()
        SectionTitle(title = stringResource(id = R.string.player_section_title))

        Player()
        Button(
            onClick = {
                //todo create campaign, campaignParticipants, etc.
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.create_campaign),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CampaignName() {
    var campaignName by remember { mutableStateOf("") }
    TextField(
        value = campaignName,
        onValueChange = { newCampaignName -> campaignName = newCampaignName },
        label = { Text(stringResource(id = R.string.campaign_name_label)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CampaignImage(
    modifier: Modifier = Modifier
) {
    var painter by remember { mutableStateOf<Uri?>(null) }
    Row(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
    ) {
        AsyncImage(
            model = painter ?: R.drawable.placeholder_view_vector,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(50))
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            val photoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { painter = it }
            )
            Button(
                onClick = {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(text = stringResource(id = R.string.pick_an_image))
            }
            Button(
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(id = R.string.take_an_image))

            }
        }

    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun DungeonMaster() {
    var dungeonMasterName by remember { mutableStateOf("") }
    var dungeonMasterEmail by remember { mutableStateOf("") }

    TextField(
        value = dungeonMasterName,
        onValueChange = { newDungeonMasterName -> dungeonMasterName = newDungeonMasterName },
        label = { Text(stringResource(id = R.string.dm_name_label)) },
        modifier = Modifier.fillMaxWidth()
    )
    TextField(
        value = dungeonMasterEmail,
        onValueChange = { newDungeonMasterEmail -> dungeonMasterEmail = newDungeonMasterEmail },
        label = { Text(stringResource(id = R.string.dm_email_label)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Player() {
    var playerName by remember { mutableStateOf("") }
    var playerEmail by remember { mutableStateOf("") }
    var playerList by remember { mutableStateOf(listOf<Participant>()) }

    playerList.forEach { player ->
        PlayerPillBox(
            playerName = player.participantName,
            onDelete = { playerList = playerList - player }
        )
    }
    TextField(
        value = playerName,
        onValueChange = { newPlayerName -> playerName = newPlayerName },
        label = { Text(stringResource(id = R.string.player_name_label)) },
        modifier = Modifier.fillMaxWidth()
    )
    TextField(
        value = playerEmail,
        onValueChange = { newPlayerEmail -> playerEmail = newPlayerEmail },
        label = { Text(stringResource(id = R.string.player_email_label)) },
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        Button(
            onClick = {
                playerList = playerList + Participant(
                    participantName = playerName,
                    email = playerEmail,
                )
                playerName = ""
                playerEmail = ""
            }
        ) {
            Text(text = stringResource(id = R.string.add_player))
        }
    }

}

@Composable
fun PlayerPillBox(
    playerName: String,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = playerName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = onDelete)
        )
    }
}

