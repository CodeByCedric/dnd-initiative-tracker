package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerTopAppBar
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


/*
* TODO: spruce up the layout a bit
*  refactor fun CharacterType() so it works with uistate instead
*
* */
object CreateCharacterScreenDestination : NavigationDestination {
    override val route: String = "create_primary_character_screen"
    override val titleRes: Int = R.string.create_primary_character_screen
}

@Composable
fun CreateCharacterScreen(
    campaignId: Long,
    navigateToCharacterScreen: () -> Unit,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    createCharacterViewModel: CreateCharacterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val createCharacterUiState by createCharacterViewModel.createCharacterUiState.collectAsState()

    Scaffold(
        topBar = {
            DnDInitiativeTrackerTopAppBar(
                title = stringResource(id = CreateCharacterScreenDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        content = { innerPadding ->
            CreateCharacterForm(
                createCharacterViewModel = createCharacterViewModel,
                createCharacterUiState = createCharacterUiState,
                modifier = Modifier
                    .padding(innerPadding)
            )
        },
        bottomBar = {
            CreateCharacterButton(
                onSave = {
                    coroutineScope.launch {
                        createCharacterViewModel.createCharacter()
                        navigateBack()
                    }
                }
            )
        }
    )
}

@Composable
fun CreateCharacterButton(
    onSave: () -> Unit
) {
    Button(
        onClick = onSave,
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
    ) {
        Text(
            text = stringResource(R.string.create_character),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CreateCharacterForm(
    createCharacterViewModel: CreateCharacterViewModel,
    createCharacterUiState: CreateCharacterUiState,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        //CharacterName
        //ArmorClass
        //Initiative Modifier
        //IsPrimary, Secondary or Enemy
        item {
            CharacterName(
                createCharacterViewModel,
                createCharacterUiState,
            )
        }
        item {
            ArmorClass(
                createCharacterViewModel,
                createCharacterUiState,
            )
        }
        item {
            InitiativeModifier(
                createCharacterViewModel,
                createCharacterUiState,
            )
        }
        item {
            CharacterType(
                createCharacterViewModel,
            )
        }
    }
}

@Composable
fun CharacterName(
    createCharacterViewModel: CreateCharacterViewModel,
    createCharacterUiState: CreateCharacterUiState,
) {
    TextField(
        value = createCharacterUiState.characterName,
        onValueChange = { characterName -> createCharacterViewModel.updateCharacterName(characterName) },
        label = { Text(stringResource(id = R.string.character_name_textfield)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ArmorClass(
    createCharacterViewModel: CreateCharacterViewModel,
    createCharacterUiState: CreateCharacterUiState,
) {
    val armorClass = createCharacterUiState.armorClass
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(id = R.string.character_armorclass_textfield))
        TextField(
            value = armorClass.toString(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { createCharacterViewModel.decrementArmorClassByOne() }) {
            Icon(
                Icons.Default.Remove,
                contentDescription = stringResource(id = R.string.decrease_armor_class)
            )
        }
        IconButton(onClick = { createCharacterViewModel.incrementArmorClassByOne() }) {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(id = R.string.increase_armor_class)
            )
        }
    }
}

@Composable
fun InitiativeModifier(
    createCharacterViewModel: CreateCharacterViewModel,
    createCharacterUiState: CreateCharacterUiState,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(id = R.string.character_initiative_textfield))

        TextField(
            value = createCharacterUiState.initiativeModifier.toString(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { createCharacterViewModel.decrementInitiativeModifierByOne() }) {
            Icon(
                Icons.Default.Remove,
                contentDescription = stringResource(id = R.string.decrease_initiative_modifier)
            )
        }
        IconButton(onClick = { createCharacterViewModel.incrementInitiativeModifierByOne() }) {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(id = R.string.increase_initiative_modifier)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterType(
    createCharacterViewModel: CreateCharacterViewModel,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.character_type)
        )
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            TextField(
                value = createCharacterViewModel.checkCharacterType(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.primary_character)) },
                    onClick = {
                        createCharacterViewModel.updateCharacterType(isPrimaryCharacter = true)
                        isExpanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.secondary_character)) },
                    onClick = {
                        createCharacterViewModel.updateCharacterType(isSecondaryCharacter = true)
                        isExpanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.enemy)) },
                    onClick = {
                        createCharacterViewModel.updateCharacterType(isEnemy = true)
                        isExpanded = false
                    })
            }

        }
    }

}
