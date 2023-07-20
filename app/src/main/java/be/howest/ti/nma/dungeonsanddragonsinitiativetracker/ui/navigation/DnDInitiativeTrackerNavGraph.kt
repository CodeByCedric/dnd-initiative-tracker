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
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createPrimaryCharacterScreen.CreatePrimaryCharacterScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.encounterBuilderScreen.EncounterBuilderScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.encounterBuilderScreen.EncounterBuilderScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.initiativeScreen.InitiativeScreen
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.initiativeScreen.InitiativeScreenDestination
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
        composable(route = AddCampaignScreenDestination.route) {
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
                navigateToEncounterBuilderScreen = {
                    navController.navigate(EncounterBuilderScreenDestination.route)
                },
                navigateToCreateCharacterScreen = { navController.navigate(CreatePrimaryCharacterScreenDestination.route) },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navController = navController
            )
        }
        composable(route = EncounterBuilderScreenDestination.route) {
            EncounterBuilderScreen(
                navigateToInitiativeScreen = {
                    navController.navigate(
                        InitiativeScreenDestination
                            .route
                    )
                },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = InitiativeScreenDestination.route) {
            InitiativeScreen(
                navigateToSkirmishScreen = {
                    navController.navigate(
                        SkirmishScreenDestination
                            .route
                    )
                },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = SkirmishScreenDestination.route) {
            SkirmishScreen(
                navigateToCharacterScreen = {
                    navController.navigate(
                        CharacterScreenDestination
                            .route
                    )
                },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

    }
}