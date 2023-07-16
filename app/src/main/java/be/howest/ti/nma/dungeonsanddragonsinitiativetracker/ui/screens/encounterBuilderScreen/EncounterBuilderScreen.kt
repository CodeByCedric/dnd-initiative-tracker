package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.encounterBuilderScreen

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


object EncounterBuilderScreenDestination : NavigationDestination {
    override val route: String = "encounter_builder_screen"
    override val titleRes: Int = R.string.encounter_builder_screen
}

@Composable
fun EncounterBuilderScreen(
    navigateToInitiativeScreen: () -> Unit = {},
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navigateToInitiativeScreen() },
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.to_initiative_screen_button),
            textAlign = TextAlign.Center
        )
    }
}

//@Preview
//@Composable
//fun EncounterBuilderPreviewScreen() {
//    DungeonsAndDragonsInitiativeTrackerTheme() {
//        EncounterBuilderScreen()
//    }
//}