package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createPrimaryCharacterScreen

import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CreatePrimaryCharacterScreenDestination : NavigationDestination {
    override val route: String = "create_primary_character_screen"
    override val titleRes: Int = R.string.create_primary_character_screen
}

@Composable
fun CreatePrimaryCharacterScreen() {
}