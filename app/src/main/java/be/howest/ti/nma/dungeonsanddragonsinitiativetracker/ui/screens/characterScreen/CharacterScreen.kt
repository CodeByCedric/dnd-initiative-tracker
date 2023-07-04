package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CharacterOverviewScreenDestination : NavigationDestination {
    override val route: String = "character_overview_screen"
    override val titleRes: Int = R.string.character_overview_screen

}

@Composable
fun CharacterScreen() {
}