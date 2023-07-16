package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

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

object SkirmishScreenDestination : NavigationDestination {
    override val route: String = "skirmish_screen"
    override val titleRes: Int = R.string.skirmish_screen
}

@Composable
fun SkirmishScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateToCharacterScreen: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navigateToCharacterScreen() },
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.to_character_overview_button),
            textAlign = TextAlign.Center
        )
    }
}

//@Preview
//@Composable
//fun SkirmishScreenPreview() {
//    DungeonsAndDragonsInitiativeTrackerTheme {
//        SkirmishScreen()
//    }
//}