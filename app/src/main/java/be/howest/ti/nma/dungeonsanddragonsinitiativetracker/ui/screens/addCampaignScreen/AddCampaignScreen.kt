package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object AddCampaignScreenDestination : NavigationDestination {
    override val route: String = "add_campaign_screen"
    override val titleRes: Int = R.string.character_overview_screen

}

@Composable
fun addCampaignScreen() {
}