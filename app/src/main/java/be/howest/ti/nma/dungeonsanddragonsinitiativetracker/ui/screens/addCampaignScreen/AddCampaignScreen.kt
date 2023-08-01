package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.photoPicker
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.requestPermissionForVisualMedia
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.selectImage
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

object AddCampaignScreenDestination : NavigationDestination {
    override val route: String = "add_campaign_screen"
    override val titleRes: Int = R.string.add_campaign_screen
}


@Composable
fun AddCampaignScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    addCampaignViewModel: AddCampaignViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val addCampaignUiState by addCampaignViewModel.addCampaignUiState.collectAsState()

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = AddCampaignScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                item {
                    CampaignName(
                        addCampaignViewModel,
                        addCampaignUiState
                    )
                }
                item {
                    CampaignImage(
                        addCampaignViewModel,
                        addCampaignUiState
                    )
                }
                item {
                    DungeonMaster(
                        addCampaignViewModel,
                        addCampaignUiState
                    )
                }
                item {
                    Player(
                        addCampaignViewModel,
                        addCampaignUiState
                    )
                }
            }
        },
        bottomBar = {
            CreateCampaignButton(
                onSave = {
                    coroutineScope.launch {
                        addCampaignViewModel.save()
                        navigateBack()
                    }
                },
            )
        }
    )
}

@Composable
fun CampaignName(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState
) {
    TextField(
        value = addCampaignUiState.campaignName,
        onValueChange = { addCampaignViewModel.updateCampaignName(it) },
        label = { Text(stringResource(id = R.string.campaign_name_label)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun CampaignImage(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
    ) {
        val context = LocalContext.current
        val photoPicker = photoPicker(
            addCampaignViewModel,
            context
        )
        val launcherSinglePermission = requestPermissionForVisualMedia(
            photoPicker,
            context
        )

        AsyncImage(
            model = addCampaignUiState.campaignImageUri ?: R.drawable.placeholder_view_vector,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(dimensionResource(id = R.dimen.image_size))
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clip(RoundedCornerShape(50))
                .clickable {
                    selectImage(
                        context,
                        photoPicker,
                        launcherSinglePermission
                    )
                }
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ImagePickerButton(
                context,
                photoPicker,
                launcherSinglePermission
            )
            //Camera Button
            Button(
                onClick = { /*TODO*/ },
                enabled = false,
            ) {
                Text(text = stringResource(id = R.string.take_an_image))

            }
        }

    }
}

@Composable
private fun ImagePickerButton(
    context: Context,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    launcherSinglePermission: ManagedActivityResultLauncher<String, Boolean>
) {
    Button(
        onClick = {
            selectImage(
                context,
                photoPicker,
                launcherSinglePermission
            )
        }
    ) {
        Text(text = stringResource(id = R.string.pick_an_image))
    }
}

@Composable
private fun SelectPlayerFromContactsButton() {
    val context = LocalContext.current

    val launcherSinglePermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            selectContact(
                context
            )
        } else {
            Toast.makeText(
                context,
                "Permission Denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Button(
        onClick = {
            val permission = Manifest.permission.READ_CONTACTS

            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager
                    .PERMISSION_GRANTED
            ) {
                selectContact(
                    context
                )
            } else {
                launcherSinglePermission.launch(permission)
            }
        },
        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(text = stringResource(id = R.string.select_player_from_contacts))
    }
}

fun selectContact(
    context: Context
) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = CommonDataKinds.Email.CONTENT_TYPE
    }
        .putExtra(
            ContactsContract.Intents.Insert.NAME,
            true
        )
        .putExtra(
            ContactsContract.Intents.Insert.EMAIL,
            true
        )

    context.startActivity(intent)
    intent.dataString?.let {
        Log.d(
            "implicitIntents",
            it
        )
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
fun DungeonMaster(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState
) {
    SectionTitle(title = stringResource(id = R.string.dm_section_title))
    DungeonMasterNameTextField(
        addCampaignUiState,
        addCampaignViewModel
    )
    DungeonMasterEmailTextField(
        addCampaignUiState,
        addCampaignViewModel
    )
}

@Composable
private fun DungeonMasterEmailTextField(
    addCampaignUiState: AddCampaignUiState,
    addCampaignViewModel: AddCampaignViewModel
) {
    var isEmailValid by remember { mutableStateOf(true) }

    TextField(
        value = addCampaignUiState.dungeonMaster.email,
        onValueChange = { newDungeonMasterEmail ->
            addCampaignViewModel.updateParticipantEmail(
                email = newDungeonMasterEmail,
                isDungeonMaster = true,
            )
            isEmailValid = addCampaignViewModel.isEmailValid(newDungeonMasterEmail)
        },
        label = { Text(stringResource(id = R.string.dm_email_label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = !isEmailValid,
        visualTransformation = if (!isEmailValid) VisualTransformation.None else VisualTransformation.None,
        trailingIcon = {
            if (!isEmailValid) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DungeonMasterNameTextField(
    addCampaignUiState: AddCampaignUiState,
    addCampaignViewModel: AddCampaignViewModel
) {
    TextField(
        value = addCampaignUiState.dungeonMaster.participantName,
        onValueChange = { newDungeonMasterName ->
            addCampaignViewModel.updateParticipantName(
                name = newDungeonMasterName,
                isDungeonMaster = true
            )
        },
        label = { Text(stringResource(id = R.string.dm_name_label)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Player(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState
) {
    val playerList = addCampaignViewModel.getParticipants()

    SectionTitle(title = stringResource(id = R.string.player_section_title))

    playerList.forEach { player ->
        PlayerPillBox(
            playerName = player.participantName,
            onDelete = { addCampaignViewModel.removePlayerFromPlayerList(player) }
        )
    }
    PlayerNameTextField(
        addCampaignViewModel,
        addCampaignUiState
    )
    PlayerEmailTextField(
        addCampaignViewModel,
        addCampaignUiState
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
        SelectPlayerFromContactsButton()
        AddPlayerButton(
            addCampaignViewModel
        )
    }
}

@Composable
private fun PlayerEmailTextField(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState
) {
    var isEmailValid by remember { mutableStateOf(true) }
    TextField(
        value = addCampaignUiState.player.email,
        onValueChange = { newPlayerEmail ->
            addCampaignViewModel.updateParticipantEmail(
                email =
                newPlayerEmail
            )
            isEmailValid = addCampaignViewModel.isEmailValid(newPlayerEmail)
        },
        label = { Text(stringResource(id = R.string.player_email_label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = !isEmailValid,
        visualTransformation = if (!isEmailValid) VisualTransformation.None else VisualTransformation.None,
        trailingIcon = {
            if (!isEmailValid) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PlayerNameTextField(
    addCampaignViewModel: AddCampaignViewModel,
    addCampaignUiState: AddCampaignUiState
) {
    TextField(
        value = addCampaignUiState.player.participantName,
        onValueChange = { newPlayerName -> addCampaignViewModel.updateParticipantName(name = newPlayerName) },
        label = { Text(stringResource(id = R.string.player_name_label)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun AddPlayerButton(
    addCampaignViewModel: AddCampaignViewModel,
) {
    Button(
        onClick = { addCampaignViewModel.updatePlayerList() },
        enabled = addCampaignViewModel.validatePlayerInputFields()
    ) {
        Text(text = stringResource(id = R.string.add_player))
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

@Composable
private fun CreateCampaignButton(
    onSave: () -> Unit,
) {
    Button(
        onClick = onSave,
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
    ) {
        Text(
            text = stringResource(R.string.create_campaign),
            textAlign = TextAlign.Center
        )
    }
}