package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CampaignScreenDestination : NavigationDestination {
    override val route: String = "campaign_screen"
    override val titleRes: Int = R.string.campaign_overview_screen

}

@Composable
fun CampaignScreen(
    navigateToCharacterOverviewScreen: () -> Unit,
    navigateToAddCampaignScreen: () -> Unit
) {
    Text(text = "hello")
}