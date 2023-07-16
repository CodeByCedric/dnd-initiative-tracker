package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CharacterScreenDestination : NavigationDestination {
    override val route: String = "character_overview_screen"
    override val titleRes: Int = R.string.character_overview_screen

}

@Composable
fun CharacterScreen(
    navigateToEncounterBuilderScreen: () -> Unit = {},
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navigateToEncounterBuilderScreen() },
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.to_encounter_builder_screen_button),
            textAlign = TextAlign.Center
        )
    }

}

//@Preview
//@Composable
//fun CharacterPreviewScreen() {
//    DungeonsAndDragonsInitiativeTrackerTheme() {
//        CharacterScreen()
//    }
//}