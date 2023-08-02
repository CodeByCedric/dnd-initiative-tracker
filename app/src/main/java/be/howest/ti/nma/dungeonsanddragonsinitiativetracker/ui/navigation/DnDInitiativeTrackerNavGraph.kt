package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen.CreateCharacterScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen.CreateCharacterScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen.SkirmishScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen.SkirmishScreenDestination

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
                navigateToCharacterScreen = { campaignId ->
                    navController.navigate("${CharacterScreenDestination.route}?campaignId=$campaignId")
                },
                navigateToAddCampaignScreen = {
                    navController.navigate(AddCampaignScreenDestination.route)
                },
            )
        }
        composable(
            route = AddCampaignScreenDestination.route
        ) {
            AddCampaignScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = "${CharacterScreenDestination.route}?campaignId={campaignId}",
            arguments = listOf(navArgument("campaignId") { defaultValue = -1L })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId") ?: -1L
            CharacterScreen(
                campaignId = campaignId,
                navigateToSkirmishScreen = {
                    navController.navigate(
                        route = "${SkirmishScreenDestination.route}?campaignId=$campaignId"
                    )
                },
                navigateToCreateCharacterScreen = {
                    navController.navigate(
                        route = "${CreateCharacterScreenDestination.route}?campaignId=$campaignId",
                    )
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = "${CreateCharacterScreenDestination.route}?campaignId={campaignId}",
            arguments = listOf(navArgument("campaignId") { defaultValue = -1L })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId") ?: -1L
            CreateCharacterScreen(
                campaignId = campaignId,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = "${SkirmishScreenDestination.route}?campaignId={campaignId}",
            arguments = listOf(navArgument("campaignId") { defaultValue = -1L })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId") ?: -1L
            SkirmishScreen(
                navigateToCharacterScreen = {
                    navController.navigate(
                        route = "${CharacterScreenDestination.route}?campaignId=$campaignId"
                    )
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }

    }
}