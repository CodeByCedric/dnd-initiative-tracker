package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterOverviewScreenDestination

@Composable
fun DnDInitiativeTrackerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = CampaignScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = CampaignScreenDestination.route) {
            CampaignScreen(
                navigateToCharacterOverviewScreen = {
                    navController.navigate(CharacterOverviewScreenDestination.route)
                }
            ) {
                navController.navigate(AddCampaignScreenDestination.route)

            }
        }
    }
}